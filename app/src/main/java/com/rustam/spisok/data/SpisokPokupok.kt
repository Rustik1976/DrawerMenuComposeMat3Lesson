package com.rustam.spisok.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spisok_pokupok")
data class SpisokPokupok(
    @PrimaryKey(true)
    val id: Int? = null,
    val name: String,
    val date: String

)