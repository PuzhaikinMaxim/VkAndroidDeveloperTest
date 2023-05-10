package com.puj.vkandroiddevelopertest.di

import android.app.Application
import android.content.Context
import com.puj.vkandroiddevelopertest.presentation.FileApplication
import com.puj.vkandroiddevelopertest.presentation.FileListFragment
import com.puj.vkandroiddevelopertest.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: FileListFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}