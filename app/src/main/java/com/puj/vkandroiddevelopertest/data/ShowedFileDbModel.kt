package com.puj.vkandroiddevelopertest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "showed_file")
data class ShowedFileDbModel(
    @PrimaryKey
    val filePath: String,
    val fileHashCode: String
) {
}