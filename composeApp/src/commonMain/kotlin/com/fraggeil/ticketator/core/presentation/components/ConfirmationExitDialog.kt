package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.BlueDarkSecondary
import com.fraggeil.ticketator.core.theme.White
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ConfirmationExitDialog(
    onCancelled: () -> Unit,
    onConfirmed: () -> Unit,
    message: String = Strings.AreYouSureYouWantToCancel.value(),
    title: String = Strings.Cancel.value(),
    confirmButtonText: String = Strings.Exit.value(),
    dismissButtonText: String = Strings.Stay.value(),
    isDismissButtonVisible: Boolean = true
){
    AlertDialog(
        containerColor = White,
        onDismissRequest = onCancelled ,
        confirmButton = { TextButton(onClick = onConfirmed){ Text(text = confirmButtonText, color = Blue) } },
        dismissButton = { if(isDismissButtonVisible) TextButton(onClick = onCancelled){ Text(text = dismissButtonText, color = BlueDarkSecondary) } },
        title = { Text(text =title) },
        text = { Text(text = message) },
        properties = DialogProperties(dismissOnBackPress = isDismissButtonVisible, dismissOnClickOutside = isDismissButtonVisible)
    )
}

@Composable
@Preview
private fun ConfirmationExitDialogPreview(){
    ConfirmationExitDialog(onCancelled = {}, onConfirmed = {})
}