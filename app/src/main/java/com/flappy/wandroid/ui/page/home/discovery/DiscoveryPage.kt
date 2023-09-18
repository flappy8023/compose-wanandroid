package com.flappy.wandroid.ui.page.home.discovery

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.flappy.wandroid.ui.widget.Banner

/**
 * @Author flappy8023
 * @Description 首页-发现
 * @Date 2023年09月18日 17:53
 **/

@Composable
fun DiscoveryPage(viewModel: DiscoveryVM = hiltViewModel()) {
    val viewState = viewModel.viewState
    Column {
        Banner(pageCount = viewState.bannerList.size, bannerItems = viewState.bannerList)
    }
}