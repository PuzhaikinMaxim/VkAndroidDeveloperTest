package com.puj.vkandroiddevelopertest.presentation

import android.app.Application
import androidx.work.Configuration
import com.puj.vkandroiddevelopertest.data.FileWorkerFactory
import com.puj.vkandroiddevelopertest.di.DaggerApplicationComponent
import javax.inject.Inject

class FileApplication: Application(), Configuration.Provider {

    @Inject
    lateinit var fileWorkerFactory: FileWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(fileWorkerFactory)
            .build()
    }
}