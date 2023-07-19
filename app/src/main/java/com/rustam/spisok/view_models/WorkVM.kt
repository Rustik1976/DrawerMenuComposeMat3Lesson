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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date

@SuppressLint("SimpleDateFormat")
class WorkVM(val database: MainDb) : ViewModel() {

    var itemsListWork = database.daoWork.getAllItems()

    var date = LocalDate.now()
    var year = date.year
    var month = date.month.value
    val sdf1 = SimpleDateFormat("dd-MM-yyyy")
    var work = mutableStateOf( Work(sdf1.format(Date()).toString(), month, year, "") )
    var newDate = mutableStateOf(sdf1.format(Date()).toString())
    var newHours = mutableStateOf("8")
    var newMonth = mutableIntStateOf(month)
    var newYear = mutableIntStateOf(year)

    fun getAllItemsFilter(monthF: Int, yearF: Int): Flow<List<Work>> {
        return database.daoWork.getAllItemsFilter(monthF, yearF)
    }

    fun insertItem(item: Work) = viewModelScope.launch {
        database.daoWork.insertItem(item)
        //work = null
        newDate.value = ""
        newHours.value = ""
    }

    fun deleteItem(item: Work) = viewModelScope.launch {
        database.daoWork.deleteItem(item)
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
                return WorkVM(database) as T
            }
        }
    }
}