package com.accessibility

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import com.accessibility.utils.AccessibilityLog

/**
 * 2019年10月8日11:05:48
 */
class AccessibilitySampleService : AccessibilityService() {

    override fun onServiceConnected(){
        super.onServiceConnected()
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // 此方法是在主线程中回调过来的，所以消息是阻塞执行的
        // 获取包名
        var pkgName : String = event!!.packageName.toString()
        var eventType:Int = event!!.eventType;
        AccessibilityOperator.getInstance().updateEvent(this,event)
        AccessibilityLog.printLog("事件类型: " + eventType + " 包名: " + pkgName)
        when(eventType){
            AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> { }
        }
    }

    override fun onInterrupt() {

    }
}