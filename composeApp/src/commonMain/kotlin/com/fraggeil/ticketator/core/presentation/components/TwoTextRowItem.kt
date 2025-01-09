package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.TextColor

enum class TwoTextRowItemStyle{
    SimpleText, LargeText, SmallCard, Card
}

@Composable
fun TwoTextRowItem(
    modifier: Modifier = Modifier,
    text1: String,
    text2: String,
    style: TwoTextRowItemStyle = TwoTextRowItemStyle.SimpleText,
    onText2Clicked: () -> Unit = {},
    isLoading: Boolean = false,
){
    var rowWidth by remember { mutableStateOf(0f) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .onGloballyPositioned {
                rowWidth = it.size.width.toFloat()
            },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            modifier = Modifier.weight(1f),
            text = text1,
            style = when(style){
                TwoTextRowItemStyle.SimpleText -> MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.sp)
                TwoTextRowItemStyle.LargeText -> MaterialTheme.typography.titleMedium.copy(letterSpacing = 0.sp)
                TwoTextRowItemStyle.SmallCard -> MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.sp)
                TwoTextRowItemStyle.Card -> MaterialTheme.typography.bodyMedium.copy(letterSpacing = 0.sp)
            },
            maxLines = 4,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            textAlign = TextAlign.End,
            modifier = Modifier
                .widthIn(max = rowWidth.dp/4*3)
                .then(
                    if (isLoading) Modifier.shimmerLoadingAnimation(
                        isLoadingCompleted = !isLoading,
                        shimmerStyle = ShimmerStyle.TextTitle
                    )else{
                        Modifier.then(
                            when(style){
                                TwoTextRowItemStyle.SimpleText -> Modifier
                                TwoTextRowItemStyle.LargeText -> Modifier
                                TwoTextRowItemStyle.SmallCard -> Modifier.background(color = Blue, shape = RoundedCornerShape(4.dp)).padding(horizontal = 4.dp, vertical = 2.dp)
                                TwoTextRowItemStyle.Card -> Modifier.background(color = Blue, shape = RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp)
                            }
                        ).clickable(
                            onClick = { if (!isLoading) onText2Clicked() },
                            interactionSource = null,
                            indication = null
                        )
                    }
                ),
            text = text2.takeIf { !isLoading } ?: "",
            style = when(style){
                TwoTextRowItemStyle.SimpleText -> MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.sp)
                TwoTextRowItemStyle.LargeText -> MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.sp)
                TwoTextRowItemStyle.SmallCard ->MaterialTheme.typography.bodyMedium.copy( letterSpacing = 0.sp)
                TwoTextRowItemStyle.Card ->MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, letterSpacing = 0.sp)
            },
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            color = when(style){
                TwoTextRowItemStyle.SimpleText -> TextColor
                TwoTextRowItemStyle.LargeText -> TextColor
                TwoTextRowItemStyle.SmallCard, TwoTextRowItemStyle.Card -> BG_White
            }
        )
    }
}