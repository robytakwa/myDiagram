package com.example.mydiagram

import android.graphics.Color.red
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class AnotherBarActivity : DemoBase(), OnSeekBarChangeListener {
    private var chart: BarChart? = null
    private var seekBarX: SeekBar? = null
    private var seekBarY: SeekBar? = null
    private var tvX: TextView? = null
    private var tvY: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_barchart)
        title = "Diagram"
        tvX = findViewById(R.id.tvXMax)
        tvY = findViewById(R.id.tvYMax)
        seekBarX = findViewById(R.id.seekBar1)
        seekBarX?.setOnSeekBarChangeListener(this)
        seekBarY = findViewById(R.id.seekBar2)
        seekBarY?.setOnSeekBarChangeListener(this)
        chart = findViewById(R.id.chart1)
        chart?.getDescription()!!.isEnabled = false

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        chart!!.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart!!.setPinchZoom(false)
        chart!!.setDrawBarShadow(false)
        chart!!.setDrawGridBackground(false)
        val xAxis = chart!!.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        chart!!.getAxisLeft().setDrawGridLines(false)

//        // setting data
//        seekBarX?.setProgress(4)
//        seekBarY?.setProgress(100)
//
//        // add a nice and smooth animation
//        chart!!.animateY(1500)
//        chart!!.getLegend().isEnabled = false
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

    }

    override fun saveToGallery() {

    }

    fun generateBarData(progress: Int, i: Int): MutableList<BarEntry>? {
        val entries = ArrayList<BarEntry>()
        for (index in 0 until progress) {
            entries.add(BarEntry(index.toFloat(), (Math.random() * i + 3).toFloat()))
        }
        return entries
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
}
