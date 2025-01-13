package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.core.domain.setFromTimeToBeginningOfTheDay
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.repository.SearchResultsRepository
import kotlinx.coroutines.delay

class DefaultSearchResultsRepository: SearchResultsRepository {
    override suspend fun getJourneysByFilter(filter: Filter): Result<List<Journey>, Error> {
        delay(Constants.FAKE_DELAY_TO_TEST)
        return Result.Success(
            fakeJourneys.filter { t ->
                t.timeStart.setFromTimeToBeginningOfTheDay() == filter.dateGo.setFromTimeToBeginningOfTheDay()
            }
        )
    }
}