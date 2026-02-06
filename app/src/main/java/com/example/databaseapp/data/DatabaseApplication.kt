package com.example.databaseapp.data

import android.app.Application

class DatabaseApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}