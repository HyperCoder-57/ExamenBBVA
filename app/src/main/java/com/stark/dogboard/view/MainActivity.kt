package com.stark.dogboard.view

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.squareup.picasso.Picasso
import com.stark.dogboard.R
import com.stark.dogboard.databinding.ActivityMainBinding
import com.stark.dogboard.viewmodel.MainViewModel
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var name: String
    private lateinit var lastName: String
    private lateinit var id: String
    private lateinit var gender: String
    private lateinit var age: String
    private lateinit var url: String

    private var isLogOut = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initComponents()
        initListeners()
        initObservers()
    }

    private fun initComponents() {
        loadUserData()

        binding.txtNombre.setText(name)
        binding.txtApellido.setText(lastName)
        binding.txtID.setText(id)
        binding.txtGenero.setText(gender)
        binding.txtEdad.setText(age)
    }

    private fun loadUserData() {
        name = intent.getStringExtra("name") ?: sharedPreferences.getString("name", "") ?: ""
        lastName = intent.getStringExtra("lastName") ?: sharedPreferences.getString("lastName", "") ?: ""
        id = intent.getStringExtra("id") ?: sharedPreferences.getString("id", "") ?: ""
        gender = intent.getStringExtra("gender") ?: sharedPreferences.getString("gender", "") ?: ""
        age = intent.getStringExtra("age") ?: sharedPreferences.getString("age", "") ?: ""
        url = intent.getStringExtra("url") ?: sharedPreferences.getString("url", "") ?: ""
    }

    private fun initObservers() {
        if (url.isNotEmpty()) {
            Picasso.get().load(url).placeholder(R.drawable.user).error(R.drawable.user)
                .into(binding.avatar)
        } else {
            mainViewModel.getRandomDogImage().observe(this, Observer { response ->
                if (response != null && response.status == "success") {
                    url = response.message
                    Picasso.get().load(url).placeholder(R.drawable.user).error(R.drawable.user)
                        .into(binding.avatar)
                } else {
                    AlertDialog.Builder(this).setTitle("Error")
                        .setMessage("No se pudo cargar la foto de perfil")
                        .setPositiveButton("OK", null).show()
                }
            })
        }
    }

    private fun initListeners() {
        binding.btnLogout.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            isLogOut = true

            val intent = Intent(this, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()

        if (!isLogOut) {
            val editor = sharedPreferences.edit()
            editor.putString("name", name)
            editor.putString("lastName", lastName)
            editor.putString("id", id)
            editor.putString("gender", gender)
            editor.putString("age", age)
            editor.putString("url", url)
            editor.apply()
        }
    }
}