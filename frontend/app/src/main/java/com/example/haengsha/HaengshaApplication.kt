package com.example.haengsha

import android.app.Application
import com.example.haengsha.model.dataSource.AppContainer
import com.example.haengsha.model.dataSource.HaengshaAppContainer

class HaengshaApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = HaengshaAppContainer()
    }
}