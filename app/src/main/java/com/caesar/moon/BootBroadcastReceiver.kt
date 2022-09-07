package com.caesar.moon

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
class BootBroadcastReceiver : BroadcastReceiver() {
//    private val actionBoot = "android.intent.action.BOOT_COMPLETED"
    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?.startService(Intent(p0, ConfigService::class.java))
    }
}