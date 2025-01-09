package com.fraggeil.ticketator.presentation.screens.card_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardInfoViewModel: ViewModel() {
    private var isInitialized = false
    private var timer = 0
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
            CardInfoAction.OnNextClicked -> {}
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