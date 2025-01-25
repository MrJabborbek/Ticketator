package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.data.Location
import com.fraggeil.ticketator.core.data.LocationFromIP
import com.fraggeil.ticketator.core.data.LocationService
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.uzbekistan.Region
import com.fraggeil.ticketator.domain.repository.HomeRepository
import dev.jordond.compass.geocoder.Geocoder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.random.Random

class DefaultHomeRepository(
    private val locationService: LocationService,
    private val geocoder: Geocoder,
    private val locationFromIP: LocationFromIP
//    private val remotePlacesDataSource: KtorRemotePlacesDataSource
): HomeRepository {
    override suspend fun getRegions(): Result<List<Region>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(regions)
    }

    override suspend fun getToRegions(fromRegion: Region): Result<List<Region>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(regions.filter { it != fromRegion })
    }

    override suspend fun getMostPopularRegions(): Result<List<Region>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(regions.shuffled().take(2))
    }

    override suspend fun getAllPosts(): Result<List<Post>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
//        val places = remotePlacesDataSource.searchPlacesData(
//            latitude = 41.311081,
//            longitude = 69.240515,
//            apiKey = "AIzaSyBetCt0piP5xjqn9Hj3LBKI1MdYxR1ZjjE"
//        )

//        println("Placessss: $places")

        return Result.Success(testPosts)
    }

    override suspend fun fetchCurrentLocation(): Result<String, DataError.LocationError> {
        return suspendCancellableCoroutine { continuation ->
            locationService.getCurrentLocation(
                onPermissionRequired = {
                    CoroutineScope(Dispatchers.IO).launch {
                        locationFromIP.getLocationFromIP()
                            .onSuccess { ipInfo ->
                                continuation.resume(Result.Error(DataError.LocationError.NO_PERMISSION(placeHolder = "${ipInfo.city}, ${ipInfo.region}" )))
                            }
                            .onError {
                                continuation.resume(Result.Error(DataError.LocationError.NO_PERMISSION()))
                            }
                    }

                },
                onTurnOnGpsRequired = {
                    CoroutineScope(Dispatchers.IO).launch {
                        locationFromIP.getLocationFromIP()
                            .onSuccess { ipInfo ->
                                continuation.resume(Result.Error(DataError.LocationError.NO_GPS(placeHolder = "${ipInfo.city}, ${ipInfo.region}" )))
                            }
                            .onError {
                                continuation.resume(Result.Error(DataError.LocationError.NO_GPS()))
                            }
                    }
                },
                onError = {
                    CoroutineScope(Dispatchers.IO).launch {
                        locationFromIP.getLocationFromIP()
                            .onSuccess { ipInfo ->
                                continuation.resume(Result.Success("${ipInfo.city}, ${ipInfo.region}"))
                            }
                            .onError {
                                continuation.resume(Result.Error(DataError.LocationError.UNKNOWN()))
                            }
                    }
                }
            ){ location: Location ->
                CoroutineScope(Dispatchers.IO).launch {
                    geocoder.reverse(location.latitude, location.longitude)
//                    geocoder.reverse(41.2647,69.2163)
                        .onSuccess { places ->
                            val place = places.firstOrNull { it.toString().contains("tuman", ignoreCase = true) || it.toString().contains("shahar", ignoreCase = true) || it.toString().contains("viloyat", ignoreCase = true)}
                                ?: places.firstOrNull { it.toString().contains("туман", ignoreCase = true) || it.toString().contains("шаҳар", ignoreCase = true) || it.toString().contains("вилоят", ignoreCase = true)}
                                ?: places.firstOrNull()
                            place?.let {
                                val district = it.subLocality?:it.subAdministrativeArea ?: it.subThoroughfare ?: it.street ?:""
                                val region = it.locality?:it.administrativeArea ?: it.thoroughfare ?: it.country ?:""

                                val response = "${if (district.isNotBlank()) "$district, " else ""}$region"

                                continuation.resume(
                                    if (response.isNotBlank()) Result.Success(response) else Result.Error(DataError.LocationError.NO_GEOLOCATION())
                                )
                            }

                        }
                        .onFailed {
                            continuation.resume(Result.Error(DataError.LocationError.NO_GEOLOCATION()))
                        }
                }
            }
        }
    }


    override fun observeIsThereNewNotifications(): Flow<Boolean> {
        return flowOf(Random.nextBoolean()) //TODO
    }

}