package com.soportereal.invefacon.funciones_de_interfaces

import android.content.Context
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import java.io.InputStream
import java.io.OutputStream
import java.net.ConnectException
import java.net.Socket
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.coroutines.coroutineContext
import kotlin.random.Random


suspend fun conectarSocket(
    context: Context,
    host: String = "sistema.soportereal.com",
    hostAlternativo : String = "backup.soportereal.com",
    mensaje: String,
    alRecibirMensaje: suspend (String) -> Unit = {},
    onExitoOrFin: suspend (Boolean) -> Unit,
    timeOut : Long = 13_000,
    crearConexion: Boolean = true
) {
    var socket: Socket? = null
    var flujoEntrada: InputStream? = null
    var flujoSalida: OutputStream? = null
    val puerto = obtenerParametroLocal(context, "puertoActual").toInt()
    var historialCodEstado = ""
    val expreValiEstadoExito = Regex("^(?=.*EVE)(?=.*FPC)")
    val expreValiEstadoError = Regex("^(?=.*FPC)(?=.*ERR)")


    try {
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        socket = withContext(Dispatchers.IO) {
            try {
                Socket(host, puerto)
            } catch (e: UnknownHostException) {
                Socket(hostAlternativo, puerto)
            } catch (e: ConnectException) {
                Socket(hostAlternativo, puerto)
            }
        }
        flujoEntrada = withContext(Dispatchers.IO) {
            socket.getInputStream()
        }
        flujoSalida = withContext(Dispatchers.IO) {
            socket.getOutputStream()
        }
        println("MSG PRIMARIO: $mensaje")
        var mensajeTemp = mensaje
        val baseDatos = obtenerParametroLocal(context, "bdActual")
        val codUsuario =  obtenerParametroLocal(context, "codUsuarioActual")
        if (crearConexion) {
            // Generar mensajes
            val mensajeConexion = generarConsultaSocket(
                context = context,
                proceso = "MODSEGU000",
                subProceso = "MODSEGU009",
                cuerpo = listOf(codUsuario, "MOD", baseDatos, "VENTAS", "11.94 23-06-2025"),
                agregarBd = false,
                generarIdProceso = false
            )
            mensajeTemp = mensajeConexion + mensaje
        }
        println("INPUT SOCKET: $mensajeTemp")
        // Enviar mensaje inicial codificado en ISO-8859-1
        val bytes = mensajeTemp.toByteArray(Charsets.ISO_8859_1)
        withContext(Dispatchers.IO) {
            flujoSalida.write(bytes)
            flujoSalida.flush()
        }

        val buffer = ByteArray(2048)
        while (coroutineContext.isActive) {
            // Si no se recibe nada en 13 segundos, salir
            val leido = withTimeoutOrNull(timeOut) {
                withContext(Dispatchers.IO) {
                    flujoEntrada.read(buffer)
                }
            }
            var finalizarSocket = false

            if (leido == null) {
                mostrarToastSeguro(context = context, mensaje = "Tiempo de espera agotado: no se recibió respuesta del Socket")
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                onExitoOrFin(false)
                break
            }

            if (leido == -1) {
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                onExitoOrFin(false)
                break
            }

            val fragmento = String(buffer, 0, leido, Charsets.ISO_8859_1)
            alRecibirMensaje(fragmento)
            // LA FUNCION DE onExitoOrFin() ES PODER RETORNAR UN TRUE Y UN FALSE
            // SI ES FALSE, NO HUBO NINGUNA RESPUESTA DE EXITO Y FINALIZO LA RESPUESTA, ESTO SIRVE PARA PODER DETECTAR SI LA EJECUION DEL SOCKET ES EXITOSA
            // SI ES TRUE LA RESPUESTA ES EXITOSA Y FINALIZO LA RESPUESTA
            validarRespuestaSocket(
                datos = fragmento,
                onExitoOrFin = { codEstado -> // SOLO RETORNA CODIGOS DE RESPUESTA EVE, ERR Y FPC
                    historialCodEstado += codEstado // SE CONCATENA EL CODIGO
                    if (expreValiEstadoExito.containsMatchIn(historialCodEstado)) { // SI EXISTE AL MENOS UN FCP Y UN EVE
                        onExitoOrFin(true)
                    }

                    if (historialCodEstado == "FPC") { // Y  SE AUSUME QUE ES EXITO O SI SOLO ES FPC YA QUE NO HUBO NINGUN ERROR Y FINALIZCO EL PROCESO
                        onExitoOrFin(true)
                        finalizarSocket = true
                    }

                    if (expreValiEstadoError.containsMatchIn(historialCodEstado)){
                        onExitoOrFin(false)  // SI CONTIENE UN ERR ESO SIGNIFICA QUE NO HUBO NINGUNA RESPUESTA EXITOSA, SE ENVIA FALSE
                    }
                }
            )
            if (finalizarSocket) break
        }
    } catch (e: Exception) {
        mostrarMensajeError(e.message ?: "Error desconocido")
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        onExitoOrFin(false)
    } finally {
        try {
            withContext(Dispatchers.IO) {
                flujoSalida?.close()
                flujoEntrada?.close()
                socket?.close()
            }
        } catch (_: Exception) {
        }
    }
}


