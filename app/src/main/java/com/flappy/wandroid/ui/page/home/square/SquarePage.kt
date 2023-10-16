package com.flappy.wandroid.ui.page.home.square

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.flappy.wandroid.ui.widget.ArticleItem
import com.flappy.wandroid.ui.widget.PagingRefreshList
import com.flappy.wandroid.utils.RouteUtils

/**
 * @Author flappy8023
 * @Description 首页-广场
 * @Date 2023年09月18日 17:55
 **/
@Composable
fun SquarePage(navController: NavController, viewModel: SquareViewModel = hiltViewModel()) {
    val pageData = viewModel.squarePager.collectAsLazyPagingItems()
    PagingRefreshList(lazyPagingItems = pageData, onRefresh = { }) {
        items(pageData.itemCount) {
            ArticleItem(article = pageData[it]!!, onClick = {
                RouteUtils.navToArticleDetail(navController, pageData[it]!!)
            })
        }
    }

}