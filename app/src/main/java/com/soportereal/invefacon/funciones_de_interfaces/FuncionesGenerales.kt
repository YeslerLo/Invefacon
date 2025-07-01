package com.soportereal.invefacon.funciones_de_interfaces

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
import com.soportereal.invefacon.R
import com.soportereal.invefacon.interfaces.modulos.facturacion.Cliente
import com.soportereal.invefacon.interfaces.modulos.facturacion.Empresa
import com.soportereal.invefacon.interfaces.modulos.facturacion.Factura
import com.soportereal.invefacon.interfaces.modulos.facturacion.VentaHija
import com.soportereal.invefacon.interfaces.modulos.facturacion.VentaMadre
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

fun formatearFechaTexto(fecha: String): String {
    val formatoEntrada = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    val fechaDate = formatoEntrada.parse(fecha)

    val formatoSalida = SimpleDateFormat("d MMM yyyy h:mm a", Locale("es", "ES"))
    return formatoSalida.format(fechaDate ?: Date()).replace("AM", "a. m.").replace("PM", "p. m.")
}

fun centrarTexto(texto: String,  context: Context): String{
    val cantidadPorLinea = obtenerParametroLocal(context,"cantidadCaracPorLineaImpre").toInt()
    val lienas = segmentarTextoConEspacios(texto = texto, largoLinea = cantidadPorLinea)
    var textoRetornar = ""
    for(i in lienas){
        for (a in 0 until (cantidadPorLinea - i.length)/2){
            textoRetornar += " "
        }
        textoRetornar += i+"\n"
    }
    return textoRetornar
}

fun obtenerFechaHoraActual(): String {
    val formato = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    return formato.format(Date())
}

