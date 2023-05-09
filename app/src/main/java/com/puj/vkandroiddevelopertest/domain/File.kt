package com.puj.vkandroiddevelopertest.domain

data class File(
    val id: Int,
    val fileName: String,
    val creationDate: String,
    val size: String,
    val fileType: FileType
) {

    enum class FileType {
        TYPE_DEFAULT,
        TYPE_APK,
        TYPE_FOLDER,
        TYPE_JPEG,
        TYPE_PNG,
        TYPE_TXT
    }
}