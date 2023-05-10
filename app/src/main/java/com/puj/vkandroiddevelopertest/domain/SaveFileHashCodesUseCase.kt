package com.puj.vkandroiddevelopertest.domain

import javax.inject.Inject

class SaveFileHashCodesUseCase @Inject constructor(
    private val fileRepository: FileRepository
) {

    operator fun invoke() {
        fileRepository.saveFileHashCodes()
    }
}