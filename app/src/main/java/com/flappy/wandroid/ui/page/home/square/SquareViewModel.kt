package com.flappy.wandroid.ui.page.home.square

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
 * @Description
 * @Date 2023年09月26日 9:48
 **/
@HiltViewModel
class SquareViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {
    val squarePager = Pager(
        config = PagingConfig(GlobalConfig.PAGE_SIZE),
        pagingSourceFactory = repository::squarePageSource
    ).flow.cachedIn(viewModelScope)
}