package com.stark.dogboard.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.stark.dogboard.R
import com.stark.dogboard.databinding.ActivityMainBinding
import com.stark.dogboard.viewmodel.LoginViewModel
import com.stark.dogboard.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initObservers()
        initComponents()


    }

    private fun initComponents() {

        val name = intent.getStringExtra("name")
        val lastName = intent.getStringExtra("lastName")
        val id = intent.getStringExtra("id")
        val gender = intent.getStringExtra("gender")
        val age = intent.getStringExtra("age")

        binding.txtNombre.setText(name)
        binding.txtApellido.setText(lastName)
        binding.txtID.setText(id)
        binding.txtGenero.setText(gender)
        binding.txtEdad.setText(age)

    }

    private fun initObservers() {
        mainViewModel.getRandomDogImage().observe(this, Observer{
            response ->
            if(response != null && response.status == "success"){

                Picasso.get()
                    .load(response.message)
                    .placeholder(R.drawable.user)
                    .error(R.drawable.user)
                    .into(binding.avatar)


            }else {
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("No se pudo cargar la foto de perfil")
                    .setPositiveButton("OK", null)
                    .show()
            }

        })
    }

    private fun initListeners() {
        binding.btnLogout.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)

        }
    }
}