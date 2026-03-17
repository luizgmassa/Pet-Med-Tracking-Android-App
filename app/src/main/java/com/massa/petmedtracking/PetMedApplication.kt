package com.massa.petmedtracking

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class PetMedApplication : Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
