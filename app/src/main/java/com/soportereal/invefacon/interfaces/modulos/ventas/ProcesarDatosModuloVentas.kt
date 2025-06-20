package com.soportereal.invefacon.interfaces.modulos.ventas

import android.content.Context
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import com.soportereal.invefacon.funciones_de_interfaces.conectarSocket
import com.soportereal.invefacon.funciones_de_interfaces.generarConsultaSocket
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.validarRespuestaSocket
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject


class ProcesarDatosModuloVentas(apiToken: String) {

    private val objetoFuncionesHttpInvefacon = FuncionesHttp(apiToken = apiToken)

    suspend fun obtenerFacturas(
        fechaInicio: String,
        fechaFinal: String,
        codCliente : String,
        nombreFactura: String,
        tipoDocumento: String,
        estado: String,
        formaPago : String,
        medioPago : String,
        agente : String,
        numeroDocumento: String,
        moneda: String,
        usuario : String,
        paginActual : String
    ): JSONObject? {

        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("fechainicial", fechaInicio)
            .addFormDataPart("fechafinal", fechaFinal)
            .addFormDataPart("idcliente", codCliente)
            .addFormDataPart("nombreenfactura", nombreFactura)
            .addFormDataPart("tipodocumento", tipoDocumento)
            .addFormDataPart("estado", estado)
            .addFormDataPart("formapago", formaPago)
            .addFormDataPart("mediopago", medioPago)
            .addFormDataPart("usuariocodigo", usuario)
            .addFormDataPart("agentecodigo", agente)
            .addFormDataPart("numerodocumento", numeroDocumento)
            .addFormDataPart("monedacodigo", moneda)
            .addFormDataPart("porPagina", "6")
            .addFormDataPart("paginaActual", paginActual)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(
            formBody = formBody,
            apiDirectorio = "ventas/facturasventas.php"
        )
    }

    suspend fun obtenerFormaPago(): JSONObject? {
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("", "")
            .addFormDataPart("", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(
            formBody = formBody,
            apiDirectorio = "varios/tipoformapago.php"
        )
    }

    suspend fun aplicarNotaCreditoCompleta(context : Context, numeroDocumento: String, onErrorOrFin: (Boolean) -> Unit){
        val caracter = '\u00DF'
        // Generar mensajes
        val mensajeSocket = generarConsultaSocket(
            context = context,
            proceso = "MODSEGU000",
            subProceso = "MODSEGU009",
            cuerpo = "00362${caracter}MOD${caracter}DEMOFERRE${caracter}VENTAS${caracter}11.72 10-06-2025${caracter}",
            agregarBd = false,
            generarIdProceso = false
        ) + generarConsultaSocket(
            context = context,
            proceso = "MODVENT000",
            subProceso = "MODVENT079",
            cuerpo = "00006${caracter}$numeroDocumento${caracter}venta${caracter}${caracter}"
        )
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        conectarSocket(
            context = context,
            mensaje = mensajeSocket,
            alRecibirMensaje = { msg ->
                withContext(Dispatchers.Main) {
                    validarRespuestaSocket(datos = msg, onFin = {onErrorOrFin(false)})
                }
            },
            onError = { errorMsg ->
                withContext(Dispatchers.Main) {
                    mostrarMensajeError(errorMsg)
                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    onErrorOrFin(false)
                }
            }
        )
    }

    suspend fun obtenerDatosFactura(numero: String): JSONObject? {
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("numerodocumento", numero)
            .addFormDataPart("", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(
            formBody = formBody,
            apiDirectorio = "ventas/ventasDetalleFactura.php"
        )
    }
}

data class FacturaVentas(
    val numero : String,
    val fecha  : String,
    val cliente : String,
    val impuestos : Double,
    val total : Double,
    val estado : String,
    val moneda : String,
    var isAnimado : Boolean = false
)

data class ClienteVentas(
    val id_cliente: String = "",
    val nombre: String = "",
    val clientenombrecomercial: String = "",
    val tipoidentificacion: String = "",
    val cedula: String = "",
    val plazo: String = "",
    val emailfactura: String = "",
    val email: String = "",
    val emailcobro: String = "",
    val direccion: String = "",
    val telefonos: String = ""
)


data class DetalleDocumentoVentas(
    val numero: String = "",
    val fecha: String = "2022-11-24 12:02:20.260",
    val usuariocodigo: String = "",
    val agentecodigo: String = "",
    val refereridocodigo: String = "",
    val referencia: String = "",
    val estado: String = "",
    val monedacodigo: String = "",
    val formapagocodigo: String = "",
    val mediopagodetalle: String = "",
    val detalle: String = ""
)


data class ArticuloVenta(
    val Cod_Articulo: String = "",
    val ArticuloTipoPrecio: String = "",
    val Descripcion: String = "",
    val ArticuloCantidad: Double = 0.0,
    val ArticuloBodegaCodigo: String = "",
    val ArticuloDescuentoPorcentage:  Double = 0.0,
    val ArticuloVenta:  Double = 0.0,
    val ArticuloDescuentoMonto:  Double = 0.0,
    val ArticuloVentaSubTotal2: String = "",
    val ArticuloVentaGravado:  Double = 0.0,
    val ArticuloVentaExonerado: String = "",
    val ArticuloVentaExento: String = "",
    val ArticuloIvaPorcentage:  Double = 0.0,
    val ArticuloIvaMonto : Double = 0.0,
    val ArticuloVentaTotal:  Double = 0.0
)


data class TotalesVentas(
    val TotalVenta : Double = 0.0,
    val TotalDescuento : Double = 0.0,
    val TotalMercGravado : Double = 0.0,
    val TotalMercExonerado : Double = 0.0,
    val TotalMercExento : Double = 0.0,
    val TotalServGravado : Double = 0.0,
    val TotalServExento : Double = 0.0,
    val TotalServExonerado : Double = 0.0,
    val totaliva : Double = 0.0,
    val TotalIvaDevuelto : Double = 0.0,
    val Total : Double = 0.0,
)
