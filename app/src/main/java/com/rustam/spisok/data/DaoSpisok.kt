package com.rustam.spisok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoSpisok {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(spisokPokupok: SpisokPokupok)

    @Delete()
    suspend fun deleteItem(spisokPokupok: SpisokPokupok)

    @Query("SELECT * FROM spisok_pokupok ORDER BY name")
    fun getAllItems() : Flow<List<SpisokPokupok>>
}