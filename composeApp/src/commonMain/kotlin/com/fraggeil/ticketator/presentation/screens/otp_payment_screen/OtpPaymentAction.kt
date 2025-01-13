package com.fraggeil.ticketator.presentation.screens.otp_payment_screen

sealed interface OtpPaymentAction {
    data object OnResendButtonClicked: OtpPaymentAction
    data object OnEnterButtonClicked: OtpPaymentAction
    data object OnBackClicked: OtpPaymentAction
    data class OnPhoneNumberChanged(val number: String): OtpPaymentAction
    data class OnOtpChanged(val otp: String): OtpPaymentAction


}