package com.myra.leaktracker.refwatcher

import android.app.Activity
import com.myra.leaktracker.gc.GcTrigger
import com.myra.leaktracker.heap.HeapDumper
import com.myra.leaktracker.watcher.Retryable
import com.myra.leaktracker.watcher.WatchExecutor

/**
 * Created by vikrambhati on 08/01/18.
 */
class RefWatcher {

    val gcTrigger: GcTrigger
    val heapDumper: HeapDumper
    val watchExecutor: WatchExecutor

    constructor(gcTrigger: GcTrigger, heapDumper: HeapDumper, watchExecutor: WatchExecutor) {
        this.gcTrigger = gcTrigger
        this.heapDumper = heapDumper
        this.watchExecutor = watchExecutor
    }

    fun watch(activity: Activity) {
        watchExecutor.execute(object : Retryable {
            override fun run(): Retryable.Result {
                gcTrigger.runGc()
                var heapDumpFile = heapDumper.heapDump()
                if (!heapDumpFile.exists()) {
                    return Retryable.Result.RETRY
                }

                return Retryable.Result.DONE
            }
        })
    }

}