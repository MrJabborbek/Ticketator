package com.fraggeil.ticketator.presentation.screens.otp_payment_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.phoneNumberFormatting.formatPhoneNumber
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.domain.repository.OtpPaymentRepository
import com.fraggeil.ticketator.presentation.screens.otp_screen.OtpOneTimeState
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpPaymentViewModel(
    private val repository: OtpPaymentRepository
): ViewModel() {
    private var checkOtpJob: Job? = null
    private var token: String = ""

    private val _oneTimeState = Channel<OtpPaymentOneTimeState>()
    val oneTimeState = _oneTimeState.receiveAsFlow()

    private var isInitialized = false
    private val _state = MutableStateFlow(OtpPaymentState())
    val state = _state
        .onStart {
            if (!isInitialized) {
                isInitialized = true
                startTimer(60)
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = kotlinx.coroutines.flow.SharingStarted.WhileSubscribed(5000),
            initialValue = OtpPaymentState()
        )
    
    fun onAction(action: OtpPaymentAction){
        when(action){
            OtpPaymentAction.OnBackClicked -> {}
            OtpPaymentAction.OnEnterButtonClicked -> {
                checkOtp(token, state.value.otp)
            }
            is OtpPaymentAction.OnPhoneNumberChanged -> {
                token = action.token
                _state.update { it.copy(phoneNumber = action.number.formatPhoneNumber(isSecret = true)) }
            }
            OtpPaymentAction.OnResendButtonClicked -> viewModelScope.launch {
                repository.resendOtp(token)
                    .onSuccess {
                        _state.update { it.copy(isResendEnabled = false) }
                        startTimer(240)
                    }
                    .onError {
                        _state.update { it.copy(error = Strings.ErrorSendingOTP.toString()) }//TODO
                    }
            }

            is OtpPaymentAction.OnOtpChanged -> {
                if (action.otp.any { !it.isDigit() }){
                    return
                }
                if (action.otp.length <= 5) {
                    _state.update { it.copy(otp = action.otp, isErrorMode = false) }
                }
                if (action.otp.length == 5) {
                    checkOtp(token, action.otp)
                }
            }
        }
    }
    private fun checkOtp(token: String, otp: String){
        checkOtpJob?.cancel()
        if (otp.length != 5) {
            _state.update { it.copy(isErrorMode = true) }
            return
        }
        checkOtpJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.verifyOtp(token, otp)
                .onSuccess { tickets ->
                    _state.update { it.copy(isLoading = false) }
                    _oneTimeState.send(OtpPaymentOneTimeState.NavigateToTickets(tickets))
                }
                .onError {
                    _state.update { it.copy(isLoading = false, error = Strings.InvalidOTP.value(), isErrorMode = true) }
                }
        }
    }

    private fun startTimer(initialTime: Int){
        viewModelScope.launch {
            (0..initialTime).reversed().forEach { value ->
                val time = if (value == 0) "" else (value).toString()
                _state.update { it.copy(timer = time, isResendEnabled = value == 0) }
                delay(1000)
            }
        }
    }
}
