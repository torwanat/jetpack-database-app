package com.example.databaseapp

import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.databaseapp.data.DatabaseApplication
import com.example.databaseapp.settings.SettingsViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory


object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            SettingsViewModel(databaseApplication().container.profileRepository)
        }
    }
}

fun CreationExtras.databaseApplication(): DatabaseApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as DatabaseApplication)