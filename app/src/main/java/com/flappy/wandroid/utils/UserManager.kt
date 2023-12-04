package com.flappy.wandroid.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.flappy.wandroid.data.bean.UserInfo
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月16日 11:21
 **/
object UserManager : CoroutineScope by MainScope() {
    private val _userState = mutableStateOf(getCacheUserInfo())
    val userState: State<UserInfo?> = _userState

    private fun getCacheUserInfo(): UserInfo? {
        val cache = DataStoreUtil.getData(KeyConst.KEY_USERINFO, "")
        return cache.fromJson()
    }

    fun loginSuccess(userInfo: UserInfo) {
        _userState.value = userInfo
        launch {
            DataStoreUtil.putData(KeyConst.KEY_USERINFO, Gson().toJson(userInfo))
        }
    }

    fun logout() {
        launch {
            DataStoreUtil.putData(KeyConst.KEY_USERINFO, "")
        }
    }
}

