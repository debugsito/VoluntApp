package com.example.voluntapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.voluntapp.R
import com.example.voluntapp.models.ProyectoModel

class ProyectoAdapter(private var lstProyectos: List<ProyectoModel>)
    : RecyclerView.Adapter<ProyectoAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //TODO: recibir la vista personalizada para el recyclerview
        val tvTitle: TextView = itemView.findViewById(R.id.tvProyecto)
        val tvFechaInicio: TextView = itemView.findViewById(R.id.tvFechaInicio)
        val tvVacantes: TextView = itemView.findViewById(R.id.tvVacantes)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.item_proyecto,parent,false))
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemCourse = lstProyectos[position]
        holder.tvTitle.text = itemCourse.title
        holder.tvFechaInicio.text = itemCourse.fecha_inicio
        holder.tvVacantes.text = itemCourse.cantidad_inscritos
    }
    override fun getItemCount(): Int {
        return lstProyectos.size
    }
}