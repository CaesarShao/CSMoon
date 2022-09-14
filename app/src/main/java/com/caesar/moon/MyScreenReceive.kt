package com.caesar.moon

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
class MyScreenReceive :DeviceAdminReceiver() {
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
    }

    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
    }
}