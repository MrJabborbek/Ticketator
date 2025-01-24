package com.fraggeil.ticketator.core.domain.geocoder.place

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val address_components: List<AddressComponent>,
    val formatted_address: String,
//    val geometry: Geometry? = null,
//    val navigation_points: List<NavigationPoint>,
    val place_id: String,
//    val plus_code: PlusCode,
    val types: List<String>
)