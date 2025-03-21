package com.fraggeil.ticketator.presentation.screens.otp_screen

data class OtpPaymentState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val phoneNumber: String = "",
    val timer: String = "",
    val isResendEnabled: Boolean = false,
    val otp: String = "",
    val isErrorMode: Boolean = false,
)