package com.fraggeil.ticketator.presentation.screens.otp_payment_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.MyOtpTextField
import com.fraggeil.ticketator.core.presentation.components.TopBar2
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.TextColorLight
import com.fraggeil.ticketator.core.theme.White
import com.fraggeil.ticketator.domain.model.Ticket

@Composable
fun OtpPaymentScreenRoot(
    viewModel: OtpPaymentViewModel,
    navigateBack: () -> Unit,
    navigateToTickets: (List<Ticket>) -> Unit
){
    LaunchedEffect(Unit){
        viewModel.oneTimeState.collect{oneTimeState ->
            when(oneTimeState){
                is OtpPaymentOneTimeState.NavigateToTickets -> { navigateToTickets(oneTimeState.tickets) }
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
        TopBar2(
            text = "",
            isLeadingButtonVisible = true,
            onLeadingButtonClick = { onAction(OtpPaymentAction.OnBackClicked) },
        )
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .padding( start = horizontal_inner_padding, end = horizontal_inner_padding, top = 24.dp)
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
                MyOtpTextField(
                    modifier = Modifier.weight(1f),
                    value = state.otp,
                    isLoading = state.isLoading,
                    focusRequester = focusRequester,
                    isErrorMode = state.isErrorMode,
                    onValueChange = {
                        onAction(OtpPaymentAction.OnOtpChanged(it))
                    }
                )
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

