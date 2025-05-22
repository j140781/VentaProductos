package com.example.tecnoexpress

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tecnoexpress.adaptadores.CarritoAdapter
import com.example.tecnoexpress.databinding.ActivityCarritoBinding
import com.example.tecnoexpress.modelo.Carrito
import java.text.NumberFormat
import java.util.Locale

class CarritoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCarritoBinding
    private lateinit var adapter: CarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        actualizarUI()
        setupListeners()
    }

    private fun setupRecyclerView() {
        adapter = CarritoAdapter(Carrito.productos) { producto ->
            Carrito.eliminarProducto(producto)
            actualizarUI()
        }

        binding.rvCarrito.apply {
            layoutManager = LinearLayoutManager(this@CarritoActivity)
            adapter = this@CarritoActivity.adapter
        }
    }

    private fun actualizarUI() {
        val total = Carrito.calcularTotal()
        val formato = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
        binding.tvTotal.text = "Total: ${formato.format(total)}"

        val estaVacio = Carrito.productos.isEmpty()
        binding.tvVacio.visibility = if (estaVacio) View.VISIBLE else View.GONE
        binding.rvCarrito.visibility = if (estaVacio) View.GONE else View.VISIBLE
        binding.btnComprar.isEnabled = !estaVacio

        adapter.notifyDataSetChanged()
    }

    private fun setupListeners() {
        binding.btnComprar.setOnClickListener {
            val intent = Intent(this, PagoActivity::class.java).apply {
                putExtra("total", Carrito.calcularTotal())
                putExtra("productos", Carrito.obtenerNombres().toTypedArray())
            }
            startActivity(intent)
        }
    }
} 