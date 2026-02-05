package com.example.databaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = initDatabase()
        enableEdgeToEdge()
        setContent {
            AppNavigation(database)
        }
    }

    fun initDatabase() : ProfileDatabase {
        return ProfileDatabase.getDatabase(this)
    }
}
