package com.example.tecnoexpress

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tecnoexpress.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            val errorMessage = when (task.exception?.message) {
                                "There is no user record corresponding to this identifier. The user may have been deleted." ->
                                    "No existe una cuenta con este correo"
                                "The password is invalid or the user does not have a password." ->
                                    "Contraseña incorrecta"
                                "The email address is badly formatted." ->
                                    "Formato de correo inválido"
                                else -> "Error al iniciar sesión"
                            }
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerTextView.setOnClickListener {
            startActivity(Intent(this, RegistroActivity::class.java))
        }

        binding.forgotPasswordTextView.setOnClickListener {
            startActivity(Intent(this, ResetPasswordActivity::class.java))
        }
    }
} 