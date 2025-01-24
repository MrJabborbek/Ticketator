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

    enum class LocationError: DataError {
        NO_PERMISSION,
        NO_GPS,
        NO_GEOLOCATION,
        UNKNOWN
    }
}