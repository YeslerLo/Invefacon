package com.soportereal.invefacon.interfaces.modulos.facturacion

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
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

    suspend fun obtenerArticulos(
        busquedaMixta: String,
        tipoPrecio: String,
        moneda: String,
        codigoBarra : String = "",
        cantidadMostrar : Int
    ):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("tipoPrecio",tipoPrecio)
            .addFormDataPart("moneda",moneda)
            .addFormDataPart("busquedaMixta",busquedaMixta)
            .addFormDataPart("codigobarra",codigoBarra)
            .addFormDataPart("cantidadMostrar",cantidadMostrar.toString())
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/articulosfacturar.php")
    }
}


data class ArticuloFacturacion(
    var codigo: String = "12221",
    var codBarra: String = "",
    var descripcion: String = "Coca Cola Negra 12Ml",
    var stock: Double = 0.0,
    var costo: Double = 0.0,
    var descuentoFijo: Double = 0.0,
    var codTarifaImpuesto: String = "",
    var impuesto: Double = 0.0,
    var codEstado: String = "",
    var codTipoMoneda: String = "",
    var codNaturalezaArticulo: String = "",
    var codCabys: String = "",
    var actividadEconomica: String = "",
    var marca: String = "",
    var articuloCantidad : Int = 0,
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
    var codPrecioVenta : String = "",
    var listaBodegas: List<ParClaveValor> = emptyList(),
    var lotes: String? = null,
    var listaPrecios : List<ParClaveValor> = emptyList(),
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

data class ArticuloLineaProforma(
    val numero: String,
    val articuloLine: String,
    val tipoDocumento : String,
    val articuloCodigo : String,
    val articuloTipoPrecio : String,
    val articuloActividadEconomica : String,
    val articuloCantidad : Int,
    val articuloUnidadMedida : String,
    val articuloBodegaCodigo: String,
    val articuloCosto: Double,
    val articuloVenta : Double,
    val articuloVentaSubTotal1 : Double,
    val articuloDescuentoPorcentage : Double,
    val articuloVentaSubTotal2 : Double,
    val articuloOtrosCargos : Double,
    val articuloVentaSubTotal3 : Double,
    val articuloIvaPorcentage : Double,
    val articuloIvaTarifa : Double,
    val articuloIvaExonerado : Double,
    val articuloIvaMonto : Double,
    val articuloIvaDevuelto : Double,
    val articuloVentaGravado : Double,
    val articuloVentaExonerado : Double,
    val articuloVentaExento : Double,
    val articuloVentaTotal : Double,
    val idCliente : String,
    val listaBodegas: List<ParClaveValor>
)


