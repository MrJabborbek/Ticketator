package com.fraggeil.ticketator.presentation.screens.otp_screen

sealed interface OtpAction {
    data object OnResendButtonClicked: OtpAction
    data object OnEnterButtonClicked: OtpAction
    data object OnBackClicked: OtpAction
    data class OnPhoneNumberChanged(val number: String): OtpAction
    data class OnOtpChanged(val otp: String): OtpAction


}