package com.fraggeil.ticketator.core.presentation

import androidx.compose.runtime.Composable

@Composable
expect fun StatusBarColors(
    isDarkText: Boolean = false,
    isDarkNavigationButtons: Boolean = false,
)