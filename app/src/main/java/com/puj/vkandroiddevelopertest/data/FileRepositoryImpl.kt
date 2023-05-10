package com.puj.vkandroiddevelopertest.data

import android.app.Application
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.puj.vkandroiddevelopertest.domain.File
import com.puj.vkandroiddevelopertest.domain.FileRepository
import com.puj.vkandroiddevelopertest.domain.SortType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedWriter
import java.io.FileWriter
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val application: Application,
    private val fileMapper: FileMapper,
    private val fileSorter: FileSorter,
    private val temporaryShowedFileDao: TemporaryShowedFileDao,
    private val fileHashDao: FileHashDao,
    private val rawFileMapper: RawFileMapper
): FileRepository {

    private val fileList = MutableLiveData<List<RawFile>>()

    private var currentDir = Environment.getExternalStorageDirectory()

    private var currentDirFileHashCodes = mapOf<String, FileHashDbModel>()

    init {
        val coroutineScope = CoroutineScope(Dispatchers.IO)
        coroutineScope.launch {
            val currentDirFiles = currentDir.listFiles()?.toList() ?: listOf()
            currentDirFileHashCodes = fileHashDao.getFileHashCodes(currentDirFiles.map {
                it.absolutePath
            }).associateBy { it.filePath }
            fileList.postValue(
                rawFileMapper.mapToRawFileList(currentDirFileHashCodes, currentDirFiles)
            )
        }
    }

    override fun getFileList(): LiveData<List<File>> {
        return Transformations.map(fileList){
            fileMapper.mapToFileList(it)
        }
    }

    override fun selectDirectory(directoryName: String) {
        CoroutineScope(Dispatchers.IO).launch{
            currentDir = java.io.File("${currentDir}/${directoryName}")
            setCurrentDirHashCodes()
            val currentDirFiles = currentDir.listFiles()?.toList() ?: listOf()
            fileList.postValue(
                rawFileMapper.mapToRawFileList(currentDirFileHashCodes, currentDirFiles)
            )
            temporaryShowedFileDao.addTemporaryShowedFileList(
                fileList.value!!.map { TemporaryShowedFileDbModel(it.file.absolutePath) }
            )
        }
    }

    override fun goOneLevelUp() {
        try{
            CoroutineScope(Dispatchers.IO).launch {
                val newDir = currentDir.parentFile
                if(newDir.listFiles() == null) return@launch
                currentDir = newDir
                setCurrentDirHashCodes()
                val currentDirFiles = currentDir.listFiles()?.toList() ?: listOf()
                fileList.postValue(
                    rawFileMapper.mapToRawFileList(currentDirFileHashCodes, currentDirFiles)
                )
            }
        }
        catch (ex: Exception){

        }
    }

    override fun sortFiles(sortType: SortType) {
        if(fileList.value == null) return
        fileList.value = fileSorter.sort(fileList.value!!, sortType)
    }

    override fun saveFileHashCodes() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            FileWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            FileWorker.makeRequest()
        )
    }

    private suspend fun setCurrentDirHashCodes() {
        val currentDirFiles = currentDir.listFiles()?.toList() ?: listOf()
        currentDirFileHashCodes = fileHashDao.getFileHashCodes(currentDirFiles.map {
            it.absolutePath
        }).associateBy { it.filePath }
    }
}