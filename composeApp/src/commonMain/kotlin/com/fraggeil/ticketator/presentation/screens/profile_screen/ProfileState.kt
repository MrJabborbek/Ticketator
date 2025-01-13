package com.fraggeil.ticketator.presentation.screens.profile_screen

import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.domain.model.Profile

data class ProfileState(
    val isLoadingProfile: Boolean = true,
    val isLoadingLanguage: Boolean = true,
    val error: String? = null,
    val profile: Profile? = null,
    val isContactWithUsOpen: Boolean = false,
    val isLanguageSelectorOpen: Boolean = false,
    val currentLanguage: Language = Language.Uzbek
//    val profile: Profile? = null

)