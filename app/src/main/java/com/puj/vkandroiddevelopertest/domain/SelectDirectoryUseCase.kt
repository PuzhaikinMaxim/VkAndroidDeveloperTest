package com.puj.vkandroiddevelopertest.domain

import javax.inject.Inject

class SelectDirectoryUseCase @Inject constructor(private val fileRepository: FileRepository) {

    operator fun invoke(directoryName: String) {
        fileRepository.selectDirectory(directoryName)
    }
}