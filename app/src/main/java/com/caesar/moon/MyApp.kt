package com.caesar.moon

import android.app.Application
import android.content.Context

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
class MyApp: Application(){
    var con:Context? = null
    override fun onCreate() {
        super.onCreate()
        con = this
    }
}