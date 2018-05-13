package ru.experimental.indicatorview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ru.mininn.recyclerindicator.R
import kotlin.math.min

class IndicatorView(context: Context): View(context) {

    var color:Int = Color.BLACK
    var drawable:Drawable? = ContextCompat.getDrawable(context,R.drawable.dot)
    var size = 10
    var porterDuffMode:PorterDuff.Mode = PorterDuff.Mode.SRC_ATOP

    constructor(context: Context, attributes: AttributeSet ):this(context)
    {
        val a:TypedArray = context.obtainStyledAttributes(
                attributes,
                R.styleable.IndicatorView,
        0,0)
        try {
            color = a.getInt(R.styleable.IndicatorView_drawColor,Color.BLACK)
            size = a.getInt(R.styleable.IndicatorView_size, 10)
            drawable = a.getDrawable(R.styleable.IndicatorView_drawable)
        }
        finally {
            a.recycle()
        }
    }

    fun setIndicator(Color:Int, Drawable:Int, Size:Int, Mode:PorterDuff.Mode)
    {
        color = Color
        drawable = ContextCompat.getDrawable(context,Drawable)?:
                ContextCompat.getDrawable(context,R.drawable.dot)
        size = Size
        porterDuffMode = Mode
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        drawable?.mutate()
        drawable?.setColorFilter(color, porterDuffMode)
        drawable?.bounds = canvas?.clipBounds
        drawable?.draw(canvas)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        var width = 0
        var height = 0


        when(widthMode)
        {
            MeasureSpec.EXACTLY -> {width = widthSize}
            MeasureSpec.AT_MOST -> {width = min(size,widthSize)}
            MeasureSpec.UNSPECIFIED ->{width = size}
        }
        when(heightMode)
        {
            MeasureSpec.EXACTLY -> {height = heightSize}
            MeasureSpec.AT_MOST -> {height = min(size,heightSize)}
            MeasureSpec.UNSPECIFIED ->{height = size}
        }
        setMeasuredDimension(width,height)
    }
}