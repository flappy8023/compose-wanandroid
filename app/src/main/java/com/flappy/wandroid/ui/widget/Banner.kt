package com.flappy.wandroid.ui.widget

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

/**
 * Banner
 *
 * @param modifier
 * @param pageCount 轮播图内容数量
 * @param switchInternal 切换间隔，单位毫秒
 * @param bannerItems
 * @param onClick
 * @receiver
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    pageCount: Int,
    switchInternal: Long = 3000L,
    bannerItems: List<BannerItem>,
    onClick: ((BannerItem) -> Unit)? = null
) {
    Box(modifier = modifier) {
        val pageState = rememberPagerState(
            initialPage = 0
        )
        val executeChangePage by remember {
            mutableStateOf(false)
        }
        LaunchedEffect(key1 = pageState.currentPage, key2 = executeChangePage) {
            if (pageCount > 0) {
                //延时切换页面
                delay(switchInternal)
                pageState.animateScrollToPage((pageState.currentPage + 1) % pageCount)
            }
        }
        HorizontalPager(pageCount = pageCount, state = pageState, modifier = Modifier.clickable {
            onClick?.invoke(bannerItems[pageState.currentPage])
        }) { page ->
            AsyncImage(model = bannerItems[page].imgUrl, contentDescription = null)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bannerItems.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (index == pageState.currentPage) Color.Black else Color.Gray)
                            .padding(end = if (index == bannerItems.lastIndex) 0.dp else 10.dp)
                            .animateContentSize()
                            .size(if (index == pageState.currentPage) 8.dp else 5.dp)
                    )
                }
            }
        }
    }
}

/**
 * Banner item
 *
 * @property imgUrl
 * @property title
 * @property contentUrl
 * @constructor Create empty Banner item
 */
data class BannerItem(val imgUrl: String, val title: String, val contentUrl: String)