package com.proyecto.android.savemymoney

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class DashboardActivity : AppCompatActivity() {

    private lateinit var graficoTorta: PieChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        graficoTorta = findViewById(R.id.pie_chart)

        setData()

        graficoTorta.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
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
        })
    }

    private fun setData() {
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
