package com.flappy.wandroid.ui.page.home.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.ui.page.web.WebItem
import com.flappy.wandroid.ui.widget.ArticleItem
import com.flappy.wandroid.ui.widget.Banner
import com.flappy.wandroid.ui.widget.RefreshList
import com.flappy.wandroid.utils.RouteUtils

/**
 * @Author flappy8023
 * @Description 首页-发现
 * @Date 2023年09月18日 17:53
 **/

@Composable
fun DiscoveryPage(navController: NavController,viewModel: DiscoveryVM = hiltViewModel()) {
    val viewState = viewModel.viewState
    val discoveryData = viewModel.pager.collectAsLazyPagingItems()
    val pinedArticles = viewState.topArticles
    val bannerList = viewState.banners
    val listSate = viewState.listState
    val isRefresh = viewState.isLoading
    Column {
        RefreshList(
            isRefresh = isRefresh,
            lazyPagingItems = discoveryData,
            lazyListState = listSate,
            onRefresh = { viewModel.sendEvent(DiscoveryUiEvent.Refresh) }) {
            if (bannerList.isNotEmpty()) {
                item {
                    Banner(
                        pageCount = bannerList.size,
                        bannerItems = bannerList
                    ) {
                        RouteUtils.navTo(
                            navController,
                            RoutePath.ROUTE_H5_DETAIL,
                            WebItem(it.title, it.contentUrl)
                        )
                    }
                }
            }
            items(pinedArticles) {
                ArticleItem(article = it, pinToTop = true, onClick = {
                    RouteUtils.navToArticleDetail(navController, it)
                })
            }
            items(discoveryData.itemCount) { index ->
                ArticleItem(
                    article = discoveryData[index]!!,
                    onClick = {
                        RouteUtils.navToArticleDetail(navController, discoveryData[index]!!)
                    }, onLongPress = {

                    }
                )
            }


        }
    }
}