package com.caesar.moon

import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        startReceiver()
        lightConfig()
        startService()
//        lighCheck()
        initData()
    }
    private fun initData(){
        if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                !Settings.canDrawOverlays(this)
            } else {
                false
            }
        ){
            val int1 = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            int1.setData(Uri.parse("package:"+packageName))
            startActivityForResult(int1,0)
        }
    }

    private fun initView(){
        findViewById<TextView>(R.id.tvShow).text = Build.CPU_ABI+"/////"+Build.CPU_ABI2
        findViewById<Button>(R.id.button).setOnClickListener {
//            lighOn()
            val intent = Intent(this, BlackActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    fun startService(){
        val intent = Intent(this,ConfigService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)

        }else {
            startService(intent)
        }

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

    fun lighCheck(){
        val screenControl = ScreenControl.getInstance().init(this)
        if (!screenControl.isAdminActive()) {
            screenControl.openScreenPermission(this, "程序操作需要相应权限，请激活设备管理器");
        }
    }

    fun lighOn(){
        val screenControl = ScreenControl.getInstance().init(this)
        screenControl.turnOff();
    }
}