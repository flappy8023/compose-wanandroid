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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.flappy.wandroid.R
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.utils.RouteUtils
import kotlinx.coroutines.flow.collectLatest

/**
 * @Author flappy8023
 * @Description 待办事项页签
 * @Date 2023年09月18日 16:06
 **/
@Composable
fun TodoPage(navController: NavController, viewModel: TodoViewModel = hiltViewModel()) {
    var isLogin = remember {
        mutableStateOf(false)
    }
    val state = viewModel.viewState.value
    LaunchedEffect(key1 = null, block = {
        viewModel.effect.collectLatest {
            when (it) {
                TodoContract.Effect.Login -> RouteUtils.navTo(navController, RoutePath.ROUTE_LOGIN)
                is TodoContract.Effect.GoDetail -> {}
            }
        }
    })
    if (isLogin.value) {
        TodoContent()
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
fun TodoContent() {
    Text(text = "hello")
}