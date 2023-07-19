package com.rustam.spisok.view_models

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.rustam.spisok.App
import com.rustam.spisok.data.MainDb
import com.rustam.spisok.data.SpisokTovarov
import com.rustam.spisok.data.Tovari
import kotlinx.coroutines.launch

class TovariVM(val database: MainDb) : ViewModel() {

    val itemsListTovari = database.daoTovar.getAllItems()
    var newText = mutableStateOf("")
    var tovari: Tovari? = null

    @SuppressLint("SimpleDateFormat")
    fun insertItem() = viewModelScope.launch {
        val nameItem = tovari?.copy(name = newText.value)
            ?: Tovari(name = newText.value)
        database.daoTovar.insertItem(nameItem)
        tovari = null
        newText.value = ""
    }

    fun deleteItem(item: Tovari) = viewModelScope.launch {
        database.daoTovar.deleteItem(item)
    }

    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return TovariVM(database) as T
            }
        }
    }
}