package com.fraggeil.ticketator.presentation.screens.tickets_screen

import com.fraggeil.ticketator.domain.model.Ticket

sealed interface TicketsAction {
    data class OnDownloadTicketClicked(val ticket: Ticket) : TicketsAction
    data class OnShareTicketClicked(val ticket: Ticket) : TicketsAction
    data class OnTicketClicked(val ticket: Ticket) : TicketsAction
    data object OnBuyTicketButtonClicked: TicketsAction
    data object OnShowAllTicketsClicked: TicketsAction
    data class OnInitialTicketsSelected(val tickets: List<Ticket>) : TicketsAction
}