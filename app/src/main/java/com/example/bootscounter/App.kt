package com.example.bootscounter

import android.app.Application
import com.example.bootscounter.di.Modules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    Modules.main
                )
            )
        }
    }
}