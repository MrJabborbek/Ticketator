package com.fraggeil.ticketator.presentation.screens.otp_payment_screen

import com.fraggeil.ticketator.domain.model.Ticket

sealed interface OtpPaymentOneTimeState {
    data class NavigateToTickets(val tickets: List<Ticket>): OtpPaymentOneTimeState
}