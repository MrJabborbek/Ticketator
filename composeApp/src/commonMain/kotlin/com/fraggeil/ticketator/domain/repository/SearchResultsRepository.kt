package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.core.domain.result.Error
import com.fraggeil.ticketator.core.domain.result.Result
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.Journey

interface SearchResultsRepository {
    suspend fun getJourneysByFilter(filter: Filter): Result<List<Journey>, Error>
}