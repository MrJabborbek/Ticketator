package com.fraggeil.ticketator.presentation.screens.post_screen

import com.fraggeil.ticketator.domain.model.Post


data class PostState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val post: Post? = null
)
