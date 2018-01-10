package com.myra.leaktracker.heap

import android.content.Context
import android.os.Debug
import android.os.Handler
import android.os.Looper
import com.myra.leaktracker.provider.LeakDirectoryProvider
import java.io.File

/**
 * Created by vikrambhati on 25/12/17.
 */
class AndroidHeapDumper : HeapDumper {

    var mContext: Context
    var mLeakDirectoryProvider : LeakDirectoryProvider
    var mMainHandler : Handler

    constructor(context: Context, leakDirectoryProvider: LeakDirectoryProvider) {
        this.mContext = context
        this.mLeakDirectoryProvider = leakDirectoryProvider
        this.mMainHandler = Handler(Looper.getMainLooper())
    }

    override fun heapDump(): File {
        var heapDumpFile = mLeakDirectoryProvider.newHeapDumpFile()
        Debug.dumpHprofData(heapDumpFile.absolutePath)
        return heapDumpFile
    }
}