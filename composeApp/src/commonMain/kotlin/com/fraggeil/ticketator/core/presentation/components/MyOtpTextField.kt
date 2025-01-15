package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.ErrorColor
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.TextColor
import kotlin.math.sin

@Composable
fun MyOtpTextField(
    modifier: Modifier = Modifier,
    value: String,
    isLoading: Boolean,
    isErrorMode: Boolean,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester,
    maxLength: Int = 5
) {
    BasicTextField(
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    ){
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 1000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )
            repeat(maxLength){ index ->
                val number = when{
                    index >= value.length -> ""
                    else -> value[index].toString()
                }
                val isFocused = index == value.length
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(5/6f)
                        .background(
                            color = LightGray,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 2.dp,
                            color = if (isLoading) BlueDark.copy(alpha = alpha + index* sin(alpha))
                            else if (isFocused) TextColor
                            else if (isErrorMode) ErrorColor
                            else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    if(number == "" && isFocused){
                        FlashingCursorIndicator(indicatorColor = BlueDark)
                    }
                    Text(
                        text = number,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
        }
    }
}