package com.fraggeil.ticketator.presentation.screens.start_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.domain.repository.AppSettingsRepository
import kotlinx.coroutines.launch

class StartViewModel(
    private val repository: AppSettingsRepository
): ViewModel() {
    fun onAction(action: StartAction){
        when(action) {
            is StartAction.OnStartClick -> {
                viewModelScope.launch {
                    repository.setLaunched()
                }
            }
        }
    }
}