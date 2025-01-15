package com.fraggeil.ticketator.presentation.screens.passengers_info_screen

import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.model.Passenger

data class PassengersInfoState(
    val isLoading: Boolean = false,
    val error: String? = null,
//    val passengers: List<Passenger> = emptyList(),
    val isAllDataValid: Boolean = false,
    var selectedJourney: Journey? = null,
    val currentUserName: String = "",
    val currentUserPhoneNumber: String = "",

)



