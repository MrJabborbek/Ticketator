package com.fraggeil.ticketator.presentation.screens.tickets_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.domain.formatWithSpacesNumber
import com.fraggeil.ticketator.core.domain.getHours
import com.fraggeil.ticketator.core.domain.millisecondsToFormattedString
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.TicketShape
import com.fraggeil.ticketator.core.presentation.components.TwoTextRowItem
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.domain.model.Ticket
import com.fraggeil.ticketator.presentation.screens.search_results_screen.components.Dots
import kotlinx.coroutines.launch
import qrgenerator.QRCodeImage

@Composable
fun TicketItem(
    modifier: Modifier = Modifier,
    ticket: Ticket,
    onClick: () -> Unit,
    onShareClick: (ImageBitmap) -> Unit,
    onDownloadClick: (ImageBitmap) -> Unit,
    isLoading: Boolean = false
){
    val graphicsLayer = rememberGraphicsLayer()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier,
    ){
        TicketShape(
            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
            onClick = null,
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
                                text = ticket.journey.from.second.abbr.uppercase().takeIf { !isLoading }?:"",
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
                                text = "${ticket.journey.from.second.name}, ${ticket.journey.from.first.name}".takeIf { !isLoading } ?:"",
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
                                text = ticket.journey.timeStart.getHours().takeIf { !isLoading } ?:"",
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
                                topText = (ticket.journey.timeArrival - ticket.journey.timeStart).millisecondsToFormattedString(),
                                bottomText = if (ticket.journey.stopAt.isEmpty()) "Non-stop" else "Stop at: ${ticket.journey.stopAt.joinToString(", ")}"
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = ticket.journey.to.second.abbr.uppercase().takeIf { !isLoading }?:"",
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
                                text = "${ticket.journey.to.second.name}, ${ticket.journey.to.first.name}".takeIf { !isLoading }?:"",
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
                                text = ticket.journey.timeArrival.getHours().takeIf { !isLoading }?:"",
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
                {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(Sizes.horizontal_out_padding),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        TwoTextRowItem(
                            modifier = Modifier.fillMaxWidth(),
                            text1 = "Jo'nash vaqti:",
                            text2 = ticket.journey.timeStart.toFormattedDate(hoursEnabled = true),
                            isLoading = isLoading
                        )
                        TwoTextRowItem(
                            modifier = Modifier.fillMaxWidth(),
                            text1 = "O'rin:",
                            text2 = ticket.passenger.seat.toString(),
                            isLoading = isLoading
                        )
                        TwoTextRowItem(
                            modifier = Modifier.fillMaxWidth(),
                            text1 = "Yo'lovchi:",
                            text2 = ticket.passenger.name,
                            isLoading = isLoading
                        )
                        TwoTextRowItem(
                            modifier = Modifier.fillMaxWidth(),
                            text1 = "Sotib olingan vaqt:",
                            text2 = ticket.buyTime.toFormattedDate(hoursEnabled = true),
                            isLoading = isLoading
                        )
                        TwoTextRowItem(
                            modifier = Modifier.fillMaxWidth(),
                            text1 = "Chipta narxi:",
                            text2 = ticket.journey.price.toString().formatWithSpacesNumber() + " so‘m",
                            isLoading = isLoading
                        )
                    }
                },
                {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(Sizes.vertical_inner_padding),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        QRCodeImage(
                            modifier = Modifier
                                .widthIn(max = 300.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(Sizes.smallRoundCorner)),
                            url = ticket.qrCodeLink,
                            contentDescription = null
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.End)
                        ){
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        onShareClick(graphicsLayer.toImageBitmap())
                                    }
                                }
                            ){
                                Icon(
                                    Icons.Default.Share,
                                    contentDescription = null,
                                    tint = Blue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            IconButton(
                                onClick = {
                                    coroutineScope.launch {
                                        onDownloadClick(graphicsLayer.toImageBitmap())
                                    }
                                }
                            ){
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = null,
                                    tint = Blue,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
            )
        )
        Box(modifier = Modifier.width(0.dp).height(0.dp).background(color = Color.Red)
            .horizontalScroll(rememberScrollState())
            .verticalScroll(rememberScrollState())
        ){
            TicketShape(
                modifier = Modifier.width(350.dp).wrapContentHeight().drawWithContent {
                    graphicsLayer.record {
                        this@drawWithContent.drawContent()
                    }
                    drawLayer(graphicsLayer)
                },
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
                                    text = ticket.journey.from.second.abbr.uppercase().takeIf { !isLoading }?:"",
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
                                    text = "${ticket.journey.from.second.name}, ${ticket.journey.from.first.name}".takeIf { !isLoading } ?:"",
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
                                    text = ticket.journey.timeStart.getHours().takeIf { !isLoading } ?:"",
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
                                    topText = (ticket.journey.timeArrival - ticket.journey.timeStart).millisecondsToFormattedString(),
                                    bottomText = if (ticket.journey.stopAt.isEmpty()) "Non-stop" else "Stop at: ${ticket.journey.stopAt.joinToString(", ")}"
                                )
                            }
                            Column(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(4.dp),
                                horizontalAlignment = Alignment.End
                            ) {
                                Text(
                                    text = ticket.journey.to.second.abbr.uppercase().takeIf { !isLoading }?:"",
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
                                    text = "${ticket.journey.to.second.name}, ${ticket.journey.to.first.name}".takeIf { !isLoading }?:"",
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
                                    text = ticket.journey.timeArrival.getHours().takeIf { !isLoading }?:"",
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
                    {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(Sizes.horizontal_out_padding),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            TwoTextRowItem(
                                modifier = Modifier.fillMaxWidth(),
                                text1 = "Jo'nash vaqti:",
                                text2 = ticket.journey.timeStart.toFormattedDate(hoursEnabled = true),
                                isLoading = isLoading
                            )
                            TwoTextRowItem(
                                modifier = Modifier.fillMaxWidth(),
                                text1 = "O'rin:",
                                text2 = ticket.passenger.seat.toString(),
                                isLoading = isLoading
                            )
                            TwoTextRowItem(
                                modifier = Modifier.fillMaxWidth(),
                                text1 = "Yo'lovchi:",
                                text2 = ticket.passenger.name,
                                isLoading = isLoading
                            )
                            TwoTextRowItem(
                                modifier = Modifier.fillMaxWidth(),
                                text1 = "Sotib olingan vaqt:",
                                text2 = ticket.buyTime.toFormattedDate(hoursEnabled = true),
                                isLoading = isLoading
                            )
                            TwoTextRowItem(
                                modifier = Modifier.fillMaxWidth(),
                                text1 = "Chipta narxi:",
                                text2 = ticket.journey.price.toString().formatWithSpacesNumber() + " so‘m",
                                isLoading = isLoading
                            )
                        }
                    },
                    {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(Sizes.vertical_inner_padding),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            QRCodeImage(
                                modifier = Modifier
                                    .widthIn(max = 300.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(Sizes.smallRoundCorner)),
                                url = ticket.qrCodeLink,
                                contentDescription = null
                            )
                        }
                    }
                )
            )
        }
    }
}
