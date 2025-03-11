package com.soportereal.invefacon.interfaces.modulos.facturacion

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import okhttp3.MultipartBody
import org.json.JSONObject

class ProcesarDatosModuloFacturacion(apiToken: String) {
    private val objetoFuncionesHttpInvefacon = FuncionesHttp(apiToken = apiToken)

    suspend fun crearNuevaProforma():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0","")
            .addFormDataPart("0","")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/nuevaproforma.php")
    }

    suspend fun abrirProforma():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0","")
            .addFormDataPart("0","")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/AbrirProforma.php")
    }
}

data class Articulo(
    var codigo: String,
    var descripcion: String,
    var precioUnitario: Double,
    var descuento: Double,
    var total : Double,
    var cantidad : Int
)
