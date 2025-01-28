package com.fraggeil.ticketator.presentation.screens.search_results_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.model.Filter
import com.fraggeil.ticketator.domain.repository.SearchResultsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchResultsViewModel(
    private val repository: SearchResultsRepository
): ViewModel() {
    private var isInitialized = false
    private var fetchResultsJob: Job? = null
    private val _state = MutableStateFlow(SearchResultsState())
    val state = _state
        .onStart {
            if (!isInitialized) {
                isInitialized = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _state.value
        )

    fun onAction(action: SearchResultsAction) {
        when (action) {
            SearchResultsAction.OnBackClicked -> {}
            is SearchResultsAction.OnJourneyClicked -> {}
            is SearchResultsAction.OnDateClicked -> {
                val newFilter = _state.value.filter?.copy(dateGo = action.date)
                _state.update { it.copy(filter = newFilter) }
                fetchResults(newFilter!!)
            }

            is SearchResultsAction.OnFilterSelected -> {
                _state.update { it.copy(filter = action.filter) }
                fetchResults(action.filter)
            }

            SearchResultsAction.OnFilterClicked -> {}
        }
    }

    private fun fetchResults(filter: Filter) {
        fetchResultsJob?.cancel()
        _state.update { it.copy(isLoadingJourneys = true) }
        fetchResultsJob = viewModelScope.launch {
            repository.getJourneysByFilter(filter)
                .onSuccess { list ->
                    _state.update {
                        it.copy(
                            isLoadingJourneys = false,
                            journeys = list
                        )
                    }
                }
                .onError { e ->
                    _state.update { it.copy(isLoadingJourneys = false, error = e.toString()) }
                }
        }
    }
}