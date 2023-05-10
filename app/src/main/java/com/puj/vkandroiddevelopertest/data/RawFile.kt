package com.puj.vkandroiddevelopertest.data

import java.io.File

data class RawFile(
    val file: File,
    val isEdited: Boolean = false
) {
}