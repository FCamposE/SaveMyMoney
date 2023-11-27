package com.proyecto.android.savemymoney

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.proyecto.android.savemymoney.modelo.Gasto

class AgregarGastoActivity : AppCompatActivity() {

    private lateinit var etPrecio: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnAgregarGasto: Button
    private lateinit var btnSeleccionarFecha: Button
    private var calendar: Calendar = Calendar.getInstance()
    private lateinit var gastos: ArrayList<Gasto>
    private lateinit var spCategorias: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_gasto)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Habilitar el botón de retroceso en el Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.gastos = intent.getSerializableExtra("listaGastosPrincipal") as ArrayList<Gasto>
        this.iniciarComponentes()
    }

    private fun iniciarComponentes() {
        this.etPrecio = findViewById(R.id.etPrecio)
        this.etDescripcion = findViewById(R.id.etmlDescripcion)
        this.etFecha = findViewById(R.id.etFecha)
        this.btnAgregarGasto = findViewById(R.id.btnAgregarGasto)
        this.btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)
        this.spCategorias = findViewById(R.id.spCategoria)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.categorias_spinner,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spCategorias.adapter = adapter

        spCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {

            /*when (pos) {
                    0 -> {
                        // Código a ejecutar cuando se selecciona la opción 1
                    }
                    1 -> {
                        // Código a ejecutar cuando se selecciona la opción 2
                    }
                    2 -> {
                        // Código a ejecutar cuando se selecciona la opción 3
                    }
                }*/
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No es necesario realizar ninguna acción aquí, simplemente lo dejamos vacío
            }
        }

        this.btnSeleccionarFecha.setOnClickListener {
            showDatePickerDialog()
        }

        this.btnAgregarGasto.setOnClickListener {
            this.gastos.add(this.obtenerDatosGasto())
            //Toast.makeText(this, "Se agregó el nuevo gasto", Toast.LENGTH_SHORT).show()

            Toast.makeText(this, getSelectedOption(), Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PrincipalActivity::class.java)
            intent.putParcelableArrayListExtra("listaGastosAgregar", this.gastos)
            startActivity(intent)
        }
    }
    private fun getSelectedOption(): String{
        val pos = spCategorias.selectedItemPosition
        return resources.getStringArray(R.array.categorias_spinner)[pos]
    }


    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                etFecha.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun obtenerDatosGasto(): Gasto {
        val gasto = Gasto(
            getSelectedOption(),
            this.etDescripcion.text?.toString(),
            this.etPrecio.text.toString().toDouble(),
            this.etFecha.text?.toString()
        )
        return gasto
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
