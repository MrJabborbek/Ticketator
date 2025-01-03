package com.fraggeil.ticketator.presentation

import androidx.lifecycle.ViewModel
import com.fraggeil.ticketator.domain.model.Post
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedPostViewModel: ViewModel() {
    private val _state = MutableStateFlow<Post?>(null)
    val state = _state.asStateFlow()

    fun onSelectItem(item: Post?) {
        _state.value = item
    }

}