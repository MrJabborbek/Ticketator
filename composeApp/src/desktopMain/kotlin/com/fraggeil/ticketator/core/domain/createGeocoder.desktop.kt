package com.fraggeil.ticketator.core.domain

import com.fraggeil.ticketator.core.domain.geocoder.network.KtorRemotePlacesDataSource
import com.fraggeil.ticketator.core.domain.geocoder.place.toPlaceList
import dev.jordond.compass.Coordinates
import dev.jordond.compass.Place
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geocoder.GeocoderResult
import dev.jordond.compass.geocoder.PlatformGeocoder
import dev.jordond.compass.geocoder.googleMaps
import org.koin.compose.getKoin
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.java.KoinJavaComponent.inject

actual fun createGeocoder(): Geocoder {
    val geocoder: Geocoder by inject(clazz = CustomGeocoder::class.java)
    return geocoder
//    return Geocoder.googleMaps(apiKey = "AIzaSyBetCt0piP5xjqn9Hj3LBKI1MdYxR1ZjjE")
}

class CustomGeocoder(
    private val apiKey: String,
    private val remotePlacesDataSource: KtorRemotePlacesDataSource
) : Geocoder{

    override val platformGeocoder: PlatformGeocoder
        get() = TODO("Not yet implemented")

    override suspend fun forward(address: String): GeocoderResult<Coordinates> {
        TODO("Not yet implemented")
    }

    override fun isAvailable(): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun reverse(latitude: Double, longitude: Double): GeocoderResult<Place> {
        remotePlacesDataSource.searchPlacesData(
            latitude = latitude,
            longitude = longitude,
            apiKey = apiKey
        )?.let {
            return GeocoderResult.Success(
                it.toPlaceList()
            )
        } ?: return GeocoderResult.NotFound

    }

}

