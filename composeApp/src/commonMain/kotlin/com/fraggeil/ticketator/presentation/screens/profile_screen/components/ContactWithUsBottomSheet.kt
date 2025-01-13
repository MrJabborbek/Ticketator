package com.fraggeil.ticketator.presentation.screens.profile_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.BlueDark
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_payment_tg
import ticketator.composeapp.generated.resources.ic_phone_payment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactWithUsBottomSheet(
    onCallClicked: () -> Unit,
    onTelegramClicked: () -> Unit,
    onDismiss: () -> Unit
){
    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
        dragHandle = {},
        containerColor = BG_White
    ){
        Column(
            modifier = Modifier.fillMaxWidth().padding(start = 24.dp, end = 24.dp, top = 32.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = Strings.ContactWithUs.value(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.padding(top = 32.dp).fillMaxWidth()
                    .clickable(onClick = {onCallClicked()}, interactionSource = null, indication = null),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Icon(
//                    imageVector = Icons.Outlined.Call,
                    painter = painterResource(Res.drawable.ic_phone_payment),
                    contentDescription = null,
                    tint = BlueDark,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = Strings.CallViaPhone.value(),
                    style = MaterialTheme.typography.bodyLarge.copy(
//                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Row(
                modifier = Modifier.padding(top = 24.dp).fillMaxWidth()
                    .clickable(onClick = {onTelegramClicked()}, interactionSource = null, indication = null),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Icon(
//                    imageVector = Icons.AutoMirrored.Outlined.Send,
                    painter = painterResource(Res.drawable.ic_payment_tg),
                    contentDescription = null,
                    tint = BlueDark,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = Strings.ContactViaTelegram.value(),
                    style = MaterialTheme.typography.bodyLarge.copy(
//                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}