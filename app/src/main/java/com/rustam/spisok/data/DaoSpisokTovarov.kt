package com.rustam.spisok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoSpisokTovarov {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(spisokTovarov: SpisokTovarov)

    @Delete()
    suspend fun deleteItem(spisokTovarov: SpisokTovarov)

    @Query("SELECT * FROM spisok_tovar WHERE id_spis = :ids ORDER BY name")
    fun getAllItems(ids: Int) : Flow<List<SpisokTovarov>>
}