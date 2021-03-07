package org.mm2021uprising.idk

import android.app.Application
import timber.log.Timber

class IdkApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}