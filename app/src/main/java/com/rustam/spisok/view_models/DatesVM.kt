package com.rustam.spisok.view_models

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.rustam.spisok.App
import com.rustam.spisok.data.Dates
import com.rustam.spisok.data.MainDb
import com.rustam.spisok.data.Work
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.Date

class DatesVM(val database: MainDb) : ViewModel() {

    val itemsListDates = database.daoDates.getAllItems()

    var date = LocalDate.now()
    var year = date.year
    var month = date.month.value
    @SuppressLint("SimpleDateFormat")
    val sdf1 = SimpleDateFormat("dd-MM - yyyy")
    var dates = mutableStateOf( Dates("${Month.of(month).name} - ${year}", month, year) )
    var newDate = mutableStateOf(sdf1.format(Date()).toString())
    var newMonth = mutableIntStateOf(month)
    var newYear = mutableIntStateOf(year)

    fun insertItem(item: Dates) = viewModelScope.launch {
        database.daoDates.insertItem(item)
        newDate.value = ""
    }

    fun deleteItem(item: Dates) = viewModelScope.launch {
        database.daoDates.deleteItem(item)
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return DatesVM(database) as T
            }
        }
    }
}