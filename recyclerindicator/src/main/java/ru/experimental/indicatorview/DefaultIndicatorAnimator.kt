package ru.experimental.indicatorview

import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.View
import kotlin.math.abs

class DefaultIndicatorAnimator(indicatorBarLayout:IndicatorBarLayout):IndicatorAnimator {

    //override val SCALE_STATE_NORMAL = 0.5f
    //override val SCALE_STATE_SELECTED = 1f
    //override val SCALE_STATE_SMALL = 0.2f
    //override val SCALE_STATE_INVISIBLE = 0.0f

    private var indicatorBar:IndicatorBarLayout = indicatorBarLayout;

    override fun init() {
        val transition = TransitionSet()
        transition.ordering = TransitionSet.ORDERING_TOGETHER
        transition.addTransition(ChangeBounds())
        transition.addTransition(Fade())
        TransitionManager.beginDelayedTransition(indicatorBar, transition)
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
        when {
            indicatorBar.childCount <= 0 -> return
            indicatorBar.childCount <= indicatorBar.maxItemCount -> normalScaling(animate)
            else -> infinityScaling(animate)
        }
    }

    private fun infinityScaling(animate: Boolean) {
        for (i in 0 until indicatorBar.childCount) {
            when {
                indicatorBar.currentPosition < 2 -> {
                    when {
                        i == indicatorBar.currentPosition -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_SELECTED, animate)
                        indicatorBar.currentPosition != i && i <= 2 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_NORMAL, animate)
                        indicatorBar.currentPosition != i &&i == 3 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_NORMAL, animate)
                        indicatorBar.currentPosition != i &&i == 4 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_SMALL, animate)
                        else -> indicatorBar.getChildAt(i).visibility = View.GONE
                    }
                }
                indicatorBar.childCount - indicatorBar.currentPosition <= 3 ->{
                    when {
                        i == indicatorBar.currentPosition -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_SELECTED, animate)
                        indicatorBar.currentPosition != i && indicatorBar.childCount - i <= 4 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_NORMAL, animate)
                        indicatorBar.currentPosition != i &&indicatorBar.childCount - i == 5 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_SMALL, animate)
                        else -> indicatorBar.getChildAt(i).visibility = View.GONE
                    }
                }
                else -> {
                    when {
                        i == indicatorBar.currentPosition -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_SELECTED, animate)
                        abs(i - indicatorBar.currentPosition) == 1 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_NORMAL, animate)
                        abs(i - indicatorBar.currentPosition) == 2 -> animateItem(indicatorBar.getChildAt(i),
                                SCALE_STATE_SMALL, animate)
                        else -> indicatorBar.getChildAt(i).visibility = View.GONE
                    }
                }
            }

        }
    }

    private fun normalScaling(animate: Boolean) {

        for (i in 0 until indicatorBar.childCount) {
            if (indicatorBar.currentPosition == i) {
                animateItem(indicatorBar.getChildAt(i), SCALE_STATE_SELECTED, animate)
            } else {
                animateItem(indicatorBar.getChildAt(i), SCALE_STATE_NORMAL, animate)
            }
        }
    }
}