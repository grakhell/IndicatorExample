package ru.mininn.recyclerindicator

interface IndicatorAdapter {

    fun getIndicatorCount(): Int

    fun getItemColor(position: Int): Int

}