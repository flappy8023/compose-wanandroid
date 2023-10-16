package com.flappy.wandroid.ui.page.todo

import com.flappy.wandroid.base.BaseViewModel

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月13日 10:15
 **/
class TodoViewModel : BaseViewModel<TodoContract.State, TodoContract.Event, TodoContract.Effect>() {
    override fun getInitialState(): TodoContract.State =
        TodoContract.State(isLoading = true, isError = false, todoList = emptyList())

    override fun handleEvents(event: TodoContract.Event) {
        when (event) {
            is TodoContract.Event.Done -> {}
            is TodoContract.Event.Edit -> {}
            is TodoContract.Event.GoLogin -> {
                setEffect { TodoContract.Effect.Login }
            }
        }
    }
}