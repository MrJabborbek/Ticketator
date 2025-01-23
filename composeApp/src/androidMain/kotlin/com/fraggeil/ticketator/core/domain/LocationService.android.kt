package com.fraggeil.ticketator.core.domain

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// Implement the LocationService in Android
actual class LocationService(private val context: Context)  {

    // Initialize the FusedLocationProviderClient
//    private val fusedLocationClient by lazy {
//        LocationServices.getFusedLocationProviderClient(context)
//    }


    @SuppressLint("MissingPermission")
    actual suspend fun getCurrentLocation(
        onPermissionRequired: () -> Unit,
        onTurnOnGpsRequired: () -> Unit,
        onError: (Throwable) -> Unit,
        onSuccessful: (Location) -> Unit,
    ) {
            if (!isPermissionGranted(context)){
                onPermissionRequired()
                return
            }

        if (!isGPSEnabled(context)){
            onTurnOnGpsRequired()
            return
        }

//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        LocationServices.getFusedLocationProviderClient(context).lastLocation.addOnSuccessListener { location ->
            location?.let {
                onSuccessful(Location(it.latitude, it.longitude))
            } ?: run {
                onError(Throwable("Location is null"))
            }
        }.addOnFailureListener { e ->
            onError(e)
        }
    }
}


private fun isGPSEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
}

private fun isPermissionGranted(context: Context): Boolean{
    return ActivityCompat.checkSelfPermission(context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

}

@SuppressLint("MissingPermission")
fun getCurrentLocation(
    fusedLocationClient: FusedLocationProviderClient,
    onLocationFound: (android.location.Location?) -> Unit
) {
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        onLocationFound(location)
    }
}
