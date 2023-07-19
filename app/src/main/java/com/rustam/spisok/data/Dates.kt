package com.rustam.spisok.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dates")
data class Dates(
    @PrimaryKey(false)
    var date: String,
    var month: Int,
    var year: Int
)
