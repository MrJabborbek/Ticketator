package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.TextColor
import ticketator.composeapp.generated.resources.im_profile
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res

@Composable
fun YouAreNotLoggedInMessage(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit,
    isLoading: Boolean = false
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier.sizeIn(maxWidth = 80.dp, maxHeight = 80.dp).fillMaxSize()
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.Custom
                ),
            contentAlignment = Alignment.Center
        ){
            if (!isLoading){
                Icon(
                    painter = painterResource(Res.drawable.im_profile),
                    contentDescription = null,
                    tint = Blue,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
        Text(
            text = Strings.YouAreNotLoggedIn.value().takeIf{!isLoading} ?: "",
            color = TextColor,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp)
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.TextTitle
                )
        )
        Text(
            text = Strings.LoginToContinue.value().takeIf{!isLoading} ?: "",
            color = TextColor,
            style = MaterialTheme.typography.bodyMedium.copy(
//                        fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 4.dp)
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.TextBody
                )
        )
        if (!isLoading){
            MyButton(
                modifier = Modifier.padding(top = 16.dp).widthIn(max = 150.dp).fillMaxWidth(),
                text = Strings.Login.value(),
                onClick = onLoginClicked
            )
        }
    }
}