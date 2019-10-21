package com.arvifox.coordlayout

import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class BottomBarBehavior(private val bottomBarUpdateListener: IBottomBarUpdateListener,
                        private val posTriggerThresholdPx: Int = 6) : CoordinatorLayout.Behavior<View>(){

    private var animating = false
    private var state: ViewState = ViewState.VISIBLE
    private var prevPos: Float = -1f

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
        val currentPos = dependency.bottom.toFloat()
        if(prevPos == -1f){
            prevPos = currentPos
            return false
        }

        if(prevPos - currentPos < -posTriggerThresholdPx && state == ViewState.HIDDEN && !animating) {
            animating = true
            bottomBarUpdateListener.showBottomBar {
                animating = false
                state = ViewState.VISIBLE
            }
        }

        if(prevPos - currentPos > posTriggerThresholdPx && state == ViewState.VISIBLE && !animating) {
            animating = true
            bottomBarUpdateListener.hideBottomBar {
                animating = false
                state = ViewState.HIDDEN
            }
        }

        prevPos = currentPos

        return true
    }

    private enum class ViewState {
        HIDDEN,
        VISIBLE
    }
}