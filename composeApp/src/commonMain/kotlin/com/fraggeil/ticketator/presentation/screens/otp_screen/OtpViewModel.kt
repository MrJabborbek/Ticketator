package com.fraggeil.ticketator.presentation.screens.otp_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.phoneNumberFormatting.formatPhoneNumber
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.repository.LoginRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OtpViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {
    private var checkOtpJob: Job? = null

    private val _oneTimeState = Channel<OtpOneTimeState>()
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
    private var phoneNumber = ""
    private var token = ""
    
    fun onAction(action: OtpAction){
        when(action){
            OtpAction.OnBackClicked -> {}
            OtpAction.OnEnterButtonClicked -> {
                checkOtp(state.value.otp)
            }
            is OtpAction.OnPhoneNumberChanged -> {
                phoneNumber = action.number
                token = action.token
                _state.update { it.copy(phoneNumber = action.number.formatPhoneNumber(isSecret = true)) }
            }
            OtpAction.OnResendButtonClicked -> viewModelScope.launch {
//                loginRepository.sendOtp(phoneNumber) TODO
//                    .onSuccess {
//                        _state.update { it.copy(isResendEnabled = false) }
//                        startTimer(240)
//                    }
//                    .onError {
//                        _state.update { it.copy(error = "Error while sending otp") }//TODO
//                    }
            }

            is OtpAction.OnOtpChanged -> {
                if (action.otp.any { !it.isDigit() }){
                    return
                }
                if (action.otp.length <= 5) {
                    _state.update { it.copy(otp = action.otp, isErrorMode = false) }
                }
                if (action.otp.length == 5) {
                    checkOtp(action.otp)
                }
            }
        }
    }
    private fun checkOtp(otp: String){
        checkOtpJob?.cancel()
        if (otp.length != 5) {
            _state.update { it.copy(isErrorMode = true) }
            return
        }
        checkOtpJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginRepository.verifyOtp(token, phoneNumber, otp)
                .onSuccess {
                    _state.update { it.copy(isLoading = false) }
                    _oneTimeState.send(OtpOneTimeState.NavigateToHome)
                }
                .onError {
                    _state.update { it.copy(isLoading = false, error = "Invalid OTP", isErrorMode = true) }
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
