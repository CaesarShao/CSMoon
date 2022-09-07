package com.caesar.moon

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.os.Build

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
class NotifyManager {
    fun showNotify(context: Context?) {
        val notification = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(context, "Moon")
        } else {
            Notification.Builder(context)
        }
        notification.setSmallIcon(R.mipmap.ic_launcher)
        notification.setContentTitle("夜间模式")
        notification.setContentText("夜间模式启动中")
        val notify = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notification.build()
        } else {
            Notification()
        }
        notify.flags = notify.flags or Notification.FLAG_NO_CLEAR
        val notifyManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifyManager.notify(1, notify)
    }

    fun cancleNotify(context: Context?){
        val notifyManager =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notifyManager.cancel(1)
    }
}