package com.arvifox.coordlayout

import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class BottomBarBehavior(private val dependencyMinHeight: Float, private val dependencyMaxHeight: Float, private val bottomBarUpdateListener: IBottomBarUpdateListener) : CoordinatorLayout.Behavior<View>(){

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        var frac = (dependency.bottom - dependencyMaxHeight) / (dependencyMinHeight - dependencyMaxHeight)
        if(frac > 1)
            frac = 1f

        Log.d("BEHAVIOR", "$dependencyMinHeight $dependencyMaxHeight")

        val offset = if(frac == 1f) child.height.toFloat() else (frac * child.height)

        bottomBarUpdateListener.onTranslationY(offset)
        child.translationY = child.height.toFloat()

        return true
    }
}