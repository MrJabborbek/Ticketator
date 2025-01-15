package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkOnSecondary
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White

@Composable
fun MyCircularButton(
    modifier: Modifier = Modifier,
    icon: Painter? = null,
    imageVector: ImageVector? = null,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    darkColor: Color = BlueDarkSecondary,
    lightColor: Color = BG_White,
    isLightMode: Boolean = false
){
    IconButton(
        modifier = modifier
            .background(
                color = if (isLightMode) lightColor else darkColor,
                shape = CircleShape
            )
            .border(
            width = 1.dp,
            color = if (!isLightMode) lightColor else darkColor,
            shape = CircleShape
        ),
        colors = IconButtonDefaults.iconButtonColors().copy(
            containerColor = if (isLightMode) lightColor else darkColor,
            contentColor = if (!isLightMode) lightColor else darkColor
        ),
        onClick = { onClick() }
    ) {
        if (icon != null){
            Icon(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                painter = icon,
                contentDescription = null
            )
        } else if (imageVector != null){
            Icon(
                modifier = Modifier.fillMaxSize().padding(8.dp),
                imageVector = imageVector,
                contentDescription = null
            )
        }
    }
}