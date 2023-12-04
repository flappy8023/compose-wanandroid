package com.flappy.wandroid.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.flappy.wandroid.MyApplication
import com.flappy.wandroid.R
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.ui.page.AppBarState
import com.flappy.wandroid.ui.page.AppToolbar
import com.flappy.wandroid.ui.page.ScaffoldState
import com.flappy.wandroid.ui.page.home.HomePage
import com.flappy.wandroid.ui.page.homeAppBarState
import com.flappy.wandroid.ui.page.login.LoginPage
import com.flappy.wandroid.ui.page.profile.ProfilePage
import com.flappy.wandroid.ui.page.system.SystemPage
import com.flappy.wandroid.ui.page.todo.TodoPage
import com.flappy.wandroid.ui.page.web.WebItem
import com.flappy.wandroid.ui.page.web.WebViewPage
import com.flappy.wandroid.ui.page.wechat.WechatPage
import com.flappy.wandroid.ui.widget.BottomNavView
import com.flappy.wandroid.utils.fromJson

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
    val scaffoldState = remember {
        mutableStateOf(ScaffoldState(AppBarState()))
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            AppToolbar(
                navController = navController,
                scrollBehavior,
                scaffoldState.value.appBarState
            )
        },
        floatingActionButton = scaffoldState.value.floatingActionButton,
        bottomBar = {
            //仅首页展示下方导航栏
            when (currentDestination?.route) {
                RoutePath.ROUTE_HOME, RoutePath.ROUTE_SYSTEM, RoutePath.ROUTE_WECHAT, RoutePath.ROUTE_TODO -> BottomNavView(
                    navController = navController
                )
            }
        },
        snackbarHost = {

        },

    ) {
        NavGraph(navController, it, scaffoldState)
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    innerPadding: PaddingValues,
    scaffoldState: MutableState<ScaffoldState>
) {

    NavHost(
        modifier = Modifier.padding(innerPadding),
        navController = navController,
        startDestination = RoutePath.ROUTE_HOME
    ) {
        //首页
        composable(RoutePath.ROUTE_HOME) {
            scaffoldState.value = ScaffoldState(homeAppBarState(navController))
            HomePage(navController)
        }
        //体系
        composable(RoutePath.ROUTE_SYSTEM) {
            scaffoldState.value = ScaffoldState(homeAppBarState(navController))
            SystemPage(navController)
        }
        //微信公账号
        composable(RoutePath.ROUTE_WECHAT) {
            scaffoldState.value = ScaffoldState(homeAppBarState(navController))
            WechatPage(navController)
        }
        //待办
        composable(RoutePath.ROUTE_TODO) {
            TodoPage(navController) {
                scaffoldState.value = it
            }
        }
        //个人资料
        composable(RoutePath.ROUTE_PROFILE) {
            ProfilePage(navController) {
                scaffoldState.value = it
            }
        }
        //登录
        composable(RoutePath.ROUTE_LOGIN){
            scaffoldState.value= ScaffoldState(AppBarState(MyApplication.context.getString(R.string.login)))
            LoginPage(navController)
        }
        composable(
            route = RoutePath.ROUTE_H5_DETAIL + "/{webItem}",
            arguments = listOf(navArgument("webItem") { type = NavType.StringType })
        ) {
            val webItem = it.arguments?.getString("webItem")?.fromJson<WebItem>()
            if (null != webItem) {
                WebViewPage(navController = navController, webItem = webItem){state->
                    scaffoldState.value =state
                }
            }
        }
    }
}


