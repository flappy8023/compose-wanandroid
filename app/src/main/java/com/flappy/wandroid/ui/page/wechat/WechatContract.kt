package com.flappy.wandroid.ui.page.wechat

import com.flappy.wandroid.base.IViewEvent
import com.flappy.wandroid.base.IViewSideEffect
import com.flappy.wandroid.base.IViewState
import com.flappy.wandroid.data.bean.WechatAccount

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月12日 10:07
 **/
class WechatContract {
    data class State(val isLoading: Boolean, val isError: Boolean, val tabs: List<WechatAccount>) :
        IViewState

    sealed class Event : IViewEvent {
        object Retry : Event()
    }

    sealed class Effect:IViewSideEffect {
        data class ToastMsg(val msg: String) : Effect()
    }

}