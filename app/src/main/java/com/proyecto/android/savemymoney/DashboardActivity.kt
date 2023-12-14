package com.proyecto.android.savemymoney

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.utils.ColorTemplate


class DashboardActivity : AppCompatActivity() {

    private lateinit var graficoTorta: PieChart
    private lateinit var spinner: Spinner
    private lateinit var barChart: BarChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        spinner = findViewById(R.id.bar_spinner)
        barChart = findViewById(R.id.bar_chart)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.bar_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                updateChart(position)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //graficoTorta = findViewById(R.id.pie_chart)

        //setData()

        //setBarData()

        /*graficoTorta.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                val pe: PieEntry = e as PieEntry
                Toast.makeText(
                    this@DashboardActivity,
                    "Value: " + e.y.toString() + "%" + ", Label: " + pe.label,
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected() {
                Toast.makeText(this@DashboardActivity, "Nothing selected", Toast.LENGTH_SHORT)
                    .show()
            }
        })*/
    }

    private fun updateChart(position: Int) {
        val newValues = ArrayList<BarEntry>()

        when (position) {
            0 -> {
                newValues.add(BarEntry(1f, 150f)) // Comida
                newValues.add(BarEntry(2f, 20f)) // Ropa
                newValues.add(BarEntry(3f, 40f)) // Servicio
                newValues.add(BarEntry(4f, 100f)) // Trabajo
            }
            1 -> {
                newValues.add(BarEntry(1f, 20f)) // Comida
                newValues.add(BarEntry(2f, 90f)) // Ropa
                newValues.add(BarEntry(3f, 65f)) // Servicio
                newValues.add(BarEntry(4f, 50f)) // Trabajo
            }
            2 -> {
                newValues.add(BarEntry(1f, 90f)) // Comida
                newValues.add(BarEntry(2f, 79f)) // Ropa
                newValues.add(BarEntry(3f, 250f)) // Servicio
                newValues.add(BarEntry(4f, 380f)) // Trabajo
            }
        }
        val barDataSet = BarDataSet(newValues, "Legenda")
        barDataSet.setColors(*ColorTemplate.VORDIPLOM_COLORS)

        val data = BarData(barDataSet)
        data.setValueTextColor(Color.BLACK)

        val xAxis = barChart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(arrayOf("0", "Comida", "Ropa", "Servicio", "Trabajo"))

        barDataSet.setDrawValues(false)

        barChart.setPinchZoom(false)
        barChart.description.isEnabled = false

        xAxis.granularity = 1f
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val rightAxis = barChart.axisRight
        rightAxis.setDrawGridLines(false)
        rightAxis.spaceTop = 35f
        rightAxis.spaceBottom = 0f
        rightAxis.setDrawLabels(false)

        barChart.getXAxis().setDrawGridLines(false)

        barChart.legend.isEnabled = false

        barChart.data = data
        barChart.invalidate()
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.duration = 1000
        animator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            barChart.setScaleY(scale)
            barChart.setScaleX(scale)
        }
        animator.start()
        barChart.animateXY(1500, 1500)

        /*val barChartData = when (position) {
            0 -> arrayListOf(
                BarEntry(0f, 25f),
                BarEntry(1f, 50f),
                BarEntry(2f, 75f)
            )
            1 -> arrayListOf(
                BarEntry(0f, 10f),
                BarEntry(1f, 30f),
                BarEntry(2f, 50f)
            )
            2 -> arrayListOf(
                BarEntry(0f, 75f),
                BarEntry(1f, 25f),
                BarEntry(2f, 50f)
            )
            else -> arrayListOf(
                BarEntry(0f, 50f),
                BarEntry(1f, 50f),
                BarEntry(2f, 50f)
            )
        }
        val dataSet = BarDataSet(barChartData, "Etiqueta de la serie")
        val data = BarData(dataSet)
        barChart.data = data
        barChart.invalidate()*/
    }

    /*private fun setData() {
        graficoTorta.description.text = "Mis gastos e ingresos"
        graficoTorta.animateXY(1400, 1400)
        graficoTorta.holeRadius = 58f
        graficoTorta.transparentCircleRadius = 61f
        graficoTorta.setDrawCenterText(true)
        graficoTorta.isDrawHoleEnabled = true
        graficoTorta.setHoleColor(Color.WHITE)

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(40f, "Categoria 1"))
        pieEntries.add(PieEntry(30f, "Categoria 2"))
        pieEntries.add(PieEntry(20f, "Categoria 3"))
        pieEntries.add(PieEntry(10f, "Categoria 4"))
        val pieDataSet = PieDataSet(pieEntries, "Mis estadisticas")
        pieDataSet.setColors(*ColorTemplate.JOYFUL_COLORS)
        pieDataSet.valueTextColor = Color.WHITE
        pieDataSet.valueTextSize = 16f
        pieDataSet.sliceSpace = 2f

        val pieData = PieData(pieDataSet)

        pieData.setValueFormatter(object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return value.toInt().toString() + "%"
            }
        })
        graficoTorta.data = pieData
    }*/

    private fun setBarData(){
        barChart.description.isEnabled = false
        barChart.setPinchZoom(false)
        barChart.setDrawBarShadow(false)
        barChart.setDrawGridBackground(false)

        val entries = arrayListOf(
            BarEntry(1f, 10f),
            BarEntry(2f, 20f),
            BarEntry(3f, 30f),
            BarEntry(4f, 40f),
            BarEntry(5f, 50f)
        )

        val barDataSet = BarDataSet(entries, "Bar DataSet")
        barDataSet.color = Color.rgb(104, 241, 175)

        val dataSets = arrayListOf(barDataSet)
        val data = BarData(dataSets as List<IBarDataSet>?)
        barChart.data = data

        barChart.invalidate()
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
