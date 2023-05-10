package com.puj.vkandroiddevelopertest.data

import java.io.File
import java.math.BigInteger
import java.nio.file.Files
import java.security.MessageDigest
import javax.inject.Inject

class RawFileMapper @Inject constructor() {

    fun mapToRawFile(
        dirHashCode: FileHashDbModel?,
        file: File
    ): RawFile {
        if(dirHashCode == null){
            return RawFile(file, false)
        }
        val isEdited = dirHashCode.fileHashCode != getFileHash(file)
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

    private fun getFileHash(file: File): String {
        if(file.isDirectory) return ""
        val bytes = Files.readAllBytes(file.toPath())
        return BigInteger(
            1,
            MessageDigest.getInstance("MD5").digest(bytes)).toString(16)
    }
}