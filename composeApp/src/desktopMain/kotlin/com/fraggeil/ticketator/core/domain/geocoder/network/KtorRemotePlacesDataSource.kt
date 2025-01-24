package com.fraggeil.ticketator.core.domain.geocoder.network


import com.fraggeil.ticketator.core.domain.geocoder.geocode_response.GeocodeResponse
import com.fraggeil.ticketator.core.domain.geocoder.place.Places
import dev.jordond.compass.InternalCompassApi
import io.ktor.client.HttpClient
import io.ktor.client.request.get

private const val BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json"

class KtorRemotePlacesDataSource(
    private val httpClient: HttpClient
): RemotePlacesDataSource {

    @OptIn(InternalCompassApi::class)
    override suspend fun searchPlacesData(
        latitude: Double,
        longitude: Double,
        apiKey: String,
        resultLimit: Int?,
    ): GeocodeResponse? {
        return safeCall<GeocodeResponse> {
            httpClient.get(
                urlString = "$BASE_URL?latlng=$latitude,$longitude&key=$apiKey"
            )
        }
    }
//
//    override suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote> {
//        return safeCall<BookWorkDto> {
//            httpClient.get(
//                urlString = "$BASE_URL/works/$bookWorkId.json"
//            )
//        }
//    }
}