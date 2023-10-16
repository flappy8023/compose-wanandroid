package com.flappy.wandroid.ui.page.wechat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flappy.wandroid.config.GlobalConfig
import com.flappy.wandroid.data.repository.WechatRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月11日 15:45
 **/
class WechatListViewModel @AssistedInject constructor(
    val repository: WechatRepository,
    @Assisted private val id: Long
) :
    ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(id: Long): WechatListViewModel
    }

    companion object {
        fun provideFactory(assistFactory: Factory, id: Long): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return assistFactory.create(id) as T
                }
            }
    }

    val pager = Pager(
        config = PagingConfig(GlobalConfig.PAGE_SIZE),
        pagingSourceFactory = { repository.getWechatArticlesById(id) }).flow.cachedIn(viewModelScope)


}
