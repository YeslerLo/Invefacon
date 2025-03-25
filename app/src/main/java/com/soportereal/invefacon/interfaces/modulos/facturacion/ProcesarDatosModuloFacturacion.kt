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

    suspend fun agregarActualizarLinea(articulo: ArticuloLineaProforma): JSONObject? {
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("articuloCodigo", articulo.articuloCodigo)
            .addFormDataPart("tipoDocumento", articulo.tipoDocumento)
            .addFormDataPart("articuloActividadEconomica", articulo.articuloActividadEconomica)
            .addFormDataPart("articuloCantidad", articulo.articuloCantidad.toString())
            .addFormDataPart("articuloUnidadMedida", articulo.articuloUnidadMedida)
            .addFormDataPart("articuloCosto", articulo.articuloCosto.toString())
            .addFormDataPart("idCliente", articulo.idCliente)
            .addFormDataPart("articuloIvaTarifa", articulo.articuloIvaTarifa.toString())
            .addFormDataPart("articuloVenta", articulo.articuloVenta.toString())
            .addFormDataPart("articuloDescuentoPorcentage", articulo.articuloDescuentoPorcentage.toString())
            .addFormDataPart("articuloIvaPorcentage", articulo.articuloIvaPorcentage.toString())
            .addFormDataPart("articuloVentaTotal", articulo.articuloVentaTotal.toString())
            .addFormDataPart("articuloVentaSubTotal1", articulo.articuloVentaSubTotal1.toString())
            .addFormDataPart("articuloVentaSubTotal2", articulo.articuloVentaSubTotal2.toString())
            .addFormDataPart("articuloVentaSubTotal3", articulo.articuloVentaSubTotal3.toString())
            .addFormDataPart("articuloIvaMonto", articulo.articuloIvaMonto.toString())
            .addFormDataPart("articuloBodegaCodigo", articulo.articuloBodegaCodigo)
            .addFormDataPart("articuloVentaExento", articulo.articuloVentaExento.toString())
            .addFormDataPart("articuloVentaExonerado", articulo.articuloVentaExonerado.toString())
            .addFormDataPart("articuloIvaDevuelto", articulo.articuloIvaDevuelto.toString())
            .addFormDataPart("articuloOtrosCargos", articulo.articuloOtrosCargos.toString())
            .addFormDataPart("articuloIvaExonerado", articulo.articuloIvaExonerado.toString())
            .addFormDataPart("presentacion", "item")
            .addFormDataPart("articuloTipoPrecio", articulo.articuloTipoPrecio)
            .addFormDataPart("articuloVentaGravado", articulo.articuloVentaGravado.toString())

        if (articulo.articuloLine.isNotEmpty()) {
            formBody.addFormDataPart("articuloLineaId", articulo.articuloLine)
        }

        if (articulo.numero.isNotEmpty()) {
            formBody.addFormDataPart("numero", articulo.numero)
        }

        return objetoFuncionesHttpInvefacon.metodoPost(
            formBody = formBody.build(),
            apiDirectorio = "facturacion/agregaactualizalineaproforma.php"
        )
    }

    suspend fun obtenerArticulos(
        busquedaMixta: String = "",
        tipoPrecio: String,
        moneda: String,
        codigoBarra : String = ""
    ):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("tipoPrecio",tipoPrecio)
            .addFormDataPart("moneda",moneda)
            .addFormDataPart("busquedaMixta",busquedaMixta)
            .addFormDataPart("codigobarra",codigoBarra)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/articulosfacturar.php")
    }

    suspend fun eliminarLineaProforma(numero: String, lineaArticulo : String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Numero",numero)
            .addFormDataPart("ArticuloLineaId", lineaArticulo)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/eliminarlineaproformahija.php")
    }

    suspend fun cambiarClienteProforma(numero: String, clienteFacturacion: ClienteFacturacion): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("numero",numero)
            .addFormDataPart("clienteid", clienteFacturacion.codigo)
            .addFormDataPart("monedacodigo", clienteFacturacion.codMoneda)
            .addFormDataPart("tipoprecioventa", clienteFacturacion.tipoPrecio)
            .addFormDataPart("clientenombre", clienteFacturacion.nombreJuridico)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/cambiaclienteproforma.php")
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
    var articuloCantidad : Double = 0.0,
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
    var listaBodegas: List<ParClaveValor> = listOf(ParClaveValor()),
    var lotes: String? = null,
    var listaPrecios : List<ParClaveValor> =listOf(ParClaveValor()),
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
    var articuloLineaId: String = "",
    var articuloCosto: Double = 0.0,
    var utilidad: Double = 0.0,
    var Cod_Tarifa_Impuesto : String = "08"
)

data class ArticuloLineaProforma(
    val numero: String = "",
    val articuloLine: String = "",
    val tipoDocumento : String = "",
    val articuloCodigo : String= "",
    val articuloTipoPrecio : String= "",
    val articuloActividadEconomica : String= "",
    val articuloCantidad : Double = 0.0,
    val articuloUnidadMedida : String= "",
    val presentacion : String = "",
    val articuloBodegaCodigo: String= "",
    val articuloCosto: Double = 0.00,
    val articuloVenta : Double = 0.00,
    val articuloVentaSubTotal1 : Double = 0.00,
    val articuloDescuentoPorcentage : Double = 0.00,
    val articuloVentaSubTotal2 : Double  = 0.00,
    val articuloOtrosCargos : Double = 0.00,
    val articuloVentaSubTotal3 : Double = 0.00,
    val articuloIvaPorcentage : Double = 0.00,
    val articuloIvaTarifa :  String= "",
    val articuloIvaExonerado : Double = 0.00,
    val articuloIvaMonto : Double = 0.00,
    val articuloIvaDevuelto : Double = 0.00,
    val articuloVentaGravado : Double = 0.00,
    val articuloVentaExonerado : Double = 0.00,
    val articuloVentaExento : Double = 0.00,
    val articuloVentaTotal : Double = 0.00,
    val idCliente : String = ""
)

data class ClienteFacturacion(
    val codigo : String = "",
    val nombreJuridico : String = "",
    val nombreComercial : String = "",
    val telefono : String = "",
    val correo : String = "",
    val codMoneda : String = "",
    val tipoPrecio : String = ""
)


