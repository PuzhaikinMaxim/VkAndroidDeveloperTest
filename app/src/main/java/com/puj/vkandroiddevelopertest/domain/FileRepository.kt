package com.puj.vkandroiddevelopertest.domain

import androidx.lifecycle.LiveData

interface FileRepository {

    fun getFileList(): LiveData<List<File>>

    fun selectDirectory(directoryName: String)

    fun goOneLevelUp()

    fun sortFiles(sortType: SortType)

    fun saveFileHashCodes()
}