package com.puj.vkandroiddevelopertest.domain

import javax.inject.Inject

class SortFilesUseCase @Inject constructor(private val fileRepository: FileRepository) {

    operator fun invoke(sortType: SortType) {
        return fileRepository.sortFiles(sortType)
    }
}