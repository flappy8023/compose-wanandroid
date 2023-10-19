package com.flappy.wandroid.data.repository

import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.data.bean.Todo
import com.flappy.wandroid.data.paging.BasePagingSource
import javax.inject.Inject

/**
 * @Auther: luweiming
 * @Date: 2023/10/19 20:55
 * @Description:
 */
class TodoRepository @Inject constructor(val api: ApiService) : BaseRepository() {

    fun todoPageSource(status: Int? = null, type: Int? = null) = BasePagingSource {
        api.getTODOList(it, status, type)
    }
}