package com.puj.vkandroiddevelopertest.data

import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.vkandroiddevelopertest.domain.File
import com.puj.vkandroiddevelopertest.domain.FileRepository
import com.puj.vkandroiddevelopertest.domain.SortType
import java.io.BufferedWriter
import java.io.FileWriter
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val fileMapper: FileMapper,
    private val fileSorter: FileSorter
): FileRepository {

    private val fileList = MutableLiveData<List<java.io.File>>()

    private var currentDir = Environment.getExternalStorageDirectory()

    init {
        fileList.value = currentDir.listFiles()?.toList()
    }

    override fun getFileList(): LiveData<List<File>> {
        return Transformations.map(fileList){
            fileMapper.mapToFileList(it)
        }
    }

    override fun selectDirectory(directoryName: String) {
        currentDir = java.io.File("${currentDir}/${directoryName}")
        fileList.value = currentDir.listFiles()?.toList() ?: listOf()
    }

    override fun goOneLevelUp() {
        try{
            val newDir = currentDir.parentFile
            if(newDir.listFiles() == null) return
            currentDir = newDir
            fileList.value = currentDir.listFiles()?.toList()
        }
        catch (ex: Exception){
            //ex.printStackTrace()
            //println(currentDir.absolutePath)
        }
    }

    override fun sortFiles(sortType: SortType) {
        if(fileList.value == null) return
        fileList.value = fileSorter.sort(fileList.value!!, sortType)
    }
}