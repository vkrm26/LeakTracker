package com.myra.leaktracker.provider

import android.annotation.TargetApi
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import java.io.File
import java.io.FilenameFilter
import java.util.*

/**
 * Created by vikrambhati on 25/12/17.
 */
class DefaultLeakDirectoryProvider : LeakDirectoryProvider {

    val MAX_STORED_DUMPS = 7
    val HPROF_SUFFIX = ".hprof"
    val PENDING_HEAPDUMP_SUFFIX = "_pending.hprof"
    val MAX_DURATION = 600000
    lateinit var context: Context
    var maxStoredHeapDump: Int = -1

    @Volatile var writeExternalStorageGranted: Boolean = false
    @Volatile var permissionNotificationDisplayed: Boolean = false

    constructor(context: Context) : this(context, 7)

    constructor(context: Context, maxHeapDump: Int) {
        if (maxHeapDump < 1) {
            throw IllegalArgumentException("maxStoredHeapDumps must be at least 1")
        } else {
            this.context = context
            this.maxStoredHeapDump = maxHeapDump
        }
    }

    override fun listFiles(filenameFilter: FilenameFilter): List<File> {
        if (!this.hasStoragePermission()) {
            // show notification to grant permission
        }

        val fileList = ArrayList<File>()
        val externalFiles = this.getExternalStorageDirectory().listFiles(filenameFilter)
        if (externalFiles != null) {
            fileList.addAll(externalFiles)
        }

        val appFiles = this.getAppDirectory().listFiles(filenameFilter)
        if (appFiles != null) {
            fileList.addAll(appFiles)
        }

        return fileList
    }

    fun getExternalStorageDirectory() : File {
        val downloadDirectory  = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        return File(downloadDirectory, "leakTracker - " + this.context.packageName)
    }

    fun getAppDirectory() : File {
        val downloadDirectory  = this.context.filesDir
        return File(downloadDirectory, "leakTracker - " + this.context.packageName)
    }

    override fun cleanDirectory() {
        var allFilesExceptPending = this.listFiles(object : java.io.FilenameFilter {
            override fun accept(file: File?, fileName: String?): Boolean {
                return !fileName!!.endsWith("_pending.hprof")
            }
        })

        var iterator = allFilesExceptPending.iterator()

        while (iterator.hasNext()) {
            var file = iterator.next()
            var isDeleted = file.delete()

            if (!isDeleted) {
                // could not delete this file
            }
        }

    }

    override fun newHeapDumpFile(): File {
        var pendingFileList = this.listFiles(object : java.io.FilenameFilter {
            override fun accept(file: File?, fileName: String?): Boolean {
                return fileName!!.endsWith("_pending.hprof")
            }
        })

        var iterator = pendingFileList.iterator()

        var directory = getExternalStorageDirectory()
        return File(directory, UUID.randomUUID().toString() + PENDING_HEAPDUMP_SUFFIX)
    }

    @TargetApi(23)
    fun hasStoragePermission() : Boolean {
        if (Build.VERSION.SDK_INT < 23) {
            return true
        } else if (this.writeExternalStorageGranted) {
            return true
        } else {
            this.writeExternalStorageGranted = this.context.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED
            return writeExternalStorageGranted
        }
    }
}