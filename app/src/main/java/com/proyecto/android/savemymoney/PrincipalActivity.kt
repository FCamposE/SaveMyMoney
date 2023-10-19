package com.proyecto.android.savemymoney

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.proyecto.android.savemymoney.adapter.GastoAdapter
import com.proyecto.android.savemymoney.modelo.Gasto
import com.proyecto.android.savemymoney.modelo.Usuario


class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var tvUsuario: TextView

    private lateinit var rvGasto: RecyclerView
    private lateinit var gastos: ArrayList<Gasto>
    private lateinit var adapterGasto: GastoAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var fabAgregarGasto: FloatingActionButton

    private lateinit var drawerPrincipal: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvPrincipal: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        this.drawerPrincipal = findViewById(R.id.dlPrincipal)
        this.toolbar = findViewById(R.id.toolbar2)
        this.nvPrincipal = findViewById(R.id.nvPrincipal)

        setSupportActionBar(this.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.setNavigationDrawer()

        if(intent.getSerializableExtra("listaGastosAgregar") != null)
            this.gastos = intent.getSerializableExtra("listaGastosAgregar") as ArrayList<Gasto>
        else
            this.llenarGastos()

        this.iniciarComponentes()
        /*val usu = intent.getParcelableExtra<Usuario>("usuario")
        this.tvUsuario = findViewById(R.id.tvUsuario)
        tvUsuario.text = "Bienvenido, " + usu?.nombre*/

    }
    private fun setNavigationDrawer(){
        val toogle = ActionBarDrawerToggle(this,
            this.drawerPrincipal,
            this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        toogle.isDrawerIndicatorEnabled = true
        this.drawerPrincipal.addDrawerListener(toogle)
        toogle.syncState()
        this.nvPrincipal.setNavigationItemSelectedListener(this)
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_agregar_gasto -> {
                val intent = Intent(this, AgregarGastoActivity::class.java)
                intent.putParcelableArrayListExtra("listaGastosPrincipal", this.gastos)
                startActivity(intent)
            }
            R.id.nav_agregar_ingreso -> {val intent = Intent(this, AgregarIngresoActivity::class.java)
            startActivity(intent)}
            R.id.nav_dashboard -> {val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)}
            R.id.nav_salir -> finish()
        }
        item.isChecked = true
        drawerPrincipal.closeDrawer(GravityCompat.START)
        return true
    }

    private fun iniciarComponentes(){
        this.rvGasto = findViewById(R.id.rvGastos)
        this.layoutManager = LinearLayoutManager(this)
        this.rvGasto.setHasFixedSize(true)
        this.rvGasto.layoutManager = this.layoutManager

        this.adapterGasto = GastoAdapter(this,this.gastos)
        this.rvGasto.adapter = this.adapterGasto

        this.fabAgregarGasto = findViewById(R.id.fabAgregarGasto)
        this.fabAgregarGasto.setOnClickListener {
            val intent = Intent(this,  AgregarGastoActivity::class.java)
            intent.putParcelableArrayListExtra("listaGastosPrincipal", this.gastos)
            startActivity(intent)
        }
    }

    private fun llenarGastos(){
        this.gastos = ArrayList<Gasto>()
        this.gastos.add(Gasto("Comida","Me comi una salchipapa", 23.5, "29/09/2023" ))
        this.gastos.add(Gasto("Ropa","Me compre una tabas", 130.5, "29/09/2023" ))
        this.gastos.add(Gasto("Comida","Me comi un chaufa con taper", 13.00, "29/09/2023"))
        this.gastos.add(Gasto("Comida","Me comi un broster parte ala", 9.00, "29/09/2023"))
    }
}

