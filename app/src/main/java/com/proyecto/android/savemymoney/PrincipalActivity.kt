package com.proyecto.android.savemymoney

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.proyecto.android.savemymoney.adapter.GastoAdapter
import com.proyecto.android.savemymoney.adapter.IngresoAdapter
import com.proyecto.android.savemymoney.modelo.Gasto
import com.proyecto.android.savemymoney.modelo.Ingreso
import com.proyecto.android.savemymoney.modelo.Usuario
import com.squareup.picasso.Picasso


class PrincipalActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var tvUsuario: TextView
    private lateinit var user: Usuario

    private lateinit var rvGastos: RecyclerView
    private lateinit var gastos: ArrayList<Gasto>
    private lateinit var adapterGastos: GastoAdapter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var fabAgregarGasto: FloatingActionButton

    private lateinit var rvIngresos: RecyclerView
    private lateinit var ingresos: ArrayList<Ingreso>
    private lateinit var adapterIngresos: IngresoAdapter

    private lateinit var drawerPrincipal: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var nvPrincipal: NavigationView

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        this.drawerPrincipal = findViewById(R.id.dlPrincipal)
        this.toolbar = findViewById(R.id.toolbar2)
        this.nvPrincipal = findViewById(R.id.nvPrincipal)

        setSupportActionBar(this.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        this.setNavigationDrawer()

        auth = FirebaseAuth.getInstance()

        val user = auth.currentUser
        if (user != null) {
            val hv: View = nvPrincipal.getHeaderView(0)

            val nombre_nd: TextView = hv.findViewById(R.id.nav_header_textView)
            val correo_nd: TextView = hv.findViewById(R.id.nav_header_textView2)
            val imagen_nd: ImageView = hv.findViewById(R.id.nav_header_imageView)
            val profile_url: String

            nombre_nd.text = user.displayName
            correo_nd.text = user.email
            profile_url = user.photoUrl.toString()

            Picasso.get().load(profile_url).into(imagen_nd)

        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        //user = intent.getParcelableExtra("usuario")!!

        //user = intent.getParcelableExtra("usuario") as Usuario


        if(intent.getParcelableArrayListExtra<Ingreso>("listaIngresosAgregar") != null)
            this.ingresos = intent.getParcelableArrayListExtra("listaIngresosAgregar") ?: arrayListOf()
        else
            this.llenarIngresos()

        if(intent.getParcelableArrayListExtra<Gasto>("listaGastosAgregar") != null)
            this.gastos = intent.getParcelableArrayListExtra("listaGastosAgregar") ?: arrayListOf()
        else
            this.llenarGastos()

        this.iniciarComponentes()

    }

    private fun setNavigationDrawer() {
        val toogle = ActionBarDrawerToggle(
            this,
            this.drawerPrincipal,
            this.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        toogle.isDrawerIndicatorEnabled = true
        this.drawerPrincipal.addDrawerListener(toogle)
        toogle.syncState()
        this.nvPrincipal.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_agregar_gasto -> {
                val intent = Intent(this, AgregarGastoActivity::class.java)
                intent.putParcelableArrayListExtra("listaGastosPrincipal", this.gastos)
                startActivity(intent)
            }
            R.id.nav_agregar_ingreso -> {
                val intent = Intent(this, AgregarIngresoActivity::class.java)
                intent.putParcelableArrayListExtra("listaIngresosPrincipal", this.ingresos)
                startActivity(intent)
            }
            R.id.nav_configurar -> {
                val intent = Intent(this, PerfilActivity::class.java)
                //intent.putExtra("usuario", user)
                startActivity(intent)
            }
            // ... otras opciones ...
            R.id.nav_dashboard -> {
                val intent = Intent(this, DashboardActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_dashboard2 -> {
                val intent = Intent(this, Dashboard2Activity::class.java)
                startActivity(intent)
            }
            R.id.nav_salir ->{
                finish()
                true
            }
        }
        item.isChecked = true
        drawerPrincipal.closeDrawer(GravityCompat.START)
        return true
    }

    private fun iniciarComponentes() {
        // Configuración del RecyclerView y el adaptador para Gastos
        this.rvGastos = findViewById(R.id.rvGastos)
        this.layoutManager = LinearLayoutManager(this)
        this.rvGastos.layoutManager = this.layoutManager
        this.adapterGastos = GastoAdapter(this, this.gastos)
        this.rvGastos.adapter = this.adapterGastos

        // Configuración del RecyclerView y el adaptador para Ingresos
        this.rvIngresos = findViewById(R.id.rvIngresos)
        this.rvIngresos.layoutManager = LinearLayoutManager(this)
        this.adapterIngresos = IngresoAdapter(this, this.ingresos)
        this.rvIngresos.adapter = this.adapterIngresos

        // Configuración del FAB
        this.fabAgregarGasto = findViewById(R.id.fabAgregarGasto)
        this.fabAgregarGasto.setOnClickListener {
            val intent = Intent(this, AgregarGastoActivity::class.java)
            intent.putParcelableArrayListExtra("listaGastosPrincipal", this.gastos)
            startActivity(intent)
        }
    }

    private fun llenarGastos() {
        this.gastos = ArrayList<Gasto>()
        this.gastos.add(Gasto("Comida", "Me comí una salchipapa", 23.5, "29/09/2023"))
        this.gastos.add(Gasto("Ropa", "Me compré una t-shirt", 130.5, "29/09/2023"))
        // Agrega más gastos según sea necesario
    }

    private fun llenarIngresos() {
        this.ingresos = ArrayList<Ingreso>()
        this.ingresos.add(Ingreso("Salario", "Ingreso mensual", 2000.0, "29/09/2023"))
        this.ingresos.add(Ingreso("Venta", "Venta de artículos", 500.0, "29/09/2023"))
        // Agrega más ingresos según sea necesario
    }
}