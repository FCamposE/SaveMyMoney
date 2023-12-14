package com.proyecto.android.savemymoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.proyecto.android.savemymoney.modelo.Usuario

class LoginActivity : AppCompatActivity() {

    private lateinit var etUsuario: EditText
    private lateinit var etPass: EditText
    private lateinit var btnCrearCuenta: Button
    private lateinit var btnIniciarSesion: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var btngoogle: Button

    private lateinit var usu: String
    private lateinit var pass:String

    private lateinit var userData: Usuario

    private var listado: ArrayList<Usuario> = arrayListOf<Usuario>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        this.etUsuario = findViewById(R.id.etUsuario)
        this.etPass = findViewById(R.id.etContraseña)
        this.btnCrearCuenta = findViewById(R.id.btnCrearCuenta)
        this.btnIniciarSesion = findViewById(R.id.btnIniciarSesion)

        btngoogle = findViewById(R.id.btngoogle)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btngoogle.setOnClickListener {
            signIn()
        }
        btnIniciarSesion.setOnClickListener {
            val email = etUsuario.text.toString()
            val password = etPass.text.toString()
            loginUser(email, password)
        }



        /*if(listado == null){
            Toast.makeText(this, "Lista vacia", Toast.LENGTH_SHORT).show()
        }else{
            listado = intent.getSerializableExtra("listado") as ArrayList<Usuario>
        }*/

        /*listado = intent.getSerializableExtra("listado") as ArrayList<Usuario>
        userData = Usuario(listado[0].nombre, listado[0].apellido, listado[0].dni, listado[0].correo, listado[0].pass)
        Toast.makeText(this, "Usuario: " + listado[0].dni + " Pass: " + listado[0].pass, Toast.LENGTH_SHORT).show()*/



        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistroActivity::class.java)
            startActivity(intent)
        }

        /*btnIniciarSesion.setOnClickListener{

            //val listaUsuarios: ArrayList<Usuario> = arrayListOf()
            this.usu = etUsuario.text.toString()
            this.pass = etPass.text.toString()
            
            for (usuario: Usuario in listado){
                if (usuario.dni == this.usu && usuario.pass == this.pass){
                    Toast.makeText(this, "Datos correctos, ingresar", Toast.LENGTH_SHORT).show()
                    //listaUsuarios.add(usuario)
                    val intent = Intent(this@LoginActivity, PrincipalActivity::class.java)
                    //intent.putParcelableArrayListExtra("listado", listaUsuarios)
                    intent.putExtra("usuario", userData)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Datos incorectos", Toast.LENGTH_SHORT).show()
                }
            }
            //Toast.makeText(this, "Funciona", Toast.LENGTH_SHORT).show()
        }*/
    }
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    startActivity(Intent(this, PrincipalActivity::class.java))
                    finish()
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }
    private fun loginUser(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Ingrese sus datos para ingresar", Toast.LENGTH_SHORT).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithEmail:success")
                    Toast.makeText(this, "Ingreso exitoso, bienvenido!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, PrincipalActivity::class.java))
                    finish()
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(this, "Datos incorrectos, ingrese nuevamente.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }
}