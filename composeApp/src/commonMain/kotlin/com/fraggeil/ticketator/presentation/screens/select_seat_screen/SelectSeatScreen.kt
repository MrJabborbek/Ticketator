package com.fraggeil.ticketator.presentation.screens.select_seat_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.domain.UiType
import com.fraggeil.ticketator.core.domain.formatWithSpacesNumber
import com.fraggeil.ticketator.core.domain.getHours
import com.fraggeil.ticketator.core.domain.getUiType
import com.fraggeil.ticketator.core.domain.millisecondsToFormattedString
import com.fraggeil.ticketator.core.domain.rememberScreenSizeInfo
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.domain.model.Journey
import com.fraggeil.ticketator.presentation.screens.search_results_screen.components.Dots
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.components.Seats
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_close
import ticketator.composeapp.generated.resources.uzbekistan


@Composable
fun SelectSeatScreenRoot(
    viewModel: SelectSeatViewModel,
    navigateBack: () -> Unit,
    navigateToPassengersInfo: (Journey) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SelectSeatScreen(
        state = state,
        onAction = {
            when(it){
                SelectSeatAction.OnBackClicked -> navigateBack()
                SelectSeatAction.OnNextClicked -> state.selectedJourney?.let { it1 -> navigateToPassengersInfo(it1) }
                is SelectSeatAction.OnSeatClicked -> {}
                is SelectSeatAction.OnJourneySelected -> {
                }
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun SelectSeatScreen(
    state: SelectSeatState,
    onAction: (SelectSeatAction) -> Unit
) {
    val uiType = rememberScreenSizeInfo().getUiType()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(BlueDark)
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(0.35f)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(Res.drawable.uzbekistan),
                contentDescription = null,
                colorFilter = ColorFilter.tint(BlueDarkSecondary)
            )
        }
//        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize(),
//                .changeScrollStateByMouse(isVerticalScroll = true, scrollState = scrollState)
//                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding)
                        .padding(top = vertical_inner_padding).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    MyCircularButton(
                        icon = painterResource(Res.drawable.ic_close),
                        onClick = { onAction(SelectSeatAction.OnBackClicked) }
                    )
                    Text(
                        text = "Select Seats",
                        color = White,
                        style = AppTypography().headlineSmall.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(vertical = vertical_out_padding).weight(1f),
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding)
                        .padding(top = vertical_out_padding).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Column(
//                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.selectedJourney?.timeStart?.toFormattedDate(hoursEnabled = false)?.takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                modifier = Modifier.width(100.dp),
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            ),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,

                        )
                        Text(
                            text = state.selectedJourney?.timeStart?.getHours()?.takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                modifier = Modifier.width(100.dp),
                                shimmerStyle = ShimmerStyle.TextTitle
                            ),
                            color = White,
                            style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${state.selectedJourney?.from?.second?.name}, ${state.selectedJourney?.from?.first?.name}".takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.widthIn(max = 100.dp).shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                modifier = Modifier.width(100.dp),
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            ),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    Dots(
                        isLoading = state.isLoading,
                        modifier = Modifier.weight(1.5f),
                        iconColor = White,
                        dotsColor = White,
                        pinColor = Blue,
                        topText = ((state.selectedJourney?.timeArrival?:0) - (state.selectedJourney?.timeStart ?: 0)).millisecondsToFormattedString(),
                        bottomText = if (state.selectedJourney?.stopAt.isNullOrEmpty()) "Non-stop" else "Stop at: ${
                            state.selectedJourney!!.stopAt.joinToString(
                                ", "
                            )
                        }"
                    )
                    Column(
//                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = state.selectedJourney?.timeArrival?.toFormattedDate(hoursEnabled = false)?.takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                modifier = Modifier.width(100.dp),
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            ),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = state.selectedJourney?.timeArrival?.getHours()?.takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                modifier = Modifier.width(100.dp),
                                shimmerStyle = ShimmerStyle.TextTitle
                            ),
                            color = White,
                            style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${state.selectedJourney?.to?.second?.name}, ${state.selectedJourney?.to?.first?.name}".takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.widthIn(max = 100.dp).shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                modifier = Modifier.width(100.dp),
                                shimmerStyle = ShimmerStyle.TextTitle
                            ),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding)
                        .padding(top = vertical_out_padding).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(
                            text = "1 ticket: ${state.selectedJourney?.price.toString().formatWithSpacesNumber()} so‘m".takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            ),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Additional: 0 so‘m".takeIf { !state.isLoading } ?:"".takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                shimmerStyle = ShimmerStyle.TextSmallLabel
                            ),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Total: ${(state.selectedJourney?.price?.times(state.selectedJourney.selectedSeats.size) ?: 0).toString().formatWithSpacesNumber()} so‘m".takeIf { !state.isLoading } ?:"",
                            modifier = Modifier.shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoading,
                                shimmerStyle = ShimmerStyle.TextBody
                            ),
                            color = White,
                            style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    MyButton(
                        isLoading = state.isLoading,
                        modifier = Modifier.width(160.dp),
//                        modifier = Modifier.padding(top = vertical_out_padding, start = Sizes.horizontal_out_padding, end = Sizes.horizontal_out_padding).widthIn(max = 600.dp).fillMaxWidth(),
                        text = "Next",
                        onClick = { onAction(SelectSeatAction.OnNextClicked) },
                        enabled = !state.selectedJourney?.selectedSeats.isNullOrEmpty()
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding, vertical = vertical_out_padding)
                        .fillMaxWidth()
                        .shimmerLoadingAnimation(
                            isLoadingCompleted = !state.isLoading,
                            shimmerStyle = ShimmerStyle.Custom
                        )
                        .background(color = BG_White, shape = RoundedCornerShape(Sizes.smallRoundCorner)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    if (!state.isLoading){
                        Seats(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            selected = state.selectedJourney?.selectedSeats?: emptyList(),
                            available = state.selectedJourney?.seatsAvailable?: emptyList(),
                            reserved = state.selectedJourney?.seatsReserved?: emptyList(),
                            onClick = {
                                onAction(SelectSeatAction.OnSeatClicked(it))
                            },
                            isHorizontal = uiType != UiType.COMPACT
                        )
                    }
                }

        }
    }
}