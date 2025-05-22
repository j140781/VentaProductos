package com.example.tecnoexpress

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tecnoexpress.adaptadores.ProductoAdapter
import com.example.tecnoexpress.databinding.FragmentCatalogoBinding
import com.example.tecnoexpress.modelo.Producto
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class FragmentCatalogo : Fragment() {
    private var _binding: FragmentCatalogoBinding? = null
    private val binding get() = _binding!!
    private lateinit var db: FirebaseFirestore
    private val productos = mutableListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = FirebaseFirestore.getInstance()
        setupRecyclerView()
        cargarProductos()
    }

    private fun setupRecyclerView() {
        binding.rvProductos.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = ProductoAdapter(productos) { producto ->
                val intent = Intent(context, DetalleProductoActivity::class.java).apply {
                    putExtra("producto", producto)
                }
                startActivity(intent)
            }
        }
    }

    private fun cargarProductos() {
        binding.progressBar.visibility = View.VISIBLE
        db.collection("productos")
            .get()
            .addOnSuccessListener { documents ->
                productos.clear()
                for (document in documents) {
                    val producto = document.toObject(Producto::class.java).copy(id = document.id)
                    productos.add(producto)
                }
                binding.rvProductos.adapter?.notifyDataSetChanged()
                binding.progressBar.visibility = View.GONE
            }
            .addOnFailureListener { e ->
                binding.progressBar.visibility = View.GONE
                Snackbar.make(binding.root, "Error al cargar productos: ${e.message}", Snackbar.LENGTH_LONG).show()
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 