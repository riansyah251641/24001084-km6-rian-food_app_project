package com.fromryan.projectfoodapp

import android.app.Application
import com.fromryan.projectfoodapp.data.source.lokal.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}