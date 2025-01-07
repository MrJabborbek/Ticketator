package com.fraggeil.ticketator.core.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fraggeil.ticketator.core.domain.DateTimeUtil.convertUtcToLocalMillis
import com.fraggeil.ticketator.core.domain.toFormattedDate
import com.fraggeil.ticketator.core.presentation.Sizes
import com.fraggeil.ticketator.core.presentation.Strings
import com.fraggeil.ticketator.core.presentation.Strings.value
import com.fraggeil.ticketator.core.theme.Blue
import com.fraggeil.ticketator.core.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyCalendarDateSelector(
    minimalSelectableDate: Long? = null,
    maximalSelectableDate: Long? = null,
    onDateSelected: (Long) -> Unit,
    currentSelectedDate: Long? = null,
    isVisible: MutableState<Boolean>,
) {
    val selectableDates = remember(minimalSelectableDate, maximalSelectableDate) {
        if (minimalSelectableDate == null && maximalSelectableDate == null) {
            DatePickerDefaults.AllDates
        } else{
            object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    return utcTimeMillis >= (minimalSelectableDate?:utcTimeMillis) && utcTimeMillis <= (maximalSelectableDate?:utcTimeMillis)
                }
            }
        }
    }
    if (isVisible.value){
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = currentSelectedDate?.takeIf { it != -1L }?.convertUtcToLocalMillis(),
            selectableDates = selectableDates
        )
        DatePickerDialog(
            modifier = Modifier.wrapContentSize().padding(8.dp),
            onDismissRequest = { isVisible.value = false },
            confirmButton = {
                Button(
                    onClick = {
                        datePickerState.selectedDateMillis?.let {
                            onDateSelected(it)
                            isVisible.value = false
                        }
                    }
                ){
                    Text(text = Strings.Select.value())
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isVisible.value = false
                    }
                ){
                    Text(text = Strings.Cancel.value())
                }
            },
            colors = DatePickerDefaults.colors().copy(containerColor = White)
        ) {
            DatePicker(
                colors = DatePickerDefaults.colors().copy(containerColor = White),
                state =  datePickerState,
                headline = {
                    Text(
                        modifier = Modifier.wrapContentWidth().padding(start = Sizes.horizontal_inner_padding),
                        text = datePickerState.selectedDateMillis?.toFormattedDate()?: Strings.SelectDate.value(),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        color = Blue
                    )
                },
                title = {
                    Text(
                        modifier = Modifier.padding(Sizes.horizontal_inner_padding),
                        text = Strings.SelectDate.value(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = Blue
                    )
                }
            )
        }
    }
}