package com.soportereal.invefacon.funciones_de_interfaces

import android.content.Context
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.Dispatchers
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
    alRecibirMensaje: suspend (String) -> Unit,
    onError: suspend (String) -> Unit,
    timeOut : Long = 13_000,
    crearConexion: Boolean = true
) {
    var socket: Socket? = null
    var flujoEntrada: InputStream? = null
    var flujoSalida: OutputStream? = null
    val puerto = obtenerParametroLocal(context, "puertoActual").toInt()


    try {
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
        if (crearConexion){
            val caracter = '\u00DF'
            val baseDatos = obtenerParametroLocal(context, "bdActual")
            val codUsuario =  obtenerParametroLocal(context, "codUsuarioActual")
            // Generar mensajes
            val mensajeConexion = generarConsultaSocket(
                context = context,
                proceso = "MODSEGU000",
                subProceso = "MODSEGU009",
                cuerpo = "$codUsuario${caracter}MOD${caracter}$baseDatos${caracter}VENTAS${caracter}11.94 23-06-2025${caracter}",
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

            if (leido == null) {
                onError("Tiempo de espera agotado: no se recibió respuesta del Socket")
                break
            }

            if (leido == -1) {
                onError("Conexión cerrada por el Socket")
                break
            }

            val fragmento = String(buffer, 0, leido, Charsets.ISO_8859_1)
            println("OUTPUT SOCKET: $fragmento")
            alRecibirMensaje(fragmento)
        }
    } catch (e: Exception) {
        onError(e.message ?: "Error desconocido")
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


fun generarConsultaSocket(context: Context, proceso: String, subProceso: String, cuerpo: String, generarIdProceso : Boolean = true, agregarBd : Boolean = true): String {
    val separador = '\u00DF'
    val baseDatos = if (agregarBd) obtenerParametroLocal(context, "bdActual")+separador else "" // ENVIAR BASE DE DATOS OPCIONAL
    val idProceso = if (generarIdProceso) generarIdProceso()+separador else ""
    return  "|>>" + // INICIO
            "$proceso.00.$subProceso$separador" + // PROCESO Y SUBPROCESO
            baseDatos+ // BASE DE DATOS
            idProceso + // IDPROCESO OPCIONAL
            cuerpo+ // CUERPO CONSULTA (SE ENVIA CON EL SEPARADOR AL FINAL)
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

fun validarRespuestaSocket(datos: String, onFin: ()->Unit) {
    val regex = Regex("""\|\>\>(.*?)\<\<\|""")
    val resultados = regex.findAll(datos).map { it.groupValues[1] }.toList()
    resultados.forEach {
        if (it.length<17) return@forEach
        val codigoRespuesta = it.substring(14,17)
        val mensajeRespuesta = it.substring(18, it.length)
        when(codigoRespuesta){
            "ERR" -> mostrarMensajeError("SCK: $mensajeRespuesta")
            "EVE"-> mostrarMensajeExito(mensajeRespuesta)
            "FPC" -> {
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                onFin()
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

class ProcGenSocket () {

    suspend fun obtenerImpresorasRemotas(
        context : Context,
        onErrorOrFin: (Boolean) -> Unit= {},
        datosRetornados : (List<List<String>>) -> Unit
    ){
        val caracter = '\u00DF'
        // Generar mensajes MODCONF000.00.MODCONF001ß*ßEND
        val mensajeSocket = generarConsultaSocket(
            context = context,
            proceso = "MODCONF000",
            subProceso = "MODCONF001",
            cuerpo = "*$caracter",
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
                    validarRespuestaSocket(datos = msg, onFin = {onErrorOrFin(true)})
                }
            },
            onError = { errorMsg ->
                withContext(Dispatchers.Main) {
                    mostrarMensajeError(errorMsg)
                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    onErrorOrFin(true)
                }
            }
        )
    }
}







