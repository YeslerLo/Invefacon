package com.soportereal.invefacon.funciones_de_interfaces

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dantsu.escposprinter.EscPosCharsetEncoding
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.DeviceConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.exceptions.EscPosConnectionException
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.util.Calendar
import java.util.Locale
import java.util.regex.Pattern

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
    try {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE)

        if (!sharedPreferences.contains(clave)) {
            sharedPreferences.edit().putString(clave, valor).apply()
            Log.d("guardarParametro", "Parametro guardado: $clave = $valor")
        } else {
            Log.d("guardarParametro", "Parametro ya existe: $clave")
        }
    } catch (e: Exception) {
        Log.e("guardarParametro", "Error al guardar parametro: ${e.message}")
    }
}

fun obtenerParametroLocal(context: Context, clave: String, valorPorDefecto: String = "0"): String {
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

fun separacionDeMiles(montoDouble:Double= 0.00, montoInt: Int = 0, montoString: String = "", isDouble: Boolean= true, isString: Boolean = false): String{
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

val gestorImpresora = ImpresoraViewModel()

class ImpresoraViewModel : ViewModel() {
    var dispositivoActual by mutableStateOf<BluetoothConnection?>(null)
    var impresora: EscPosPrinter? = null
    var conexion: DeviceConnection? = null
    var nombre : String = "Desconocido"
    var listaImpresoras by mutableStateOf<List<BluetoothConnection>>(emptyList())
        private set
    var isConectada by mutableStateOf(false)
    private var bluetoothAdapter: BluetoothAdapter? = null
    private var isPermisosOtorgados by mutableStateOf(false)

    // Parámetros predeterminados para la impresora
    private val printerDpi = 203 // Definir DPI (resolución) de la impresora
    private val printerWidthMM = 48f // Ancho de impresión en milímetros
    private var cantidadCaracPorLinea by mutableIntStateOf(32 )// Número de caracteres por línea
    private val charset = EscPosCharsetEncoding("windows-1252", 16) // Codificación

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun PedirPermisos(context: Context) {
        val isPermisosOtorgadosTemp by remember {
            mutableStateOf(
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.BLUETOOTH_CONNECT
                ) == PackageManager.PERMISSION_GRANTED
            )
        }

        isPermisosOtorgados = isPermisosOtorgadosTemp

        val permisosLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permisos ->
            isPermisosOtorgados = permisos[Manifest.permission.BLUETOOTH_CONNECT] == true

            if (!isPermisosOtorgados) {
                Toast.makeText(
                    context,
                    "Permiso denegado: no se puede usar la impresora por Bluetooth.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        LaunchedEffect(Unit) {
            if (!isPermisosOtorgados) {
                permisosLauncher.launch(
                    arrayOf(
                        Manifest.permission.BLUETOOTH_CONNECT,
                        Manifest.permission.BLUETOOTH_SCAN
                    )
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun ValidarPermisos(context: Context){
        isPermisosOtorgados = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.BLUETOOTH_CONNECT
        ) == PackageManager.PERMISSION_GRANTED
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun buscar(context: Context) {
        ValidarPermisos(context)
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                if (!isPermisosOtorgados) return@withContext Toast.makeText(
                    context,
                    "Active el permiso de Dispositivos cercanos en Permisos de la App.",
                    Toast.LENGTH_LONG
                ).show()
            }
            if (!isPermisosOtorgados) return@launch

            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
            bluetoothAdapter = bluetoothManager?.adapter
            if (bluetoothAdapter == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Bluetooth no disponible", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            if (!bluetoothAdapter!!.isEnabled) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Por favor, activa Bluetooth", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Buscando impresoras...", Toast.LENGTH_SHORT).show()
            }

            val impresoras = BluetoothPrintersConnections().list
            listaImpresoras = impresoras?.toList() ?: emptyList()
            delay(1000)
            if (listaImpresoras.isEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No se encontraron impresoras disponibles.", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            withContext(Dispatchers.Main) {
                if (listaImpresoras.size==1) return@withContext Toast.makeText(context, "Se encontró ${listaImpresoras.size} impresora", Toast.LENGTH_SHORT).show()

                Toast.makeText(context, "Se encontraron ${listaImpresoras.size} impresoras", Toast.LENGTH_SHORT).show()
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    suspend fun validarConexion(context: Context): Boolean {
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return withContext(Dispatchers.Main) {
            Toast.makeText(
                context,
                "El app no cuenta con permisos para imprimir.",
                Toast.LENGTH_LONG
            ).show()
            false
        }
        return withContext(Dispatchers.Main) {
            if (conexion?.isConnected == false){
                Toast.makeText(
                    context,
                    "No hay ninguna impresora conectada.",
                    Toast.LENGTH_LONG
                ).show()
            }
            conexion?.isConnected == true
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun conectar(context: Context, mac: String, name: String) {
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return
        viewModelScope.launch(Dispatchers.IO) {
            if (conexion != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Conectando impresora...", Toast.LENGTH_SHORT).show()
                }
                try {
                    cantidadCaracPorLinea = obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt()
                    // Crear la instancia de la impresora con los parámetros configurados
                    impresora = EscPosPrinter(conexion!!, printerDpi, printerWidthMM, cantidadCaracPorLinea, charset)
                    isConectada = true

                    actualizarParametro(context, "macImpresora", mac)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Conectado a $name", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: EscPosConnectionException) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al conectar", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Ya hay una impresora conectada", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Función para imprimir texto
    @RequiresApi(Build.VERSION_CODES.S)
    suspend fun imprimir(text: String, context: Context): Boolean {
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return false
        return withContext(Dispatchers.IO) {
            if (impresora != null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Imprimiendo...", Toast.LENGTH_SHORT).show()
                }
                try {
                    impresora?.printFormattedText(text)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Impresión Exitosa!", Toast.LENGTH_SHORT).show()
                    }
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Error al imprimir: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                    false
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Conecte una impresora.", Toast.LENGTH_SHORT).show()
                }
                false
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("SuspiciousIndentation")
    fun probar(context: Context, nombreEmpresa: String){
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return
        viewModelScope.launch(Dispatchers.IO) {
            var textoPrueba = ""
            try{
                val rutaImagen = obtenerParametroLocal(context, clave = "$nombreEmpresa.jpg")
                val imagenHex = imagenAHexadecimal(rutaImagen, impresora) ?: "0"

                textoPrueba = if (imagenHex != "0") {
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
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun reconectar(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val lastDeviceAddress = obtenerParametroLocal(context, "macImpresora")
            buscar(context)
            if (!isPermisosOtorgados) return@launch
            delay(1000)
            if (lastDeviceAddress == "0") {
                if (listaImpresoras.isEmpty()) return@launch
                val primerImpresora = listaImpresoras.first()
                conexion = primerImpresora
                dispositivoActual = primerImpresora
                conectar(context, primerImpresora.device.address, primerImpresora.device.name)
                return@launch
            }

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Permiso Bluetooth denegado", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val ultimaImpresora = listaImpresoras.find { it.device.address.toString() == lastDeviceAddress }

            if (ultimaImpresora != null) {
                conexion = ultimaImpresora
                dispositivoActual = ultimaImpresora
                conectar(context, ultimaImpresora.device.address, ultimaImpresora.device.name)
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No se encontró el último dispositivo guardado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Función para desconectar la impresora
    @RequiresApi(Build.VERSION_CODES.S)
    fun deconectar(context: Context) {
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return
        viewModelScope.launch(Dispatchers.IO) {
            impresora?.disconnectPrinter()
            isConectada = false
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Impresora desconectada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun addTextBig(text: String, justification: String, context: Context) : String {
    val lienas = segmentarTextoConEspacios(texto = text, largoLinea = obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt()/2)
    var textoRetornar = ""
    for(i in lienas){
        textoRetornar += "[$justification]<font size='big'>$i</font>\n"
    }
    return textoRetornar
}

fun addTextTall(text: String, justification: String, destacar: Boolean = false, context: Context) : String {
    val lienas = segmentarTextoConEspacios(texto = text, largoLinea = obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt())
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

fun addText(text: String, justification: String, destacar: Boolean = false, context: Context, conLiena: Boolean = false) : String {
    val lienas = segmentarTextoConEspacios(texto = text, largoLinea = obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt())
    var textoRetornar = ""
    return if (destacar) {
        if (conLiena){
            for(i in lienas){
                textoRetornar += "[$justification]<u><b>$i</b></u>\n"
            }
            textoRetornar
        }else{
            for(i in lienas){
                textoRetornar += "[$justification]<b>$i</b>\n"
            }
            textoRetornar
        }
    } else {
        if (conLiena){
            for(i in lienas){
                textoRetornar += "[$justification]<u>$i</u>\n"
            }
            textoRetornar
        }else{
            for(i in lienas){
                textoRetornar += "[$justification]$i\n"
            }
            textoRetornar
        }
    }
}

fun agregarLinea(ajustarATexto: Boolean = false, text: String = "", justification: String ="L", context: Context): String {
    var linea = ""
    val length = if (ajustarATexto) text.length else obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt()
    for (i in 0 until length) {
        linea += "-"
    }
    return "[$justification]$linea\n"
}

fun agregarDobleLinea(ajustarATexto: Boolean = false, text: String = "", justification: String ="L", context: Context): String {
    var linea = ""
    val length = if (ajustarATexto) text.length else obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt()
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

fun tienePermiso(codPermiso:String): Boolean{
    return listaPermisos.find { it.clave == codPermiso } != null
}

fun obtenerValorParametroEmpresa(codParametro: String, valorAuxiliar : String): String{
    val parametro = listaParametros.find { it.clave == codParametro }
    return if(parametro !=null){
        parametro.valor
    }else{
        mostrarMensajeError("EL PARAMETRO $codParametro NO SE LOGRO ECONTRAR")
        valorAuxiliar
    }
}

var listaPermisos: List<ParClaveValor> = listOf()

var listaParametros : List<ParClaveValor> = listOf()

fun validarCorreo(correo: String): Boolean {
    val estructuraParaValidacionCorreo = "^([A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{2,})(\\s*;\\s*[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{2,})*\$"
    val objetoValidadorCorreo = Pattern.compile(estructuraParaValidacionCorreo)
    return if (objetoValidadorCorreo.matcher(correo).matches()) {
        true
    } else {
        mostrarMensajeError("El Correo Electrónico ingresado no es válido.")
        false
    }
}


fun validacionCedula(tipo: String, cedula : String):Boolean{
    if(cedula.isEmpty()){
        mostrarMensajeError("Ingrese un numero de Cédula")
        return false
    }
    if(tipo=="01"){
        if (cedula.length!=9){
            mostrarMensajeError("El número de cédula ingresado no cumple con los requisitos de una cédula física válida.")
            return false
        }
    }
    if(tipo=="02"){
        if (cedula.length!=10){
            mostrarMensajeError("El número de cédula ingresado no cumple con los requisitos de una cédula jurídica válida.")
            return false
        }
    }
    if(tipo=="03"){
        if (cedula.length != 11 && cedula.length != 12){
            mostrarMensajeError("El número de cédula ingresado no cumple con los requisitos de una cédula Dimex válida.")
            return false
        }
    }
    if(tipo=="04"){
        if (cedula.length!=10){
            mostrarMensajeError("El número de cédula ingresado no cumple con los requisitos de una cédula Nite válida.")
            return false
        }
    }
    return true
}














