package com.fraggeil.ticketator.core.domain.geocoder.network

import com.fraggeil.ticketator.core.domain.geocoder.geocode_response.GeocodeResponse
import com.fraggeil.ticketator.core.domain.geocoder.place.Places
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Result
import dev.jordond.compass.InternalCompassApi


interface RemotePlacesDataSource {
    @OptIn(InternalCompassApi::class)
    suspend fun searchPlacesData(
        latitude: Double,
        longitude: Double,
        apiKey: String,
        resultLimit: Int? = null,
    ): Result<GeocodeResponse, DataError>
}