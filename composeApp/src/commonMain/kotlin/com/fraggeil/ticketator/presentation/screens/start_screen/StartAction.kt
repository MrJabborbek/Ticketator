package com.fraggeil.ticketator.presentation.screens.start_screen

sealed interface StartAction {
    data object OnStartClick : StartAction
}