package com.fraggeil.ticketator.presentation.screens.login_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.InputStyle
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyTextField
import com.fraggeil.ticketator.core.presentation.components.TopBar
import com.fraggeil.ticketator.core.presentation.components.TopBar2
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.presentation.screens.select_seat_screen.SelectSeatAction
import ticketator.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.ticketator_logo

@Composable
fun LoginScreenRoot(
    viewModel: LoginViewModel,
    navigateBack: () -> Unit,
    navigateToOtp: (phoneNumber: String, token: String) -> Unit,
    navigateToTermsAndConditions: () -> Unit
){
    LaunchedEffect(Unit){
        viewModel.oneTimeState.collect{oneTimeState ->
            when (oneTimeState){
                is LoginOneTimeState.NavigateToOtpScreen -> navigateToOtp(oneTimeState.phoneNumber, oneTimeState.token)
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(
        state = state,
        onAction = {
            when (it) {
                LoginAction.OnBackClicked -> navigateBack()
                LoginAction.OnTermsAndConditionsClicked -> navigateToTermsAndConditions()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginState,
    onAction: (LoginAction) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar2(
            text = Strings.Login.value(),
            isLeadingButtonVisible = true,
            isLightMode = true,
            onLeadingButtonClick = { onAction(LoginAction.OnBackClicked) },
        )
        Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = horizontal_inner_padding)) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .changeScrollStateByMouse(
                        isVerticalScroll = true,
                        scrollState = scrollState,
                    )
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Image(
//                    imageVector = Icons.Default.Home,
                    painter = painterResource(Res.drawable.ticketator_logo),
                    contentDescription = null,
                    modifier = Modifier.sizeIn(maxHeight = 80.dp, maxWidth = 80.dp).fillMaxSize()
                )
                Text(
                    text = Strings.AppName.value(),
                    color = TextColor,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = Strings.EnterYourPhoneNumberToLogIn.value(),
                    color = TextColor,
                    style = MaterialTheme.typography.bodyMedium.copy(
//                fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    textAlign = TextAlign.Center,

                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.height(32.dp))
                MyTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = Strings.PhoneNumber.value(),
                    value = state.phoneNumber,
                    onValueChange = { onAction(LoginAction.OnPhoneNumberChanged(it)) },
                    inputStyle = InputStyle.PHONE_NUMBER

//                    prefix = "+998"
                )
                MyButton(
                    isLoading = state.isLoading,
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    text = Strings.Login.value(),
                    onClick = { onAction(LoginAction.OnLoginClicked) }
                )
                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = Strings.ByAuthorisationYouAgreeText.value(),
                    color = TextColor,
                    style = MaterialTheme.typography.bodySmall.copy(
//                fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Text(
                    text = Strings.YouWillAgreeText.value(),
                    color = Blue,
                    style = MaterialTheme.typography.bodySmall.copy(
//                fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.CenterHorizontally).clickable(
                        onClick = { onAction(LoginAction.OnTermsAndConditionsClicked)},
                        indication = null,
                        interactionSource = null
                    )
                )
            }
        }
    }
}

