package com.myra.leaktracker.provider

import java.io.File
import java.io.FilenameFilter

/**
 * Created by vikrambhati on 25/12/17.
 */
interface LeakDirectoryProvider {

    fun listFiles(filenameFilter: FilenameFilter) : List<File>

    fun cleanDirectory()

    fun newHeapDumpFile() : File

}