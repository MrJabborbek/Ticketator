package com.fraggeil.ticketator.presentation.screens.profile_edit_screen

sealed interface ProfileEditAction {
    data object OnSaveClicked : ProfileEditAction
    data object OnBackClicked : ProfileEditAction
    data object OnDeleteProfileClicked : ProfileEditAction
    data class OnFirstNameChanged(val firstName: String) : ProfileEditAction
    data class OnLastNameChanged(val lastName: String) : ProfileEditAction
    data class OnMiddleNameChanged(val middleName: String) : ProfileEditAction

}