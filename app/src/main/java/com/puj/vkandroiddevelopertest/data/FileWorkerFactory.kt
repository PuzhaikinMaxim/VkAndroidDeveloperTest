package com.puj.vkandroiddevelopertest.data

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject

class FileWorkerFactory @Inject constructor(
    private val fileHashDao: FileHashDao,
    private val temporaryShowedFileDao: TemporaryShowedFileDao
    ): WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return FileWorker(
            appContext,
            workerParameters,
            fileHashDao,
            temporaryShowedFileDao
        )
    }
}