package com.flappy.wandroid.ui.page.home.discovery

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.data.bean.Article
import com.flappy.wandroid.data.pading.ArticlePagingSource
import com.flappy.wandroid.ui.widget.BannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description 发现页viewModel
 * @Date 2023年09月20日 16:40
 **/
@HiltViewModel
class DiscoveryVM @Inject constructor(private val apiService: ApiService) : ViewModel() {
    private val pager = Pager(
        config = PagingConfig(30),
        pagingSourceFactory = { ArticlePagingSource() }).flow.cachedIn(viewModelScope)
    var viewState by mutableStateOf(DiscoveryUIState(pagingData = pager))
        private set

    init {
        dispatchAction(DiscoveryUIAction.Fetch)
    }


    fun dispatchAction(action: DiscoveryUIAction) {
        when (action) {
            is DiscoveryUIAction.Refresh, DiscoveryUIAction.Fetch -> {
                fetchData()
            }
        }
    }

    private fun fetchData() {
        val banners = flow {
            emit(apiService.getBanners())
        }.map { response ->
            response.data?.let {
                it.map { item -> BannerItem(item.imagePath, item.title, item.url) }
            } ?: emptyList()
        }
        val pinedArticles = flow {
            emit(apiService.getTopArticles())
        }.map {
            it.data ?: emptyList()
        }
        viewModelScope.launch {
            //zip同时请求轮播图和置顶文章，统一回调
            banners.zip(pinedArticles) { bannerList, articleList ->
                viewState = viewState.copy(
                    loading = false,
                    bannerList = bannerList,
                    topArticles = articleList
                )
            }.onStart {
                viewState = viewState.copy(loading = true)
            }.catch {
                viewState = viewState.copy(loading = false)
            }.collect()
        }
    }
}

data class DiscoveryUIState(
    val loading: Boolean = true,
    val bannerList: List<BannerItem> = emptyList(),
    val topArticles: List<Article> = emptyList(),
    val pagingData: Flow<PagingData<Article>>,
    val listState: LazyListState = LazyListState()
)

sealed class DiscoveryUIAction {
    object Refresh : DiscoveryUIAction()
    object Fetch : DiscoveryUIAction()
}
