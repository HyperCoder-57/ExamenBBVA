package com.stark.dogboard.view

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.stark.dogboard.databinding.ActivityLoginBinding
import com.stark.dogboard.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("app_prefs", MODE_PRIVATE)
        editor = sharedPreferences.edit()

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        initObservers()
    }

    private fun autenticarUsuario() {
        val inputUser = binding.editTextUsuario.text.toString().trim()
        val inputPass = binding.editTextPassword.text.toString().trim()

        if ((inputUser == "admin" || inputUser == "correo@ejemplo.com") && inputPass == "admin") {
            loginViewModel.login(inputUser, inputUser, inputPass).observe(this) { response ->
                if (response != null) {
                    editor.putString("auth_token", "dummy_token")
                    editor.apply()

                    val intent = Intent(this, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("name", response.name.toString())
                        putExtra("lastName", response.lastName.toString())
                        putExtra("id", response.id.toString())
                        putExtra("gender", response.gender.toString())
                        putExtra("age", response.age.toString()+" años")
                    }

                    startActivity(intent)
                    finish()
                } else {
                    showErrorDialog("No se puede iniciar sesión. Intente nuevamente.")
                }
            }
        } else {
            Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListeners() {
        binding.editTextUsuario.addTextChangedListener {
            loginViewModel.textUserChange(it.toString().trim())
        }

        binding.editTextPassword.addTextChangedListener {
            loginViewModel.textPassChange(it.toString().trim())
        }

        binding.btnIngresar.setOnClickListener {
            autenticarUsuario()
        }
    }

    private fun initObservers() {
        loginViewModel.isBtnEnabled.observe(this) { isEnabled ->
            binding.btnIngresar.isEnabled = isEnabled
            binding.btnIngresar.alpha = if (isEnabled) 1.0f else 0.5f
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
