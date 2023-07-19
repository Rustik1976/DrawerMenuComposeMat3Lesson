package com.rustam.spisok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoTovar {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(tovari: Tovari)

    @Delete()
    suspend fun deleteItem(tovari: Tovari)

    @Query("SELECT * FROM spr_tovar ORDER BY name")
    fun getAllItems() : Flow<List<Tovari>>
}