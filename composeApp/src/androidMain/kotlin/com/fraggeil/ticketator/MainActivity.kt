package com.fraggeil.ticketator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmk.kmpnotifier.permission.permissionUtil
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val permissionUtil by permissionUtil()
        permissionUtil.askNotificationPermission()


        var isLoading by mutableStateOf(true)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                isLoading
            }
        }
        enableEdgeToEdge(
//            statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT,),
//            navigationBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
        )
        setContent {
            val viewModel: AppViewModel = koinViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()
            isLoading = state.isLoading
            App(
                appViewModel= viewModel
            )
        }
    }
}

