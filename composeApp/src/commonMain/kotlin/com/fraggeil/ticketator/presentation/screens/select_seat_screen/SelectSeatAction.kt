package com.fraggeil.ticketator.presentation.screens.select_seat_screen

import com.fraggeil.ticketator.domain.model.Journey

sealed interface SelectSeatAction {
    data object OnBackClicked : SelectSeatAction
    data object OnPayClicked : SelectSeatAction
    data class OnSeatClicked(val seat: Int) : SelectSeatAction
    data class OnJourneySelected(val journey: Journey) : SelectSeatAction
}