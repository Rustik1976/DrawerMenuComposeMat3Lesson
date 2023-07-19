package com.rustam.spisok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoWork {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(work: Work)

    @Delete()
    suspend fun deleteItem(work: Work)

    @Query("SELECT * FROM work")
    fun getAllItems() : Flow<List<Work>>

    @Query("SELECT * FROM work WHERE month = :monthF AND year = :yearF ORDER BY date")
    fun getAllItemsFilter(monthF: Int, yearF: Int) : Flow<List<Work>>
}