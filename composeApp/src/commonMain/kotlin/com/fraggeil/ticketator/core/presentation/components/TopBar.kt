package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.TextColor
import com.fraggeil.ticketator.core.theme.TextColorLight
import org.jetbrains.compose.resources.painterResource
import ticketator.composeapp.generated.resources.Res
import ticketator.composeapp.generated.resources.ic_close

@Composable
fun TopBar(
        modifier: Modifier = Modifier,
        text: String = "",
        isBackButtonVisible: Boolean = false,
        isTextButtonVisible: Boolean = false,
        textButton: String = "",
        isEditable: Boolean = false,
        onSearchClicked: () -> Unit = {},
        query: String = "",
        hint: String = "",
        onQueryChanged: (String) -> Unit = {},
        isProgressBarVisible: Boolean = false,
        onBackButtonClicked: () -> Unit = {},
        onTextButtonClicked: () -> Unit = {},
        isTextCentered: Boolean = false,
        backButton: @Composable (onClick:()->Unit) -> Unit = { DefaultBackButton(onBackButtonClicked = it) },
){
    val keyboardController = LocalSoftwareKeyboardController.current

    CompositionLocalProvider(
        value = LocalTextSelectionColors provides TextSelectionColors(
            handleColor = BlueDark,
            backgroundColor = BlueDark.copy(alpha = 0.4f)
        )
    ){
        Column(modifier = modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (isBackButtonVisible){
                    backButton(onBackButtonClicked)
                }else{
                    Spacer(modifier = Modifier.width(16.dp))
                }
                if (!isEditable){
                    Text(
                        text = text,
//            fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = if (isTextCentered) TextAlign.Center else TextAlign.Start,
//            fontFamily = montserratFontFamily,
//            fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        color = TextColor,
                        modifier = Modifier.weight(1f).padding(vertical = 12.dp)
                    )
                }else{
                    Box(
                        modifier = Modifier.weight(1f).padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ){
                        BasicTextField(
                            value = query,
                            onValueChange = onQueryChanged,
                            maxLines = 2,
//                overflow = TextOverflow.Ellipsis,
                            textStyle = MaterialTheme.typography.titleLarge.copy(
//                            fontWeight = FontWeight.Bold,
                                color = TextColor,
                                textAlign = if (isTextCentered) TextAlign.Center else TextAlign.Start,
                            ),
                            keyboardActions = KeyboardActions(
                                onSearch = {
                                    keyboardController?.hide()
                                    onSearchClicked()
                                }
                            ),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Search
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                        if (query.isEmpty()){
                            Text(
                                text = hint,
//            fontSize = 16.sp,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = if (isTextCentered) TextAlign.Center else TextAlign.Start,
                                style = MaterialTheme.typography.titleLarge,
                                color = TextColorLight ,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                        if (query.isNotEmpty()){
                            IconButton(
                                onClick = {
                                    onQueryChanged("")
                                }
                            ) {
                                Icon(
//                                    imageVector = Icons.Outlined.Close,
                                    painter = painterResource(Res.drawable.ic_close),
                                    modifier = Modifier.size(24.dp),
                                    contentDescription = null,
                                    tint = TextColor,
                                )
                            }
                        }

                }

                if (isTextButtonVisible){
                    TextButton(onClick = onTextButtonClicked) {
                        Text(
                            text = textButton,
                            fontSize = 12.sp,
//                    fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold,
                            color = BlueDark,
                            modifier = Modifier
                        )
                    }
                }else{
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
            if (isProgressBarVisible){
                LinearProgressIndicator(
                    modifier= Modifier
                        .height(3.dp)
                        .fillMaxWidth(),
                    color = BlueDark,
                    trackColor = Color.Transparent,
                )
            }else{
                Spacer(modifier = Modifier.height(3.dp))
            }
        }
    }
}

@Composable
private fun DefaultBackButton(
    modifier: Modifier = Modifier,
    onBackButtonClicked: () -> Unit
){
    IconButton(onClick = onBackButtonClicked) {
        Icon(
            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = null,
            tint = TextColor,
            modifier = modifier
        )
    }
}