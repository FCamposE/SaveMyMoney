package com.proyecto.android.savemymoney

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.proyecto.android.savemymoney.modelo.Ingreso

class AgregarIngresoActivity : AppCompatActivity() {

    private lateinit var etTipo: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etDescripcion: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnAgregarIngreso: Button
    private lateinit var btnSeleccionarFecha: Button
    private var calendar: Calendar = Calendar.getInstance()
    private lateinit var ingresos: ArrayList<Ingreso>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_ingreso)

        val toolbar: Toolbar = findViewById(R.id.tbIngresos)
        setSupportActionBar(toolbar)

        // Habilitar el botón de retroceso en el Toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        this.ingresos = intent.getParcelableArrayListExtra("listaIngresosPrincipal") ?: arrayListOf()
        this.iniciarComponentes()
    }

    private fun iniciarComponentes() {
        this.etTipo = findViewById(R.id.etTipoIngreso)
        this.etPrecio = findViewById(R.id.etPrecioIngreso)
        this.etDescripcion = findViewById(R.id.etmlDescripcionIngreso)
        this.etFecha = findViewById(R.id.etFechaIngreso)
        this.btnAgregarIngreso = findViewById(R.id.btnAgregarIngreso)
        this.btnSeleccionarFecha = findViewById(R.id.btnSeleccionarFechaIngreso)

        this.btnSeleccionarFecha.setOnClickListener {
            showDatePickerDialog()
        }

        this.btnAgregarIngreso.setOnClickListener {
            this.ingresos.add(this.obtenerDatosIngreso())
            Toast.makeText(this, "Se agregó el nuevo ingreso", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PrincipalActivity::class.java)
            intent.putParcelableArrayListExtra("listaIngresosAgregar", this.ingresos)
            startActivity(intent)
        }
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                calendar.set(year, monthOfYear, dayOfMonth)
                val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                etFecha.setText(selectedDate)
            },
            year, month, day
        )
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        datePickerDialog.show()
    }

    private fun obtenerDatosIngreso(): Ingreso {
        val ingreso = Ingreso(
            this.etTipo.text?.toString(),
            this.etDescripcion.text?.toString(),
            this.etPrecio.text.toString().toDouble(),
            this.etFecha.text?.toString()
        )
        return ingreso
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
