package com.fraggeil.ticketator.presentation.screens.home_screen

import com.fraggeil.ticketator.domain.model.Filter

sealed interface HomeOneTimeState {
    data class NavigateToSearchResults(val filter: Filter): HomeOneTimeState
}