package com.rustam.spisok.view_models

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.rustam.spisok.App
import com.rustam.spisok.data.MainDb
import com.rustam.spisok.data.SpisokPokupok
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class SpisokViewModel(val database: MainDb) : ViewModel() {
    val itemsListSpisok = database.daoSpisok.getAllItems()
    var newText = mutableStateOf("")
    var spisokPokupok: SpisokPokupok? = null

    @SuppressLint("SimpleDateFormat")
    fun insertItem() = viewModelScope.launch {
        val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        val nameItem = spisokPokupok?.copy(name = newText.value)
            ?: SpisokPokupok(name = newText.value, date = sdf.format(Date()).toString())
        database.daoSpisok.insertItem(nameItem)
        spisokPokupok = null
        newText.value = ""
    }

    fun deleteItem(item: SpisokPokupok) = viewModelScope.launch {
        database.daoSpisok.deleteItem(item)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return SpisokViewModel(database) as T
            }
        }
    }
}