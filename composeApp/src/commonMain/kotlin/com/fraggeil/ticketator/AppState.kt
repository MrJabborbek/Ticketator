package com.fraggeil.ticketator

import com.fraggeil.ticketator.domain.model.AppSettings

data class AppState(
    val appSettings: AppSettings = AppSettings(),
    val isLoading: Boolean = true,
)
