package com.fraggeil.ticketator.presentation.screens.tickets_screen

import com.fraggeil.ticketator.domain.model.Ticket

sealed interface TicketsAction {
    data object OnBackClicked : TicketsAction
    data class OnDownloadTicketClicked(val ticket: Ticket) : TicketsAction
    data class OnShareTicketClicked(val ticket: Ticket) : TicketsAction
    data class OnTicketClicked(val ticket: Ticket) : TicketsAction
}