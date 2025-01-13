package com.fraggeil.ticketator.presentation.screens.otp_payment_screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.FlashingCursorIndicator
import com.fraggeil.ticketator.core.presentation.components.MyCircularButton
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.ErrorColor
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.core.theme.White
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_close
import kotlin.math.sin

@Composable
fun OtpPaymentScreenRoot(
    viewModel: OtpPaymentViewModel,
    navigateBack: () -> Unit,
    navigateToTickets: () -> Unit
){

    LaunchedEffect(Unit){
        viewModel.oneTimeState.collect{oneTimeState ->
            when(oneTimeState){
                OtpPaymentOneTimeState.NavigateToTickets -> {
                    navigateToTickets()
                }
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    OtpPaymentScreen(
        state = state,
        onAction = {
            when(it){
                OtpPaymentAction.OnBackClicked -> navigateBack()
                else -> Unit
            }
            viewModel.onAction(it)
        }

    )

}

@Composable
fun OtpPaymentScreen(
    state: OtpPaymentState,
    onAction: (OtpPaymentAction) -> Unit
){
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit){
        focusRequester.requestFocus() // initially focus on the text field
    }
    Column(
        modifier = Modifier.fillMaxSize().background(BlueDark),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .padding(horizontal = Sizes.horizontal_out_padding)
                .padding(top = vertical_inner_padding).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            MyCircularButton(
                icon = painterResource(Res.drawable.ic_close),
                onClick = { onAction(OtpPaymentAction.OnBackClicked) }
            )
//            Text(
//                text = "Card info",
//                color = White,
//                style = AppTypography().headlineSmall.copy(fontWeight = FontWeight.Medium),
//                modifier = Modifier.padding(vertical = vertical_out_padding).weight(1f),
//                textAlign = TextAlign.Start,
//                maxLines = 2,
//                overflow = TextOverflow.Ellipsis
//            )
        }
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding( start = horizontal_inner_padding, end = horizontal_inner_padding, top = 40.dp)
                .sizeIn(maxWidth = 280.dp)
                .fillMaxWidth()
                .changeScrollStateByMouse(
                    isVerticalScroll = true,
                    scrollState = scrollState,
                )
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    append(Strings.OtpSentText1.value())
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ){
                        append(state.phoneNumber)
                    }
                    append(Strings.OtpSentText2.value())
                },
                textAlign = TextAlign.Center,
                color = White,
                style = MaterialTheme.typography.titleMedium
            )
            Row(
                modifier = Modifier.padding(top = 40.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ){
                BasicTextField(
                    value = state.otp,
                    onValueChange = {
                        onAction(OtpPaymentAction.OnOtpChanged(it))
                    },
                    modifier = Modifier.weight(1f).focusRequester(focusRequester),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    )
                ){
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ){
                        val infiniteTransition = rememberInfiniteTransition()
                        val alpha by infiniteTransition.animateFloat(
                            initialValue = 1f,
                            targetValue = 0f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(durationMillis = 1000, easing = LinearEasing),
                                repeatMode = RepeatMode.Reverse
                            )
                        )
                        repeat(5){ index ->
                            val number = when{
                                index >= state.otp.length -> ""
                                else -> state.otp[index].toString()
                            }
                            val isFocused = index == state.otp.length
                            Box(
                                modifier = Modifier
                            .weight(1f)
                            .aspectRatio(5/6f)
                            .background(
                                color = LightGray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                width = 2.dp,
                                color = if (state.isLoading) LightGray.copy(alpha = alpha + index*sin(alpha))
                                else if (isFocused) TextColor
                                else if (state.isErrorMode) ErrorColor
                                else Color.Transparent,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                if(number == "" && isFocused){
                                   FlashingCursorIndicator(indicatorColor = BlueDark)
                                }
                                Text(
                                    text = number,
                                    style = MaterialTheme.typography.titleLarge,
                                )
                            }
                        }
                    }
                }
            }
            TextButton(
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 24.dp),
                onClick = { onAction(OtpPaymentAction.OnResendButtonClicked) },
                enabled = state.isResendEnabled,
                colors = ButtonDefaults.textButtonColors().copy(
                    contentColor = White,
                    disabledContentColor = TextColorLight,
                    containerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                )
            ){
                Text(
                    text = "${Strings.ResendIn.value()} ${state.timer}",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    )
                )
            }
        }
    }
}

