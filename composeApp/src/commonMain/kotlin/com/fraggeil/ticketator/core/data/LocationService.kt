package com.fraggeil.ticketator.core.data

// Define a data class to store latitude and longitude coordinates
data class Location(val latitude: Double, val longitude: Double)

// Define a common class for location service
expect class LocationService {
   fun getCurrentLocation(
      locationSource: LocationSource = LocationSource.GPS_AND_NETWORK,
      onPermissionRequired: () -> Unit = {},
      onTurnOnGpsRequired: () -> Unit = {},
      onError: (Throwable) -> Unit = {},
      onSuccessful: (Location) -> Unit,
   )
}

enum class LocationSource{
   GPS_ONLY, NETWORK_ONLY, GPS_AND_NETWORK
}