package com.fraggeil.ticketator.presentation.screens.login_screen

sealed interface LoginAction {
    data object OnLoginClicked : LoginAction
    data object OnBackClicked : LoginAction
    data object OnTermsAndConditionsClicked : LoginAction
    data class OnPhoneNumberChanged(val phoneNumber: String) : LoginAction

}