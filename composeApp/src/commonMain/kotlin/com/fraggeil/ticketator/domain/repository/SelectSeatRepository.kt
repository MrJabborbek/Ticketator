package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.domain.model.Journey
import kotlinx.coroutines.flow.Flow

interface SelectSeatRepository {
    fun observeJourneyById(id: String): Flow<Journey?>
    suspend fun onSeatSelected(seat: Int, journeyId: String)
}