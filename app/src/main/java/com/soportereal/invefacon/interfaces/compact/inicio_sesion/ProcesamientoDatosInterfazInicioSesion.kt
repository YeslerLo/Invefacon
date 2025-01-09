package com.soportereal.invefacon.interfaces.compact.inicio_sesion

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import okhttp3.MultipartBody
import org.json.JSONObject

class ProcesamientoDatosInterfazInicioSesion {

    private val objetoFuncionesHttpInvefacon= FuncionesHttp(servidorUrl = "https://invefacon.com", apiToken = "") //No se pasa ningun parametro para apiToken ya que este no es necesario

    suspend fun obtenerNombresEmpresasPorCorreo(clienteCorreoEmpresa: String): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", clienteCorreoEmpresa)
            .build()
        // Llama a la función `metodoPost` y devuelve el resultado
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "api/seguridad/empresas.php")
    }

    suspend fun validarInicioSesion(correoEmpresa: String, nombreEmpresa: String, passwordEmpresa: String): JSONObject?{
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("email", correoEmpresa)
            .addFormDataPart("clave",passwordEmpresa)
            .addFormDataPart("empresa", nombreEmpresa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "api/seguridad/authtoken.php")

    }
}