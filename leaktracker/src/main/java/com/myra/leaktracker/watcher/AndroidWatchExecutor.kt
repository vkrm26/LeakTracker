package com.myra.leaktracker.watcher

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.MessageQueue

/**
 * Created by vikrambhati on 09/01/18.
 */
class AndroidWatchExecutor : WatchExecutor {

    var backgroundThreadName = "LeakTracker"
    var mainThreadHandler : Handler
    var backgroundHandler : Handler
    var initialDelayMillis : Long

    constructor(delayMillis : Long) {
        this.initialDelayMillis = delayMillis

        mainThreadHandler = Handler(Looper.getMainLooper())

        var handlerThread = HandlerThread(backgroundThreadName)
        handlerThread.start()

        backgroundHandler = Handler(handlerThread.looper)
    }


    override fun execute(retryable: Retryable) {
        if (Looper.getMainLooper().thread == Thread.currentThread()) {
            // wait for Idle time in main thread
            waitForIdle(retryable)
        } else {
            // post message with delay
            postWaitForIdle(retryable)
        }
    }

    fun waitForIdle(retryable: Retryable) {
        Looper.myQueue().addIdleHandler(object : MessageQueue.IdleHandler {
            override fun queueIdle(): Boolean {
                postToBackgroundWithDelay(retryable)
                return false
            }
        })
    }

    fun postWaitForIdle(retryable: Retryable) {
        mainThreadHandler.post(object : Runnable {
            override fun run() {
                waitForIdle(retryable)
            }
        })
    }

    fun postToBackgroundWithDelay(retryable: Retryable) {
        backgroundHandler.postDelayed(object : Runnable {
            override fun run() {
                var result = retryable.run()
                if (result == Retryable.Result.RETRY) {
                    postWaitForIdle(retryable)
                }
            }
        }, initialDelayMillis)
    }

}