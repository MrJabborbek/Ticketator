package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight

@Composable
fun MyInfoButton(
    modifier: Modifier = Modifier,
    label: String,
    text: String?,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(shape = RoundedCornerShape(Sizes.smallRoundCorner))
            .clickable {
                onClick()
            }
            .background(color = Blue.copy(alpha = 0.05f), shape = RoundedCornerShape(Sizes.smallRoundCorner))
            .padding(Sizes.horizontal_inner_padding),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Text(
            text = label,
            style = if (text != null) AppTypography().bodyLarge else AppTypography().titleLarge,
            color = TextColorLight
        )
        if (text != null) {
            Text(
                text = text,
                style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = TextColor,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}