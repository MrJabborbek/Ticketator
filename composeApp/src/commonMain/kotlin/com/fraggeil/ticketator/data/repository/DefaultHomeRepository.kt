package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.uzbekistan.District
import com.fraggeil.ticketator.domain.model.Post
import com.fraggeil.ticketator.domain.model.uzbekistan.Region
import com.fraggeil.ticketator.domain.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.random.Random

class DefaultHomeRepository(
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

    override fun observeCurrentLocation(): Flow<Pair<Region, District>> {
        val region = regions.random()
        val district = region.districts.random()
        return flowOf(Pair(region, district)) //TODO

    }

    override fun observeIsThereNewNotifications(): Flow<Boolean> {
        return flowOf(Random.nextBoolean()) //TODO
    }

}