package com.example.voluntapp.ui.slideshow

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voluntapp.R
import com.example.voluntapp.adapters.VoluntarioAdapter
import com.example.voluntapp.databinding.FragmentSlideshowBinding
import com.example.voluntapp.models.UserModel

import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class SlideshowFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View =  inflater.inflate(R.layout.fragment_slideshow, container, false)
        val rvUsers: RecyclerView = view.findViewById(R.id.rvUsers)
        val db = FirebaseFirestore.getInstance()
        var lstUsers: List<UserModel>
        db.collection("users")
            .addSnapshotListener{snap,e->
                if(e!=null){
                    Snackbar
                        .make(view
                            ,"Error al obtener la colección"
                            , Snackbar.LENGTH_LONG).show()
                    return@addSnapshotListener
                }
                lstUsers = snap!!.documents.map { documentSnapshot ->
                    UserModel(
                        documentSnapshot["name"].toString(),
                        documentSnapshot["dni"].toString(),
                        documentSnapshot["edad"].toString(),
                        documentSnapshot["email"].toString(),
                        documentSnapshot["resena"].toString(),
                        documentSnapshot["uid"].toString(),
                        documentSnapshot["perfil"].toString(),
                        documentSnapshot["razon"].toString(),
                    )
                }
                val adapter = VoluntarioAdapter(lstUsers)
                rvUsers.adapter = adapter
                rvUsers.layoutManager = LinearLayoutManager(requireContext())

            }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}