package com.flappy.wandroid.data.repository

import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.data.paging.BasePagingSource
import com.flappy.wandroid.ui.widget.BannerItem
import com.flappy.wandroid.utils.safeCall
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description 首页数据源
 * @Date 2023年09月25日 10:21
 **/
class HomeRepository @Inject constructor(val api: ApiService) : BaseRepository() {
    fun discoveryPageSource() =
        BasePagingSource { api.getHomeArticleList(it) }

    fun qaPageSource() = BasePagingSource {
        api.getQAList(it)
    }

    fun squarePageSource() = BasePagingSource {
        api.getSquareArticleList(it)
    }

    fun getBanners() = flow {
        emit(safeCall { api.getBanners() })
    }.map {
        if (it is ApiResult.Success) {
            it.data.map { bannerItem ->
                BannerItem(bannerItem.imagePath, bannerItem.title, bannerItem.url)
            }
        } else emptyList()

    }

    fun getTopArticles() = flow {
        emit(safeCall { api.getTopArticles() })
    }.map {
        if (it is ApiResult.Success) {
            it.data
        } else emptyList()
    }

}