package com.flappy.wandroid.data.repository

import com.flappy.wandroid.data.api.ApiService
import com.flappy.wandroid.utils.safeCall
import javax.inject.Inject

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月16日 10:06
 **/
class UserRepository @Inject constructor(val api: ApiService) : BaseRepository() {
    suspend fun login(userName: String, pwd: String) = safeCall { api.login(userName, pwd) }
}