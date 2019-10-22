package com.accessibility.utils

import android.content.Context
import com.accessibility.AccessibilitySampleService
import android.provider.Settings;
import android.text.TextUtils
import android.content.Intent


class AccessibilityUtil {
    companion object {
        private val ACCESSIBILITY_SERVICE_PATH: String? = AccessibilitySampleService::class.java.canonicalName

        fun isAccessibilitySettingsOn(content: Context): Boolean {
            if (content == null) {
                return false
            }
            var accessibilityEnabled: Int = 0
            try {
                accessibilityEnabled = Settings.Secure.getInt(
                    content.applicationContext.contentResolver,
                    Settings.Secure.ACCESSIBILITY_ENABLED
                );
            } catch (e: Settings.SettingNotFoundException) {
                e.printStackTrace()
            }
            var packageName: String = content.packageName;
            val serviceStr: String = "$packageName/$ACCESSIBILITY_SERVICE_PATH"
            AccessibilityLog.printLog("serviceStr: $serviceStr")

            if (accessibilityEnabled == 1) {
                var mStringColonSplitter: TextUtils.SimpleStringSplitter = TextUtils.SimpleStringSplitter(':');

                var settingValue: String = Settings.Secure.getString(
                    content.applicationContext.contentResolver,
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
                );

                if (settingValue != null) {
                    var splitter: TextUtils.SimpleStringSplitter = mStringColonSplitter
                    splitter.setString(settingValue);
                    while (splitter.hasNext()) {
                        var accessabilityService: String = splitter.next();
                        if (accessabilityService == serviceStr) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        fun getAccessibilitySettingPageIntent(context: Context): Intent {
            // 一些品牌的手机可能不是这个Intent,需要适配
            return Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        }
    }
}
