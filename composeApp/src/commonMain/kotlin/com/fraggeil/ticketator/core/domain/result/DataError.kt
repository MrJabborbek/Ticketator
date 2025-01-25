package com.fraggeil.ticketator.core.domain.result

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN,
        INVALID_OTP,
        INVALID_TOKEN,
        NOT_FOUND
    }

    enum class Local: DataError {
        DISK_FULL,
        UNKNOWN,
        PRODUCT_NOT_FOUND,
        POST_NOT_FOUND,
        PRODUCT_COUNT_EXCEEDED,
        USER_NOT_FOUND
    }

    sealed class LocationError: DataError {
        data class NO_PERMISSION(val message: String? = null, val placeHolder: String? = null): LocationError()
        data class NO_GPS(val message: String? = null, val placeHolder: String? = null): LocationError()
        data class NO_GEOLOCATION(val message: String? = null, val placeHolder: String? = null): LocationError()
        data class UNKNOWN(val message: String? = null, val placeHolder: String? = null): LocationError()
    }
}