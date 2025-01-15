package com.fraggeil.ticketator.presentation

import androidx.lifecycle.ViewModel
import com.fraggeil.ticketator.domain.model.Ticket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedTicketsViewModel: ViewModel() {
    private val _state = MutableStateFlow<List<Ticket>?>(null)
    val state = _state.asStateFlow()

    fun onSelectItem(item: List<Ticket>?) {
        _state.value = item
    }

}