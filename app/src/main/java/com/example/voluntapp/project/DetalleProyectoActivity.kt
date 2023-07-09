package com.example.voluntapp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.voluntapp.PrincipalActivity
import com.example.voluntapp.R
import com.example.voluntapp.models.ProyectoModel
import com.example.voluntapp.models.ProyectoUserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class DetalleProyectoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_proyecto)

        val proyecto = intent.getSerializableExtra("proyecto") as? ProyectoModel
        val yapostulo = intent.getStringExtra("yapostulo")

        val tvTitle: TextView = findViewById(R.id.tvTitleProject)
        val tvDescription: TextView = findViewById(R.id.tvDescriptionProject)
        val tvFechaInicio: TextView = findViewById(R.id.tvFechaInicioProject)
        val tvFechaFin: TextView = findViewById(R.id.tvFechaFinProject)
        val tvVacantes: TextView = findViewById(R.id.tvVacantesProject)
        val btnPostular: Button = findViewById(R.id.btnPostularProject)
        val btnRegresar: Button = findViewById(R.id.btnRegresarProject)
        if (proyecto != null) {
            println(proyecto.toString())
            tvTitle.text = proyecto.title
            tvDescription.text = proyecto.description
            tvFechaInicio.text = proyecto.fecha_inicio
            tvFechaFin.text = proyecto.fecha_fin
            tvVacantes.text = (proyecto.cantidad.toInt() - proyecto.cantidad_inscritos.toInt()).toString()

        }
        val db = FirebaseFirestore.getInstance()
        val projectUsersRef = db.collection("projectUsers")
        btnRegresar.setOnClickListener {
            startActivity(Intent(this, PrincipalActivity::class.java))
        }
        val userModel = MySharedPreferences.getUserModel()

        if((yapostulo?.toInt() ?:0) >0){
            btnPostular.visibility = View.GONE
        }
        if((userModel?.perfil ?:"") =="organizacion"){
            btnPostular.visibility = View.GONE
        }
        btnPostular.setOnClickListener{
            val userModel = MySharedPreferences.getUserModel()

            var nuevoProyectoUser = ProyectoUserModel(
                proyecto?.id ?: "-",
                userModel?.uid ?: "-",
            )
            projectUsersRef.add(nuevoProyectoUser)
                .addOnSuccessListener { documentReference ->
                    Snackbar
                        .make(findViewById(android.R.id.content)
                            ,"Registro exitoso ID: ${documentReference.id}"
                            , Snackbar.LENGTH_LONG).show()
                    val documentRef = db.collection("projects").document(proyecto?.id ?: "-")

                    documentRef.update("cantidad_inscritos", ((proyecto?.cantidad_inscritos?.toInt()
                        ?: 0) +1).toString())
                        .addOnSuccessListener {
                            startActivity(Intent(this, PrincipalActivity::class.java))
                        }
                        .addOnFailureListener { exception ->
                            // Maneja cualquier error en la actualización
                        }

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