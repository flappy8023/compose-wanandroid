package com.flappy.wandroid.ui.page.todo

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flappy.wandroid.base.BaseViewModel
import com.flappy.wandroid.config.GlobalConfig
import com.flappy.wandroid.data.repository.TodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月13日 10:15
 **/
@HiltViewModel
class TodoViewModel @Inject constructor(val repository: TodoRepository) :
    BaseViewModel<TodoContract.State, TodoContract.Event, TodoContract.Effect>() {

    fun donePager(type: Int? = null) = Pager(
        config = PagingConfig(GlobalConfig.PAGE_SIZE),
        pagingSourceFactory = {
            repository.todoPageSource(1, type)
        }).flow.cachedIn(viewModelScope)

    fun todoPager(type: Int? = null) =
        Pager(config = PagingConfig(GlobalConfig.PAGE_SIZE), pagingSourceFactory = {
            repository.todoPageSource(0, type)
        }).flow.cachedIn(viewModelScope)


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