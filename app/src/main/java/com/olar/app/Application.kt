package com.olar.app

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.olar.di.module.classesModule
import com.olar.di.module.preferencesModule
import com.olar.di.module.viewModelModule
import com.olar.di.module.networkModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class Application : Application() {

    companion object {
        var language: String = "ar"
        var appTheme: String = "light"
    }

    val sharedPreferences: SharedPreferences by inject()

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@Application)
            modules(networkModule, preferencesModule, viewModelModule, classesModule)
        }
        language = if (sharedPreferences.getString("lang", "en") == "en") {
            "en"
        } else {
            "ar"
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)

    }


}