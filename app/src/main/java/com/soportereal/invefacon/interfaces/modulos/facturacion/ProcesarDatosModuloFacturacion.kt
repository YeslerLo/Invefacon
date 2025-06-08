package com.soportereal.invefacon.interfaces.modulos.facturacion

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.addText
import com.soportereal.invefacon.funciones_de_interfaces.addTextBig
import com.soportereal.invefacon.funciones_de_interfaces.addTextTall
import com.soportereal.invefacon.funciones_de_interfaces.agregarDobleLinea
import com.soportereal.invefacon.funciones_de_interfaces.agregarLinea
import com.soportereal.invefacon.funciones_de_interfaces.gestorImpresora
import com.soportereal.invefacon.funciones_de_interfaces.imagenAHexadecimal
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
import com.soportereal.invefacon.funciones_de_interfaces.obtenerValorParametroEmpresa
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import okhttp3.MultipartBody
import org.json.JSONObject
import java.util.Locale

class ProcesarDatosModuloFacturacion(apiToken: String)
{
    private val objetoFuncionesHttpInvefacon = FuncionesHttp(apiToken = apiToken)

    suspend fun crearNuevaProforma():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0","")
            .addFormDataPart("0","")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/nuevaproforma.php")
    }

    suspend fun abrirProforma(numero: String = ""):JSONObject? {
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0","")
            .addFormDataPart("isUltimaVersion","1")
            .addFormDataPart("numerodocumento",numero)
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
            .addFormDataPart("articuloIvaTarifa", articulo.articuloIvaTarifa)
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
            .addFormDataPart("articuloSerie", articulo.articuloSerie)
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

    suspend fun obtenerArticulos(busquedaMixta: String = "", tipoPrecio: String, moneda: String, codigoBarra : String = "", ultimaActualizacion: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("tipoPrecio",tipoPrecio)
            .addFormDataPart("moneda",moneda)
            .addFormDataPart("busquedaMixta",busquedaMixta)
            .addFormDataPart("codigobarra",codigoBarra)
            .addFormDataPart("ultimaActualizacion",ultimaActualizacion)
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

    suspend fun guardarProformaBorrador(numero: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("proforma", numero)
            .addFormDataPart("0", "")
            .addFormDataPart("0", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/guardarproformaborrador.php")
    }

    suspend fun guardarProforma(numero: String, tipoPago: String, tipo: String, correo: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("proforma", numero)
            .addFormDataPart("tipo", tipo)
            .addFormDataPart("procesar", "")
            .addFormDataPart("correo", correo)
            .addFormDataPart("tipoPago", tipoPago)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/guardarproforma.php")
    }

    suspend fun clonarProforma(numero: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("FacturaClona", numero)
            .addFormDataPart("0", "")
            .addFormDataPart("0", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/clonar.php")
    }

    suspend fun exonerarProforma(numero: String, codigoCliente: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("ClienteId", codigoCliente)
            .addFormDataPart("Numero", numero)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/exonerarfactura.php")
    }

    suspend fun cambiarMonedaProforma(numero: String, codigoMoneda: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("monedacodigo", codigoMoneda)
            .addFormDataPart("numero", numero)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/cambiamonedaproforma.php")
    }

    suspend fun cambiarTipoPrecio(numero: String, tipoPrecio: String, lineas : String, monedaFactura : String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("tipoPrecio", tipoPrecio)
            .addFormDataPart("numero", numero)
            .addFormDataPart("lineas", lineas)
            .addFormDataPart("monedaFactura", monedaFactura)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/CambiarTodosTipoPrecio.php")
    }

    suspend fun aplicarDescuento(numero: String, descuento: String, lineas : String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("descuento", descuento)
            .addFormDataPart("numero", numero)
            .addFormDataPart("lineas", lineas)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/ActualizaDescuentoGlobal.php")
    }

    suspend fun quitarExoneracionProforma(numero: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0", "")
            .addFormDataPart("Numero", numero)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/quitaexoneracionfactura.php")
    }

    suspend fun obtenerMediosPago(): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0", "")
            .addFormDataPart("0", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "varios/cuentasmediopago.php")
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

    suspend fun obtenerProformas(nombreCliente: String, fechaInicio: String, fechaFinal: String, estadoProforma: String, codUsuario : String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("fechainicial",fechaInicio)
            .addFormDataPart("fechafinal",fechaFinal)
            .addFormDataPart("clientenombre",nombreCliente)
            .addFormDataPart("tipo",estadoProforma)
            .addFormDataPart("usuariocodigo",codUsuario)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/consultarproformas.php")
    }

    suspend fun obtenerFactura(referencia: String): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("referencia",referencia)
            .addFormDataPart("0","")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/obtenerFactura.php")
    }

    suspend fun agregarFormaPago(pago:Pago): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("id",pago.Id)
            .addFormDataPart("documento",pago.Documento)
            .addFormDataPart("cuentaContable", pago.CuentaContable)
            .addFormDataPart("codigoMoneda", pago.CodigoMoneda)
            .addFormDataPart("monto",pago.Monto)
            .addFormDataPart("tipoCambio", pago.TipoCambio)
            .addFormDataPart("funcion", pago.funcion)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "facturacion/DocumentoMedioPago.php")
    }

    suspend fun obtenerPermisosUsuario(codUsuario: String): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("codigousuario", codUsuario)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "seguridad/derechossistemadelusuario.php")
    }

    suspend fun obtenerParemetrosEmpresa(): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("0", "0")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "varios/parametrosempresa.php")
    }

    suspend fun guardarDestalleFactura(detalle: String, numero: String): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Numero", numero)
            .addFormDataPart("Detalle", detalle)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "facturacion/ActualizaDetalleFacturaProforma.php")
    }

    suspend fun guardarOrdenCompra(detalle: String, numero: String): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("numero", numero)
            .addFormDataPart("ordencompra", detalle)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "facturacion/agregarordencompra.php")
    }

    suspend fun actualizarNombreProforma(nombre: String, numero: String): JSONObject? {
        val apiBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("numero", numero)
            .addFormDataPart("nombre", nombre)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(apiBody, "facturacion/ActualizarNombreProforma.php")
    }
}

