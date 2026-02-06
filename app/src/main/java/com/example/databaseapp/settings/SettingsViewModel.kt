package com.example.databaseapp.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.databaseapp.data.Profile
import com.example.databaseapp.data.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class SettingsViewModel(val profileRepository: ProfileRepository) : ViewModel() {
    private val _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState = _settingsUiState.asStateFlow()

    private val _usernameFlow = MutableStateFlow("")

    init {
        viewModelScope.launch {
            profileRepository.getAllProfilesStream()
                .collect { profiles ->
                    val username = profiles.lastOrNull()?.username ?: ""
                    _settingsUiState.value = _settingsUiState.value.copy(username = username)
                    _usernameFlow.value = username
                }
        }

        viewModelScope.launch {
            _usernameFlow
                .debounce(500)
                .distinctUntilChanged()
                .collect { username ->
                    saveProfile(username)
                }
        }
    }

    fun updateUsername(username: String) {
        _usernameFlow.value = username
        _settingsUiState.value = _settingsUiState.value.copy(username = username)
    }

    private suspend fun saveProfile(username: String) {
        profileRepository.insertProfile(Profile(username = username))
    }

    data class SettingsUiState(val username: String = "")
}
