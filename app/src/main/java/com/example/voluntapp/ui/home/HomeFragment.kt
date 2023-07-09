package com.example.voluntapp.ui.home

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
import com.example.voluntapp.PrincipalActivity
import com.example.voluntapp.R
import com.example.voluntapp.adapters.ProyectoAdapter
import com.example.voluntapp.databinding.FragmentHomeBinding
import com.example.voluntapp.models.ProyectoModel
import com.example.voluntapp.project.DetalleProyectoActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View =  inflater.inflate(R.layout.fragment_home, container, false)
        val rvProyectos: RecyclerView = view.findViewById(R.id.rvProyectos)
        val db = FirebaseFirestore.getInstance()
        var lstProyectos: List<ProyectoModel>
        val collectionRef = db.collection("projectUsers")
        val userModel = MySharedPreferences.getUserModel()
        db.collection("projects")
            .addSnapshotListener{snap,e->
                if(e!=null){
                    Snackbar
                        .make(view
                            ,"Error al obtener la colección"
                            , Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                lstProyectos = snap!!.documents.map { documentSnapshot ->
                    ProyectoModel(
                        documentSnapshot.id,
                        documentSnapshot["title"].toString(),
                        documentSnapshot["description"].toString(),
                        documentSnapshot["fecha_inicio"].toString(),
                        documentSnapshot["fecha_fin"].toString(),
                        documentSnapshot["cantidad"].toString(),
                        documentSnapshot["cantidad_inscritos"].toString(),
                    )
                }
                val adapter = ProyectoAdapter(lstProyectos)
                rvProyectos.adapter = adapter
                rvProyectos.layoutManager = LinearLayoutManager(requireContext())
                adapter.setOnItemClickListener(object : ProyectoAdapter.OnItemClickListener {
                    override fun onItemClick(proyecto: ProyectoModel) {
                        // Abrir la actividad de detalles del proyecto
                        //preguntar si ya postuló al proyecto
                        collectionRef
                            .whereEqualTo("uidProject", proyecto.id)
                            .whereEqualTo("uidUser", userModel?.uid)
                            .get()
                            .addOnSuccessListener { documents ->
                                val count = documents.size()
                                println("cantidad:"+count.toString())
                                // Utiliza la variable "count" para obtener el número de registros encontrados
                                if(count>0){
                                    Snackbar
                                        .make(view
                                            ,"Ya postulaste a este proyecto."
                                            , Snackbar.LENGTH_LONG).show()

                                }
                                val intent = Intent(requireContext(), DetalleProyectoActivity::class.java)
                                intent.putExtra("proyecto", proyecto)
                                intent.putExtra("yapostulo", count.toString())
                                startActivity(intent)
                            }
                            .addOnFailureListener { exception ->
                                Snackbar
                                    .make(view
                                        ,"Error al realizar la consulta: $exception"
                                        , Snackbar.LENGTH_LONG).show()
                            }

                    }
                })
            }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}