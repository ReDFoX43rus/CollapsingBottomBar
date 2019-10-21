package com.arvifox.coordlayout

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.addListener
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

    override fun showBottomBar(onEnd: (Animator) -> Unit) {
        ObjectAnimator.ofFloat(navigation_container, "translationY", navigation_container.height.toFloat(), 0f).apply {
            duration = 200
            interpolator = DecelerateInterpolator()
            addListener(onEnd = onEnd)
            start()
        }
    }

    override fun hideBottomBar(onEnd: (Animator) -> Unit) {
        ObjectAnimator.ofFloat(navigation_container, "translationY", 0f, navigation_container.height.toFloat()).apply {
            duration = 200
            interpolator = DecelerateInterpolator()
            addListener(onEnd = onEnd)
            start()
        }
    }
}

interface IBottomBarUpdateListener {
    fun onTranslationY(translation: Float)
    fun showBottomBar(onEnd: (Animator) -> Unit)
    fun hideBottomBar(onEnd: (Animator) -> Unit)
}
