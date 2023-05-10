package com.puj.vkandroiddevelopertest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file_hash")
data class FileHashDbModel(
    @PrimaryKey
    val filePath: String,
    val fileHashCode: String
) {
}