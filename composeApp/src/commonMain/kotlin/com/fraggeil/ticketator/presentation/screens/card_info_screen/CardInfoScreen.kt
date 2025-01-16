package com.fraggeil.ticketator.presentation.screens.card_info_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.domain.formatWithSpacesNumber
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.components.InputStyle
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.presentation.components.MyTextField
import com.fraggeil.ticketator.core.presentation.components.TopBar2
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.ErrorColor
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatAction
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_close
import ticketator.composeapp.generated.resources.uzbekistan

@Composable
fun CardInfoScreenRoot(
    viewModel: CardInfoViewModel,
    navigateBack: () -> Unit,
    navigateToEnterCode: (token: String, phoneNumber: String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(true){
        viewModel.oneTimeState.collect{
            when(it){
                is CardInfoOneTimeState.NavigateToOtpPayment -> navigateToEnterCode(it.token, it.phoneNumber)
            }
        }
    }
    CardInfoScreen(
        state = state,
        onAction = {
            when (it) {
                CardInfoAction.OnBackClicked -> navigateBack()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun CardInfoScreen(
    state: CardInfoState,
    onAction: (CardInfoAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(BlueDark),
    ) {
        Box(
            modifier = Modifier
                .weight(1f).fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f)
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
                contentAlignment = Alignment.Center
            ) {
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
                TopBar2(
                    text = "Card Info",
                    isLeadingButtonVisible = true,
                    onLeadingButtonClick = { onAction(CardInfoAction.OnBackClicked) },
                )
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier
                        .padding(
                            horizontal = Sizes.horizontal_out_padding,
                            vertical = vertical_out_padding
                        )
                        .fillMaxWidth()
                        .background(
                            color = BG_White,
                            shape = RoundedCornerShape(Sizes.smallRoundCorner)
                        )
                        .padding(horizontal = Sizes.horizontal_inner_padding)
                        .changeScrollStateByMouse(
                            scrollState = scrollState,
                            isVerticalScroll = true
                        )
                        .verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(Sizes.vertical_inner_padding))
                    MyTextField(
                        modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                        label = "Card number",
                        hint = "0000 0000 0000 0000",
                        value = state.cardNumber,
                        onValueChange = {
                            onAction(CardInfoAction.OnCardNumberChanged(it))
                        },
                        inputStyle = InputStyle.PLASTIC_CARD,
                    )
                    Row(
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ){
                        MyTextField(
                            modifier = Modifier.weight(1f),
                            label = "Valid date",
                            hint = "MM/YY",
                            value = state.cardValidUntil,
                            onValueChange = {
                                onAction(CardInfoAction.OnCardValidUntilChanged(it))
                            },
                            inputStyle = InputStyle.PLASTIC_CARD_VALID_DATE,
                        )
                        Text(
                            modifier = Modifier.wrapContentWidth(),
                            textAlign = TextAlign.End,
                            text = state.timer,
                            style = AppTypography().bodyLarge.copy(color = Blue, fontWeight = FontWeight.SemiBold),
                            maxLines = 1
                        )
                    }
                    if (state.isTimerEnd) {
                        Text(
                            modifier = Modifier.padding(top = vertical_inner_padding).fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = "Timer ended. Please try again from beginning",
                            style = AppTypography().bodyMedium.copy(color = ErrorColor, fontWeight = FontWeight.Medium),
                        )
                    }
                    Spacer(modifier = Modifier.height(Sizes.vertical_inner_padding))

                }
            }
        }
        state.selectedJourney?.let { selectedJourney ->
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Blue.copy(0.2f))
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.Start
                    ){
                        Text(
                            text = "1 ticket: ${selectedJourney.price.toString().formatWithSpacesNumber()} so‘m",
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Additional: 0 so‘m",
                            color = White,
                            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Normal),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Total: ${(selectedJourney.price*state.selectedJourney.selectedSeats.size).toString().formatWithSpacesNumber()} so‘m",
                            color = White,
                            style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Start,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    MyButton(
                        modifier = Modifier.width(160.dp),
//                        modifier = Modifier.padding(top = vertical_out_padding, start = Sizes.horizontal_out_padding, end = Sizes.horizontal_out_padding).widthIn(max = 600.dp).fillMaxWidth(),
                        text = "Send SMS",
                        onClick = { onAction(CardInfoAction.OnNextClicked) },
                        enabled = state.isAllDataValid,
                        isLoading = state.isSendingData
                    )
                }
            }
        }
    }
}