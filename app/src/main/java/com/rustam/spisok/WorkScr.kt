package com.rustam.spisok

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.util.Log
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.rustam.spisok.data.Dates
import com.rustam.spisok.data.Work
import com.rustam.spisok.ui.theme.BackFon
import com.rustam.spisok.ui.theme.BackMenu
import com.rustam.spisok.ui.theme.Pink20
import com.rustam.spisok.ui.theme.Pink40
import com.rustam.spisok.ui.theme.Purple150
import com.rustam.spisok.ui.theme.Purple200
import com.rustam.spisok.ui.theme.PurpleGrey80
import com.rustam.spisok.view_models.DatesVM
import com.rustam.spisok.view_models.WorkVM
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.Calendar
import java.util.Date


@SuppressLint("SimpleDateFormat")
@Composable
fun WorkScr(
    workVM: WorkVM = viewModel(factory = WorkVM.factory),
    datesVM: DatesVM = viewModel(factory = DatesVM.factory)
) {
    var itemsList = workVM.getAllItemsFilter(workVM.work.value.month, workVM.work.value.year)
        .collectAsState(initial = emptyList())

    val openDialogW = remember { mutableStateOf(false) }
    val reload = remember { mutableStateOf(true) }
    val selectedDateText by remember { mutableStateOf(Dates("", 0, 0)) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("dd-MM-yyyy")
    val date = LocalDate.now()
    val year1 = date.year
    val month1 = date.month.value
    val item by remember {
        mutableStateOf(
            Work(
                sdf.format(Date()).toString(),
                month1,
                year1,
                ""
            )
        )
    }
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            run {
                var ddd = ""
                if (selectedDayOfMonth.toString().length == 2) ddd = "$selectedDayOfMonth"
                else ddd = "0$selectedDayOfMonth"
                var mmm = ""
                if ((selectedMonth + 1).toString().length == 2) mmm = "${selectedMonth + 1}"
                else mmm = "0${selectedMonth + 1}"
                selectedDateText.month = selectedMonth + 1
                selectedDateText.year = selectedYear
                selectedDateText.date = ("$mmm-$selectedYear")
                datesVM.newDate.value = "${Month.of(selectedMonth + 1).name} - $selectedYear"

                item.month = selectedDateText.month
                item.year = selectedDateText.year
                datesVM.dates.value.month = selectedDateText.month
                datesVM.dates.value.year = selectedDateText.year
                datesVM.dates.value.date = datesVM.newDate.value

                workVM.newMonth.intValue = selectedDateText.month
                workVM.newYear.intValue = selectedDateText.year
                datesVM.newMonth.intValue = selectedDateText.month
                datesVM.newYear.intValue = selectedDateText.year

                datesVM.insertItem(datesVM.dates.value)
                Log.d("MyLog", "Dates: ${datesVM.dates.value}")
            }
        }, year, month, dayOfMonth
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackFon)
    ) {
        Card(

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackFon),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,

                ) {
                IconButton(
                    onClick = {
                        openDialogW.value = true
                        workVM.newDate.value = item.date
                        workVM.newHours.value = "8"
                        workVM.newMonth.intValue = item.month
                        workVM.newYear.intValue = item.year
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_day),
                        contentDescription = "Add",
                        modifier = Modifier, tint = Purple150
                    )
                }
                IconButton(
                    onClick = {
                        datePicker.show()
                    }
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_add_month),
                        contentDescription = "Add",
                        modifier = Modifier, tint = Purple150
                    )
                }
                MenuWorkDate(datesVM, workVM) {
                    workVM.work.value.month = it.month
                    workVM.work.value.year = it.year
                    datesVM.dates.value = it
                    reload.value = true
                    Log.d("MyLog", "Menu CallBack 1: ${workVM.work.value}")
                }
            }
        }
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BackFon),
                horizontalArrangement = Arrangement.SpaceAround,
            ) {
                Text(text = "День", color = Purple200)
                Text(text = "Часы", color = Purple200)
                Spacer(modifier = Modifier.width(75.dp))
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(itemsList.value) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(5.dp)
                        .clickable {
                            openDialogW.value = true
                            workVM.newDate.value = item.date
                            workVM.newHours.value = item.hours
                            workVM.newMonth.intValue = item.month
                            workVM.newYear.intValue = item.year
                            workVM.work.value.year = item.year
                            workVM.work.value.month = item.month
                            workVM.work.value.hours = item.hours
                            workVM.work.value.date = item.date
                        },
                    backgroundColor = PurpleGrey80
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.date,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(10.dp),
                            color = Pink40
                        )
                        Text(
                            text = item.hours,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(10.dp),
                            color = Pink20
                        )
                        IconButton(
                            onClick = {
                                workVM.deleteItem(item)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                modifier = Modifier, tint = Purple150
                            )
                        }
                    }
                }
            }
        }
    }
    if (openDialogW.value) EditWork(item, workVM, openDialogW)
    if (reload.value) {
        itemsList = workVM.getAllItemsFilter(workVM.work.value.month, workVM.work.value.year)
            .collectAsState(initial = emptyList())
        Log.d("MyLog", "Menu CallBack 2: ${itemsList}")
        reload.value = false
    }
}


