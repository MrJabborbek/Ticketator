package com.fraggeil.ticketator.presentation.screens.profile_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.Constants
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.repository.ProfileRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val profileRepository: ProfileRepository
): ViewModel() {
    private var observeCurrentUserJob: Job? = null
    private var observeCurrentLangJob: Job? = null

    private val _oneTimeState = Channel<ProfileOneTimeState>()
    val oneTimeState = _oneTimeState.receiveAsFlow()

    private var isInitialized = false
    private val _state = MutableStateFlow(ProfileState())
    val state = _state
        .onStart {
            if (isInitialized.not()) {
                isInitialized = true
                observeCurrentUser()
                observerCurrentLang()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ProfileState()
        )

    fun onAction(action: ProfileAction){
        when(action){
            ProfileAction.OnAboutAppClicked -> {}
            ProfileAction.OnBackClicked -> {}
            ProfileAction.OnCallClicked -> {
                _state.value = _state.value.copy(
                    isContactWithUsOpen = false
                )
            }
            ProfileAction.OnContactWithUsClicked -> {
                _state.value = _state.value.copy(
                    isContactWithUsOpen = true
                )
            }
            ProfileAction.OnContactWithUsDismiss -> {
                _state.value = _state.value.copy(
                    isContactWithUsOpen = false
                )
            }
            ProfileAction.OnLanguageClicked -> {
                _state.value = _state.value.copy(
                    isLanguageSelectorOpen = true
                )
            }
            ProfileAction.OnLoginClicked -> {}
            ProfileAction.OnLogoutClicked -> viewModelScope.launch {
                _state.update { it.copy(isLoadingProfile = true) }
                profileRepository.logout()
                    .onSuccess {
                        _oneTimeState.send(ProfileOneTimeState.NavigateToMain)
                        _state.update { it.copy(profile = null, isLoadingProfile = false) }
                    }
            }
            ProfileAction.OnProfileClicked -> {}
            ProfileAction.OnTelegramClicked -> {
                _state.value = _state.value.copy(
                    isContactWithUsOpen = false
                )
            }
            ProfileAction.OnTermsAndConditionsClicked -> {}
            ProfileAction.OnDismissLanguageSelector -> {
                _state.value = _state.value.copy(
                    isLanguageSelectorOpen = false
                )
            }
            is ProfileAction.OnLanguageSelected -> {
                _state.value = _state.value.copy(
                    isLanguageSelectorOpen = false
                )
                viewModelScope.launch {
                    profileRepository.changeAppLanguage(action.language)
                }
            }
        }
    }

    private fun observeCurrentUser() {
        observeCurrentUserJob?.cancel()
        _state.update { it.copy(isLoadingProfile = true) }
        observeCurrentUserJob =
            profileRepository.getCurrentProfileRealtime()
                .onStart {
                    delay(Constants.FAKE_DELAY_TO_TEST)
                }
                .onEach { profile ->
                    _state.update { it.copy(profile = profile, isLoadingProfile = false) }
                }
                .launchIn(viewModelScope)

    }
    private fun observerCurrentLang() {
        observeCurrentLangJob?.cancel()
        _state.update { it.copy(isLoadingLanguage = true) }
        observeCurrentLangJob =
            profileRepository.getCurrentAppLanguage()
                .onStart {
                    delay(Constants.FAKE_DELAY_TO_TEST)
                }
                .onEach { lang ->
                    _state.update { it.copy(currentLanguage = lang, isLoadingLanguage = false) }
                }.launchIn(viewModelScope)
    }
}