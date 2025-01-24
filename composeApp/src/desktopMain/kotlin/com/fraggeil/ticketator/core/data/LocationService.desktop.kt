package com.fraggeil.ticketator.core.data

// Define a common class for location service
actual class LocationService {
    actual fun getCurrentLocation(
        onPermissionRequired: () -> Unit,
        onTurnOnGpsRequired: () -> Unit,
        onError: (Throwable) -> Unit,
        onSuccessful: (Location) -> Unit,

        ) {
        onSuccessful(Location(latitude = 41.311081, longitude = 69.240562))
    }
}