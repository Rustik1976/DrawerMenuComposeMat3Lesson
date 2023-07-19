package com.rustam.spisok.data

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [SpisokPokupok::class, NoteBook::class, Tovari::class, SpisokTovarov::class, Work::class, Dates::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 3, to = 4)
    ]
)
abstract class MainDb : RoomDatabase() {
    abstract val daoSpisok: DaoSpisok
    abstract val daoNoteBook: DaoNoteBook
    abstract val daoTovar: DaoTovar
    abstract val daoSpisokTovarov: DaoSpisokTovarov
    abstract val daoWork: DaoWork
    abstract val daoDates: DaoDates

    companion object {
        val migration2To3 = object : Migration(2,3){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE IF NOT EXISTS work (" +
                        "date TEXT NOT NULL PRIMARY KEY," +
                        "hours TEXT NOT NULL)")
                database.execSQL("CREATE TABLE IF NOT EXISTS dates (" +
                        "date TEXT NOT NULL PRIMARY KEY)")
            }
        }
        fun createDatebase(context: Context): MainDb {
            return Room.databaseBuilder(
                context,
                MainDb::class.java,
                "spisok.db"
            ).addMigrations(migration2To3).build()
        }
    }


}