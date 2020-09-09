package com.dawar.evreka

import android.app.Application
import android.content.Context
import com.dawar.evreka.di.DIFramework

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        DIFramework.init(this)
    }

    companion object {
        var instance: App? = null
        fun getAppContext(): Context {
            return instance as Context
        }
    }
}