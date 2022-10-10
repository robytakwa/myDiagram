package com.example.mydiagram.data

import android.graphics.Color
import com.example.mydiagram.utils.Fill
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleDataSet
import com.github.mikephil.charting.data.DataSet
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet


class BarDataSet(yVals: List<BarEntry>, label: String?) :
    BarLineScatterCandleBubbleDataSet<BarEntry?>(yVals, label), IBarDataSet {
    /**
     * the maximum number of bars that are stacked upon each other, this value
     * is calculated from the Entries that are added to the DataSet
     */
    private var mStackSize = 1

    /**
     * the color used for drawing the bar shadows
     */
    private var mBarShadowColor = Color.rgb(215, 215, 215)
    private var mBarBorderWidth = 0.0f
    private var mBarBorderColor = Color.BLACK

    /**
     * the alpha value used to draw the highlight indicator bar
     */
    private var mHighLightAlpha = 120
    /**
     * returns the overall entry count, including counting each stack-value
     * individually
     *
     * @return
     */
    /**
     * the overall entry count, including counting each stack-value individually
     */
    var entryCountStacks = 0
        private set

    /**
     * array of labels used to describe the different values of the stacked bars
     */
    private var mStackLabels = arrayOf<String>()
    protected var mFills: MutableList<Fill>? = null

    init {
        mHighLightColor = Color.rgb(0, 0, 0)
        calcStackSize(yVals)
        calcEntryCountIncludingStacks(yVals)
    }

    override fun copy(): DataSet<BarEntry?>? {
        val entries: MutableList<BarEntry> = ArrayList()
       for (i in 0 until mValues.size) {
           mValues[i]?.let { entries.add(it.copy()) }
            }
        val copied = BarDataSet(entries, label)
        copy(copied)
        return copied
    }

    protected fun copy(barDataSet: BarDataSet) {
        super.copy(barDataSet)
        barDataSet.mStackSize = mStackSize
        barDataSet.mBarShadowColor = mBarShadowColor
        barDataSet.mBarBorderWidth = mBarBorderWidth
        barDataSet.mStackLabels = mStackLabels
        barDataSet.mHighLightAlpha = mHighLightAlpha
    }

    val fills: List<Any>?
        get() = mFills

    fun getFill(index: Int): Fill {
        return mFills!![index % mFills!!.size]
    }

    /**
     * This method is deprecated.
     * Use getFills() instead.
     */
    @get:Deprecated("")
    val gradients: List<Any>?
        get() = mFills

    /**
     * This method is deprecated.
     * Use getFill(...) instead.
     *
     * @param index
     */
    @Deprecated("")
    fun getGradient(index: Int): Fill {
        return getFill(index)
    }

    /**
     * Sets the start and end color for gradient color, ONLY color that should be used for this DataSet.
     *
     * @param startColor
     * @param endColor
     */
    override fun setGradientColor(startColor: Int, endColor: Int) {
        mFills!!.clear()
        mFills!!.add(Fill(startColor, endColor))
    }

    /**
     * This method is deprecated.
     * Use setFills(...) instead.
     *
     * @param gradientColors
     */
    @Deprecated("")
//    override fun setGradientColors(gradientColors: MutableList<Fill>?) {
//        mFills = gradientColors
//    }

    /**
     * Sets the fills for the bars in this dataset.
     *
     * @param fills
     */
    fun setFills(fills: MutableList<Fill>?) {
        mFills = fills
    }

    /**
     * Calculates the total number of entries this DataSet represents, including
     * stacks. All values belonging to a stack are calculated separately.
     */
    private fun calcEntryCountIncludingStacks(yVals: List<BarEntry>) {
        entryCountStacks = 0
        for (i in yVals.indices) {
            val vals = yVals[i].yVals
            if (vals == null) entryCountStacks++ else entryCountStacks += vals.size
        }
    }

    /**
     * calculates the maximum stacksize that occurs in the Entries array of this
     * DataSet
     */
    private fun calcStackSize(yVals: List<BarEntry>) {
        for (i in yVals.indices) {
            val vals = yVals[i].yVals
            if (vals != null && vals.size > mStackSize) mStackSize = vals.size
        }
    }

     override fun calcMinMax(e: BarEntry?) {
        if (e != null && !java.lang.Float.isNaN(e.y)) {
            if (e.yVals == null) {
                if (e.y < mYMin) mYMin = e.y
                if (e.y > mYMax) mYMax = e.y
            } else {
                if (-e.negativeSum < mYMin) mYMin = -e.negativeSum
                if (e.positiveSum > mYMax) mYMax = e.positiveSum
            }
            calcMinMaxX(e)
        }
    }

    override fun getStackSize(): Int {
        return mStackSize
    }

    override fun getEntryIndex(e: BarEntry?): Int {
        for (i in 0 until mValues.size) {
            if (mValues[i] === e) return i
        }
        return -1
    }


    override fun isStacked(): Boolean {
        return if (mStackSize > 1) true else false
    }

    /**
     * Sets the color used for drawing the bar-shadows. The bar shadows is a
     * surface behind the bar that indicates the maximum value. Don't for get to
     * use getResources().getColor(...) to set this. Or Color.rgb(...).
     *
     * @param color
     */
    fun setBarShadowColor(color: Int) {
        mBarShadowColor = color
    }

    override fun getBarShadowColor(): Int {
        return mBarShadowColor
    }

    /**
     * Sets the width used for drawing borders around the bars.
     * If borderWidth == 0, no border will be drawn.
     *
     * @return
     */
    fun setBarBorderWidth(width: Float) {
        mBarBorderWidth = width
    }

    /**
     * Returns the width used for drawing borders around the bars.
     * If borderWidth == 0, no border will be drawn.
     *
     * @return
     */
    override fun getBarBorderWidth(): Float {
        return mBarBorderWidth
    }

    /**
     * Sets the color drawing borders around the bars.
     *
     * @return
     */
    fun setBarBorderColor(color: Int) {
        mBarBorderColor = color
    }

    /**
     * Returns the color drawing borders around the bars.
     *
     * @return
     */
    override fun getBarBorderColor(): Int {
        return mBarBorderColor
    }

    /**
     * Set the alpha value (transparency) that is used for drawing the highlight
     * indicator bar. min = 0 (fully transparent), max = 255 (fully opaque)
     *
     * @param alpha
     */
    fun setHighLightAlpha(alpha: Int) {
        mHighLightAlpha = alpha
    }

    override fun getHighLightAlpha(): Int {
        return mHighLightAlpha
    }

    /**
     * Sets labels for different values of bar-stacks, in case there are one.
     *
     * @param labels
     */
    fun setStackLabels(labels: Array<String>) {
        mStackLabels = labels
    }

    override fun getStackLabels(): Array<String> {
        return mStackLabels
    }
}
