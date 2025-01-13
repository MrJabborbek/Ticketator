package com.fraggeil.ticketator.presentation.screens.select_seat_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.domain.repository.SelectSeatRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SelectSeatViewModel(
    private val repository: SelectSeatRepository
): ViewModel() {
    private var observeJourneyJob: Job? = null

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
                    _state.value = _state.value.copy(selectedJourney = selectedJourney.copy(selectedSeats = selectedSeats.sortedBy { t -> t }))

                    selectSeat(action.seat)
                }
            }

            is SelectSeatAction.OnJourneySelected -> {
                observeJourneyJob?.cancel()
                observeJourneyJob = repository.observeJourneyById(action.journey.id)
                    .onStart {
                        delay(Constants.FAKE_DELAY_TO_TEST)
                    }
                    .onEach { journey ->
                        _state.update { it.copy(selectedJourney = journey, isLoading = false) }
                    }
                    .launchIn(viewModelScope)
            }
        }
    }
    private fun selectSeat(seat: Int){
        //Reserving in server and refetch
//        selectSeatJob?.cancel()
//        selectSeatJob =
        viewModelScope.launch {
            repository.onSeatSelected(seat = seat, journeyId = _state.value.selectedJourney?.id?: return@launch)
        }
    }
}