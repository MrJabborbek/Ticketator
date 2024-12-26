package com.fraggeil.ticketator

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.fraggeil.ticketator.core.data.di.initKoin
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = Strings.AppName.value(),
        state = WindowState(size = DpSize(422.dp, 938.dp) ),
//        icon = painterResource(Res.drawable.ic_logo)
    ) {
        App()
    }
}