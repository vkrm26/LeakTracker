package com.myra.leaktracker.watcher

/**
 * Created by vikrambhati on 08/01/18.
 */
interface WatchExecutor {

    fun execute(retryable: Retryable)

    companion object {
        val NONE: WatchExecutor = object : WatchExecutor {
            override fun execute(retryable: Retryable) {
            }
        }
    }
}