package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable
data class AddressComponent(
    val long_name: String,
    val short_name: String,
    val types: List<String>
)