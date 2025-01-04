package com.fraggeil.ticketator.presentation.screens.search_results_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.domain.formatWithSpacesNumber
import com.fraggeil.ticketator.core.domain.getHours
import com.fraggeil.ticketator.core.domain.millisecondsToFormattedString
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.components.DashedLineDivider
import com.fraggeil.ticketator.core.presentation.components.DottedHorizontalDivider
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.domain.model.Journey
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.seat
import kotlin.math.abs

@Composable
fun JourneyListItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    journey: Journey,
    onClick: () -> Unit,
    isLoading: Boolean = false
){
    val interactionSource = remember { MutableInteractionSource() }
    val localIndication = LocalIndication.current

    CustomBox(
        modifier = modifier,
        content1 = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(
                            if (!isLoading){
                                Modifier.clickable(
                                    interactionSource = interactionSource,
                                    indication = localIndication,
                                    onClick = onClick
                                )
                            } else {
                                Modifier
                            }
                        )
//                        .shimmerLoadingAnimation(
//                            isLoadingCompleted = !isLoading,
//                            shimmerStyle = ShimmerStyle.Custom
//                        )
                        .padding(Sizes.horizontal_out_padding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = journey.from.second.abbr.uppercase().takeIf { !isLoading }?:"",
                            color = TextColor,
                            style = AppTypography().headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                shimmerStyle = ShimmerStyle.TextTitle
                            )
                        )
                        Text(
                            text = "${journey.from.second.name}, ${journey.from.first.name}".takeIf { !isLoading } ?:"",
                            color = TextColorLight,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            )
                        )
                        Text(
                            text = journey.timeStart.getHours().takeIf { !isLoading } ?:"",
                            color = TextColor,
                            style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                shimmerStyle = ShimmerStyle.TextBody
                            )
                        )
                    }
                    if(isLoading){
                        Box(Modifier.weight(2f))
                    }else{
                        Dots(
                            modifier = Modifier.weight(2f),
                            iconColor = Blue,
                            dotsColor = LightGray,
                            pinColor = LightGray,
                            topText = (journey.timeArrival - journey.timeStart).millisecondsToFormattedString(),
                            bottomText = if (journey.stopAt.isEmpty()) "Non-stop" else "Stop at: ${journey.stopAt.joinToString(", ")}"
                        )
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = journey.to.second.abbr.uppercase().takeIf { !isLoading }?:"",
                            color = TextColor,
                            style = AppTypography().headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                shimmerStyle = ShimmerStyle.TextTitle
                            )
                        )
                        Text(
                            text = "${journey.to.second.name}, ${journey.to.first.name}".takeIf { !isLoading }?:"",
                            color = TextColorLight,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.End,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            )
                        )
                        Text(
                            text = journey.timeArrival.getHours().takeIf { !isLoading }?:"",
                            color = TextColor,
                            style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.End,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !isLoading,
                                shimmerStyle = ShimmerStyle.TextBody
                            )
                        )
                    }
                }

        },
        content2 = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .then(
                        if (!isLoading){
                            Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = localIndication,
                                onClick = onClick
                            )
                        } else {
                            Modifier
                        }
                    )
