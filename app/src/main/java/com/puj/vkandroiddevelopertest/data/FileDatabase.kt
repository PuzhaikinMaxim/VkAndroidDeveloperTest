package com.puj.vkandroiddevelopertest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [
        FileHashDbModel::class,
        ShowedFileDbModel::class
     ],
    version = 1,
    exportSchema = false)
abstract class FileDatabase: RoomDatabase() {

    abstract fun temporaryShowedFileDao(): ShowedFileDao

    abstract fun fileHashDao(): FileHashDao

    companion object {

        private var db: FileDatabase? = null
        private const val DB_NAME="file.db"
        private val LOCK = Any()

        fun getInstance(context: Context): FileDatabase {
            synchronized(LOCK){
                db?.let { return it }
                val instance = Room.databaseBuilder(
                    context,
                    FileDatabase::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                db = instance
                return instance
            }
        }
    }
}