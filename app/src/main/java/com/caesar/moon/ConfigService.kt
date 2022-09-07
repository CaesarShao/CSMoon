package com.caesar.moon

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.ServiceConnection
import android.os.IBinder
import kotlinx.coroutines.*
import java.util.*

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
class ConfigService : Service() {
    override fun onCreate() {
        super.onCreate()
        LogCS.I("服务1创建了")
        NotifyManager().showNotify(this)
        bindGuardService()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        LogCS.I("服务1启动了")
        startTimeCount()
        return super.onStartCommand(intent, flags, startId)
    }


    var job: Job? = null
    private fun startTimeCount() {
        cancleTimeCount()
        job = GlobalScope.launch(Dispatchers.IO) {
            delay(40000)
            if (isActive){
                checkTime()
            }
        }
    }

    private fun cancleTimeCount() {
        job?.cancel()
        job = null
    }

    private suspend fun checkTime() {
//        LogCS.I("当前时间" + Calendar.getInstance().get(Calendar.HOUR_OF_DAY))
//        LogCS.I("当前时间" + Calendar.getInstance().get(Calendar.MINUTE))
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 23/*&&Calendar.getInstance().get(Calendar.MINUTE)>=30*/) {
            GlobalScope.launch(Dispatchers.Main){
                val intent = Intent(this@ConfigService, BlackActivity::class.java)
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                cancleTimeCount()
            }
        }else{
            startTimeCount()
        }
    }

    fun bindGuardService() {
        LogCS.I("绑定了守护进程")
        val intent = Intent(this, GuardService::class.java)
        startService(intent)
        bindService(intent, remoteConn, Context.BIND_IMPORTANT)
    }

    override fun onDestroy() {
        NotifyManager().cancleNotify(this)
        unbindService(remoteConn)
        bindGuardService()
        super.onDestroy()
    }

    private val remoteConn: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            LogCS.I("跟守护进程的GuardService服务绑定上:$name")
        }

        override fun onServiceDisconnected(name: ComponentName) {
            LogCS.I("守护进程的GuardService服务断开了,进行重新绑定:$name")
            bindGuardService()
        }
    }
}
