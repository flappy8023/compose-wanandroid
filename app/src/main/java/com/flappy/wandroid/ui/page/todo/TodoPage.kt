package com.flappy.wandroid.ui.page.todo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.flappy.wandroid.R
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.data.bean.Todo
import com.flappy.wandroid.ui.widget.PagingRefreshList
import com.flappy.wandroid.utils.RouteUtils
import com.flappy.wandroid.utils.UserManager
import kotlinx.coroutines.flow.collectLatest

/**
 * @Author flappy8023
 * @Description 待办事项页签
 * @Date 2023年09月18日 16:06
 **/
@Composable
fun TodoPage(navController: NavController, viewModel: TodoViewModel = hiltViewModel()) {
    var userState = UserManager.userState.value
    LaunchedEffect(key1 = null, block = {
        viewModel.effect.collectLatest {
            when (it) {
                TodoContract.Effect.Login -> RouteUtils.navTo(navController, RoutePath.ROUTE_LOGIN)
                is TodoContract.Effect.GoDetail -> {}
            }
        }
    })
    if (userState.isLogin) {
        TodoContent(viewModel)
    } else {
        NotLogin(viewModel = viewModel)
    }


}

@Composable
fun NotLogin(viewModel: TodoViewModel) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_unlogin),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        Text(
            text = stringResource(id = R.string.need_login),
            modifier = Modifier.padding(top = 20.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { viewModel.sendEvent(TodoContract.Event.GoLogin) }) {
            Text(text = stringResource(id = R.string.go_login))
        }
    }
}

@Composable
fun TodoContent(viewModel: TodoViewModel) {
    val state = viewModel.viewState.value
    val lazyPagingItems = viewModel.todoPager().collectAsLazyPagingItems()
    PagingRefreshList(lazyPagingItems = lazyPagingItems) {
        items(lazyPagingItems.itemCount, key = { lazyPagingItems[it]!!.id }) {
            TodoItem(todo = lazyPagingItems[it]!!)
        }
    }
}

@Composable
fun TodoItem(todo: Todo) {
    ConstraintLayout {
        val (check, date, title, content) = createRefs()
        Checkbox(
            checked = todo.status == 1,
            onCheckedChange = {},
            modifier = Modifier.constrainAs(check) {
                start.linkTo(parent.start)
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
            })
        Text(
            text = todo.dateStr, modifier = Modifier.constrainAs(date) {
                start.linkTo(check.end, margin = 16.dp)
                top.linkTo(parent.top, margin = 4.dp)
            }, style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = todo.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.constrainAs(title) {
                start.linkTo(date.start)
                top.linkTo(date.bottom, margin = 4.dp)
            })
        Text(
            text = todo.content,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.constrainAs(content) {
                start.linkTo(title.start)
                top.linkTo(title.bottom, margin = 4.dp)
                end.linkTo(parent.end)
                bottom.linkTo(parent.bottom, margin = 4.dp)
            })
    }
}