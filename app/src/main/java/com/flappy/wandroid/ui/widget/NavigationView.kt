package com.flappy.wandroid.ui.widget

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.flappy.wandroid.R
import com.flappy.wandroid.config.RoutePath

/**
 * @Author flappy8023
 * @Description 底部导航栏
 * @Date 2023年09月18日 15:27
 **/
@Composable
fun BottomNavView(navController: NavController) {
    val navList = listOf(
        Home, System, Wechat, TODO
    )
    NavigationBar(modifier = Modifier.navigationBarsPadding()) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val curDestination = navBackStackEntry?.destination
        navList.forEach { navItem ->
            NavigationBarItem(
                selected = curDestination?.hierarchy?.any { it.route == navItem.route } == true,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painter = painterResource(id = navItem.icon),
                        contentDescription = stringResource(
                            id = navItem.title
                        )
                    )
                },
                label = { Text(text = stringResource(id = navItem.title)) })
        }
    }
}

sealed class BottomNavItem(val title: Int, val icon: Int, val route: String)
object Home : BottomNavItem(R.string.nav_home, R.drawable.ic_round_home_24, RoutePath.ROUTE_HOME)
object System :
    BottomNavItem(R.string.nav_system, R.drawable.ic_round_account_tree_24, RoutePath.ROUTE_SYSTEM)

object Wechat :
    BottomNavItem(R.string.nav_wechat, R.drawable.ic_wechat_account_24, RoutePath.ROUTE_WECHAT)

object TODO : BottomNavItem(R.string.nav_todo, R.drawable.icon_todo_24, RoutePath.ROUTE_TODO)
