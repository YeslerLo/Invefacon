package com.soportereal.invefacon.interfaces.pantallas_principales.ajustes.impresora

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Print
import androidx.compose.ui.graphics.vector.ImageVector
import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas

sealed class OpcionesInterfazAjsutes(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector,
    val permisoValidar : String = ""
){
    data object Impresora : OpcionesInterfazAjsutes(
        ruta = RutasPatallas.AjustImpresora.ruta,
        titulo = "Impresora",
        icono = Icons.Default.Print
    )

    data object Eestacion : OpcionesInterfazAjsutes(
        ruta = RutasPatallas.ConfiEstacion.ruta,
        titulo = "Estación",
        icono = Icons.Default.PointOfSale,
        permisoValidar = "605"
    )

    companion object {
        val opciones = listOf(Impresora, Eestacion)
    }
}