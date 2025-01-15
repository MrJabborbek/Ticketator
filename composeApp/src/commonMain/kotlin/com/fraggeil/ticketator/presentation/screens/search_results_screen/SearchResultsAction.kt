package com.fraggeil.ticketator.presentation.screens.search_results_screen

import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.Journey

sealed interface SearchResultsAction {
    data object OnBackClicked : SearchResultsAction
    data object OnFilterClicked : SearchResultsAction
    data class OnJourneyClicked(val journey: Journey) : SearchResultsAction
    data class OnDateClicked(val date: Long) : SearchResultsAction
    data class OnFilterSelected(val filter: Filter) : SearchResultsAction
}