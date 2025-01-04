package com.fraggeil.ticketator.presentation

import androidx.lifecycle.ViewModel
import com.fraggeil.ticketator.domain.model.Journey
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedJourneyViewModel: ViewModel() {
    private val _state = MutableStateFlow<Journey?>(null)
    val state = _state.asStateFlow()

    fun onSelectItem(item: Journey?) {
        _state.value = item
    }

}