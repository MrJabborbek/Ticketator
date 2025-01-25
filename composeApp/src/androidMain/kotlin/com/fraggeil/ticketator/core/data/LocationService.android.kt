package com.fraggeil.ticketator.core.data

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationServices

// Implement the LocationService in Android
actual class LocationService(private val context: Context)  {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    // Initialize the FusedLocationProviderClient
//    private val fusedLocationClient by lazy {
//        LocationServices.getFusedLocationProviderClient(context)
//    }



    @SuppressLint("MissingPermission")
    actual fun getCurrentLocation(
        locationSource: LocationSource,
        onPermissionRequired: () -> Unit,
        onTurnOnGpsRequired: () -> Unit,
        onError: (Throwable) -> Unit,
        onSuccessful: (Location) -> Unit,
    ) {


        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager


        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (isNetworkEnabled && locationSource != LocationSource.GPS_ONLY){
            val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER,)
            if (location != null){
                onSuccessful(Location(location.latitude, location.longitude))
                return
            }
        }


            if (!isPermissionGranted(context)){
                onPermissionRequired()
                return
            }

        if (!isGPSEnabled(context)){
            onTurnOnGpsRequired()
            return
        }

        if (fusedLocationClient == null){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        }
        fusedLocationClient!!.lastLocation.addOnSuccessListener { location ->
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

private fun isNetworkEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
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

