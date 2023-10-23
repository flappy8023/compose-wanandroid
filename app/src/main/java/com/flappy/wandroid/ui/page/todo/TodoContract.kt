package com.flappy.wandroid.ui.page.todo

import com.flappy.wandroid.base.IViewEvent
import com.flappy.wandroid.base.IViewSideEffect
import com.flappy.wandroid.base.IViewState
import com.flappy.wandroid.data.bean.Todo

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月13日 10:07
 **/
class TodoContract {
    data class State(
        val isLoading: Boolean,
        val isError: Boolean,
        val todoList: List<Todo>,
        val todoDetail: Todo? = null,
        val saveSuccess:Boolean = false
    ) :
        IViewState

    sealed class Event : IViewEvent {
        object GoLogin : Event()
        data class Edit(val todo: Todo) : Event()
        data class Add(val title: String, val date: String, val content: String) : Event()
        data class Done(val todo: Todo) : Event()
    }

    sealed class Effect : IViewSideEffect {
        data class GoDetail(val todo: Todo) : Effect()
        object Login : Effect()
        data class ShowToast(val msg: String) : Effect()
    }
}