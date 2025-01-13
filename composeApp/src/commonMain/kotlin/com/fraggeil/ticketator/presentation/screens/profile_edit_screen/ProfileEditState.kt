package com.fraggeil.ticketator.presentation.screens.profile_edit_screen

data class ProfileEditState(
    val isLoadingProfile: Boolean = true,
    val isLoadingDeleteOrUpdate: Boolean = false,
    val error: String? = null,
    val firstName: String = "",
    val lastName: String = "",
    val middleName: String = "",
    val phoneNumber: String = "",
)
