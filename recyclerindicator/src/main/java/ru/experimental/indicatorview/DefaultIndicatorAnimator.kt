package ru.experimental.indicatorview

import android.view.View

class DefaultIndicatorAnimator:IndicatorAnimator {

    val SCALE_STATE_NORMAL = 0.5f
    val SCALE_STATE_SELECTED = 1f
    val SCALE_STATE_SMALL = 0.2f
    val SCALE_STATE_INVISIBLE = 0.0f

    override fun init() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun animateItem(view: View, scale: Float, animate: Boolean) {
        if (scale == SCALE_STATE_INVISIBLE) {
            view.visibility = View.GONE
            view.animate().scaleY(scale).scaleX(scale)
        } else {
            view.visibility = View.VISIBLE
            if (animate) {
                view.animate().scaleY(scale).scaleX(scale)
            } else {
                view.scaleX = scale
                view.scaleY = scale
            }
        }
    }

    override fun animateTransition(animate: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}