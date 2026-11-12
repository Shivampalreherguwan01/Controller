package com.controller2.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class MyAccessibilityService : AccessibilityService() {

    private var isVolumeUpPressed = false
    private var isPowerPressed = false
    private val handler = Handler(Looper.getMainLooper())
    private var triggerRunnable: Runnable? = null
    private var isTriggered = false

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Not required for key monitoring
    }

    override fun onInterrupt() {}

    override fun onKeyEvent(event: KeyEvent?): Boolean {
        if (event == null) return false

        val keyCode = event.keyCode
        val action = event.action

        // Track Volume Up and Power button states
        if (keyCode == KeyEvent.KEYCODE_VOLUME_UP) {
            isVolumeUpPressed = (action == KeyEvent.ACTION_DOWN)
        } else if (keyCode == KeyEvent.KEYCODE_POWER) {
            isPowerPressed = (action == KeyEvent.ACTION_DOWN)
        }

        // Check if both Volume Up and Power are pressed together
        if (isVolumeUpPressed && isPowerPressed) {
            if (!isTriggered) {
                isTriggered = true
                // Wait for 1 second (1000ms) hold duration
                triggerRunnable = Runnable {
                    if (isVolumeUpPressed && isPowerPressed) {
                        openMainActivity()
                    }
                }
                handler.postDelayed(triggerRunnable!!, 1000)
            }
        } else {
            // Reset if keys are released before 1 second
            if (action == KeyEvent.ACTION_UP) {
                if (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_POWER) {
                    isTriggered = false
                    triggerRunnable?.let { handler.removeCallbacks(it) }
                }
            }
        }

        return super.onKeyEvent(event)
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
    }
}
