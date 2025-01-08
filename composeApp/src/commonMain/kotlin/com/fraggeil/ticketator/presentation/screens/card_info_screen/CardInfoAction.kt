package com.fraggeil.ticketator.presentation.screens.card_info_screen

sealed interface CardInfoAction {
    data object OnBackClicked : CardInfoAction
    data object OnNextClicked : CardInfoAction
    data class OnCardNumberChanged(val cardNumber: String) : CardInfoAction
    data class OnCardValidUntilChanged(val cardValidUntil: String) : CardInfoAction
}