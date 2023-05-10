package com.puj.vkandroiddevelopertest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ShowedFileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addShowedFileList(list: List<ShowedFileDbModel>)

    @Query("SELECT * FROM showed_file")
    suspend fun getShowedFiles(): List<ShowedFileDbModel>

    @Query("DELETE FROM showed_file")
    suspend fun clearShowedFiles()
}