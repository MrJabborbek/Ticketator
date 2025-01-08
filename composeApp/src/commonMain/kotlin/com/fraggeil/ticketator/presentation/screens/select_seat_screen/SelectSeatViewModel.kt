package com.fraggeil.ticketator.presentation.screens.select_seat_screen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SelectSeatViewModel: ViewModel() {
    private val _state = MutableStateFlow(SelectSeatState())
    val state = _state.asStateFlow()

    fun onAction(action: SelectSeatAction){
        when(action){
            SelectSeatAction.OnBackClicked -> {}
            SelectSeatAction.OnNextClicked -> {}
            is SelectSeatAction.OnSeatClicked -> {
                if (_state.value.selectedJourney?.seatsAvailable?.contains(action.seat) != true) {
                    return
                }
                _state.value.selectedJourney?.let { selectedJourney ->
                    val selectedSeats = selectedJourney.selectedSeats.toMutableList()
                    if (action.seat in selectedSeats) {
                        selectedSeats.remove(action.seat)
                    } else {
                        selectedSeats.add(action.seat)
                    }
                    _state.update { it.copy(selectedJourney = selectedJourney.copy(selectedSeats = selectedSeats.sortedBy { t -> t })) }
                }
            }

            is SelectSeatAction.OnJourneySelected -> {
                _state.update { it.copy(selectedJourney = action.journey, isLoading = false) } //TODO: load seats
            }
        }
    }
}