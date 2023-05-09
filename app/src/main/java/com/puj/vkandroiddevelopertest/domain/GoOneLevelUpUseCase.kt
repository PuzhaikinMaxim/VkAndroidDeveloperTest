package com.puj.vkandroiddevelopertest.domain

import javax.inject.Inject

class GoOneLevelUpUseCase @Inject constructor(
    private val repository: FileRepository
){

    operator fun invoke() {
        repository.goOneLevelUp()
    }
}