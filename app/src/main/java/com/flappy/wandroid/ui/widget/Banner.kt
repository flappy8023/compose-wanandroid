package com.flappy.wandroid.ui.widget

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.changedToDownIgnoreConsumed
import androidx.compose.ui.input.pointer.changedToUpIgnoreConsumed
import androidx.compose.ui.input.pointer.pointerInput
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
    val TAG = "Banner"
    Box(modifier = modifier) {
        val pageState = rememberPagerState(initialPage = 0) {
            pageCount
        }
        var executeChangePage by remember {
            mutableStateOf(false)
        }
        var curIndex = 0
        //pageCount发生变化（数据被填充）或者手动拖动了banner重新触发自动切换
        LaunchedEffect(key1 = pageCount, key2 = executeChangePage) {
            while (true) {
                Log.d(TAG, "launchedEffect pageCount = $pageCount")
                //延时切换页面
                delay(switchInternal)
                val nextPage = (pageState.currentPage + 1) % pageState.pageCount
                pageState.animateScrollToPage(nextPage)
                Log.d(
                    TAG, "Banner: after $switchInternal, begin animateScrollTo page $nextPage"
                )
            }
        }
        HorizontalPager(state = pageState, modifier = Modifier
            .clickable {
                onClick?.invoke(bannerItems[pageState.currentPage])
            }
            .fillMaxWidth()
            //宽高比限制为9/5，与图片实际比例保持一致
            .aspectRatio(9 / 5f, false)
            .pointerInput(pageState.currentPage) {
                awaitPointerEventScope {
                    while (true) {
                        //优先拦截事件
                        val event = awaitPointerEvent(PointerEventPass.Initial)
                        val dragEvent = event.changes.firstOrNull()

                        when {
                            dragEvent!!.isConsumed -> return@awaitPointerEventScope
                            dragEvent.changedToDownIgnoreConsumed() -> curIndex =
                                pageState.currentPage

                            dragEvent.changedToUpIgnoreConsumed() -> {
                                if (curIndex == pageState.currentPage && pageState.pageCount > 1) {
                                    executeChangePage = !executeChangePage
                                }
                            }
                        }

                    }
                }
            }) { page ->
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = bannerItems[page].imgUrl,
                contentDescription = null,
            )
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(start = 10.dp, end = 10.dp, bottom = 14.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                bannerItems.forEachIndexed { index, _ ->
                    //指示器中间添加间隔
                    if (index > 0) {
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(if (index == pageState.currentPage) Color.Black else Color.Gray)
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