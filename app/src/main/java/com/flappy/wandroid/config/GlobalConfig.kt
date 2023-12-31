package com.flappy.wandroid.config

import androidx.datastore.preferences.core.booleanPreferencesKey

/**
 * @Author flappy8023
 * @Description 全局配置
 * @Date 2023年09月25日 10:17
 **/
object GlobalConfig {
    /**
     * 分页请求单页请求个数
     */
    const val PAGE_SIZE = 30
    val KEY_LOGIN = booleanPreferencesKey("login")
}