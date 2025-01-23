package com.fraggeil.ticketator.presentation.screens.profile_edit_screen

import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fraggeil.ticketator.core.domain.result.onError
import com.fraggeil.ticketator.core.domain.result.onSuccess
import com.fraggeil.ticketator.domain.model.Profile
import com.fraggeil.ticketator.domain.repository.ProfileRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileEditViewModel(
    private val profileRepository: ProfileRepository,
    private val snackbarHostState: SnackbarHostState
): ViewModel() {
    private var observeCurrentUserJob: Job? = null
    private var updateCurrentUserJob: Job? = null
    private var deleteCurrentUserJob: Job? = null
    private var profile : Profile? = null

    private val _oneTimeState = Channel<ProfileEditOneTimeState>()
    val oneTimeState = _oneTimeState.receiveAsFlow()

    private var isInitialized = false
    private val _state = MutableStateFlow(ProfileEditState())
    val state = _state
        .onStart {
            if (isInitialized.not()) {
                isInitialized = true
                observeCurrentProfile()
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    fun onAction(action: ProfileEditAction){
        when(action){
            ProfileEditAction.OnBackClicked -> {}
            ProfileEditAction.OnDeleteProfileClicked -> {
                deleteCurrentProfile()
            }
            ProfileEditAction.OnSaveClicked -> {
                profile?.copy(
                    firstName = state.value.firstName,
                    lastName = state.value.lastName,
                    middleName = state.value.middleName
                )?.let { updateCurrentProfile(it) }
            }
            is ProfileEditAction.OnFirstNameChanged -> {
                _state.update { it.copy(firstName = action.firstName)}
            }
            is ProfileEditAction.OnLastNameChanged -> {
                _state.update { it.copy(lastName = action.lastName)}
            }
            is ProfileEditAction.OnMiddleNameChanged -> {
                _state.update { it.copy(middleName = action.middleName)}

            }

        }
    }
    private fun observeCurrentProfile() {
        observeCurrentUserJob?.cancel()
        _state.update { it.copy(isLoadingProfile = true) }
        observeCurrentUserJob = viewModelScope.launch {
            profileRepository.getCurrentProfile()
                .onSuccess { user ->
                    println("USERRR SUCCESS")
                    profile = user
                    _state.update { it.copy(
                        firstName = user.firstName,
                        lastName = user.lastName,
                        middleName = user.middleName,
                        phoneNumber = user.phoneNumber,
                        isLoadingProfile = false
                    ) }
                }
                .onError {
                    println("USERRR Errorr")

                    _state.update { it.copy(isLoadingProfile = false, error = "Error") }
                    snackbarHostState.showSnackbar("Error while loading profile")
                }
        }
    }
    private fun updateCurrentProfile(profile: Profile) {
        updateCurrentUserJob?.cancel()
        updateCurrentUserJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingDeleteOrUpdate = true) }
            profileRepository.updateProfile(profile)
                .onSuccess {
                    _state.update { it.copy(isLoadingDeleteOrUpdate = false) }
                    _oneTimeState.send(ProfileEditOneTimeState.NavigateBack)
                }
                .onError {
                    _state.update { it.copy(isLoadingDeleteOrUpdate = false, error = "Error") }
                }
        }
    }

    private fun deleteCurrentProfile() {
        deleteCurrentUserJob?.cancel()
        deleteCurrentUserJob = viewModelScope.launch {
            _state.update { it.copy(isLoadingDeleteOrUpdate = true) }
            profileRepository.deleteProfile()
                .onSuccess {
                    _state.update { it.copy(isLoadingDeleteOrUpdate = false) }
                    _oneTimeState.send(ProfileEditOneTimeState.NavigateToMain)
                }
                .onError {
                    _state.update { it.copy(isLoadingDeleteOrUpdate = false, error = "Error") }
                }
        }
    }
}