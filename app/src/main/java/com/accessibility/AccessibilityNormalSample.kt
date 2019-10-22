package com.accessibility

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button
import com.accessibility.utils.AccessibilityLog;
import kotlinx.android.synthetic.main.activity_accessibility_normal_sample.*


class AccessibilityNormalSample :Activity(),View.OnClickListener{
    private val mHandler = Handler(Looper.getMainLooper())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accessibility_normal_sample)
        println("zzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzzz")
        initViews()
    }

    private fun initViews() {
        //findViewById<Button>(R.id.normal_sample_back).setOnClickListener(this)
        normal_sample_back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val id = v.id
        when (id) {
            R.id.normal_sample_back -> finish()
        }
    }

    override fun onResume() {
        super.onResume()
        mHandler.postDelayed({
            //                simulationClickByText();
            simulationClickById()
        }, 2000)
    }

    private fun simulationClickByText() {
        val result = AccessibilityOperator.getInstance().clickByText("复选框开关")
        AccessibilityLog.printLog(if (result) "复选框模拟点击成功" else "复选框模拟点击失败")
        mHandler.postDelayed({
            val result = AccessibilityOperator.getInstance().clickByText("单选按钮")
            AccessibilityLog.printLog(if (result) "单选按钮模拟点击成功" else "单选按钮模拟点击失败")
        }, 2000)
        mHandler.postDelayed({
            val result = AccessibilityOperator.getInstance().clickByText("OFF")
            AccessibilityLog.printLog(if (result) "OnOff开关模拟点击成功" else "OnOff开关模拟点击失败")
        }, 4000)
        mHandler.postDelayed({
            val result = AccessibilityOperator.getInstance().clickByText("退出本页面")
            AccessibilityLog.printLog(if (result) "退出本页面模拟点击成功" else "退出本页面模拟点击失败")
        }, 6000)
    }

    private fun simulationClickById() {
        val result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_checkbox")
        AccessibilityLog.printLog(if (result) "复选框模拟点击成功" else "复选框模拟点击失败")
        mHandler.postDelayed({
            val result = AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_radiobutton")
            AccessibilityLog.printLog(if (result) "单选按钮模拟点击成功" else "单选按钮模拟点击失败")
        }, 2000)
        mHandler.postDelayed({
            val result =
                AccessibilityOperator.getInstance().clickById("com.accessibility:id/normal_sample_togglebutton")
            AccessibilityLog.printLog(if (result) "OnOff开关模拟点击成功" else "OnOff开关模拟点击失败")
        }, 4000)
        mHandler.postDelayed({
//          boolean result = AccessibilityOperator().getInstance().clickById("com.accessibility:id/normal_sample_back");
//          AccessibilityLog().printLog(result ? "退出本页面模拟点击成功" : "退出本页面模拟点击失败");
            // 下面这个模拟点击系统返回键
            val result = AccessibilityOperator.getInstance().clickBackKey()
            AccessibilityLog.printLog(if (result) "返回键模拟点击成功" else "返回键模拟点击失败")
        }, 6000)
    }
}