package com.example.voluntapp.ui.gallery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voluntapp.R
import com.example.voluntapp.adapters.ProyectoAdapter
import com.example.voluntapp.databinding.FragmentGalleryBinding
import com.example.voluntapp.models.ProyectoModel
import com.example.voluntapp.project.DetalleProyectoActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_home, container, false)
        val rvProyectos: RecyclerView = view.findViewById(R.id.rvProyectos)
        val db = FirebaseFirestore.getInstance()
        var lstProyectos: List<ProyectoModel>
        val userModel = MySharedPreferences.getUserModel()

        db.collection("projectUsers")
            .whereEqualTo("uidUser", userModel?.uid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Snackbar.make(view, "Error al obtener la colección", Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }

                val proyectoIds = snapshot?.documents?.mapNotNull { it.getString("uidProject") }
                if (proyectoIds.isNullOrEmpty()) {
                    // No se encontraron proyectos para el uidUser
                    return@addSnapshotListener
                }

                db.collection("projects")
                    .whereIn(FieldPath.documentId(), proyectoIds)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        val lstProyectos: MutableList<ProyectoModel> = mutableListOf()

                        for (document in querySnapshot) {
                            val proyecto = ProyectoModel(
                                document.id,
                                document["title"].toString(),
                                document["description"].toString(),
                                document["fecha_inicio"].toString(),
                                document["fecha_fin"].toString(),
                                document["cantidad"].toString(),
                                document["cantidad_inscritos"].toString()
                            )
                            lstProyectos.add(proyecto)
                        }

                        val adapter = ProyectoAdapter(lstProyectos)
                        rvProyectos.adapter = adapter
                        rvProyectos.layoutManager = LinearLayoutManager(requireContext())
                        adapter.setOnItemClickListener(object : ProyectoAdapter.OnItemClickListener {
                            override fun onItemClick(proyecto: ProyectoModel) {
                                // Abrir la actividad de detalles del proyecto
                                val intent = Intent(requireContext(), DetalleProyectoActivity::class.java)
                                intent.putExtra("proyecto", proyecto)
                                intent.putExtra("yapostulo", "1")
                                startActivity(intent)
                            }
                        })
                    }
                    .addOnFailureListener { exception ->
                        // Manejar errores de la consulta
                    }
            }


        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}