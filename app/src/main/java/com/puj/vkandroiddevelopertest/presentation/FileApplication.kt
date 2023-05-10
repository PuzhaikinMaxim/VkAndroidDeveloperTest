package com.puj.vkandroiddevelopertest.presentation

import android.app.Application
import androidx.work.Configuration
import com.puj.vkandroiddevelopertest.di.DaggerApplicationComponent
import javax.inject.Inject

class FileApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}