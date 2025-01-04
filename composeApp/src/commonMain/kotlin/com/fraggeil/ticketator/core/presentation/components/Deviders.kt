package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun DottedHorizontalDivider(
    modifier: Modifier = Modifier,
    color: Color = Color.Black,
    dotSize: Dp = 3.dp,
    spaceBetweenDots: Dp = 4.dp
){
    val dotSizePx = with(LocalDensity.current) { dotSize.toPx() }
    val spaceBetweenDotsPx = with(LocalDensity.current) { spaceBetweenDots.toPx() }

    Canvas(modifier = modifier.fillMaxWidth()) {
        var startX = 0f
        while (startX < size.width) {
            drawCircle(
                color = color,
                radius = dotSizePx / 2,
                center = Offset(startX + dotSizePx / 2, size.height / 2)
            )
            startX += dotSizePx + spaceBetweenDotsPx
        }
    }
}

@Composable
fun DashedLineDivider(
    modifier: Modifier = Modifier.fillMaxWidth(),
    color: Color = Color.Black,
    dashWidth: Dp = 6.dp,
    dashSpacing: Dp = 4.dp,
    lineHeight: Dp = 1.dp
) {
    val dashWidthPx = with(LocalDensity.current) { dashWidth.toPx() }
    val dashSpacingPx = with(LocalDensity.current) { dashSpacing.toPx() }
    val lineHeightPx = with(LocalDensity.current) { lineHeight.toPx() }

    Canvas(modifier = modifier.fillMaxWidth()) {
        var startX = 0f
        while (startX < size.width) {
            // Draw a single dash
            drawRect(
                color = color,
                topLeft = Offset(startX, (size.height - lineHeightPx) / 2),
                size = androidx.compose.ui.geometry.Size(dashWidthPx, lineHeightPx)
            )
            // Move the start position to the next dash position
            startX += dashWidthPx + dashSpacingPx
        }
    }
}