package com.fraggeil.ticketator.presentation

import kotlinx.serialization.Serializable

sealed interface Route {
    val route: String

    @Serializable
    data object TicketatorGraph : Route{
        override val route: String = "TicketatorGraph"
    }

    @Serializable
    data object Home : Route{
        override val route: String = "Home"
    }
}