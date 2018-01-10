package com.myra.leaktracker.refwatcher

import android.app.Application
import android.content.Context
import com.myra.leaktracker.heap.AndroidHeapDumper
import com.myra.leaktracker.heap.HeapDumper
import com.myra.leaktracker.provider.DefaultLeakDirectoryProvider
import com.myra.leaktracker.provider.LeakDirectoryProvider
import com.myra.leaktracker.watcher.AndroidWatchExecutor
import com.myra.leaktracker.watcher.WatchExecutor

/**
 * Created by vikrambhati on 09/01/18.
 */
class AndroidRefWatcherBuilder : RefWatcherBuilder {

    var context: Context

    constructor(context: Context) {
        this.context = context
    }

    fun buildAndInstall(application: Application) : RefWatcher {
        var refWatcher = build()
        ActivityRefWatcher.install(application, refWatcher)
        return refWatcher
    }

    override fun defaultWatchExecutor(): WatchExecutor {
        return AndroidWatchExecutor(100)
    }

    override fun defaultHeapDumper(): HeapDumper {
        return AndroidHeapDumper(context, getLeakDirectoryProvider())
    }

    fun getLeakDirectoryProvider() : LeakDirectoryProvider {
        return DefaultLeakDirectoryProvider(context)
    }


}