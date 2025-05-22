package com.example.tecnoexpress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.tecnoexpress.databinding.FragmentPerfilBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FragmentPerfil : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        setupUI()
        setupListeners()
        cargarDatosUsuario()
    }

    private fun setupUI() {
        binding.tvCorreo.text = auth.currentUser?.email
    }

    private fun setupListeners() {
        binding.btnCambiarContrasena.setOnClickListener {
            mostrarDialogoCambioContrasena()
        }
    }

    private fun cargarDatosUsuario() {
        binding.progressBar.visibility = View.VISIBLE
        val uid = auth.currentUser?.uid ?: return

        db.collection("usuarios")
            .document(uid)
            .get()
            .addOnSuccessListener { document ->
                binding.apply {
                    tvNombres.text = document.getString("nombres") ?: "—"
                    tvApellidos.text = document.getString("apellidos") ?: "—"
                    tvDni.text = document.getString("dni") ?: "—"
                }
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Snackbar.make(binding.root, "Error al cargar datos: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
    }

    private fun mostrarDialogoCambioContrasena() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_cambiar_contrasena, null)
        val etActual = dialogView.findViewById<TextInputEditText>(R.id.etContrasenaActual)
        val etNueva = dialogView.findViewById<TextInputEditText>(R.id.etNuevaContrasena)
        val etConfirmar = dialogView.findViewById<TextInputEditText>(R.id.etConfirmarContrasena)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Cambiar contraseña")
            .setView(dialogView)
            .setPositiveButton("Cambiar") { _, _ ->
                val actual = etActual.text.toString()
                val nueva = etNueva.text.toString()
                val confirmar = etConfirmar.text.toString()

                when {
                    actual.isEmpty() -> Toast.makeText(context, "Ingrese su contraseña actual", Toast.LENGTH_SHORT).show()
                    nueva.length < 6 -> Toast.makeText(context, "La nueva contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                    nueva != confirmar -> Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                    else -> cambiarContrasena(actual, nueva)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun cambiarContrasena(actual: String, nueva: String) {
        val user = auth.currentUser ?: return
        val credential = EmailAuthProvider.getCredential(user.email!!, actual)

        user.reauthenticate(credential)
            .addOnSuccessListener {
                user.updatePassword(nueva)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Contraseña actualizada correctamente", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, "Error al actualizar contraseña: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Contraseña actual incorrecta", Toast.LENGTH_SHORT).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 