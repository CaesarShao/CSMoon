package com.caesar.moon

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        startReceiver()
        lightConfig()
        startService()
    }

    private fun initView(){
        findViewById<Button>(R.id.button).setOnClickListener {
            startService()
        }
    }

    fun startService(){
        val intent = Intent(this,ConfigService::class.java)
        startService(intent)

    }


    fun startReceiver(){
        val receiver = BootBroadcastReceiver()
        var receiverFilter= IntentFilter()
        receiverFilter.addAction("android.intent.action.BOOT_COMPLETED")
        registerReceiver(receiver,receiverFilter)
    }

    fun shotDown(){
        try {

            //获得ServiceManager类
            val ServiceManager = Class.forName("android.os.ServiceManager")

            //获得ServiceManager的getService方法
            val getService: Method = ServiceManager.getMethod("getService", String::class.java)

            //调用getService获取RemoteService
            val oRemoteService: Any = getService.invoke(null, POWER_SERVICE)

            //获得IPowerManager.Stub类
            val cStub = Class.forName("android.os.IPowerManager\$Stub")
            //获得asInterface方法
            val asInterface: Method = cStub.getMethod("asInterface", IBinder::class.java)
            //调用asInterface方法获取IPowerManager对象
            val oIPowerManager: Any = asInterface.invoke(null, oRemoteService)
            //获得shutdown()方法
            val shutdown: Method = oIPowerManager.javaClass.getMethod(
                "shutdown",
                Boolean::class.javaPrimitiveType,
                Boolean::class.javaPrimitiveType
            )
            //调用shutdown()方法
            shutdown.invoke(oIPowerManager, false, true)
        } catch (e: Exception) {
        }
    }

    fun lightConfig(){
        val lp = window.attributes
        lp.screenBrightness = 0f
        window.attributes = lp

    }
}