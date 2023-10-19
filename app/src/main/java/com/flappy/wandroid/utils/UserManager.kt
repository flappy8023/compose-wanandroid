package com.flappy.wandroid.utils

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.preferences.core.edit
import com.flappy.wandroid.MyApplication
import com.flappy.wandroid.config.GlobalConfig
import com.flappy.wandroid.data.bean.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月16日 11:21
 **/
object UserManager : CoroutineScope by MainScope() {
    private val _userState = mutableStateOf(UserState(false, null))
    val userState: State<UserState> = _userState

    fun init(){

    }
    fun setLoginSuccess(userInfo: UserInfo) {
        _userState.value = UserState(isLogin = true, userInfo = userInfo)
        launch {
            MyApplication.context.dataStore.edit {
                it[GlobalConfig.KEY_LOGIN] = true
            }
        }
    }
    
}

data class UserState(val isLogin: Boolean, val userInfo: UserInfo?)
