package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.uzbekistan.Region
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getRegions(): Result<List<Region>, Error>
    suspend fun getToRegions(fromRegion: Region): Result<List<Region>, Error>
    suspend fun getMostPopularRegions(): Result<List<Region>, Error>
    suspend fun getAllPosts(): Result<List<Post>, Error>
    suspend fun fetchCurrentLocation(): Result<String, DataError.LocationError>
    fun observeIsThereNewNotifications(): Flow<Boolean>

}