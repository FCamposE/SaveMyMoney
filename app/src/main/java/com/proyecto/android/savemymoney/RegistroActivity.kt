package com.proyecto.android.savemymoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.android.savemymoney.modelo.Usuario

class RegistroActivity : AppCompatActivity() {

    private lateinit var etDNI: EditText
    private lateinit var etNombres: EditText
    private lateinit var etApellidos: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPass: EditText
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnRegresar: Button

    private lateinit var dni:String
    private lateinit var nombre:String
    private lateinit var apellido:String
    private lateinit var correo:String
    private lateinit var pass:String
    private var listaUsuarios: ArrayList<Usuario> = arrayListOf()

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //INICIAR COMPONENTES
        this.etDNI = findViewById(R.id.etDNI)
        this.etNombres = findViewById(R.id.etNombre)
        this.etApellidos = findViewById(R.id.etApellidos)
        this.etCorreo = findViewById(R.id.etCorreo)
        this.etPass = findViewById(R.id.etContraseña)
        this.btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        //this.btnRegresar = findViewById(R.id.btnRegresar)

        //OBTENER DATOS, GUARDAR USUARIO E IR A MAIN
        /*btnCrearCuenta.setOnClickListener {
            this.dni = etDNI.text.toString()
            this.nombre = etNombres.text.toString()
            this.apellido = etApellidos.text.toString()
            this.correo = etCorreo.text.toString()
            this.pass = etPass.text.toString()

            val usu = Usuario(this.nombre, this.apellido, this.dni, this.correo, this.pass)
            this.listaUsuarios.add(usu)

            val  intent = Intent(this@RegistroActivity, LoginActivity::class.java)
            intent.putParcelableArrayListExtra("listado", this.listaUsuarios)
            Toast.makeText(this, "Bienvenido, " + usu.nombre, Toast.LENGTH_LONG).show()
            startActivity(intent)
        }*/

        auth = FirebaseAuth.getInstance()
        btnCrearCuenta.setOnClickListener {
            val email = etCorreo.text.toString()
            val password = etPass.text.toString()
            registerUser(email,password)
        }

        /*btnRegresar.setOnClickListener {
            val  intent = Intent(this@RegistroActivity, LoginActivity::class.java)
            startActivity(intent)
        }*/
    }
    private fun registerUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingrese su correo y contraseña", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(this, "Registro completo", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(this, "El registro falló, intente nuevamente.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }
}