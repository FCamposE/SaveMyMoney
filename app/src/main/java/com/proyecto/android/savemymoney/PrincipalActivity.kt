package com.proyecto.android.savemymoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.proyecto.android.savemymoney.modelo.Usuario

class PrincipalActivity : AppCompatActivity() {

    private lateinit var tvUsuario: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        val usu = intent.getParcelableExtra<Usuario>("usuario")
        this.tvUsuario = findViewById(R.id.tvUsuario)
        tvUsuario.text = "Bienvenido, " + usu?.nombre
    }
}