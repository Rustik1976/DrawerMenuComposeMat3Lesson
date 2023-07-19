package com.rustam.spisok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoDates {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(dates: Dates)

    @Delete()
    suspend fun deleteItem(dates: Dates)

    @Query("SELECT * FROM dates")
    fun getAllItems() : Flow<List<Dates>>
}