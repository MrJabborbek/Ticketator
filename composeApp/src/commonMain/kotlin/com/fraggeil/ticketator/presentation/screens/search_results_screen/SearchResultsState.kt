package com.fraggeil.ticketator.presentation.screens.search_results_screen

import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.model.Journey

data class SearchResultsState(
    val isLoading: Boolean = true,
    val isLoadingJourneys: Boolean = false,
    val error: String? = null,
    val filter: Filter? = null,
    val journeys: List<Journey> = emptyList(),
)
