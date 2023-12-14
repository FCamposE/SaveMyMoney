package com.proyecto.android.savemymoney

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.android.savemymoney.modelo.Usuario
import com.squareup.picasso.Picasso

class PerfilActivity : AppCompatActivity() {

    private lateinit var user:Usuario

    private lateinit var etNombreUsuario: EditText
    private lateinit var etApellidoUsuario: EditText
    private lateinit var etDniUsuario: EditText
    private lateinit var etCorreoUsuario: EditText
    private lateinit var etContraseñaUsuario: EditText

    private lateinit var auth: FirebaseAuth
    private lateinit var img: ShapeableImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        //user = intent.getParcelableExtra("usuario")!!

        initComponents()

        /*this.etNombreUsuario.hint = user.nombre
        this.etApellidoUsuario.hint = user.apellido
        this.etDniUsuario.hint = user.dni
        this.etCorreoUsuario.hint = user.correo
        this.etContraseñaUsuario.hint = user.pass*/

        //this.etNombreUsuario.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS

        /*this.etNombreUsuario.setText(user.nombre)
        this.etApellidoUsuario.setText(user.apellido)
        this.etDniUsuario.setText(user.dni)
        this.etCorreoUsuario.setText(user.correo)
        this.etContraseñaUsuario.setText(user.pass)*/

        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null) {
            val str = user.displayName
            val parts = str?.split(" ")

            this.etNombreUsuario.setText(parts!![0])
            this.etApellidoUsuario.setText(parts!![1])
            this.etDniUsuario.setText("71400830")
            this.etCorreoUsuario.setText(user.email)
            this.etContraseñaUsuario.setText("123456")

            val profile_photo_url = user.photoUrl.toString()

            Picasso.get().load(profile_photo_url).into(img)

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

    }
    private fun initComponents(){
        this.etNombreUsuario = findViewById(R.id.etNombreUsuario)
        this.etApellidoUsuario = findViewById(R.id.etApellidoUsuario)
        this.etDniUsuario = findViewById(R.id.etDniUsuario)
        this.etCorreoUsuario = findViewById(R.id.etCorreoUsuario)
        this.etContraseñaUsuario = findViewById(R.id.etContraseñaUsuario)
        this.img = findViewById(R.id.ivFotoUsuario)
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