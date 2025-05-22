package com.example.tecnoexpress.modelo

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Producto(
    val id: String = "",
    val nombre: String = "",
    val precio: String = "",
    val descripcion: String = "",
    val imagenUrl: String = "",
    val caracteristicas: List<String> = emptyList()
) : Parcelable 