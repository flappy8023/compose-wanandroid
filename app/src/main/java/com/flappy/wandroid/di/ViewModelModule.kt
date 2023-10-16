package com.flappy.wandroid.di

import com.flappy.wandroid.ui.page.wechat.WechatListViewModel
import dagger.Module
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年10月12日 9:51
 **/
@EntryPoint
@InstallIn(ActivityComponent::class)
interface ViewModelFactoryProvider{
    fun wechatListViewModel(): WechatListViewModel.Factory
}