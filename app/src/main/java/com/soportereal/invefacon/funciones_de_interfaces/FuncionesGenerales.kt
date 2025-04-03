package com.soportereal.invefacon.funciones_de_interfaces

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.DeviceConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.URL
import java.util.Calendar
import java.util.Locale
import java.util.UUID

fun mostrarMensajeError(mensaje: String){
    val jsonObject = JSONObject(
        """
                    {
                        "code": 400,
                        "status": "error",
                        "data": "$mensaje"
                    }
                """
    )
    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
}

fun mostrarMensajeExito(mensaje: String){
    val jsonObject = JSONObject(
        """
                    {
                        "code": 200,
                        "status": "ok",
                        "data": "$mensaje"
                    }
                """
    )
    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
}

fun guardarParametroSiNoExiste(context: Context, clave: String, valor: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

    if (!sharedPreferences.contains(clave)) {
        sharedPreferences.edit().putString(clave, valor).apply()
    }
}

fun obtenerParametro(context: Context, clave: String, valorPorDefecto: String = "Desconocido"): String {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
    return sharedPreferences.getString(clave, valorPorDefecto) ?: valorPorDefecto
}

fun actualizarParametro(context: Context, clave: String, nuevoValor: String) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)
    sharedPreferences.edit().putString(clave, nuevoValor).apply() // Sobrescribe el valor
}

suspend fun obtenerDatosClienteByCedula(
    numeroCedula: String
):JSONObject?{
    val objetoFuncionesHttpInvefacon= FuncionesHttp(servidorUrl = "https://apis.gometa.org/")
    return objetoFuncionesHttpInvefacon.metodoGet(apiDirectorio = "cedulas/$numeroCedula", validarJson = false, enviarToken = false)
}

fun separacionDeMiles(
    montoDouble:Double= 0.00,
    montoInt: Int = 0,
    montoString: String = "",
    isDouble: Boolean= true,
    isString: Boolean = false
): String{
    if (isString){
        val monto = if (montoString.isEmpty()) 0.00 else montoString.toDouble()
        return String.format(Locale.US, "%,.2f", monto.toString().replace(",", "").toDouble())
    }else{

        val monto = if (isDouble) montoDouble else montoInt
        return String.format(Locale.US, "%,.2f", monto.toString().replace(",", "").toDouble())
    }
}

fun validarExitoRestpuestaServidor(respuesta : JSONObject): Boolean{
    return respuesta.getString("code")=="200"
}

fun String.isSoloNumeros(): Boolean {
    return this.matches(Regex("\\d+"))
}

fun mostrarTeclado(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
}

fun obtenerFechaHoy(): String{
    val calendario = Calendar.getInstance()
    val anioActual = calendario.get(Calendar.YEAR)
    val mesActual = calendario.get(Calendar.MONTH)
    val diaActual = calendario.get(Calendar.DAY_OF_MONTH)
    return  String.format(Locale.ROOT, "%04d-%02d-%02d", anioActual, mesActual + 1, diaActual)
}

class ViewModelImpresoraBlu : ViewModel() {
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    var dispositivos = mutableStateListOf<BluetoothDevice>()
    var isEscaneando by mutableStateOf(false)
    var isConectada by mutableStateOf(false)
    var dispositivoActual by mutableStateOf<BluetoothDevice?>(null)

    // UUID para comunicación SPP (Serial Port Profile)
    private val UUID_SPP: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")

    fun inicializar(context: Context) {
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        bluetoothAdapter = bluetoothManager?.adapter
    }

