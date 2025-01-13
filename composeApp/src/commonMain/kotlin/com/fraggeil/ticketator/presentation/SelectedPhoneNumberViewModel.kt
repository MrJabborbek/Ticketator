package com.fraggeil.ticketator.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedPhoneNumberViewModel: ViewModel() {
    private val _state = MutableStateFlow<String?>(null)
    val state = _state.asStateFlow()

    fun onSelectItem(item: String?) {
        _state.value = item
    }

}