package com.example. voluntapp

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.voluntapp.databinding.ActivityPrincipalBinding
import com.example.voluntapp.project.NewProject
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PrincipalActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPrincipalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val userModel = MySharedPreferences.getUserModel()
        if((userModel?.perfil ?:"") =="organizacion"){
            hideItem()
        }
        if((userModel?.perfil ?:"") =="voluntario"){
            val favicon: FloatingActionButton = findViewById(R.id.fab)
            favicon.visibility = View.GONE
        }
        setSupportActionBar(binding.appBarPrincipal.toolbar)

        binding.appBarPrincipal.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val intent = Intent(this, NewProject::class.java)
            startActivity(intent)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_gallery,
                R.id.nav_slideshow,
                R.id.nav_reporte
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
    private fun hideItem() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val navMenu: Menu = navigationView.menu
        navMenu.findItem(R.id.nav_gallery)?.isVisible = false
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.principal, menu)
        super.onCreateOptionsMenu(menu)
        val userModel = MySharedPreferences.getUserModel()
        val tvUser: TextView = findViewById(R.id.tvNameUser)
        val tvEmail: TextView = findViewById(R.id.tvEmailUser)
        if (userModel != null) {
            tvUser.text = userModel.name
            tvEmail.text = userModel.email
        }
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.cerrar_sesion -> {
                // Aquí puedes poner la lógica para cerrar sesión
                // Por ejemplo, puedes llamar a un método que realice el cierre de sesión
                MySharedPreferences.clearSharedPreferences()
                startActivity(Intent(this,LoginActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_principal)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}