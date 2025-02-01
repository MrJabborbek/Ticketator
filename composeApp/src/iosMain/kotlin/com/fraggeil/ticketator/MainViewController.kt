package com.fraggeil.ticketator

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import com.fraggeil.ticketator.core.data.di.initKoin

object Settings{
    var isDarkTheme = mutableStateOf(false)
}

fun MainViewController(themeCallback: (String) -> Unit) = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    LaunchedEffect(Settings.isDarkTheme.value){
        themeCallback(if (Settings.isDarkTheme.value) "dark" else "light")
    }
    // Call the callback as needed, for example when a button is clicked
    // Trigger the callback to change the color scheme
    App(
//        isDarkStatusBar = {
//            if (it) themeCallback("dark") else themeCallback("light") //https://medium.com/@yatimistark/how-to-change-status-bar-style-easy-swift-ios-5b48c00abe82
//        }
    )
}