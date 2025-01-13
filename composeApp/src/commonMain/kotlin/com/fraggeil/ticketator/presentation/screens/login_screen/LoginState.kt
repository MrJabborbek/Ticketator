package com.fraggeil.ticketator.presentation.screens.login_screen

data class LoginState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val phoneNumber: String = "+998",
)
