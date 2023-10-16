package com.flappy.wandroid.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.ui.page.home.HomePage
import com.flappy.wandroid.ui.page.login.LoginPage
import com.flappy.wandroid.ui.page.system.SystemPage
import com.flappy.wandroid.ui.page.todo.TodoPage
import com.flappy.wandroid.ui.page.web.WebItem
import com.flappy.wandroid.ui.page.web.WebViewPage
import com.flappy.wandroid.ui.page.wechat.WechatPage
import com.flappy.wandroid.ui.widget.AppToolbar
import com.flappy.wandroid.ui.widget.BottomNavView
import com.flappy.wandroid.utils.fromJson
import okhttp3.Route

/**
 * @Author flappy8023
 * @Description //TODO
 * @Date 2023年09月15日 16:54
 **/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { AppToolbar(navController = navController, title = "玩安卓", scrollBehavior) },
        bottomBar = {
            //仅首页展示下方导航栏
            when (currentDestination?.route) {
                RoutePath.ROUTE_HOME, RoutePath.ROUTE_SYSTEM, RoutePath.ROUTE_WECHAT, RoutePath.ROUTE_TODO -> BottomNavView(
                    navController = navController
                )
            }
        },

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
        //登录
        composable(RoutePath.ROUTE_LOGIN){
            LoginPage(navController)
        }
        composable(
            route = RoutePath.ROUTE_H5_DETAIL + "/{webItem}",
            arguments = listOf(navArgument("webItem") { type = NavType.StringType })
        ) {
            val webItem = it.arguments?.getString("webItem")?.fromJson<WebItem>()
            if (null != webItem) {
                WebViewPage(navController = navController, webItem = webItem)
            }
        }
    }
}

