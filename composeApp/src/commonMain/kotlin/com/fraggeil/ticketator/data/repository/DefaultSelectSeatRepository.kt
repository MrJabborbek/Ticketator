package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.domain.repository.SelectSeatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class DefaultSelectSeatRepository: SelectSeatRepository {
    override fun observeJourneyById(id: String): Flow<Journey?> {
        return _testJourneys.map { it.find { t -> t.id == id } }
    }

    override suspend fun onSeatSelected(seat: Int, journeyId: String) {
        delay(Constants.FAKE_DELAY_TO_TEST)
        val selectedJourney = _testJourneys.value.find { it.id == journeyId }
        if (selectedJourney?.seatsAvailable?.contains(seat) != true) {
            return
        }
            val selectedSeats = selectedJourney.selectedSeats.toMutableList()
            if (seat in selectedSeats) {
                selectedSeats.remove(seat)
            } else {
                selectedSeats.add(seat)
            }
        _testJourneys.update { it.map { t -> if (t.id == journeyId) t.copy(selectedSeats = selectedSeats) else t } }
    }
}