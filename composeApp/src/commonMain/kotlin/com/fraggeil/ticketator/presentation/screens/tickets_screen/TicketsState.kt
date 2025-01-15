package com.fraggeil.ticketator.presentation.screens.tickets_screen

import com.fraggeil.ticketator.domain.FakeData
import com.fraggeil.ticketator.domain.model.Ticket

data class TicketsState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val initialTickets: List<Ticket> = emptyList(),
    val comingTickets: List<Ticket> = FakeData.fakeTickets,//emptyList()
    val pastTickets: List<Ticket> = emptyList()// FakeData.fakeTickets//emptyList()
)