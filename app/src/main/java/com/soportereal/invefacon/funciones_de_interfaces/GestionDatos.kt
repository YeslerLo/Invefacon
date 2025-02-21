package com.soportereal.invefacon.funciones_de_interfaces

import android.content.Context
import android.content.SharedPreferences

fun guardarParametroSiNoExiste(context: Context, clave: String, valor: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

    // Solo guarda si no existe
    if (!sharedPreferences.contains(clave)) {
        sharedPreferences.edit().putString(clave, valor).apply()
    }
}

fun obtenerParametro(context: Context, clave: String, valorPorDefecto: String = "Desconocido"): String {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
    return sharedPreferences.getString(clave, valorPorDefecto) ?: valorPorDefecto
}

fun actualizarParametro(context: Context, clave: String, nuevoValor: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(clave, nuevoValor).apply() // Sobrescribe el valor
}



