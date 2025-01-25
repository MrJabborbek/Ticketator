package com.fraggeil.ticketator.core.data

import com.fraggeil.ticketator.core.data.network.safeCall
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.serialization.Serializable

class LocationFromIP(
    private val httpClient: HttpClient
) {
    suspend fun getLocationFromIP(): Result<IpInfo,  DataError.Remote> {
//        return safeCall<IpInfo> { httpClient.get(urlString = "http://ip-api.com/json/") }
        return safeCall<IpInfo> { httpClient.get(urlString = "https://ipinfo.io/json/") }

//        CoroutineScope(Dispatchers.IO).launch {
//            try {
//                val response = Url("http://ip-api.com/json/").readText()
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

@Serializable
data class IpInfo( //https://ipinfo.io/json/
    val city: String,
    val country: String,
    val ip: String,
    val loc: String,
    val org: String,
    val readme: String,
    val region: String,
    val timezone: String
)

//@Serializable
//data class IpInfo( //"http://ip-api.com/json/
//    @SerialName("as")
//    val ath: String,
//    val city: String,
//    val country: String,
//    val countryCode: String,
//    val isp: String,
//    val lat: Double,
//    val lon: Double,
//    val org: String,
//    val query: String,
//    val region: String,
//    val regionName: String,
//    val status: String,
//    val timezone: String,
//    val zip: String
//)