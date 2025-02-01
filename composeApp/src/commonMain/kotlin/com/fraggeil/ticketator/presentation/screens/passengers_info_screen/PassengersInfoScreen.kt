package com.fraggeil.ticketator.presentation.screens.passengers_info_screen

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.domain.Constants.MAX_BUTTON_SIZE
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_out_padding
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.InputStyle
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyTextField
import com.fraggeil.ticketator.core.presentation.components.TopBar2
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.domain.model.Journey
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.uzbekistan

@Composable
fun PassengersInfoScreenRoot(
    viewModel: PassengersInfoViewModel,
    navigateBack: () -> Unit,
    navigateToCardInfo: (Journey) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    PassengersInfoScreen(
        state = state,
        onAction = {
            when (it){
                PassengersInfoAction.OnBackClicked -> navigateBack()
                PassengersInfoAction.OnPaymentClicked -> state.selectedJourney?.let { it1 -> navigateToCardInfo(it1) }
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun PassengersInfoScreen(
    state: PassengersInfoState,
    onAction: (PassengersInfoAction) -> Unit
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
                    text = Strings.PassengerInfo.value(),
                    isLeadingButtonVisible = true,
                    onLeadingButtonClick = { onAction(PassengersInfoAction.OnBackClicked) },
                )
                val scrollState = rememberScrollState()
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = vertical_inner_padding)
                        .changeScrollStateByMouse(
                            scrollState = scrollState,
                            isVerticalScroll = true
                        )
                        .verticalScroll(scrollState)
                ){
                    state.selectedJourney?.passengers?.takeIf { it.isNotEmpty() }?.let { passengers ->
                        Column(
                            modifier = Modifier
                                .padding(
                                    bottom = vertical_out_padding
                                )
                                .fillMaxWidth()
                                .background(
                                    color = BG_White,
                                    shape = RoundedCornerShape(Sizes.smallRoundCorner)
                                )
                                .padding(horizontal = Sizes.horizontal_inner_padding)
                            ,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(modifier = Modifier.height(Sizes.vertical_inner_padding))
                            passengers.forEachIndexed { index, passenger ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = if (Strings.selectedLanguage.value in listOf(Language.Uzbek, Language.UzbekCyr)) "${index + 1}-${Strings.Passenger.value().lowercase()}, " else "${Strings.Passenger.value().lowercase()} ${index + 1}, ",
                                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                        color = TextColor
//                                text = "Seat ${passenger.seat}"
                                    )
                                    Text(
                                        text =  if (Strings.selectedLanguage.value in listOf(Language.Uzbek, Language.UzbekCyr)) "${passenger.seat}-${Strings.Seat.value()}" else "${Strings.Seat.value()} ${passenger.seat}",
                                        style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.SemiBold),
                                        color = Blue
                                    )
                                }
                                MyTextField(
                                    modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                                    label = Strings.FullName.value(),
                                    value = passenger.name,
                                    onValueChange = {
                                        onAction(
                                            PassengersInfoAction.OnPassengerNameEntered(
                                                passenger.seat,
                                                it
                                            )
                                        )
                                    },
                                    suggestions = state.suggestionUserNames
                                )
                                MyTextField(
                                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                                    label = Strings.PhoneNumber.value(),
                                    value = passenger.phone,
                                    onValueChange = {
                                        onAction(
                                            PassengersInfoAction.OnPassengerPhoneEntered(
                                                passenger.seat,
                                                it
                                            )
                                        )
                                    },
                                    inputStyle = InputStyle.PHONE_NUMBER,
                                    suggestions = state.suggestionPhones
                                )
                                if (index != passengers.lastIndex) {
                                    HorizontalDivider(
                                        modifier = Modifier.padding(vertical = 24.dp),
                                        color = LightGray
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(Sizes.default_bottom_padding))

                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = Blue.copy(0.2f))
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                MyButton(
                    modifier = Modifier.widthIn(max = MAX_BUTTON_SIZE).fillMaxWidth(),
                    text = Strings.Payment.value(),
                    onClick = { onAction(PassengersInfoAction.OnPaymentClicked) },
                    enabled = state.isAllDataValid,
//                        icon = Icons.Outlined.ShoppingCart
//                    iconPainter = painterResource(Res.drawable.ic_cart)
                )

            }
        }
    }
}