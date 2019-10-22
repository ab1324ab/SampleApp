package com.accessibility

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.accessibility.utils.AccessibilityUtil;

import java.util.Timer;
import java.util.TimerTask;

class AccessibilityOpenHelperActivity : Activity() {
    private var isFirstCome = true
    private val ACTION = "action"
    private val ACTION_FINISH_SELF = "action_finis_self"

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null
    private var mTimeoutCounter = 0

    private val TIMEOUT_MAX_INTERVAL = 60 * 2 // 2 min

    private val TIMER_CHECK_INTERVAL: Long = 1000
    protected var mHandle: Handler? = Handler()
    protected var tipToastDelayRunnable: Runnable? = null

    private fun removeDelayedToastTask() {
        if (mHandle != null && tipToastDelayRunnable != null) {
            mHandle!!.removeCallbacks(tipToastDelayRunnable)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility_transparent_layout)
        val intent = intent
        if (intent != null && intent.extras != null) {
            val action = intent.getStringExtra(ACTION)
            if (ACTION_FINISH_SELF.equals(action)) {
                finishCurrentActivity()
                return
            }
        }
        mTimeoutCounter = 0
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishCurrentActivity()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null && intent.extras != null) {
            val action = intent.getStringExtra(ACTION)
            if (ACTION_FINISH_SELF.equals(action)) {
                finishCurrentActivity()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!isFirstCome) {
            removeDelayedToastTask()
            finishCurrentActivity()
        } else {
            jumpActivities()
            startCheckAccessibilityOpen()
        }
        isFirstCome = false
    }

    private fun jumpActivities() {
        try {
            val intent = AccessibilityUtil.getAccessibilitySettingPageIntent(this)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        freeTimeTask()
        super.onDestroy()
    }

    private fun finishCurrentActivity() {
        freeTimeTask()
        finish()
    }

    private fun startCheckAccessibilityOpen() {
        freeTimeTask()
        initTimeTask()
        timer!!.schedule(timerTask, 0, TIMER_CHECK_INTERVAL)
    }

    private fun initTimeTask() {
        timer = Timer()
        mTimeoutCounter = 0
        timerTask = object : TimerTask() {

            override fun run() {
                if (AccessibilityUtil.isAccessibilitySettingsOn(this@AccessibilityOpenHelperActivity)) {
                    freeTimeTask()
                    Looper.prepare()
                    try {
                        this@AccessibilityOpenHelperActivity.runOnUiThread {
                            Toast.makeText(
                                this@AccessibilityOpenHelperActivity,
                                "辅助功能开启成功",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        val intent = Intent()
                        intent.putExtra(ACTION, ACTION_FINISH_SELF)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        intent.setClass(
                            this@AccessibilityOpenHelperActivity,
                            this@AccessibilityOpenHelperActivity.javaClass
                        )
                        startActivity(intent)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }

                    Looper.loop()
                }
                // 超过2分钟超时，就释放timer。
                mTimeoutCounter++
                if (mTimeoutCounter > TIMEOUT_MAX_INTERVAL) {
                    freeTimeTask()
                }
            }
        }
    }

    private fun freeTimeTask() {
        if (timerTask != null) {
            timerTask!!.cancel()
            timerTask = null
        }
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }
}
