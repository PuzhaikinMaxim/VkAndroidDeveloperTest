package com.puj.vkandroiddevelopertest.presentation

import androidx.lifecycle.ViewModel
import com.puj.vkandroiddevelopertest.domain.*
import javax.inject.Inject

class FileListViewModel @Inject constructor(
    private val getFileListUseCase: GetFileListUseCase,
    private val selectDirectoryUseCase: SelectDirectoryUseCase,
    private val goOneLevelUpUseCase: GoOneLevelUpUseCase,
    private val sortFilesUseCase: SortFilesUseCase
): ViewModel() {

    val fileList = getFileListUseCase.invoke()

    fun onFileClick(file: File) {
        if(file.fileType == File.FileType.TYPE_FOLDER){
            selectDirectoryUseCase.invoke(file.fileName)
        }
    }

    fun goOneLevelUp() {
        goOneLevelUpUseCase()
    }

    fun sortFiles(sortType: SortType) {
        sortFilesUseCase.invoke(sortType)
    }
}