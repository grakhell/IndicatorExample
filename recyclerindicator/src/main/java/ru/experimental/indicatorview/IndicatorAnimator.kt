package ru.experimental.indicatorview

import android.view.View

interface IndicatorAnimator {
        
    fun init()
    fun animateItem(view: View, scale: Float, animate: Boolean)
    fun animateTransition(animate: Boolean)
}