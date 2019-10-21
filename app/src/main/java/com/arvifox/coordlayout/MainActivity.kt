package com.arvifox.coordlayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IBottomBarUpdateListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.content_frame, MobFragment.newInstance(this), "").commit()
    }

    override fun onTranslationY(translation: Float) {
        navigation_container.translationY = translation
    }
}

interface IBottomBarUpdateListener {
    fun onTranslationY(translation: Float)
}
