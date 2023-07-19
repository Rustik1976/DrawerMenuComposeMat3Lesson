package com.rustam.spisok.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spr_tovar")
data class Tovari(
    @PrimaryKey(true)
    val id: Int? = null,
    val name: String
)
