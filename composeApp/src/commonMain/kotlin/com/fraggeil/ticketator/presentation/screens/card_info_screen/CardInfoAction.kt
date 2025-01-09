package com.fraggeil.ticketator.presentation.screens.card_info_screen

import com.fraggeil.ticketator.domain.model.Journey

sealed interface CardInfoAction {
    data object OnBackClicked : CardInfoAction
    data object OnNextClicked : CardInfoAction
    data class OnCardNumberChanged(val cardNumber: String) : CardInfoAction
    data class OnCardValidUntilChanged(val cardValidUntil: String) : CardInfoAction
    data class OnJourneySelected(val journey: Journey) : CardInfoAction
}