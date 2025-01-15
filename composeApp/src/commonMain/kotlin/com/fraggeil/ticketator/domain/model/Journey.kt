package com.fraggeil.ticketator.domain.model

import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.uzbekistan.Region

data class Journey(
    val id: String,
    val timeStart: Long,
    val timeArrival: Long,
    val from: Pair<Region, District>,
    val to: Pair<Region, District>,
    val price: Int,
    val seats: Int,
    val seatsReserved: List<Int>,
    val seatsAvailable: List<Int>,
    val seatsUnavailable: List<Int>,
    val selectedSeats: List<Int> = emptyList(),
    val passengers: List<Passenger> = emptyList(),
    val stopAt: List<String>
)