fun generarConsultaSocket(context: Context, proceso: String, subProceso: String, cuerpo: List<String>, generarIdProceso : Boolean = true, agregarBd : Boolean = true): String {
    val separador = '\u00DF'
    val baseDatos = if (agregarBd) obtenerParametroLocal(context, "bdActual")+separador else "" // ENVIAR BASE DE DATOS OPCIONAL
    val idProceso = if (generarIdProceso) generarIdProceso()+separador else ""
    var cuerpoTemp = ""
    cuerpo.forEach {
        cuerpoTemp+=it+separador
    }

    return  "|>>" + // INICIO
            "$proceso.00.$subProceso$separador" + // PROCESO Y SUBPROCESO
            baseDatos+ // BASE DE DATOS
            idProceso + // IDPROCESO OPCIONAL
            cuerpoTemp+ // CUERPO CONSULTA (SE ENVIA CON EL SEPARADOR AL FINAL)
            "END$separador" + // END
            "<<|" // FIN
}

fun generarIdProceso(): String {
    // Obtener el tiempo actual con milisegundos
    val now = System.currentTimeMillis()
    val fecha = Date(now)

    // Configurar formato de fecha/hora para Costa Rica
    val formato = SimpleDateFormat("ddMMyyHHmmss", Locale.getDefault())
    formato.timeZone = TimeZone.getTimeZone("America/Costa_Rica")

    val fechaFormateada = formato.format(fecha)

    // Extraer los 4 primeros decimales de los milisegundos
    val milis = String.format(Locale.US, "%04d", now % 10000)
    val sufijoAleatorio = (1..11)
        .map { Random.nextInt(0, 36) }.joinToString("") { it.toString(36) }
        .uppercase(Locale.ROOT)

    // Unir todo
    val cadenaFinal = "$sufijoAleatorio..$fechaFormateada$milis"
    return cadenaFinal
}

suspend fun validarRespuestaSocket(datos: String, onExitoOrFin: suspend (String)->Unit) {
    val regex = Regex("""\|\>\>(.*?)\<\<\|""")
    val resultados = regex.findAll(datos).map { it.groupValues[1] }.toList()
    resultados.forEach {
        println("OUTPUT SOCKET: $it")
        if (it.length<17) return@forEach
        val codigoRespuesta = it.substring(14,17)
        val mensajeRespuesta = it.substring(18, it.length)
        when(codigoRespuesta){
            "ERR" -> {
                mostrarMensajeError("SCK: $mensajeRespuesta")
                onExitoOrFin("ERR")
            }
            "EVE"-> {
                mostrarMensajeExito("SCK: $mensajeRespuesta")
                onExitoOrFin("EVE")
            }
            "FPC" -> {
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                onExitoOrFin("FPC")
            }
        }
    }
}

suspend fun obtenerConsecutivoSocket(datos: String, consecutivoRetornar: suspend (String)->Unit) {
    val regex = Regex("""\|\>\>(.*?)\<\<\|""")
    val resultados = regex.findAll(datos).map { it.groupValues[1] }.toList()
    resultados.forEach {
        if (it.length<17) return@forEach
        val codigoRespuesta = it.substring(14,17)
        val consecutivo = it.substring(18, it.length)
        when(codigoRespuesta){
            "FTC" -> {
                consecutivoRetornar(consecutivo.split("ß")[0])
            }
            "FTF" -> {
                consecutivoRetornar(consecutivo.split("ß")[0])
            }
            "FTD" -> {
                consecutivoRetornar(consecutivo.split("ß")[0])
            }
            "FTP" -> {
                consecutivoRetornar(consecutivo.split("ß")[0])
            }
        }
    }
}

fun deserializarListaSocket(input: String, codigoRespuesta: String): List<List<String>> {
    println(input)
    val regex = Regex("""\|\>\>(.*?)\<\<\|""")
    val resultados = regex.findAll(input).map { it.groupValues[1] }

    val contenidoValido = resultados.firstOrNull { contenido ->
        contenido.length >= 17 && contenido.substring(14, 17) == codigoRespuesta
    } ?: return emptyList()

    val datos = if (contenidoValido.length > 18) contenidoValido.substring(18) else return emptyList()

    return datos.split("Ý")
        .filter { it.isNotBlank() }
        .map { tupla ->
            tupla.split("ß").filter { it.isNotBlank() }
        }
}

class ProcGenSocket {

    suspend fun obtenerImpresorasRemotas(
        context : Context,
        onExitoOrFin: (Boolean) -> Unit,
        datosRetornados : (List<List<String>>) -> Unit
    ){
        // Generar mensajes MODCONF000.00.MODCONF001ß*ßEND
        val mensajeSocket = generarConsultaSocket(
            context = context,
            proceso = "MODCONF000",
            subProceso = "MODCONF001",
            cuerpo = listOf("*"),
            agregarBd = false,
            generarIdProceso = false
        )
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        conectarSocket(
            context = context,
            mensaje = mensajeSocket,
            alRecibirMensaje = { msg ->
                withContext(Dispatchers.Main) {
                    val listaImpresora = deserializarListaSocket(msg, "334")
                    datosRetornados(listaImpresora)
                }
            },
            onExitoOrFin = {
                onExitoOrFin(it)
            }
        )

    }

    suspend fun imprimirRemotamente(
        context : Context,
        onExitorOrFin: (Boolean) -> Unit,
        documento : String,
        isProforma : Boolean,
        codImpresora : String,
        formatoImpresora : String
    ){
        val tabla = if (isProforma) "PROFORMA" else "VENTA"
        // Generar mensajes MODCONF000.00.MODCONF001ß*ßEND
        val mensajeSocket = generarConsultaSocket(
            context = context,
            proceso = "MODFACT000",
            subProceso = "MODFACT022",
            cuerpo = listOf(documento, tabla, codImpresora, formatoImpresora)
        )

        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        conectarSocket(
            context = context,
            mensaje = mensajeSocket,
            onExitoOrFin = {
                onExitorOrFin(it)
            }
        )
    }
}







