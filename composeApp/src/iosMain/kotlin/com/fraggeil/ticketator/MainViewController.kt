package com.fraggeil.ticketator

import androidx.compose.ui.window.ComposeUIViewController
import com.fraggeil.ticketator.core.data.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}