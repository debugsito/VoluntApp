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

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(proyecto: ProyectoModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvProyecto)
        val tvFechaInicio: TextView = itemView.findViewById(R.id.tvFechaInicio)
        val tvVacantes: TextView = itemView.findViewById(R.id.tvVacantes)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(lstProyectos[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_proyecto, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val proyecto = lstProyectos[position]
        holder.tvTitle.text = proyecto.title
        holder.tvFechaInicio.text = proyecto.fecha_inicio
        holder.tvVacantes.text = (proyecto.cantidad.toInt() - proyecto.cantidad_inscritos.toInt()).toString()
    }

    override fun getItemCount(): Int {
        return lstProyectos.size
    }
}
