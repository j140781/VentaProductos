package com.example.tecnoexpress.adaptadores

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tecnoexpress.databinding.ItemProductoBinding
import com.example.tecnoexpress.modelo.Producto

class ProductoAdapter(
    private val productos: List<Producto>,
    private val onProductoClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    inner class ProductoViewHolder(private val binding: ItemProductoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(producto: Producto) {
            binding.apply {
                tvNombre.text = producto.nombre
                tvPrecio.text = producto.precio
                Glide.with(ivProducto)
                    .load(producto.imagenUrl)
                    .centerCrop()
                    .into(ivProducto)
                root.setOnClickListener { onProductoClick(producto) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding = ItemProductoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(productos[position])
    }

    override fun getItemCount() = productos.size
} 