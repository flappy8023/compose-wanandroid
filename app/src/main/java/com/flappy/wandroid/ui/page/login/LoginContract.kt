package com.flappy.wandroid.ui.page.login

import com.flappy.wandroid.base.IViewEvent
import com.flappy.wandroid.base.IViewSideEffect
import com.flappy.wandroid.base.IViewState

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月16日 9:38
 **/
class LoginContract {
    data class State(val loggingIn: Boolean, val loginSuccess: Boolean) : IViewState
    sealed class Event : IViewEvent {
        data class GoLogin(val userName: String?, val pwd: String?) : Event()
    }

    sealed class Effect : IViewSideEffect {
        data class ShowToast(val msg: String) : Effect()
        object NavBack:Effect()
    }
}