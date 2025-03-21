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

    @Serializable
    data object Post : Route{
        override val route: String = "Post"
    }

    @Serializable
    data object Start : Route{
        override val route: String = "Start"
    }

    @Serializable
    data object SearchResults : Route{
        override val route: String = "SearchResults"
    }

    @Serializable
    data object SelectSeat : Route{
        override val route: String = "SelectSeat"
    }

    @Serializable
    data object PassengersInfo : Route{
        override val route: String = "PassengersInfo"
    }

    @Serializable
    data object CardInfo : Route{
        override val route: String = "CardInfo"
    }

    @Serializable
    data object OtpPayment : Route{
        override val route: String = "OtpPayment"
    }


    @Serializable
    data object Otp : Route{
        override val route: String = "Otp"
    }


    @Serializable
    data object ProfileEdit: Route{
        override val route = "ProfileEdit"
    }

    @Serializable
    data object Tickets : Route{
        override val route: String = "Tickets"
    }

    @Serializable
    data object Profile: Route{
        override val route: String = "Profile"
    }

    @Serializable
    data object Login: Route{
        override val route = "Login"
    }


}