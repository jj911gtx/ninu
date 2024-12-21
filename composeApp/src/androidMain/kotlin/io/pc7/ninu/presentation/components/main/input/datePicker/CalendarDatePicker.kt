package io.pc7.ninu.presentation.components.main.input.datePicker


import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import io.pc7.ninu.data.mapper.toEpochMillis
import kotlinx.datetime.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDatePicker(
    onDateChanged: (LocalDate?) -> Unit,
    initialDate: LocalDate? = null,
    showDialog: MutableState<Boolean>
){

    val initDayMillis = initialDate?.toEpochMillis()
    val dateState = rememberDatePickerState(initialDisplayedMonthMillis = initDayMillis)


    LaunchedEffect(dateState.selectedDateMillis) {
        dateState.toDate()?.let {
            onDateChanged(it)
        }

    }

    if(showDialog.value){
        DatePickerDialog(
            onDismissRequest = { showDialog.value = false },
            confirmButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text = "OK")
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = dateState,
                showModeToggle = true
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerState.toDate(): LocalDate?{
    return this.selectedDateMillis?.let { millis ->
        LocalDate.fromEpochDays((millis / (24 * 60 * 60 * 1000)).toInt())
    }
}