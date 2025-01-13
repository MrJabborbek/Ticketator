package com.fraggeil.ticketator.presentation.screens.search_results_screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.presentation.components.DottedHorizontalDivider
import com.fraggeil.ticketator.core.presentation.components.ShimmerStyle
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.White
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.bus

@Composable
fun Dots(
    modifier: Modifier = Modifier.fillMaxWidth(),
    iconColor: Color,
    dotsColor: Color,
    pinColor: Color,
    topText: String = "",
    bottomText: String = "",
    isLoading: Boolean = false
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ){
        Text(
            text = topText.takeIf { !isLoading } ?:"",
            modifier = Modifier.shimmerLoadingAnimation(
                isLoadingCompleted = !isLoading,
                modifier = Modifier.width(100.dp),
                shimmerStyle = ShimmerStyle.TextSmallLabel
            ),
            color = BlueDark,
            style = AppTypography().bodyMedium.copy(fontWeight = FontWeight.Medium),
            maxLines = 1,
            textAlign = TextAlign.Center
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading
                ),
            verticalAlignment = Alignment.CenterVertically
        ){
            if (!isLoading){
                Icon(
                    painter = painterResource(Res.drawable.bus),
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.width(40.dp)
                )
                DottedHorizontalDivider(modifier = Modifier.weight(1f), color = dotsColor)
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(shape = CircleShape)
                        .background(color = pinColor, shape = CircleShape)
                        .border(width = (1.5).dp, color = White, shape = CircleShape)
                )
            }
        }
        Text(
            text = bottomText.takeIf { !isLoading } ?:"",
            modifier = Modifier.shimmerLoadingAnimation(
                isLoadingCompleted = !isLoading,
                modifier = Modifier.width(100.dp),
                shimmerStyle = ShimmerStyle.TextSmallLabel
            ),
            color = BlueDark,
            style = AppTypography().bodySmall,
            maxLines = 2,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

