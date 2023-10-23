package com.flappy.wandroid.ui.page.todo

import android.icu.text.CaseMap.Title
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flappy.wandroid.base.BaseViewModel
import com.flappy.wandroid.config.GlobalConfig
import com.flappy.wandroid.data.api.ApiResponse
import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.bean.Todo
import com.flappy.wandroid.data.repository.TodoRepository
import com.flappy.wandroid.utils.launch
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

            is TodoContract.Event.Add -> {
                addTODO(event.title, event.date, event.content)
            }

        }
    }


    private fun addTODO(title: String, date: String, content: String) {
        launch {
            val result = repository.addTODO(title, date, content)
            when (result) {
                is ApiResult.Failure -> setEffect { TodoContract.Effect.ShowToast(result.exception.errMsg) }
                is ApiResult.Success -> setState { copy(saveSuccess = true) }
            }
        }
    }

}