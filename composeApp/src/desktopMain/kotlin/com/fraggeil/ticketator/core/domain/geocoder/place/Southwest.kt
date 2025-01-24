package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable

data class Southwest(
    val lat: Double,
    val lng: Double
)