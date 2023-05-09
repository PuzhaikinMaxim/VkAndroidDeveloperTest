package com.puj.vkandroiddevelopertest.di

import android.content.Context
import com.puj.vkandroiddevelopertest.data.FileRepositoryImpl
import com.puj.vkandroiddevelopertest.domain.FileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {


    @ApplicationScope
    @Binds
    fun fileRepository(impl: FileRepositoryImpl): FileRepository
}