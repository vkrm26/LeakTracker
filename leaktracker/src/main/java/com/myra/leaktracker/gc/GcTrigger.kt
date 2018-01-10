package com.myra.leaktracker.gc

/**
 * Created by vikrambhati on 25/12/17.
 */
interface GcTrigger {

    fun runGc()

    companion object {
        val DEFAULT: GcTrigger = object : GcTrigger {
            override fun runGc() {
                Runtime.getRuntime().gc()
                System.runFinalization()
            }
        }
    }
}