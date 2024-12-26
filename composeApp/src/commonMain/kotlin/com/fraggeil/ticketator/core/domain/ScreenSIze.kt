package com.fraggeil.ticketator.core.domain

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun rememberScreenSizeInfo(): ScreenSizeInfo

data class ScreenSizeInfo(
    val heightDp: Dp,
    val widthDp: Dp
)

fun ScreenSizeInfo.getUiType(): UiType {
    return when (widthDp){
        in 0.dp..600.dp -> UiType.COMPACT
        in 600.dp..840.dp -> UiType.MEDIUM
        else -> UiType.EXPANDED
    }
}

enum class UiType {
    COMPACT,
    MEDIUM,
    EXPANDED
}
