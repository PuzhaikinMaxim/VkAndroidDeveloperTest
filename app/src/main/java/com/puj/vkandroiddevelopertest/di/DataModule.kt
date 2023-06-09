package com.puj.vkandroiddevelopertest.di

import android.app.Application
import com.puj.vkandroiddevelopertest.data.FileDatabase
import com.puj.vkandroiddevelopertest.data.FileHashDao
import com.puj.vkandroiddevelopertest.data.FileRepositoryImpl
import com.puj.vkandroiddevelopertest.data.ShowedFileDao
import com.puj.vkandroiddevelopertest.domain.FileRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {


    @ApplicationScope
    @Binds
    fun fileRepository(impl: FileRepositoryImpl): FileRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideFileHashDao(
            application: Application
        ): FileHashDao {
            return FileDatabase.getInstance(application).fileHashDao()
        }

        @Provides
        @ApplicationScope
        fun provideTemporaryShowedFileDao(
            application: Application
        ): ShowedFileDao {
            return FileDatabase.getInstance(application).temporaryShowedFileDao()
        }
    }
}