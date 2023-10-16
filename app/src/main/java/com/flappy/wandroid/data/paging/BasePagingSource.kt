package com.flappy.wandroid.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.flappy.wandroid.data.api.ApiResponse
import com.flappy.wandroid.data.api.ApiResult
import com.flappy.wandroid.data.bean.PagedData
import com.flappy.wandroid.utils.safeCall

/**
 * @Author: luweiming
 * @Description:抽离获取分页数据的公共逻辑，子类实现具体的请求函数执行分页请求
 * @Date: Created in 9:30 2022/11/4
 */
class BasePagingSource<T : Any>(
    private val startPage: Int = 0,
    val request: suspend (Int) -> ApiResponse<PagedData<T>>
) :
    PagingSource<Int, T>() {


    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        //第一次加载params.key为空
        val page = params.key ?: startPage
        val result = safeCall {
            request(page)
        }
        return if (result is ApiResult.Success) {
            val nextPage = result.data.let { if (page >= it.pageCount - 1) null else page + 1 }
            val list = result.data.datas
            LoadResult.Page(
                list, prevKey = when (page) {
                    startPage -> null
                    else -> page - 1
                }, nextKey = nextPage
            )
        } else {
            LoadResult.Error((result as ApiResult.Failure).exception)
        }
    }


}