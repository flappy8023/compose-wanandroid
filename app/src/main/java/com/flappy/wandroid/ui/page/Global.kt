package com.flappy.wandroid.ui.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.flappy.wandroid.MyApplication
import com.flappy.wandroid.R
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.utils.RouteUtils
import com.flappy.wandroid.utils.UserManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolbar(
    navController: NavController,
    scrollBehavior: TopAppBarScrollBehavior,
    appBarState: AppBarState
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    //区分是否是一级页面
    val isHome = when (currentDestination?.route) {
        RoutePath.ROUTE_HOME, RoutePath.ROUTE_SYSTEM, RoutePath.ROUTE_WECHAT, RoutePath.ROUTE_TODO, null -> true

        else -> false
    }
    CenterAlignedTopAppBar(title = { Text(text = appBarState.title) }, navigationIcon = {
        Image(
            painter = if (isHome) painterResource(id = R.drawable.ic_round_search_24) else painterResource(
                id = R.drawable.round_arrow_back_24
            ),
            contentDescription = null,
            modifier = Modifier.clickable {
                if (isHome) {
                } else {
                    navController.navigateUp()
                }
            }
        )
    }, scrollBehavior = scrollBehavior, actions = {
        appBarState.actions?.invoke(this, navController)
    })
}


/**
 * 统一管理TopBar、SnackBar、floatingActionButton
 *
 * @property appBarState
 * @property floatingActionButton
 * @property snackBarHostState
 * @constructor Create empty Scaffold state
 */
data class ScaffoldState(
    val appBarState: AppBarState,
    val floatingActionButton: @Composable () -> Unit = {},
    val snackBarHostState: SnackbarHostState? = null
)

data class AppBarState(
    val title: String = MyApplication.context.getString(R.string.app_name),
    val actions: @Composable (RowScope.(NavController) -> Unit)? = null
)

fun homeAppBarState(navController: NavController) = AppBarState {
    val userState by remember {
        UserManager.userState
    }
    if (userState != null) {
        //已登录显示用户名第一个字符
        Text(
            text = "玩",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .size(40.dp)
                .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                .wrapContentHeight(align = Alignment.CenterVertically)
                .clickable {
                    RouteUtils.navTo(navController, RoutePath.ROUTE_PROFILE)
                },

            textAlign = TextAlign.Center
        )
    } else {
        //未登录展示默认头像
        Icon(
            painter = painterResource(id = R.drawable.icon_user_default),
            contentDescription = "",
            modifier = Modifier
                .width(32.dp)
                .height(32.dp)
                .clickable {
                    RouteUtils.navTo(navController, RoutePath.ROUTE_PROFILE)
                }
        )
    }
}

