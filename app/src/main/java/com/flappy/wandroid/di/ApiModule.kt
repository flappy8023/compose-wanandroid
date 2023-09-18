package com.flappy.wandroid.di

import com.flappy.wandroid.data.api.ApiManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * @Author flappy8023
 * @Description //TODO
 * @Date 2023年09月20日 17:28
 **/
@Module
@InstallIn(SingletonComponent::class)
class ApiModule {
    @Provides
    @Singleton
    fun provideApiService() = ApiManager.service
}