//                    .shimmerLoadingAnimation(
//                        isLoadingCompleted = !isLoading,
//                        shimmerStyle = ShimmerStyle.Custom
//                    )
                    .padding(Sizes.horizontal_out_padding),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                if(!isLoading){
                    Icon(
                        painter = painterResource(Res.drawable.seat),
                        contentDescription = null,
                        tint = TextColorLight,
                        modifier = Modifier.height(24.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .shimmerLoadingAnimation(
                            isLoadingCompleted = !isLoading,
                            shimmerStyle = ShimmerStyle.TextBody
                        )
                    ,
                    text = "${journey.seatsAvailable.size} Remaining".takeIf { !isLoading }?:"",
                    style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Normal),
                    color = TextColorLight
                )
                Text(
                    text = "${journey.price.toString().formatWithSpacesNumber()} sum".takeIf { !isLoading }?:"",
                    style = AppTypography().titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Blue,
                    modifier = Modifier.shimmerLoadingAnimation(
                        isLoadingCompleted = !isLoading,
                        shimmerStyle = ShimmerStyle.TextTitle
                    )
                )
            }
        }
    )

}
@Composable
fun JourneyListItem2(
    modifier: Modifier = Modifier.fillMaxWidth(),
    journey: Journey,
    onClick: () -> Unit,
    isLoading: Boolean = false
){
    Box(
        modifier = modifier
            .background (
                color = Color.Yellow,
                shape = CustomRoundedCornerShape(topStart = Sizes.smallRoundCorner, bottomStart = (-12).dp ))
//                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = (-12).dp, bottomEnd = (-12).dp))
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
//                .clip(RoundedCornerShape(Sizes.smallRoundCorner))
                .then(
                    if (isLoading) Modifier else Modifier.clickable { onClick() }
                )
//                .background(color = BG_White, shape = RoundedCornerShape(Sizes.smallRoundCorner))
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.Custom
                )
                .padding(Sizes.horizontal_inner_padding),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = journey.from.second.abbr.uppercase(),
                        color = TextColor,
                        style = AppTypography().headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${journey.from.second.name}, ${journey.from.first.name}",
                        color = TextColorLight,
                        style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = journey.timeStart.getHours(),
                        color = TextColor,
                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Dots(
                    modifier = Modifier.weight(2f),
                    iconColor = Blue,
                    dotsColor = LightGray,
                    pinColor = LightGray,
                    topText = (journey.timeArrival - journey.timeStart).millisecondsToFormattedString(),
                    bottomText = if (journey.stopAt.isEmpty()) "Non-stop" else "Stop at: ${journey.stopAt.joinToString(", ")}"
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = journey.to.second.abbr.uppercase(),
                        color = TextColor,
                        style = AppTypography().headlineMedium.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${journey.to.second.name}, ${journey.to.first.name}",
                        color = TextColorLight,
                        style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                        textAlign = TextAlign.End,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = journey.timeArrival.getHours(),
                        color = TextColor,
                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                        textAlign = TextAlign.End,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            DashedLineDivider(
                modifier = Modifier.padding(vertical = Sizes.vertical_inner_padding).fillMaxWidth(),
                color = LightGray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Icon(
                    painter = painterResource(Res.drawable.seat),
                    contentDescription = null,
                    tint = TextColorLight,
                    modifier = Modifier.height(24.dp)
                )
                Text(
                    modifier = Modifier.weight(1f),
                    text = "${journey.seatsAvailable.size} Remaining",
                    style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Normal),
                    color = TextColorLight
                )
                Text(
                    text = "${journey.price.toString().formatWithSpacesNumber()} sum",
                    style = AppTypography().titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Blue
                )
            }
        }
    }
}

@Composable
fun DynamicCustomBoxWithQuarterCirclesInRectangles(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(SpeechBubbleShape())
            .padding(horizontal = 16.dp) // Optional padding
    ) {
        Canvas(
            modifier = Modifier.matchParentSize() // Matches the size of the Box
        ) {
            val boxColor = Color.Yellow // Replace with your desired color
            val cornerRadius = 24.dp.toPx()
            val cutoutHeight = 24.dp.toPx()
            val cutoutWidth = 48.dp.toPx()
            val circleRadius = cutoutHeight // Radius of the quarter-circle is equal to the cutout height

            // Draw the rounded rectangle for the background
            drawRoundRect(
                color = boxColor,
                size = size,
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )
            drawArc(
                color = boxColor, // Repaint the arc to restore the original box color
                startAngle = 180f,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = Offset(size.width - 2 * circleRadius, size.height - 2 * circleRadius),

                size = Size(circleRadius * 2, circleRadius * 2)
            )

            drawArc(
                color = boxColor, // Repaint the arc to restore the original box color
                startAngle = 270f,
                sweepAngle = 90f,
                useCenter = true,
                topLeft = Offset(0f, size.height - 2 * circleRadius),
                size = Size(circleRadius * 2, circleRadius * 2)
            )
        }

        // Content Area
        Box(
            modifier = Modifier
                .padding(16.dp) // Padding inside the box
        ) {
            content()
        }
    }
}

@Composable
fun CustomBox(
    modifier: Modifier,
    cornerSize: Float = 12f,
    backgroundColor: Color = BG_White,
    content1: @Composable BoxScope.() -> Unit,
    content2: @Composable BoxScope.() -> Unit
){
    Column(
        modifier = modifier
    ){
        Box(
            modifier = Modifier.fillMaxWidth()
                .clip(CustomRoundedCornerShape(topStart = cornerSize.dp, bottomStart = (-cornerSize).dp))
                .background(backgroundColor, CustomRoundedCornerShape(topStart = cornerSize.dp, bottomStart = (-cornerSize).dp))
        ){
            content1()
        }
        DashedLineDivider(modifier = Modifier.padding(horizontal = cornerSize.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
                .clip(CustomRoundedCornerShape(topStart = -cornerSize.dp, bottomStart = (cornerSize).dp))
                .background(backgroundColor, CustomRoundedCornerShape(topStart = -cornerSize.dp, bottomStart = (cornerSize).dp))
        ){
            content2()
        }
    }
}

class SpeechBubbleShape(
    private val cornerRadius: Dp = 15.dp,
    private val tipSize: Dp = 15.dp
): Shape {

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
    private val bottomStart:Dp = topStart,
    private val bottomEnd:Dp = bottomStart
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


