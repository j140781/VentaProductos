package com.example.tecnoexpress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.tecnoexpress.databinding.ActivityDetalleProductoBinding
import com.example.tecnoexpress.modelo.Carrito
import com.example.tecnoexpress.modelo.Producto
import com.google.android.material.chip.Chip
import com.google.android.material.snackbar.Snackbar

class DetalleProductoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetalleProductoBinding
    private lateinit var producto: Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleProductoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        producto = intent.getParcelableExtra("producto") ?: return finish()

        setupUI()
        setupListeners()
    }

    private fun setupUI() {
        binding.apply {
            tvNombre.text = producto.nombre
            tvPrecio.text = producto.precio
            tvDescripcion.text = producto.descripcion

            Glide.with(this@DetalleProductoActivity)
                .load(producto.imagenUrl)
                .centerCrop()
                .into(ivProducto)

            producto.caracteristicas.forEach { caracteristica ->
                val chip = Chip(this@DetalleProductoActivity).apply {
                    text = caracteristica
                    isCheckable = false
                }
                chipGroup.addView(chip)
            }
        }
    }

    private fun setupListeners() {
        binding.btnComprar.setOnClickListener {
            Carrito.agregarProducto(producto)
            Snackbar.make(binding.root, "Producto agregado al carrito", Snackbar.LENGTH_SHORT).show()
        }
    }
} 