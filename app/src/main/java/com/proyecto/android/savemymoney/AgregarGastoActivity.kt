package com.proyecto.android.savemymoney

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.proyecto.android.savemymoney.modelo.Gasto

class AgregarGastoActivity : AppCompatActivity() {

    private lateinit var etTipo: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnAgregarGasto: Button
    private lateinit var btnSeleccionarFecha: Button
    private var calendar: Calendar = Calendar.getInstance()
    private lateinit var gastos: ArrayList<Gasto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_gasto)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        this.gastos = intent.getSerializableExtra("listaGastosPrincipal") as ArrayList<Gasto>
        this.iniciarComponentes()
    }

    private fun iniciarComponentes() {
        this.etTipo = findViewById(R.id.etTipo)
        this.etPrecio = findViewById(R.id.etPrecio)
        this.etDescripcion = findViewById(R.id.etmlDescripcion)
        this.etFecha = findViewById(R.id.etFecha)
        this.btnAgregarGasto = findViewById(R.id.btnAgregarGasto)
        this.btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFecha)

        this.btnSeleccionarFecha.setOnClickListener {
            showDatePickerDialog()
        }

        this.btnAgregarGasto.setOnClickListener {
            this.gastos.add(this.obtenerDatosGasto())
            Toast.makeText(this, "Se agregó el nuevo gasto", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PrincipalActivity::class.java)
            intent.putParcelableArrayListExtra("listaGastosAgregar", this.gastos)
            startActivity(intent)
        }
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
            this.etTipo.text?.toString(),
            this.etDescripcion.text?.toString(),
            this.etPrecio.text.toString().toDouble(),
            this.etFecha.text?.toString()
        )
        return gasto
    }
}