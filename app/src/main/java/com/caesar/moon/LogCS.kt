package com.caesar.moon

import android.util.Log

/**
 * Created by Caesar
 * email : caesarshao@163.com
 */
object LogCS {
    fun I(str:String?){
        str?.apply {
            Log.i("caesarMoon",this)
        }
    }

}