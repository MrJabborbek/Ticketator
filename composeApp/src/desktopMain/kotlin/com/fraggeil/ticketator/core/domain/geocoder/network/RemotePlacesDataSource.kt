package com.fraggeil.ticketator.core.domain.geocoder.network

import com.fraggeil.ticketator.core.domain.geocoder.place.Places


interface RemotePlacesDataSource {
    suspend fun searchPlacesData(
        latitude: Double,
        longitude: Double,
        apiKey: String,
        resultLimit: Int? = null,
    ): Places?
}