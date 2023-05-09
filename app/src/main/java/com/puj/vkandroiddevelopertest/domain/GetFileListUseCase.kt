package com.puj.vkandroiddevelopertest.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetFileListUseCase @Inject constructor(private val repository: FileRepository) {

    operator fun invoke(): LiveData<List<File>> {
        return repository.getFileList()
    }
}