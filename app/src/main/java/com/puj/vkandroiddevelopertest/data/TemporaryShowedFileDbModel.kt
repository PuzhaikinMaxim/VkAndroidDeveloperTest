package com.puj.vkandroiddevelopertest.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "temporary_showed_file")
data class TemporaryShowedFileDbModel(
    @PrimaryKey
    val filePath: String
) {
}