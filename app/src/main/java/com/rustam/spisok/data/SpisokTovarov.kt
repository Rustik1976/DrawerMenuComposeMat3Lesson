package com.rustam.spisok.data

import androidx.room.ColumnInfo
import androidx.room.ColumnInfo.Companion.VALUE_UNSPECIFIED
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "spisok_tovar")
data class SpisokTovarov(
    @PrimaryKey(true)
    var id: Int? = null,
    val id_spis: Int,
    val name: String,
    @ColumnInfo(name = "kol", defaultValue = "")
    var kol: String,
    @ColumnInfo(name = "status", defaultValue = "0")
    var status: Boolean
)
