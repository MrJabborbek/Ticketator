package com.fraggeil.ticketator.presentation.screens.profile_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.presentation.screens.profile_screen.components.ContactWithUsBottomSheet
import com.fraggeil.ticketator.presentation.screens.profile_screen.components.LanguageBottomSheet
import com.fraggeil.ticketator.core.domain.phoneNumberFormatting.formatPhoneNumber
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.domain.DialPhoneNumber
import com.fraggeil.ticketator.core.domain.OpenUrlInBrowser
import com.fraggeil.ticketator.core.presentation.StatusBarColors
import com.fraggeil.ticketator.core.presentation.Sizes.default_bottom_padding
import com.fraggeil.ticketator.core.presentation.Sizes.default_bottom_padding_double
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.components.ButtonWithIconAndArrow
import com.fraggeil.ticketator.core.presentation.components.YouAreNotLoggedInMessage
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.ErrorColor
import com.fraggeil.ticketator.core.theme.TextColor
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_default_profile
import ticketator.composeapp.generated.resources.ic_feedback
import ticketator.composeapp.generated.resources.ic_how_to_use
import ticketator.composeapp.generated.resources.ic_lang
import ticketator.composeapp.generated.resources.ic_offer
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun ProfileScreenRoot(
    viewModel: ProfileViewModel,
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
    navigateToMain: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToAboutApp: () -> Unit,
    navigateToTermsAndConditions: () -> Unit,
){
    StatusBarColors(isDarkText = true)

    val dialPhoneNumber = koinInject<DialPhoneNumber>()
    val openUrlInBrowser = koinInject<OpenUrlInBrowser>()

    LaunchedEffect(Unit){
       viewModel.oneTimeState.collect{oneTimeState ->
           when(oneTimeState){
               ProfileOneTimeState.NavigateToMain -> navigateToMain()
           }
       }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileScreen(
        state = state,
        onAction = {
            when (it) {
                ProfileAction.OnBackClicked -> navigateBack()
                ProfileAction.OnLoginClicked -> navigateToLogin()
                ProfileAction.OnProfileClicked -> navigateToEditProfile()
                ProfileAction.OnAboutAppClicked -> navigateToAboutApp()
                ProfileAction.OnTermsAndConditionsClicked -> navigateToTermsAndConditions()
                ProfileAction.OnCallClicked -> dialPhoneNumber.dialPhoneNumber("+99812345678")
                ProfileAction.OnTelegramClicked -> openUrlInBrowser.open("https://t.me/fraggeil")
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun ProfileScreen(
    state: ProfileState,
    onAction: (ProfileAction) -> Unit
){
    var height by remember { mutableStateOf(0) }
    val density = LocalDensity.current.density
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = horizontal_inner_padding)
            .changeScrollStateByMouse(
                scrollState = scrollState,
                isVerticalScroll = true
            )
            .verticalScroll(scrollState)
            .onGloballyPositioned {
            height = it.size.height
        },
    ){
        Box(modifier = Modifier.statusBarsPadding())
        state.profile?.let { user ->
            Row(
                modifier = Modifier.padding(top = 16.dp).clickable (onClick =  {onAction(
                    ProfileAction.OnProfileClicked)}, interactionSource = null, indication = null),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                Icon(
//                    imageVector = Icons.Default.Person,
                    painter = painterResource(Res.drawable.ic_default_profile),
                    contentDescription = null,
                    tint = BlueDark,
                    modifier = Modifier.size(64.dp).background(color = BG_White, shape = CircleShape).padding(12.dp)
                )
                Text(
                    text = user.phoneNumber.formatPhoneNumber(),
                    color = TextColor,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = TextColor,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            ButtonWithIconAndArrow(
                isLoading = state.isLoadingProfile,
                modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                text = Strings.ContactWithUs.value(),
                onClick = {onAction(ProfileAction.OnContactWithUsClicked)},
//                icon = Icons.Outlined.Phone,
                iconPainter = painterResource(Res.drawable.ic_feedback)
            )
        } ?: kotlin.run {
            Box(modifier = Modifier.height((height * 0.15/density).dp))

            YouAreNotLoggedInMessage(
                isLoading = state.isLoadingProfile,
                modifier = Modifier.fillMaxWidth(),
                onLoginClicked = { onAction(ProfileAction.OnLoginClicked) }
            )
            Spacer(Modifier.height(height = default_bottom_padding))
        }
        Text(
            text = Strings.Settings.value().takeIf { !state.isLoadingProfile } ?: "",
            modifier = Modifier.padding(top = 32.dp).fillMaxWidth()
                .shimmerLoadingAnimation(isLoadingCompleted = !state.isLoadingProfile, shimmerStyle = ShimmerStyle.TextTitle),
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Start,
            color = TextColor,
            overflow = TextOverflow.Ellipsis
        )
        ButtonWithIconAndArrow(
            isLoading = state.isLoadingProfile,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            text = Strings.Language_.value(),
            onClick = {onAction(ProfileAction.OnLanguageClicked)},
//            icon = Icons.Outlined.Done,
            iconPainter = painterResource(Res.drawable.ic_lang)
        )
        ButtonWithIconAndArrow(
            isLoading = state.isLoadingProfile,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            text = Strings.AboutApp.value(),
            onClick = {onAction(ProfileAction.OnAboutAppClicked)},
//            icon = Icons.Outlined.Done,
            iconPainter = painterResource(Res.drawable.ic_how_to_use)

        )
        ButtonWithIconAndArrow(
            isLoading = state.isLoadingProfile,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
            text = Strings.TermsAndConditions.value(),
            onClick = {onAction(ProfileAction.OnTermsAndConditionsClicked)},
//            icon = Icons.Outlined.Done,
            iconPainter = painterResource(Res.drawable.ic_offer)

        )

        if (state.profile != null && !state.isLoadingProfile){
            TextButton(
                modifier = Modifier.padding(top = 48.dp).align(Alignment.CenterHorizontally) ,
                onClick = { onAction(ProfileAction.OnLogoutClicked) }
            ){
                Text(
                    text = Strings.LogOut.value(),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    color = ErrorColor,
                )
            }
        }
        Spacer(modifier = Modifier.height(default_bottom_padding_double))
    }
    if (state.isContactWithUsOpen){
        ContactWithUsBottomSheet(
            onCallClicked = { onAction(ProfileAction.OnCallClicked) },
            onTelegramClicked = { onAction(ProfileAction.OnTelegramClicked) },
            onDismiss = { onAction(ProfileAction.OnContactWithUsDismiss) }
        )
    }
    if (state.isLanguageSelectorOpen){
        LanguageBottomSheet(
            isLoading = state.isLoadingLanguage,
            languages = Language.entries,
            onLanguageClicked = { onAction(ProfileAction.OnLanguageSelected(it)) },
            selectedLanguage = state.currentLanguage,
            onDismiss = { onAction(ProfileAction.OnDismissLanguageSelector) }
        )
    }
}