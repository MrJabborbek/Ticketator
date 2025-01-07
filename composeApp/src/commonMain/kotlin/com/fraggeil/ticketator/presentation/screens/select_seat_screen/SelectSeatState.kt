package com.fraggeil.ticketator.presentation.screens.select_seat_screen

import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.domain.model.Journey

data class SelectSeatState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val selectedJourney: Journey? = FakeData.fakeJourneys[0],
    val selectedSeats: List<Int> = emptyList(),
//    val availableSeats: List<Int> = emptyList(),
//    val reservedSeats: List<Int> = emptyList(),
//    val unAvailableSeats: List<Int> = emptyList(),
)
