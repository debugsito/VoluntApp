package com.example.voluntapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.voluntapp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroVoluntario : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_voluntario)

        val btnBackRV: Button = findViewById(R.id.btnBackRV)
        val btnRegisterRV: Button = findViewById(R.id.btnRegisterRv)

        val txtFullname: EditText = findViewById(R.id.etRUC)
        val txtDNI: EditText = findViewById(R.id.etRazonSocial)
        val txtEdad: EditText = findViewById(R.id.etEdad)
        val txtEmail: EditText = findViewById(R.id.etEmail)
        val txtPassword: EditText = findViewById(R.id.etPassword)
        val txtResena: EditText = findViewById(R.id.etResena)

        btnBackRV.setOnClickListener{
            val intent = Intent(this, PrincipalActivity::class.java)
            startActivity(intent)
        }
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")
        btnRegisterRV.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
            auth
                .createUserWithEmailAndPassword(txtEmail.text.toString(),txtPassword.text.toString())
                .addOnSuccessListener { obj ->
                    Snackbar.make(findViewById(android.R.id.content),"Usuario creado"+ (obj.user?.uid
                        ?: "-"),
                        Snackbar.LENGTH_LONG).show()
                    val nuevoUser = UserModel(
                        txtFullname.text.toString(),
                        txtDNI.text.toString(),
                        txtEdad.text.toString(),
                        txtEmail.text.toString(),
                        txtResena.text.toString(),
                        (obj.user?.uid ?: "-"),
                        "voluntario",
                        ""
                    )
                    collectionRef.add(nuevoUser)
                        .addOnSuccessListener { documentReference ->
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Registro exitoso ID: ${documentReference.id}"
                                    , Snackbar.LENGTH_LONG).show()
                            startActivity(Intent(this,LoginActivity::class.java))
                        }
                        .addOnFailureListener{ error ->
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Ocurrió un error: $error"
                                    , Snackbar.LENGTH_LONG).show()
                        }

                }.addOnFailureListener{error->
                    Snackbar.make(findViewById(android.R.id.content),"Error al crear usuario",
                        Snackbar.LENGTH_LONG).show()
                }
        }

    }
}