fun deserializarFacturaHecha(resultadoFactura: JSONObject): Factura {

    val ventaMadreJson = resultadoFactura.getJSONObject("ventaMadre")
    val ventaMadre = VentaMadre(
        Id = ventaMadreJson.getString("Id"),
        Numero = ventaMadreJson.getString("Numero"),
        TipoDocumento = ventaMadreJson.getString("TipoDocumento"),
        Referencia = ventaMadreJson.getString("Referencia"),
        Estado = ventaMadreJson.getString("Estado"),
        Fecha = ventaMadreJson.getString("Fecha"),
        MonedaCodigo = ventaMadreJson.getString("MonedaCodigo"),
        MonedaTipoCambio = ventaMadreJson.getString("MonedaTipoCambio"),
        ClienteID = ventaMadreJson.getString("ClienteID"),
        ClienteNombre = ventaMadreJson.getString("ClienteNombre"),
        UsuarioCodigo = ventaMadreJson.getString("UsuarioCodigo"),
        AgenteCodigo = ventaMadreJson.getString("AgenteCodigo"),
        RefereridoCodigo = ventaMadreJson.getString("RefereridoCodigo"),
        Oficina = ventaMadreJson.getString("Oficina"),
        CajaNumero = ventaMadreJson.getString("CajaNumero"),
        FormaPagoCodigo = ventaMadreJson.getString("FormaPagoCodigo"),
        MedioPagoCodigo = ventaMadreJson.getString("MedioPagoCodigo"),
        MedioPagoDetalle = ventaMadreJson.getString("MedioPagoDetalle"),
        ModEntregaCodigo = ventaMadreJson.getString("ModEntregaCodigo"),
        DetallePide = ventaMadreJson.getString("DetallePide"),
        Detalle = ventaMadreJson.getString("Detalle"),
        TotalCosto = ventaMadreJson.getString("TotalCosto"),
        TotalVenta = ventaMadreJson.getString("TotalVenta"),
        TotalDescuento = ventaMadreJson.getString("TotalDescuento"),
        TotalMercGravado = ventaMadreJson.getString("TotalMercGravado"),
        TotalMercExonerado = ventaMadreJson.getString("TotalMercExonerado"),
        TotalMercExento = ventaMadreJson.getString("TotalMercExento"),
        TotalServGravado = ventaMadreJson.getString("TotalServGravado"),
        TotalServExonerado = ventaMadreJson.getString("TotalServExonerado"),
        TotalServExento = ventaMadreJson.getString("TotalServExento"),
        TotalImpuestoServicio = ventaMadreJson.getString("TotalImpuestoServicio"),
        TotalIva = ventaMadreJson.getString("TotalIva"),
        TotalIvaDevuelto = ventaMadreJson.getString("TotalIvaDevuelto"),
        Total = ventaMadreJson.getString("Total")
    )

    val ventaHijaJsonArray = resultadoFactura.getJSONArray("ventaHija")
    val ventaHija = (0 until ventaHijaJsonArray.length()).map { i ->
        val item = ventaHijaJsonArray.getJSONObject(i)
        VentaHija(
            ArticuloLineaId = item.getString("ArticuloLineaId"),
            Numero = item.getString("Numero"),
            TipoDocumento = item.getString("TipoDocumento"),
            ArticuloCodigo = item.getString("ArticuloCodigo"),
            ArticuloCabys = item.getString("ArticuloCabys"),
            ArticuloActividadEconomica = item.getString("ArticuloActividadEconomica"),
            ArticuloCantidad = item.getString("ArticuloCantidad"),
            ArticuloUnidadMedida = item.getString("ArticuloUnidadMedida"),
            ArticuloSerie = item.getString("ArticuloSerie"),
            ArticuloTipoPrecio = item.getString("ArticuloTipoPrecio"),
            ArticuloBodegaCodigo = item.getString("ArticuloBodegaCodigo"),
            ArticuloCosto = item.getString("ArticuloCosto"),
            ArticuloVenta = item.getString("ArticuloVenta"),
            ArticuloVentaSubTotal1 = item.getString("ArticuloVentaSubTotal1"),
            ArticuloDescuentoPorcentage = item.getString("ArticuloDescuentoPorcentage"),
            ArticuloDescuentoMonto = item.getString("ArticuloDescuentoMonto"),
            ArticuloVentaSubTotal2 = item.getString("ArticuloVentaSubTotal2"),
            ArticuloOtrosCargos = item.getString("ArticuloOtrosCargos"),
            ArticuloVentaSubTotal3 = item.getString("ArticuloVentaSubTotal3"),
            ArticuloIvaPorcentage = item.getInt("ArticuloIvaPorcentage"),
            ArticuloIvaTarifa = item.getString("ArticuloIvaTarifa"),
            ArticuloIvaExonerado = item.getString("ArticuloIvaExonerado"),
            ArticuloIvaMonto = item.getString("ArticuloIvaMonto"),
            ArticuloIvaDevuelto = item.getString("ArticuloIvaDevuelto"),
            ArticuloVentaGravado = item.getString("ArticuloVentaGravado"),
            ArticuloVentaExonerado = item.getString("ArticuloVentaExonerado"),
            ArticuloVentaExento = item.getString("ArticuloVentaExento"),
            ArticuloVentaTotal = item.getString("ArticuloVentaTotal"),
            nombreArticulo = item.getString("nombreArticulo")
        )
    }

    val empresaJson = resultadoFactura.getJSONObject("empresa")
    val empresa = Empresa(
        nombre = empresaJson.getString("nombre"),
        cedula = empresaJson.getString("cedula"),
        telefono = empresaJson.getString("telefono"),
        direccion = empresaJson.getString("direccion"),
        correo = empresaJson.getString("correo")
    )

    val clienteJson = resultadoFactura.getJSONObject("cliente")
    val cliente = Cliente(
        Id_Cliente = clienteJson.getString("Id_Cliente"),
        Nombre = clienteJson.getString("Nombre"),
        Telefonos = clienteJson.getString("Telefonos"),
        Direccion = clienteJson.getString("Direccion"),
        Fecha = clienteJson.getString("Fecha"),
        TipoPrecioVenta = clienteJson.getString("TipoPrecioVenta"),
        Cod_Tipo_Cliente = clienteJson.getString("Cod_Tipo_Cliente"),
        Email = clienteJson.getString("Email"),
        DiaCobro = clienteJson.getString("DiaCobro"),
        Contacto = clienteJson.getString("Contacto"),
        Exento = clienteJson.getString("Exento"),
        AgenteVentas = clienteJson.getString("AgenteVentas"),
        Cod_Estado = clienteJson.getString("Cod_Estado"),
        UltimaVenta = clienteJson.getString("UltimaVenta"),
        Cod_Zona = clienteJson.getString("Cod_Zona"),
        DetalleContrato = clienteJson.getString("DetalleContrato"),
        MontoContrato = clienteJson.getString("MontoContrato"),
        NoForzarCredito = clienteJson.getString("NoForzarCredito"),
        Descuento = clienteJson.getString("Descuento"),
        DivisionTerritorial = clienteJson.getString("DivisionTerritorial"),
        MontoCredito = clienteJson.getString("MontoCredito"),
        plazo = clienteJson.getString("plazo"),
        TieneCredito = clienteJson.getString("TieneCredito"),
        Cedula = clienteJson.getString("Cedula"),
        FechaNacimiento = clienteJson.getString("FechaNacimiento"),
        Cod_Moneda = clienteJson.getString("Cod_Moneda"),
        FechaVencimiento = clienteJson.getString("FechaVencimiento"),
        TipoIdentificacion = clienteJson.getString("TipoIdentificacion"),
        ClienteNombreComercial = clienteJson.getString("ClienteNombreComercial"),
        EmailFactura = clienteJson.getString("EmailFactura"),
        EmailCobro = clienteJson.getString("EmailCobro"),
        PorcentajeInteres = clienteJson.getString("PorcentajeInteres")
    )

    val clave = resultadoFactura.getString("clave")
    val leyenda = resultadoFactura.getString("leyenda")

    val factura = Factura(
        ventaMadre = ventaMadre,
        ventaHija = ventaHija,
        empresa = empresa,
        cliente = cliente,
        clave = clave,
        leyenda = leyenda,
        descrpcionFormaPago = resultadoFactura.getString("descrpcionFormaPago"),
        descripcionMedioPago = resultadoFactura.getString("descripcionMedioPago"),
        nombreAgente = resultadoFactura.getString("nombreAgente")
    )

    return factura
}

