package com.fraggeil.ticketator.presentation.screens.card_info_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardInfoViewModel: ViewModel() {
    private var isInitialized = false
    private var timer = 8*60
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
                isAllDataValid = timer > 0 &&
                        it.cardNumber.isNotBlank() &&
                        it.cardValidUntil.isNotBlank()
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
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (timer > 0) {
                val minutes = timer / 60
                val seconds = timer % 60
                _state.update { it.copy(timer = "$minutes:$seconds") }
                timer--
                delay(1000)
            }
        }
    }
}