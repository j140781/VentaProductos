package com.example.tecnoexpress

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tecnoexpress.databinding.ActivityPagoBinding
import java.text.NumberFormat
import java.util.Locale

class PagoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPagoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPagoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val total = intent.getDoubleExtra("total", 0.0)
        val productos = intent.getStringArrayExtra("productos") ?: return finish()

        val formato = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
        binding.tvPrecio.text = formato.format(total)

        binding.btnPagar.setOnClickListener {
            val url = "https://mpago.la/2e5Ftxe"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        }
    }
} 