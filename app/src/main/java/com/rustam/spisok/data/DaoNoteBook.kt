package com.rustam.spisok.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoNoteBook {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(noteBook: NoteBook)

    @Delete()
    suspend fun deleteItem(noteBook: NoteBook)

    @Query("SELECT * FROM note_book ORDER BY name")
    fun getAllItems() : Flow<List<NoteBook>>
}