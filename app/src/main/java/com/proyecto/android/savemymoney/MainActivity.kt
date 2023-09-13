package com.proyecto.android.savemymoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.proyecto.android.savemymoney.modelo.Usuario

class MainActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etPass: EditText
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnIniciarSesion: Button


    private lateinit var usu: String
    private lateinit var pass:String


    private var listado: ArrayList<Usuario> = arrayListOf<Usuario>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*if(listado == null){
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show()
        }else{
            listado = intent.getSerializableExtra("listado") as ArrayList<Usuario>
        }*/

        listado = intent.getSerializableExtra("listado") as ArrayList<Usuario>
        Toast.makeText(this, "Usuario: " + listado[0].dni + " Pass: " + listado[0].pass, Toast.LENGTH_SHORT).show()

        this.etUsuario = findViewById(R.id.etUsuario)
        this.etPass = findViewById(R.id.etContraseña)
        this.btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        this.btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this@MainActivity, CuentaActivity::class.java)
            startActivity(intent)
        }

        btnIniciarSesion.setOnClickListener{
            this.usu = etUsuario.text.toString()
            this.pass = etPass.text.toString()
            
            for (usuario: Usuario in listado){
                if (usuario.dni == this.usu && usuario.pass == this.pass){
                    Toast.makeText(this, "Datos correctos, ingresar", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@MainActivity, PrincipalActivity::class.java)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Datos incorectos", Toast.LENGTH_SHORT).show()
                }
            }
            //Toast.makeText(this, "Funciona", Toast.LENGTH_SHORT).show()
        }
    }
}