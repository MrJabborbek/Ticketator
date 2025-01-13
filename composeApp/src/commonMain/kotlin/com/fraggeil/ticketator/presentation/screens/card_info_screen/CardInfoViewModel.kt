package com.fraggeil.ticketator.presentation.screens.card_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.repository.SendDataToCheckRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardInfoViewModel(
    private val repository: SendDataToCheckRepository
): ViewModel() {
    private var sendDataToCheckJob: Job? = null
    private var isInitialized = false
    private var timer = 0

    private val _oneTimeState = Channel<CardInfoOneTimeState>()
    val oneTimeState = _oneTimeState.receiveAsFlow()

    private val _state = MutableStateFlow(CardInfoState())
    val state = _state
        .onStart {
            if (!isInitialized) {
                isInitialized = true
                startTimer()
            }
        }
        .onEach {
            _state.update { it.copy(
                isAllDataValid = !it.isTimerEnd &&
                        it.cardNumber.length == 16 && it.cardNumber.all { d-> d.isDigit() } &&
                        it.cardValidUntil.length == 4 && it.cardValidUntil.all {d-> d.isDigit() }
            ) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    fun onAction(action: CardInfoAction){
        when(action) {
            CardInfoAction.OnBackClicked -> {}
            CardInfoAction.OnNextClicked -> {
               sendDataToCheck()
            }
            is CardInfoAction.OnCardNumberChanged -> {
                _state.update { it.copy(cardNumber = action.cardNumber) }
            }
            is CardInfoAction.OnCardValidUntilChanged -> {
                _state.update { it.copy(cardValidUntil = action.cardValidUntil) }
            }

            is CardInfoAction.OnJourneySelected -> {
                _state.update { it.copy(selectedJourney = action.journey, isLoading = true) }
                timer = 10
            }
        }
    }
    private fun sendDataToCheck() {
        if (_state.value.selectedJourney == null) return
        sendDataToCheckJob?.cancel()
        _state.update { it.copy(isSendingData = true) }
        sendDataToCheckJob = viewModelScope.launch {
            repository.sendDataToCheck(
                _state.value.selectedJourney!!,
                _state.value.cardNumber,
                _state.value.cardValidUntil,
            )
                .onSuccess { pair ->
                    _state.update { it.copy(isSendingData = false) }
                    _oneTimeState.send(CardInfoOneTimeState.NavigateToOtpPayment(pair.first, pair.second))
                }
                .onError {
                    _state.update { it.copy(isSendingData = false, error = it.error) }
                }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (timer >= 0) {
                val minutes = (timer / 60).toString()//.let { if (it.length == 1) "0$it" else it }
                val seconds = (timer % 60).toString().let { if(it.length == 1) "0$it" else it }
                _state.update { it.copy(timer = "$minutes:$seconds", isTimerEnd = timer == 0) }
                timer--
                delay(1000)
            }
        }
    }
}