package com.caesar.moon

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class BlackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_black)
        lightConfig()
    }

    private fun lightConfig(){
        val lp = window.attributes
        lp.screenBrightness = 0f
        window.attributes = lp

    }
}