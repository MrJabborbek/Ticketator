package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.theme.LightGray

enum class ShimmerStyle{
    TextBody, TextSmallLabel, TextTitle, Custom
}

fun Modifier.shimmerLoadingAnimation(
    modifier: Modifier = Modifier.fillMaxWidth(),
    isLoadingCompleted: Boolean = true, // <-- New parameter for start/stop.
    isLightModeActive: Boolean = true, // <-- New parameter for display modes.
    shimmerBGColor: Color = if(isLightModeActive) Color.White else Color.Black,
    bgColor: Color = if(isLightModeActive) LightGray else Color.DarkGray,
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
    shimmerStyle: ShimmerStyle = ShimmerStyle.TextBody,
    linesCount: Int = 1,
    cornerRadius: Float = 8f
): Modifier {
    if (isLoadingCompleted) { // <-- Step 1.
        return this
    } else {
        return composed {
            val shimmerColors = listOf(
                shimmerBGColor.copy(alpha = 0.3f),
                shimmerBGColor.copy(alpha = 0.5f),
                shimmerBGColor.copy(alpha = 1.0f),
                shimmerBGColor.copy(alpha = 0.5f),
                shimmerBGColor.copy(alpha = 0.3f),
            )
            val transition = rememberInfiniteTransition(label = "")
            val translateAnimation = transition.animateFloat(
                initialValue = 0f,
                targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = durationMillis,
                        easing = LinearEasing,
                    ),
                    repeatMode = RepeatMode.Restart,
                ),
                label = "Shimmer loading animation",
            )

            val brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            )

            val density =  LocalDensity.current.density

            this.then(
                    when (shimmerStyle) {
                        ShimmerStyle.Custom -> modifier
                            .clip(RoundedCornerShape(cornerRadius.dp))
                            .background(bgColor, shape = RoundedCornerShape(cornerRadius.dp))
                            .background(brush = brush)

                        ShimmerStyle.TextTitle -> modifier
                            .drawShimmerLines(
                                linesCount = linesCount,
                                lineHeight = 25f,
                                linePadding = 10f,
                                cornerRadius =cornerRadius,
                                brush,
                                density = density,
                                bgColor = bgColor
                            )

                        ShimmerStyle.TextSmallLabel -> modifier
                            .drawShimmerLines(
                                linesCount = linesCount,
                                lineHeight = 10f,
                                linePadding = 10f,
                                cornerRadius = cornerRadius,
                                brush,
                                density = density,
                                bgColor = bgColor
                            )

                        ShimmerStyle.TextBody -> modifier
                            .drawShimmerLines(
                                linesCount = linesCount,
                                lineHeight = 15f,
                                linePadding = 10f,
                                cornerRadius = cornerRadius,
                                brush,
                                density = density,
                                bgColor = bgColor
                            )
                    }
                )
        }
    }
}

private fun Modifier.drawShimmerLines(
    linesCount: Int,
    lineHeight: Float,
    linePadding: Float,
    cornerRadius: Float,
    brush: Brush,
    density: Float,
    bgColor: Color
) = this
    .height(((linesCount * (lineHeight + linePadding) - linePadding)).dp )
    .drawBehind {
    for (i in 0 until linesCount*2-1) {
        val k = i / 2
        if (i % 2 == 0){
            drawRoundRect(
                color = bgColor,
                topLeft = Offset(x = 0f, y = (lineHeight + linePadding) * k*density),
                size = Size(width = size.width, height = lineHeight*density),
                cornerRadius = CornerRadius(cornerRadius)
            )
            drawRoundRect(
                brush = brush,
                topLeft = Offset(x = 0f, y = (lineHeight + linePadding) * k*density),
                size = Size(width = size.width, height = lineHeight*density),
                cornerRadius = CornerRadius(cornerRadius)
            )
        }else{
            drawRect(
                color = Color.Transparent,
                topLeft = Offset(x = 0f, y = (lineHeight + linePadding) * k*density),
                size = Size(width = size.width, height = linePadding*density)
            )
        }
    }
}

//data class ShimmerAnimationData(
//    private val isLightMode: Boolean
//) {
//    fun getColours(): List<Color> {
//        return if (isLightMode) {
//            val color = Color.White
//
//            listOf(
//                color.copy(alpha = 0.3f),
//                color.copy(alpha = 0.5f),
//                color.copy(alpha = 1.0f),
//                color.copy(alpha = 0.5f),
//                color.copy(alpha = 0.3f),
//            )
//        } else {
//            val color = Color.Black
//
//            listOf(
//                color.copy(alpha = 0.0f),
//                color.copy(alpha = 0.3f),
//                color.copy(alpha = 0.5f),
//                color.copy(alpha = 0.3f),
//                color.copy(alpha = 0.0f),
//            )
//        }
//    }
//}


//fun Modifier.splitBackground(lineCount: Int,
//                             widthOfShadowBrush: Int = 10000,
//                             angleOfAxisY: Float = 270f,
//                             durationMillis: Int = 1000,
//                             shimmerStyle: ShimmerStyle = ShimmerStyle.TextBody,
//                             textLinesCount: Int = 1,
//                             linePadding: Float = 4f // <-- Additional padding between lines
//): Modifier {
//    return composed {
//        val shimmerColors = ShimmerAnimationData(isLightMode = false).getColours()
//        val transition = rememberInfiniteTransition(label = "")
//        val translateAnimation = transition.animateFloat(
//            initialValue = 0f,
//            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
//            animationSpec = infiniteRepeatable(
//                animation = tween(
//                    durationMillis = durationMillis,
//                    easing = LinearEasing,
//                ),
//                repeatMode = RepeatMode.Restart,
//            ),
//            label = "Shimmer loading animation",
//        )
//        val brush = Brush.linearGradient(
//            colors = shimmerColors,
//            start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
//            end = Offset(x = translateAnimation.value, y = angleOfAxisY),
//        )
//
//
//        this.then(
//
//            Modifier.drawBehind {
//                if (lineCount <= 0) return@drawBehind
//
//                val lineWidth = size.height / lineCount
//
//                for (i in 0 until lineCount) {
//                    if (i % 2 == 0){
//                        drawRoundRect(
//                            brush = brush,
//                            topLeft = Offset(x = 0f, y = lineWidth * i,),
//                            size = Size(width = size.width, height = lineWidth),
//                            cornerRadius = CornerRadius(4f,4f)
//                        )
//                    }else{
//                        drawRect(
//                            color = Color.Transparent,
//                            topLeft = Offset(x = 0f, y = lineWidth * i,),
//                            size = Size(width = size.width, height = lineWidth)
//                        )
//                    }
//                }
//            }
//        )
//    }
//}
