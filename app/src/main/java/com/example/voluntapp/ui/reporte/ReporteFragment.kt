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
import com.example.voluntapp.adapters.TopUserAdapter
import com.example.voluntapp.databinding.FragmentHomeBinding
import com.example.voluntapp.models.ProyectoModel
import com.example.voluntapp.models.UserModel
import com.example.voluntapp.project.DetalleProyectoActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class ReporteFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view: View =  inflater.inflate(R.layout.fragment_reporte, container, false)
        val rvTopUsers: RecyclerView = view.findViewById(R.id.rvTopUsers)
        val userModel = MySharedPreferences.getUserModel()
        //cantidad de proyectos a la que un usuario está asignado
        val db = FirebaseFirestore.getInstance()
        val projectUsersCollection = db.collection("projectUsers")

        projectUsersCollection
            .get()
            .addOnSuccessListener { querySnapshot ->
                val projectUserMap = mutableMapOf<String, Int>()

                for (document in querySnapshot) {
                    val uidUser = document.getString("uidUser") ?: ""
                    projectUserMap[uidUser] = (projectUserMap[uidUser] ?: 0) + 1
                }

                val usersCollection = db.collection("users")
                usersCollection.get().addOnSuccessListener { usersSnapshot ->
                    val userList = mutableListOf<UserModel>()

                    for (userDocument in usersSnapshot) {
                        val userId = userDocument.getString("uid")
                        val name = userDocument.getString("name") ?: ""
                        val projectCount = projectUserMap[userId] ?: 0

                        val user = UserModel(name,
                            userDocument.getString("dni").toString(),
                            userDocument.getString("edad").toString(),
                            userDocument.getString("email").toString(),
                            projectCount.toString(),
                            userDocument.getString("uid").toString(),
                            userDocument.getString("perfil").toString(),
                            userDocument.getString("razon").toString(),
                        )
                        userList.add(user)
                    }
                    val sortedUserList = userList.sortedByDescending { it.resena }

                    println("ListadoTop"+userList.toString())
                    val adapter = TopUserAdapter(sortedUserList)
                    rvTopUsers.adapter = adapter
                    rvTopUsers.layoutManager = LinearLayoutManager(requireContext())

                }
            }
            .addOnFailureListener { exception ->

            }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}