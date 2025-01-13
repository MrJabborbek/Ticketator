package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor

@Composable
fun ButtonWithIconAndArrow(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
//    icon: @Composable () -> Unit,
    icon: ImageVector? = null,
    iconPainter: Painter? = null,
    isLoading: Boolean = false
){
    Row(
        modifier = modifier.clickable(onClick = { onClick() }, interactionSource = null, indication = null),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ){
        Box(
            modifier = Modifier
                .size(36.dp)
                .aspectRatio(1f)
                .background(color = LightGray, shape = RoundedCornerShape(12.dp))
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.Custom
                )
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            if (!isLoading){
                icon?.let{
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Blue,
                    )
                } ?: iconPainter?.let {
                    Icon(
                        painter = iconPainter,
                        contentDescription = null,
                        tint = Blue,
                    )
                }
            }
        }
        Text(
            text = text.takeIf { !isLoading } ?: "",
            color = TextColor,
            style = MaterialTheme.typography.titleMedium.copy(
//                fontWeight = FontWeight.Bold,
                letterSpacing = 0.sp
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f)
                .shimmerLoadingAnimation(
                    isLoadingCompleted = !isLoading,
                    shimmerStyle = ShimmerStyle.TextTitle
                )
        )
        if (!isLoading){
            Icon(
                imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
                contentDescription = null,
                tint = TextColor,
            )
        }
    }
}