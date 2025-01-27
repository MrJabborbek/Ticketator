package com.fraggeil.ticketator

import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.data.di.initKoin
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
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



//fun main2() = application {
//    initKoin()
//    val appViewModel: AppViewModel = koinViewModel()
//    val state by appViewModel.state.collectAsStateWithLifecycle()
//    val isLoading = state.isLoading
//
//    if (state.isLoading){
//        //Show splash screen
////        Icon(
////            painter = painterResource(Res.drawable.ic_logo),
////            contentDescription = null
////        )
//        Window(
//            onCloseRequest = ::exitApplication,
//            title = Strings.AppName.value(),
//            state = WindowState(size = DpSize(422.dp, 938.dp) ),
////        icon = painterResource(Res.drawable.ic_logo)
//        ) {
//            App(
//                appViewModel = appViewModel
//            )
//        }
//    }else{
//        Window(
//            onCloseRequest = ::exitApplication,
//            title = Strings.AppName.value(),
//            state = WindowState(size = DpSize(422.dp, 938.dp) ),
////        icon = painterResource(Res.drawable.ic_logo)
//        ) {
//            App(
//                appViewModel = appViewModel
//            )
//        }
//    }
//}