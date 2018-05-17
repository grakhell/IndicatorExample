package ru.experimental.indicatorview

import android.view.View

interface IndicatorAnimator {

    val SCALE_STATE_NORMAL:Float
        get() = 0.5f
    val SCALE_STATE_SELECTED:Float
        get() = 1f
    val SCALE_STATE_SMALL:Float
        get() = 0.2f
    val SCALE_STATE_INVISIBLE:Float
        get() = 0.0f

    fun init()
    fun animateItem(view: View, scale: Float, animate: Boolean)
    fun animateTransition(animate: Boolean)
}