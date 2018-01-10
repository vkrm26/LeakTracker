package com.myra.leaktracker

import android.app.Application
import com.myra.leaktracker.LeakTracker

/**
 * Created by vikrambhati on 09/01/18.
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LeakTracker.install(this)
    }

}