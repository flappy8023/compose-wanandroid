package com.flappy.wandroid.ui.page.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.flappy.wandroid.ui.page.AppBarState
import com.flappy.wandroid.ui.page.ScaffoldState

/**
 * @Author flappy8023
 * @Description 个人资料页
 * @Date 2023年09月18日 16:09
 **/
@Composable
fun ProfilePage(navController: NavController, onCompose: (ScaffoldState) -> Unit) {
    LaunchedEffect(key1 = true, block = {
        onCompose(
            ScaffoldState(
                AppBarState("我的")
            )
        )
    })

}