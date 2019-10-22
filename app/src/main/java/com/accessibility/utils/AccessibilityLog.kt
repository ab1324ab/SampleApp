package com.accessibility.utils

import android.util.Log
import com.accessibility.BuildConfig

class AccessibilityLog {

    companion object {
        private val TAG: String = "AccessibilityService";

        fun printLog(logMsg: String) {
            if (!BuildConfig.DEBUG) {
                return;
            }
            Log.d(TAG, logMsg);
        }
    }
}