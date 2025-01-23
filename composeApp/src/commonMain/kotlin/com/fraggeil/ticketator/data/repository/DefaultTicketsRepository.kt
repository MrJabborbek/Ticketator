package com.fraggeil.ticketator.data.repository

import com.fraggeil.ticketator.core.domain.DateTimeUtil
import com.fraggeil.ticketator.data.database.Dao
import com.fraggeil.ticketator.data.database.Database
import com.fraggeil.ticketator.data.mappers.toTicket
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.domain.repository.TicketsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class DefaultTicketsRepository(
    private val dao: Dao
): TicketsRepository {
    override fun getTickets(): Flow<Pair<List<Ticket>, List<Ticket>>> {
        val localTickets = dao.getTickets()
            .map { ticketEntities ->
                ticketEntities.map { ticketEntity ->
                    ticketEntity.toTicket()
                }
            }.map {
                it.partition { it.journey.timeArrival < DateTimeUtil.nowMilliseconds() }
            }
        return localTickets
//        return flowOf(fakeTickets.partition { it.journey.timeArrival < DateTimeUtil.nowMilliseconds() })
    }
}