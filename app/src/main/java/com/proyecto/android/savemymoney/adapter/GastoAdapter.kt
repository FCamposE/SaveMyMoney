package com.proyecto.android.savemymoney.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proyecto.android.savemymoney.R
import com.proyecto.android.savemymoney.databinding.ActivityPrincipalBinding
import com.proyecto.android.savemymoney.modelo.Gasto
//import kotlinx.android.synthetic.main.item_gasto_activity.view.*


class GastoAdapter(val context:Context, val lista: ArrayList<Gasto>): RecyclerView.Adapter<GastoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gasto_activity, parent, false)
        val vh:ViewHolder = ViewHolder(view)
        return vh
    }

    override fun getItemCount(): Int {
        return this.lista.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val gasto = this.lista.get(position)
        holder.tipo!!.text = gasto.tipo
        holder.precio!!.text = gasto.precio.toString()
        holder.fecha!!.text = gasto.fecha
        holder.descripcion!!.text = gasto.descripcion
    }
    class ViewHolder(vista:View): RecyclerView.ViewHolder(vista) {
        var v = vista
        var tipo: TextView? = v.findViewById(R.id.tvTipo)
        var precio: TextView? = v.findViewById(R.id.tvMonto)
        var fecha: TextView? = v.findViewById(R.id.tvFecha)
        var descripcion: TextView? = v.findViewById(R.id.tvDescripcion)
    }

}