val gestorImpresora = ImpresoraViewModel()

class ImpresoraViewModel : ViewModel() {
    var dispositivoActual by mutableStateOf<BluetoothConnection?>(null)
    var impresora: EscPosPrinter? = null
    var conexion: DeviceConnection? = null
    var nombre : String = "Desconocido"
    var listaImpresoras by mutableStateOf<List<BluetoothConnection>>(emptyList())
    var isConectada by mutableStateOf(false)
    private var isPermisosOtorgados by mutableStateOf(false)

    // Parámetros predeterminados para la impresora
    private val printerDpi = 203 // Definir DPI (resolución) de la impresora
    private val printerWidthMM = 48f // Ancho de impresión en milímetros
    private var cantidadCaracPorLinea by mutableIntStateOf(32 )// Número de caracteres por línea
    private val charset = EscPosCharsetEncoding("windows-1252", 16) // Codificación

    @Composable
    fun PedirPermisos(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
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
        } else {
            isPermisosOtorgados = true // No requiere permisos en versiones anteriores
        }
    }

    suspend fun estaImpresoraConectadaRealmente(): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                if (impresora == null) return@withContext false
                // Enviar comando de estado ESC/POS (DLE EOT 1 o 4 dependiendo de la impresora)
                impresora?.printFormattedText("",0)
                true
            } catch (e: Exception) {
                false
            }
        }
    }

    fun ValidarPermisos(context: Context) {
        isPermisosOtorgados = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // En versiones anteriores no se requiere este permiso
        }
    }

    @SuppressLint("MissingPermission")
    fun buscar(context: Context) {
        ValidarPermisos(context)
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                if (!isPermisosOtorgados) {
                    Toast.makeText(
                        context,
                        "Active el permiso de Dispositivos cercanos en Permisos de la App.",
                        Toast.LENGTH_LONG
                    ).show()
                    return@withContext
                }
            }
            if (!isPermisosOtorgados) return@launch

            val bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as? BluetoothManager
            val bluetoothAdapter = bluetoothManager?.adapter

            if (bluetoothAdapter == null) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Bluetooth no disponible", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            if (!bluetoothAdapter.isEnabled) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Por favor, activa Bluetooth", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            val dispositivosEmparejados = bluetoothAdapter.bondedDevices?.toList() ?: emptyList()

            if (dispositivosEmparejados.isEmpty()) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "No hay dispositivos emparejados.", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }
            val conexionesBluetooth = dispositivosEmparejados.map { device ->
                BluetoothConnection(device)
            }
            listaImpresoras = conexionesBluetooth

            if (listaImpresoras.isNotEmpty()) return@launch

            withContext(Dispatchers.Main) {
                Toast.makeText(context, "No se encontraron dispositivos...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    suspend fun validarConexion(context: Context): Boolean {
        ValidarPermisos(context)

        if (!isPermisosOtorgados) {
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    context,
                    "El app no cuenta con permisos para imprimir.",
                    Toast.LENGTH_LONG
                ).show()
            }
            return false
        }

        val conectado = estaImpresoraConectadaRealmente()

        return conectado
    }

    fun conectar(context: Context, mac: String, name: String) {
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return
        viewModelScope.launch(Dispatchers.IO) {
            if (conexion != null) {
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
            }catch (e: Exception) {
                println("NO SE IMPRIMIO EL LOGO")
            }
            textoPrueba += "[C]<font size='big'>Prueba</font>\n" +
                    "[C]<font size='tall'>Calibrar</font>\n"+
                    "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                    "[C]32 Caracteres\n" +
                    "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                    "[C]36 Caracteres\n" +
                    "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                    "[C]40 Caracteres\n" +
                    "[L]-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n" +
                    "[C]42 Caracteres\n" +
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

    fun reconectar(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isPermisosOtorgados) return@launch
            val isConectado = estaImpresoraConectadaRealmente()
            if (isConectado) return@launch
            val lastDeviceAddress = obtenerParametroLocal(context, "macImpresora")
            buscar(context)
            delay(1000)
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Permiso Bluetooth denegado", Toast.LENGTH_SHORT).show()
                }
                return@launch
            }

            if (lastDeviceAddress == "0") {
                if (listaImpresoras.isEmpty()) return@launch
                val primerImpresora = BluetoothPrintersConnections().list?.first()
                if (primerImpresora == null){
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "No se encontraron impresoras...", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }
                conexion = primerImpresora
                dispositivoActual = primerImpresora
                conectar(context, primerImpresora.device.address, primerImpresora.device.name)
                return@launch
            }

            val ultimaImpresora = listaImpresoras.find { it.device.address.toString() == lastDeviceAddress }

            if (ultimaImpresora != null) {
                conexion = ultimaImpresora
                dispositivoActual = ultimaImpresora
                conectar(context, ultimaImpresora.device.address, ultimaImpresora.device.name)
            }
        }
    }

    // Función para desconectar la impresora
    fun deconectar(context: Context) {
        ValidarPermisos(context)
        if (!isPermisosOtorgados) return
        viewModelScope.launch(Dispatchers.IO) {
            impresora?.disconnectPrinter()
            impresora = null
            isConectada = false
            actualizarParametro(context, "macImpresora", "0")
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Impresora desconectada", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


suspend fun imprimirFactura(
    factura: Factura,
    context: Context,
    nombreEmpresa : String,
    tipoDoc : String, // 1-ORIGINAL 2-COPIA 3-REIMPRESION 4-PRORFORMA 5-NOTA CREDITO 6-NOTA DEBITO
    numeroEnCola : String,
    usuario: String
) : Boolean {
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
    facturaTexto += addTextTall("DOC: ${factura.ventaMadre.Numero}", "C", context = context)
    if (tipoDoc in listOf("5", "6")) facturaTexto += addTextTall("REF: ${factura.ventaMadre.Referencia}", "C", context = context)
    val tipoDocumento = if(tipoDoc == "2") "COPIA $numeroEnCola" else if(tipoDoc == "3") "REIMPRESION" else "ORIGINAL"
    facturaTexto += addTextTall(
        when(tipoDoc){
            "4"-> ""
            "5" -> "NOTA CREDITO ELECTRONICA ${factura.descripcionMedioPago.uppercase(Locale.ROOT)} $tipoDocumento"
            "6" -> "NOTA DEBITO ELECTRONICA ${factura.descripcionMedioPago.uppercase(Locale.ROOT)} $tipoDocumento"
            else -> "FACTURA ELECTRONICA ${factura.descripcionMedioPago.uppercase(Locale.ROOT)} $tipoDocumento"
        },
        "C",
        destacar = true,
        context = context
    )
    if (tipoDoc != "4"){
        facturaTexto += addText("CLAVE: ${factura.clave.substring(0, 25)}", "C", context = context)
        facturaTexto += addText("       ${factura.clave.substring(25, 50)}", "C", context = context)
    }else{
        facturaTexto += addTextBig("PROFORMA", "C", context = context)
    }

    facturaTexto += addText("FECHA: ${factura.ventaMadre.Fecha}", "L", context = context)
    if (obtenerValorParametroEmpresa("20", "0") == "1" && tipoDoc != "4") facturaTexto += addTextTall("FORMA PAGO: ${factura.descrpcionFormaPago.uppercase(Locale.ROOT)}", "C", context = context)
    if (factura.cliente.Cedula.isNotEmpty())facturaTexto += addText("CEDULA: ${factura.cliente.Cedula} COD: ${factura.cliente.Id_Cliente} ", "L", context = context)
    facturaTexto += addText("NOMBRE: ${factura.cliente.Nombre}", "L", context = context)
    if (factura.cliente.EmailFactura.isNotEmpty()) facturaTexto += addText("EMAIL: ${factura.cliente.EmailFactura}", "L", context = context)
    if (factura.cliente.Telefonos.isNotEmpty()) facturaTexto += addText("TELEFONO: ${factura.cliente.Telefonos}", "L", context = context)
    if (factura.cliente.Direccion.isNotEmpty()) facturaTexto += addText("DIRECCION: ${factura.cliente.Direccion}", "L", context = context)
    facturaTexto += addTextTall("DETALLE", "C", context = context)
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
        val ivaTemp = listaIvas.find { it.clave == articulo.ArticuloIvaPorcentage.toString() }
        if ( ivaTemp != null){
            ivaTemp.valor = (ivaTemp.valor.toDouble() + articulo.ArticuloIvaMonto.toDouble()).toString()
            ivaTemp.venta = (ivaTemp.venta.toDouble() + articulo.ArticuloVentaGravado.toDouble()).toString()
        }else{
            listaIvas.add(ParClaveValor(clave = articulo.ArticuloIvaPorcentage.toString(), valor = articulo.ArticuloIvaMonto, venta = articulo.ArticuloVentaGravado))
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
    if (tipoDoc != "4") facturaTexto += addText("CAJA: ${factura.ventaMadre.CajaNumero} VEND: ${factura.nombreAgente}", "C", context = context)
    if (tipoDoc != "4") facturaTexto += addText(factura.ventaMadre.MedioPagoDetalle.replace("\r", "").uppercase(Locale.ROOT), "C", context = context, conLiena = true)
    if (tipoDoc != "4") facturaTexto += addText(obtenerValorParametroEmpresa("137", "GRACIAS POR SU COMPRA!"), "C", context = context)
    if (tipoDoc != "4") facturaTexto += addText(factura.leyenda, "C", context = context)
    facturaTexto += addText("Usu: $usuario", "C", context = context)
    facturaTexto += addText("INVEFACON ANDROID V.${context.getString(R.string.app_version)}", "C", context = context, destacar = true)
    val isImpreso = gestorImpresora.imprimir(text = facturaTexto, context)
    return isImpreso
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
        mostrarMensajeError("EL PARAMETRO $codParametro NO SE LOGRO ENCONTRAR")
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

suspend fun validarVersionApp(context : Context){
    val resultAppVersion = obtenerUltimaVersionApp()
    if (resultAppVersion == null) {
        mostrarMensajeError("Se obtuvo un null a la hora de obtener la versión del App.")
        return
    }
    if (!validarExitoRestpuestaServidor(resultAppVersion)) {
        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = resultAppVersion)
        return
    }
    val versionLocal = context.getString(R.string.versionCode)
    gestorDialogActualizacion.cambiarEstado(versionLocal.toInt() > resultAppVersion.optInt("versionCodeMax", 25) || versionLocal.toInt() < resultAppVersion.getInt("versionCodeMin") )
}

suspend fun obtenerUltimaVersionApp():JSONObject?{
    val objetoFuncionesHttpInvefacon = FuncionesHttp()
    val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
        .addFormDataPart("0","")
        .addFormDataPart("0","")
        .build()
    return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "seguridad/obtenerVersionAndroid.php", validarJson = false)
}

@Composable
fun DialogoActualizacion() {
    val mostrar by gestorDialogActualizacion.pedirActualizacion.collectAsState()
    val context = LocalContext.current

    if (!mostrar) return

    val fondoGrisOscuro = Color(0xFF2C2C2C)

    var isMenuVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(100)
        isMenuVisible = true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(enabled = false) {},
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 4.dp, vertical = 8.dp)
                    .align(Alignment.Center),
                shape = RoundedCornerShape(12.dp),
                color = fondoGrisOscuro,
                shadowElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Actualización disponible",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "La aplicación requiere la última versión para funcionar correctamente.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )

                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW).apply {
                                data = Uri.parse("https://play.google.com/store/apps/details?id=${context.packageName}")
                            }
                            context.startActivity(intent)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fondoGrisOscuro,
                            contentColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.White)
                    ) {
                        Text("Actualizar")
                    }
                }
            }
        }
    }
}

class EstadoDialogActualizacion : ViewModel() {
    private val _pedirActualizacion = MutableStateFlow(false)

    val pedirActualizacion: StateFlow<Boolean> = _pedirActualizacion

    fun cambiarEstado(actualizar: Boolean) {
        _pedirActualizacion.value = actualizar
    }
}

val gestorDialogActualizacion = EstadoDialogActualizacion()




















