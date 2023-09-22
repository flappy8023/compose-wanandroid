package com.flappy.wandroid.utils

import android.net.Uri
import androidx.navigation.NavController
import com.flappy.wandroid.config.RoutePath
import com.flappy.wandroid.data.bean.Article
import com.google.gson.Gson

/**
 * @Author flappy8023
 * @Description 路由工具
 * @Date 2023年09月22日 17:24
 **/
object RouteUtils {

    fun navTo(
        navController: NavController,
        destination: String,
        args: Any? = null,
        backStackRouteName: String? = null,
        singleTop: Boolean = true,
        needRestoreState: Boolean = true
    ) {
        var argument = ""
        when (args) {
            is Int, String, Float, Double, Boolean, Long -> {
                argument = "/$args"
            }

            else -> argument = "/${Uri.encode(Gson().toJson(args))}"
        }
        navController.navigate(destination + argument) {
            if (!backStackRouteName.isNullOrEmpty()) {
                popUpTo(backStackRouteName)
            }
            launchSingleTop = singleTop
            restoreState = needRestoreState
        }
    }


    fun navToArticleDetail(navController: NavController, article: Article) {
        navTo(navController, RoutePath.ROUTE_H5_DETAIL, article)
    }
}