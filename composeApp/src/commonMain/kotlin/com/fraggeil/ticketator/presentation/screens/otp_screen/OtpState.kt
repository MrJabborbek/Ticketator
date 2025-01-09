package com.fraggeil.ticketator.presentation.screens.otp_screen

data class OtpState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val phoneNumber: String = "+99890*******67",
    val timer: String = "",
    val isResendEnabled: Boolean = false,
    val otp: String = "",
    val isErrorMode: Boolean = false,
)