package com.fraggeil.ticketator.presentation.screens.profile_edit_screen

sealed interface ProfileEditOneTimeState {
    data object NavigateBack : ProfileEditOneTimeState
    data object NavigateToMain : ProfileEditOneTimeState
}