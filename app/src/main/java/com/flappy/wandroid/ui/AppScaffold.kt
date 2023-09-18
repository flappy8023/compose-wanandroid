package com.flappy.wandroid.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.ui.page.home.HomePage
import com.flappy.wandroid.ui.page.system.SystemPage
import com.flappy.wandroid.ui.page.todo.TodoPage
import com.flappy.wandroid.ui.page.wechat.WechatPage
import com.flappy.wandroid.ui.widget.BottomNavView

/**
 * @Author flappy8023
 * @Description //TODO
 * @Date 2023年09月15日 16:54
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding(),
        bottomBar = { BottomNavView(navController = navController) },
    ) {
        NavGraph(navController, it)
    }
}

@Composable
fun NavGraph(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = RoutePath.ROUTE_HOME
    ) {
        //首页
        composable(RoutePath.ROUTE_HOME) {
            HomePage(navController)
        }
        //体系
        composable(RoutePath.ROUTE_SYSTEM) {
            SystemPage(navController)
        }
        //微信公账号
        composable(RoutePath.ROUTE_WECHAT) {
            WechatPage(navController)
        }
        //待办
        composable(RoutePath.ROUTE_TODO) {
            TodoPage(navController)
        }
        //个人资料
        composable(RoutePath.ROUTE_PROFILE) {

        }
    }
}

