package com.example.databaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.databaseapp.data.ProfileDao
import com.example.databaseapp.data.ProfileDatabase
import com.example.databaseapp.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dao = getDao()
        enableEdgeToEdge()
        setContent {
            AppNavigation(dao = dao)
        }
    }

    fun getDao() : ProfileDao {
        return ProfileDatabase.getDatabase(this).profileDao()
    }
}
