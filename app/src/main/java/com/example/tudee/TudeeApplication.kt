package com.example.tudee

import android.app.Application
import com.example.tudee.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class TudeeApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TudeeApplication)
            modules(appModule)
        }
    }
}