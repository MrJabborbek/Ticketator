package com.fraggeil.ticketator.presentation.screens.profile_screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.presentation.Language
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.presentation.components.shimmerLoadingAnimation
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.BlueDark
import com.fraggeil.ticketator.core.theme.BlueDarkOnSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageBottomSheet(
    languages: List<Language>,
    onLanguageClicked: (Language) -> Unit,
    selectedLanguage: Language,
    onDismiss: () -> Unit,
    isLoading: Boolean = false
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
                text = Strings.Language_.value(),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.sp
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (isLoading){
                Box(
                    modifier = Modifier
                        .shimmerLoadingAnimation(
                            isLoadingCompleted = !isLoading,
                            linesCount = 5
                        )
                )
            }else{
                languages.forEach {
                    Row(
                        modifier = Modifier.padding(top = 0.dp).fillMaxWidth()
                            .clickable(onClick = { onLanguageClicked(it) }, interactionSource = null, indication = null),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ){
                        RadioButton(
                            selected = it == selectedLanguage,
                            onClick = {onLanguageClicked(it)},
                            colors = RadioButtonDefaults.colors().copy(selectedColor = BlueDark, unselectedColor = BlueDarkOnSecondary)
                        )
                        Text(
                            text = it.lang,
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
    }
}