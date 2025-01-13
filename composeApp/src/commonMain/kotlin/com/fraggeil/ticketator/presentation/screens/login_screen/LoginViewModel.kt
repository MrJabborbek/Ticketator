package com.fraggeil.ticketator.presentation.screens.login_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.repository.LoginRepository
import com.fraggeil.ticketator.core.domain.isValidPhoneNumber
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
): ViewModel() {
    private val _oneTimeState = Channel<LoginOneTimeState>()
    val oneTimeState = _oneTimeState.receiveAsFlow()

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction){
        when(action){
            LoginAction.OnBackClicked -> {}
            LoginAction.OnLoginClicked -> viewModelScope.launch{
                if (state.value.phoneNumber.isValidPhoneNumber()){
                    _state.update { it.copy(isLoading = true) }
                    loginRepository.sendOtp(_state.value.phoneNumber)
                        .onSuccess { token ->
                            _oneTimeState.send(LoginOneTimeState.NavigateToOtpScreen(_state.value.phoneNumber, token))
                            _state.update { it.copy(isLoading = false) }
                        }
                        .onError {
                            _state.update { it.copy(error = "Error while sending otp") }//TODO
                        }
                }else{
                    _state.update { it.copy(error = "Invalid phone number") }//TODO
                }
            }
            LoginAction.OnTermsAndConditionsClicked -> {}
            is LoginAction.OnPhoneNumberChanged -> {
                if (_state.value.isLoading) return
                if (action.phoneNumber.any { !it.isDigit() && it != '+' }){
                    return
                }
                val phone = if (action.phoneNumber.length <= 3){
                    "+998"
                } else action.phoneNumber
                _state.value = _state.value.copy(phoneNumber = phone)
            }

        }
    }
}