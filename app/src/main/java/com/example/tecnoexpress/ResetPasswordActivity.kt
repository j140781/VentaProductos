package com.example.tecnoexpress

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tecnoexpress.databinding.ActivityResetPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResetPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        binding.sendButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()

            if (email.isNotEmpty()) {
                auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Te hemos enviado un correo para restablecer tu contraseña",
                                Toast.LENGTH_LONG
                            ).show()
                            finish()
                        } else {
                            val errorMessage = when (task.exception?.message) {
                                "There is no user record corresponding to this identifier. The user may have been deleted." ->
                                    "No existe una cuenta con este correo"
                                "The email address is badly formatted." ->
                                    "Formato de correo inválido"
                                else -> "Error al enviar el correo de recuperación"
                            }
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Por favor ingrese su correo electrónico", Toast.LENGTH_SHORT).show()
            }
        }
    }
} 