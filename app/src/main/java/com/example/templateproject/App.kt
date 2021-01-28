package com.example.templateproject

import android.app.Application
import com.czh.xhlib.config.AppConfig

class App : Application() {

    private lateinit var instance: App

    override fun onCreate() {
        super.onCreate()
        instance = this
        AppConfig.init(instance)
    }
}