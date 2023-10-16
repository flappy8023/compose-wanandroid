package com.flappy.wandroid.ui.page.system

import com.flappy.wandroid.base.IViewEvent
import com.flappy.wandroid.base.IViewSideEffect
import com.flappy.wandroid.base.IViewState
import com.flappy.wandroid.data.bean.Article
import com.flappy.wandroid.data.bean.TreeItem

/**
 * @Author flappy8023
 * @Description 契约类，统一存放页面状态、一次性事件、用户动作的定义
 * @Date 2023年10月10日 10:48
 **/
class SystemContract {
    sealed class Event : IViewEvent {
        object Retry : Event()
        data class ItemClick(val tree: TreeItem) : Event()
    }

    data class State(val isLoading: Boolean, val isError: Boolean, val data: List<TreeItem>) :
        IViewState

    sealed class Effect : IViewSideEffect {
        data class GoDetail(val article: Article) : Effect()
        data class ToastMsg(val msg: String):Effect()
    }

}