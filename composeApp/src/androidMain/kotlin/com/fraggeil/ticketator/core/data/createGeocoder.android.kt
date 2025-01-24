package com.fraggeil.ticketator.core.data

import dev.jordond.compass.geocoder.Geocoder
import dev.jordond.compass.geocoder.mobile

actual fun createGeocoder(): Geocoder {
    return Geocoder.mobile()
}