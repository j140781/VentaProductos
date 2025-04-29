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

class PagosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pagos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtMontoPago = findViewById<TextView>(R.id.txtMontoPago)
        val rgMetodoPago = findViewById<RadioGroup>(R.id.rgMetodoPago)

        val layoutTarjeta = findViewById<LinearLayout>(R.id.layoutTarjeta)
        val edtNumeroTarjeta = findViewById<EditText>(R.id.edtNumeroTarjeta)
        val edtFechaVencimiento = findViewById<EditText>(R.id.edtFechaVencimiento)
        val edtCVV = findViewById<EditText>(R.id.edtCVV)

        val layoutVirtual = findViewById<LinearLayout>(R.id.layoutVirtual)
        val edtCelular = findViewById<EditText>(R.id.edtCelular)
        val edtClaveVirtual = findViewById<EditText>(R.id.edtClaveVirtual)

        val btnPagar = findViewById<Button>(R.id.btnPagar)

        val totalRecibido = intent.getDoubleExtra("total_pagar", 0.0)
        txtMontoPago.text = "Monto a pagar: S/. %.2f".format(totalRecibido)

        rgMetodoPago.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbTarjeta -> {
                    layoutTarjeta.visibility = View.VISIBLE
                    layoutVirtual.visibility = View.GONE
                }
                R.id.rbVirtual -> {
                    layoutTarjeta.visibility = View.GONE
                    layoutVirtual.visibility = View.VISIBLE
                }
            }
        }

        btnPagar.setOnClickListener {
            val idSeleccionado = rgMetodoPago.checkedRadioButtonId

            if (idSeleccionado == -1) {
                Toast.makeText(this, "Debe seleccionar un método de pago", Toast.LENGTH_SHORT).show()
            } else if (idSeleccionado == R.id.rbTarjeta) {
                val numero = edtNumeroTarjeta.text.toString()
                val fecha = edtFechaVencimiento.text.toString()
                val cvv = edtCVV.text.toString()

                if (numero.isEmpty() || fecha.isEmpty() || cvv.isEmpty()) {
                    Toast.makeText(this, "Complete los datos de la tarjeta", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Pago realizado con Tarjeta", Toast.LENGTH_LONG).show()

                    val intent = Intent (this, MainActivity ::class.java)
                    startActivity(intent)


                }
            } else if (idSeleccionado == R.id.rbVirtual) {
                val celular = edtCelular.text.toString()
                val clave = edtClaveVirtual.text.toString()

                if (celular.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(this, "Complete los datos de pago virtual", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Pago realizado con Yape/Plin", Toast.LENGTH_LONG).show()

                    val intent = Intent (this, MainActivity ::class.java)
                    startActivity(intent)

                }
            }





        }



    }
}