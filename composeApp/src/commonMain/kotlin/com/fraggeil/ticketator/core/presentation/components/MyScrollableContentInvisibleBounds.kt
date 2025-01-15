package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun MyScrollableContentInvisibleBoundsBox(
    modifier: Modifier = Modifier,
    size: Dp,
    top: Boolean = true,
    bottom: Boolean = top,
    left: Boolean = true,
    right: Boolean = left,
    color: Color = Color.White,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ){
        content()
        if (top) {
            Box(
                modifier = Modifier.align(Alignment.TopCenter).fillMaxWidth().height(size)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                color,
                                Color.Transparent,
                            ),
                        )
                    )
            )
        }
        if (bottom) {
            Box(
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth().height(size)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                color,
                            ),
                        )
                    )
            )
        }
        if (left) {
            Box(
                modifier = Modifier.align(Alignment.CenterStart).fillMaxHeight().width(size)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                color,
                                Color.Transparent,
                            ),
                        )
                    )
            )
        }
        if (right) {
            Box(
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight().width(size)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                color,
                            ),
                        )
                    )
            )
        }

    }
}