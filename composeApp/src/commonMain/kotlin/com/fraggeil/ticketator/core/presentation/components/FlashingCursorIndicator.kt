package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.theme.LightGray

@Composable
fun FlashingCursorIndicator(indicatorColor: Color = LightGray) {
    // Infinite transition to control the cursor's flashing
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // Box representing the cursor
    Box(
        modifier = Modifier
            .size(width = 2.dp, height = 24.dp) // Cursor dimensions
            .background(indicatorColor.copy(alpha = alpha)) // Flashes by changing opacity
    )
}