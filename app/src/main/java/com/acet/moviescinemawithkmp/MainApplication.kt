package com.acet.moviescinemawithkmp

import android.app.Application
import com.acet.moviescinemawithkmp.di.appModule
import com.acet.shared_mobile.data.di.dataModule
import com.acet.shared_mobile.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(
                appModule,
                dataModule,
                domainModule
            )
        }
    }
}