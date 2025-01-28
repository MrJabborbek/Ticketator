package com.fraggeil.ticketator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.domain.repository.AppSettingsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class AppViewModel(
    private val repository: AppSettingsRepository
): ViewModel() {
    private var observeAppSettingsJob: Job? = null
    private var isInitialized = false
    private val _state = MutableStateFlow(AppState())
    val state = _state
        .onStart {
            if (!isInitialized) {
                isInitialized = true
                observeAppSettings()
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    private fun observeAppSettings() {
        observeAppSettingsJob?.cancel()
        _state.update { it.copy(isLoading = true) }
        observeAppSettingsJob = repository.getAppSettings()
            .onEach { appSettings ->
                _state.update { it.copy(
                    appSettings = appSettings,
                    isLoading = false
                ) }
            }
            .launchIn(viewModelScope)

    }

}