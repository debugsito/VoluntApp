package com.example.voluntapp.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.voluntapp.LoginActivity
import com.example.voluntapp.MainActivity
import com.example.voluntapp.R

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
        btnPublicarNP.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}