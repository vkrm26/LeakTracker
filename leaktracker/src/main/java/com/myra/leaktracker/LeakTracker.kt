package com.myra.leaktracker

import android.app.Application
import com.myra.leaktracker.refwatcher.AndroidRefWatcherBuilder
import com.myra.leaktracker.refwatcher.RefWatcher

/**
 * Created by vikrambhati on 25/12/17.
 */
class LeakTracker {

    private constructor() {
        AssertionError()
    }

    companion object {

        fun install(application: Application) : RefWatcher {
            return refWatcher(application).buildAndInstall(application)
        }

        fun refWatcher(application: Application) : AndroidRefWatcherBuilder {
            return AndroidRefWatcherBuilder(application)
        }
    }

}