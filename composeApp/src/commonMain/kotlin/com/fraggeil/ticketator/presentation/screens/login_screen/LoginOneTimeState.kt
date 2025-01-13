package com.fraggeil.ticketator.presentation.screens.login_screen

sealed interface LoginOneTimeState {
    data class NavigateToOtpScreen(val phoneNumber: String) : LoginOneTimeState
}