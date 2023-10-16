package com.flappy.wandroid.ui.page.home.qa

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.flappy.wandroid.ui.widget.ArticleItem
import com.flappy.wandroid.ui.widget.PagingRefreshList
import com.flappy.wandroid.utils.RouteUtils

/**
 * @Author flappy8023
 * @Description 首页-问答
 * @Date 2023年09月18日 17:54
 **/

@Composable
fun QAPage(navController: NavController, viewModel: QAViewModel = hiltViewModel()) {
    val qaData = viewModel.pagingData.collectAsLazyPagingItems()
    PagingRefreshList(lazyPagingItems = qaData) {
        items(qaData.itemCount, key = { index -> qaData[index]!!.id }) { index ->
            ArticleItem(
                article = qaData[index]!!,
                onClick = {
                    RouteUtils.navToArticleDetail(navController, qaData[index]!!)
                }, onLongPress = {

                }
            )
        }
    }

}