package ru.mininn.indicatorexample

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.experimental.indicatorview.IndicatorAdapterWIP
import ru.mininn.recyclerindicator.IndicatorAdapter
import java.util.ArrayList

class ColorAdapter(var items: ArrayList<Int>) : RecyclerView.Adapter<ColorViewHolder>(), IndicatorAdapterWIP {

    override fun getIndicatorCount(): Int {
        return items.size
    }

    override fun getItemColor(position: Int): Int {
        return items[position]
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        return ColorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(items[position])
    }

}