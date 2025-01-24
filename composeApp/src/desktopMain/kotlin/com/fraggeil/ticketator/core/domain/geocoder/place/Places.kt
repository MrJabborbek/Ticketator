package com.fraggeil.ticketator.core.domain.geocoder.place

import dev.jordond.compass.Coordinates
import dev.jordond.compass.Place
import kotlinx.serialization.Serializable

@Serializable
data class Places(
    val plus_code: PlusCode,
    val results: List<Result>,
    val status: String
)

fun Places.toPlaceList(): List<Place> {
    return results.map { result ->
        val coordinates = result.address_components.find { "latitude" in it.types && "longitude" in it.types }
            ?.let { Coordinates(it.long_name.toDoubleOrNull() ?: 0.0, it.short_name.toDoubleOrNull() ?: 0.0) }
            ?: Coordinates(0.0, 0.0)

        Place(
            coordinates = coordinates,
            name = result.formatted_address,
            street = result.address_components.find { "route" in it.types }?.long_name,
            isoCountryCode = result.address_components.find { "country" in it.types }?.short_name,
            country = result.address_components.find { "country" in it.types }?.long_name,
            postalCode = result.address_components.find { "postal_code" in it.types }?.long_name,
            administrativeArea = result.address_components.find { "administrative_area_level_1" in it.types }?.long_name,
            subAdministrativeArea = result.address_components.find { "administrative_area_level_2" in it.types }?.long_name,
            locality = result.address_components.find { "locality" in it.types }?.long_name,
            subLocality = result.address_components.find { "sublocality" in it.types }?.long_name,
            thoroughfare = result.address_components.find { "route" in it.types }?.long_name,
            subThoroughfare = result.address_components.find { "street_number" in it.types }?.long_name
        )
    }
}
