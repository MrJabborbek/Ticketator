package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.domain.repository.TicketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class DefaultTicketsRepository: TicketsRepository {
    override fun getTickets(): Flow<Pair<List<Ticket>, List<Ticket>>> {
        return flowOf(fakeTickets.partition { it.journey.timeArrival < DateTimeUtil.nowMilliseconds() })
    }
}