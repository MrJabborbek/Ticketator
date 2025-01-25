package com.fraggeil.ticketator.domain.model

import com.fraggeil.ticketator.core.presentation.Language

data class AppSettings(
    val isLaunched: Boolean = false,
    val language: Language = Language.Uzbek,
)