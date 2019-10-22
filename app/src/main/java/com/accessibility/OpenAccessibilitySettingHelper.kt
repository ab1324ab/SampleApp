package com.accessibility

import android.content.Context
import android.content.Intent

class OpenAccessibilitySettingHelper {
    companion object {
        private val ACTION: String = "action"
        private val ACTION_START_ACCESSIBILITY_SETTING: String = "action_start_accessibility_setting"

        fun jumpToSettingPage(context: Context) {
            try {
                var intent = Intent(context, AccessibilityOpenHelperActivity::class.java)
                intent.putExtra(ACTION, ACTION_START_ACCESSIBILITY_SETTING)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
