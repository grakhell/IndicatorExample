package ru.mininn.indicatorexample

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View

class ColorViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    fun bind(color: Int?) {
        itemView.setBackgroundColor(color!!)
    }
}