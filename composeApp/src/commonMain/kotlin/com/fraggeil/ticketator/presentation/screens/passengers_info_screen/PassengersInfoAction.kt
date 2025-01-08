package com.fraggeil.ticketator.presentation.screens.passengers_info_screen

import com.fraggeil.ticketator.domain.model.Journey

sealed interface PassengersInfoAction {
    data object OnBackClicked: PassengersInfoAction
    data object OnPaymentClicked: PassengersInfoAction
    data class OnPassengerNameEntered(val seat: Int, val name: String): PassengersInfoAction
    data class OnPassengerPhoneEntered(val seat: Int, val phone: String): PassengersInfoAction
    data class OnJourneySelected(val journey: Journey): PassengersInfoAction
}