package ru.experimental.indicatorview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.view.View
import ru.mininn.recyclerindicator.R
import kotlin.math.min

class IndicatorView(context: Context): View(context) {

    private var color:Int = Color.BLACK
    private var drawable:Drawable? = ContextCompat.getDrawable(context,R.drawable.dot)
    private var size = 10

    fun setIndicator(Color:Int, Drawable:Int, Size:Int)
    {
        color = Color
        drawable = ContextCompat.getDrawable(context,Drawable)?:ContextCompat.getDrawable(context,R.drawable.dot)
        size = Size
        invalidate()
        requestLayout()
    }

    override fun onDraw(canvas: Canvas?) {
        drawable?.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
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
            MeasureSpec.AT_MOST -> {width = widthSize}
            MeasureSpec.EXACTLY -> {width = min(size,widthSize)}
            MeasureSpec.UNSPECIFIED ->{width = size}
        }
        when(heightMode)
        {
            MeasureSpec.AT_MOST -> {height = heightSize}
            MeasureSpec.EXACTLY -> {height = min(size,heightSize)}
            MeasureSpec.UNSPECIFIED ->{height = size}
        }
        setMeasuredDimension(width,height)
    }
}