package com.soportereal.invefacon.interfaces.modulos.facturacion

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import okhttp3.MultipartBody
import org.json.JSONObject

class ProcesarDatosModuloFacturacion(
    apiToken: String
) {
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

    suspend fun agregarActualizarLinea():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0","")
            .addFormDataPart("0","")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/agregaactualizalineaproforma.php ")
    }

    suspend fun obtenerArticulos(codigo: String, descripcion: String, tipoPrecio: String, moneda: String, codigoBarra : String = ""):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("tipoPrecio",tipoPrecio)
            .addFormDataPart("descripcion",descripcion)
            .addFormDataPart("moneda",moneda)
            .addFormDataPart("codigo",codigo)
            .addFormDataPart("codigobarra",codigoBarra)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/articulosfacturar.php")
    }
}

data class ArticuloFacturado(
    var articuloCodigo: String = "00",
    var pv: String = "1",
    var descripcion: String = "COCA COLA 3L",
    var articuloCantidad: Int = 1,
    var precioUd: Double = 1300.0,
    var articuloDescuentoPorcentaje: Double = 0.0,
    var articuloDescuentoMonto: Double = 0.0,
    var articuloVentaSubTotal2: Double = 0.0,
    var articuloVentaGravado: Double = 0.0,
    var articuloIvaExonerado: Double = 0.0,
    var articuloVentaExento: Double = 0.0,
    var articuloIvaPorcentaje: Double = 0.0,
    var articuloIvaMonto: Double = 0.0,
    var articuloBodegaCodigo: String = "",
    var articuloVentaTotal: Double = 0.0,
    var existencia: Double = 0.0,
    var articuloLineaId: Int = 0,
    var articuloCosto: Double = 0.0,
    var utilidad: Double = 0.0

)

data class ArticuloFaturacion(
    var codigo: String = "",
    var codBarra: String = "",
    var descripcion: String = "",
    var stock: Double = 0.0,
    var costo: Double = 0.0,
    var descuentoFijo: Double = 0.0,
    var precio1: Double = 0.0,
    var precio2: Double = 0.0,
    var precio3: Double = 0.0,
    var precio4: Double = 0.0,
    var precio5: Double = 0.0,
    var precio6: Double = 0.0,
    var precio7: Double = 0.0,
    var precio8: Double = 0.0,
    var precio9: Double = 0.0,
    var precio10: Double = 0.0,
    var codTarifaImpuesto: String = "",
    var impuesto: Double = 0.0,
    var codEstado: String = "",
    var codTipoMoneda: String = "",
    var codNaturalezaArticulo: String = "",
    var codCabys: String = "",
    var actividadEconomica: String = "",
    var marca: String = "",
    var unidadXMedida: Double = 0.0,
    var unidadMedida: String = "",
    var fraccionamiento: Int = 0,
    var precioVentaManual: Int = 0,
    var minimo: Double = 0.0,
    var maximo: Double = 0.0,
    var descuentoAdmitido: Double = 0.0,
    var precio: Double = 0.0,
    var precioNeto: Double = 0.0,
    var lote: Int = 0,
    var bodegas: String = "[]",
    var lotes: String? = null
)

