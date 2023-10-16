package com.flappy.wandroid.ui.page.system

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.flappy.wandroid.ui.widget.RefreshList
import com.flappy.wandroid.utils.showToast
import kotlinx.coroutines.flow.onEach

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年09月18日 16:04
 **/
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SystemPage(navController: NavController, viewModel: SystemViewModel = hiltViewModel()) {
    val viewState = viewModel.viewState.value
    val context = LocalContext.current
    Column {
        LaunchedEffect(key1 = null) {
            viewModel.effect.onEach {
                when (it) {
                    is SystemContract.Effect.GoDetail->{

                    }
                    is SystemContract.Effect.ToastMsg->{
                        context.showToast(it.msg)
                    }
                }
            }
        }
        RefreshList(
            modifier = Modifier.padding(PaddingValues(10.dp)),
            isRefresh = viewState.isLoading,
            isLoading = viewState.isLoading,
            isError = viewState.isError,
            onRefresh = { viewModel.sendEvent(SystemContract.Event.Retry) }
        ) {
            itemsIndexed(viewState.data) { position, system ->
                //标题
                Text(
                    text = system.name,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.onPrimary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
                //下方流式布局
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    system.children.forEachIndexed { index, treeItem ->
                        SuggestionChip(onClick = {
                            viewModel.sendEvent(SystemContract.Event.ItemClick(treeItem))
                        }, label = { Text(text = treeItem.name) })
                    }
                }
            }
        }
    }

}