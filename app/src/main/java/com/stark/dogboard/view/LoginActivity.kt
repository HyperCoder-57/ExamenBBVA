package com.stark.dogboard.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText


import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.stark.dogboard.R
import com.stark.dogboard.databinding.ActivityLoginBinding
import com.stark.dogboard.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    private val username = "admin"
    private val email = "correo@ejemplo.com"
    private val password = "admin"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initObservers()
    }

    private fun autenticarUsuario() {


        if ((binding.editTextUsuario.text.toString()
                .trim() == username || binding.editTextUsuario.text.toString()
                .trim() == email) && binding.editTextPassword.text.toString().trim() == password
        )

            loginViewModel.login(username, email, password).observe(this) { response ->
                if (response != null) {


                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }


                    intent.putExtra("name", response.name.toString())
                    intent.putExtra("lastName", response.lastName.toString())
                    intent.putExtra("id", response.id.toString())
                    intent.putExtra("gender",response.gender.toString())
                    intent.putExtra("age", response.age.toString())


                    startActivity(intent)
                } else {
                    AlertDialog.Builder(this).setTitle("Error")
                        .setMessage("No se puede iniciar sesión")
                        .setPositiveButton("OK", null).show()
                }
            }
    }

    private fun initListeners() {
        setEditTextListener(binding.editTextUsuario) { loginViewModel.textUserChange(it) }
        setEditTextListener(binding.editTextPassword) { loginViewModel.textPassChange(it) }

        binding.btnIngresar.setOnClickListener { autenticarUsuario() }
    }

    private fun setEditTextListener(editText: EditText, onTextChanged: (String) -> Unit) {
        editText.addTextChangedListener { edit -> onTextChanged(edit.toString().trim()) }
    }

    private fun initObservers() {
        loginViewModel.isBtnEnabled.observe(this) { isEnabled ->
            binding.btnIngresar.isEnabled = isEnabled

            if (isEnabled) {
                // binding.btnIngresar.setBackgroundColor("@Color")
            } else {

            }
        }
    }
}