package com.flappy.wandroid.ui.page.wechat

import com.flappy.wandroid.base.BaseViewModel
import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.repository.WechatRepository
import com.flappy.wandroid.utils.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月11日 14:28
 **/
@HiltViewModel
class WechatViewModel @Inject constructor(val repository: WechatRepository) :
    BaseViewModel<WechatContract.State, WechatContract.Event, WechatContract.Effect>() {
    init {
        fetchData()
    }

    override fun getInitialState(): WechatContract.State =
        WechatContract.State(isLoading = true, isError = false, tabs = emptyList())

    override fun handleEvents(event: WechatContract.Event) {
        when (event) {
            WechatContract.Event.Retry -> fetchData()
        }
    }

    private fun fetchData() {
        setState { copy(isLoading = true, isError = false) }
        launch {
            when (val result = repository.getWechatAccountList()) {
                is ApiResult.Success -> setState { copy(isLoading = false, tabs = result.data) }
                is ApiResult.Failure -> {
                    setState { copy(isLoading = false, isError = true) }
                    setEffect { WechatContract.Effect.ToastMsg(result.exception.errMsg) }
                }
            }
        }
    }
}