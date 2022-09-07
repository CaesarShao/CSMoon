package com.caesar.moon

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.*

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
class GuardService:Service() {

    override fun onCreate() {
        super.onCreate()
        LogCS.I("守护进程,创建了")
        bindMainProcess()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogCS.I("守护进程onStartCommand创建了")
        startTimeCount()
        return super.onStartCommand(intent, flags, startId)
    }

    var job: Job? = null
    private fun startTimeCount() {
        cancleTimeCount()
        job = GlobalScope.launch(Dispatchers.IO) {
            delay(30000)
            startTimeCount()
        }
    }

    private fun cancleTimeCount() {
        job?.cancel()
        job = null
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
    fun bindMainProcess() {
        LogCS.I("守护进程,绑定了ConfigService")
        val intent = Intent(this, ConfigService::class.java)
        startService(intent)
        bindService(intent, remoteConn, Context.BIND_IMPORTANT)
    }


    override fun onDestroy() {
        unbindService(remoteConn)
        bindMainProcess()
        super.onDestroy()
    }
    private val remoteConn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            LogCS.I("跟主进程的AppHelpService服务绑定上:" + name)
        }

        override fun onServiceDisconnected(name: ComponentName) {
            LogCS.I("主进程的AppHelpService服务断开了,进行重新绑定:" + name)
            bindMainProcess()
        }
    }
}