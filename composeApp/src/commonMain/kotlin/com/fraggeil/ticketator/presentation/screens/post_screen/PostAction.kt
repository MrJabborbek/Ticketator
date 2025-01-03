package com.fraggeil.ticketator.presentation.screens.post_screen

import com.fraggeil.ticketator.domain.model.Post


sealed interface PostAction {
    data object OnBackClicked: PostAction
    data class OnSelectedPostChange(val post: Post): PostAction
}