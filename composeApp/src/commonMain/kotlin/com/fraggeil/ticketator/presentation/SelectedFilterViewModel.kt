package com.fraggeil.ticketator.presentation

import androidx.lifecycle.ViewModel
import com.fraggeil.ticketator.domain.model.Filter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedFilterViewModel: ViewModel() {
    private val _state = MutableStateFlow<Filter?>(null)
    val state = _state.asStateFlow()

    fun onSelectItem(item: Filter?) {
        _state.value = item
    }

}