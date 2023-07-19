package com.rustam.spisok.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_book")
data class NoteBook(
    @PrimaryKey(true)
    val id: Int? = null,
    val name: String,
    var desc: String
)
