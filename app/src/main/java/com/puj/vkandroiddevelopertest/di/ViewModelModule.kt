package com.puj.vkandroiddevelopertest.di

import androidx.lifecycle.ViewModel
import com.puj.vkandroiddevelopertest.presentation.FileListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(FileListViewModel::class)
    fun bindMainViewModel(viewModel: FileListViewModel): ViewModel
}