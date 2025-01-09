package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.theme.BG_White
import kotlin.math.abs

@Composable
fun TicketShape(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    isLoading: Boolean = false,
    cornerSize: Float = 12f,
    backgroundColor: Color = BG_White,
    contents: List<@Composable BoxScope.() -> Unit>,
){
    val interactionSource = remember { if (onClick != null && !isLoading) MutableInteractionSource()  else null }
    val localIndication = if (onClick != null && !isLoading) LocalIndication.current else null

    Column(
        modifier = modifier
    ){
        contents.forEachIndexed { index, content ->
            Box(
                modifier = Modifier.fillMaxWidth()
                    .clip(
                        CustomRoundedCornerShape(
                            topStart = if (index == 0) cornerSize.dp else (-cornerSize).dp,
                            bottomStart = if(index == contents.lastIndex) cornerSize.dp else (-cornerSize).dp
                        )
                    )
                    .then(
                        if (onClick != null && !isLoading) Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = localIndication,
                            onClick = onClick
                        ) else Modifier
                    )
                    .shimmerLoadingAnimation(
                        isLoadingCompleted = !isLoading,
                        shimmerStyle = ShimmerStyle.Custom
                    )
                    .background(
                        backgroundColor,
                        CustomRoundedCornerShape(
                            topStart = if (index == 0) cornerSize.dp else (-cornerSize).dp,
                            bottomStart = if(index == contents.lastIndex) cornerSize.dp else (-cornerSize).dp
                        )
                    )
            ){
                content()
            }
            if (index != contents.lastIndex) {
                DashedLineDivider(modifier = Modifier.padding(horizontal = cornerSize.dp))
            }
        }
    }
}

class SpeechBubbleShape(
    private val cornerRadius: Dp = 15.dp,
    private val tipSize: Dp = 15.dp
): Shape
{

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val tipSize = with(density) { tipSize.toPx() }
        val cornerRadius = with(density) { cornerRadius.toPx() }
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = tipSize,
                    top = 0f,
                    right = size.width,
                    bottom = size.height - tipSize,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )

//            moveTo(
//                x = tipSize,
//                y = size.height - tipSize - cornerRadius
//            )
//
//            lineTo(
//                x = 0f,
//                y = size.height
//            )
//
//            lineTo(
//                x = tipSize + cornerRadius,
//                y = size.height - tipSize
//            )

            close()
        }

        return Outline.Generic(path)
    }
}

class CustomRoundedCornerShape(
    private val topStart: Dp,
    private val topEnd: Dp = topStart,
    private val bottomStart: Dp = topStart,
    private val bottomEnd: Dp = bottomStart
) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val topStartActually = with(density) { topStart.toPx() }
        val topStart = abs(topStartActually)

        val topEndActually = with(density) { topEnd.toPx() }
        val topEnd = abs(topEndActually)

        val bottomStartActually = with(density) { bottomStart.toPx() }
        val bottomStart = abs(bottomStartActually)

        val bottomEndActually = with(density) { bottomEnd.toPx() }
        val bottomEnd = abs(bottomEndActually)

        val path = Path().apply {
            moveTo(0f, topStart)
            if (topStartActually >= 0 ) quadraticTo(0f, 0f, topStart, 0f) else quadraticTo(topStart, topStart, topStart, 0f) // Top-left rounded corner
            lineTo(size.width - topEnd, 0f)
            if (topEndActually >= 0) quadraticTo(size.width, 0f, size.width, topEnd) else quadraticTo(size.width - topEnd, topEnd, size.width, topEnd)// Top-right rounded corner
            lineTo(size.width, size.height - bottomEnd) // Right side
            if (bottomEndActually >= 0) quadraticTo(size.width, size.height, size.width - bottomEnd, size.height) else quadraticTo(size.width-bottomEnd, size.height-bottomEnd, size.width - bottomEnd, size.height) // Bottom-right rounded corner
            lineTo(bottomStart, size.height) // Bottom-right corner
//            quadraticTo(0f, size.height, 0f, size.height-cornerRadius) // Bottom-left rounded corner
            if (bottomStartActually>= 0) quadraticTo(0f, size.height, 0f, size.height-bottomStart) else quadraticTo(bottomStart, size.height-bottomStart, 0f, size.height-bottomStart)// Bottom-left rounded corner
            lineTo(0f, topStart)
            close() // Close the shape
        }
        return Outline.Generic(path)
    }
}
