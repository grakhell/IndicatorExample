package ru.mininn.indicatorexample

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import kotlinx.android.synthetic.main.activity_main.*
import ru.experimental.indicatorview.IndicatorBarLayout
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = this.recycler
        val items = ArrayList<Int>()
        items.add(Color.RED)
        items.add(Color.GREEN)
        items.add(Color.BLUE)
        items.add(Color.CYAN)
        items.add(Color.DKGRAY)
        items.add(Color.CYAN)
        items.add(Color.CYAN)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = ColorAdapter(items)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
        this.indicatorBar.attachToRecyclerView(recyclerView)
        items.add(Color.LTGRAY)
        items.add(Color.MAGENTA)
        recyclerView.adapter.notifyDataSetChanged()
    }
}
