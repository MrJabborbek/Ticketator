package com.fraggeil.ticketator.presentation.screens.otp_payment_screen

sealed interface OtpPaymentOneTimeState {
    data object NavigateToTickets: OtpPaymentOneTimeState
}