    fun escanearImpresora(context: Context) {
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter!!.isEnabled) {
            Toast.makeText(context, "Por favor, activa Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }

        isEscaneando = true
        dispositivos.clear()

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {
            val pairedDevices = bluetoothAdapter!!.bondedDevices

            val printers = mutableStateListOf<BluetoothDevice>()
            for (device in pairedDevices) {
                if (device.bluetoothClass.majorDeviceClass == BluetoothClass.Device.Major.IMAGING ||
                    device.name?.contains("printer", ignoreCase = true) == true ||
                    device.name?.contains("epson", ignoreCase = true) == true ||
                    device.name?.contains("zebra", ignoreCase = true) == true ||
                    device.uuids?.any { it.uuid.toString() == "00001101-0000-1000-8000-00805F9B34FB" } == true) {

                    printers.add(device)
                }
            }
            dispositivos.addAll(printers)
        } else {
            Toast.makeText(context, "Permiso Bluetooth denegado", Toast.LENGTH_SHORT).show()
        }

        isEscaneando = false
    }

    fun conectarImpresora(device: BluetoothDevice, context: Context, onConnected: () -> Unit) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permiso Bluetooth denegado", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            bluetoothAdapter?.cancelDiscovery()
            val socket = device.createRfcommSocketToServiceRecord(UUID_SPP)

            Thread {
                try {
                    socket.connect()
                    bluetoothSocket = socket
                    outputStream = socket.outputStream

                    isConectada = true
                    dispositivoActual = device

                    actualizarParametro(context, "macImpresora", device.address)

                    onConnected()
                } catch (e: IOException) {
                    e.printStackTrace()
                    try {
                        socket.close()
                    } catch (closeException: IOException) {
                        closeException.printStackTrace()
                    }
                    isConectada = false
                    dispositivoActual = null
                }
            }.start()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Error al conectar: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    fun reconectarUltimaImpresora(context: Context, onConnected: () -> Unit) {
        val lastDeviceAddress = obtenerParametro(context, "macImpresora")

        if (lastDeviceAddress == "0") {
            Toast.makeText(context, "No se ha agregado una impresora", Toast.LENGTH_SHORT).show()
            return
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permiso Bluetooth denegado", Toast.LENGTH_SHORT).show()
            return
        }
        inicializar(context)
        escanearImpresora(context)
        val impresora = dispositivos.find { it.address.toString() == lastDeviceAddress }

        if (impresora != null) {
            conectarImpresora(impresora, context, onConnected)
        } else {
            Toast.makeText(context, "No se encontró el último dispositivo guardado", Toast.LENGTH_SHORT).show()
        }
    }

    fun desconectarImpresora(context: Context) {
        actualizarParametro(context, "macImpresora", "0")
        try {
            outputStream?.close()
            bluetoothSocket?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            isConectada = false
            dispositivoActual = null
        }
    }

    fun imprimirTexto(text: String): Boolean {
        if (!isConectada || outputStream == null) {
            return false
        }

        return try {
            outputStream?.write(text.toByteArray())
            outputStream?.write("\n\n\n".toByteArray())
            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

}

val gestorImpresora = PrinterViewModel()

class PrinterViewModel : ViewModel() {
    var dispositivoActual by mutableStateOf<BluetoothConnection?>(null)
    var impresora: EscPosPrinter? = null
    var conexion: DeviceConnection? = null
    var nombre : String = "Desconocido"
    var listaImpresoras: Array<BluetoothConnection>? = BluetoothPrintersConnections().list
    var isConectada by mutableStateOf(false)
    private var bluetoothAdapter: BluetoothAdapter? = null

    // Parámetros predeterminados para la impresora
    private val printerDpi = 203 // Definir DPI (resolución) de la impresora
    private val printerWidthMM = 48f // Ancho de impresión en milímetros
    private val printerNbrCharactersPerLine = 32 // Número de caracteres por línea
    private val charset = EscPosCharsetEncoding("windows-1252", 16) // Codificación

    fun buscar(context: Context){
        val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
        bluetoothAdapter = bluetoothManager?.adapter
        if (bluetoothAdapter == null) {
            Toast.makeText(context, "Bluetooth no disponible", Toast.LENGTH_SHORT).show()
            return
        }

        if (!bluetoothAdapter!!.isEnabled) {
            Toast.makeText(context, "Por favor, activa Bluetooth", Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(context, "Buscando impresoras...", Toast.LENGTH_SHORT).show()
        listaImpresoras = BluetoothPrintersConnections().list
    }

    fun validarConexion(context: Context): Boolean{
        if (conexion?.isConnected == true) return true
        Toast.makeText(context, "Sin conexión a la impresora.", Toast.LENGTH_SHORT).show()
        return false
    }

    // Función para conectar a la primera impresora emparejada
    fun conectar(context: Context, mac: String, name: String){
        return if (conexion != null) {
            Toast.makeText(context, "Conectando impresora...", Toast.LENGTH_SHORT).show()
            try {
                // Crear la instancia de la impresora con los parámetros configurados
                impresora = EscPosPrinter(conexion!!, printerDpi, printerWidthMM, printerNbrCharactersPerLine, charset)
                isConectada = true
                actualizarParametro(context, "macImpresora", mac)
                Toast.makeText(context, "Conectado a $name",Toast.LENGTH_SHORT).show()
            } catch (e: EscPosConnectionException) {
                e.printStackTrace()
                Toast.makeText(context, "Error al conectar", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Ya hay una impreso conectada", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para imprimir texto
    fun imprimir(text: String, context: Context): Boolean {
        return if (impresora != null) {
            Toast.makeText(context, "Imprimiendo...", Toast.LENGTH_SHORT).show()
            try {
                impresora?.printFormattedText(text)
                Toast.makeText(context, "Impresión Exitosa!", Toast.LENGTH_SHORT).show()
                true
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(context, "Error al imprimir: ${e.printStackTrace()}", Toast.LENGTH_SHORT).show()
                false
            }
        } else {
            Toast.makeText(context, "Conecte una impresora.", Toast.LENGTH_SHORT).show()
            false
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun probar(context: Context, nombreEmpresa: String){
        var textoPrueba = ""
        try{
            val rutaImagen = obtenerParametro(context, clave = "$nombreEmpresa.png")
            val imagenHex = imagenAHexadecimal(rutaImagen, impresora)

            textoPrueba = if (imagenHex != null) {
                "[C]<img>$imagenHex</img>\n"
            } else {
                ""
            }
        }catch (e:Exception){
            Toast.makeText(context, "Error en impresión de Logo", Toast.LENGTH_SHORT).show()
        }
         textoPrueba += "[C]<font size='big'>Prueba</font>\n" +
                "[C]<font size='tall'>Calibrar</font>\n"+
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]32 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]36 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]40 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]44 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]48 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]52 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]56 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]60 Caracteres\n" +
                "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                "[C]64 Caracteres\n"
                "[L]\n" +
                "[C]<qrcode size='20'>https://soportereal.com</qrcode>\n"+
        imprimir(textoPrueba, context)
    }

    fun reconectar(context: Context){
        val lastDeviceAddress = obtenerParametro(context, "macImpresora")
        buscar(context)

        if (lastDeviceAddress == "0") {
            if (listaImpresoras?.isEmpty() == true) return Toast.makeText(context, "No se encontraron impresoras dispobibles.", Toast.LENGTH_SHORT).show()
            val primerImpresora = listaImpresoras?.first()
            if (primerImpresora != null) {
                conexion = primerImpresora
                dispositivoActual = primerImpresora
                conectar(context, primerImpresora.device.address, primerImpresora.device.name)
            } else {
                Toast.makeText(context, "Por favor, vincule una impresora.", Toast.LENGTH_SHORT).show()
            }
            return
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, "Permiso Bluetooth denegado", Toast.LENGTH_SHORT).show()
            return
        }
        val ultimaImpresora = listaImpresoras?.find { it.device.address.toString() == lastDeviceAddress }

        if (ultimaImpresora != null) {
            conexion = ultimaImpresora
            dispositivoActual = ultimaImpresora
            conectar(context, ultimaImpresora.device.address, ultimaImpresora.device.name)
        } else {
            Toast.makeText(context, "No se encontró el último dispositivo guardado", Toast.LENGTH_SHORT).show()
        }
    }

    // Función para desconectar la impresora
    fun deconectar(context: Context) {
        impresora?.disconnectPrinter()
        actualizarParametro(context, "macImpresora", "0")
        isConectada = false
        Toast.makeText(context, "Impresora deesconectada", Toast.LENGTH_SHORT).show()
    }
}

fun addTextBig(text: String, justification: String) : String {
    val lienas = segmentarTextoConEspacios(texto = text, largoLinea = 16)
    var textoRetornar = ""
    for(i in lienas){
        textoRetornar += "[$justification]<font size='big'>$i</font>\n"
    }
    return textoRetornar
}

fun addTextTall(text: String, justification: String, destacar: Boolean = false) : String {
    val lienas = segmentarTextoConEspacios(texto = text, largoLinea = 32)
    var textoRetornar = ""
    if (destacar){
        for(i in lienas){
            textoRetornar += "[$justification]<b><font size='tall'>$i</font></b>\n"
        }
        return textoRetornar
    }else{
        for(i in lienas){
            textoRetornar += "[$justification]<font size='tall'>$i</font>\n"
        }
        return textoRetornar
    }

}

fun addText(text: String, justification: String, destacar: Boolean = false) : String {
    val lienas = segmentarTextoConEspacios(texto = text, largoLinea = 32)
    var textoRetornar = ""
    return if (destacar) {
        for(i in lienas){
            textoRetornar += "[$justification]<b>$i</b>\n"
        }
        textoRetornar

    } else {
        for(i in lienas){
            textoRetornar += "[$justification]$i\n"
        }
        textoRetornar

    }
}

fun agregarLinea(ajustarATexto: Boolean = false, text: String = "", justification: String ="L"): String {
    var linea = ""
    val length = if (ajustarATexto) text.length else 32
    for (i in 0 until length) {
        linea += "-"
    }
    return "[$justification]$linea\n"
}

fun agregarDobleLinea(ajustarATexto: Boolean = false, text: String = "", justification: String ="L"): String {
    var linea = ""
    val length = if (ajustarATexto) text.length else 32
    for (i in 0 until length) {
        linea += "="
    }
    return "[$justification]$linea\n"
}

fun segmentarTextoConEspacios(texto: String, largoLinea: Int): List<String> {
    val lineas = mutableListOf<String>()
    var lineaActual = ""
    var espaciosIniciales = true

    for (palabra in texto.split("(?<= )|(?= )".toRegex())) { // Conserva los espacios en la segmentación
        if (espaciosIniciales && palabra.all { it == ' ' }) {
            lineaActual += palabra  // Mantener espacios iniciales
        } else {
            espaciosIniciales = false
            // Si la palabra cabe en la línea actual
            if (lineaActual.length + palabra.length <= largoLinea) {
                lineaActual += palabra
            } else {
                // Agregar línea completa y empezar una nueva
                lineas.add(lineaActual)
                lineaActual = palabra
            }
        }
    }

    // Añadir la última línea si no está vacía
    if (lineaActual.isNotEmpty()) {
        lineas.add(lineaActual)
    }

    return lineas
}

suspend fun descargarImagenSiNoExiste(context: Context, urlImagen: String, nombreArchivo: String) {
    val archivoDestino = File(context.filesDir, nombreArchivo)

    // Verifica si el archivo ya existe
    if (archivoDestino.exists()) {
        return
    }

    try {
        // Ejecutamos la descarga en un hilo de fondo
        withContext(Dispatchers.IO) {
            val url = URL(urlImagen)
            val inputStream: InputStream = url.openStream()

            inputStream.use { input ->
                FileOutputStream(archivoDestino).use { output ->
                    input.copyTo(output)
                }
            }
        }

        // Cuando la descarga termina, mostramos el Toast en el hilo principal
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Logo Descargado", Toast.LENGTH_SHORT).show()
        }

        // Guardar la ruta en un parámetro local si es necesario
        guardarParametroSiNoExiste(context, nombreArchivo, archivoDestino.absolutePath)

    } catch (e: Exception) {
        e.printStackTrace()

        // Mostramos el mensaje de error en el hilo principal
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Error en descarga Logo: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}

fun imagenAHexadecimal(rutaImagen: String, impresora: EscPosPrinter?): String? {
    val file = File(rutaImagen)
    if (!file.exists()) return null
    val bitmap: Bitmap = BitmapFactory.decodeFile(rutaImagen)
    return PrinterTextParserImg.bitmapToHexadecimalString(impresora,bitmap)
}













