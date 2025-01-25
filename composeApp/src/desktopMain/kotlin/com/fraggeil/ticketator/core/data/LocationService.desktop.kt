package com.fraggeil.ticketator.core.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.net.URL

// Define a common class for location service
actual class LocationService {
    actual fun getCurrentLocation(
        locationSource: LocationSource,
        onPermissionRequired: () -> Unit,
        onTurnOnGpsRequired: () -> Unit,
        onError: (Throwable) -> Unit,
        onSuccessful: (Location) -> Unit,

        ) {
        onError(Throwable("Not implemented"))
////        onSuccessful(Location(latitude = 41.311081, longitude = 69.240562))
//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = URL("http://ip-api.com/json/").readText()
//                val location = Json.decodeFromString<IPGeolocationResponse>(response)
//                withContext(Dispatchers.Main) {
//                    println("IPPPP: $location")
//                    onSuccessful(Location(location.lat, location.lon))
//                }
//            } catch (e: Exception) {
//                withContext(Dispatchers.Main) {
//                    println("IPPPP: error:$e")
//                    onError(e)
//                }
//            }
//        }
    }
}

//@Serializable
//data class IPGeolocationResponse(val lat: Double, val lon: Double)
//
//fun getLocationFromIP(onSuccess: (Double, Double) -> Unit, onFailure: (Exception) -> Unit) {
//
//}