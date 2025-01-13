package com.fraggeil.ticketator.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedPhoneNumberAndTokenViewModel: ViewModel() {
    private val _state = MutableStateFlow<Pair<String, String>?>(null)
    val state = _state.asStateFlow()

    fun onSelectItem(item: Pair<String, String>?) {
        _state.value = item
    }

}