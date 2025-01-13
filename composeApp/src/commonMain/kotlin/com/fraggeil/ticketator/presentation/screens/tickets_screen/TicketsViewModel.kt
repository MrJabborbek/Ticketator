package com.fraggeil.ticketator.presentation.screens.tickets_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class TicketsViewModel: ViewModel() {
    private val _state = MutableStateFlow(TicketsState())
    val state = _state.asStateFlow()

    fun onAction(action: TicketsAction){
        when(action){
            is TicketsAction.OnDownloadTicketClicked -> {}
            is TicketsAction.OnShareTicketClicked -> {}
            is TicketsAction.OnTicketClicked -> {}
            TicketsAction.OnBuyTicketButtonClicked -> {}
        }
    }
}