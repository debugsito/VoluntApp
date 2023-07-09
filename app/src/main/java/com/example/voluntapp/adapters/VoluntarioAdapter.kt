package com.example.voluntapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.voluntapp.R
import com.example.voluntapp.models.UserModel

class VoluntarioAdapter(private var lstUsers: List<UserModel>)
    : RecyclerView.Adapter<VoluntarioAdapter.ViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(user: UserModel)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tvNameP)
        val tvDni: TextView = itemView.findViewById(R.id.tvDniP)
        val tvEmail: TextView = itemView.findViewById(R.id.tvEmailP)
        val tvEdad: TextView = itemView.findViewById(R.id.tvEdadP)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(lstUsers[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_voluntario, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = lstUsers[position]
        holder.tvTitle.text = user.name
        holder.tvDni.text = user.dni
        holder.tvEmail.text = user.email
        holder.tvEdad.text = user.edad
    }

    override fun getItemCount(): Int {
        return lstUsers.size
    }
}
