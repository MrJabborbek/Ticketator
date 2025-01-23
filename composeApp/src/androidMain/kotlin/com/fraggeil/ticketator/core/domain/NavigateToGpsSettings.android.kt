package com.fraggeil.ticketator.core.domain

import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun NavigateToGpsSettings() {
    val context = LocalContext.current
    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
    context.startActivity(intent)
}