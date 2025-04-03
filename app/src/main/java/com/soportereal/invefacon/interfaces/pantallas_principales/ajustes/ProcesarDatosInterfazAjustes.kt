package com.soportereal.invefacon.interfaces.pantallas_principales.ajustes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Print
import androidx.compose.ui.graphics.vector.ImageVector
import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas

sealed class OpcionesInterfazAjsutes(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
){
    data object Impresora : OpcionesInterfazAjsutes(
        ruta = RutasPatallas.AjustImpresora.ruta,
        titulo = "Impresora",
        icono = Icons.Default.Print
    )

    companion object {
        val opciones = listOf(Impresora)
    }
}