package com.myra.leaktracker.heap

import java.io.File

/**
 * Created by vikrambhati on 25/12/17.
 */
interface HeapDumper {

    fun heapDump() : File

    companion object {
        val NONE: HeapDumper = object : HeapDumper {
            override fun heapDump(): File {
                return RETRY_LATER!!
            }
        }

        val RETRY_LATER: File? = null
    }
}