package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable

data class LocationX(
    val latitude: Double,
    val longitude: Double
)