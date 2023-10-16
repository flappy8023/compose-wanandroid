package com.flappy.wandroid.ui.page.home.qa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.flappy.wandroid.config.GlobalConfig
import com.flappy.wandroid.data.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description 问答页面viewmodel
 * @Date 2023年09月25日 14:41
 **/
@HiltViewModel
class QAViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    val pagingData = Pager(
        config = PagingConfig(GlobalConfig.PAGE_SIZE),
        pagingSourceFactory = repository::qaPageSource
    ).flow.cachedIn(viewModelScope)


}



