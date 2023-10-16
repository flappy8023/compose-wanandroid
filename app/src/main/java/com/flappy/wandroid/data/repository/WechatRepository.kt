package com.flappy.wandroid.data.repository

import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.data.paging.BasePagingSource
import com.flappy.wandroid.utils.safeCall
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月11日 14:26
 **/
class WechatRepository @Inject constructor(private val api: ApiService) : BaseRepository() {
    suspend fun getWechatAccountList() = safeCall { api.getWechatAccountList() }

    fun getWechatArticlesById(id: Long) =
        BasePagingSource { api.getWechatHistoryArticles(id, it) }
}