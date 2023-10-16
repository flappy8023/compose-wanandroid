package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.flappy.wandroid.R

@Composable
fun <T : Any> PagingRefreshList(
    lazyPagingItems: LazyPagingItems<T>,
    isRefresh: Boolean = false,
    onRefresh: () -> Unit = {},
    lazyListState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit = {}
) {

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefresh,
        onRefresh = {
            onRefresh()
            lazyPagingItems.refresh()
        }
    )
    val loadingState = lazyPagingItems.loadState.refresh
    //加载出错页面
    if (loadingState is LoadState.Error) {
        ErrorPage {
            lazyPagingItems.refresh()
        }

    } else {
        Box(modifier = Modifier.pullRefresh(pullRefreshState)) {

            LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
                content()
                //
                item {
                    if (!isRefresh) {
                        when (lazyPagingItems.loadState.append) {
                            is LoadState.Loading -> FooterLoading()
                            is LoadState.Error -> FooterError { lazyPagingItems.retry() }
                            is LoadState.NotLoading -> {
                                //需要区分是否是分页结束
                                if (lazyPagingItems.loadState.append.endOfPaginationReached) {
                                    FooterNoMore()
                                }
                            }
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefresh,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

@Composable
fun RefreshList(
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    isRefresh: Boolean = false,
    isError: Boolean = false,
    isLoading: Boolean = false,
    onRefresh: () -> Unit = {},
    content: LazyListScope.() -> Unit = {}
) {
    val pullRefreshState =
        rememberPullRefreshState(refreshing = isRefresh, onRefresh = { onRefresh() })
    if (isError) {
        ErrorPage { onRefresh() }
    } else if (isLoading) {
        LoadingView()
    } else {
        Box(modifier = modifier.pullRefresh(pullRefreshState)) {

            LazyColumn(modifier = Modifier.fillMaxSize(), state = lazyListState) {
                //增加顶部刷新加载框
                item {
                    if (isRefresh) {
                        FooterLoading()
                    }
                }
                content()
            }
            PullRefreshIndicator(
                refreshing = isRefresh,
                state = pullRefreshState,
                Modifier.align(Alignment.TopCenter)
            )
        }
    }
}


@Preview
@Composable
fun FooterLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .padding(8.dp)
                .size(48.dp)
        )
    }
}


@Composable
fun FooterError(retry: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        Button(onClick = { retry() }, modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.list_content_retry),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun FooterNoMore() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = R.string.list_content_no_more),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelMedium
        )
    }
}

