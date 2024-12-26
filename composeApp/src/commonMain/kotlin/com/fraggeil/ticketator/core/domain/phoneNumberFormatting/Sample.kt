package com.fraggeil.ticketator.core.domain.phoneNumberFormatting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.intl.Locale
import com.fraggeil.ticketator.core.domain.phoneNumberFormatting.PhoneNumberVisualTransformation
import io.michaelrocks.libphonenumber.kotlin.PhoneNumberUtil
import io.michaelrocks.libphonenumber.kotlin.metadata.defaultMetadataLoader

@Composable
fun Sample() {
    val phoneNumberUtil: PhoneNumberUtil by remember {
        mutableStateOf(PhoneNumberUtil.createInstance(defaultMetadataLoader()))
    }
    var examplePhoneNumberToFormat by remember { mutableStateOf("909741797") }
    var examplePhoneNumberFormatted by remember { mutableStateOf(false) }
    var asYouTypeFormatterText by remember { mutableStateOf("") }
    val region = remember {
        try {
            Locale.current.region
            "UZ"
        } catch (e: Exception) {
            // as of compose 1.4.3, js fails to get the region so default to US
            "UZ"
        }
    }
        Column(Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Button(onClick = {
                    val phoneNumber = phoneNumberUtil.parse(examplePhoneNumberToFormat, region)
                    examplePhoneNumberToFormat = phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
                    examplePhoneNumberFormatted = true
                }) {
                    Text(if (!examplePhoneNumberFormatted) "Click to format" else "Formatted")
                }
                Text("Phone number: $examplePhoneNumberToFormat")
            }
            Row {
                OutlinedTextField(
                    value = asYouTypeFormatterText,
                    onValueChange = { text: String ->
                        asYouTypeFormatterText = text
                    },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("AsYouTypeFormatter Input") },
                    singleLine = true,
                    visualTransformation = PhoneNumberVisualTransformation(phoneNumberUtil, region),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
                )
            }
        }

}