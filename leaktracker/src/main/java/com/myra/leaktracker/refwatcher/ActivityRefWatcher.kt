package com.myra.leaktracker.refwatcher

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Created by vikrambhati on 09/01/18.
 */
class ActivityRefWatcher {

    var application : Application
    var refWatcher: RefWatcher

    private constructor(application: Application, refWatcher: RefWatcher) {
        this.application = application
        this.refWatcher = refWatcher
    }

    companion object {

        fun install(application: Application, refWatcher: RefWatcher) {
            ActivityRefWatcher(application, refWatcher).watchActivities()
        }

    }

    fun watchActivities() {
        stopWatchingActivities()
        application.registerActivityLifecycleCallbacks(applicationCallbacks)
    }

    fun stopWatchingActivities() {
        application.unregisterActivityLifecycleCallbacks(applicationCallbacks)
    }

    fun activityDestroyed(activity: Activity) {
        refWatcher.watch(activity)
    }

    private var applicationCallbacks = object : Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(p0: Activity?, p1: Bundle?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
            activityDestroyed(activity!!)
        }

        override fun onActivityPaused(p0: Activity?) {
        }

        override fun onActivityResumed(p0: Activity?) {
        }

        override fun onActivityStarted(p0: Activity?) {
        }

        override fun onActivityStopped(p0: Activity?) {
        }

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
        }
    }

}