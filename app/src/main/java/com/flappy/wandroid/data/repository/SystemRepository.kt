package com.flappy.wandroid.data.repository

import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.utils.safeCall
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月10日 14:08
 **/
class SystemRepository @Inject constructor(private val api: ApiService) : BaseRepository() {
    suspend fun getSystemList() = safeCall { api.getTrees() }

}