package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkOnSecondary
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White

@Composable
fun MyCircularButton(
    icon: Painter,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
){
    IconButton(
        modifier = Modifier
            .background(
                color = BlueDarkSecondary,
                shape = CircleShape
            )
            .border(
            width = 1.dp,
            color = BlueDarkOnSecondary,
            shape = CircleShape
        )
            .padding(4.dp),
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = BlueDarkSecondary,
            contentColor = White
        ),
        onClick = { onClick() }
    ) {
        Icon(
            tint = White,
            painter = icon,
            contentDescription = null
        )
    }
}