package com.proyecto.android.savemymoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.proyecto.android.savemymoney.modelo.Gasto

class AgregarGastoActivity : AppCompatActivity() {

    private lateinit var etTipo: EditText
    private lateinit var etPrecio: EditText
    private lateinit var etmlDescripcion: EditText
    private lateinit var etFecha: EditText
    private lateinit var btnAgregarGasto: Button
    private lateinit var gastos: ArrayList<Gasto>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_gasto)

        this.gastos = intent.getSerializableExtra("listaGastosPrincipal") as ArrayList<Gasto>
        this.iniciarComponentes()

    }

    private fun iniciarComponentes(){
        this.etTipo = findViewById(R.id.etTipo)
        this.etPrecio = findViewById(R.id.etPrecio)
        this.etmlDescripcion = findViewById(R.id.etmlDescripcion)
        this.etFecha = findViewById(R.id.etFecha)
        this.btnAgregarGasto = findViewById(R.id.btnAgregarGasto)

        this.btnAgregarGasto.setOnClickListener {
            this.gastos.add(this.obtenerDatosGasto())
            Toast.makeText(this, "Se agrego el nuevo gasto", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, PrincipalActivity::class.java)
            intent.putParcelableArrayListExtra("listaGastosAgregar", this.gastos)
            startActivity(intent)
        }
    }

    private fun obtenerDatosGasto(): Gasto{
        val gasto = Gasto(this.etTipo.text?.toString(), this.etmlDescripcion.text?.toString(), this.etPrecio.text!!.toString().toDouble(), this.etFecha.text?.toString())
        return gasto
    }
}