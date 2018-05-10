package ru.experimental.indicatorview

import android.graphics.Color
import ru.mininn.recyclerindicator.R

class Indicator() {
    private var color:Int = Color.BLACK
    private var drawable:Int = R.drawable.dot
    private var size = 10.0
    private var margin = 2.0

    constructor(Color:Int, Drawable:Int, Size:Double, Margin:Double):this()
    {
        color = Color
        drawable = Drawable
        size = Size
        margin = Margin
    }
}