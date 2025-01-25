package com.fraggeil.ticketator.core.data

import com.fraggeil.ticketator.core.domain.geocoder.network.KtorRemotePlacesDataSource
import com.fraggeil.ticketator.core.domain.geocoder.place.toPlaceList
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import dev.jordond.compass.Coordinates
import dev.jordond.compass.InternalCompassApi
import dev.jordond.compass.Place
import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geocoder.GeocoderResult
import dev.jordond.compass.geocoder.PlatformGeocoder
import dev.jordond.compass.geocoder.googleMaps
import dev.jordond.compass.geocoder.web.google.internal.toPlaces
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.koin.java.KoinJavaComponent.inject
import kotlin.coroutines.resume

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
        return true
    }

    @OptIn(InternalCompassApi::class)
    override suspend fun reverse(latitude: Double, longitude: Double): GeocoderResult<Place> {
        return suspendCancellableCoroutine {  continuation ->
            CoroutineScope(Dispatchers.IO).launch {
                remotePlacesDataSource.searchPlacesData(
                    latitude = latitude,
                    longitude = longitude,
                    apiKey = apiKey
                ).onSuccess {
                    continuation.resume( GeocoderResult.Success(it.results.toPlaces()))
                }.onError {
                    continuation.resume(GeocoderResult.NotFound)
                }
            }
        }
    }
}

