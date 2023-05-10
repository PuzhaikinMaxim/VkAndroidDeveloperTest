package com.puj.vkandroiddevelopertest.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FileHashDao {

    @Query("SELECT * FROM file_hash WHERE filePath IN(:filePaths)")
    suspend fun getFileHashCodes(filePaths: List<String>): List<FileHashDbModel>

    @Query("DELETE FROM file_hash")
    suspend fun clearHashCodes()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFileHashCodes(fileHashCodes: List<FileHashDbModel>)
}