@RequiresApi(Build.VERSION_CODES.S)

suspend fun imprimirFactura(factura: Factura, context: Context, nombreEmpresa : String, datosImpresion : ParClaveValor) : Boolean{
    var facturaTexto = ""

    try {
        val rutaImagen = obtenerParametroLocal(context, clave = "$nombreEmpresa.jpg")
        val imagenHex = imagenAHexadecimal(rutaImagen, gestorImpresora.impresora) ?: "0"
        facturaTexto = if (imagenHex != "0") {
            "[C]<img>$imagenHex</img>\n"
        } else {
            ""
        }
    }catch (e:Exception){
        Toast.makeText(context, "Error en impresión de Logo", Toast.LENGTH_SHORT).show()
    }
    facturaTexto += "\n"
    facturaTexto += addTextBig(factura.empresa.nombre, "C", context = context)
    facturaTexto += addText("IDENTIFICACION: "+factura.empresa.cedula, "C", context = context)
    facturaTexto += addTextTall("FACTURA ELECTRONICA ${factura.descripcionMedioPago.uppercase(Locale.ROOT)} ${if(datosImpresion.valor == "2") "COPIA ${datosImpresion.clave}" else if(datosImpresion.valor == "3") "REIMPRESION" else "ORIGINAL"}", "C", destacar = true, context = context)
    facturaTexto += addTextTall("DOC: ${factura.ventaMadre.Numero}", "C", context = context)
    facturaTexto += addText("CLAVE: ${factura.clave.substring(0, 25)}", "C", context = context)
    facturaTexto += addText("       ${factura.clave.substring(25, 50)}", "C", context = context)
    facturaTexto += addText("FECHA: ${factura.ventaMadre.Fecha}", "L", context = context)
    if (obtenerValorParametroEmpresa("20", "0") == "1") facturaTexto += addTextTall("FORMA PAGO: ${factura.descrpcionFormaPago.uppercase(Locale.ROOT)}", "C", context = context)
    if (factura.cliente.Cedula.isNotEmpty())facturaTexto += addText("CEDULA: ${factura.cliente.Cedula}", "L", context = context)
    facturaTexto += addText("NOMBRE: ${factura.cliente.Nombre}", "L", context = context)
    if (factura.cliente.EmailFactura.isNotEmpty()) facturaTexto += addText("EMAIL: ${factura.cliente.EmailFactura}", "L", context = context)
    if (factura.cliente.Telefonos.isNotEmpty()) facturaTexto += addText("TELEFONO: ${factura.cliente.Telefonos}", "L", context = context)
    if (factura.cliente.Direccion.isNotEmpty()) facturaTexto += addText("DIRECCION: ${factura.cliente.Direccion}", "L", context = context)
    facturaTexto += addTextTall("DETALLE FACTURA", "C", context = context)
    facturaTexto += agregarDobleLinea(context = context, justification = "C")
    facturaTexto += addText(if (obtenerValorParametroEmpresa("307", "0") == "1") "CABYS" else "", "L", context = context, destacar = true)
    facturaTexto += addText("CANTIDAD ${if (obtenerValorParametroEmpresa("15", "0") == "1") "#CODIGO" else ""} DESCRIPCION", "L", context = context, destacar = true)
    facturaTexto += addText("P/U / IMP(%) / DES(%) / T.GRAV", "R", context = context)
    facturaTexto += agregarLinea(context = context)
    val listaIvas = mutableListOf<ParClaveValor>()
    for (i in 0 until factura.ventaHija.size) {
        val articulo = factura.ventaHija[i]
        facturaTexto += addText(if (obtenerValorParametroEmpresa("307", "0") == "1")  articulo.ArticuloCabys else "", "L", context = context)
        facturaTexto += addText("${articulo.ArticuloCantidad.toDouble()} ${if (obtenerValorParametroEmpresa("15", "0") == "1") "#${articulo.ArticuloCodigo} " else ""}"+articulo.nombreArticulo, "L", destacar = true, context = context)
        facturaTexto += addText("${separacionDeMiles(isString = true, montoString = articulo.ArticuloVenta)} / ${articulo.ArticuloIvaPorcentage}% / ${articulo.ArticuloDescuentoPorcentage}% / ${separacionDeMiles(isString = true, montoString = articulo.ArticuloVentaGravado)}", "R", context = context)
        facturaTexto += agregarLinea(context = context)
        val ivaTemp = listaIvas.find { it.clave == articulo.ArticuloIvaPorcentage }
        if ( ivaTemp != null){
            ivaTemp.valor = (ivaTemp.valor.toDouble() + articulo.ArticuloIvaMonto.toDouble()).toString()
            ivaTemp.venta = (ivaTemp.venta.toDouble() + articulo.ArticuloVentaGravado.toDouble()).toString()
        }else{
            listaIvas.add(ParClaveValor(clave = articulo.ArticuloIvaPorcentage, valor = articulo.ArticuloIvaMonto, venta = articulo.ArticuloVentaGravado))
        }
    }
    if (obtenerValorParametroEmpresa("192", "0") == "1"){
        facturaTexto += agregarDobleLinea(context = context, justification = "C")
        facturaTexto += addText("SUBTOTAL: [R]${separacionDeMiles(isString = true, montoString = (factura.ventaMadre.TotalVenta.toDouble()+factura.ventaMadre.TotalDescuento.toDouble()).toString())}", "R", context = context)
        if (obtenerValorParametroEmpresa("26", "0") == "1") facturaTexto += addText("DESCUENTO: [R]${separacionDeMiles(isString = true, montoString = factura.ventaMadre.TotalDescuento)}", "R", context = context)
        facturaTexto += addText("MERC GRAVADA: [R]${ separacionDeMiles(isString = true, montoString = factura.ventaMadre.TotalMercGravado)}", "R", context = context)
        facturaTexto += addText("IVA: [R]${separacionDeMiles(isString = true, montoString = factura.ventaMadre.TotalIva)}", "R", context = context)
        facturaTexto += agregarLinea(ajustarATexto = true, text = "  ${factura.ventaMadre.MonedaCodigo} TOTAL ${factura.ventaMadre.Total}", justification = "R", context = context)
        facturaTexto += addTextTall("  ${factura.ventaMadre.MonedaCodigo} TOTAL ${separacionDeMiles(isString = true, montoString = factura.ventaMadre.Total)}", "R", destacar = true, context = context)
    }
    if (obtenerValorParametroEmpresa("47", "0") == "1"){
        facturaTexto += addText("***RESUMEN IVA***", "C", context = context)
        facturaTexto += addText("IVA%  /  VENTA  /  IVA", "C", context = context, conLiena = true)
        for(iva in listaIvas){
            facturaTexto += addText("${iva.clave}% / ${separacionDeMiles(montoString = iva.venta, isString = true)} / ${separacionDeMiles(montoString = iva.valor, isString = true)}", "C", context = context)
        }
    }
    facturaTexto += "\n"
    facturaTexto += addText("CAJA: ${factura.ventaMadre.CajaNumero} VEND: ${factura.nombreAgente}", "C", context = context)
    facturaTexto += addText(factura.ventaMadre.MedioPagoDetalle.replace("\r", "").uppercase(Locale.ROOT), "C", context = context, conLiena = true)
    facturaTexto += addText(obtenerValorParametroEmpresa("137", "GRACIAS POR SU COMPRA!"), "C", context = context)
    facturaTexto += addText(factura.leyenda, "C", context = context)
    facturaTexto += addText("INVEFACON ANDROID V.${context.getString(R.string.app_version)}", "C", context = context, destacar = true)
    return gestorImpresora.imprimir(text = facturaTexto, context)
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
    var Cod_Tarifa_Impuesto : String = "08",
    var articuloSerie : String = ""
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
    val articuloSerie : String = "",
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
    val tipoPrecio : String = "",
    val estado : String = ""
)

data class Proforma(
    val numero : String = "",
    val nombreCliente : String = "",
    val total : String = "",
    val codMoneda: String = ""
)

data class VentaMadre(
    val Id: String = "",
    val Numero: String = "",
    val TipoDocumento: String = "",
    val Referencia: String = "",
    val Estado: String = "",
    val Fecha: String = "",
    val MonedaCodigo: String = "",
    val MonedaTipoCambio: String = "",
    val ClienteID: String = "",
    val ClienteNombre: String = "",
    val UsuarioCodigo: String = "",
    val AgenteCodigo: String = "",
    val RefereridoCodigo: String = "",
    val Oficina: String = "",
    val CajaNumero: String = "",
    val FormaPagoCodigo: String = "",
    val MedioPagoCodigo: String = "",
    val MedioPagoDetalle: String = "",
    val ModEntregaCodigo: String = "",
    val DetallePide: String = "",
    val Detalle: String = "",
    val TotalCosto: String = "0",
    val TotalVenta: String = "0",
    val TotalDescuento: String = "0",
    val TotalMercGravado: String = "0",
    val TotalMercExonerado: String = "0",
    val TotalMercExento: String = "0",
    val TotalServGravado: String = "0",
    val TotalServExonerado: String = "0",
    val TotalServExento: String = "0",
    val TotalImpuestoServicio: String = "0",
    val TotalIva: String = "0",
    val TotalIvaDevuelto: String = "0",
    val Total: String = "0"
)

data class VentaHija(
    val ArticuloLineaId: String = "",
    val Numero: String = "",
    val TipoDocumento: String = "",
    val ArticuloCodigo: String = "",
    val ArticuloCabys: String = "",
    val ArticuloActividadEconomica: String = "",
    val ArticuloCantidad: String = "0",
    val ArticuloUnidadMedida: String = "",
    val ArticuloSerie: String = "",
    val ArticuloTipoPrecio: String = "",
    val ArticuloBodegaCodigo: String = "",
    val ArticuloCosto: String = "0",
    val ArticuloVenta: String = "0",
    val ArticuloVentaSubTotal1: String = "0",
    val ArticuloDescuentoPorcentage: String = "0",
    val ArticuloDescuentoMonto: String = "0",
    val ArticuloVentaSubTotal2: String = "0",
    val ArticuloOtrosCargos: String = "0",
    val ArticuloVentaSubTotal3: String = "0",
    val ArticuloIvaPorcentage: String = "0",
    val ArticuloIvaTarifa: String = "0",
    val ArticuloIvaExonerado: String = "0",
    val ArticuloIvaMonto: String = "0",
    val ArticuloIvaDevuelto: String = "0",
    val ArticuloVentaGravado: String = "0",
    val ArticuloVentaExonerado: String = "0",
    val ArticuloVentaExento: String = "0",
    val ArticuloVentaTotal: String = "0",
    val nombreArticulo: String = ""
)

data class Empresa(
    val nombre: String = "",
    val cedula: String = "",
    val telefono: String = "",
    val direccion: String = "",
    val correo: String = ""
)

data class Cliente(
    val Id_Cliente: String = "",
    var Nombre: String = "",
    val Telefonos: String = "",
    var Direccion: String = "",
    val Fecha: String = "",
    var TipoPrecioVenta: String = "1",
    val Cod_Tipo_Cliente: String = "",
    var Email: String = "",
    val DiaCobro: String = "",
    val Contacto: String = "",
    val Exento: String = "",
    val AgenteVentas: String = "",
    val Cod_Estado: String = "",
    val UltimaVenta: String = "",
    val Cod_Zona: String = "",
    val DetalleContrato: String = "",
    val MontoContrato: String = "0",
    val NoForzarCredito: String = "",
    val Descuento: String = "0",
    val DivisionTerritorial: String = "",
    val MontoCredito: String = "0",
    val plazo: String = "",
    val TieneCredito: String = "0",
    var Cedula: String = "",
    val FechaNacimiento: String = "",
    var Cod_Moneda: String = "CRC",
    val FechaVencimiento: String = "",
    var TipoIdentificacion: String = "01",
    var ClienteNombreComercial: String = "",
    var EmailFactura: String = "",
    var EmailCobro: String = "",
    val PorcentajeInteres: String = "0"
)

data class Factura(
    val ventaMadre: VentaMadre = VentaMadre(),
    val ventaHija: List<VentaHija> = listOf(),
    val empresa: Empresa = Empresa(),
    val cliente: Cliente = Cliente(),
    val clave: String = "",
    val leyenda: String = "",
    val descrpcionFormaPago : String = "",
    val descripcionMedioPago : String = "",
    val nombreAgente : String = ""
)

data class Pago(
    var Id : String = "0",
    var funcion : String = "crear",
    var Documento: String = "",
    var TipoDocumento: String = "",
    var Monto: String = "",
    var CuentaContable: String = "",
    var CodigoMoneda: String = "",
    var TipoCambio: String = "500",
    var Total: String = ""
)



