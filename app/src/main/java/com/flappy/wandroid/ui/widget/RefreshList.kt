package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.flappy.wandroid.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T : Any> RefreshList(
    lazyPagingItems: LazyPagingItems<T>,
    isRefresh: Boolean = false,
    onRefresh: () -> Unit,
    lazyListState: LazyListState = rememberLazyListState(),
    content: LazyListScope.() -> Unit
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


@Preview
@Composable
fun ErrorPage(retry: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.error_24),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Text(
                text = stringResource(id = R.string.list_content_error),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 20.dp)
            )
            OutlinedButton(
                onClick = { retry() },
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 0.dp),
                modifier = Modifier.padding(top = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.list_content_retry),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary

                )
            }
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

