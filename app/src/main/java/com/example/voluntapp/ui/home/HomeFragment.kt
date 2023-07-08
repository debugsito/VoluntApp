package com.example.voluntapp.ui.home

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
import com.example.voluntapp.databinding.FragmentHomeBinding
import com.example.voluntapp.models.ProyectoModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

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
                        documentSnapshot["title"].toString(),
                        documentSnapshot["description"].toString(),
                        documentSnapshot["fecha_inicio"].toString(),
                        documentSnapshot["fecha_fin"].toString(),
                        documentSnapshot["cantidad"].toString(),
                        (documentSnapshot["cantidad"].toString().toInt() - documentSnapshot["cantidad_inscritos"].toString().toInt()).toString(),
                    )
                }
                rvProyectos.adapter = ProyectoAdapter(lstProyectos)
                rvProyectos.layoutManager = LinearLayoutManager(requireContext())
            }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}