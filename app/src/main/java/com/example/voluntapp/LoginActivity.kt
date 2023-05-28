package com.example.voluntapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin: Button = findViewById(R.id.btnLogin)
        val btnRegistroVoluntario: Button = findViewById(R.id.btnRegistroVoluntario)
        val btnRegistroOrganizacion: Button = findViewById(R.id.btnRegistroOrganizacion)

        btnLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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