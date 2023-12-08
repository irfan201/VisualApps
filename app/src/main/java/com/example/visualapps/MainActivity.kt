package com.example.visualapps

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.example.visualapps.databinding.ActivityMainBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class MainActivity : AppCompatActivity() {

    private lateinit var lineChart: LineChart
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lineChart = binding.lineChart


        val systoleData = listOf(120, 130, 125, 140, 135, 128)


        setData(systoleData, "Systole Pressure")


        customizeChart()


        enableInteractions()
    }

    private fun setData(data: List<Int>, label: String) {
        val entries = ArrayList<Entry>()

        for (i in data.indices) {
            entries.add(Entry(i.toFloat(), data[i].toFloat()))
        }

        val dataSet = LineDataSet(entries, label)
        dataSet.color = ColorTemplate.COLORFUL_COLORS[0]
        dataSet.setCircleColor(ColorTemplate.COLORFUL_COLORS[0])
        dataSet.setDrawFilled(true)
        dataSet.fillColor = ColorTemplate.COLORFUL_COLORS[0]


        val lineDataSets = ArrayList<ILineDataSet>()
        lineDataSets.add(dataSet)

        val lineData = LineData(lineDataSets)
        lineChart.data = lineData
    }

    private fun customizeChart() {
        lineChart.description.text = "Blood Pressure Chart"
        lineChart.description.textSize = 14f
        lineChart.description.textColor = resources.getColor(android.R.color.black)
        lineChart.legend.isEnabled = true
    }

    private fun enableInteractions() {
        lineChart.setPinchZoom(true)
        lineChart.isDoubleTapToZoomEnabled = true
        lineChart.setTouchEnabled(true)

        lineChart.setOnChartGestureListener(object : OnChartGestureListener {
            override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
                // Handle gesture start
            }

            override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartTouchListener.ChartGesture?) {
                // Handle gesture end
            }

            override fun onChartLongPressed(me: MotionEvent?) {
                // Handle long press on chart
            }

            override fun onChartDoubleTapped(me: MotionEvent?) {
                // Handle double tap on chart
            }

            override fun onChartSingleTapped(me: MotionEvent?) {

                if (me != null) {
                    val xVal = me.x
                    val yVal = me.y

                    ShowDialog("Clicked on point", xVal,yVal)
                }
            }

            override fun onChartFling(me1: MotionEvent?, me2: MotionEvent?, velocityX: Float, velocityY: Float) {

            }

            override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
                if (scaleX < 1f) {
                    val newScaleX = Math.max(scaleX, 0.5f)
                    val newScaleY = Math.max(scaleY, 0.5f)
                    lineChart.zoom(newScaleX, newScaleY, lineChart.pivotX, lineChart.pivotY)
                }
            }

            override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {

            }
        })

        lineChart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                // Handle value selected
                if (e != null) {
                    val xVal = e.x
                    val yVal = e.y

                    ShowDialog("Selected value", xVal,yVal)
                }
            }

            override fun onNothingSelected() {
                // Handle nothing selected
            }
        })
    }


    private fun ShowDialog(title: String, xVal: Float, yVal: Float) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
            .setMessage("x=$xVal, y=$yVal")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        val dialog = builder.create()
        dialog.show()
    }
}