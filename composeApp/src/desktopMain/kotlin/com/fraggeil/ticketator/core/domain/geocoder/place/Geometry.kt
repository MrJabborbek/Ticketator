package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable
data class Geometry(
    val bounds: Bounds,
    val location: Location,
    val location_type: String,
    val viewport: Viewport
)