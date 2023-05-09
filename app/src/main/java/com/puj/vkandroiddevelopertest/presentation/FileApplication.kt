package com.puj.vkandroiddevelopertest.presentation

import android.app.Application
import com.puj.vkandroiddevelopertest.di.DaggerApplicationComponent

class FileApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}