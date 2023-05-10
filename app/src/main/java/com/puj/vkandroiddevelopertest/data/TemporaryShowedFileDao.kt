package com.puj.vkandroiddevelopertest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TemporaryShowedFileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTemporaryShowedFileList(list: List<TemporaryShowedFileDbModel>)

    @Query("SELECT * FROM temporary_showed_file")
    suspend fun getTemporaryShowedFiles(): List<TemporaryShowedFileDbModel>

    @Query("DELETE FROM temporary_showed_file")
    suspend fun clearShowedFiles()
}