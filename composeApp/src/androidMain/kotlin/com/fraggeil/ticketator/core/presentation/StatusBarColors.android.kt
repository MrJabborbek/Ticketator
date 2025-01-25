package com.fraggeil.ticketator.core.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun StatusBarColors(
    isDarkText: Boolean,
    isDarkNavigationButtons: Boolean,
){
    val activity = LocalView.current.context as? Activity
    SideEffect {
        activity?.window?.statusBarColor = Color.Transparent.toArgb()
        WindowCompat.getInsetsController(activity?.window!!, activity.window.decorView).isAppearanceLightStatusBars = isDarkText
    }
//    val sysUiController = rememberSystemUiController()
//    SideEffect {
//        sysUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = false)
//        sysUiController.setNavigationBarColor(color = Color.Transparent, darkIcons = true)
//    }
}