package com.flappy.wandroid.base

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * Base view model
 *
 * @param UiState 页面状态信息
 * @param UiEvent 用户的触发操作
 * @param Effect 仅生效一次的副作用，例如Toast、Dialog、页面跳转等
 * @constructor Create empty Base view model
 */
abstract class BaseViewModel<UiState : IViewState, UiEvent : IViewEvent, Effect : IViewSideEffect> :
    ViewModel() {
    /**
     * 页面初始状态
     */
    private val initialViewState: UiState by lazy { getInitialState() }

    /**
     * 由子类定义页面初始状态
     *
     * @return
     */
    abstract fun getInitialState(): UiState

    /**
     * 读写分离的页面状态state
     */
    private val _viewState = mutableStateOf(initialViewState)
    val viewState: State<UiState> = _viewState


    /**
     * 页sharedFlow实现面触发事件的收发
     */
    private val _viewEvent = MutableSharedFlow<UiEvent>()

    /**
     * channel实现一次性事件的收发
     */
    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()


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
        val newState = viewState.value.reducer()
        _viewState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effect = builder()
        viewModelScope.launch {
            _effect.send(effect)
        }
    }


}