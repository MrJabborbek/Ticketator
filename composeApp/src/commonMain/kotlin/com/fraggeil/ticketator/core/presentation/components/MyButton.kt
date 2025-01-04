package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.LightGray
import com.fraggeil.ticketator.core.theme.White

enum class MyButtonStyle{ OUTLINED, FILLED, SMALL }
@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isSelected: Boolean = true,
    isLoading: Boolean = false,
    icon: ImageVector? = null,
    iconPainter: Painter? = null,
    style: MyButtonStyle = MyButtonStyle.FILLED,
){
    @Composable
    fun content() = Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        if (isLoading){
            CircularProgressIndicator(
                modifier = Modifier.size(if (style == MyButtonStyle.SMALL) 12.dp else 20.dp),
                color = BG_White,
                strokeWidth = 2.dp
            )
        }else {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size( if (style == MyButtonStyle.SMALL) 12.dp else 20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            } else if (iconPainter != null) {
                Icon(
                    painter = iconPainter,
                    contentDescription = null,
                    modifier = Modifier.size( if (style == MyButtonStyle.SMALL) 12.dp else 20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = if (style == MyButtonStyle.SMALL){
                    MaterialTheme.typography.titleMedium.copy(
//                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp
                    )
                } else{
                    MaterialTheme.typography.titleSmall.copy(
//                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        letterSpacing = 0.sp
                    )
                },
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
//                color = if (enabled) DesertWhite else ,
            )
        }
    }


    when(style){
        MyButtonStyle.FILLED -> {
            Button(
                shape = RoundedCornerShape(100),
                modifier = modifier,
                onClick = onClick,
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors().copy(containerColor = if (isSelected) Blue else Blue.copy(0.2f) ),
                contentPadding = PaddingValues(16.dp)
            ){
                content()
            }
        }
        MyButtonStyle.OUTLINED -> {
            OutlinedButton(
                shape = RoundedCornerShape(100),
                modifier = modifier,
                onClick = onClick,
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.outlinedButtonColors().copy(containerColor = BG_White, contentColor = BlueDark),
                border = BorderStroke(width = 1.dp, color = if (isSelected) BlueDark else Blue.copy(0.2f)),
                contentPadding = PaddingValues(16.dp)
            ){
                content()
            }
        }

        MyButtonStyle.SMALL -> {
            Button(
                shape = RoundedCornerShape(100),
                modifier = modifier,
                onClick = onClick,
                enabled = enabled && !isLoading,
                colors = ButtonDefaults.buttonColors().copy(containerColor = if (isSelected) Blue else Blue.copy(0.2f), contentColor = if (isSelected) White else BlueDark),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ){
                content()
            }
        }
    }

}