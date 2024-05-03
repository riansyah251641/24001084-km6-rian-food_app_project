package com.fromryan.projectfoodapp

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.fromryan.projectfoodapp.data.source.lokal.database.AppDatabase
import com.fromryan.projectfoodapp.di.AppModules
import com.fromryan.projectfoodapp.presentation.main.SharedPreferenceMainManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
        val sharedPreferenceManager = SharedPreferenceMainManager(this)
        AppCompatDelegate.setDefaultNightMode(sharedPreferenceManager.themeFlag[sharedPreferenceManager.theme])
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules (AppModules.modules)
        }
    }
}