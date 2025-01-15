package com.fraggeil.ticketator.domain.repository

import com.fraggeil.ticketator.domain.model.Ticket
import kotlinx.coroutines.flow.Flow

interface TicketsRepository {
    fun getTickets(): Flow<Pair<List<Ticket>, List<Ticket>>> //old and new
}