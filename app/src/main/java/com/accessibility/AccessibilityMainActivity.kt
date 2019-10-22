package com.accessibility


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_accessibility_main.*
import kotlinx.android.synthetic.main.activity_main.*

class AccessibilityMainActivity : Activity(), View.OnClickListener {

    private var mOpenSetting: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility_main)
        initView()
        AccessibilityOperator.getInstance().init(this);
    }

    private fun initView() {
        //mOpenSetting = findViewById<Button>(R.id.open_accessibility_setting)
        open_accessibility_setting.setOnClickListener(this);
        //mOpenSetting!!.setOnClickListener(this)
        accessibility_find_and_click.setOnClickListener(this);
        //findViewById<Button>(R.id.accessibility_find_and_click).setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.open_accessibility_setting -> {
                OpenAccessibilitySettingHelper.jumpToSettingPage(this)
            }
            R.id.accessibility_find_and_click -> {
                startActivity(Intent(this, AccessibilityNormalSample::class.java))
            }
        }
    }
}
