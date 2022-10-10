package com.example.mydiagram

import android.graphics.RectF
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.mydiagram.custom.DayAxisValueFormatter
import com.example.mydiagram.custom.MyAxisValueFormatter
import com.example.mydiagram.custom.XYMarkerView
import com.example.mydiagram.data.BarDataSet
import com.example.mydiagram.utils.Fill
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.Legend.LegendForm
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.components.YAxis.YAxisLabelPosition
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF


class BarChartActivity : DemoBase(), OnSeekBarChangeListener,
    OnChartValueSelectedListener {
    private var chart: BarChart? = null
    private var seekBarX: SeekBar? = null
    private var seekBarY: SeekBar? = null
    private var tvX: TextView? = null
    private var tvY: TextView? = null
    var mFills: MutableList<Fill>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_barchart)
        title = "BarChartActivity"
        tvX = findViewById(R.id.tvXMax)
        tvY = findViewById(R.id.tvYMax)
        seekBarX = findViewById(R.id.seekBar1)
        seekBarY = findViewById(R.id.seekBar2)
        seekBarY!!.setOnSeekBarChangeListener(this)
        seekBarX!!.setOnSeekBarChangeListener(this)
        chart = findViewById(R.id.chart1)
        chart!!.setOnChartValueSelectedListener(this)
        chart!!.setDrawBarShadow(false)
        chart!!.setDrawValueAboveBar(true)
        chart!!.getDescription().isEnabled = false

        chart!!.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        chart!!.setPinchZoom(false)
        chart!!.setDrawGridBackground(false)
        // chart.setDrawYLabels(false);


     val xAxisFormatter : IAxisValueFormatter = DayAxisValueFormatter(chart!!)
        val xAxis = chart!!.getXAxis()
        xAxis.position = XAxisPosition.BOTTOM
        xAxis.typeface = tfLight
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f // only intervals of 1 day
        xAxis.labelCount = 7
//        xAxis.valueFormatter = xAxisFormatter
        val custom: IAxisValueFormatter = MyAxisValueFormatter()
        val leftAxis = chart!!.getAxisLeft()
        leftAxis.typeface = tfLight
        leftAxis.setLabelCount(8, false)
//        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val rightAxis = chart!!.getAxisRight()
        rightAxis.setDrawGridLines(false)
        rightAxis.typeface = tfLight
        rightAxis.setLabelCount(8, false)
//        rightAxis.setValueFormatter(custom)
        rightAxis.spaceTop = 15f
        rightAxis.axisMinimum = 0f // this replaces setStartAtZero(true)
        val l = chart!!.getLegend()
        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.HORIZONTAL
        l.setDrawInside(false)
        l.form = LegendForm.SQUARE
        l.formSize = 9f
        l.textSize = 11f
        l.xEntrySpace = 4f
        val mv = XYMarkerView(this, xAxisFormatter)
        mv.setChartView(chart) // For bounds control
        chart!!.setMarker(mv) // Set the marker to the chart

        // setting data
        seekBarY!!.setProgress(50)
        seekBarX!!.setProgress(12)

        // chart.setDrawLegend(false);
    }

    private fun setData(count: Int, range: Float) {
        val start = 1f
        val values = ArrayList<BarEntry>()
        var i = start.toInt()
        while (i < start + count) {
            val vai = (Math.random() * (range + 1)).toFloat()
            if (Math.random() * 100 < 25) {
               values.add(BarEntry(i.toFloat(), vai, ContextCompat.getDrawable(this, R.drawable.star)))
            } else {
                values.add(BarEntry(i.toFloat(), vai))
            }
            i++
        }
        val set1: BarDataSet
        if (chart!!.data != null &&
            chart!!.data.dataSetCount > 0
        ) {
            set1 = chart!!.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values as List<BarEntry?>?
            chart!!.data.notifyDataChanged()
            chart!!.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, "The year 2017")
            set1.setDrawIcons(false)
            val startColor1 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor2 = ContextCompat.getColor(this, android.R.color.holo_blue_light)
            val startColor3 = ContextCompat.getColor(this, android.R.color.holo_orange_light)
            val startColor4 = ContextCompat.getColor(this, android.R.color.holo_green_light)
            val startColor5 = ContextCompat.getColor(this, android.R.color.holo_red_light)
            val endColor1 = ContextCompat.getColor(this, android.R.color.transparent)
            val endColor2 = ContextCompat.getColor(this, android.R.color.transparent)
            val endColor3 = ContextCompat.getColor(this, android.R.color.transparent)
            val endColor4 = ContextCompat.getColor(this, android.R.color.transparent)
            val endColor5 = ContextCompat.getColor(this, android.R.color.transparent)
            val gradientFills: MutableList<Fill> = ArrayList()
            gradientFills.add(Fill(startColor1, endColor1))
            gradientFills.add(Fill(startColor2, endColor2))
            gradientFills.add(Fill(startColor3, endColor3))
            gradientFills.add(Fill(startColor4, endColor4))
            gradientFills.add(Fill(startColor5, endColor5))
            set1.setFills(gradientFills)
            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)
            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            data.setValueTypeface(tfLight)
            data.barWidth = 0.9f
            chart!!.data = data
        }
    }


    override fun saveToGallery() {
        TODO("Not yet implemented")
    }

    override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
        tvX!!.text = seekBarX!!.progress.toString()
        tvY!!.text = seekBarY!!.progress.toString()
        setData(seekBarX!!.progress, seekBarY!!.progress.toFloat())
        chart!!.invalidate()
    }

    override fun onStartTrackingTouch(seekBar: SeekBar) {}
    override fun onStopTrackingTouch(seekBar: SeekBar) {}
    private val onValueSelectedRectF = RectF()
    override fun onValueSelected(e: Entry, h: Highlight) {
        if (e == null) return
        val bounds = onValueSelectedRectF
        chart!!.getBarBounds(e as BarEntry, bounds)
        val position = chart!!.getPosition(e, AxisDependency.LEFT)
        Log.i("bounds", bounds.toString())
        Log.i("position", position.toString())
        Log.i(
            "x-index",
            "low: " + chart!!.lowestVisibleX + ", high: "
                    + chart!!.highestVisibleX
        )
        MPPointF.recycleInstance(position)
    }

    override fun onNothingSelected() {}
}
