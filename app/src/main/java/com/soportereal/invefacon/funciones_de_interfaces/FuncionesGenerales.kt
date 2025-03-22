package com.soportereal.invefacon.funciones_de_interfaces

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import org.json.JSONObject
import java.util.Locale

fun mostrarMensajeError(mensaje: String){
    val jsonObject = JSONObject(
        """
                    {
                        "code": 400,
                        "status": "error",
                        "data": "$mensaje"
                    }
                """
    )
    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
}

fun mostrarMensajeExito(mensaje: String){
    val jsonObject = JSONObject(
        """
                    {
                        "code": 200,
                        "status": "ok",
                        "data": "$mensaje"
                    }
                """
    )
    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
}

fun guardarParametroSiNoExiste(context: Context, clave: String, valor: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

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

suspend fun obtenerDatosClienteByCedula(
    numeroCedula: String
):JSONObject?{
    val objetoFuncionesHttpInvefacon= FuncionesHttp(servidorUrl = "https://apis.gometa.org/")
    return objetoFuncionesHttpInvefacon.metodoGet(apiDirectorio = "cedulas/$numeroCedula", validarJson = false, enviarToken = false)
}

fun separacionDeMiles(
    montoDouble:Double= 0.00,
    montoInt: Int = 0,
    montoString: String = "",
    isDouble: Boolean= true,
    isString: Boolean = false
): String{
    if (isString){
        val monto = if (montoString.isEmpty()) 0.00 else montoString.toDouble()
        return String.format(Locale.US, "%,.2f", monto.toString().replace(",", "").toDouble())
    }else{

        val monto = if (isDouble) montoDouble else montoInt
        return String.format(Locale.US, "%,.2f", monto.toString().replace(",", "").toDouble())
    }
}

fun validarExitoRestpuestaServidor(respuesta : JSONObject): Boolean{
    return respuesta.getString("status")=="ok" && respuesta.getString("code")=="200"
}

fun String.isSoloNumeros(): Boolean {
    return this.matches(Regex("\\d+"))
}

fun mostrarTeclado(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

