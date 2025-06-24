package com.soportereal.invefacon.interfaces.pantallas_principales.ajustes.estacion

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import okhttp3.MultipartBody
import org.json.JSONObject

class ProcesarDatosEstacion(apiToken: String) {

    private val objetoFuncionesHttpInvefacon = FuncionesHttp(apiToken = apiToken)

    suspend fun obtenerBodegas(): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("", "")
            .addFormDataPart("", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "inventarios/bodegas.php")
    }

}
