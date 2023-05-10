package com.puj.vkandroiddevelopertest.data

import java.io.File
import javax.inject.Inject

class RawFileMapper @Inject constructor() {

    fun mapToRawFile(
        dirHashCode: FileHashDbModel?,
        file: File
    ): RawFile {
        if(dirHashCode == null){
            return RawFile(file, false)
        }
        val isEdited = dirHashCode.fileHashCode != file.hashCode()
        return RawFile(file, isEdited)
    }

    fun mapToRawFileList(
        dirHashCodes: Map<String, FileHashDbModel>,
        fileList: List<File>
    ): List<RawFile> {
        return fileList.map {
            val dirHashCode = dirHashCodes[it.absolutePath]
            mapToRawFile(dirHashCode, it)
        }
    }
}