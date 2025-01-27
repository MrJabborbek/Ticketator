package com.fraggeil.ticketator

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import com.fraggeil.ticketator.core.data.di.initKoin
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.White
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ticketator_logo
import java.awt.Toolkit


class DesktopViewModelStoreOwner : ViewModelStoreOwner {
    private val store = ViewModelStore()
    override val viewModelStore: ViewModelStore
        get() = store
}

fun main() {
    initKoin()
    application {
        val viewModelStoreOwner = remember { DesktopViewModelStoreOwner() }
        CompositionLocalProvider(LocalViewModelStoreOwner provides viewModelStoreOwner) {
            val viewModel: AppViewModel = koinViewModel()
            val state by viewModel.state.collectAsState()

            if (state.isLoading) {
                SplashScreen()
            } else {
                val screenSize = Toolkit.getDefaultToolkit().screenSize
                val windowWidth = 422.dp
                val windowHeight = 938.dp
                Window(
                    onCloseRequest = ::exitApplication,
                    title = Strings.AppName.value(),
                    state = WindowState(
                        size = DpSize(windowWidth, windowHeight),
                        position = androidx.compose.ui.window.WindowPosition(
                            (screenSize.width.dp - windowWidth) / 2,  // Center X
                            (screenSize.height.dp - windowHeight) / 2 // Center Y
                        )
                    ),
                    icon = painterResource(Res.drawable.ticketator_logo)
                ) {
                    App(
                        appViewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SplashScreen() {
    val screenSize = Toolkit.getDefaultToolkit().screenSize
    val windowWidth = 400.dp
    val windowHeight = 250.dp

    Window(
        onCloseRequest = {},
        state = WindowState(
            size = DpSize(windowWidth, windowHeight),
            position = androidx.compose.ui.window.WindowPosition(
                (screenSize.width.dp - windowWidth) / 2,  // Center X
                (screenSize.height.dp - windowHeight) / 2 // Center Y
            )
        ),
        undecorated = true,
        resizable = false,
        alwaysOnTop = true
    ) {
        Box(
            modifier = Modifier.fillMaxSize().background(BG_White),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Image(
                    modifier = Modifier.size(100.dp),
                    painter = painterResource(Res.drawable.ticketator_logo),
                    contentDescription = "Splash Logo")
                Text("Loading...", fontSize = 16.sp, color = BlueDark, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}