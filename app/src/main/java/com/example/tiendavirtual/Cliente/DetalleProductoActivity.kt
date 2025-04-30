package com.example.tiendavirtual.Cliente

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendavirtual.R

class DetalleProductoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detalle_producto)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val nombre = findViewById<TextView>(R.id.txtNombre)
        val descripcion = findViewById<TextView>(R.id.txtDescripcion)
        val precio = findViewById<TextView>(R.id.txtPrecio)
        val imagen = findViewById<ImageView>(R.id.imgProducto)
        val btnAgregar = findViewById<Button>(R.id.btnAgregarCarrito)





        nombre.text = "Laptop HP Pavilion 15"
        descripcion.text = "Procesador Intel Core i7, 16GB RAM, 512GB SSD, pantalla Full HD"
        precio.text = "Precio: S/ 3500"
        imagen.setImageResource(R.drawable.images)

        btnAgregar.setOnClickListener {



            Toast.makeText(this, "Producto agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    fun irhomeactivity (view: View){

        val intent = Intent (this, HomeActivity ::class.java)
        startActivity(intent)

    }


}
