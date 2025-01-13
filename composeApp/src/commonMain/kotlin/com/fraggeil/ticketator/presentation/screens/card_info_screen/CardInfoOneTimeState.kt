package com.fraggeil.ticketator.presentation.screens.card_info_screen

sealed interface CardInfoOneTimeState {
    data class NavigateToOtpPayment(val token: String, val phoneNumber: String): CardInfoOneTimeState
}