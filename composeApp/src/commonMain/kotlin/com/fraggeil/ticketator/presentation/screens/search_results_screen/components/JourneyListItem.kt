package com.fraggeil.ticketator.presentation.screens.search_results_screen.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.domain.formatWithSpacesNumber
import com.fraggeil.ticketator.core.domain.getHours
import com.fraggeil.ticketator.core.domain.millisecondsToFormattedString
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.TicketShape
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.domain.model.Journey
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.seat
import ticketator.composeapp.generated.resources.seat_3

@Composable
fun JourneyListItem(
    modifier: Modifier = Modifier.fillMaxWidth(),
    journey: Journey,
    onClick: () -> Unit,
    isLoading: Boolean = false
){
    TicketShape(
        modifier = modifier,
        onClick = onClick,
        isLoading = isLoading,
        contents = listOf(
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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

            },{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Sizes.horizontal_out_padding),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    if(!isLoading){
                        Icon(
                            painter = painterResource(Res.drawable.seat_3),
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
        ),
    )

}
//@Composable
//fun JourneyListItem2(
//    modifier: Modifier = Modifier.fillMaxWidth(),
//    journey: Journey,
//    onClick: () -> Unit,
//    isLoading: Boolean = false
//){
//    Box(
//        modifier = modifier
//            .background (
//                color = Color.Yellow,
//                shape = CustomRoundedCornerShape(topStart = Sizes.smallRoundCorner, bottomStart = (-12).dp ))
////                shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = (-12).dp, bottomEnd = (-12).dp))
//    ) {
//        Column(
//            modifier = Modifier.fillMaxSize()
////                .clip(RoundedCornerShape(Sizes.smallRoundCorner))
//                .then(
//                    if (isLoading) Modifier else Modifier.clickable { onClick() }
//                )
////                .background(color = BG_White, shape = RoundedCornerShape(Sizes.smallRoundCorner))
//                .shimmerLoadingAnimation(
//                    isLoadingCompleted = !isLoading,
//                    shimmerStyle = ShimmerStyle.Custom
//                )
//                .padding(Sizes.horizontal_inner_padding),
//            horizontalAlignment = Alignment.CenterHorizontally,
//        ){
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                Column(
//                    modifier = Modifier.weight(1f),
//                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                    horizontalAlignment = Alignment.Start
//                ) {
//                    Text(
//                        text = journey.from.second.abbr.uppercase(),
//                        color = TextColor,
//                        style = AppTypography().headlineMedium.copy(fontWeight = FontWeight.SemiBold),
//                        textAlign = TextAlign.Start,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Text(
//                        text = "${journey.from.second.name}, ${journey.from.first.name}",
//                        color = TextColorLight,
//                        style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
//                        textAlign = TextAlign.Start,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Text(
//                        text = journey.timeStart.getHours(),
//                        color = TextColor,
//                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
//                        textAlign = TextAlign.Start,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//                Dots(
//                    modifier = Modifier.weight(2f),
//                    iconColor = Blue,
//                    dotsColor = LightGray,
//                    pinColor = LightGray,
//                    topText = (journey.timeArrival - journey.timeStart).millisecondsToFormattedString(),
//                    bottomText = if (journey.stopAt.isEmpty()) "Non-stop" else "Stop at: ${journey.stopAt.joinToString(", ")}"
//                )
//                Column(
//                    modifier = Modifier.weight(1f),
//                    verticalArrangement = Arrangement.spacedBy(4.dp),
//                    horizontalAlignment = Alignment.End
//                ) {
//                    Text(
//                        text = journey.to.second.abbr.uppercase(),
//                        color = TextColor,
//                        style = AppTypography().headlineMedium.copy(fontWeight = FontWeight.SemiBold),
//                        textAlign = TextAlign.End,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Text(
//                        text = "${journey.to.second.name}, ${journey.to.first.name}",
//                        color = TextColorLight,
//                        style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
//                        textAlign = TextAlign.End,
//                        maxLines = 2,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                    Text(
//                        text = journey.timeArrival.getHours(),
//                        color = TextColor,
//                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
//                        textAlign = TextAlign.End,
//                        maxLines = 1,
//                        overflow = TextOverflow.Ellipsis
//                    )
//                }
//            }
//            DashedLineDivider(
//                modifier = Modifier.padding(vertical = Sizes.vertical_inner_padding).fillMaxWidth(),
//                color = LightGray
//            )
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ){
//                Icon(
//                    painter = painterResource(Res.drawable.seat),
//                    contentDescription = null,
//                    tint = TextColorLight,
//                    modifier = Modifier.height(24.dp)
//                )
//                Text(
//                    modifier = Modifier.weight(1f),
//                    text = "${journey.seatsAvailable.size} Remaining",
//                    style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Normal),
//                    color = TextColorLight
//                )
//                Text(
//                    text = "${journey.price.toString().formatWithSpacesNumber()} sum",
//                    style = AppTypography().titleLarge.copy(fontWeight = FontWeight.SemiBold),
//                    color = Blue
//                )
//            }
//        }
//    }
//}
//
//@Composable
//fun DynamicCustomBoxWithQuarterCirclesInRectangles(
//    modifier: Modifier = Modifier,
//    content: @Composable BoxScope.() -> Unit
//) {
//    Box(
//        modifier = modifier
//            .fillMaxWidth()
//            .clip(SpeechBubbleShape())
//            .padding(horizontal = 16.dp) // Optional padding
//    ) {
//        Canvas(
//            modifier = Modifier.matchParentSize() // Matches the size of the Box
//        ) {
//            val boxColor = Color.Yellow // Replace with your desired color
//            val cornerRadius = 24.dp.toPx()
//            val cutoutHeight = 24.dp.toPx()
//            val cutoutWidth = 48.dp.toPx()
//            val circleRadius = cutoutHeight // Radius of the quarter-circle is equal to the cutout height
//
//            // Draw the rounded rectangle for the background
//            drawRoundRect(
//                color = boxColor,
//                size = size,
//                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
//            )
//            drawArc(
//                color = boxColor, // Repaint the arc to restore the original box color
//                startAngle = 180f,
//                sweepAngle = 90f,
//                useCenter = true,
//                topLeft = Offset(size.width - 2 * circleRadius, size.height - 2 * circleRadius),
//
//                size = Size(circleRadius * 2, circleRadius * 2)
//            )
//
//            drawArc(
//                color = boxColor, // Repaint the arc to restore the original box color
//                startAngle = 270f,
//                sweepAngle = 90f,
//                useCenter = true,
//                topLeft = Offset(0f, size.height - 2 * circleRadius),
//                size = Size(circleRadius * 2, circleRadius * 2)
//            )
//        }
//
//        // Content Area
//        Box(
//            modifier = Modifier
//                .padding(16.dp) // Padding inside the box
//        ) {
//            content()
//        }
//    }
//}


