package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable

data class Bounds(
    val northeast: Northeast,
    val southwest: Southwest
)