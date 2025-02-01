package com.fraggeil.ticketator.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.fraggeil.ticketator.Settings
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.zeroValue
import platform.CoreGraphics.CGRect
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIDocumentBrowserUserInterfaceStyleDark
import platform.UIKit.UINavigationBar
import platform.UIKit.UIStatusBarStyle
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.UIUserInterfaceStyle
import platform.UIKit.UIView
import platform.UIKit.UIViewController
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene
import platform.UIKit.statusBarManager

@OptIn(ExperimentalForeignApi::class)
@Composable
private fun statusBarView() = remember {
    val keyWindow: UIWindow? =
        UIApplication.sharedApplication.windows.firstOrNull { (it as? UIWindow)?.isKeyWindow() == true } as? UIWindow
    val tag = 3848245L // https://stackoverflow.com/questions/56651245/how-to-change-the-status-bar-background-color-and-text-color-on-ios-13

    keyWindow?.viewWithTag(tag)?.let {
        it
    } ?: run {
        val height =
            keyWindow?.windowScene?.statusBarManager?.statusBarFrame ?: zeroValue<CGRect>()
        val statusBarView = UIView(frame = height)
        statusBarView.tag = tag
        statusBarView.layer.zPosition = 999999.0
        keyWindow?.addSubview(statusBarView)
        statusBarView
    }
}


@Composable
actual fun StatusBarColors(
    isDarkText: Boolean,
    isDarkNavigationButtons: Boolean,
) {
    Settings.isDarkTheme.value = !isDarkText
//    val viewController = getCurrentViewController()
//    println("ISDARK: $isDarkText")
//    SideEffect {
//        setStatusBarAppearance(viewController, isDarkText)
//    }
//    val viewController = getCurrentController()
//    SideEffect {
//        println("ISDARK: $isDarkText")
//        val style = if (isDarkText) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent
//        setStatusBarStyle(viewController, style)
//    }
//    val statusBar = statusBarView() //TODO
//    SideEffect {
//        statusBar.backgroundColor = Color.Transparent.toUIColor()
//        UINavigationBar.appearance().backgroundColor = Color.Transparent.toUIColor()
//    }
}

fun getCurrentViewController(): UIViewController? {
//    val keyWindow = UIApplication.sharedApplication.connectedScenes
//        .filterIsInstance<UIWindowScene>()
//        .firstOrNull()?.windows?.firstOrNull { it.isKeyWindow }

    val keyWindow = UIApplication.sharedApplication.connectedScenes.filterIsInstance<UIWindowScene>().firstOrNull()?.keyWindow

    var topController = keyWindow?.rootViewController
    while (topController?.presentedViewController != null) {
        topController = topController.presentedViewController
    }

    return topController
}
fun setStatusBarAppearance(controller: UIViewController?, isDarkText: Boolean) {
    controller?.setNeedsStatusBarAppearanceUpdate()

    controller?.overrideUserInterfaceStyle = if (isDarkText) {
        UIUserInterfaceStyle.UIUserInterfaceStyleLight
    } else {
        UIUserInterfaceStyle.UIUserInterfaceStyleDark
    }
}


fun getCurrentController(): UIViewController? {
    val keyWindow = UIApplication.sharedApplication.keyWindow
    return keyWindow?.rootViewController
}

fun setStatusBarStyle(controller: UIViewController?, style: UIStatusBarStyle) {
    controller?.setNeedsStatusBarAppearanceUpdate()
    controller?.overrideUserInterfaceStyle = when (style) {
        UIStatusBarStyleDarkContent -> UIUserInterfaceStyle.UIUserInterfaceStyleLight
        else -> UIUserInterfaceStyle.UIUserInterfaceStyleDark
    }
}


private fun Color.toUIColor(): UIColor = UIColor(
    red = this.red.toDouble(),
    green = this.green.toDouble(),
    blue = this.blue.toDouble(),
    alpha = this.alpha.toDouble()
)