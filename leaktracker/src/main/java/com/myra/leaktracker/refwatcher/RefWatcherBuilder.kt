package com.myra.leaktracker.refwatcher

import com.myra.leaktracker.gc.GcTrigger
import com.myra.leaktracker.heap.HeapDumper
import com.myra.leaktracker.watcher.WatchExecutor

/**
 * Created by vikrambhati on 08/01/18.
 */
open class RefWatcherBuilder {

    private var watchExecutor: WatchExecutor?= null
    private var heapDumper: HeapDumper?= null
    private var gcTrigger: GcTrigger?= null

    fun build() : RefWatcher {

        var gcTrigger = this.gcTrigger
        if (gcTrigger == null) gcTrigger = defaultGcTrigger()

        var heapDumper = this.heapDumper
        if (heapDumper == null) heapDumper = defaultHeapDumper()

        var watchExecutor = this.watchExecutor
        if (watchExecutor == null) watchExecutor = defaultWatchExecutor()

        return RefWatcher(gcTrigger, heapDumper, watchExecutor)
    }

    open fun defaultWatchExecutor() : WatchExecutor {
        return WatchExecutor.NONE
    }

    open fun defaultHeapDumper() : HeapDumper {
        return HeapDumper.NONE
    }

    fun defaultGcTrigger() : GcTrigger {
        return GcTrigger.DEFAULT
    }

}