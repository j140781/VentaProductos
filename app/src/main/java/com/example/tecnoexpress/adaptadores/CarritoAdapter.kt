package com.example.tecnoexpress.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tecnoexpress.databinding.ItemCarritoBinding
import com.example.tecnoexpress.modelo.Producto

class CarritoAdapter(
    private val productos: List<Producto>,
    private val onEliminarClick: (Producto) -> Unit
) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(private val binding: ItemCarritoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: Producto) {
            binding.apply {
                tvNombre.text = producto.nombre
                tvPrecio.text = producto.precio
                btnEliminar.setOnClickListener { onEliminarClick(producto) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val binding = ItemCarritoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CarritoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount() = productos.size
} 