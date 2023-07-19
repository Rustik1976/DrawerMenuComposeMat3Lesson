package com.rustam.spisok.view_models

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.rustam.spisok.App
import com.rustam.spisok.data.MainDb
import com.rustam.spisok.data.NoteBook
import kotlinx.coroutines.launch

class NoteBookVM(val database: MainDb) : ViewModel() {

    val itemsListNoteBook = database.daoNoteBook.getAllItems()
    var newText = mutableStateOf("")
    var newDesc = mutableStateOf("")
    var noteBook: NoteBook? = null

    fun updatedesc(item: NoteBook) = viewModelScope.launch{
        database.daoNoteBook.insertItem(item)
    }

    @SuppressLint("SimpleDateFormat")
    fun insertItem() = viewModelScope.launch {
        val nameItem = noteBook?.copy(name = newText.value)
            ?: NoteBook(name = newText.value, desc = newDesc.value)
        database.daoNoteBook.insertItem(nameItem)
        noteBook = null
        newText.value = ""
        newDesc.value = ""
    }

    fun deleteItem(item: NoteBook) = viewModelScope.launch {
        database.daoNoteBook.deleteItem(item)
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory
                        .APPLICATION_KEY]) as App).database
                return NoteBookVM(database) as T
            }
        }
    }
}