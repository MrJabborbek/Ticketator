package com.fraggeil.ticketator.presentation.screens.start_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.presentation.components.MyButton
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BlueDark
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.dust
import ticketator.composeapp.generated.resources.image
import ticketator.composeapp.generated.resources.leaf


@Composable
fun StartScreenRoot(
    viewModel: StartViewModel,
    navigateToHome: () -> Unit
) {
    StartScreen(
        onAction = {
            when(it){
                StartAction.OnStartClick -> navigateToHome()
                else -> {}
            }
            viewModel.onAction(it)
        }
    )

}

@Composable
private fun StartScreen(
    onAction: (StartAction) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painterResource(Res.drawable.image),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd).fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.BottomEnd
        )
        Box(
            Modifier.align(Alignment.BottomCenter).padding(start = 32.dp, end = 32.dp).widthIn(max = 400.dp).fillMaxWidth(),
        ){
            MyButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Start",
                onClick = {
                    onAction(StartAction.OnStartClick)
                }
            )
            Image(
                painter = painterResource(Res.drawable.leaf),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomEnd).size(120.dp)
            )
        }

        Text(
            text = "Search For Flights To your Destination",
            textAlign = TextAlign.Center,
            maxLines = 4,
            style = AppTypography().displayMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = BlueDark,
            modifier = Modifier.padding(top = 128.dp, start = 32.dp, end = 32.dp).align(Alignment.TopCenter).widthIn(max = 400.dp).fillMaxWidth()
        )
        Image(
            painter = painterResource(Res.drawable.dust),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomEnd).fillMaxHeight(),
            contentScale = ContentScale.FillHeight,
            alignment = Alignment.BottomEnd
        )
    }
}