package com.flappy.wandroid.ui.page.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.flappy.wandroid.R
import com.flappy.wandroid.ui.page.home.discovery.DiscoveryPage
import com.flappy.wandroid.ui.page.home.qa.QAPage
import com.flappy.wandroid.ui.page.home.square.SquarePage
import com.flappy.wandroid.ui.widget.HomeAppToolbar
import com.flappy.wandroid.ui.widget.TabLayoutBar
import kotlinx.coroutines.launch

/**
 * @Author flappy8023
 * @Description 首页
 * @Date 2023年09月18日 16:03
 **/
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePage(navController: NavController) {
    Column {
        val titles = listOf(
            stringResource(R.string.tab_discovery), stringResource(R.string.tab_qa),
            stringResource(R.string.tab_square)
        )
        val coroutineScope = rememberCoroutineScope()
        val pageState = rememberPagerState(initialPage = 0) { titles.size }
        HomeAppToolbar(title = stringResource(id = R.string.nav_home))
        TabLayoutBar(titles = titles, checkedPosition = pageState.currentPage) {
            coroutineScope.launch {
                pageState.scrollToPage(it)
            }
        }
        HorizontalPager(
            state = pageState,
        ) {
            when (it) {
                //发现
                0 -> DiscoveryPage(navController)
                //问答
                1 -> QAPage(navController)
                //广场
                2 -> SquarePage(navController)
            }
        }

    }
}