package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable

data class Northeast(
    val lat: Double,
    val lng: Double
)