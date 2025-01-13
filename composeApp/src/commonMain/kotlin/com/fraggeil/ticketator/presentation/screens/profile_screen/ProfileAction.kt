package com.fraggeil.ticketator.presentation.screens.profile_screen

import com.fraggeil.ticketator.core.presentation.Language


sealed interface ProfileAction {
    data object OnLogoutClicked : ProfileAction
    data object OnLoginClicked : ProfileAction
    data object OnBackClicked : ProfileAction
    data object OnProfileClicked: ProfileAction
    data object OnContactWithUsClicked: ProfileAction
    data object OnLanguageClicked: ProfileAction
    data object OnAboutAppClicked: ProfileAction
    data object OnTermsAndConditionsClicked: ProfileAction
    data object OnCallClicked: ProfileAction
    data object OnTelegramClicked: ProfileAction
    data object OnContactWithUsDismiss: ProfileAction
    data class OnLanguageSelected(val language: Language) : ProfileAction
    data object OnDismissLanguageSelector : ProfileAction

}