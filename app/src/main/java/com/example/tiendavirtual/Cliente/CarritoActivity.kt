package com.example.tiendavirtual.Cliente

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiendavirtual.MainActivity
import com.example.tiendavirtual.R

class CarritoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_carrito)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        fun irhomeactivity (view: View){

            val intent = Intent (this, MainActivity ::class.java)
            startActivity(intent)


        }


        val btnPago = findViewById<Button>(R.id.btnPago)

        val txtTotal = findViewById<TextView>(R.id.txtTotal)

        val carrito: ArrayList<String> = ArrayList()
        carrito.add("Laptop Asus - Cantidad: 1 - S/.3500.00")
        carrito.add("Laptop Lenovo - Cantidad: 1 - S/.3000.00")
        carrito.add("Laptop HP - Cantidad: 1 - S/.2500.00")

        val listViewCart = findViewById<ListView>(R.id.listViewCart)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            carrito
        )
        listViewCart.adapter = adaptador


        var total = 0.0


        for (item in carrito) {
            val partes = item.split("S/.")
            if (partes.size > 1) {
                val precioString = partes[1].trim()
                val precio = precioString.toDoubleOrNull() ?: 0.0
                total += precio
            }
        }

        txtTotal.text = "Total: S/. %.2f".format(total)



        btnPago.setOnClickListener {
            val intent = Intent(this, PagosActivity::class.java)
            intent.putExtra("total_pagar", total)
            startActivity(intent)
        }

    }




}

