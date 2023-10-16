package com.flappy.wandroid.ui.page.system

import com.flappy.wandroid.base.BaseViewModel
import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.repository.SystemRepository
import com.flappy.wandroid.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月10日 10:43
 **/
@HiltViewModel
class SystemViewModel @Inject constructor(val repository: SystemRepository) :
    BaseViewModel<SystemContract.State, SystemContract.Event, SystemContract.Effect>() {
    init {
        fetchData()
    }

    override fun getInitialState() =
        SystemContract.State(isLoading = true, isError = false, data = emptyList())

    override fun handleEvents(event: SystemContract.Event) {
        when (event) {
            SystemContract.Event.Retry -> fetchData()
            is SystemContract.Event.ItemClick -> {}
        }
    }

    private fun fetchData() {
        setState { copy(isLoading = true, isError = false) }
        launch {
            when (val result = repository.getSystemList()) {
                is ApiResult.Success -> setState { copy(isLoading = false, data = result.data) }
                is ApiResult.Failure -> {
                    setState { copy(isLoading = false, isError = true) }
                    setEffect { SystemContract.Effect.ToastMsg(result.exception.errMsg) }
                }
            }
        }
    }
}