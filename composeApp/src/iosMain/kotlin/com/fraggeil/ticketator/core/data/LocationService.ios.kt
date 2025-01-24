package com.fraggeil.ticketator.core.data

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLLocation
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLDistanceFilterNone
import platform.CoreLocation.kCLLocationAccuracyBest
import platform.Foundation.NSError
import platform.darwin.NSObject
import kotlin.concurrent.AtomicReference

// Implement the LocationService in iOS
actual class LocationService  {

    // Define a native CLLocationManager object
    private val locationManager = CLLocationManager()

    // Define an atomic reference to store the latest location
    private val latestLocation = AtomicReference<Location?>(null)

    // Define a custom delegate that extends NSObject and implements CLLocationManagerDelegateProtocol
    private class LocationDelegate : NSObject(), CLLocationManagerDelegateProtocol {

        // Define a callback to receive location updates
        var onLocationUpdate: ((Location?) -> Unit)? = null

        @OptIn(ExperimentalForeignApi::class)
        override fun locationManager(manager: CLLocationManager, didUpdateLocations: List<*>) {
            didUpdateLocations.firstOrNull()?.let {
                val location = it as CLLocation
                location.coordinate.useContents {
                    onLocationUpdate?.invoke(Location(latitude, longitude))
                }

            }
        }

        override fun locationManager(manager: CLLocationManager, didFailWithError: NSError) {
            onLocationUpdate?.invoke(null)
        }
    }


    // Implement the getCurrentLocation() method
    actual fun getCurrentLocation(
        onPermissionRequired: () -> Unit,
        onTurnOnGpsRequired: () -> Unit,
        onError: (Throwable) -> Unit,
        onSuccessful: (Location) -> Unit,
    ) {
        locationManager.requestWhenInUseAuthorization()
        locationManager.desiredAccuracy = kCLLocationAccuracyBest
        locationManager.distanceFilter = kCLDistanceFilterNone
        locationManager.startUpdatingLocation()

        // Define a callback to receive location updates
        val locationDelegate = LocationDelegate()
        locationDelegate.onLocationUpdate = { location ->
            locationManager.stopUpdatingLocation()
            latestLocation.value = location
            if (location != null) {
                onSuccessful(location)
            } else {
                onError(Throwable("Location is null"))
            }
        }

        // Assign the locationDelegate to the CLLocationManager delegate
        locationManager.delegate = locationDelegate
    }
}