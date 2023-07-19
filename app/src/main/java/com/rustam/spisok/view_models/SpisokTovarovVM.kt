package com.rustam.spisok.view_models

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.rustam.spisok.App
import com.rustam.spisok.data.MainDb
import com.rustam.spisok.data.SpisokTovarov
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SpisokTovarovVM(val database: MainDb) : ViewModel() {
    var itemsListSpisokTovarov = database.daoSpisokTovarov.getAllItems(0)
    var newText = mutableStateOf("")
    var newId = mutableStateOf(0)
    var newKol = mutableStateOf("")
    var spisokTovarov: SpisokTovarov? = null

    fun getAll(idd: Int): Flow<List<SpisokTovarov>> {
        return database.daoSpisokTovarov.getAllItems(idd)
    }

    fun insertItem(item: SpisokTovarov) = viewModelScope.launch {
//        val nameItem = spisokTovarov?.copy(name = newText.value)
//            ?: SpisokTovarov(name = newText.value, id_spis = newId.value, id = null, kol = newKol.value)
        database.daoSpisokTovarov.insertItem(item)
        spisokTovarov = null
        newText.value = ""
    }

    fun deleteItem(item: SpisokTovarov) = viewModelScope.launch {
        database.daoSpisokTovarov.deleteItem(item)
    }

    companion object {

        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val database = (checkNotNull(extras[APPLICATION_KEY]) as App).database
                return SpisokTovarovVM(database) as T
            }
        }
    }
}