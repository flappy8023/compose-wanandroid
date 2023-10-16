package com.flappy.wandroid.ui.page.home.discovery

import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flappy.wandroid.base.BaseViewModel
import com.flappy.wandroid.base.IViewEvent
import com.flappy.wandroid.base.IViewSideEffect
import com.flappy.wandroid.base.IViewState
import com.flappy.wandroid.config.GlobalConfig
import com.flappy.wandroid.data.bean.Article
import com.flappy.wandroid.data.repository.HomeRepository
import com.flappy.wandroid.ui.widget.BannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description 发现页viewModel
 * @Date 2023年09月20日 16:40
 **/
@HiltViewModel
class DiscoveryVM @Inject constructor(private val repository: HomeRepository) :
    BaseViewModel<DiscoveryUiState, DiscoveryUiEvent, IViewSideEffect>() {
    val pager = Pager(
        config = PagingConfig(GlobalConfig.PAGE_SIZE),
        pagingSourceFactory = repository::discoveryPageSource
    ).flow.cachedIn(viewModelScope)

    init {
        fetchData()
    }

    override fun getInitialState() = DiscoveryUiState(true, listOf(), listOf())


    override fun handleEvents(event: DiscoveryUiEvent) {
        when (event) {
            is  DiscoveryUiEvent.Refresh -> fetchData()
        }
    }


    private fun fetchData() {
        viewModelScope.launch {
            //zip同时请求轮播图和置顶文章，统一回调
            repository.getBanners().zip(repository.getTopArticles()) { bannerList, articleList ->
                setState {
                    copy(isLoading = false, banners = bannerList, topArticles = articleList)
                }
            }.catch { }.collect()
        }
    }
}

data class DiscoveryUiState(
    val isLoading: Boolean,
    val banners: List<BannerItem>,
    val topArticles: List<Article>,
    val errorMsg: String? = null,
    val listState: LazyListState = LazyListState()
) : IViewState

sealed class DiscoveryUiEvent : IViewEvent {
    object Refresh : DiscoveryUiEvent()
}




