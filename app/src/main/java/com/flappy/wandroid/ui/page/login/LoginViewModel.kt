package com.flappy.wandroid.ui.page.login

import com.flappy.wandroid.MyApplication
import com.flappy.wandroid.R
import com.flappy.wandroid.base.BaseViewModel
import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.repository.UserRepository
import com.flappy.wandroid.utils.UserManager
import com.flappy.wandroid.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月16日 9:49
 **/
@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: UserRepository) :
    BaseViewModel<LoginContract.State, LoginContract.Event, LoginContract.Effect>() {
    override fun getInitialState() = LoginContract.State(loggingIn = false, loginSuccess = false)

    override fun handleEvents(event: LoginContract.Event) {
        when (event) {
            is LoginContract.Event.GoLogin -> login(event.userName, event.pwd)
        }
    }

    private fun login(userName: String?, pwd: String?) {
        if (userName.isNullOrEmpty()) {
            setEffect { LoginContract.Effect.ShowToast(MyApplication.context.getString(R.string.username_not_be_null)) }
            return
        }
        if (pwd.isNullOrEmpty()) {
            setEffect { LoginContract.Effect.ShowToast(MyApplication.context.getString(R.string.pwd_not_be_null)) }
            return
        }
        setState { copy(loggingIn = true) }
        launch {
            when (val result = repository.login(userName, pwd)) {
                is ApiResult.Success -> {
                    setState {
                        copy(loginSuccess = true)
                    }
                    setEffect { LoginContract.Effect.NavBack }
                    UserManager.setLoginSuccess(result.data)
                }

                is ApiResult.Failure -> {
                    setState {
                        copy(loggingIn = false)
                    }
                    setEffect { LoginContract.Effect.ShowToast(result.exception.errMsg) }
                }
            }
        }

    }
}