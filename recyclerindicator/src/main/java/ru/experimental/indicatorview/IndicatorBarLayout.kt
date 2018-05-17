package ru.experimental.indicatorview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import ru.mininn.recyclerindicator.R
import kotlin.math.abs

class IndicatorBarLayout : LinearLayout {

    private lateinit var recyclerView: RecyclerView

    var currentPosition:Int
    var maxItemCount: Int
        private set
    private var indicatorSize:Int
    private var indicatorMargin:Int
    private var defaultDrawable:Int = R.drawable.dot
    private var defaultColor:Int  = Color.BLACK

    private var animator:IndicatorAnimator = DefaultIndicatorAnimator(this)

    constructor(context: Context?):super(context)
    {
        currentPosition = -1
        maxItemCount = 10
        indicatorSize = 5
        indicatorMargin = 2
    }

    constructor(context: Context, attrs: AttributeSet?):super(context, attrs)
    {
        val a: TypedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.IndicatorBarLayout,
                0,0)

        try {
            currentPosition = a.getInt(R.styleable.IndicatorBarLayout_currentPosition,-1)
            maxItemCount = a.getInt(R.styleable.IndicatorBarLayout_maxVisibleItemCount, 5)
            indicatorSize = a.getInt(R.styleable.IndicatorBarLayout_indicatorSize, 10)
            indicatorMargin = a.getInt(R.styleable.IndicatorBarLayout_indicatorMargin, 2)
        }
        finally {
            a.recycle()
        }
    }

    fun attachToRecyclerView(rv: RecyclerView) {
        this.recyclerView = rv
        animator.init()
        addAdapterDataObserver(rv)
        addScrollListener(rv)
        val displayMetrics = resources.displayMetrics
        this.indicatorSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                indicatorSize.toFloat(), displayMetrics).toInt()
        this.indicatorMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                indicatorMargin.toFloat(), displayMetrics).toInt()
        updateIndicatorsCount(recyclerView.adapter.itemCount)
    }

    fun setPosition(position: Int) {
        if (currentPosition == position) {
            return
        } else {
            currentPosition = position
            animator.animateTransition(true)
        }

    }

    fun updateIndicatorsCount(itemCount: Int) {
        removeAllViews()
        var x = 0
        while (x < itemCount) {
            addIndicatorOLD(x)
            x++
        }
    }

    private fun addIndicatorOLD(position: Int) {
        val view = IndicatorView(context)
        val params = ViewGroup.MarginLayoutParams(indicatorSize, indicatorSize)
        val adapter = recyclerView.adapter as IndicatorAdapterWIP
        view.setIndicator(adapter.getItemColor(position),defaultDrawable,indicatorSize,
                PorterDuff.Mode.MULTIPLY)

        params.leftMargin = indicatorMargin
        params.rightMargin = indicatorMargin
        params.topMargin = indicatorMargin
        params.bottomMargin = indicatorMargin
        addView(view, params)
        animator.animateItem(view, animator.SCALE_STATE_NORMAL, false)
    }

    fun addIndicator(Color:Int?, Drawable:Int?, Size:Int?)
    {
        val view = IndicatorView(context)
        val params = ViewGroup.MarginLayoutParams(indicatorSize, indicatorSize)

        view.setIndicator(Color?:defaultColor,
                    Drawable?:defaultDrawable,
                        Size?:indicatorSize,
                        PorterDuff.Mode.MULTIPLY)

        params.leftMargin = indicatorMargin
        params.rightMargin = indicatorMargin
        params.topMargin = indicatorMargin
        params.bottomMargin = indicatorMargin

        addView(view,params)
    }

    private fun addScrollListener(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val position = if ((recyclerView?.layoutManager
                                as LinearLayoutManager).findFirstVisibleItemPosition()
                        > currentPosition) {
                    (recyclerView.layoutManager as LinearLayoutManager)
                            .findLastVisibleItemPosition()
                } else {
                    (recyclerView.layoutManager as LinearLayoutManager)
                            .findFirstVisibleItemPosition()
                }
                if (position != currentPosition && position >= 0) {
                    setPosition(position)
                }
            }
        })
    }

    private fun addAdapterDataObserver(recyclerView: RecyclerView) {
        recyclerView.adapter.registerAdapterDataObserver(
                object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                currentPosition = (recyclerView.layoutManager
                        as LinearLayoutManager).findFirstVisibleItemPosition()
                updateIndicatorsCount(recyclerView.adapter.itemCount)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                super.onItemRangeChanged(positionStart, itemCount)
                currentPosition = positionStart
                updateIndicatorsCount(recyclerView.adapter.itemCount)
            }

            override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                super.onItemRangeChanged(positionStart, itemCount, payload)
                currentPosition = positionStart
                updateIndicatorsCount(recyclerView.adapter.itemCount)
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                updateIndicatorsCount(recyclerView.adapter.itemCount)
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                updateIndicatorsCount(recyclerView.adapter.itemCount)
            }

            override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                updateIndicatorsCount(recyclerView.adapter.itemCount)
            }
        })
    }

}