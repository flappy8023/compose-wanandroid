package com.flappy.wandroid

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * @Author flappy8023
 * @Description
 * @Date 2023年09月15日 17:25
 **/
@HiltAndroidApp
class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }
}