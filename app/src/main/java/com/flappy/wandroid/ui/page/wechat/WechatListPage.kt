package com.flappy.wandroid.ui.page.wechat

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.flappy.wandroid.data.bean.WechatAccount
import com.flappy.wandroid.di.ViewModelFactoryProvider
import com.flappy.wandroid.ui.widget.ArticleItem
import com.flappy.wandroid.ui.widget.PagingRefreshList
import com.flappy.wandroid.utils.RouteUtils
import dagger.hilt.android.EntryPointAccessors

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月12日 10:15
 **/
@Composable
fun wechatListViewModel(id: Long): WechatListViewModel {
    val factory = EntryPointAccessors.fromActivity(
        LocalContext.current as Activity,
        ViewModelFactoryProvider::class.java
    ).wechatListViewModel()
    return viewModel(factory = WechatListViewModel.provideFactory(factory, id))
}

@Composable
fun WechatArticleList(
    navController: NavController,
    wechatAccount: WechatAccount,
    viewModel: WechatListViewModel = wechatListViewModel(id = wechatAccount.id)
) {
    val pageData = viewModel.pager.collectAsLazyPagingItems()
    PagingRefreshList(lazyPagingItems = pageData) {
        items(pageData.itemCount, key = { index -> pageData[index]!!.id }) { index ->
            ArticleItem(
                article = pageData[index]!!,
                onClick = {
                    RouteUtils.navToArticleDetail(navController, pageData[index]!!)
                }, onLongPress = {

                }
            )
        }
    }
}

