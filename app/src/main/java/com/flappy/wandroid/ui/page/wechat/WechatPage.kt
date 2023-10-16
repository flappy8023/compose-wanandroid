package com.flappy.wandroid.ui.page.wechat

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.flappy.wandroid.data.bean.WechatAccount
import com.flappy.wandroid.ui.widget.ErrorPage
import com.flappy.wandroid.ui.widget.LoadingView
import kotlinx.coroutines.launch

/**
 * @Author flappy8023
 * @Description 微信公账号页签
 * @Date 2023年09月18日 16:05
 **/
@Composable
fun WechatPage(navController: NavController, viewModel: WechatViewModel = hiltViewModel()) {
    val viewState = viewModel.viewState.value
    Column {
        if (viewState.isLoading) {
            LoadingView()
        } else if (viewState.isError) {
            ErrorPage()
        } else {
            WechatAccount(navController = navController, tabs = viewState.tabs)
        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WechatAccount(navController: NavController, tabs: List<WechatAccount>) {
    val pagerState = rememberPagerState(initialPage = 0) {
        tabs.size
    }
    val scope = rememberCoroutineScope()
    Column {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            divider = {
                Spacer(modifier = Modifier.height(5.dp))
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            tabs.forEachIndexed { index, wechatAccount ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.scrollToPage(index)
                        }
                    }) {
                    Text(
                        text = wechatAccount.name,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }

        }
        HorizontalPager(state = pagerState) {
            WechatArticleList(navController = navController, wechatAccount = tabs[it])
        }
    }

}



