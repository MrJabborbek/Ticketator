package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Sizes.vertical_inner_padding
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White

@Composable
fun TopBar2(
    modifier: Modifier = Modifier.padding(horizontal = Sizes.horizontal_out_padding, vertical = vertical_inner_padding).fillMaxWidth(),
    text: String,
    isTextCentered: Boolean = true,
    isLeadingButtonVisible: Boolean = false,
    isTrailingButtonVisible: Boolean = false,
    onLeadingButtonClick: () -> Unit = {},
    onTrailingButtonClick: () -> Unit = {},
    leadingButtonIcon: ImageVector? = Icons.AutoMirrored.Default.ArrowBack,
    trailingButtonIcon: Painter? = null,
    isLightMode: Boolean = false,
) {
    var width by remember { mutableStateOf(0) }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ){
        if (isLeadingButtonVisible){
            MyCircularButton(
                isLightMode = isLightMode,
                modifier = Modifier.onGloballyPositioned {
                    width = maxOf(width, it.size.width)
                },
                imageVector = leadingButtonIcon,
                onClick = { onLeadingButtonClick() }
            )
        }
        Text(
            text = text,
            color = if (!isLightMode) BG_White else BlueDarkSecondary,
            style = AppTypography().headlineSmall.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier
                .then(
                    if (isTextCentered)
                        Modifier.padding(start = if (!isLeadingButtonVisible) width.dp else 0.dp, end = if (!isTrailingButtonVisible) width.dp else 0.dp)
                    else Modifier
                ).weight(1f),
            textAlign = if (isTextCentered) TextAlign.Center else TextAlign.Start,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        if (isTrailingButtonVisible) {
            if (isLeadingButtonVisible){
                MyCircularButton(
                    isLightMode = isLightMode,
                    modifier = Modifier.onGloballyPositioned {
                        width = maxOf(width, it.size.width)
                    },
                    icon = trailingButtonIcon,
                    onClick = { onTrailingButtonClick() }
                )
            }
        }
    }
}