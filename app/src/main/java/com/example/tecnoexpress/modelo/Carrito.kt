package com.example.tecnoexpress.modelo

object Carrito {
    val productos = mutableListOf<Producto>()

    fun agregarProducto(producto: Producto) {
        if (!productos.contains(producto)) {
            productos.add(producto)
        }
    }

    fun eliminarProducto(producto: Producto) {
        productos.remove(producto)
    }

    fun calcularTotal(): Double {
        return productos.sumOf { producto ->
            producto.precio.replace("S/ ", "").replace(",", "").toDouble()
        }
    }

    fun obtenerNombres(): List<String> {
        return productos.map { it.nombre }
    }
} 