package com.rustam.spisok.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "work")
data class Work(
    @PrimaryKey(false)
    var date: String,
    var month: Int,
    var year: Int,
    var hours: String
)
