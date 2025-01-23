package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.core.domain.result.DataError
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.domain.setFromTimeToBeginningOfTheDay
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.repository.SearchResultsRepository
import kotlinx.coroutines.delay
import kotlin.random.Random

class DefaultSearchResultsRepository: SearchResultsRepository {
    override suspend fun getJourneysByFilter(filter: Filter): Result<List<Journey>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)

        val journeys = (1..12).map {
            val fromRegion = filter.fromRegion ?: return Result.Error(DataError.Local.UNKNOWN)
            val fromDistrict = filter.fromDistrict ?: return Result.Error(DataError.Local.UNKNOWN)
            val toRegion =filter.toRegion ?: return Result.Error(DataError.Local.UNKNOWN)
            val toDistrict = filter.toDistrict ?: return Result.Error(DataError.Local.UNKNOWN)

            val timeStart = DateTimeUtil.nowMilliseconds() + it*1000*60*60
            val timeArrival = timeStart + 8*1000*60*60 // 8 hours

            val seats = 51
            val allSeats = (1..seats).toMutableList()
            val seatsReserved = allSeats.shuffled().take(Random.nextInt(5))
            allSeats.removeAll(seatsReserved)

            val seatsUnavailable = allSeats.shuffled().take(Random.nextInt(51))
            allSeats.removeAll(seatsUnavailable)

            val seatsAvailable = allSeats

            Journey(
                id = it.toString(),
                timeStart = timeStart,
                timeArrival = timeArrival,
                from = Pair(fromRegion, fromDistrict),
                to = Pair(toRegion, toDistrict),
                price = 139000,
                seats = seats,
                seatsReserved = seatsReserved,
                seatsAvailable = seatsAvailable,
                seatsUnavailable = seatsUnavailable,
                stopAt = emptyList()
            )
        }
        _testJourneys.value = journeys

        return Result.Success(
            journeys.filter { t ->
                t.timeStart.setFromTimeToBeginningOfTheDay() == filter.dateGo.setFromTimeToBeginningOfTheDay()
            }
        )
    }
}