
package com.proyecto.android.savemymoney.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.android.savemymoney.R
import com.proyecto.android.savemymoney.modelo.Ingreso

class IngresoAdapter(val context: Context, val lista: ArrayList<Ingreso>) :
    RecyclerView.Adapter<IngresoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_ingreso_activity, parent, false)
        val vh: ViewHolder = ViewHolder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingreso = this.lista[position]
        holder.tipo!!.text = ingreso.tipo
        holder.precio!!.text = ingreso.precio.toString()
        holder.fecha!!.text = ingreso.fecha
        //holder.descripcion!!.text = ingreso.descripcion
        holder.v.setOnClickListener{
            Toast.makeText(context, ingreso.descripcion, Toast.LENGTH_SHORT).show()
        }


    }

    class ViewHolder(vista: View) : RecyclerView.ViewHolder(vista) {
        var v = vista
        var tipo: TextView? = v.findViewById(R.id.tvTipo)
        var precio: TextView? = v.findViewById(R.id.tvMonto)
        var fecha: TextView? = v.findViewById(R.id.tvFecha)
        //var descripcion: TextView? = v.findViewById(R.id.tvDescripcion)
    }
}
