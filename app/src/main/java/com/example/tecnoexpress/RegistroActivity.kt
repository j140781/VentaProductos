package com.example.tecnoexpress

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tecnoexpress.databinding.ActivityRegistroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registerButton.setOnClickListener {
            val nombres = binding.nombresEditText.text.toString()
            val apellidos = binding.apellidosEditText.text.toString()
            val dni = binding.dniEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            val confirmPassword = binding.confirmPasswordEditText.text.toString()

            if (validarCampos(nombres, apellidos, dni, email, password, confirmPassword)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.let { firebaseUser ->
                                val profileUpdates = UserProfileChangeRequest.Builder()
                                    .setDisplayName("$nombres $apellidos")
                                    .build()

                                firebaseUser.updateProfile(profileUpdates)
                                    .addOnCompleteListener { profileTask ->
                                        if (profileTask.isSuccessful) {
                                            guardarDatosUsuario(firebaseUser.uid, nombres, apellidos, dni, email)
                                        }
                                    }
                            }
                        } else {
                            val errorMessage = when (task.exception?.message) {
                                "The email address is already in use by another account." ->
                                    "Este correo ya está registrado"
                                "The email address is badly formatted." ->
                                    "Formato de correo inválido"
                                else -> "Error al crear la cuenta"
                            }
                            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
            }
        }
    }

    private fun validarCampos(
        nombres: String,
        apellidos: String,
        dni: String,
        email: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        var isValid = true

        binding.nombresLayout.error = null
        binding.apellidosLayout.error = null
        binding.dniLayout.error = null
        binding.emailLayout.error = null
        binding.passwordLayout.error = null
        binding.confirmPasswordLayout.error = null

        if (nombres.isEmpty()) {
            binding.nombresLayout.error = "Ingrese sus nombres"
            isValid = false
        }

        if (apellidos.isEmpty()) {
            binding.apellidosLayout.error = "Ingrese sus apellidos"
            isValid = false
        }

        if (dni.isEmpty()) {
            binding.dniLayout.error = "Ingrese su DNI"
            isValid = false
        } else if (dni.length != 8 || !dni.all { it.isDigit() }) {
            binding.dniLayout.error = "El DNI debe tener 8 dígitos"
            isValid = false
        }

        if (email.isEmpty()) {
            binding.emailLayout.error = "Ingrese su correo"
            isValid = false
        }

        if (password.isEmpty()) {
            binding.passwordLayout.error = "Ingrese una contraseña"
            isValid = false
        } else if (password.length < 6) {
            binding.passwordLayout.error = "La contraseña debe tener al menos 6 caracteres"
            isValid = false
        }

        if (confirmPassword.isEmpty()) {
            binding.confirmPasswordLayout.error = "Confirme su contraseña"
            isValid = false
        } else if (password != confirmPassword) {
            binding.confirmPasswordLayout.error = "Las contraseñas no coinciden"
            isValid = false
        }

        return isValid
    }

    private fun guardarDatosUsuario(uid: String, nombres: String, apellidos: String, dni: String, email: String) {
        val userData = hashMapOf(
            "nombres" to nombres,
            "apellidos" to apellidos,
            "dni" to dni,
            "correo" to email
        )

        db.collection("usuarios").document(uid)
            .set(userData)
            .addOnSuccessListener {
                Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Error al guardar datos", Toast.LENGTH_SHORT).show()
            }
    }
} 