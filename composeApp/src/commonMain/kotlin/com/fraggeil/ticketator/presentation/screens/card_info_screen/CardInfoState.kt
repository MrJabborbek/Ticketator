package com.fraggeil.ticketator.presentation.screens.card_info_screen

import com.fraggeil.ticketator.domain.model.CardData
import com.fraggeil.ticketator.domain.model.Journey

data class CardInfoState(
    val isLoading: Boolean = true,
    val isSendingData: Boolean = false,
    val error: String? = null,
    val isAllDataValid: Boolean = false,
    val isTimerEnd: Boolean = false,
    val cardData: CardData = CardData(),
    val selectedJourney: Journey? = null,
    val timer: String = "",
    val isSaveCardChecked: Boolean = false,
    val savedCardData: List<CardData> = emptyList()
)
