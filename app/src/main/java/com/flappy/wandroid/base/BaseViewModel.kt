package com.flappy.wandroid.base

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年09月25日 16:22
 **/
abstract class BaseViewModel<UiState : IViewState, UiEvent : IViewEvent> :
    ViewModel() {
    private val initialViewState: UiState by lazy { getInitialState() }
    abstract fun getInitialState(): UiState
    var viewState by mutableStateOf<UiState>(initialViewState)
        private set

    /**
     * 页面触发事件
     */
    private val _viewEvent = MutableSharedFlow<UiEvent>()


    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            _viewEvent.collect {
                handleEvents(it)
            }
        }
    }

    fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _viewEvent.emit(event)
        }
    }

    abstract fun handleEvents(event: UiEvent)

    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = viewState.reducer()
        viewState = newState
    }


}