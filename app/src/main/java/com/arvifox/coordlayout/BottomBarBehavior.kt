package com.arvifox.coordlayout

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

class BottomBarBehavior(private val toolBarResId: Int, private val collapsingToolbarLayoutResId: Int, private val bottomBarUpdateListener: IBottomBarUpdateListener) : CoordinatorLayout.Behavior<View>(){

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
        val collapsingTb = parent.findViewById<CollapsingToolbarLayout>(collapsingToolbarLayoutResId) ?: return false
        val toolBar = parent.findViewById<Toolbar>(toolBarResId) ?: return false

        val minHeight = toolBar.height.toFloat()
        val maxHeight = collapsingTb.height.toFloat()

        var frac = (dependency.bottom - maxHeight) / (minHeight - maxHeight)
        if(frac > 1)
            frac = 1f
        else if(frac < 0f)
            frac = 0f

        val offset = if(frac == 1f) child.height.toFloat() else (frac * child.height)

        bottomBarUpdateListener.onTranslationY(offset)
        child.translationY = child.height.toFloat()

        return true
    }
}