@SuppressLint("SimpleDateFormat")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuWorkDate(
    datesVM: DatesVM,
    workVM: WorkVM,
    onChosen: @Composable (Dates) -> Unit
) {
    var datesList = datesVM.database.daoDates.getAllItems().collectAsState(initial = emptyList())

    var expanded by remember { mutableStateOf(false) }
    val options = ArrayList<Dates>()
    var selectedOptionText by remember { mutableStateOf(Dates("", 0, 0)) }
    var choice by remember { mutableStateOf(true) }
    var firstRun by remember { mutableStateOf(true) }
    Log.d("MyLog", "Item Dates: ${datesVM.dates.value}")
    selectedOptionText = datesVM.dates.value

    datesList.value.forEach {
        options.add(it)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.wrapContentWidth()
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor(),
            readOnly = true,
            value = selectedOptionText.date,
            onValueChange = {},
            label = {
                Text(
                    text = "Фильтр",
                    color = Purple200
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                textColor = Pink40, containerColor = PurpleGrey80
            )
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(PurpleGrey80)
        ) {
            options.forEach { dateS ->
                DropdownMenuItem(
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = dateS.date,
                                color = Color.Black
                            )
                            IconButton(
                                onClick = {
                                    datesVM.deleteItem(dateS)
                                    choice = true
                                    firstRun = true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete",
                                    modifier = Modifier, tint = Purple150
                                )
                            }
                        }
                    },
                    onClick = {
                        selectedOptionText.date = dateS.date
                        selectedOptionText.year = dateS.year
                        selectedOptionText.month = dateS.month
                        workVM.work.value.month = dateS.month
                        workVM.work.value.year = dateS.year

                        datesVM.dates.value = selectedOptionText
                        datesVM.newDate.value = dateS.date
                        datesVM.newMonth.intValue = dateS.month
                        datesVM.newYear.intValue = dateS.year

                        choice = true
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
    if (choice) {
        onChosen(selectedOptionText)
        if (firstRun) {
            datesList = datesVM.database.daoDates.getAllItems().collectAsState(initial = emptyList())
            datesList.value.forEach {
                options.add(it)
            }
            onChosen(selectedOptionText)
            selectedOptionText.date = selectedOptionText.date
            selectedOptionText.year = selectedOptionText.year
            selectedOptionText.month = selectedOptionText.month
            datesVM.dates.value = selectedOptionText
            workVM.work.value.month = selectedOptionText.month
            workVM.work.value.year = selectedOptionText.year
            datesVM.newDate.value = selectedOptionText.date
            datesVM.newMonth.intValue = selectedOptionText.month
            datesVM.newYear.intValue = selectedOptionText.year
            Log.d("MyLog", "Menu FirstRun: ${workVM.work}")
        }
        choice = false
        firstRun = false
    }
}

@Composable
fun EditWork(
    item: Work,
    workVM: WorkVM,
    openDialogW: MutableState<Boolean>
) {
    val selectedDateText by remember { mutableStateOf(Dates("", 0, 0)) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]
    val focusRequester = remember { FocusRequester() }
    val datePicker = DatePickerDialog(
        context,
        { datePicker: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            run {
                selectedDateText.month = selectedMonth + 1
                selectedDateText.year = selectedYear
                selectedDateText.date = ("${selectedMonth + 1}-$selectedYear")
                var ddd = ""
                if (selectedDayOfMonth.toString().length == 2) ddd = "$selectedDayOfMonth"
                else ddd = "0$selectedDayOfMonth"
                var mmm = ""
                if ((selectedMonth + 1).toString().length == 2) mmm = "${selectedMonth + 1}"
                else mmm = "0${selectedMonth + 1}"
                workVM.newDate.value = "$ddd-$mmm-$selectedYear"
                item.month = selectedDateText.month
                item.year = selectedDateText.year
            }
        }, year, month, dayOfMonth
    )
    if (openDialogW.value) {
        AlertDialog(
            onDismissRequest = {
                openDialogW.value = false
            },
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        color = Purple200,
                        text = "Количество часов"
                    )
                    IconButton(
                        onClick = {
                            datePicker.show()
                        }) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = "Date",
                            tint = Purple200
                        )
                    }
                }
            },
            text = {
                Column() {
                    OutlinedTextField(
                        value = workVM.newDate.value,
                        onValueChange = { text ->
                            workVM.newDate.value = text
                        }, label = {
                            Text("Дата")
                        },
                        placeholder = {
                            Text(text = "Дата")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            imeAction = ImeAction.Next
                        )
                    )
                    OutlinedTextField(
                        value = workVM.newHours.value,
                        onValueChange = { text ->
                            workVM.newHours.value = text
                        }, label = {
                            Text("Часы")
                        },
                        placeholder = { Text(text = "Часы") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth()
                            .wrapContentHeight()
                            .focusRequester(focusRequester),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        )
                    )
                }
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Button(
                        onClick = {
                            val temp = workVM.newDate.value
                            val list = temp.split("-")
                            item.date = workVM.newDate.value
                            item.month = list[1].toInt()
                            item.year = list[2].toInt()
                            item.hours = workVM.newHours.value
                            workVM.newYear.intValue = item.year
                            workVM.newMonth.intValue = item.month
                            workVM.work.value = item
                            workVM.insertItem(item)
                            openDialogW.value = false
                        }
                    ) {
                        Text(text = "Сохранить")
                    }
                    Button(
                        onClick = { openDialogW.value = false }
                    ) {
                        Text("Отмена")
                    }
                }
                Spacer(modifier = Modifier.height(15.dp))
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentWidth()
                .wrapContentHeight(),
            backgroundColor = BackMenu
        )
    }
}