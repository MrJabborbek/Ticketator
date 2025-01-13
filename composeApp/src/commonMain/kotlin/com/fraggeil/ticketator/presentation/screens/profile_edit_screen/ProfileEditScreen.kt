package com.fraggeil.ticketator.presentation.screens.profile_edit_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.horizontal_inner_padding
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.InputStyle
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.presentation.components.MyButtonStyle
import com.fraggeil.ticketator.core.presentation.components.MyTextField
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.TopBar
import com.fraggeil.ticketator.core.presentation.components.changeScrollStateByMouse
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkOnSecondary
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.ErrorColor
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_default_profile
import org.jetbrains.compose.resources.painterResource

@Composable
fun ProfileEditScreenRoot(
    viewModel: ProfileEditViewModel,
    navigateBack: () -> Unit,
    navigateToMain: () -> Unit
){
    LaunchedEffect(Unit){
        viewModel.oneTimeState.collect{oneTimeState ->
            when(oneTimeState){
                ProfileEditOneTimeState.NavigateBack -> navigateBack()
                ProfileEditOneTimeState.NavigateToMain -> navigateToMain()
            }
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ProfileEditScreen(
        state = state,
        onAction = {
            when (it) {
                ProfileEditAction.OnBackClicked -> navigateBack()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun ProfileEditScreen(
    state: ProfileEditState,
    onAction: (ProfileEditAction) -> Unit
){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopBar(
            text = Strings.EditProfile.value(),
            isBackButtonVisible = true,
            onBackButtonClicked = { onAction(ProfileEditAction.OnBackClicked) }
        )
        Box(modifier = Modifier.weight(1f).fillMaxWidth().padding(horizontal = horizontal_inner_padding)) {
            if (state.isLoadingDeleteOrUpdate){
                CircularProgressIndicator(
                    color = BlueDark,
                    modifier = Modifier.align(Alignment.Center)
                )
            }else{
                val scrollState = rememberScrollState()
                Column(
                    modifier = Modifier.fillMaxSize()
                        .changeScrollStateByMouse(
                            isVerticalScroll = true,
                            scrollState = scrollState,
                        ).verticalScroll(scrollState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .sizeIn(maxHeight = 120.dp, maxWidth = 120.dp)
                            .fillMaxWidth().aspectRatio(1f)
                            .clip(RoundedCornerShape(40))
                            .background(color = Blue.copy(0.2f), shape = RoundedCornerShape(40))
                            .shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoadingProfile,
                                shimmerStyle = ShimmerStyle.Custom
                            )
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ){
                        if (!state.isLoadingProfile){
                            Icon(
//                    imageVector = Icons.Default.Home,
                                painter = painterResource(Res.drawable.ic_default_profile),
                                contentDescription = null,
                                tint = BlueDarkSecondary,
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                    MyTextField(
                        isLoading = state.isLoadingProfile,
                        modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
                        label = Strings.LastName.value(),
                        value = state.lastName,
                        onValueChange = { onAction(ProfileEditAction.OnLastNameChanged(it)) },
                    )
                    MyTextField(
                        isLoading = state.isLoadingProfile,
                        modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                        label = Strings.FirstName.value(),
                        value = state.firstName,
                        onValueChange = { onAction(ProfileEditAction.OnFirstNameChanged(it)) },
                    )
                    MyTextField(
                        isLoading = state.isLoadingProfile,
                        modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                        label = Strings.MiddleName.value(),
                        value = state.middleName,
                        onValueChange = { onAction(ProfileEditAction.OnMiddleNameChanged(it)) },
                    )
                    MyTextField(
                        isLoading = state.isLoadingProfile,
                        modifier = Modifier.padding(top = 12.dp).fillMaxWidth(),
                        label = Strings.PhoneNumber.value(),
                        value = state.phoneNumber,
                        onValueChange = { },
                        isEditable = false,
                        inputStyle = InputStyle.PHONE_NUMBER
                    )

                    TextButton(
                        modifier = Modifier.padding(top = 48.dp).align(Alignment.CenterHorizontally)
                            .shimmerLoadingAnimation(
                                isLoadingCompleted = !state.isLoadingProfile,
                                shimmerStyle = ShimmerStyle.Custom
                            ),
                        onClick = { if (!state.isLoadingProfile) onAction(ProfileEditAction.OnDeleteProfileClicked) }
                    ){
                        Text(
                            text = Strings.DeleteProfile.value().takeIf { !state.isLoadingProfile } ?: "",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.sp
                            ),
                            color = ErrorColor,
                        )
                    }
                    Spacer(modifier = Modifier.height(Sizes.default_bottom_padding))
                }
            }
        }
        if (!state.isLoadingProfile && !state.isLoadingDeleteOrUpdate){
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth().padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ){
                MyButton(
                    modifier = Modifier.weight(1f),
                    text = Strings.Cancel.value(),
                    onClick = { onAction(ProfileEditAction.OnBackClicked) },
                    style = MyButtonStyle.OUTLINED
                )
                MyButton(
                    modifier = Modifier.weight(1f),
                    text = Strings.Save.value(),
                    onClick = { onAction(ProfileEditAction.OnSaveClicked) },
                )
            }
        }
    }
}