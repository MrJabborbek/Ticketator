package com.fraggeil.ticketator.presentation.screens.card_info_screen

import com.fraggeil.ticketator.domain.model.Journey

data class CardInfoState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val isAllDataValid: Boolean = false,
    val isTimerEnd: Boolean = false,
    val cardNumber: String = "",
    val cardValidUntil: String = "",
    val selectedJourney: Journey? = null,
    val timer: String = "",
)
