package com.example.voluntapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.voluntapp.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        MySharedPreferences.init(sharedPreferences)

        setContentView(R.layout.activity_login)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegistroVoluntario: Button = findViewById(R.id.btnRegistroVoluntario)
        val btnRegistroOrganizacion: Button = findViewById(R.id.btnRegistroOrganizacion)
        val txtEmail: EditText = findViewById(R.id.etUser)
        val txtPassword: EditText = findViewById(R.id.etPasswordLogin)
        var auth = FirebaseAuth.getInstance();
        val db = FirebaseFirestore.getInstance()
        val usersCollection = db.collection("users")

        btnLogin.setOnClickListener {
            val correo = txtEmail.text.toString();
            val clave = txtPassword.text.toString();

            auth.signInWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        val uid = auth.currentUser?.uid
                        usersCollection.whereEqualTo("uid", uid)
                            .get()
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful && task.result != null) {
                                    val snapshot = task.result
                                    for (document in snapshot.documents) {
                                        val userModel = UserModel(
                                            document.get("name") as String,
                                            document.get("dni") as String,
                                            document.get("edad") as String,
                                            document.get("email") as String,
                                            document.get("resena") as String,
                                            document.get("uid") as String,
                                            document.get("perfil") as String,
                                            (document.get("razon")?:"" as String) as String
                                        )
                                        MySharedPreferences.saveUserModel(userModel)

                                    }
                                    val intent = Intent(this, PrincipalActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    // Ocurrió un error al obtener los datos
                                }
                            }

                    }else{
                        Snackbar.make(findViewById(android.R.id.content),"Las credenciales son inválidas",
                            Snackbar.LENGTH_LONG).show()
                    }
                }
        }
        btnRegistroVoluntario.setOnClickListener {
            val intent = Intent(this, RegistroVoluntario::class.java)
            startActivity(intent)
        }
        btnRegistroOrganizacion.setOnClickListener {
            val intent = Intent(this, RegistroOrganizacion::class.java)
            startActivity(intent)
        }

    }
}