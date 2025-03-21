package com.fraggeil.ticketator.presentation.screens.passengers_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.isValidPhoneNumber
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.model.Passenger
import com.fraggeil.ticketator.domain.repository.CurrentUserInfoRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PassengersInfoViewModel(
    private val repository: CurrentUserInfoRepository
): ViewModel() {
    private var fetchUserInfoJob: Job? = null
    private var isInitialized = false
    private val _state = MutableStateFlow(PassengersInfoState())
    val state = _state
        .onStart {
            if (!isInitialized){
                isInitialized = true
                fetchUserInfo()
//                _state.update { it.copy(
//                    isLoading = false,
//                    selectedJourney = it.selectedJourney?.copy(passengers = listOf(1,15,51).map { seat -> Passenger(seat = seat) })
//                ) }
            }
        }
        .onEach {
            _state.update {
                it.copy(
                    isAllDataValid = it.selectedJourney?.passengers?.all { p-> p.name.isNotBlank() && p.phone.isValidPhoneNumber() } ?: false
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    private fun fetchUserInfo() {
        fetchUserInfoJob?.cancel()
        fetchUserInfoJob = viewModelScope.launch {
            async {
                repository.getUserInfo()
                    .onSuccess {
                        val suggestionUserNames = _state.value.suggestionUserNames.toMutableList()
                        val suggestionPhones = _state.value.suggestionPhones.toMutableList()
                        suggestionUserNames.add(it.name)
                        suggestionPhones.add(it.phoneNumber)
                        _state.update {
                            it.copy(
                                suggestionUserNames = suggestionUserNames.distinct(),
                                suggestionPhones = suggestionPhones.distinct()
                            )
                        }
                    }
            }
            async {
                repository.getOldTicketUsers()
                    .onSuccess {
                        val suggestionUserNames = _state.value.suggestionUserNames.toMutableList()
                        val suggestionPhones = _state.value.suggestionPhones.toMutableList()
                        suggestionUserNames.addAll(it.map {t -> t.name})
                        suggestionPhones.addAll(it.map {t -> t.phoneNumber})
                        _state.update {
                            it.copy(
                                suggestionUserNames = suggestionUserNames.distinct(),
                                suggestionPhones = suggestionPhones.distinct()
                            )
                        }
                    }
            }
        }
    }

    fun onAction(action: PassengersInfoAction){
        when(action){
            PassengersInfoAction.OnBackClicked -> {}
            PassengersInfoAction.OnPaymentClicked -> {}
            is PassengersInfoAction.OnPassengerNameEntered -> {
                _state.value.selectedJourney?.let { selectedJourney ->
                    val passengers = selectedJourney.passengers.toMutableList()
                    passengers.indexOfFirst { it.seat == action.seat }.takeIf { it != -1 }
                        ?.let { index ->
                            val passenger = passengers[index].copy(name = action.name)
                            val suggestionUserNames = _state.value.suggestionUserNames.toMutableList()
                            suggestionUserNames.remove(passenger.name)
                            passengers[index] = passenger
                            _state.update { it.copy(
                                selectedJourney = selectedJourney.copy(passengers = passengers),
                                suggestionUserNames = suggestionUserNames,

                            ) }
                        }
                }
            }
            is PassengersInfoAction.OnPassengerPhoneEntered -> {
                _state.value.selectedJourney?.let { selectedJourney ->
                    val passengers = selectedJourney.passengers.toMutableList()
                    passengers.indexOfFirst { it.seat == action.seat }.takeIf { it != -1 }
                        ?.let { index ->
                            val passenger = passengers[index].copy(phone = action.phone)
                            val suggestionPhones = _state.value.suggestionPhones.toMutableList()
                            suggestionPhones.remove(passenger.phone)
                            passengers[index] = passenger
                            _state.update { it.copy(
                                selectedJourney = selectedJourney.copy(passengers = passengers),
                                suggestionPhones = suggestionPhones
                            )
                            }
                        }
                }
            }
            is PassengersInfoAction.OnJourneySelected -> {
                val passengers = action.journey.selectedSeats.map { seat -> Passenger(seat = seat) }
                _state.update { it.copy(
                    isLoading = false,
                    selectedJourney = action.journey.copy(passengers = passengers)
                ) }
            }
        }
    }
}