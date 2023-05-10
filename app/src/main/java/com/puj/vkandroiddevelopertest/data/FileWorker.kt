package com.puj.vkandroiddevelopertest.data

import android.content.Context
import androidx.work.*
import kotlinx.coroutines.delay
import java.io.File

class FileWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val fileHashDao: FileHashDao,
    private val temporaryShowedFileDao: TemporaryShowedFileDao
): CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        fileHashDao.clearHashCodes()
        val filesList = temporaryShowedFileDao.getTemporaryShowedFiles()
        val fileHashes = mutableListOf<FileHashDbModel>()
        for(showedFile in filesList){
            val file = File(showedFile.filePath)
            fileHashes.add(
                FileHashDbModel(
                file.absolutePath,
                file.hashCode()
            )
            )
        }
        fileHashDao.addFileHashCodes(fileHashes)
        temporaryShowedFileDao.clearShowedFiles()
        return Result.success()
    }

    companion object {

        const val NAME = "FileWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<FileWorker>().build()
        }
    }
}