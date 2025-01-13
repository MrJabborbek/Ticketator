package com.fraggeil.ticketator.presentation.screens.otp_screen

sealed interface OtpOneTimeState {
    data object NavigateToHome: OtpOneTimeState
}