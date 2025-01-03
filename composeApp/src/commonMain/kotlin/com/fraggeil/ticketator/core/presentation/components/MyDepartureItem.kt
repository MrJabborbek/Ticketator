package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.theme.AppTypography
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight

@Composable
fun MyDepartureItem(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    label: String,
    text: String?,
    abbr: String?,
    onClick: () -> Unit,
    isLoading: Boolean,
    isEnabled: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clip(shape = RoundedCornerShape(Sizes.smallRoundCorner))
            .then(
                if (isEnabled && !isLoading) Modifier.clickable {onClick()} else Modifier
            )
            .background(color =Blue.copy(alpha = 0.05f),
                shape = RoundedCornerShape(Sizes.smallRoundCorner))
            .shimmerLoadingAnimation(
                isLoadingCompleted = !isLoading,
                shimmerStyle = ShimmerStyle.Custom
            )
            .padding(Sizes.horizontal_inner_padding),
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isEnabled && !isLoading) Blue else Blue.copy(alpha = 0.1f),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = abbr?.uppercase()?:"AA",
                style = AppTypography().headlineLarge.copy(fontWeight = FontWeight.Bold),
                color = if (abbr != null && isEnabled && !isLoading) TextColor else Color.Transparent,
                )
        }
        if (!isLoading) VerticalDivider(modifier = Modifier.padding(horizontal = Sizes.horizontal_inner_padding), color = TextColorLight)
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ){
            Text(
                text = label.takeIf { !isLoading } ?:"",
                style = if (text == null) AppTypography().titleLarge else AppTypography().bodyLarge,
                color = if (isEnabled) TextColorLight else TextColorLight.copy(alpha = 0.5f)
            )
            text?.let {
                Text(
                    text = text.takeIf { !isLoading } ?:"",
                    style = AppTypography().bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = if (isEnabled) TextColor else TextColor.copy(alpha = 0.5f),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}