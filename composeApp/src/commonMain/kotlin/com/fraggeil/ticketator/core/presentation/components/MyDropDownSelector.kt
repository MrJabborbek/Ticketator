package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.theme.BG_White
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.TextColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T>MyDropDownSelector(
    modifier: Modifier = Modifier,
//    isMenuExpanded: Boolean,
//    onDismiss: () -> Unit,
    items: List<T>?,
    selectedItem: T?,
    itemToString: (T) -> String,
    onItemSelected: (T) -> Unit,
//    onExpandRequest: () -> Unit,
    label: String,
    isLoading: Boolean = false,
    content: @Composable () -> Unit,
){
    var isMenuExpanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
        expanded = isMenuExpanded,
        onExpandedChange = {isMenuExpanded = it}
    ){
        content()

        if (isLoading) {
                ExposedDropdownMenu(
                    modifier = Modifier.width(IntrinsicSize.Max),
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    containerColor = BG_White
                ) {
                    repeat(5) {
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = "",
                                    modifier = Modifier.shimmerLoadingAnimation(
                                        isLoadingCompleted = !isLoading,
                                        modifier = Modifier.fillMaxWidth(),
                                        shimmerStyle = ShimmerStyle.Custom
                                    )
                                )
                            },
                            onClick = {},
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
        }else{
            items?.takeIf { it.isNotEmpty() }?.let {
                ExposedDropdownMenu(
                    modifier = Modifier.width(IntrinsicSize.Max),
                    expanded = isMenuExpanded,
                    onDismissRequest = { isMenuExpanded = false },
                    containerColor = BG_White
                ){

                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(
                                text = itemToString(item),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontWeight = FontWeight.Medium,
                                    letterSpacing = 0.sp
                                ),
                                color = if (item == selectedItem) Blue else TextColor
                            ) },
                            leadingIcon = {
                                if (item == selectedItem){
                                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Blue)
                                } },
                            onClick = {
                                onItemSelected(item)
                                isMenuExpanded = false
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }

                }
            } ?: kotlin.run {
                isMenuExpanded = false
            }
        }
    }
}