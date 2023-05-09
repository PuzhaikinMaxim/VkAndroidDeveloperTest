package com.puj.vkandroiddevelopertest.data

import com.puj.vkandroiddevelopertest.domain.File
import java.nio.file.Files
import java.nio.file.attribute.FileTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

class FileMapper @Inject constructor() {

    fun mapToFile(file: java.io.File, id: Int): File {
        val fileName = file.name
        val creationTime = Files.getAttribute(file.toPath(), "creationTime") as FileTime
        val formatter = DateTimeFormatter.ofPattern("dd.M.yyyy hh:mm").withZone(ZoneId.systemDefault())
        val formattedDate = formatter.format(creationTime.toInstant())
        val size = getSize(file.length())
        return File(
            id, fileName, formattedDate, size, getFileType(file)
        )
    }

    fun mapToFileList(list: List<java.io.File>): List<File> {
        val fileList = mutableListOf<File>()
        for((index, elem) in list.withIndex()){
            fileList.add(mapToFile(elem,index))
        }
        return fileList
    }

    private fun getFileType(file: java.io.File): File.FileType {
        if(file.isDirectory) return File.FileType.TYPE_FOLDER

        return when(file.extension){
            "txt" -> File.FileType.TYPE_TXT
            "png" -> File.FileType.TYPE_PNG
            "apk" -> File.FileType.TYPE_APK
            "jpeg" -> File.FileType.TYPE_JPEG
            else -> File.FileType.TYPE_DEFAULT
        }
    }

    private fun getSize(fileSize: Long): String {
        if(fileSize/ GIGABYTE != 0L) return "${(fileSize / GIGABYTE).toInt()} ГБ"
        if(fileSize/ MEGABYTE != 0L) return "${(fileSize / MEGABYTE).toInt()} МБ"
        if(fileSize/ KILOBYTE != 0L) return "${(fileSize / KILOBYTE).toInt()} КБ"
        if(fileSize/ BYTE != 0L) return "${(fileSize / BYTE).toInt()} Б"
        return "0 Б"
    }

    companion object {
        private const val BYTE = 8L
        private const val KILOBYTE = 1024L * BYTE
        private const val MEGABYTE = 1024L * KILOBYTE
        private const val GIGABYTE = 1024L * MEGABYTE
    }
}