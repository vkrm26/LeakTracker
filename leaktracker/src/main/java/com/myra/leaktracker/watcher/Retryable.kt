package com.myra.leaktracker.watcher

/**
 * Created by vikrambhati on 08/01/18.
 */
interface Retryable {

    enum class Result {
        DONE, RETRY
    }

    fun run(): Result
}