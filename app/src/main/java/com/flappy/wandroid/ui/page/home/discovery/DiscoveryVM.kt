package com.flappy.wandroid.ui.page.home.discovery

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.ui.widget.BannerItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description 发现页viewModel
 * @Date 2023年09月20日 16:40
 **/
@HiltViewModel
class DiscoveryVM @Inject constructor(private val apiService: ApiService) : ViewModel() {

    var viewState by mutableStateOf(DiscoveryUiState(true, emptyList()))
        private set

    init {
        fetchBanners()
    }

    private fun fetchBanners() {
        viewModelScope.launch {
            flow {
                emit(apiService.getBanners())
            }.map {
                it.data?.let {
                    it.map { item -> BannerItem(item.imagePath, item.title, item.url) }
                } ?: emptyList()
            }.onEach {
                viewState = viewState.copy(loading = false, bannerList = it)
            }.catch {
                viewState = viewState.copy(loading = false)
            }.collect()
        }
    }

}

data class DiscoveryUiState(
    val loading: Boolean = true,
    val bannerList: List<BannerItem> = emptyList()
)