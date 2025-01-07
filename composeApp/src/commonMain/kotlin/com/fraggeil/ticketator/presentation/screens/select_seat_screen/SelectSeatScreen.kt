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
import com.fraggeil.ticketator.core.domain.getHours
import com.fraggeil.ticketator.core.domain.getUiType
import com.fraggeil.ticketator.core.domain.millisecondsToFormattedString
import com.fraggeil.ticketator.core.domain.rememberScreenSizeInfo
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White
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
    navigateToPayment: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SelectSeatScreen(
        state = state,
        onAction = {
            when(it){
                SelectSeatAction.OnBackClicked -> navigateBack()
                SelectSeatAction.OnPayClicked -> navigateToPayment()
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
            state.selectedJourney?.let { selectedJourney ->
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
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
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
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = selectedJourney.timeStart.toFormattedDate(hoursEnabled = false),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = selectedJourney.timeStart.getHours(),
                            color = White,
                            style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${selectedJourney.from.second.name}, ${selectedJourney.from.first.name}",
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Dots(
                        modifier = Modifier.weight(1.5f),
                        iconColor = White,
                        dotsColor = White,
                        pinColor = Blue,
                        topText = (selectedJourney.timeArrival - selectedJourney.timeStart).millisecondsToFormattedString(),
                        bottomText = if (selectedJourney.stopAt.isEmpty()) "Non-stop" else "Stop at: ${
                            selectedJourney.stopAt.joinToString(
                                ", "
                            )
                        }"
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = selectedJourney.timeArrival.toFormattedDate(hoursEnabled = false),
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = selectedJourney.timeArrival.getHours(),
                            color = White,
                            style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${selectedJourney.to.second.name}, ${selectedJourney.to.first.name}",
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .padding(horizontal = Sizes.horizontal_out_padding, vertical = vertical_out_padding)
                        .fillMaxWidth()
                        .background(color = BG_White, shape = RoundedCornerShape(Sizes.smallRoundCorner)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Seats(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        selected = state.selectedSeats,
                        available = selectedJourney.seatsAvailable,
                        reserved = selectedJourney.seatsReserved,
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