package com.example.voluntapp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.voluntapp.LoginActivity
import com.example.voluntapp.MainActivity
import com.example.voluntapp.PrincipalActivity
import com.example.voluntapp.R
import com.example.voluntapp.models.ProyectoModel
import com.example.voluntapp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class NewProject : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        val btnBackNP: Button = findViewById(R.id.btnBackNP)
        val btnPublicarNP: Button = findViewById(R.id.btnPublicar)

        btnBackNP.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val db = FirebaseFirestore.getInstance()
        val projectsCollection = db.collection("projects")

        val txtTitle: EditText = findViewById(R.id.etTitulo)
        val txtDesc: EditText = findViewById(R.id.etDesc)
        val txtFechaInicio: EditText = findViewById(R.id.etFechaInicio)
        val txtFechaFin: EditText = findViewById(R.id.etFechaFin)
        val txtCantidad: EditText = findViewById(R.id.ettCantidad)
        btnPublicarNP.setOnClickListener{
            val nuevoProyecto = ProyectoModel(
                "",
                txtTitle.text.toString(),
                txtDesc.text.toString(),
                txtFechaInicio.text.toString(),
                txtFechaFin.text.toString(),
                txtCantidad.text.toString(),
                "0",
            )
            projectsCollection.add(nuevoProyecto)
                .addOnSuccessListener { documentReference ->
                    Snackbar
                        .make(findViewById(android.R.id.content)
                            ,"Registro exitoso ID: ${documentReference.id}"
                            , Snackbar.LENGTH_LONG).show()
                    startActivity(Intent(this,PrincipalActivity::class.java))
                }
                .addOnFailureListener{ error ->
                    Snackbar
                        .make(findViewById(android.R.id.content)
                            ,"Ocurrió un error: $error"
                            , Snackbar.LENGTH_LONG).show()
                }
        }
    }
}