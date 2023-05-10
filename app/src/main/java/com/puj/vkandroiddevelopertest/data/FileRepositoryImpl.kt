package com.puj.vkandroiddevelopertest.data

import android.app.Application
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.puj.vkandroiddevelopertest.domain.File
import com.puj.vkandroiddevelopertest.domain.FileRepository
import com.puj.vkandroiddevelopertest.domain.SortType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigInteger
import java.nio.file.Files
import java.security.MessageDigest
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val application: Application,
    private val fileMapper: FileMapper,
    private val fileSorter: FileSorter,
    private val showedFileDao: ShowedFileDao,
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
            fileHashDao.clearHashCodes()
            fileHashDao.addFileHashCodes(showedFileDao.getShowedFiles().map {
                FileHashDbModel(it.filePath, it.fileHashCode)
            })
            showedFileDao.clearShowedFiles()
            currentDirFileHashCodes = fileHashDao.getFileHashCodes(currentDirFiles.map {
                it.absolutePath
            }).associateBy { it.filePath }
            fileList.postValue(
                rawFileMapper.mapToRawFileList(currentDirFileHashCodes, currentDirFiles)
            )
            showedFileDao.addShowedFileList(
                currentDirFiles.map {
                    ShowedFileDbModel(it.absolutePath, getFileHash(it))
                }
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
            println(currentDir)
            val currentDirFiles = currentDir.listFiles()?.toList() ?: listOf()
            setCurrentDirHashCodes()
            fileList.postValue(
                rawFileMapper.mapToRawFileList(currentDirFileHashCodes, currentDirFiles)
            )
            showedFileDao.addShowedFileList(
                currentDirFiles.map { ShowedFileDbModel(it.absolutePath, getFileHash(it)) }
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

    private suspend fun setCurrentDirHashCodes() {
        val currentDirFiles = currentDir.listFiles()?.toList() ?: listOf()
        currentDirFileHashCodes = fileHashDao.getFileHashCodes(currentDirFiles.map {
            it.absolutePath
        }).associateBy { it.filePath }
    }

    private fun getFileHash(file: java.io.File): String {
        if(file.isDirectory) return ""
        val bytes = Files.readAllBytes(file.toPath())
        return BigInteger(
            1,
            MessageDigest.getInstance("MD5").digest(bytes)).toString(16)
    }
}