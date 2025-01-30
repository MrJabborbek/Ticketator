package com.fraggeil.ticketator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmk.kmpnotifier.permission.permissionUtil
import org.koin.androidx.compose.koinViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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
            Box(modifier = Modifier.fillMaxSize()){

                Button(
                    modifier = Modifier.align(Alignment.Center),
                    onClick = {
                        val intent = Intent()
                        intent.action = "com.fraggeil.ecommerce.PROCESS_REQUEST"
                        intent.putExtra("number1", 2)
                        intent.putExtra("number2", 2)
                        intent.setPackage("com.fraggeil.ecommerce") // Specify the target app package
                        sendBroadcast(intent).also { println("BROADCASTTT: sent from A") }
                    }
                ) { }
            }
//            App(
//                appViewModel= viewModel
//            )
        }
    }

    // BroadcastReceiver to receive result from App B
    class ResultReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val result = intent?.getIntExtra("result", -1) ?: -1
            val activity = context as? MainActivity
            println("BROADCASTTT: Received Result: $result")
        }
    }
}

