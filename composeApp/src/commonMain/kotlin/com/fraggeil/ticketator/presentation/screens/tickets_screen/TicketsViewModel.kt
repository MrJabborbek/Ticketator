package com.fraggeil.ticketator.presentation.screens.tickets_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.domain.repository.TicketsRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class TicketsViewModel(
    private val repository: TicketsRepository
): ViewModel() {
    private var loadTicketsJob: Job? = null
    private var isInitialized = false
    private val _state = MutableStateFlow(TicketsState())
    val state = _state
        .onStart {
            if (!isInitialized) {
                loadTickets()
                isInitialized = true
            }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            TicketsState()
        )

    fun onAction(action: TicketsAction) {
        when (action) {
            is TicketsAction.OnDownloadTicketClicked -> {}
            is TicketsAction.OnShareTicketClicked -> {}
            is TicketsAction.OnTicketClicked -> {}
            TicketsAction.OnBuyTicketButtonClicked -> {}
            is TicketsAction.OnInitialTicketsSelected -> {
                _state.update { it.copy(initialTickets = action.tickets) }
            }

            TicketsAction.OnShowAllTicketsClicked -> {
                _state.update { it.copy(initialTickets = emptyList()) }

            }
        }
    }

    private fun loadTickets() {
        loadTicketsJob?.cancel()
        _state.update { it.copy(isLoading = true) }
        loadTicketsJob = repository.getTickets()
            .onStart {
                delay(Constants.FAKE_DELAY_TO_TEST)
            }
            .onEach { pair ->
                _state.update {
                    it.copy(
                        comingTickets = pair.second.sortedByDescending {t-> t.journey.timeStart },
                        pastTickets = pair.first.sortedByDescending {t-> t.journey.timeStart },
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}