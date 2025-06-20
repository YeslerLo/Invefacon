package com.soportereal.invefacon.interfaces.modulos.facturacion

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Percent
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.RequestQuote
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.ArticuloDb
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.ButtonFecha
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.GestorTablaArticulos
import com.soportereal.invefacon.funciones_de_interfaces.MenuConfirmacion
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.TextFieldMultifuncional
import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
import com.soportereal.invefacon.funciones_de_interfaces.deserializarFacturaHecha
import com.soportereal.invefacon.funciones_de_interfaces.gestorImpresora
import com.soportereal.invefacon.funciones_de_interfaces.guardarParametroSiNoExiste
import com.soportereal.invefacon.funciones_de_interfaces.listaParametros
import com.soportereal.invefacon.funciones_de_interfaces.listaPermisos
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeExito
import com.soportereal.invefacon.funciones_de_interfaces.mostrarTeclado
import com.soportereal.invefacon.funciones_de_interfaces.obtenerDatosClienteByCedula
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloLabelBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerFechaHoraActual
import com.soportereal.invefacon.funciones_de_interfaces.obtenerFechaHoy
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
import com.soportereal.invefacon.funciones_de_interfaces.obtenerValorParametroEmpresa
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.tienePermiso
import com.soportereal.invefacon.funciones_de_interfaces.validacionCedula
import com.soportereal.invefacon.funciones_de_interfaces.validarCorreo
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.funciones_de_interfaces.validarVersionApp
import com.soportereal.invefacon.interfaces.modulos.clientes.Cliente
import com.soportereal.invefacon.interfaces.modulos.clientes.ProcesarDatosModuloClientes
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.concurrent.atomic.AtomicInteger

@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun IniciarInterfazFacturacion(
    token: String,
    navController: NavController,
    nombreEmpresa: String,
    codUsuario: String,
    nombreUsuario: String
){
    val context = LocalContext.current
    val gestorTablaArticulos: GestorTablaArticulos = viewModel()
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val focusManager = LocalFocusManager.current
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var expandedClientes by remember { mutableStateOf(false) }
//    var expandedArticulos by remember { mutableStateOf(false) }
    var expandedTotales by remember { mutableStateOf(false) }
    val listaArticulosSeleccionados = remember { mutableStateListOf<ArticuloFacturacion>() }
    var listaPagos = remember { mutableStateListOf<Pago>() }
    val listaMediosPago = remember { mutableStateListOf<ParClaveValor>() }
    val listaImpresion = remember { mutableStateListOf<ParClaveValor>() }
    var listaArticulosProforma by remember {  mutableStateOf<List<ArticuloFacturacion>>(emptyList()) }
    var nombreFactura by remember { mutableStateOf("") }
    var nombreCliente by remember { mutableStateOf("") }
    var descuentoCliente by remember { mutableStateOf("0.00") }
    var tipoPrecioCliente by remember { mutableStateOf("") }
    var tipoCedula by remember { mutableStateOf("") }
    var numeroCedula by remember { mutableStateOf("") }
    var emailGeneral by remember { mutableStateOf("") }
    var nombreComercial by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefonos by remember { mutableStateOf("") }
    var plazoCredito by remember { mutableStateOf("") }
    var montoCredito by remember { mutableStateOf("") }
    var montoContrato by remember { mutableStateOf("") }
    var fechaProforma by remember { mutableStateOf("") }
    var totalGravado by remember { mutableDoubleStateOf(0.00) }
    var totalIva by remember { mutableDoubleStateOf(0.00) }
    var totalDescuento by remember { mutableDoubleStateOf(0.00) }
    var totalPago by remember { mutableDoubleStateOf(0.00) }
    var totalExonerado by remember { mutableDoubleStateOf(0.00) }
    var totalIvaDevuelto by remember { mutableDoubleStateOf(0.00) }
    var totalMercGrav by remember { mutableDoubleStateOf(0.00) }
    var total by remember { mutableDoubleStateOf(0.00) }
    var tasaCambio by remember { mutableDoubleStateOf(504.21) }
    var codMonedaCliente by remember { mutableStateOf("") }
    var codMonedaProforma by remember { mutableStateOf("") }
    var numeroProforma by remember { mutableStateOf("") }
    var tipoDocumento by remember { mutableStateOf("") }
    var clienteId by remember { mutableStateOf("") }
    var estadoProforma by remember { mutableStateOf("") }
    var isCrearProforma by remember { mutableStateOf(false) }
    var detalleProforma by remember { mutableStateOf("") }
    var ordenCompra by remember { mutableStateOf("") }
    val objectoProcesadorDatosApi = ProcesarDatosModuloFacturacion(token)
    val objectoProcesadorDatosApiCliente = ProcesarDatosModuloClientes(token)
    var isCargandoDatos by remember { mutableStateOf(true) }
    var iniciarDescargaArticulos by remember { mutableStateOf(false) }
    var iniciarMenuAgregaEditaArticulo by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarArticulo by remember { mutableStateOf(false) }
    var actualizarDatosProforma by remember { mutableStateOf(true) }
    var apiConsultaProforma by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiProforma= CoroutineScope(Dispatchers.IO)
    var apiConsultaArticulos by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiArticulos= CoroutineScope(Dispatchers.IO)
    var apiConsultaBusquedaClientes by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiBusquedaClientes= CoroutineScope(Dispatchers.IO)
    var errorCargarProforma by remember { mutableStateOf(false) }
    var simboloMoneda by remember { mutableStateOf("") }
    val listaIvas = remember { mutableStateListOf<ParClaveValor>() }
    var listaArticulosEncontrados by remember { mutableStateOf(emptyList<ArticuloFacturacion>()) }
    var listaClientesEncontrados by remember { mutableStateOf(emptyList<ClienteFacturacion>()) }
    var datosIngresadosBarraBusquedaArticulos by remember { mutableStateOf("") }
    var isCargandoClientes by remember { mutableStateOf(false) }
    var isCargandoArticulos by remember { mutableStateOf(false) }
    var agregarEditarArticuloActual by remember { mutableStateOf(false) }
    var articuloLineaProforma by remember { mutableStateOf(ArticuloLineaProforma()) }
    var isAgregar by remember { mutableStateOf(false) }
    var articuloActual by remember { mutableStateOf(ArticuloFacturacion()) }
    var validacionCargaFinalizada by remember { mutableIntStateOf(0) } // si es '2' las dos peticiones al api ya finalizaron
    var iniciarMenuConfirmacionSalidaModulo by remember { mutableStateOf(false) }
    var eliminarLinea by remember { mutableStateOf(false) }
    var lineaAcual by remember { mutableStateOf("") }
    var soloActualizarArticulos by remember { mutableStateOf(false) }
    var soloActualizarDatosCliente by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarCliente by remember { mutableStateOf(false) }
    var datosIngresadosBarraBusquedaCliente by remember { mutableStateOf("") }
    var cambiarClienteProforma by remember { mutableStateOf(false) }
    var clienteSeleccionado by remember { mutableStateOf(ClienteFacturacion()) }
    var iniciarMenuSeleccionarProforma by remember { mutableStateOf(false) }
    var iniciarMenuClonarProforma by remember { mutableStateOf(false) }
    var nuevoCodigoMoneda by remember { mutableStateOf("") }
    var estadoBusquedaProforma by remember { mutableStateOf("") }
    var nombreClienteBusquedaProforma by remember { mutableStateOf("") }
    var listaProforma by remember { mutableStateOf<List<Proforma>>(emptyList()) }
    var isCargandoProformas by remember { mutableStateOf(false) }
    var fechaInicialProforma by remember { mutableStateOf( obtenerFechaHoy()) }
    var fechafinalProforma by remember { mutableStateOf( obtenerFechaHoy()) }
    var buscarProformas by remember { mutableStateOf(false) }
    var clonarProforma by remember { mutableStateOf(false) }
    var apiBusquedaProforma by remember { mutableStateOf<Job?>(null) }
    var apiGuardarDetalle by remember { mutableStateOf<Job?>(null) }
    var apiGuardarOrdenCompra by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiBusquedaProforma = CoroutineScope(Dispatchers.IO)
    var guardarProformaBorrador by remember { mutableStateOf(false) }
    var iniciarMenuOpcionesProforma by remember { mutableStateOf(false) }
    var iniciarMenuAplicarDescuento by remember { mutableStateOf(false) }
    var iniciarMenuCambiarMoneda by remember { mutableStateOf(false) }
    var iniciarMenuCambiarPrecio by remember { mutableStateOf(false) }
    var cambiarMoneda by remember { mutableStateOf(false) }
    var nuevoTipoPrecio by remember { mutableStateOf("") }
    var isFormaPagoAgregada by remember { mutableStateOf(false) }
    var isSoloAgregarFormaPago by remember { mutableStateOf(false) }
    var aplicarDescuento by remember { mutableStateOf(false) }
    var nuevoPorcentajeDescuento by remember { mutableStateOf("0") }
    var iniciarMenuConfExoneracion by remember { mutableStateOf(false) }
    var iniciarMenuConfPagoCredito by remember { mutableStateOf(false) }
    var iniciarMenuConfPagoCompleto by remember { mutableStateOf(false) }
    var iniciarMenuConfComoProforma by remember { mutableStateOf(false) }
    var iniciarMenuActuNombreProforma by remember { mutableStateOf(false) }
    var iniciarMenuConfEliminarArt by remember { mutableStateOf(false) }
    var exonerar by remember { mutableStateOf(false) }
    var iniciarMenuConfQuitarExoneracion by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarMedioPago by remember { mutableStateOf(false) }
    var iniciarMenuProcesar by remember { mutableStateOf(false) }
    var quitarExoneracion by remember { mutableStateOf(false) }
    var guardarProforma by remember { mutableStateOf(false) }
    val listaMonedas by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("CRC", "CRC"),
                ParClaveValor("USD", "USD"),
                ParClaveValor("EUR", "EUR")
            )
        )
    }
    val listaTipoCedula by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("00", "No definido"),
                ParClaveValor("01", "Física"),
                ParClaveValor("02", "Jurídica"),
                ParClaveValor("03", "Dimex"),
                ParClaveValor("04", "Nite")
            )
        )
    }
    val listaTipoPrecios by remember {
        mutableStateOf(
            (1..10).map { ParClaveValor(it.toString(), it.toString()) }
        )
    }
    var estadoProformaInicial by remember { mutableStateOf("") }
    var agregarFormapago by remember { mutableStateOf(false) }
    var tipoPago by remember { mutableStateOf("") }
    var tasaCambioDolar by remember { mutableDoubleStateOf(0.00) }
    var tipoFormaProcesar by remember { mutableStateOf("factura") }
    var correoProformaTemp by remember { mutableStateOf("") }
    var iniciarPantallaEstadoImpresion by remember { mutableStateOf(false) }
    var isImprimiendo by remember { mutableStateOf(false) }
    var exitoImpresion by remember { mutableStateOf(true) }
    var imprimir by remember { mutableStateOf(false) }
    var datosFacturaEmitida by remember { mutableStateOf(Factura()) }
    var obtenerDatosFacturaEmitida by remember { mutableStateOf(false) }
    var consecutivoFactura by remember { mutableStateOf("") }
    val valorImpresionActiva by remember { mutableStateOf( obtenerParametroLocal(context, "isImpresionActiva$codUsuario$nombreEmpresa")) }
    var iniciarMenuOpcionesCliente by remember { mutableStateOf(false) }
    var iniciarMenuAgregaEditaCliente by remember { mutableStateOf(false) }
    var isEditarCliente by remember { mutableStateOf(false) }
    var clienteTemp by remember { mutableStateOf(Cliente(Cod_Zona = "1", AgenteVentas = codUsuario, noForzaCredito = "0", TipoPrecioVenta = "1", TipoIdentificacion = "01")) }
    var agregarNuevoCliente by remember { mutableStateOf(false) }
    var editarCliente by remember { mutableStateOf(false) }
    var guardarDetalleFactura by remember { mutableStateOf(false) }
    var guardarOrdenCompra by remember { mutableStateOf(false) }
    var actualizarNombreProforma by remember { mutableStateOf(false) }
    var nombreProformaTemp by remember { mutableStateOf("") }
    var iniciarBusquedaClienteByCedula by remember { mutableStateOf(false) }
    val listaArticulos by gestorTablaArticulos.articulos.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val coroutineScopeAgreEditArt = rememberCoroutineScope()
    val transition = rememberInfiniteTransition(label = "shimmer")
    val shimmerTranslate by transition.animateFloat(
        initialValue = -800f,
        targetValue = 1100f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    val brush = Brush.linearGradient(
        colors = listOf(
            Color.Black.copy(alpha = 0.1f),
            Color.Gray.copy(alpha = 0.4f),
            Color.Black.copy(alpha = 0.1f)
        ),
        start = Offset(shimmerTranslate, 0f),
        end = Offset(shimmerTranslate + 800f, 0f)
    )

    fun agregarColaImpresion(isContado: Boolean = true, isReimpresion: Boolean = false, isProforma: Boolean = false) {
        val parametroEmpresa = if (isContado) "99" else "98"
        val cantidadCopias = if (isReimpresion || isProforma) 0 else obtenerValorParametroEmpresa(parametroEmpresa, "0").toInt()

        val items = (0..cantidadCopias).map { index ->
            val valor = when {
                isProforma -> "4"
                isReimpresion -> "3"
                index == 0 -> "1"
                else -> "2"
            }
            ParClaveValor(index.toString(), valor)
        }

        listaImpresion.addAll(items)
    }

    fun pagarCompleto(){
        tipoPago = "contado"
        listaPagos.forEach {
            it.funcion = "eliminar"
        }
        if (listaMediosPago.isEmpty()) return mostrarMensajeError("No existen Medios de Pago.")
        agregarColaImpresion()
        listaPagos.add(Pago(
            Documento = numeroProforma,
            CodigoMoneda = codMonedaProforma,
            Monto = total.toString(),
            CuentaContable = listaMediosPago.first().clave
        ))
        isSoloAgregarFormaPago = false
        agregarFormapago = true
    }

    fun pagarACredito(){
        tipoPago = "credito"
        agregarColaImpresion(false)
        guardarProforma = true
    }

    suspend fun AgregaEditaArticulo(articulo :  ArticuloLineaProforma){
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.agregarActualizarLinea(articulo)
        if (result != null){
            if (validarExitoRestpuestaServidor(result)){
                iniciarMenuAgregaEditaArticulo = false
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                soloActualizarArticulos = true
            }else{
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            }
        }
    }

    fun deserializarArticulo(datos : String) : ArticuloFacturacion {
        val listaPrecios = mutableListOf<ParClaveValor>()
        val listaBodegas = mutableListOf<ParClaveValor>()
        val datosArticulo = JSONObject(datos)
        for (a in 1 until 11){
            listaPrecios.add(
                ParClaveValor(
                    clave = "$a - $simboloMoneda${separacionDeMiles(datosArticulo.getDouble("Precio$a")/tasaCambio)}",
                    valor = (datosArticulo.getDouble("Precio$a")/tasaCambio).toString(),
                    tipo = "$a"
                )
            )
        }

        val bodegasString = datosArticulo.getString("bodegas")
        val datosBodegas = JSONArray(bodegasString)

        for (e in 0 until datosBodegas.length()) {
            val datoBodega = datosBodegas.getJSONObject(e)
            val bodega = ParClaveValor(
                clave = datoBodega.getString("Cod_Bodega"),
                valor = datoBodega.getString("Descripcion"),
                existencia = datoBodega.getDouble("Existencia")
            )
            listaBodegas.add(bodega)
        }

        val costoTemp = datosArticulo.getDouble("Costo")/tasaCambio
        val articulo = ArticuloFacturacion(
            codigo = datosArticulo.getString("Codigo"),
            codBarra = datosArticulo.getString("Cod_Barra"),
            descripcion = datosArticulo.getString("Descripcion"),
            stock = datosArticulo.optDouble("Stock", 0.00),
            costo = costoTemp,
            fraccionamiento = datosArticulo.getInt("Fraccionamineto"),
            descuentoFijo = datosArticulo.getDouble("Descuento_Fijo"),
            codTarifaImpuesto = datosArticulo.getString("Cod_Tarifa_Impuesto"),
            impuesto = datosArticulo.getDouble("Impuesto"),
            Cod_Tarifa_Impuesto =  datosArticulo.getString("Cod_Tarifa_Impuesto"),
            codEstado = datosArticulo.getString("Cod_Estado"),
            codTipoMoneda = datosArticulo.getString("Cod_Tipo_Moneda"),
            codNaturalezaArticulo = datosArticulo.getString("Cod_Naturaleza_Articulo"),
            codCabys = datosArticulo.getString("Cod_Cabys"),
            actividadEconomica = datosArticulo.getString("Actividad_Economica"),
            unidadXMedida = datosArticulo.getDouble("Unidad_x_Medida"),
            unidadMedida = datosArticulo.getString("Unidad_Medida"),
            descuentoAdmitido = datosArticulo.getDouble("Descuento_Admitido"),
            precio = datosArticulo.getDouble("Precio")/tasaCambio,
            listaPrecios = listaPrecios,
            listaBodegas = listaBodegas
        )
        return articulo
    }

    suspend fun filtrarBusquedaArticulos(){
        if(datosIngresadosBarraBusquedaArticulos.isNotEmpty()){
            listaArticulosEncontrados = emptyList()
            gestorTablaArticulos.actualizarInputBusqueda(datosIngresadosBarraBusquedaArticulos)
            gestorTablaArticulos.modoExacto(true)
            delay(250) // opcional si quieres cambiar a parcial después
            if (listaArticulos.isEmpty()) {
                gestorTablaArticulos.modoExacto(false)
            }
            delay(250)
            val listaArticulosTemp = mutableListOf<ArticuloFacturacion>()
            listaArticulos.forEach{ articuloTemp ->
                listaArticulosTemp.add(deserializarArticulo(articuloTemp.datos))
            }
            listaArticulosEncontrados = listaArticulosTemp
            isCargandoArticulos = false
        }

    }

    suspend fun descargarArticulos(){
        errorCargarProforma = false
        apiConsultaArticulos?.cancel()
        apiConsultaArticulos= cortinaConsultaApiArticulos.launch{
            val result = objectoProcesadorDatosApi.obtenerArticulos(
                tipoPrecio = "1",
                moneda = "CRC",
                ultimaActualizacion =  obtenerParametroLocal(context, "ultActuArtFac$nombreEmpresa")
            )
            if (result!=null){
                if (validarExitoRestpuestaServidor(result)){
                    val data = result.getJSONArray("data")
                    val listaArticulosTemp = mutableListOf<ArticuloDb>()
                    for(i in 0 until data.length()){
                        val datosArticulo = data.getJSONObject(i)
                        listaArticulosTemp.add(ArticuloDb(codigo = datosArticulo.getString("Codigo"), datos = datosArticulo.toString(), empresa = nombreEmpresa, description = datosArticulo.getString("Descripcion"), codigoBarra = datosArticulo.getString("Cod_Barra")))
                        if (obtenerParametroLocal(context, "ultActuArtFac$nombreEmpresa") != ""){
                            gestorTablaArticulos.eliminarArticulo(codigo = datosArticulo.getString("Codigo"), empresa =  nombreEmpresa)
                        }
                    }
                    gestorTablaArticulos.insertarArticulos(listaArticulosTemp)
                    actualizarParametro(context = context, clave = "ultActuArtFac$nombreEmpresa", nuevoValor = obtenerFechaHoraActual())
                }else{
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    errorCargarProforma = true
                    actualizarDatosProforma = false
                }
            }
            validacionCargaFinalizada++
        }
        if (datosIngresadosBarraBusquedaArticulos.isNotEmpty()){ // SE HACE PARA QUE SE ACTULICEN LOS ARTICULOS YA BUCADOS
            filtrarBusquedaArticulos()
        }
        iniciarDescargaArticulos = false

    }

    if (valorImpresionActiva == "1") {
        gestorImpresora.PedirPermisos(context)
    }

    LaunchedEffect(Unit) {
        gestorTablaArticulos.cargarEmpresa(nombreEmpresa)
        guardarParametroSiNoExiste(context, "ultActuArtFac$nombreEmpresa", "")
    }

    LaunchedEffect (Unit) {
        validarVersionApp(context)
    }

    LaunchedEffect (actualizarDatosProforma, soloActualizarArticulos, soloActualizarDatosCliente) {
        if (actualizarDatosProforma || soloActualizarArticulos || soloActualizarDatosCliente){
            listaArticulosSeleccionados.clear()
            isCargandoDatos = (!soloActualizarArticulos  && !soloActualizarDatosCliente)
            expandedTotales = false
            expandedClientes = false
            errorCargarProforma = false
            iniciarDescargaArticulos = false
            apiConsultaArticulos?.cancel()
            apiConsultaProforma?.cancel()
            apiConsultaProforma= cortinaConsultaApiProforma.launch{
                objectoProcesadorDatosApi.crearNuevaProforma()
                val result= objectoProcesadorDatosApi.abrirProforma(numeroProforma)
                if (result!=null){
                    if(validarExitoRestpuestaServidor(result)) {
                        val data = result.getJSONObject("data")

                        //DATOS CLIENTE
                        val datosCliente = data.getJSONArray("cliente").getJSONObject(0)
                        clienteId= datosCliente.getString("ClienteID")
                        nombreFactura = datosCliente.getString("ClienteNombre")
                        nombreCliente= datosCliente.getString("Nombre")
                        nombreComercial = datosCliente.getString("clientenombrecomercial")
                        numeroCedula = datosCliente.getString("Cedula")
                        emailGeneral = datosCliente.getString("Email")
                        telefonos = datosCliente.getString("Telefonos")
                        tipoCedula = datosCliente.getString("TipoIdentificacion")
                        plazoCredito = datosCliente.getString("plazo")
                        montoCredito = datosCliente.getInt("MontoCredito").toString()
                        montoContrato = datosCliente.getInt("MontoContrato").toString()
                        tipoPrecioCliente = datosCliente.getString("TipoPrecioVenta")
                        nuevoTipoPrecio = tipoPrecioCliente
                        codMonedaCliente = datosCliente.getString("monedacodigo")
                        descuentoCliente = datosCliente.getDouble("Descuento").toString()
                        direccion = datosCliente.getString("direccion")
                        isCrearProforma = datosCliente.getString("Cod_Estado") == "2"

                        //DATOS PROFORMA
                        val datosProforma = data.getJSONArray("datos").getJSONObject(0)
                        numeroProforma= datosProforma.getString("Numero")
                        tipoDocumento = datosProforma.getString("TipoDocumento")
                        estadoProforma = datosProforma.getString("Estado")

                        estadoProformaInicial = estadoProforma

                        //Tipo Moneda
//                        val actualizarListaArticulos = codMonedaProforma != data.getString("monedaDocumento")
//                        validacionCargaFinalizada = if (actualizarListaArticulos) 0 else 1
                        codMonedaProforma = data.getString("monedaDocumento")
                        tasaCambio = data.getDouble("tipoCambio")
                        tasaCambioDolar = data.getDouble("tipoCambioDolar")
                        detalleProforma = data.optString("detalle")
                        fechaProforma = data.getString("fecha")
                        ordenCompra = data.getString("ordenCompra")
                        nuevoCodigoMoneda = codMonedaProforma
                        simboloMoneda =  if(codMonedaProforma == "CRC") "\u20A1 " else "\u0024 "
                        iniciarDescargaArticulos = !iniciarMenuSeleccionarArticulo // SI EL MENU ESTA ABIERTO NO DESCRAGAN LOS ARTICULOS

                        //Totales
                        val totales = data.getJSONArray("totales").getJSONObject(0)
                        totalGravado = totales.getDouble("TotalGravado")
                        totalIva = totales.getDouble("TotalIva")
                        totalDescuento = totales.getDouble("TotalDescuento")
                        totalPago = totales.getDouble("Pago")
                        totalExonerado = totales.getDouble("TotalExonerado")
                        totalIvaDevuelto = totales.getDouble("TotalIvaDevuelto")
                        totalMercGrav = totales.getDouble("TotalMercGravado")
                        total = totales.getDouble("Total")

                        // ARTICULOS PROFORMA
                        val listaArticuloFacturados = mutableListOf<ArticuloFacturacion>()
                        val articulos = data.getJSONArray("proforma")
                        listaIvas.clear()
                        for(i in 0 until articulos.length()){
                            val datosArticulo = articulos.getJSONObject(i)

                            val montoIvaUnitario = datosArticulo.getDouble("PrecioUd") * datosArticulo.getDouble("ArticuloIvaPorcentage") / 100
                            val articuloFacturado = ArticuloFacturacion(
                                codigo = datosArticulo.getString("ArticuloCodigo"),
                                codPrecioVenta = datosArticulo.getString("PV"),
                                descripcion = datosArticulo.getString("Descripcion"),
                                articuloCantidad = datosArticulo.getDouble("ArticuloCantidad"),
                                precio = datosArticulo.getDouble("PrecioUd"),
                                precioNeto = datosArticulo.getDouble("PrecioUd") + montoIvaUnitario,
                                articuloDescuentoPorcentaje = datosArticulo.getDouble("ArticuloDescuentoPorcentage"),
                                articuloDescuentoMonto = datosArticulo.getDouble("ArticuloDescuentoMonto"),
                                articuloVentaSubTotal2 = datosArticulo.getDouble("ArticuloVentaSubTotal2"),
                                articuloVentaGravado = datosArticulo.getDouble("ArticuloVentaGravado"),
                                articuloIvaExonerado = datosArticulo.getDouble("ArticuloIvaExonerado"),
                                articuloVentaExento = datosArticulo.getDouble("ArticuloVentaExento"),
                                impuesto = datosArticulo.getDouble("ArticuloIvaPorcentage"),
                                articuloIvaMonto = datosArticulo.getDouble("ArticuloIvaMonto"),
                                articuloBodegaCodigo = datosArticulo.getString("ArticuloBodegaCodigo"),
                                articuloVentaTotal = datosArticulo.getDouble("ArticuloVentaTotal"),
                                existencia = datosArticulo.getDouble("Existencia"),
                                articuloLineaId = datosArticulo.getString("ArticuloLineaId"),
                                articuloCosto = datosArticulo.getDouble("ArticuloCosto"),
                                utilidad = datosArticulo.getDouble("Utilidad"),
                                articuloSerie = datosArticulo.getString("ArticuloSerie")
                            )
                            val ivaTemp = listaIvas.find { it.clave == articuloFacturado.impuesto.toString() }
                            if ( ivaTemp != null){
                                ivaTemp.valor = (ivaTemp.valor.toDouble() + articuloFacturado.articuloIvaMonto).toString()
                            }else{
                                listaIvas.add(ParClaveValor(clave = articuloFacturado.impuesto.toString(), valor = articuloFacturado.articuloIvaMonto.toString()))
                            }
                            listaArticuloFacturados.add(articuloFacturado)
                        }
                        listaArticulosProforma = listaArticuloFacturados

                        // PAGOS
                        val pagos = data.getJSONArray("pagos")
                        listaPagos.clear()
                        for(i in 0 until pagos.length()){
                            val datosPago = pagos.getJSONObject(i)
                            listaPagos.add(
                                Pago(
                                    Id = datosPago.getString("Id"),
                                    Documento = datosPago.getString("Documento"),
                                    TipoDocumento = datosPago.getString("TipoDocumento"),
                                    Monto = datosPago.getString("Monto"),
                                    CuentaContable = datosPago.getString("CuentaContable"),
                                    CodigoMoneda = datosPago.getString("CodigoMoneda"),
                                    TipoCambio = datosPago.getString("TipoCambio"),
                                    Total = datosPago.getString("Total"),
                                    funcion = "editar"
                                )
                            )
                        }
                    }
                    else{
                        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                        numeroProforma = ""
                        errorCargarProforma = true
                        actualizarDatosProforma = false
                    }
                }
                validacionCargaFinalizada++
                soloActualizarArticulos = false
                soloActualizarDatosCliente = false
            }
        }
    }

    LaunchedEffect (actualizarDatosProforma) {
        if (!actualizarDatosProforma) return@LaunchedEffect
        listaMediosPago.clear()
        val result = objectoProcesadorDatosApi.obtenerMediosPago()
        if (result != null){
            if (validarExitoRestpuestaServidor(result)){
                val data = result.getJSONArray("data")
                for(i in 0 until data.length()){
                    val datosMedioPago = data.getJSONObject(i)
                    listaMediosPago.add(
                        ParClaveValor(
                            clave = datosMedioPago.getString("Cuenta"),
                            valor = datosMedioPago.getString("Nombre")
                        )
                    )
                }
            }else{
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            }
        }
    }

    LaunchedEffect (actualizarDatosProforma) {
        if (!actualizarDatosProforma) return@LaunchedEffect
        val result = objectoProcesadorDatosApi.obtenerPermisosUsuario(codUsuario)
        if (result == null) return@LaunchedEffect
        if (!validarExitoRestpuestaServidor(result)){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            return@LaunchedEffect
        }
        listaPermisos = (0 until result.getJSONArray("data").length()).map { i ->
            val permiso = result.getJSONArray("data").getJSONObject(i)
            ParClaveValor(clave = permiso.getString("Cod_Derecho"), valor = permiso.getString("Descripcion"))
        }
    }

    LaunchedEffect (actualizarDatosProforma) {
        if (!actualizarDatosProforma) return@LaunchedEffect
        val result = objectoProcesadorDatosApi.obtenerParemetrosEmpresa()
        if (result == null) return@LaunchedEffect
        if (!validarExitoRestpuestaServidor(result)) {
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            return@LaunchedEffect
        }
        listaParametros = (0 until result.getJSONObject("resultado").getJSONArray("data").length()).map { i ->
            val parametro = result.getJSONObject("resultado").getJSONArray("data").getJSONObject(i)
            ParClaveValor(clave = parametro.getString("Parametro"), valor = parametro.getString("Valor"), descripcion = parametro.getString("Descripcion"))
        }
    }

    LaunchedEffect (iniciarDescargaArticulos) {
        if (!iniciarDescargaArticulos) return@LaunchedEffect
        descargarArticulos()
    }

    LaunchedEffect (datosIngresadosBarraBusquedaArticulos) {
        filtrarBusquedaArticulos()
    }

    LaunchedEffect (validacionCargaFinalizada) {
        if (validacionCargaFinalizada == 2){
            actualizarDatosProforma= false
            isCargandoDatos= false
            validacionCargaFinalizada = 0
        }
    }

    LaunchedEffect (eliminarLinea) {
        if (eliminarLinea){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.eliminarLineaProforma(numero = numeroProforma, lineaArticulo = lineaAcual)
            if (result != null){

                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                if (validarExitoRestpuestaServidor(result)){
                    soloActualizarArticulos = true
                    lineaAcual = ""
                }else{
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                }
            }
        }
        eliminarLinea = false
    }

    LaunchedEffect (datosIngresadosBarraBusquedaCliente) {
        if (datosIngresadosBarraBusquedaCliente.isNotEmpty()){
            isCargandoClientes = true
            listaClientesEncontrados = emptyList()
            apiConsultaBusquedaClientes?.cancel()
            apiConsultaBusquedaClientes= cortinaConsultaApiBusquedaClientes.launch{
                delay(250)
                val result = objectoProcesadorDatosApiCliente.obtenerDatosClientes(
                    clienteEstado = "4",
                    clienteDatoBusqueda = datosIngresadosBarraBusquedaCliente,
                    clientesPorPagina = "50",
                    paginaCliente = "1",
                    busquedaPor = "BusquedaMixta"
                )
                if (result != null){
                    if (validarExitoRestpuestaServidor(result)){
                        val resultado= result.getJSONObject("resultado")
                        val datosClientes= resultado.getJSONArray("data")
                        val listaClientes = mutableListOf<ClienteFacturacion>()
                        for (i in 0 until datosClientes.length()) {
                            val datosCliente = datosClientes.getJSONObject(i)
                            val cliente = ClienteFacturacion(
                                codigo = datosCliente.getString("codigo"),
                                nombreComercial = datosCliente.getString("nombrecomercial"),
                                nombreJuridico = datosCliente.getString("nombrejuridico"),
                                telefono = datosCliente.getString("telefonos"),
                                correo = datosCliente.getString("emailgeneral"),
                                codMoneda = datosCliente.getString("moneda"),
                                tipoPrecio = datosCliente.getString("tipoprecio"),
                                estado = datosCliente.getString("estado")
                            )
                            listaClientes.add(cliente)
                        }
                        listaClientesEncontrados = listaClientes
                    }else{
                        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    }
                }
                isCargandoClientes = false
            }
        }
    }

    LaunchedEffect (buscarProformas) {
        if (!buscarProformas) return@LaunchedEffect
        buscarProformas = false
        isCargandoProformas = true
        listaProforma = emptyList()
        apiBusquedaProforma?.cancel()

        apiBusquedaProforma = cortinaConsultaApiBusquedaProforma.launch {
            delay(250)

            val result = objectoProcesadorDatosApi.obtenerProformas(
                nombreCliente = nombreClienteBusquedaProforma,
                fechaInicio = fechaInicialProforma,
                fechaFinal = fechafinalProforma,
                estadoProforma = estadoBusquedaProforma,
                codUsuario = if (tienePermiso("069")) "" else codUsuario
            )

            if (result != null) {
                if (validarExitoRestpuestaServidor(result)) {
                    val resultado = result.getJSONObject("resultado")
                    val data = resultado.getJSONArray("data")

                    listaProforma = List(data.length()) { i ->
                        val datosProforma = data.getJSONObject(i)
                        Proforma(
                            nombreCliente = datosProforma.getString("ClienteNombre"),
                            numero = datosProforma.getString("Numero"),
                            total = datosProforma.getString("Total"),
                            codMoneda = datosProforma.getString("MonedaCodigo")
                        )
                    }
                }else{
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                }
            }
            isCargandoProformas = false
        }
    }

    LaunchedEffect (cambiarClienteProforma) {
        if(cambiarClienteProforma){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.cambiarClienteProforma(
                numero = numeroProforma,
                clienteFacturacion = clienteSeleccionado
            )
            if (result != null){
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                if(validarExitoRestpuestaServidor(result)){
                    iniciarMenuSeleccionarCliente = false
                    soloActualizarDatosCliente = true
                }else{
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                }
            }
        }
        cambiarClienteProforma = false
    }

    LaunchedEffect (guardarProformaBorrador) {
        if (!guardarProformaBorrador) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.guardarProformaBorrador(numeroProforma)
        if (result != null) {
            if (validarExitoRestpuestaServidor(result)){
                numeroProforma = ""
                actualizarDatosProforma = true
            }else{
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        guardarProformaBorrador = false
    }

    LaunchedEffect (clonarProforma) {
        if (!clonarProforma) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.clonarProforma(numeroProforma)
        if (result != null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            if (validarExitoRestpuestaServidor(result)){
                numeroProforma = result.getString("NuevoCosecutivoProforma")
                actualizarDatosProforma = true
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        clonarProforma = false


    }

    LaunchedEffect (exonerar) {
        if (!exonerar) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.exonerarProforma(numero = numeroProforma, codigoCliente = clienteId)
        if (result != null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta  = true, datosRespuesta = result)
            if (validarExitoRestpuestaServidor(result)){
                actualizarDatosProforma = true
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        exonerar = false
    }

    LaunchedEffect (quitarExoneracion) {
        if (!quitarExoneracion) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.quitarExoneracionProforma(numeroProforma)
        if (result != null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            if (validarExitoRestpuestaServidor(result)){
                actualizarDatosProforma = true
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        quitarExoneracion = false
    }

    LaunchedEffect (cambiarMoneda)  {
        if(!cambiarMoneda) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.cambiarMonedaProforma(numero = numeroProforma, codigoMoneda = nuevoCodigoMoneda)
        if (result != null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            if (validarExitoRestpuestaServidor(result)){
                actualizarDatosProforma = true
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        cambiarMoneda = false
    }

    LaunchedEffect (aplicarDescuento) {
        if (!aplicarDescuento) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val lineas = JSONArray()
        listaArticulosSeleccionados.forEach { articulo ->
            lineas.apply {
                put(articulo.articuloLineaId)
            }
        }
        val result = objectoProcesadorDatosApi.aplicarDescuento(numero = numeroProforma, descuento = nuevoPorcentajeDescuento, lineas = lineas.toString())
        if (result != null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            if(validarExitoRestpuestaServidor(result)){
                actualizarDatosProforma = true
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        listaArticulosSeleccionados.clear()
        nuevoPorcentajeDescuento = "0"
        aplicarDescuento = false


    }

    LaunchedEffect (agregarFormapago) {
        if (!agregarFormapago) return@LaunchedEffect
        isFormaPagoAgregada = false
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)

        val cantidadPagosEnviados = AtomicInteger(0)

        coroutineScope {
            listaPagos.map { pago ->
                async {
                    cantidadPagosEnviados.incrementAndGet()

                    if ( ((pago.Monto.isEmpty() || pago.Monto.toDouble() == 0.0) && pago.Id != "0") || pago.funcion == "eliminar"){
                        pago.Monto = "eliminar"
                    }
                    pago.TipoCambio= if ( pago.CodigoMoneda != "CRC" ) tasaCambio.toString() else "1"
                    val result = objectoProcesadorDatosApi.agregarFormaPago(pago = pago)

                    if (result != null) {

                        estadoRespuestaApi.cambiarEstadoRespuestaApi(
                            mostrarRespuesta = isSoloAgregarFormaPago,
                            mostrarSoloRespuestaError = !isSoloAgregarFormaPago,
                            datosRespuesta = result
                        )

                        if (validarExitoRestpuestaServidor(result)) {
                            cantidadPagosEnviados.decrementAndGet()
                            listaPagos.removeIf { it.Monto == "eliminar" }
                        }
                    }
                }
            }
        }.awaitAll()
        if (cantidadPagosEnviados.get() == 0) {
            actualizarDatosProforma = isSoloAgregarFormaPago
            guardarProforma = !isSoloAgregarFormaPago
        }
        else{
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            actualizarDatosProforma = true
        }
        if (listaPagos.size==0) mostrarMensajeExito("Las formas de pago se han guardado exitosamente.")
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        isSoloAgregarFormaPago = false
        agregarFormapago = false
    }

    LaunchedEffect (guardarProforma) {
        if (!guardarProforma) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        try {
            var result = objectoProcesadorDatosApi.guardarProforma(
                numero = numeroProforma,
                tipoPago = tipoPago,
                tipo = tipoFormaProcesar,
                correo = correoProformaTemp
            )

            if (result == null) return@LaunchedEffect

            if (!validarExitoRestpuestaServidor(result)){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                actualizarDatosProforma = true
                return@LaunchedEffect
            }

            val data = result.getJSONObject("data")
            consecutivoFactura = data.getString("consecutivo")

            if(valorImpresionActiva == "0" || listaImpresion.isEmpty()){
                if (tipoFormaProcesar == "proforma") mostrarMensajeExito("La Proforma se ha emitido exitosamente con el consecutivo: $consecutivoFactura") else mostrarMensajeExito("La factura se ha emitido exitosamente!")
                numeroProforma = ""
                actualizarDatosProforma = true
                return@LaunchedEffect
            }
            datosFacturaEmitida = Factura()
            result = objectoProcesadorDatosApi.obtenerFactura(consecutivoFactura)

            if (result == null) return@LaunchedEffect

            if (validarExitoRestpuestaServidor(result)) {
                datosFacturaEmitida = deserializarFacturaHecha(result)
                imprimir = true
            }else{
                mostrarMensajeError(result.getString("message"))
                iniciarPantallaEstadoImpresion = true
                exitoImpresion = false
                isImprimiendo = false
                imprimir = false
            }
        } finally {
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            correoProformaTemp = ""
            guardarProforma = false
        }
    }

    LaunchedEffect (imprimir) {
        if (!imprimir) return@LaunchedEffect
        isImprimiendo = true
        iniciarPantallaEstadoImpresion = true
        val isConectado = gestorImpresora.validarConexion(context)
        delay(1000)
        if (!isConectado){
            gestorImpresora.reconectar(context)
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Reconectando impresora...", Toast.LENGTH_SHORT).show()
            }
            delay(12000)
            val isConectada = gestorImpresora.validarConexion(context)
            delay(1000)
            if (!isConectada){
                exitoImpresion = false
                isImprimiendo = false
                estadoProforma = "2"
                imprimir = false
                return@LaunchedEffect
            }
        }
        exitoImpresion = imprimirFactura(datosFacturaEmitida, context, nombreEmpresa, listaImpresion.first(), "#$codUsuario $nombreUsuario")
        delay(3500)
        isImprimiendo = false
        if (!exitoImpresion){
            estadoProforma = "2"
            imprimir = false
            return@LaunchedEffect
        }
        listaImpresion.removeAt(0)
        if (listaImpresion.isNotEmpty()) {
            imprimir = false
            return@LaunchedEffect
        }
        delay(2000)
        iniciarPantallaEstadoImpresion= false
        numeroProforma = ""
        actualizarDatosProforma = true
        imprimir = false
    }

    LaunchedEffect (obtenerDatosFacturaEmitida) {
        if (!obtenerDatosFacturaEmitida) return@LaunchedEffect
        isImprimiendo = true
        iniciarPantallaEstadoImpresion = true
        delay(1000)
        if (!gestorImpresora.validarConexion(context)){
            exitoImpresion = false
            isImprimiendo = false
            imprimir = false
            obtenerDatosFacturaEmitida = false
            return@LaunchedEffect
        }
        val result = objectoProcesadorDatosApi.obtenerFactura(consecutivoFactura)

        if (result == null) return@LaunchedEffect

        if (validarExitoRestpuestaServidor(result)) {
            datosFacturaEmitida = deserializarFacturaHecha(result)
            imprimir = true
        }else{
            mostrarMensajeError(result.getString("message"))
            iniciarPantallaEstadoImpresion = true
            exitoImpresion = false
            isImprimiendo = false
            imprimir = false

        }
        obtenerDatosFacturaEmitida = false
    }

    LaunchedEffect (agregarNuevoCliente) {
        if (!agregarNuevoCliente) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApiCliente.agregarCliente(datosCliente = clienteTemp)
        if (result == null) return@LaunchedEffect gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        if (validarExitoRestpuestaServidor(result)){
            clienteSeleccionado = ClienteFacturacion(
                codigo = result.getString("Id_Cliente"),
                nombreJuridico = clienteTemp.Nombre,
                nombreComercial = clienteTemp.nombreComercial,
                telefono = clienteTemp.Telefonos,
                correo = clienteTemp.EmailFactura,
                tipoPrecio = clienteTemp.TipoPrecioVenta,
                codMoneda = clienteTemp.Cod_Moneda
            )
            cambiarClienteProforma = true
        }else{
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        }
        clienteTemp = Cliente()
        agregarNuevoCliente = false
    }

    LaunchedEffect (iniciarBusquedaClienteByCedula) {
        if(iniciarBusquedaClienteByCedula){
            if (clienteTemp.Cedula.length >=9){
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = obtenerDatosClienteByCedula(numeroCedula = clienteTemp.Cedula)
                if (result!=null){
                    if ((result.optString("resultcount", "0")) == "1"){
                        val results = result.getJSONArray("results")
                        val datos = results.getJSONObject(0)
                        clienteTemp.Nombre = datos.getString("fullname")
                    }else{
                        mostrarMensajeError("El cliente no encontrado")
                    }
                }
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            }else{
                mostrarMensajeError("Ingrese una cedula valida.")
            }
        }
        iniciarBusquedaClienteByCedula = false
    }

    LaunchedEffect (editarCliente) {
        if(!editarCliente) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApiCliente.actualizarDatosClientes(clienteTemp, clienteTemp)
        if (result == null) return@LaunchedEffect gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
        if (validarExitoRestpuestaServidor(result)) soloActualizarDatosCliente = true
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        clienteTemp = Cliente()
        editarCliente = false
    }

    LaunchedEffect (guardarDetalleFactura) {
        if (!guardarDetalleFactura) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.guardarDestalleFactura(detalleProforma, numeroProforma)
        if (result == null) return@LaunchedEffect
        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        guardarDetalleFactura = false
    }

    LaunchedEffect (guardarOrdenCompra) {
        if (!guardarOrdenCompra) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.guardarOrdenCompra(ordenCompra, numeroProforma)
        if (result == null) return@LaunchedEffect
        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        guardarOrdenCompra = false
    }

    LaunchedEffect (actualizarNombreProforma) {
        if (!actualizarNombreProforma) return@LaunchedEffect
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        val result = objectoProcesadorDatosApi.actualizarNombreProforma(nombreProformaTemp, numeroProforma)
        if (result == null) return@LaunchedEffect
        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        if (validarExitoRestpuestaServidor(result)) soloActualizarDatosCliente = true
        nombreProformaTemp = ""
        actualizarNombreProforma = false
    }

    LaunchedEffect(Unit) {
        if (valorImpresionActiva != "1") return@LaunchedEffect  Toast.makeText(context, "La impresión está inactiva.", Toast.LENGTH_SHORT).show()
        gestorImpresora.reconectar(context)
    }

    // Interceptar el botón de retroceso
    BackHandler {
        if (
            iniciarMenuSeleccionarProforma ||
            iniciarMenuSeleccionarCliente ||
            iniciarMenuSeleccionarArticulo ||
            iniciarMenuOpcionesProforma ||
            iniciarMenuConfirmacionSalidaModulo ||
            iniciarMenuConfQuitarExoneracion ||
            iniciarMenuConfExoneracion ||
            iniciarMenuCambiarMoneda ||
            iniciarMenuCambiarPrecio ||
            iniciarMenuAplicarDescuento ||
            iniciarMenuProcesar ||
            iniciarMenuSeleccionarMedioPago ||
            iniciarMenuConfComoProforma ||
            iniciarMenuAgregaEditaCliente ||
            iniciarMenuOpcionesCliente
        ){
            iniciarMenuOpcionesCliente = false
            iniciarMenuAgregaEditaCliente = false
            iniciarMenuConfComoProforma= false
            iniciarMenuSeleccionarMedioPago = false
            iniciarMenuProcesar = false
            iniciarMenuCambiarMoneda = false
            iniciarMenuCambiarPrecio = false
            iniciarMenuAplicarDescuento = false
            iniciarMenuConfirmacionSalidaModulo = false
            iniciarMenuConfQuitarExoneracion = false
            iniciarMenuConfExoneracion = false
            iniciarMenuOpcionesProforma = false
            iniciarMenuSeleccionarProforma = false
            iniciarMenuSeleccionarCliente = false
            iniciarMenuSeleccionarArticulo = false
        }else if(!iniciarMenuAgregaEditaArticulo){
            iniciarMenuConfirmacionSalidaModulo = true
        }

        if (iniciarMenuAgregaEditaArticulo){
            iniciarMenuAgregaEditaArticulo= false
            iniciarMenuSeleccionarArticulo = isAgregar
        }
    }

    fun validarEstadoProforma(): Boolean {
        if (estadoProforma=="2"){
            mostrarMensajeError("La Proforma es tipo factura y no se puede editar.")
        }
        return estadoProforma=="2"
    }

    @Composable
    fun BxContenedorArticulosFacturacion(
        articulo : ArticuloFacturacion
    ) {
        val valorString = articulo.listaPrecios.find { it.tipo == tipoPrecioCliente }?.valor
        val precioArticuloDouble = valorString?.toDoubleOrNull() ?: articulo.precio
        val precioArticulo = BigDecimal.valueOf(precioArticuloDouble)
        val impuestoFactor = BigDecimal.ONE + BigDecimal.valueOf(articulo.impuesto).divide(BigDecimal(100))
        val precioIva = precioArticulo.multiply(impuestoFactor)
        var expandedBodegas by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(7),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16))
                )
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .clickable {
                    isAgregar = true
                    coroutineScope.launch {
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                        descargarArticulos()
                        delay(300)
                        val articuloTemp =
                            gestorTablaArticulos.obetenerDatosArticulo(articulo.codigo)
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                        delay(300)
                        if (articuloTemp == null) return@launch mostrarMensajeError(
                            "No se logró encontrar las bodegas y los precios de este artículo actualice y vuelva a intentar."
                        )
                        articuloActual = deserializarArticulo(articuloTemp.datos)
                        iniciarMenuAgregaEditaArticulo = true

                    }
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
            colors = CardDefaults.cardColors(containerColor =Color(0xFF31BF59))
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = objetoAdaptardor.ajustarAncho(16))
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Box(
                    modifier = Modifier
                        .height(objetoAdaptardor.ajustarAltura(50))
                        .width(objetoAdaptardor.ajustarAncho(50))
                        .padding(
                            start = objetoAdaptardor.ajustarAncho(8),
                            top = objetoAdaptardor.ajustarAltura(8)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articulo.codigo}.png",
                        contentDescription = "Imagen Articulo",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                CircularProgressIndicator(
                                    color = Color(0xFF244BC0),
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(20))
                                )
                            }
                        },
                        error = {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterResource(id = R.drawable.sin_imagen),
                                contentDescription = "Descripción de la imagen",
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    )
                }
                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TText(
                        text = "#" + articulo.codigo,
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), top = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodySmall(),
                        color = Color(0xFF787877)
                    )
                    TText(
                        text = articulo.descripcion,
                        fontSize = obtenerEstiloBodyBig(),
                        maxLines = 2,
                        modifier = Modifier.padding(start = objetoAdaptardor.ajustarAncho(8), end = objetoAdaptardor.ajustarAncho(16))
                    )
                    TText(
                        text = simboloMoneda + separacionDeMiles(montoString = precioIva.toString(), isString = true) + " $codMonedaProforma",
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8)),
                        fontSize = obtenerEstiloBodyMedium(),
                        color = Color(0xFF5E5E5E)
                    )
                    if (articulo.listaBodegas.isNotEmpty()){
                        if (obtenerValorParametroEmpresa(codParametro = "1", valorAuxiliar = "0") == "1"){
                            val primeraBodega = articulo.listaBodegas.first()
                            TText(
                                text = "#${primeraBodega.clave} | ${primeraBodega.valor} | Exis: ${separacionDeMiles(primeraBodega.existencia)}",
                                fontSize = obtenerEstiloBodySmall(),
                                maxLines = 2,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        start = objetoAdaptardor.ajustarAncho(8),
                                        end = objetoAdaptardor.ajustarAncho(8)
                                    ),
                                color = if (primeraBodega.existencia.toInt() <= 0 ) Color.Red else Color.DarkGray,
                                textAlign = TextAlign.Start
                            )
                            if (obtenerValorParametroEmpresa(codParametro = "53", valorAuxiliar = "0") == "1"){
                                AnimatedVisibility(
                                    visible = expandedBodegas,
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        articulo.listaBodegas.forEach {
                                            if (it.clave != primeraBodega.clave){
                                                TText(
                                                    text = "#${it.clave} | ${it.valor} | Exis: ${separacionDeMiles(it.existencia)}",
                                                    fontSize = obtenerEstiloBodySmall(),
                                                    maxLines = 2,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(
                                                                8
                                                            ),
                                                            end = objetoAdaptardor.ajustarAncho(8)
                                                        ),
                                                    color =  if (it.existencia.toInt() <= 0 ) Color.Red else Color.DarkGray,
                                                    textAlign = TextAlign.Start
                                                )
                                            }
                                        }
                                    }
                                }
                                if (articulo.listaBodegas.size > 1){
                                    BButton(
                                        text =  if (expandedBodegas) "Mostrar menos Bodegas" else "Mostrar más Bodegas",
                                        onClick = {expandedBodegas = !expandedBodegas},
                                        contenteColor = Color(0xFF244BC0),
                                        backgroundColor = Color.White,
                                        conSombra = false,
                                        textSize = obtenerEstiloBodySmall(),
                                        objetoAdaptardor = objetoAdaptardor,
                                        modifier = Modifier
                                            .padding(start = objetoAdaptardor.ajustarAncho(4), end = objetoAdaptardor.ajustarAncho(8))
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier =Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                }
            }
        }
    }

    @Composable
    fun BxContenedorClienteFacturacion(
        cliente : ClienteFacturacion
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(7),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16))
                )
                .clickable {
                    clienteSeleccionado = cliente
                    cambiarClienteProforma = true
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
            colors = CardDefaults.cardColors(containerColor = if(cliente.estado == "1") Color(0xFF31BF59) else Color(0xFFF3ED00))
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = objetoAdaptardor.ajustarAncho(16))
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TText(
                        text = "#" + cliente.codigo,
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), top = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodySmall(),
                        color = Color(0xFF787877)
                    )
                    TText(
                        text = cliente.nombreJuridico,
                        fontSize = obtenerEstiloBodyBig(),
                        maxLines = 2,
                        modifier = Modifier.padding(start = objetoAdaptardor.ajustarAncho(8), end = objetoAdaptardor.ajustarAncho(16))
                    )
                    TText(
                        text = cliente.nombreComercial,
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), bottom = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodyMedium(),
                        color = Color(0xFF5E5E5E)
                    )
                    TText(
                        text = cliente.telefono,
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), bottom = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodySmall(),
                        color = Color(0xFF5E5E5E)
                    )
                    TText(
                        text = cliente.correo,
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), bottom = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodySmall(),
                        color = Color(0xFF5E5E5E)
                    )
                }
            }

        }
    }

    @Composable
    fun BxContenedorProforma(
        proforma : Proforma
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(7),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16))
                )
                .clickable {
                    numeroProforma = proforma.numero
                    if (iniciarMenuClonarProforma) {
                        clonarProforma = true
                        iniciarMenuClonarProforma = false
                    } else {
                        iniciarMenuSeleccionarProforma = false
                        actualizarDatosProforma = true
                    }

                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
            colors = CardDefaults.cardColors(containerColor =Color(0xFF31BF59))
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = objetoAdaptardor.ajustarAncho(16))
                    .fillMaxSize()
                    .background(Color.White)
            ) {

                Column(
                    horizontalAlignment = Alignment.Start
                ) {
                    TText(
                        text = "#" + proforma.numero,
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), top = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodySmall(),
                        color = Color(0xFF787877)
                    )
                    TText(
                        text = proforma.nombreCliente,
                        fontSize = obtenerEstiloBodyBig(),
                        maxLines = 2,
                        modifier = Modifier.padding(start = objetoAdaptardor.ajustarAncho(8), end = objetoAdaptardor.ajustarAncho(16))
                    )
                    TText(
                        text = proforma.codMoneda + separacionDeMiles(montoString= proforma.total, isString = true) + " $codMonedaProforma",
                        modifier = Modifier
                            .padding(start = objetoAdaptardor.ajustarAncho(8), bottom = objetoAdaptardor.ajustarAltura(8)),
                        fontSize = obtenerEstiloBodyMedium(),
                        color = Color(0xFF5E5E5E)
                    )
                }
            }

        }
    }

    @Composable
    fun BasicTexfiuldWithText(
        textTitle: String,
        text: String,
        icon: ImageVector,
        variable: String,
        nuevoValor: (String) -> Unit,
        darFormatomiles : Boolean = false
    ) {
        Column {
            TText(
                text = textTitle,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = objetoAdaptardor.ajustarAncho(4)),
                color = Color.DarkGray,
                fontSize = obtenerEstiloBodyBig()
            )
            BBasicTextField(
                value = variable,
                onValueChange = {nuevoValor(it)},
                placeholder = text,
                utilizarMedidas = false,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(
                        horizontal = objetoAdaptardor.ajustarAltura(2)
                    ),
                objetoAdaptardor = objetoAdaptardor,
                icono = icon,
                iconTint = Color.Gray,
                textColor = Color.Black,
                fontSize = obtenerEstiloBodyMedium(),
                darFormatoMiles = darFormatomiles,
                enable = false,
                cantidadLineas = 10
            )
        }
    }

    @Composable
    fun MenuAgregaEditaArticulo(
        mostrarVentanaArticulo: Boolean,
        onDismiss: () -> Unit,
        opcionesPresentacion: List<String>,
        articulo : ArticuloFacturacion,
        objetoAdaptardor: FuncionesParaAdaptarContenido,
        codigoMoneda : String,
        descuentoCliente : String,
        isAgregar: Boolean,
        agregaEditaArticulo : (ArticuloLineaProforma)->Unit,
        bodega : ParClaveValor,
        precioVenta: String
    ) {
        if (!mostrarVentanaArticulo) return
        var bodegaSeleccionda by remember { mutableStateOf(bodega) }
        var tipoPrecioSeleccionado by remember { mutableStateOf(articulo.listaPrecios.find { it.tipo == precioVenta.trim() }?:ParClaveValor()) }
        var cantidadProducto by remember { mutableStateOf(if (articulo.articuloCantidad > 0) articulo.articuloCantidad.toString() else "1.0") }
        var cantidadProductoForApi by remember { mutableDoubleStateOf(0.0) }
        var precioUnitarioIva by remember { mutableStateOf("0.00") }
        var precioUnitarioIvaDesc by remember { mutableStateOf("0.00") }
        var precioProducto by remember {mutableStateOf(if (isAgregar) tipoPrecioSeleccionado.valor else articulo.precio.toString()) }
        var seleccionPresentacion by remember { mutableStateOf("Unidad") }
        var porcentajeDescuentoProducto by remember { mutableStateOf(descuentoCliente) }
        var montoDescuento by remember { mutableStateOf("") }
        var gravado by remember { mutableDoubleStateOf(0.0) }
        var montoIVA by remember { mutableDoubleStateOf(0.0) }
        var esCambioPorMontoDescuento by remember { mutableStateOf(false) }
        var isCalculandoPrecioIva by remember { mutableStateOf(false) }
        var isMenuVisible by remember { mutableStateOf(false) }
        val simboloMonedaArticulo by remember { mutableStateOf(if(codigoMoneda == "CRC") "\u20A1 " else "\u0024 ") }
        var precioMinimoPermitido by remember { mutableDoubleStateOf(0.00) }
        var porcentajeUtilidadMinima by remember { mutableDoubleStateOf(0.00) }
        var detallesAdicionales by remember { mutableStateOf(articulo.articuloSerie) }

        fun calcularTotales() {
            try {
                // Conversión segura de String a BigDecimal
                val cantidad = cantidadProducto.toBigDecimalOrNull() ?: BigDecimal.ZERO
                val precio = precioProducto.toBigDecimalOrNull() ?: BigDecimal.ZERO
                var porcentajeDescuento = porcentajeDescuentoProducto.toBigDecimalOrNull() ?: BigDecimal.ZERO
                var tempMontoDescuento = montoDescuento.toBigDecimalOrNull() ?: BigDecimal.ZERO
                val impuesto = articulo.impuesto.toBigDecimal()

                // Cálculo de cantidad para la API
                cantidadProductoForApi = if (seleccionPresentacion != "Unidad") {
                    cantidad.multiply(BigDecimal(articulo.unidadXMedida)).toDouble()
                } else {
                    cantidad.toDouble()
                }

                // Subtotal sin descuento
                val subtotal = cantidadProductoForApi.toBigDecimal().multiply(precio)

                // Cálculo de descuento
                if (esCambioPorMontoDescuento) {
                    porcentajeDescuento = if (precio.compareTo(BigDecimal.ZERO) != 0) {
                        tempMontoDescuento.multiply(BigDecimal(100)).divide(precio, 2, RoundingMode.HALF_UP)
                    } else {
                        BigDecimal.ZERO
                    }
                    porcentajeDescuentoProducto = porcentajeDescuento.toString()
                } else {
                    tempMontoDescuento = precio.multiply(porcentajeDescuento).divide(BigDecimal(100), 2, RoundingMode.HALF_UP)
                    montoDescuento = tempMontoDescuento.toString()
                }

                // Cálculo del precio unitario con IVA
                if (!isCalculandoPrecioIva) {
                    val factorImpuesto = BigDecimal.ONE.add(impuesto.divide(BigDecimal(100), 4, RoundingMode.HALF_UP))
                    precioUnitarioIva = precio.multiply(factorImpuesto).setScale(2, RoundingMode.HALF_UP).toString()
                }

                // Monto gravado y cálculo de IVA
                gravado = subtotal.subtract(tempMontoDescuento.multiply(cantidad)).toDouble()
                val iva = gravado.toBigDecimal().multiply(impuesto).divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

                montoIVA = iva.toDouble()

                // Calcular el monto del descuento
                val montoDescuentoUnitario = precio.multiply(porcentajeDescuento)
                    .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

                // Precio con descuento
                val precioConDescuentoUnitario = precio.subtract(montoDescuentoUnitario)

                // Calcular el monto del IVA sobre el precio con descuento
                val montoIVAUnitario = precioConDescuentoUnitario.multiply(impuesto)
                    .divide(BigDecimal(100), 2, RoundingMode.HALF_UP)

                // Precio final con descuento e IVA
                precioUnitarioIvaDesc = precioConDescuentoUnitario.add(montoIVAUnitario).setScale(2, RoundingMode.HALF_UP).toString()

                // Restablecer banderas
                isCalculandoPrecioIva = false
                esCambioPorMontoDescuento = false


            } catch (e: Exception) {
                iniciarMenuAgregaEditaArticulo = false
                mostrarMensajeError("Error al calcular totales: ${e.message ?: "Valor no válido"}")
            }
        }

        fun esPrecioValidoConDescuento(): Boolean {
            fun validarParametro(codParametro: String): Boolean {
                return obtenerValorParametroEmpresa(codParametro, "0") != "1"  // SI EL PARAMETRO ES 1 ES QUE NO ES PERMIRTIDO EN ESTE CASO
            }

            val mapaPrecios = mapOf(
                "10" to "153",
                "9" to "154",
                "8" to "155",
                "7" to "156",
                "6" to "157",
                "5" to "158"
            )

            val codigoParametro = mapaPrecios[tipoPrecioSeleccionado.tipo]

            return codigoParametro?.let { validarParametro(it) } ?: true
        }

        LaunchedEffect(Unit) {
            if(!esPrecioValidoConDescuento()){
                montoDescuento = ""
                porcentajeDescuentoProducto = ""
            }
            porcentajeUtilidadMinima = obtenerValorParametroEmpresa("16", "0").toDouble()
            precioMinimoPermitido = if(porcentajeUtilidadMinima == 0.00) 0.01 else (articulo.costo * porcentajeUtilidadMinima/100.00) + articulo.costo
            calcularTotales()
            val precioConDesc = precioProducto.ifEmpty {"0.00"}.toDouble() - montoDescuento.ifEmpty { "0.00" }.toDouble()
            if (precioConDesc < precioMinimoPermitido)  {
                mostrarMensajeError("El $porcentajeDescuentoProducto% de descuento supera la utilidad mínima, el precio de venta minimo con o sin descuento es de: $simboloMoneda${separacionDeMiles(precioMinimoPermitido)} $codMonedaProforma. Por lo tanto, tanto el porcentaje como el monto de descuento se ajustarán a 0 para que pueda ingresar un descuento válido.")
                montoDescuento = ""
                porcentajeDescuentoProducto = ""
                calcularTotales()
            }
        }

        LaunchedEffect(Unit) {
            delay(100)
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isMenuVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
            ) {
                Card(
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(400))
                        .wrapContentHeight()
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        }
                        .clickable(enabled = false) { }
                        .padding(
                            vertical = objetoAdaptardor.ajustarAncho(8),
                            horizontal = objetoAdaptardor.ajustarAltura(16)
                        ),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16)),
                        horizontalAlignment = Alignment.Start
                    ) {

                        TText(
                            text = if (seleccionPresentacion == "Unidad") "Unidad ${articulo.descripcion}" else  "Caja ${articulo.descripcion}" ,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = obtenerEstiloHeadMedium(),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )

                        TText(
                            text = "Código: ${articulo.codigo}",
                            fontSize = obtenerEstiloBodyBig(),
                            fontWeight = FontWeight.Light
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        LazyColumn(
                            modifier = Modifier.wrapContentSize()
                        ) {
                            item {
                                Column(
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    TText(
                                        text = "Bodega:",
                                        fontSize = obtenerEstiloBodyBig(),
                                        modifier = Modifier.fillMaxWidth(),
                                        fontWeight = FontWeight.Light,
                                        color = Color.DarkGray,
                                        textAlign = TextAlign.Start
                                    )

                                    TextFieldMultifuncional(
                                        label = "Bodega",
                                        textPlaceholder = if (bodegaSeleccionda.valor.isEmpty()) "Sin Bodegas" else "Selccione una bodega.",
                                        nuevoValor2 = {
                                            if(obtenerValorParametroEmpresa("102","0") == "0") return@TextFieldMultifuncional mostrarMensajeError("El parámetro 102 no permite seleccionar otra bodega.")
                                            bodegaSeleccionda = it
                                        },
                                        valor = bodegaSeleccionda.valor,
                                        contieneOpciones = true,
                                        usarOpciones4 = true,
                                        opciones4 = articulo.listaBodegas,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                2.dp,
                                                color = Color.Gray,
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                            ),
                                        mostrarLeadingIcon = true,
                                        leadingIcon = Icons.Default.Warehouse,
                                        isUltimo = true,
                                        medidaAncho = 350,
                                        tomarAnchoMaximo = false,
                                        fontSize = obtenerEstiloBodyBig(),
                                        mostrarLabel = false,
                                        onFocus = {
                                            if(obtenerValorParametroEmpresa("102","0") == "0") mostrarMensajeError("El parámetro 102 no permite seleccionar otra bodega.")
                                        }
                                    )

                                    // DATOS BODEGA
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(objetoAdaptardor.ajustarAltura(4)),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        TText(
                                            text = "Código: ${bodegaSeleccionda.clave}",
                                            fontSize = obtenerEstiloBodyBig(),
                                            modifier = Modifier
                                                .weight(1f),
                                            textAlign = TextAlign.Start
                                        )
                                        TText(
                                            text = "Existencia: ${bodegaSeleccionda.existencia}",
                                            modifier = Modifier
                                                .weight(1f),
                                            fontSize = obtenerEstiloBodyBig(),
                                            textAlign = TextAlign.End
                                        )
                                    }

                                    // Tipo medida Y TIPO PRECIO
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                                    ){
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ){
                                            TText(
                                                text = "Tipo de Medida:",
                                                fontSize = obtenerEstiloBodyMedium(),
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
                                            )
                                            TextFieldMultifuncional(
                                                label = "Medida",
                                                textPlaceholder = "Medida",
                                                nuevoValor = {
                                                    seleccionPresentacion = it
                                                    calcularTotales()
                                                },
                                                valor = seleccionPresentacion,
                                                contieneOpciones = true,
                                                usarOpciones3 = true,
                                                opciones3 = opcionesPresentacion,
                                                usarModifierForSize = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        2.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                12
                                                            )
                                                        )
                                                    ),
                                                mostrarLeadingIcon = true,
                                                leadingIcon = Icons.Default.Straighten,
                                                isUltimo = true,
                                                medidaAncho = 350,
                                                tomarAnchoMaximo = false,
                                                fontSize = obtenerEstiloBodyBig(),
                                                mostrarLabel = false,
                                                cantidadLineas = 1
                                            )
                                        }

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Tipo de Precio:",
                                                fontSize = obtenerEstiloBodyMedium(),
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
                                            )
                                            TextFieldMultifuncional(
                                                label = "",
                                                textPlaceholder = "Precio",
                                                nuevoValor2 = {
                                                    if(!tienePermiso("005")) return@TextFieldMultifuncional mostrarMensajeError("No posee el permiso 005 para cambiar el precio de venta.")
                                                    if (it.tipo != "1" &&  !tienePermiso( if(it.tipo=="10") "040" else "03${it.tipo}" ) ) return@TextFieldMultifuncional mostrarMensajeError("No cuenta con el permiso ${if(it.tipo=="10") "040" else "03${it.tipo}"} para modificar el tipo de precio a ${it.tipo}.")
                                                    if (obtenerValorParametroEmpresa("278", "0") == "1"){
                                                        porcentajeDescuentoProducto = ""
                                                    }// quitar descuento al cambiar el tipo de precio
                                                    tipoPrecioSeleccionado = it
                                                    if(!esPrecioValidoConDescuento()){
                                                        montoDescuento = ""
                                                        porcentajeDescuentoProducto = ""
                                                    }
                                                    precioProducto = it.valor
                                                    calcularTotales()
                                                },
                                                valor = tipoPrecioSeleccionado.tipo,
                                                mostrarClave = true,
                                                contieneOpciones = true,
                                                usarOpciones4 = true,
                                                opciones4 = articulo.listaPrecios,
                                                usarModifierForSize = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        2.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                12
                                                            )
                                                        )
                                                    ),
                                                mostrarLeadingIcon = true,
                                                leadingIcon = Icons.Filled.PriceChange,
                                                isUltimo = true,
                                                medidaAncho = 350,
                                                tomarAnchoMaximo = false,
                                                fontSize = obtenerEstiloBodyBig(),
                                                mostrarLabel = false,
                                                cantidadLineas = 1
                                            )
                                        }
                                    }

                                    // PRECIO Y CANTIDAD
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                                    ) {
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Cantidad:",
                                                fontSize = obtenerEstiloBodyMedium(),
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
                                            )
                                            TextFieldMultifuncional(
                                                nuevoValor = {
                                                    cantidadProducto = it.trim()
                                                    calcularTotales()
                                                },
                                                valor = cantidadProducto,
                                                usarModifierForSize = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        2.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                12
                                                            )
                                                        )
                                                    ),
                                                isUltimo = true,
                                                fontSize = obtenerEstiloBodyBig(),
                                                cantidadLineas = 1,
                                                mostrarPlaceholder = true,
                                                textPlaceholder = "0.00",
                                                mostrarLabel = false,
                                                permitirPuntosDedimales = articulo.fraccionamiento == 1,
                                                soloPermitirValoresNumericos = true,
                                                onFocus = {
                                                    cantidadProducto = ""
                                                    calcularTotales()
                                                }
                                            )
                                        }

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Precio:",
                                                fontSize = obtenerEstiloBodyMedium(),
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
                                            )
                                            TextFieldMultifuncional(
                                                nuevoValor = {
                                                    if(!tienePermiso("003")) return@TextFieldMultifuncional mostrarMensajeError("No posee el permiso 003 para actualizar el precio de venta.")
                                                    if(!tienePermiso("024")) return@TextFieldMultifuncional mostrarMensajeError("No posee el permiso 024 para editar el precio de venta desde facturación.")
                                                    porcentajeDescuentoProducto = ""
                                                    precioProducto = it.trim()
                                                    calcularTotales()
                                                },
                                                valor = precioProducto,
                                                usarModifierForSize = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        2.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                12
                                                            )
                                                        )
                                                    ),
                                                isUltimo = true,
                                                fontSize = obtenerEstiloBodyBig(),
                                                cantidadLineas = 1,
                                                mostrarPlaceholder = true,
                                                textPlaceholder = "0.00",
                                                mostrarLabel = false,
                                                soloPermitirValoresNumericos = true,
                                                darFormatoMiles = true,
                                                permitirPuntosDedimales = true
                                            )
                                        }
                                    }

                                    TText(
                                        text = "Precio (con IVA ${articulo.impuesto}%): ",
                                        fontSize = obtenerEstiloBodyMedium(),
                                        fontWeight = FontWeight.Light,
                                        color = Color.DarkGray
                                    )
                                    TextFieldMultifuncional(
                                        nuevoValor = {
                                            if(!tienePermiso("003")) return@TextFieldMultifuncional mostrarMensajeError("No posee el permiso 003 para actualizar el precio de venta.")
                                            if(!tienePermiso("024")) return@TextFieldMultifuncional mostrarMensajeError("No posee el permiso 024 para editar el precio de venta desde facturación.")
                                            isCalculandoPrecioIva = true
                                            porcentajeDescuentoProducto = ""
                                            precioUnitarioIva = it.trim()
                                            val precioUnitarioIvaTemp = precioUnitarioIva.toDoubleOrNull() ?: 0.00
                                            precioProducto = (precioUnitarioIvaTemp/(1.00+articulo.impuesto/100)).toString()
                                            calcularTotales()
                                        },
                                        valor = precioUnitarioIva,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                2.dp,
                                                color = Color.Gray,
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                            ),
                                        isUltimo = true,
                                        fontSize = obtenerEstiloBodyBig(),
                                        cantidadLineas = 1,
                                        mostrarPlaceholder = true,
                                        textPlaceholder = "0.00",
                                        mostrarLabel = false,
                                        darFormatoMiles = true,
                                        soloPermitirValoresNumericos = true
                                    )

                                    // Descuento
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(15.dp)
                                    ) {

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Descuento (%)",
                                                fontSize = obtenerEstiloBodyMedium(),
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
                                            )
                                            TextFieldMultifuncional(
                                                label = "Descuento",
                                                textPlaceholder = "0.00",
                                                nuevoValor = {
                                                    if (!tienePermiso("004")) return@TextFieldMultifuncional mostrarMensajeError("No cuenta con el permiso 004 para modificar el descuento de los artículos.")
                                                    if(!esPrecioValidoConDescuento()) return@TextFieldMultifuncional mostrarMensajeError("No se puede aplicar un descuento a un artículo con precio ${tipoPrecioSeleccionado.tipo}")
                                                    val porcentajeTemp = if(it.trim().isEmpty()) 0.00 else it.trim().toDouble()
                                                    val precioConDesc = precioProducto.ifEmpty { "0.00" }.toDouble() - (precioProducto.ifEmpty { "0.00" }.toDouble() * porcentajeTemp / 100)
                                                    if ( !tienePermiso("023") && porcentajeTemp>articulo.descuentoAdmitido) return@TextFieldMultifuncional mostrarMensajeError("No cuenta con el permiso 023 para aplicar Descuento ilimitado, el descuento máximo es de: ${articulo.descuentoAdmitido}%")
                                                    if (porcentajeTemp > 0.00 && (precioConDesc < precioMinimoPermitido) ) return@TextFieldMultifuncional  if (porcentajeUtilidadMinima == 0.00) mostrarMensajeError("El descuento del $porcentajeTemp% supera el precio mínimo de venta, que es $simboloMoneda ${separacionDeMiles(precioMinimoPermitido)} $codMonedaProforma, ya sea con o sin aplicar descuentos.") else mostrarMensajeError("El $porcentajeTemp% de descuento supera la utilidad mínima, el precio de venta minimo con o sin descuento es de: $simboloMoneda${separacionDeMiles(precioMinimoPermitido)} $codMonedaProforma")
                                                    porcentajeDescuentoProducto = it.trim()
                                                    calcularTotales()
                                                },
                                                valor = porcentajeDescuentoProducto,
                                                usarModifierForSize = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        2.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                12
                                                            )
                                                        )
                                                    ),
                                                isUltimo = true,
                                                medidaAncho = 350,
                                                tomarAnchoMaximo = false,
                                                fontSize = obtenerEstiloBodyBig(),
                                                mostrarLabel = false,
                                                soloPermitirValoresNumericos = true,
                                                permitirPuntosDedimales = true,
                                                cantidadLineas = 1
                                            )
                                        }

                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Monto Descuento:",
                                                fontSize = obtenerEstiloBodyMedium(),
                                                fontWeight = FontWeight.Light,
                                                color = Color.DarkGray
                                            )
                                            TextFieldMultifuncional(
                                                valor = montoDescuento,
                                                nuevoValor = {
                                                    if (!tienePermiso("004")) return@TextFieldMultifuncional mostrarMensajeError("No cuenta con el permiso 004 para modificar el descuento de los artículos.")
                                                    if(!esPrecioValidoConDescuento()) return@TextFieldMultifuncional mostrarMensajeError("No se puede aplicar un descuento a un artículo con precio ${tipoPrecioSeleccionado.tipo}")
                                                    val montoTemp = if(it.trim().isEmpty()) 0.00 else it.trim().toDouble()
                                                    if ( !tienePermiso("023") && montoTemp>articulo.descuentoAdmitido) return@TextFieldMultifuncional mostrarMensajeError("No cuenta con el permiso 023 para aplicar Descuento ilimitado, el descuento máximo es de: ${articulo.descuentoAdmitido}%")
                                                    val precioConDesc = precioProducto.ifEmpty { "0.00" }.toDouble() - montoTemp
                                                    if (montoTemp > 0.00 && (precioConDesc < precioMinimoPermitido) ) return@TextFieldMultifuncional  if (porcentajeUtilidadMinima == 0.00) mostrarMensajeError("El monto $simboloMoneda${separacionDeMiles(montoTemp)} $codMonedaProforma de descuento supera el precio mínimo de venta, que es $simboloMoneda${separacionDeMiles(precioMinimoPermitido)} $codMonedaProforma, ya sea con o sin aplicar descuentos.") else mostrarMensajeError("El monto $simboloMoneda${separacionDeMiles(montoTemp)} $codMonedaProforma de descuento supera la utilidad mínima, el precio de venta minimo con o sin descuento es de: $simboloMoneda${separacionDeMiles(precioMinimoPermitido)} $codMonedaProforma")
                                                    montoDescuento = it.trim()
                                                    esCambioPorMontoDescuento = true
                                                    calcularTotales()
                                                },
                                                usarModifierForSize = true,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        2.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                12
                                                            )
                                                        )
                                                    ),
                                                isUltimo = true,
                                                fontSize = obtenerEstiloBodyBig(),
                                                cantidadLineas = 1,
                                                mostrarPlaceholder = true,
                                                textPlaceholder = "0.00",
                                                mostrarLabel = false,
                                                soloPermitirValoresNumericos = true,
                                                darFormatoMiles = true,
                                                permitirPuntosDedimales = true
                                            )
                                        }
                                    }

                                    TText(
                                        text = "Detalles adicionales: ",
                                        fontSize = obtenerEstiloBodyMedium(),
                                        fontWeight = FontWeight.Light,
                                        color = Color.DarkGray
                                    )
                                    TextFieldMultifuncional(
                                        nuevoValor = {
                                            detallesAdicionales = it
                                        },
                                        valor = detallesAdicionales,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                2.dp,
                                                color = Color.Gray,
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                            ),
                                        isUltimo = true,
                                        fontSize = obtenerEstiloBodyBig(),
                                        cantidadLineas = 2,
                                        mostrarPlaceholder = true,
                                        textPlaceholder = "Serie, Notas, etc..",
                                        mostrarLabel = false
                                    )

                                    Spacer(modifier = Modifier.height(4.dp))

                                    // Totales
                                    Column(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            TText(
                                                text = "P/U (desc. + IVA): ",
                                                textAlign = TextAlign.Start,
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            TText(
                                                text = simboloMonedaArticulo + separacionDeMiles(montoString = precioUnitarioIvaDesc, isString = true) +" $codigoMoneda",
                                                textAlign = TextAlign.End,
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }
                                        HorizontalDivider(modifier = Modifier.padding(vertical = objetoAdaptardor.ajustarAltura(4)))
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            TText(
                                                text = "Gravado: ",
                                                textAlign = TextAlign.Start,
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            TText(
                                                text = simboloMonedaArticulo + separacionDeMiles(gravado) +" $codigoMoneda",
                                                textAlign = TextAlign.End,
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }
                                        HorizontalDivider(modifier = Modifier.padding(vertical = objetoAdaptardor.ajustarAltura(4)))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            TText(
                                                text = "IVA (${articulo.impuesto}%): " ,
                                                textAlign = TextAlign.End,
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            TText(
                                                text = "$simboloMonedaArticulo${separacionDeMiles(montoIVA)} $codigoMoneda",
                                                textAlign = TextAlign.End,
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }
                                        HorizontalDivider(modifier = Modifier.padding(vertical = objetoAdaptardor.ajustarAltura(4)))
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            TText(
                                                text = "Total a pagar: ",
                                                textAlign = TextAlign.End,
                                                fontSize = obtenerEstiloTitleSmall()
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            TText(
                                                text = "$simboloMonedaArticulo${separacionDeMiles(montoIVA+gravado)} $codigoMoneda",
                                                textAlign = TextAlign.End,
                                                fontSize = obtenerEstiloTitleMedium()
                                            )
                                        }
                                    }

                                    // Botones de acción
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        BButton(
                                            text = "Cancelar",
                                            objetoAdaptardor = objetoAdaptardor,
                                            onClick = {
                                                onDismiss()
                                            },
                                            modifier = Modifier.weight(1f),
                                            backgroundColor = Color.Red,
                                            textSize = obtenerEstiloTitleBig()
                                        )

                                        BButton(
                                            text = "Guardar",
                                            objetoAdaptardor = objetoAdaptardor,
                                            onClick = {
                                                cantidadProducto = cantidadProducto.ifEmpty { "0.00" }
                                                precioProducto = precioProducto.ifEmpty { "0.00" }
                                                montoDescuento = montoDescuento.ifEmpty { "0.00" }
                                                porcentajeDescuentoProducto = porcentajeDescuentoProducto.ifEmpty { "0.00" }
                                                if (precioProducto.toDouble() < precioMinimoPermitido ) return@BButton  mostrarMensajeError("El precio de venta minimo es de : ${separacionDeMiles(precioMinimoPermitido)}")
                                                if (cantidadProducto.toDouble() > 0.00 && precioProducto.toDouble() > 0.00){
                                                    val articuloTemp = ArticuloLineaProforma(
                                                        numero = numeroProforma,
                                                        articuloLine = articulo.articuloLineaId,
                                                        tipoDocumento = tipoDocumento,
                                                        articuloCodigo = articulo.codigo,
                                                        articuloTipoPrecio = tipoPrecioSeleccionado.tipo,
                                                        articuloActividadEconomica = articulo.actividadEconomica,
                                                        articuloCantidad = cantidadProductoForApi,
                                                        articuloUnidadMedida = articulo.unidadMedida,
                                                        presentacion = seleccionPresentacion,
                                                        articuloSerie = detallesAdicionales,
                                                        articuloBodegaCodigo = bodegaSeleccionda.clave,
                                                        articuloVenta = precioProducto.toDouble(),
                                                        articuloVentaSubTotal1 = precioProducto.toDouble(),
                                                        articuloDescuentoPorcentage = porcentajeDescuentoProducto.toDouble(),
                                                        articuloVentaSubTotal2 = precioProducto.toDouble(),
                                                        articuloVentaSubTotal3 = precioProducto.toDouble(),
                                                        articuloIvaPorcentage = articulo.impuesto,
                                                        articuloIvaTarifa = articulo.Cod_Tarifa_Impuesto,
                                                        idCliente = clienteId,
                                                        articuloVentaGravado = precioProducto.toDouble(),
                                                        articuloVentaTotal = precioProducto.toDouble()
                                                    )
                                                    agregaEditaArticulo(articuloTemp)
                                                }else{
                                                    if (cantidadProducto.toDouble() == 0.00){
                                                        cantidadProducto = ""
                                                        mostrarMensajeError("La cantidad del artículo deber ser mayor a '0.00'.")
                                                    }else{
                                                        precioProducto = ""
                                                        mostrarMensajeError("El Precio del artículo deber ser mayor a '0.00'.")
                                                    }

                                                }
                                            },
                                            modifier = Modifier.weight(1f),
                                            backgroundColor = Color(0xFF244BC0),
                                            textSize = obtenerEstiloTitleBig()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SegmentedButton(
        options: List<String>,
        selectedOption: String,
        onOptionSelected: (String) -> Unit,
        modifier: Modifier = Modifier
    ) {
        Row(
            modifier = modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)))
                .background(Color(0xFFEEEEEE)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                BButton(
                    text = option,
                    contenteColor = if (isSelected) Color(0xFF244BC0) else Color.Gray,
                    backgroundColor = if (isSelected) Color.White else Color(0xFFEEEEEE),
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier
                        .padding(
                            vertical = objetoAdaptardor.ajustarAncho(2),
                            horizontal = objetoAdaptardor.ajustarAltura(4)
                        )
                        .weight(1f),
                    onClick = {
                        onOptionSelected(option)
                    },
                    conSombra = isSelected
                )
            }
        }
    }

    // Aplica colores de fondo visibles en las áreas del sistema
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fondo para la status bar
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(WindowInsets.statusBars.asPaddingValues().calculateTopPadding())
                .background(Color(0xFF244BC0))
                .align(Alignment.TopCenter)
        )
        // Tu layout principal
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .background(Color.White)
        ) {
            val (bxSuperior, lzColumPrincipal) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0xFF244BC0))
                    .constrainAs(bxSuperior) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(lzColumPrincipal.top)
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(bottom = objetoAdaptardor.ajustarAltura(6))
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                focusManager.clearFocus()
                            })
                        }
                ) {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
                            navController.popBackStack()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Flecha atras",
                            tint = Color.White,
                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = "Icono Facturación",
                        tint = Color.White,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                    Text(
                        if(estadoProforma != "2") {
                            if (!isCrearProforma) {
                                "Facturación"
                            } else "Proforma"
                        } else "Factura",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = obtenerEstiloDisplayBig(),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            actualizarDatosProforma = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Refresh,
                            contentDescription = "Flecha atras",
                            tint = Color.White,
                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(lzColumPrincipal) {
                        start.linkTo(parent.start)
                        top.linkTo(bxSuperior.bottom)
                    }
            ) {
                item {
                    AnimatedVisibility(
                        visible = errorCargarProforma,
                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                    ){
                        Column(
                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Dangerous,
                                contentDescription = "ICONO DE PELIGRO",
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50)),
                                tint = Color(0xFFEB4242)
                            )

                            TText(
                                text = "Ha ocurrido un error en la respuesta del servidor.",
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(250)),
                                fontSize = obtenerEstiloTitleMedium(),
                                maxLines = 5,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                item {
                    AnimatedVisibility(
                        visible = !errorCargarProforma,
                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top,
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(objetoAdaptardor.ajustarAltura(8))
                        ) {

                            // PANEL DE CARGA BOTONES SUPERIORES
                            if (isCargandoDatos){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(objetoAdaptardor.ajustarAltura(35))
                                        .background(
                                            brush,
                                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4))
                                        )
                                )
                            }

                            // BOTONES SUPERIORES
                            AnimatedVisibility(
                                visible = !isCargandoDatos,
                                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                ) {
                                    BButton(
                                        modifier = Modifier
                                            .weight(1f),
                                        text = "Abrir",
                                        onClick = {
                                            iniciarMenuSeleccionarProforma = true
                                        },
                                        textSize = obtenerEstiloBodyBig(),
                                        objetoAdaptardor = objetoAdaptardor
                                    )
                                    BButton(
                                        modifier = Modifier
                                            .weight(1f),
                                        text = "Guardar",
                                        onClick = {
                                            guardarProformaBorrador = true
                                        },
                                        textSize = obtenerEstiloBodyBig(),
                                        objetoAdaptardor = objetoAdaptardor
                                    )
                                    BButton(
                                        modifier = Modifier
                                            .weight(1f),
                                        text = "Clonar",
                                        onClick = {
                                            iniciarMenuClonarProforma = true
                                        },
                                        textSize = obtenerEstiloBodyBig(),
                                        objetoAdaptardor = objetoAdaptardor
                                    )
                                }
                            }

                            // CARD DE DATOS CLIENTE
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(
                                        top = objetoAdaptardor.ajustarAltura(8),
                                        bottom = objetoAdaptardor.ajustarAltura(8)
                                    )
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                if (isCargandoDatos || soloActualizarDatosCliente){
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ){
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .width(objetoAdaptardor.ajustarAncho(250))
                                                    .height(objetoAdaptardor.ajustarAltura(20))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(4)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .width(objetoAdaptardor.ajustarAncho(200))
                                                    .height(objetoAdaptardor.ajustarAltura(20))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(4)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )

                                            Row (
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                            ){
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(20))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(4)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(4)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(4)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(objetoAdaptardor.ajustarAltura(35))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(4)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )
                                            Box(
                                                modifier = Modifier
                                                    .width(objetoAdaptardor.ajustarAncho(100))
                                                    .height(objetoAdaptardor.ajustarAltura(35))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(4)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = (!isCargandoDatos && !soloActualizarDatosCliente),
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ){
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4)),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ) {
                                        TText(
                                            text = "Doc: $numeroProforma",
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            fontSize = obtenerEstiloTitleMedium()
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4)),
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                                Icon(
                                                    imageVector = Icons.Default.CalendarMonth,
                                                    contentDescription = "ICONO DE CLIENTE"
                                                )
                                            TText(
                                                text = "Fecha: $fechaProforma",
                                                fontSize = obtenerEstiloTitleSmall()
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PersonPin,
                                                contentDescription = "ICONO DE CLIENTE"
                                            )
                                            TText(
                                                text = "Información del cliente",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = objetoAdaptardor.ajustarAncho(4))
                                                , fontSize = obtenerEstiloTitleBig()
                                            )
                                        }

                                        Row (
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                        ){
                                            TText(
                                                text = "Cuenta: $clienteId",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            BButton(
                                                text = "Opciones",
                                                onClick = {
                                                    if (validarEstadoProforma()) return@BButton
                                                    iniciarMenuOpcionesCliente = true
                                                },
                                                modifier = Modifier
                                                    .weight(0.8f),
                                                textSize = obtenerEstiloBodyBig(),
                                                objetoAdaptardor = objetoAdaptardor,

                                                )
                                            BButton(
                                                text = "Buscar",
                                                onClick = {
                                                    if (validarEstadoProforma()) return@BButton
                                                    if(!tienePermiso("310")) return@BButton mostrarMensajeError("No tiene permiso 310 para acceder al modulo de Clientes")
                                                    if(!tienePermiso("313")) return@BButton mostrarMensajeError("No tiene permiso 313 para Consultar la lista de Clientes.")
                                                    datosIngresadosBarraBusquedaCliente = ""
                                                    listaClientesEncontrados = emptyList()
                                                    iniciarMenuSeleccionarCliente = true
                                                },
                                                modifier = Modifier
                                                    .weight(0.8f),
                                                textSize = obtenerEstiloBodyBig(),
                                                objetoAdaptardor = objetoAdaptardor
                                            )
                                        }

                                        BasicTexfiuldWithText(
                                            textTitle = "Nombre de Factura:",
                                            text = "Nombre de Factura",
                                            variable = nombreFactura,
                                            nuevoValor = {nombreFactura=it},
                                            icon = Icons.Default.PersonOutline
                                        )

                                        AnimatedVisibility(
                                            visible = expandedClientes,
                                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                            ) {
                                                BasicTexfiuldWithText(
                                                    textTitle = "Nombre Comercial:",
                                                    text = "Nombre Comercial",
                                                    variable = nombreComercial,
                                                    nuevoValor = {nombreComercial=it},
                                                    icon = Icons.Default.Person
                                                )

                                                BasicTexfiuldWithText(
                                                    textTitle = "Nombre Jurídico:",
                                                    text = "Nombre Jurídico",
                                                    variable = nombreCliente,
                                                    nuevoValor = {nombreCliente=it},
                                                    icon = Icons.Default.PersonPin
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Tipo de Cédula:",
                                                    text = "Tipo de Cédula",
                                                    variable = listaTipoCedula.find { it.clave == tipoCedula }?.valor ?: "No valido",
                                                    nuevoValor = {tipoCedula=it},
                                                    icon = Icons.Default.Badge
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Cédula:",
                                                    text = "Cédula",
                                                    variable = numeroCedula,
                                                    nuevoValor = {numeroCedula=it},
                                                    icon = Icons.Default.AccountBox
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Email General:",
                                                    text = "Email General",
                                                    variable = emailGeneral,
                                                    nuevoValor = {emailGeneral=it},
                                                    icon = Icons.Default.Email
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Teléfonos:",
                                                    text = "Teléfonos",
                                                    variable = telefonos,
                                                    nuevoValor = {telefonos=it},
                                                    icon = Icons.Default.Phone
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Plazo de Crédito en Días:",
                                                    text = "Plazo de Crédito en Días",
                                                    variable = plazoCredito,
                                                    nuevoValor = {plazoCredito=it},
                                                    icon = Icons.Default.Payments
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Tipo de Precio:",
                                                    text = "Tipo de Precio",
                                                    variable = tipoPrecioCliente,
                                                    nuevoValor = {plazoCredito=it},
                                                    icon = Icons.Filled.LocalOffer
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Monto Crédito:",
                                                    text = "Monto Crédito",
                                                    variable = montoCredito,
                                                    nuevoValor = {plazoCredito=it},
                                                    icon = Icons.Filled.CreditCard,
                                                    darFormatomiles = true
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Monto Contrato:",
                                                    text = "Monto Contrato",
                                                    variable = montoContrato,
                                                    nuevoValor = {plazoCredito=it},
                                                    icon = Icons.Filled.RequestQuote,
                                                    darFormatomiles = true
                                                )
                                                BasicTexfiuldWithText(
                                                    textTitle = "Descuento(%):",
                                                    text = "Descuento(%)",
                                                    variable = descuentoCliente,
                                                    nuevoValor = {plazoCredito=it},
                                                    icon = Icons.Filled.Percent
                                                )
                                            }
                                        }
                                        BButton(
                                            text =  if (expandedClientes) "Mostrar menos" else "Mostrar más",
                                            onClick = {expandedClientes = !expandedClientes},
                                            contenteColor = Color(0xFF244BC0),
                                            backgroundColor = Color.White,
                                            conSombra = false,
                                            textSize = obtenerEstiloBodyBig(),
                                            objetoAdaptardor = objetoAdaptardor
                                        )
                                    }
                                }
                            }

                            // CARD DE DATOS ARTICULOS
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(
                                        top = objetoAdaptardor.ajustarAltura(8),
                                        bottom = objetoAdaptardor.ajustarAltura(8)
                                    )
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                if (isCargandoDatos){
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ){
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                        ){
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(objetoAdaptardor.ajustarAltura(30))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(6)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4)),
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(objetoAdaptardor.ajustarAltura(2))
                                            ){
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1.25f)
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = (!isCargandoDatos),
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ){
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ){
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8)),
                                            modifier = Modifier.padding(
                                                bottom = objetoAdaptardor.ajustarAltura(8)
                                            )
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Inventory,
                                                contentDescription = "ICONO DE ARTICULOS"
                                            )
                                            TText(
                                                text = "Artículos",
                                                modifier = Modifier
                                                    .weight(1f),
                                                fontSize = obtenerEstiloTitleBig()
                                            )
                                            BButton(
                                                text = "Agregar",
                                                onClick = {
                                                    if (validarEstadoProforma()) return@BButton
                                                    coroutineScope.launch {
                                                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                                                        descargarArticulos()
                                                        delay(250)
                                                        iniciarMenuSeleccionarArticulo = true
                                                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                                                    }

                                                },
                                                modifier = Modifier
                                                    .weight(1f),
                                                textSize = obtenerEstiloBodyBig(),
                                                objetoAdaptardor = objetoAdaptardor
                                            )
                                            BButton(
                                                text = "Opciones",
                                                onClick = {
                                                    if (validarEstadoProforma()) return@BButton
                                                    iniciarMenuOpcionesProforma = true
                                                },
                                                modifier = Modifier
                                                    .weight(1f),
                                                textSize = obtenerEstiloBodyBig(),
                                                objetoAdaptardor = objetoAdaptardor
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(2)),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(Color.LightGray)
                                                .padding(objetoAdaptardor.ajustarAltura(2))
                                        ) {
                                            TText("Descripción", Modifier.weight(1f), textAlign = TextAlign.Center)
                                            TText("Cant", Modifier.weight(0.5f), textAlign = TextAlign.Center)
                                            TText("Precio.Unit", Modifier.weight(1f), textAlign = TextAlign.Center)
                                            TText("Desc", Modifier.weight(0.5f), textAlign = TextAlign.Center)
                                            TText("Total", Modifier.weight(1.25f), textAlign = TextAlign.Center)
                                        }

                                        AnimatedVisibility(
                                            visible = (soloActualizarArticulos),
                                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                        ){
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(6))
                                            ){
                                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(6)))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4)),
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(objetoAdaptardor.ajustarAltura(2))
                                                ){
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .height(objetoAdaptardor.ajustarAltura(30))
                                                            .background(
                                                                brush,
                                                                shape = RoundedCornerShape(
                                                                    objetoAdaptardor.ajustarAltura(6)
                                                                )
                                                            )
                                                            .padding(
                                                                top = objetoAdaptardor.ajustarAncho(8)
                                                            )
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(0.5f)
                                                            .height(objetoAdaptardor.ajustarAltura(30))
                                                            .background(
                                                                brush,
                                                                shape = RoundedCornerShape(
                                                                    objetoAdaptardor.ajustarAltura(6)
                                                                )
                                                            )
                                                            .padding(
                                                                top = objetoAdaptardor.ajustarAncho(8)
                                                            )
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .height(objetoAdaptardor.ajustarAltura(30))
                                                            .background(
                                                                brush,
                                                                shape = RoundedCornerShape(
                                                                    objetoAdaptardor.ajustarAltura(6)
                                                                )
                                                            )
                                                            .padding(
                                                                top = objetoAdaptardor.ajustarAncho(8)
                                                            )
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(0.5f)
                                                            .height(objetoAdaptardor.ajustarAltura(30))
                                                            .background(
                                                                brush,
                                                                shape = RoundedCornerShape(
                                                                    objetoAdaptardor.ajustarAltura(6)
                                                                )
                                                            )
                                                            .padding(
                                                                top = objetoAdaptardor.ajustarAncho(8)
                                                            )
                                                    )
                                                    Box(
                                                        modifier = Modifier
                                                            .weight(1.25f)
                                                            .height(objetoAdaptardor.ajustarAltura(30))
                                                            .background(
                                                                brush,
                                                                shape = RoundedCornerShape(
                                                                    objetoAdaptardor.ajustarAltura(6)
                                                                )
                                                            )
                                                            .padding(
                                                                top = objetoAdaptardor.ajustarAncho(8)
                                                            )
                                                    )
                                                }
                                            }
                                        }

                                        AnimatedVisibility(
                                            visible = (!soloActualizarArticulos),
                                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                        ){
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.Center
                                            ){
                                                listaArticulosProforma.forEach { articulo ->
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(2)),
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .clickable {
                                                                coroutineScope.launch {
                                                                    if (validarEstadoProforma()) return@launch
                                                                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(
                                                                        true
                                                                    )
                                                                    descargarArticulos()
                                                                    delay(300)
                                                                    val articuloTemp1 =
                                                                        gestorTablaArticulos.obetenerDatosArticulo(
                                                                            articulo.codigo
                                                                        )
                                                                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(
                                                                        false
                                                                    )
                                                                    delay(300)
                                                                    if (articuloTemp1 == null) return@launch mostrarMensajeError(
                                                                        "No se logró encontrar las bodegas y los precios de este artículo actualice y vuelva a intentar."
                                                                    )
                                                                    val articuloTemp =
                                                                        deserializarArticulo(
                                                                            articuloTemp1.datos
                                                                        )
                                                                    val listaBodegas =
                                                                        articuloTemp.listaBodegas
                                                                    val listaPrecios =
                                                                        articuloTemp.listaPrecios
                                                                    articulo.listaBodegas = listaBodegas
                                                                    articulo.listaPrecios = listaPrecios
                                                                    articulo.descuentoAdmitido =
                                                                        articuloTemp.descuentoAdmitido
                                                                    articulo.unidadXMedida =
                                                                        articuloTemp.unidadXMedida
                                                                    articulo.actividadEconomica =
                                                                        articuloTemp.actividadEconomica
                                                                    articulo.Cod_Tarifa_Impuesto =
                                                                        articuloTemp.codTarifaImpuesto
                                                                    articulo.unidadMedida =
                                                                        articuloTemp.unidadMedida
                                                                    articulo.costo = articuloTemp.costo
                                                                    articulo.fraccionamiento =
                                                                        articuloTemp.fraccionamiento
                                                                    articuloActual = articulo
                                                                    isAgregar = false
                                                                    iniciarMenuAgregaEditaArticulo =
                                                                        true
                                                                }
                                                            }
                                                            .padding(
                                                                vertical = objetoAdaptardor.ajustarAltura(
                                                                    4
                                                                )
                                                            )
                                                    ) {
                                                        TText(
                                                            articulo.descripcion,
                                                            Modifier
                                                                .weight(1f)
                                                                .padding(
                                                                    start = objetoAdaptardor.ajustarAncho(
                                                                        2
                                                                    )
                                                                ),
                                                            textAlign = TextAlign.Start,
                                                            maxLines = 3,
                                                            fontFamily = FontFamily(Font(R.font.akshar_regular)),
                                                            fontWeight = null
                                                        )
                                                        TText(
                                                            articulo.articuloCantidad.toString(),
                                                            Modifier.weight(0.5f),
                                                            textAlign =TextAlign.Center,
                                                            maxLines = 3,
                                                            fontFamily = FontFamily(Font(R.font.akshar_regular)),
                                                            fontWeight = null
                                                        )
                                                        TText(
                                                            simboloMoneda + separacionDeMiles(articulo.precioNeto),
                                                            Modifier.weight(1f),
                                                            textAlign = TextAlign.Center,
                                                            maxLines = 3,
                                                            fontFamily = FontFamily(Font(R.font.akshar_regular)),
                                                            fontWeight = null
                                                        )
                                                        TText(
                                                            "${articulo.articuloDescuentoPorcentaje}%",
                                                            Modifier.weight(0.5f),
                                                            textAlign = TextAlign.Center,
                                                            maxLines = 3,
                                                            fontFamily = FontFamily(Font(R.font.akshar_regular)),
                                                            fontWeight = null
                                                        )
                                                        TText(
                                                            simboloMoneda + separacionDeMiles(articulo.articuloVentaTotal),
                                                            Modifier.weight(1f),
                                                            textAlign = TextAlign.End,
                                                            maxLines = 3,
                                                            fontFamily = FontFamily(Font(R.font.akshar_regular)),
                                                            fontWeight = null
                                                        )

                                                        IconButton(
                                                            onClick = {
                                                                if (validarEstadoProforma()) return@IconButton
                                                                lineaAcual = articulo.articuloLineaId
                                                                iniciarMenuConfEliminarArt = true
                                                            },
                                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(18))
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Filled.Delete,
                                                                contentDescription = "Eliminar",
                                                                tint = Color(0xFFEB4242),
                                                                modifier = Modifier
                                                                    .size(
                                                                        objetoAdaptardor.ajustarAltura(
                                                                            18
                                                                        )
                                                                    )
                                                                    .weight(0.25f)
                                                            )
                                                        }
                                                    }

                                                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                            // CARD DE DETALLE FACTURA Y ORDEN DE COMPRA
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(
                                        top = objetoAdaptardor.ajustarAltura(8),
                                        bottom = objetoAdaptardor.ajustarAltura(8)
                                    )
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                if (isCargandoDatos) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ) {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(
                                                objetoAdaptardor.ajustarAltura(
                                                    8
                                                )
                                            )
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    objetoAdaptardor.ajustarAncho(8)
                                                )
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(2f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(objetoAdaptardor.ajustarAltura(30))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(6)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )

                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(
                                                    objetoAdaptardor.ajustarAncho(8)
                                                )
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(2f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }

                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(objetoAdaptardor.ajustarAltura(30))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(6)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = (!isCargandoDatos),
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Description,
                                                contentDescription = "ICONO DE ARTICULOS"
                                            )
                                            TText(
                                                text = "Detalle de Factura",
                                                fontSize = obtenerEstiloTitleBig()
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            BButton(
                                                text = "Guardar",
                                                onClick = {
                                                    if (validarEstadoProforma()) return@BButton
                                                    focusManager.clearFocus()
                                                    guardarDetalleFactura = true
                                                },
                                                conSombra = false,
                                                textSize = obtenerEstiloBodyBig(),
                                                objetoAdaptardor = objetoAdaptardor,
                                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100))
                                            )
                                        }

                                        BBasicTextField(
                                            value = detalleProforma,
                                            onValueChange = {
                                                detalleProforma = it
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            utilizarMedidas = false,
                                            placeholder = "Ingrese el detalle de la factura",
                                            icono = Icons.Default.EditNote,
                                            fontSize = obtenerEstiloBodyBig(),
                                            enable = estadoProforma != "2",
                                            cantidadLineas = 30,
                                            offFocus = {
                                                apiGuardarDetalle?.cancel()
                                                apiGuardarDetalle = coroutineScope.launch {
                                                    val result = objectoProcesadorDatosApi.guardarDestalleFactura(detalleProforma, numeroProforma)
                                                    if (result!=null)
                                                        if (!validarExitoRestpuestaServidor(result)){
                                                            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                                                        }
                                                }
                                            }
                                        )

                                        Spacer(modifier = Modifier.fillMaxWidth())
                                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.ShoppingCart,
                                                contentDescription = "ICONO DE ARTICULOS"
                                            )
                                            TText(
                                                text = "Orden de Compra",
                                                fontSize = obtenerEstiloTitleBig()
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            BButton(
                                                text = "Guardar",
                                                onClick = {
                                                    if (validarEstadoProforma()) return@BButton
                                                    focusManager.clearFocus()
                                                    guardarOrdenCompra = true
                                                },
                                                conSombra = false,
                                                textSize = obtenerEstiloBodyBig(),
                                                objetoAdaptardor = objetoAdaptardor,
                                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100))
                                            )
                                        }

                                        BBasicTextField(
                                            value = ordenCompra,
                                            onValueChange = {
                                                if (ordenCompra.length>30) return@BBasicTextField mostrarMensajeError("La cantidad maxima de caracteres es de 30 para la orden ")
                                                ordenCompra= it
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            utilizarMedidas = false,
                                            placeholder = "Ingrese la Orden de Compra",
                                            icono = Icons.Default.EditNote,
                                            fontSize = obtenerEstiloBodyBig(),
                                            enable = estadoProforma != "2",
                                            cantidadLineas = 30,
                                            offFocus = {
                                                apiGuardarOrdenCompra?.cancel()
                                                apiGuardarOrdenCompra = coroutineScope.launch {
                                                    val result = objectoProcesadorDatosApi.guardarOrdenCompra(ordenCompra, numeroProforma)
                                                    if (result!=null)
                                                        if (!validarExitoRestpuestaServidor(result)){
                                                            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                                                        }
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // CARD DE TOTALES
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(
                                        top = objetoAdaptardor.ajustarAltura(8),
                                        bottom = objetoAdaptardor.ajustarAltura(8)
                                    )
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                if(isCargandoDatos){
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ){
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                        ){
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .height(objetoAdaptardor.ajustarAltura(35))
                                                    .background(
                                                        brush,
                                                        shape = RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(6)
                                                        )
                                                    )
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    )
                                            )
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35))
                                                        .background(
                                                            brush,
                                                            shape = RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(6)
                                                            )
                                                        )
                                                        .padding(
                                                            start = objetoAdaptardor.ajustarAncho(8),
                                                            end = objetoAdaptardor.ajustarAncho(8),
                                                            top = objetoAdaptardor.ajustarAncho(8)
                                                        )
                                                )
                                            }
                                        }
                                    }
                                }

                                AnimatedVisibility(
                                    visible = !isCargandoDatos,
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ){

                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ){
                                        TText(
                                            text = "Totales",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloTitleBig()
                                        )

                                        Row {
                                            TText(
                                                text = "Total Gravado:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            TText(
                                                text = simboloMoneda + separacionDeMiles(totalGravado) +" $codMonedaProforma",
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }

                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        Row {
                                            TText(
                                                text = "Total IVA:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            TText(
                                                text = simboloMoneda + separacionDeMiles(totalIva) +" $codMonedaProforma",
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }

                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        Row {
                                            TText(
                                                text = "Total Descuento:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            TText(
                                                text = simboloMoneda + separacionDeMiles(totalDescuento) +" $codMonedaProforma",
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }

                                        AnimatedVisibility(
                                            visible = expandedTotales,
                                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(
                                                    objetoAdaptardor.ajustarAltura(8)
                                                )
                                            ) {
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Pago:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                    TText(
                                                        text = simboloMoneda + separacionDeMiles(totalPago) +" $codMonedaProforma",
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Total Exonerado:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                    TText(
                                                        text = simboloMoneda + separacionDeMiles(totalExonerado) +" $codMonedaProforma",
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Total IVA Devuelto:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                    TText(
                                                        text = simboloMoneda + separacionDeMiles(totalIvaDevuelto) +" $codMonedaProforma",
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Total Merc Gravado:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                    TText(
                                                        text = simboloMoneda + separacionDeMiles(totalMercGrav) +" $codMonedaProforma",
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f),
                                                        fontSize = obtenerEstiloBodyBig()
                                                    )
                                                }
                                                for (i in listaIvas){
                                                    HorizontalDivider(
                                                        thickness = 1.dp,
                                                        color = Color.LightGray
                                                    )
                                                    Row {
                                                        TText(
                                                            text = "IVA( ${i.clave}% ): ",
                                                            textAlign = TextAlign.Start,
                                                            modifier = Modifier.weight(1f),
                                                            fontSize = obtenerEstiloBodyBig()
                                                        )
                                                        TText(
                                                            text = simboloMoneda + separacionDeMiles(i.valor.toDouble())+" $codMonedaProforma",
                                                            textAlign = TextAlign.End,
                                                            modifier = Modifier.weight(1f),
                                                            fontSize = obtenerEstiloBodyBig()
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        Row {
                                            TText(
                                                text = "Total:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                            TText(
                                                text = simboloMoneda + separacionDeMiles(total) +" $codMonedaProforma",
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f),
                                                fontSize = obtenerEstiloBodyBig()
                                            )
                                        }
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        BButton(
                                            text =  if (expandedTotales) "Mostrar menos" else "Mostrar más",
                                            onClick = {expandedTotales = !expandedTotales},
                                            contenteColor = Color(0xFF244BC0),
                                            backgroundColor = Color.White,
                                            conSombra = false,
                                            textSize = obtenerEstiloBodyBig(),
                                            objetoAdaptardor = objetoAdaptardor
                                        )
                                    }
                                }
                            }

                            if (isCargandoDatos){
                                Box(
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(150))
                                        .height(objetoAdaptardor.ajustarAltura(35))
                                        .background(
                                            brush,
                                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4))
                                        )
                                )
                            }

                            // BOTONES INFERIORES
                            AnimatedVisibility(
                                visible = !isCargandoDatos,
                                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                            ) {
                                BButton(
                                    text = if (estadoProforma =="2") if(valorImpresionActiva=="1") "     Reimprimir     "  else "     Reimpresión Inactiva     " else "     Procesar     ",
                                    onClick = {
                                        if (valorImpresionActiva=="0" && estadoProforma =="2") return@BButton mostrarMensajeError("Impresión inactiva. Si desea imprimir, cambie el estado del parámetro en ajustes.")
                                        if (estadoProforma =="2"){
                                            agregarColaImpresion(isReimpresion = true)
                                            consecutivoFactura = numeroProforma
                                            obtenerDatosFacturaEmitida = true
                                            return@BButton
                                        }
                                        if (listaArticulosProforma.isEmpty()) return@BButton mostrarMensajeError("Por favor, agregue al menos un Artículo para continuar.")
                                        if (isCrearProforma){
                                            iniciarMenuConfComoProforma = true
                                            return@BButton
                                        }
                                        if (clienteId != "SN" && tipoCedula == "00" && obtenerValorParametroEmpresa("353", "0") == "0") return@BButton mostrarMensajeError("No puedo facturar a un Cliente con tipo Cédula: 'No Definido'")
                                        iniciarMenuProcesar = true
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    textSize = obtenerEstiloHeadSmall()
                                )
                            }
                        }
                    }
                }

                item {
                    Box(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(70)))
                }
            }
        }

        // Fondo para la navigation bar
        Box(
            modifier = Modifier
                .background(Color(0xFF000000))
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.Black)
                .align(Alignment.BottomCenter), contentAlignment = Alignment.Center
        ) {
            val versionApp = stringResource(R.string.app_version)

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center

            ){
                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(5)))
                Text(
                    text = "$nombreUsuario _ $nombreEmpresa _ #$codUsuario",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabelBig(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(154))
                        .padding(start = 4.dp)
                )

                Text(
                    text = "Invefacon ©2025",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabelBig(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100))
                )

                Text(
                    text = "Versión: $versionApp",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = obtenerEstiloLabelBig(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(130))
                        .padding(end = 6.dp)
                )
                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(30)))
            }
        }


    }

    if (iniciarMenuSeleccionarArticulo && !iniciarDescargaArticulos) {
        var isMenuVisible by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            delay(100 )
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isMenuVisible,
                enter = fadeIn(animationSpec = tween(300)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(300)) + slideOutVertically(targetOffsetY = { it })
            ) {

                LaunchedEffect(Unit) {
                    if (listaArticulosEncontrados.isNotEmpty()) return@LaunchedEffect
                    delay(100)
                    focusRequester.requestFocus()
                    mostrarTeclado(context)
                }

                Surface(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(24))
                        .align(Alignment.TopCenter),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                    color = Color.White
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        TText(
                            text = "Agregar Artículo",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                        ){
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ){
                                BBasicTextField(
                                    value = datosIngresadosBarraBusquedaArticulos,
                                    onValueChange = {
                                        isCargandoArticulos = true
                                        datosIngresadosBarraBusquedaArticulos = it
                                        if (it.isEmpty()){
                                            isCargandoArticulos = false
                                            listaArticulosEncontrados = emptyList()
                                        }
                                    },
                                    modifier = Modifier
                                        .focusRequester(focusRequester),
                                    objetoAdaptardor = objetoAdaptardor,
                                    utilizarMedidas = false,
                                    fontSize = obtenerEstiloTitleMedium()
                                )
                            }

                            IconButton(
                                onClick = {
                                    if (listaArticulosEncontrados.isEmpty()) datosIngresadosBarraBusquedaArticulos = ""
                                    iniciarMenuSeleccionarArticulo = false
                                    isMenuVisible = false
                                },
                                modifier = Modifier.weight(0.1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                )
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(onTap = {
                                        focusManager.clearFocus()
                                    })
                                }
                                .height(objetoAdaptardor.ajustarAltura(400)),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                if (isCargandoArticulos){
                                    Box(modifier = Modifier
                                        .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ){
                                        CircularProgressIndicator(
                                            color = Color(0xFF244BC0),
                                            modifier = Modifier
                                                .size(objetoAdaptardor.ajustarAltura(50))
                                                .padding(2.dp)
                                        )
                                    }
                                }else{
                                    if (datosIngresadosBarraBusquedaArticulos.isEmpty() || (listaArticulosEncontrados.isEmpty() && !isCargandoArticulos)){
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = if (datosIngresadosBarraBusquedaArticulos.isEmpty()) Icons.Default.Search else Icons.Default.Dangerous,
                                                contentDescription = "ICONO DE PELIGRO",
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50)),
                                                tint = if (datosIngresadosBarraBusquedaArticulos.isEmpty()) Color(0xFF244BC0) else Color(0xFFEB4242)
                                            )

                                            TText(
                                                text = if (datosIngresadosBarraBusquedaArticulos.isEmpty()) "Ingrese datos para iniciar la búsqueda." else "No se encontró ningún artículo relacionado con la palabra '$datosIngresadosBarraBusquedaArticulos'",
                                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200)),
                                                fontSize = obtenerEstiloTitleMedium(),
                                                maxLines = 5,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }else{
                                        listaArticulosEncontrados.forEachIndexed { index, articulo ->
                                            var isArticuloVisible by remember { mutableStateOf(false) }
                                            LaunchedEffect(isArticuloVisible) {
                                                delay(index * 100L) // Retrasa cada artículo en 100ms según su índice
                                                isArticuloVisible = true
                                            }

                                            AnimatedVisibility(
                                                visible = isArticuloVisible,
                                                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it })
                                            ) {
                                                BxContenedorArticulosFacturacion(articulo)
                                            }

                                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16)))
                                        }

                                    }
                                }
                            }
                        }

                        TText(
                            text = "Artículos encontrados: " + listaArticulosEncontrados.size.toString(),
                            fontSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF244BC0)
                        )
                    }
                }
            }
        }
    }

    MenuAgregaEditaArticulo(
        mostrarVentanaArticulo = iniciarMenuAgregaEditaArticulo,
        onDismiss = { iniciarMenuAgregaEditaArticulo = false },
        opcionesPresentacion = if(articuloActual.unidadXMedida>1) listOf("Caja", "Unidad") else listOf("Unidad"),
        objetoAdaptardor = objetoAdaptardor,
        articulo = articuloActual,
        codigoMoneda = codMonedaProforma,
        descuentoCliente = when {
            articuloActual.articuloDescuentoPorcentaje > 0 -> articuloActual.articuloDescuentoPorcentaje.toString() // SI EL ARTICULO YA POSEE UN DESCUENTO SE TOMA ESE
            descuentoCliente.toDouble() > articuloActual.descuentoAdmitido -> articuloActual.descuentoAdmitido.toString() // SI EL DESCUENTO DEL CLIENTE ES MAYOR AL ADMTIDO SE TOMA EL MAXIMNO ADMITIDO
            descuentoCliente.toDouble() > 0.00-> descuentoCliente // SE TOMA EL DESCUENTO DEL CLIENTE YA QUE NO SOBREPASA EL ADMITIDO
            else -> articuloActual.descuentoFijo.toString() // SE TOMA EL DESCUENTO FIJO SI NINGUNA CONDICIONAL SE CUMPLE
        },
        isAgregar = isAgregar,
        agregaEditaArticulo = {
            coroutineScopeAgreEditArt.launch {
                AgregaEditaArticulo(it)
            }
        },
        bodega = if (articuloActual.articuloBodegaCodigo.isEmpty()) articuloActual.listaBodegas.first() else articuloActual.listaBodegas.find { it.clave == articuloActual.articuloBodegaCodigo } ?: ParClaveValor(),
        precioVenta = articuloActual.codPrecioVenta.ifEmpty { tipoPrecioCliente }
    )

    if (iniciarMenuSeleccionarCliente) {
        var isMenuVisible by remember { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }

        LaunchedEffect(Unit) {
            delay(100)
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isMenuVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
            ) {

                LaunchedEffect(Unit) {
                    delay(100)
                    focusRequester.requestFocus()
                    mostrarTeclado(context)
                }

                Surface(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(24))
                        .align(Alignment.TopCenter),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                    color = Color.White
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        TText(
                            text = "Buscar Cliente",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                        ){
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ){
                                BBasicTextField(
                                    value = datosIngresadosBarraBusquedaCliente,
                                    onValueChange = {
                                        datosIngresadosBarraBusquedaCliente = it
                                    },
                                    modifier = Modifier
                                        .focusRequester(focusRequester),
                                    objetoAdaptardor = objetoAdaptardor,
                                    utilizarMedidas = false,
                                    fontSize = obtenerEstiloTitleMedium()
                                )
                            }

                            IconButton(
                                onClick = {
                                    iniciarMenuSeleccionarCliente = false
                                    isMenuVisible = false
                                    datosIngresadosBarraBusquedaCliente = ""
                                },
                                modifier = Modifier.weight(0.1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                )
                            }
                        }

                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ){
                            Box(
                                modifier = Modifier
                                    .height(objetoAdaptardor.ajustarAltura(12))
                                    .width(objetoAdaptardor.ajustarAncho(12))
                                    .background(Color(0xFF31BF59))
                            )
                            TText(
                                text = "Activo",
                                fontSize = obtenerEstiloBodySmall(),
                                textAlign = TextAlign.Start
                            )
                            Box(
                                modifier = Modifier
                                    .height(objetoAdaptardor.ajustarAltura(12))
                                    .width(objetoAdaptardor.ajustarAncho(12))
                                    .background(Color(0xFFF3ED00))
                            )
                            TText(
                                text = "Suspendido",
                                fontSize = obtenerEstiloBodySmall(),
                                textAlign = TextAlign.Start
                            )

                        }

                        LazyColumn(
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(400)),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                if (isCargandoClientes){
                                    Box(modifier = Modifier
                                        .fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ){
                                        CircularProgressIndicator(
                                            color = Color(0xFF244BC0),
                                            modifier = Modifier
                                                .size(objetoAdaptardor.ajustarAltura(50))
                                                .padding(2.dp)
                                        )
                                    }
                                }else{
                                    if (datosIngresadosBarraBusquedaCliente.isEmpty() || (listaClientesEncontrados.isEmpty() && !isCargandoClientes)){
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.fillMaxWidth()
                                        ) {
                                            Icon(
                                                imageVector = if (datosIngresadosBarraBusquedaCliente.isEmpty()) Icons.Default.Search else Icons.Default.Dangerous,
                                                contentDescription = "ICONO DE PELIGRO",
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50)),
                                                tint = if (datosIngresadosBarraBusquedaCliente.isEmpty()) Color(0xFF244BC0) else Color(0xFFEB4242)
                                            )

                                            TText(
                                                text = if (datosIngresadosBarraBusquedaCliente.isEmpty()) "Ingrese datos para iniciar la búsqueda." else "No se encontró ningún cliente relacionado con la palabra '$datosIngresadosBarraBusquedaCliente'",
                                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200)),
                                                fontSize = obtenerEstiloTitleMedium(),
                                                maxLines = 5,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }else{
                                        listaClientesEncontrados.forEachIndexed { index, cliente ->
                                            var isClienteVisible by remember { mutableStateOf(false) }
                                            LaunchedEffect(isClienteVisible) {
                                                delay(index * 100L)
                                                isClienteVisible = true
                                            }

                                            AnimatedVisibility(
                                                visible = isClienteVisible,
                                                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it })
                                            ) {
                                                BxContenedorClienteFacturacion(cliente)
                                            }

                                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16)))
                                        }

                                    }
                                }
                            }
                        }
                        TText(
                            text = "Clientes encontrados: " + listaClientesEncontrados.size.toString(),
                            fontSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            color = Color(0xFF244BC0)
                        )
                    }
                }
            }
        }
    }

    if (iniciarMenuSeleccionarProforma || iniciarMenuClonarProforma) {
        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            buscarProformas = true
            delay(100)
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(objetoAdaptardor.ajustarAncho(350))
                            .height(objetoAdaptardor.ajustarAltura(600)),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                    ) {
                        // Barra de búsqueda
                        TText(
                            text = if (iniciarMenuClonarProforma) "Clonar Proforma" else "Buscar Proforma",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                        ){
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                            ){
                                BBasicTextField(
                                    value = nombreClienteBusquedaProforma,
                                    onValueChange = {
                                        nombreClienteBusquedaProforma = it
                                        buscarProformas = true
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    utilizarMedidas = false,
                                    fontSize = obtenerEstiloTitleMedium()
                                )
                            }

                            IconButton(
                                onClick = {
                                    iniciarMenuSeleccionarProforma = false
                                    iniciarMenuClonarProforma = false
                                    isMenuVisible = false
                                    listaProforma = emptyList()
                                    nombreClienteBusquedaProforma = ""
                                },
                                modifier = Modifier.weight(0.1f)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Cerrar",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                        ) {
                            TText(
                                text ="Desde:"
                            )
                            ButtonFecha(
                                valor = fechaInicialProforma,
                                nuevoValor = {
                                    fechaInicialProforma = it
                                    buscarProformas = true
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color(0xFFEEEEEE),
                                contenteColor = Color.Black,
                                textSize = obtenerEstiloBodySmall()
                            )
                            TText(
                                text ="Hasta:"
                            )
                            ButtonFecha(
                                valor = fechafinalProforma,
                                nuevoValor = {
                                    fechafinalProforma = it
                                    buscarProformas = true
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color(0xFFEEEEEE),
                                contenteColor = Color.Black,
                                textSize = obtenerEstiloBodySmall()
                            )
                        }

                        // Segmented Control para tipo de proforma
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(objetoAdaptardor.ajustarAltura(50))
                                .background(Color.White),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            SegmentedButton(
                                options = listOf("Borrador", "Guardadas", "Facturadas"),
                                selectedOption = when(estadoBusquedaProforma) {
                                    "1" -> "Borrador"
                                    "2" -> "Guardadas"
                                    "3" -> "Facturadas"
                                    else -> "Borrador"
                                },
                                onOptionSelected = { option ->
                                    estadoBusquedaProforma = when(option) {
                                        "Borrador" -> "1"
                                        "Guardadas" -> "2"
                                        "Facturadas" -> "3"
                                        else -> "1"
                                    }
                                    buscarProformas = true
                                },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(vertical = 5.dp)
                        ) {
                            item {
                                if (listaProforma.isEmpty() && !isCargandoProformas) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Información",
                                            tint = Color.Red,
                                            modifier = Modifier.size(40.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        TText(
                                            text = "No se encontraron proformas.",
                                            fontSize = obtenerEstiloTitleMedium(),
                                            color = Color.Gray,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                                else if (isCargandoProformas) {
                                    Box(
                                        modifier = Modifier.fillMaxSize(),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            color = Color(0xFF244BC0),
                                            modifier = Modifier.size(40.dp)
                                        )
                                    }
                                } else {
                                    listaProforma.forEachIndexed { index, proforma ->
                                        var isClienteVisible by remember { mutableStateOf(false) }
                                        LaunchedEffect(isClienteVisible) {
                                            delay(index * 100L)
                                            isClienteVisible = true
                                        }
                                        AnimatedVisibility(
                                            visible = isClienteVisible,
                                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it })
                                        ) {
                                            BxContenedorProforma(proforma)
                                        }

                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16)))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuOpcionesProforma) {
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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(objetoAdaptardor.ajustarAncho(220))
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                    ) {
                        TText(
                            text = "Opciones Proforma",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        BButton(
                            text = "Exonerar Proforma",
                            onClick = {
                                if (clienteId == "SN") return@BButton mostrarMensajeError("Cliente SN no se puede ser exonerado.")
                                iniciarMenuConfExoneracion = true
                                iniciarMenuOpcionesProforma = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Quitar Exoneración",
                            onClick = {
                                iniciarMenuConfQuitarExoneracion = true
                                iniciarMenuOpcionesProforma = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Cambiar Moneda",
                            onClick = {
                                if(!tienePermiso("008")) return@BButton mostrarMensajeError("No posee el permiso 008 para facturar en otra moneda.")
                                iniciarMenuCambiarMoneda = true
                                iniciarMenuOpcionesProforma = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Cambiar Tipo de Precio",
                            onClick = {
                                return@BButton mostrarMensajeError("Esta opción está en desarrollo...")
//                                if(!tienePermiso("005")) return@BButton mostrarMensajeError("No posee el permiso 005 para cambiar el tipo precio de venta.")
//                                nuevoTipoPrecio = tipoPrecio
//                                iniciarMenuCambiarPrecio = true
//                                iniciarMenuOpcionesProforma = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Aplicar Descuento",
                            onClick = {
                                if(!tienePermiso("004")) return@BButton mostrarMensajeError("No posee el permiso 004 para aplicar descuento global.")
                                if(!tienePermiso("023")) return@BButton mostrarMensajeError("No posee el permiso 023 para dar descuentos ilimitados.")
                                nuevoPorcentajeDescuento = descuentoCliente
                                iniciarMenuAplicarDescuento = true
                                iniciarMenuOpcionesProforma = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Salir",
                            onClick = {
                                iniciarMenuOpcionesProforma = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor,
                            backgroundColor = Color.Red
                        )
                    }

                }
            }
        }
    }

    if (iniciarMenuCambiarMoneda) {
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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(objetoAdaptardor.ajustarAncho(200))
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TText(
                            text = "Cambiar Moneda de la Proforma",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            modifier = Modifier.fillMaxWidth()
                        )

                        TText(
                            text = "Nueva moneda:",
                            fontSize = obtenerEstiloBodyBig(),
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.fillMaxWidth()
                        )

                        TextFieldMultifuncional(
                            label = "",
                            textPlaceholder = "",
                            nuevoValor2 = {
                                nuevoCodigoMoneda = it.clave
                            },
                            valor = nuevoCodigoMoneda,
                            mostrarClave = true,
                            contieneOpciones = true,
                            usarOpciones4 = true,
                            opciones4 = listaMonedas,
                            usarModifierForSize = true,
                            modifier = Modifier
                                .border(
                                    2.dp,
                                    color = Color.Gray,
                                    RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                ),
                            mostrarLeadingIcon = true,
                            leadingIcon = Icons.Filled.PriceChange,
                            isUltimo = true,
                            medidaAncho = 350,
                            tomarAnchoMaximo = false,
                            fontSize = obtenerEstiloBodyBig(),
                            mostrarLabel = false,
                            cantidadLineas = 1
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ){
                            BButton(
                                text = "Cancelar",
                                textSize = obtenerEstiloBodyBig(),
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    iniciarMenuOpcionesProforma = true
                                    iniciarMenuCambiarMoneda = false
                                },
                                objetoAdaptardor = objetoAdaptardor
                            )
                            BButton(
                                text = "Cambiar",
                                textSize = obtenerEstiloBodyBig(),
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    if (nuevoCodigoMoneda == codMonedaProforma) return@BButton mostrarMensajeError("La proforma ya está en esta moneda ($codMonedaProforma). Por favor, seleccione otra para continuar.")
                                    cambiarMoneda = true
                                    iniciarMenuCambiarMoneda = false
                                },
                                backgroundColor = Color.Red,
                                objetoAdaptardor = objetoAdaptardor
                            )
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuCambiarPrecio || iniciarMenuAplicarDescuento) {
        var isMenuVisible by remember { mutableStateOf(false) }
        var seleccionarTodos by remember { mutableStateOf(false) }
        val listaPrecios by remember {
            mutableStateOf(
                (1..10).map { numero -> ParClaveValor(numero.toString(), numero.toString()) }
            )
        }
        val listaDescuentos by remember {
            mutableStateOf(
                listOf(
                    ParClaveValor("5", "5%"),
                    ParClaveValor("10", "10%"),
                    ParClaveValor("15", "15%"),
                    ParClaveValor("20", "20%"),
                    ParClaveValor("25", "25%"),
                    ParClaveValor("30", "30%"),
                    ParClaveValor("40", "40%"),
                    ParClaveValor("50", "50%")
                )
            )
        }

        LaunchedEffect(Unit) {
            delay(100)
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isMenuVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(24))
                        .align(Alignment.TopCenter),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                    color = Color.White
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        TText(
                            text = if(iniciarMenuAplicarDescuento) "Aplicar Descuento" else "Cambiar Tipo de Precio" ,
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8)),
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Box(
                                modifier = Modifier.weight(1.2f)
                            ){
                                if (iniciarMenuAplicarDescuento){
                                    TextFieldMultifuncional(
                                        label = "Desc (%)",
                                        textPlaceholder = "0.00",
                                        nuevoValor = {nuevoPorcentajeDescuento= it},
                                        valor = nuevoPorcentajeDescuento,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                2.dp,
                                                color = Color.Gray,
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                            ),
                                        isUltimo = true,
                                        tomarAnchoMaximo = false,
                                        fontSize = obtenerEstiloBodyBig(),
                                        cantidadLineas = 2,
                                        mostrarPlaceholder = true,
                                        soloPermitirValoresNumericos = true,
                                        permitirPuntosDedimales = true
                                    )
                                }
                                else{
                                    TextFieldMultifuncional(
                                        label = "Precio:",
                                        textPlaceholder = "",
                                        nuevoValor2 = {
                                            if (iniciarMenuAplicarDescuento) return@TextFieldMultifuncional
                                            nuevoTipoPrecio = it.clave
                                        },
                                        valor = nuevoTipoPrecio,
                                        mostrarClave = true,
                                        contieneOpciones = true,
                                        usarOpciones4 = true,
                                        opciones4 = listaPrecios,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(
                                                2.dp,
                                                color = Color.Gray,
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                            ),
                                        isUltimo = true,
                                        tomarAnchoMaximo = false,
                                        fontSize = obtenerEstiloBodyBig(),
                                        cantidadLineas = 2,
                                        mostrarPlaceholder = true
                                    )
                                }
                            }

                            BButton(
                                text =  if (seleccionarTodos) "Deseleccionar todos" else "Seleccionar todos",
                                onClick = {
                                    seleccionarTodos = !seleccionarTodos
                                    if (seleccionarTodos) {
                                        listaArticulosProforma.forEach { articulo ->
                                            if (!listaArticulosSeleccionados.contains(articulo)) {
                                                listaArticulosSeleccionados.add(articulo)
                                            }
                                        }
                                    } else {
                                        listaArticulosSeleccionados.clear()
                                    }
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                textSize = obtenerEstiloBodyBig(),
                                maxLines = 2,
                                backgroundColor = if (seleccionarTodos) Color.Red else Color(0xFF244BC0)
                            )
                        }

                        if (iniciarMenuAplicarDescuento) {
                            var isDescuentosVisible by remember { mutableStateOf(false) }

                            LaunchedEffect(Unit) {
                                delay(100)
                                isDescuentosVisible = true
                            }

                            LazyRow {
                                itemsIndexed(listaDescuentos) { index, descuento ->
                                    AnimatedVisibility(
                                        visible = isDescuentosVisible,
                                        enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(
                                            initialOffsetX = { it * (index + 1) }
                                        )
                                    ) {
                                        BButton(
                                            text = descuento.valor,
                                            onClick = {
                                                nuevoPorcentajeDescuento = descuento.clave
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            modifier = Modifier
                                                .height(objetoAdaptardor.ajustarAltura(30))
                                                .padding(
                                                    horizontal = objetoAdaptardor.ajustarAncho(
                                                        4
                                                    )
                                                ),
                                            backgroundColor = Color.LightGray,
                                            contenteColor = Color.Black
                                        )
                                    }
                                }
                            }
                        }

                        LazyColumn(
                            modifier = Modifier
                                .heightIn(max = objetoAdaptardor.ajustarAltura(400)),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                if (listaArticulosProforma.isEmpty()){
                                    Column(
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Dangerous,
                                            contentDescription = "ICONO DE PELIGRO",
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50)),
                                            tint = Color(0xFFEB4242)
                                        )

                                        TText(
                                            text = "La proforma no contiene ningún artículo.",
                                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200)),
                                            fontSize = obtenerEstiloTitleMedium(),
                                            maxLines = 5,
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }else{
                                    listaArticulosProforma.forEachIndexed { index, articulo ->
                                        var isArticuloVisible by remember { mutableStateOf(false) }
                                        LaunchedEffect(isArticuloVisible) {
                                            delay(index * 100L)
                                            isArticuloVisible = true
                                        }


                                        AnimatedVisibility(
                                            visible = isArticuloVisible,
                                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it })
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4)),
                                                modifier = Modifier
                                                    .clickable {
                                                        if (!listaArticulosSeleccionados.contains(
                                                                articulo
                                                            )
                                                        ) {
                                                            listaArticulosSeleccionados.add(articulo)
                                                        } else {
                                                            listaArticulosSeleccionados.remove(
                                                                articulo
                                                            )
                                                        }
                                                        seleccionarTodos =
                                                            listaArticulosProforma.size == listaArticulosSeleccionados.size
                                                    }
                                                    .fillMaxWidth()
                                                    .padding(
                                                        vertical = objetoAdaptardor.ajustarAltura(
                                                            2
                                                        )
                                                    )
                                            ) {
                                                Checkbox(
                                                    checked = listaArticulosSeleccionados.contains(articulo),
                                                    onCheckedChange = { valor ->
                                                        if (valor) {
                                                            listaArticulosSeleccionados.add(articulo)
                                                        } else {
                                                            listaArticulosSeleccionados.remove(articulo)
                                                        }
                                                        seleccionarTodos = listaArticulosProforma.size == listaArticulosSeleccionados.size
                                                    },
                                                    modifier = Modifier
                                                        .weight(0.2f)
                                                        .scale(0.7f),
                                                    colors = CheckboxDefaults.colors(
                                                        checkedColor = Color(0xFF244BC0),
                                                        uncheckedColor = Color.Gray,
                                                        checkmarkColor = Color.White
                                                    )
                                                )
                                                Box(
                                                    modifier = Modifier
                                                        .height(objetoAdaptardor.ajustarAltura(30))
                                                        .width(objetoAdaptardor.ajustarAncho(30)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    SubcomposeAsyncImage(
                                                        model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articulo.codigo}.png",
                                                        contentDescription = "Imagen Articulo",
                                                        modifier = Modifier
                                                            .fillMaxSize(),
                                                        contentScale = ContentScale.FillBounds,
                                                        loading = {
                                                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                                                CircularProgressIndicator(
                                                                    color = Color(0xFF244BC0),
                                                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(20))
                                                                )
                                                            }
                                                        },
                                                        error = {
                                                            Image(
                                                                modifier = Modifier
                                                                    .fillMaxSize(),
                                                                painter = painterResource(id = R.drawable.sin_imagen),
                                                                contentDescription = "Descripción de la imagen",
                                                                contentScale = ContentScale.FillBounds
                                                            )
                                                        }
                                                    )
                                                }

                                                Column(
                                                    horizontalAlignment = Alignment.Start,
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    TText(articulo.descripcion, textAlign = TextAlign.Start, maxLines = 3, fontSize = obtenerEstiloBodyBig())
                                                    TText("Cant: "+articulo.articuloCantidad, textAlign = TextAlign.Start, maxLines = 3)
                                                }
                                                Column(
                                                    horizontalAlignment = Alignment.Start,
                                                    modifier = Modifier.weight(1f)
                                                ) {
                                                    if (iniciarMenuAplicarDescuento){
                                                        TText("Desc (%): "+articulo.articuloDescuentoPorcentaje, textAlign = TextAlign.Start, maxLines = 3, fontSize = obtenerEstiloBodyBig())
                                                        TText("Mon.Desc: "+simboloMoneda + separacionDeMiles(articulo.articuloDescuentoMonto), textAlign = TextAlign.Start, maxLines = 3)
                                                    }else{
                                                        TText("Tip.precio: "+articulo.codPrecioVenta, textAlign = TextAlign.Start, maxLines = 3, fontSize = obtenerEstiloBodyBig())
                                                        TText("Precio: "+simboloMoneda + separacionDeMiles(articulo.precio), textAlign = TextAlign.Start, maxLines = 3)
                                                    }

                                                }

                                            }
                                            HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                                        }
                                    }
                                }
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ){
                            BButton(
                                text = if(iniciarMenuAplicarDescuento) "Aplicar" else "Cambiar",
                                onClick = {
                                    if (listaArticulosSeleccionados.isEmpty()) return@BButton mostrarMensajeError("Seleccione Artículos para continuar.")
                                    if (iniciarMenuAplicarDescuento) aplicarDescuento = true else mostrarMensajeError("Esta opción está en desarrollo...")
                                    iniciarMenuAplicarDescuento = false
                                    iniciarMenuCambiarPrecio = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                textSize = obtenerEstiloBodyBig(),
                                backgroundColor = Color.Red
                            )
                            BButton(
                                text =  "Cancelar",
                                onClick = {
                                    listaArticulosSeleccionados.clear()
                                    iniciarMenuOpcionesProforma = true
                                    iniciarMenuAplicarDescuento = false
                                    iniciarMenuCambiarPrecio = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                textSize = obtenerEstiloBodyBig()
                            )
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuProcesar) {
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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(objetoAdaptardor.ajustarAncho(220))
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                    ) {
                        TText(
                            text = "Opciones Procesar",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        BButton(
                            text = "Completo",
                            onClick = {
                                if (estadoProforma != "2" && !tienePermiso("078")) return@BButton mostrarMensajeError("No posee permisos para emitir facturas.")
                                iniciarMenuProcesar = false
                                iniciarMenuConfPagoCompleto = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Contado",
                            onClick = {
                                if (estadoProforma != "2" && !tienePermiso("078")) return@BButton mostrarMensajeError("No posee permisos para emitir facturas.")
                                iniciarMenuProcesar = false
                                iniciarMenuSeleccionarMedioPago = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Crédito",
                            onClick = {
                                if (codMonedaProforma != "CRC") return@BButton mostrarMensajeError("Actualmente, solo es posible procesar a crédito en moneda CRC.")
                                if (estadoProforma != "2" && !tienePermiso("078")) return@BButton mostrarMensajeError("No posee permisos para emitir facturas.")
                                if (clienteId == "SN") return@BButton mostrarMensajeError("Cliente SN no puede Facturar a Crédito.")
                                iniciarMenuProcesar = false
                                iniciarMenuConfPagoCredito = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Proforma",
                            onClick = {
                                iniciarMenuProcesar = false
                                iniciarMenuConfComoProforma = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Salir",
                            onClick = {
                                iniciarMenuProcesar = false
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor,
                            backgroundColor = Color.Red
                        )
                    }
                }
            }
        }

    }

    if (iniciarMenuSeleccionarMedioPago) {
        var isMenuVisible by remember { mutableStateOf(false) }
        var isModificado by remember { mutableStateOf(false) }
        var vuelto by remember { mutableDoubleStateOf(0.00) }
        var totalPagado by remember { mutableDoubleStateOf(0.00) }
        val listaPagosTemp = listaPagos

        fun calcularVuelto(){
            try{
                var totalPagadoTemp = 0.00
                listaPagosTemp.filter { it.funcion != "eliminar" }.forEach { pago ->
                    val monto = if(pago.Monto.isEmpty()) 0.0 else pago.Monto.toDouble()
                    totalPagadoTemp += if (pago.CodigoMoneda == "CRC"){
                        if (codMonedaProforma == "CRC") monto else monto/tasaCambioDolar
                    }else{
                        if (codMonedaProforma == "CRC") monto*tasaCambioDolar else monto
                    }
                }
                totalPagado = totalPagadoTemp
                vuelto = totalPagadoTemp - total
            }catch (e: Exception){
                mostrarMensajeError("Hubo un error al calcular el vuelto")
            }
        }

        LaunchedEffect(Unit) {
            calcularVuelto()
            delay(100)
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isMenuVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .wrapContentSize(),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                    ) {
                        TText(
                            text = "Medios de Pago",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )

                        Row(
                            modifier = Modifier
                                .padding(objetoAdaptardor.ajustarAltura(2))
                                .background(Color.LightGray)
                        ){
                            TText(
                                text = "Medio de Pago",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1.2f)
                            )

                            TText(
                                text = "Moneda",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(0.9f)
                            )

                            TText(
                                text = "Monto",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1.2f)
                            )

                            TText(
                                text = "",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(0.25f)
                            )
                        }

                        LazyColumn {
                            item {
                                listaPagosTemp.filter { it.funcion != "eliminar" }.forEachIndexed { index, pago ->
                                    var isArticuloVisible by remember { mutableStateOf(false) }

                                    LaunchedEffect(isArticuloVisible) {
                                        delay(index * 100L)
                                        isArticuloVisible = true
                                    }

                                    AnimatedVisibility(
                                        visible = isArticuloVisible,
                                        enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                                            initialOffsetY = { it })
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(vertical = objetoAdaptardor.ajustarAltura(4)),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4))
                                        ){
                                            Box(
                                                modifier = Modifier.weight(1.2f)
                                            ){
                                                BBasicTextField(
                                                    value = listaMediosPago.find { it.clave == pago.CuentaContable }?.valor ?: "Nada",
                                                    onValueChange = {
                                                        pago.CuentaContable = it
                                                        isModificado = true
                                                    },
                                                    mostrarLeadingIcon = false,
                                                    objetoAdaptardor = objetoAdaptardor,
                                                    utilizarMedidas = false,
                                                    modifier = Modifier
                                                        .wrapContentHeight(),
                                                    backgroundColor = Color.White,
                                                    textColor = Color.Black,
                                                    fontSize = obtenerEstiloBodyBig(),
                                                    textAlign = TextAlign.Start,
                                                    placeholder = "0.00",
                                                    placeholderColor = Color.Black,
                                                    opciones = listaMediosPago,
                                                    cantidadLineas = 3
                                                )
                                            }

                                            Box(
                                                modifier = Modifier.weight(0.9f)
                                            ){
                                                BBasicTextField(
                                                    value = pago.CodigoMoneda,
                                                    onValueChange = {
                                                        pago.CodigoMoneda = it
                                                        pago.Monto = "0.00"
                                                        calcularVuelto()
                                                        isModificado = true
                                                    },
                                                    mostrarLeadingIcon = false,
                                                    objetoAdaptardor = objetoAdaptardor,
                                                    utilizarMedidas = false,
                                                    modifier = Modifier
                                                        .wrapContentHeight(),
                                                    backgroundColor = Color.White,
                                                    textColor = Color.Black,
                                                    fontSize = obtenerEstiloBodyBig(),
                                                    textAlign = TextAlign.Center,
                                                    placeholder = "0.00",
                                                    placeholderColor = Color.Black,
                                                    opciones = listaMonedas
                                                )
                                            }

                                            Box(
                                                modifier = Modifier.weight(1.2f)
                                            ){
                                                BBasicTextField(
                                                    value = pago.Monto,
                                                    onValueChange = {
                                                        pago.Monto = it
                                                        calcularVuelto()
                                                        isModificado = true
                                                    },
                                                    mostrarLeadingIcon = false,
                                                    objetoAdaptardor = objetoAdaptardor,
                                                    utilizarMedidas = false,
                                                    modifier = Modifier
                                                        .wrapContentHeight()
                                                        .border(
                                                            1.dp,
                                                            color = Color.Gray,
                                                            RoundedCornerShape(
                                                                objetoAdaptardor.ajustarAltura(
                                                                    8
                                                                )
                                                            )
                                                        ),
                                                    backgroundColor = Color.White,
                                                    textColor = Color.Black,
                                                    fontSize = obtenerEstiloBodyBig(),
                                                    textAlign = TextAlign.End,
                                                    placeholder = "0.00",
                                                    placeholderColor = Color.Black,
                                                    soloPermitirValoresNumericos = true, 
                                                    darFormatoMiles = true,
                                                    cantidadLineas = 1
                                                )
                                            }

                                            IconButton(
                                                onClick = {
                                                    if(pago.Id != "0"){
                                                        pago.funcion = "eliminar"
                                                        isModificado = true
                                                        calcularVuelto()
                                                        return@IconButton
                                                    }
                                                    listaPagosTemp.remove(pago)
                                                    calcularVuelto()
                                                },
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(22))
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Delete,
                                                    contentDescription = "Eliminar",
                                                    tint = Color(0xFFEB4242),
                                                    modifier = Modifier
                                                        .size(objetoAdaptardor.ajustarAltura(22))
                                                        .weight(0.25f)
                                                )
                                            }
                                        }
                                    }

                                    HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                                }
                            }
                        }

                        BButton(
                            text = "Agregar medio de pago",
                            onClick = {
                                listaPagosTemp.add(Pago(
                                    Documento = numeroProforma,
                                    CodigoMoneda = codMonedaProforma,
                                    Monto = if( (total-totalPagado) < 0 ) "0.00" else (total-totalPagado).toString(),
                                    CuentaContable = listaMediosPago.first().clave
                                ))
                                calcularVuelto()
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            contenteColor = Color(0xFF244BC0),
                            backgroundColor = Color.White,
                            mostrarIcono = true,
                            conSombra = false
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            TText(
                                text = "Total a Pagar:",
                                modifier = Modifier.weight(1f),
                                fontSize = obtenerEstiloTitleSmall(),
                                textAlign = TextAlign.Start
                            )

                            TText(
                                text = simboloMoneda + separacionDeMiles(montoDouble = total) + " $codMonedaProforma",
                                modifier = Modifier.weight(1f),
                                fontSize = obtenerEstiloTitleSmall(),
                                textAlign = TextAlign.End
                            )
                        }

                        HorizontalDivider(thickness = 1.dp, color = Color.Black)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            TText(
                                text = "Pagado:",
                                modifier = Modifier.weight(1f),
                                fontSize = obtenerEstiloTitleSmall(),
                                textAlign = TextAlign.Start
                            )

                            TText(
                                text = simboloMoneda + separacionDeMiles(montoDouble = totalPagado) + " $codMonedaProforma",
                                modifier = Modifier.weight(1f),
                                fontSize = obtenerEstiloTitleSmall(),
                                textAlign = TextAlign.End,
                                color = if(totalPago<0) Color.Red else Color.Black
                            )
                        }

                        HorizontalDivider(thickness = 1.dp, color = Color.Black)

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            TText(
                                text = "Vuelto:",
                                modifier = Modifier.weight(1f),
                                fontSize = obtenerEstiloTitleBig(),
                                textAlign = TextAlign.Start
                            )
                            TText(
                                text = simboloMoneda + separacionDeMiles(montoDouble = vuelto) + " $codMonedaProforma",
                                modifier = Modifier.weight(1f),
                                fontSize = obtenerEstiloTitleBig(),
                                textAlign = TextAlign.End
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            BButton(
                                text = "Facturar",
                                onClick = {
                                    val pagosInvalidos = listaPagosTemp.filter { (it.Monto.isEmpty() || it.Monto.toDouble() == 0.0) && it.Id == "0" }
                                    listaPagosTemp.removeAll(pagosInvalidos)
                                    if (listaPagosTemp.size>0){
                                        tipoPago = "contado"
                                        listaPagos = listaPagosTemp
                                        agregarFormapago = true
                                        isSoloAgregarFormaPago = false
                                    }else{
                                        mostrarMensajeError("Por favor, agregue al menos una forma de pago para continuar.")
                                    }
                                    agregarColaImpresion()
                                    iniciarMenuSeleccionarMedioPago = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color.Red
                            )
                            BButton(
                                text = if (listaPagosTemp.size == 0 ) "Salir" else "Guardar",
                                onClick = {
                                    val pagosInvalidos = listaPagosTemp.filter { (it.Monto.isEmpty() || it.Monto.toDouble() == 0.0) && it.Id == "0" }
                                    listaPagosTemp.removeAll(pagosInvalidos)
                                    if (isModificado || listaPagos.any { (it.Monto.isEmpty() || it.Monto.toDouble() == 0.0) && it.Id != "0" } ){
                                        listaPagos = listaPagosTemp
                                        isSoloAgregarFormaPago = true
                                        agregarFormapago = true
                                    }else{
                                        mostrarMensajeExito("Las formas de pago se han guardado exitosamente.")
                                    }
                                    iniciarMenuSeleccionarMedioPago = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuConfComoProforma) {

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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(6)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        Text(
                            "Facturar como Proforma",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloDisplayMedium(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        TText(
                            text = "Ingrese un correo si desea recibir una copia de la proforma: ",
                            fontSize = obtenerEstiloTitleSmall(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 4
                        )
                        BBasicTextField(
                            value = correoProformaTemp,
                            onValueChange =  { nuevoValor ->
                                correoProformaTemp = nuevoValor
                            },
                            utilizarMedidas = false,
                            placeholder = "Correo electrónico",
                            icono = Icons.Filled.Email,
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            fontSize = obtenerEstiloBodyMedium()
                        )
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            BButton(
                                text = "Procesar",
                                onClick = {
                                    tipoFormaProcesar = "proforma"
                                    guardarProforma = true
                                    iniciarMenuConfComoProforma = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f)
                            )

                            BButton(
                                text = "Proce/Impri",
                                onClick = {
                                    agregarColaImpresion(isProforma = true)
                                    tipoFormaProcesar = "proforma"
                                    guardarProforma = true
                                    iniciarMenuConfComoProforma = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color(0xFFFF5722),
                                textSize = obtenerEstiloBodyBig()
                            )

                            BButton(
                                text = "Cancelar",
                                onClick = {
                                    correoProformaTemp =  ""
                                    iniciarMenuConfComoProforma = false
                                    if (isCrearProforma) return@BButton
                                    iniciarMenuProcesar = true

                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color.Red,
                                textSize = obtenerEstiloBodyBig()
                            )
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuActuNombreProforma) {

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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(6)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        Text(
                            "Actualizar Nombre Cliente Factura",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloDisplayMedium(),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        TText(
                            text = "Ingrese el nuevo nombre: ",
                            fontSize = obtenerEstiloTitleSmall(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 4
                        )
                        BBasicTextField(
                            value = nombreProformaTemp,
                            onValueChange =  { nuevoValor ->
                                nombreProformaTemp = nuevoValor
                            },
                            utilizarMedidas = false,
                            placeholder = "Nombre del Cliente",
                            icono = Icons.Filled.Person,
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            fontSize = obtenerEstiloBodyMedium()
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            BButton(
                                text = "Actualizar",
                                onClick = {
                                    actualizarNombreProforma = true
                                    iniciarMenuActuNombreProforma = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f)
                            )

                            BButton(
                                text = "Cancelar",
                                onClick = {
                                    iniciarMenuActuNombreProforma = false
                                    nombreProformaTemp = ""
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                backgroundColor = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    if (iniciarPantallaEstadoImpresion) {
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
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(objetoAdaptardor.ajustarAltura(24))
                    .align(Alignment.Center),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                color = Color.White,
                shadowElevation = 8.dp
            ) {
                AnimatedVisibility(
                    visible = isMenuVisible,
                    enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                    exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
                ) {
                    Column (
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ){
                        AnimatedVisibility(
                            visible = isMenuVisible,
                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
                        ) {
                            if (isImprimiendo){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    CircularProgressIndicator(
                                        color = Color(0xFF244BC0),
                                        modifier = Modifier
                                            .size(objetoAdaptardor.ajustarAltura(50))
                                            .padding(4.dp)
                                    )
                                    TText(
                                        text = if(estadoProformaInicial == "2") "Reimprimiendo..." else "Imprimiendo...",
                                        fontSize = obtenerEstiloTitleSmall(),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }else{
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ){
                                    Icon(
                                        imageVector = if(exitoImpresion) Icons.Default.CheckCircle else Icons.Default.Error,
                                        contentDescription = "",
                                        tint = if(exitoImpresion) Color(0xFF4CAF50) else Color(0xFFF44336),
                                        modifier = Modifier
                                            .size(objetoAdaptardor.ajustarAltura(50))
                                            .padding(4.dp)
                                    )
                                    TText(
                                        text = if(exitoImpresion) "Reimpresión exitosa!" else "Error en la Reimpresión!",
                                        fontSize = obtenerEstiloTitleSmall(),
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                                AnimatedVisibility(
                                    visible = exitoImpresion && listaImpresion.isNotEmpty(),
                                    enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                                    exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(
                                            objetoAdaptardor.ajustarAncho(8)
                                        )
                                    ) {
                                        BButton(
                                            text = "Imprimir Copia ${listaImpresion.first().clave}",
                                            onClick = {
                                                imprimir = true
                                            },
                                            textSize = obtenerEstiloBodyBig(),
                                            objetoAdaptardor = objetoAdaptardor,
                                            modifier = Modifier.weight(1f),
                                            backgroundColor = Color(0xFFF44336)
                                        )
                                    }
                                }
                            }
                        }



                        AnimatedVisibility(
                            visible = !exitoImpresion && !isImprimiendo,
                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                            ){
                                BButton(
                                    text = "Salir",
                                    onClick = {
                                        iniciarPantallaEstadoImpresion = false
                                        numeroProforma = ""
                                        actualizarDatosProforma = true
                                    },
                                    textSize = obtenerEstiloBodyBig(),
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.weight(1f),
                                    backgroundColor = Color(0xFFF44336)
                                )
                                BButton(
                                    text = "Reintentar",
                                    onClick = {
                                        obtenerDatosFacturaEmitida = true
                                    },
                                    textSize = obtenerEstiloBodyBig(),
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.weight(1f)
                                )
                                BButton(
                                    text = "Reconectar",
                                    onClick = {
                                        coroutineScope.launch {
                                            gestorImpresora.reconectar(context)
                                        }
                                    },
                                    textSize = obtenerEstiloBodyBig(),
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuOpcionesCliente) {
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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(50))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        TText(
                            text = "Opciones de Cliente",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        BButton(
                            text = "Agregar",
                            onClick = {
                                if(!tienePermiso("310")) return@BButton mostrarMensajeError("No tiene permiso 310 para acceder al modulo de Clientes")
                                if(!tienePermiso("311")) return@BButton mostrarMensajeError("No tiene permiso 311 para Agregar y Actualizar datos de Clientes")
                                isEditarCliente = false
                                iniciarMenuOpcionesCliente = false
                                iniciarMenuAgregaEditaCliente = true
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.fillMaxWidth(),
                            textSize = obtenerEstiloBodyBig()
                        )

                        BButton(
                            text = "Editar",
                            onClick = {
                                if (clienteId == "SN") return@BButton mostrarMensajeError("El cliente SN no puede ser editado.")
                                if(!tienePermiso("310")) return@BButton mostrarMensajeError("No tiene permiso 310 para acceder al modulo de Clientes")
                                if(!tienePermiso("311")) return@BButton mostrarMensajeError("No tiene permiso 311 para Agregar y Actualizar datos de Clientes")
                                clienteTemp = Cliente(
                                    Id_cliente = clienteId,
                                    Nombre = nombreCliente,
                                    Cedula = numeroCedula,
                                    nombreComercial = nombreComercial,
                                    ClienteNombreComercial = nombreComercial,
                                    Direccion = direccion,
                                    Telefonos = telefonos,
                                    Email = emailGeneral,
                                    EmailFactura = emailGeneral,
                                    EmailCobro = emailGeneral,
                                    TipoPrecioVenta = tipoPrecioCliente,
                                    Cod_Moneda = codMonedaCliente,
                                    TipoIdentificacion = tipoCedula,
                                    plazo = plazoCredito,
                                    MontoCredito = montoCredito,
                                    Descuento = descuentoCliente,
                                    MontoContrato = montoContrato
                                )
                                isEditarCliente = true
                                iniciarMenuOpcionesCliente = false
                                iniciarMenuAgregaEditaCliente = true
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.fillMaxWidth(),
                            textSize = obtenerEstiloBodyBig()
                        )

                        BButton(
                            text = "Actualizar Nombre Factura",
                            onClick = {
                                iniciarMenuActuNombreProforma = true
                                iniciarMenuOpcionesCliente = false
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.fillMaxWidth(),
                            textSize = obtenerEstiloBodyBig()
                        )

                        BButton(
                            text = "Cambiar a SN",
                            onClick = {
                                clienteSeleccionado = ClienteFacturacion(
                                    codigo = "SN",
                                    nombreComercial = "ESTIMADO CLIENTE",
                                    nombreJuridico = "ESTIMADO CLIENTE",
                                    telefono = "",
                                    correo = "",
                                    codMoneda = "CRC",
                                    tipoPrecio = "1"
                                )
                                cambiarClienteProforma = true
                                iniciarMenuOpcionesCliente = false
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.fillMaxWidth(),
                            textSize = obtenerEstiloBodyBig()
                        )

                        BButton(
                            text = "Salir",
                            onClick = {
                                iniciarMenuOpcionesCliente = false
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.fillMaxWidth(),
                            textSize = obtenerEstiloBodyBig(),
                            backgroundColor = Color.Red
                        )
                    }
                }
            }
        }
    }

    if (iniciarMenuAgregaEditaCliente) {
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
            contentAlignment = Alignment.Center
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
                        .padding(objetoAdaptardor.ajustarAltura(16))
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                    color = Color.White,
                    shadowElevation = 8.dp
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(6)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        // titulo
                        TText(
                            text = if (isEditarCliente) "Editar Cliente" else "Agregar Cliente",
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = obtenerEstiloHeadBig(),
                            textAlign = TextAlign.Center
                        )
                        LazyColumn(
                            modifier = Modifier.wrapContentSize()
                        ) {
                            item {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                                ) {
                                    // Tipo Cedula
                                    Column {
                                        TText(
                                            text = "Tipo de Cédula: ",
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloBodySmall(),
                                            textAlign = TextAlign.Start
                                        )
                                        BBasicTextField(
                                            value = listaTipoCedula.find { it.clave == clienteTemp.TipoIdentificacion }?.valor ?:"Sin definir",
                                            onValueChange = {
                                                clienteTemp.TipoIdentificacion = it
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            opciones = listaTipoCedula,
                                            utilizarMedidas = false,
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloBodyBig(),
                                            icono = Icons.Default.Badge
                                        )
                                    }

                                    // Numero de cedula y Boton para obtener el nombre juridico del cliente por el numero de cedula
                                    Row (
                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8)),
                                        verticalAlignment = Alignment.CenterVertically
                                    ){
                                        Column (
                                            modifier = Modifier.weight(1f)
                                        ){
                                            TText(
                                                text = "Cédula: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.Cedula,
                                                onValueChange = {
                                                    clienteTemp.Cedula = it.trim()
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                fontSize = obtenerEstiloBodyMedium(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Cédula",
                                                soloPermitirValoresNumericos = true,
                                                backgroundColor = Color.White,
                                                enable = (clienteId=="SN" || !isEditarCliente)
                                            )
                                        }
                                        if (clienteId=="SN" || !isEditarCliente){
                                            BButton(
                                                text = "Buscar",
                                                onClick = {
                                                    iniciarBusquedaClienteByCedula = true
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                textSize = obtenerEstiloBodyBig()
                                            )
                                        }
                                    }

                                    // Nombre Juridico
                                    Column {
                                        TText(
                                            text = "Nombre Jurídico: ",
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloBodySmall(),
                                            textAlign = TextAlign.Start
                                        )
                                        BBasicTextField(
                                            value = clienteTemp.Nombre,
                                            onValueChange = {
                                                clienteTemp.Nombre = it
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            utilizarMedidas = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    1.dp,
                                                    color = Color.Gray,
                                                    RoundedCornerShape(
                                                        objetoAdaptardor.ajustarAltura(
                                                            10
                                                        )
                                                    )
                                                ),
                                            backgroundColor = Color.White,
                                            fontSize = obtenerEstiloBodyBig(),
                                            mostrarLeadingIcon = false,
                                            placeholder = "Nombre Jurídico",
                                            cantidadLineas = 10
                                        )
                                    }

                                    // Nombre comercial y telefono
                                    Row (
                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                    ){
                                        // Nombre Comercial
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Nombre Comercial: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.nombreComercial,
                                                onValueChange = {
                                                    clienteTemp.nombreComercial = it
                                                    clienteTemp.ClienteNombreComercial = it
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                backgroundColor = Color.White,
                                                fontSize = obtenerEstiloBodyBig(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Nombre Comercial",
                                                cantidadLineas = 10
                                            )
                                        }

                                        // Telefono
                                        Column (
                                            modifier = Modifier.weight(1f)
                                        ){
                                            TText(
                                                text = "Teléfono: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.Telefonos,
                                                onValueChange = {
                                                    clienteTemp.Telefonos = it.trim()
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                fontSize = obtenerEstiloBodyMedium(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Telefono",
                                                soloPermitirValoresNumericos = true,
                                                backgroundColor = Color.White
                                            )
                                        }
                                    }

                                    // Direccion
                                    Column {
                                        TText(
                                            text = "Dirección: ",
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloBodySmall(),
                                            textAlign = TextAlign.Start
                                        )
                                        BBasicTextField(
                                            value = clienteTemp.Direccion,
                                            onValueChange = {
                                                clienteTemp.Direccion = it
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            utilizarMedidas = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    1.dp,
                                                    color = Color.Gray,
                                                    RoundedCornerShape(
                                                        objetoAdaptardor.ajustarAltura(
                                                            10
                                                        )
                                                    )
                                                ),
                                            backgroundColor = Color.White,
                                            fontSize = obtenerEstiloBodyBig(),
                                            mostrarLeadingIcon = false,
                                            placeholder = "Dirección",
                                            cantidadLineas = 10
                                        )
                                    }

                                    // Correo Electronico
                                    Column {
                                        TText(
                                            text = "Correo Electrónico: ",
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloBodySmall(),
                                            textAlign = TextAlign.Start
                                        )
                                        BBasicTextField(
                                            value = clienteTemp.Email,
                                            onValueChange = {
                                                clienteTemp.Email = it.trim()
                                                clienteTemp.EmailCobro = it.trim()
                                                clienteTemp.EmailFactura = it.trim()
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            utilizarMedidas = false,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    1.dp,
                                                    color = Color.Gray,
                                                    RoundedCornerShape(
                                                        objetoAdaptardor.ajustarAltura(
                                                            10
                                                        )
                                                    )
                                                ),
                                            backgroundColor = Color.White,
                                            fontSize = obtenerEstiloBodyBig(),
                                            mostrarLeadingIcon = false,
                                            placeholder = "Correo Electrónico",
                                            cantidadLineas = 10
                                        )
                                    }

                                    // Plazo y Monto de credito
                                    Row (
                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                    ){
                                        // Plazo de credito
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Plazo Crédito: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.plazo,
                                                onValueChange = {
                                                    clienteTemp.plazo = it.trim().replace(".", "").replace(",", "").replace("-", "")
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                backgroundColor = Color.White,
                                                fontSize = obtenerEstiloBodyBig(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Plazo Crédito",
                                                soloPermitirValoresNumericos = true
                                            )
                                        }

                                        // Monto Credito
                                        Column (
                                            modifier = Modifier.weight(1f)
                                        ){
                                            TText(
                                                text = "Monto Crédito: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.MontoCredito,
                                                onValueChange = {
                                                    clienteTemp.MontoCredito = it.trim().replace(".", "").replace(",", "").replace("-", "")
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                fontSize = obtenerEstiloBodyMedium(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Monto Crédito",
                                                soloPermitirValoresNumericos = true,
                                                darFormatoMiles = true,
                                                backgroundColor = Color.White
                                            )
                                        }
                                    }

                                    // Monto de contrato y Descuento
                                    Row (
                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                    ){
                                        // Descuento
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Descuento(%):",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.Descuento,
                                                onValueChange = {
                                                    clienteTemp.Descuento = it.trim()
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                backgroundColor = Color.White,
                                                fontSize = obtenerEstiloBodyBig(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Descuento(%)",
                                                soloPermitirValoresNumericos = true
                                            )
                                        }

                                        // Monto de contrato
                                        Column (
                                            modifier = Modifier.weight(1f)
                                        ){
                                            TText(
                                                text = "Monto Contrato: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.MontoContrato,
                                                onValueChange = {
                                                    clienteTemp.MontoContrato = it.trim().replace(".", "").replace(",", "").replace("-", "")
                                                },
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .border(
                                                        1.dp,
                                                        color = Color.Gray,
                                                        RoundedCornerShape(
                                                            objetoAdaptardor.ajustarAltura(
                                                                10
                                                            )
                                                        )
                                                    ),
                                                fontSize = obtenerEstiloBodyMedium(),
                                                mostrarLeadingIcon = false,
                                                placeholder = "Monto Contrato",
                                                darFormatoMiles = true,
                                                soloPermitirValoresNumericos = true,
                                                backgroundColor = Color.White
                                            )
                                        }
                                    }

                                    // Tipo Precio y Moneda
                                    Row (
                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                    ){
                                        // Tipo Precio
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Tipo de Precio: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.TipoPrecioVenta,
                                                onValueChange = {
                                                    clienteTemp.TipoPrecioVenta = it
                                                },
                                                opciones = listaTipoPrecios,
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodyBig(),
                                                placeholder = "Tipo de Precio",
                                                icono = Icons.Filled.LocalOffer
                                            )
                                        }

                                        // Moneda
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            TText(
                                                text = "Moneda: ",
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodySmall(),
                                                textAlign = TextAlign.Start
                                            )
                                            BBasicTextField(
                                                value = clienteTemp.Cod_Moneda,
                                                onValueChange = {
                                                    clienteTemp.Cod_Moneda = it
                                                },
                                                opciones = listaMonedas,
                                                objetoAdaptardor = objetoAdaptardor,
                                                utilizarMedidas = false,
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloBodyBig(),
                                                placeholder = "Moneda",
                                                icono = Icons.Default.AttachMoney
                                            )
                                        }
                                    }

                                    // Botones inferiores
                                    Row (
                                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                    ){
                                        BButton(
                                            text = "Salir",
                                            onClick = {
                                                clienteTemp = Cliente()
                                                iniciarMenuAgregaEditaCliente = false
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            modifier = Modifier.weight(1f),
                                            textSize = obtenerEstiloBodyBig(),
                                            backgroundColor = if (!isEditarCliente) Color.Red else Color(0xFF244BC0)
                                        )

                                        BButton(
                                            text = if(isEditarCliente) "Editar" else "Agregar",
                                            onClick = {
                                                if (!validacionCedula(clienteTemp.TipoIdentificacion, clienteTemp.Cedula)) return@BButton
                                                if (clienteTemp.Nombre.isEmpty()) return@BButton mostrarMensajeError("Complete el campo de Nombre Jurídico.")
                                                if (clienteTemp.nombreComercial.isEmpty()) clienteTemp.nombreComercial = clienteTemp.nombreJuridico
                                                if (clienteTemp.Telefonos.isNotEmpty() && clienteTemp.Telefonos.length<8) return@BButton mostrarMensajeError("Ingrese un numero de telefono valido.")
                                                if (!validarCorreo(clienteTemp.Email)) return@BButton
                                                if (isEditarCliente){
                                                    iniciarMenuAgregaEditaCliente = false
                                                    editarCliente = true
                                                }else{
                                                    iniciarMenuAgregaEditaCliente = false
                                                    agregarNuevoCliente = true
                                                }
                                            },
                                            objetoAdaptardor = objetoAdaptardor,
                                            modifier = Modifier.weight(1f),
                                            backgroundColor = if (isEditarCliente) Color.Red else Color(0xFF244BC0),
                                            textSize = obtenerEstiloBodyBig()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    MenuConfirmacion(
        txBtAceptar = "Exonerar",
        txBtDenegar = "Cancelar",
        onAceptar = {
            exonerar = true
            iniciarMenuOpcionesProforma = false
            iniciarMenuConfExoneracion = false

        },
        onDenegar = {
            iniciarMenuOpcionesProforma = true
            iniciarMenuConfExoneracion = false
        },
        mostrarMenu = iniciarMenuConfExoneracion,
        titulo = "Exonerar Proforma.",
        subTitulo = "¿Está seguro que desea exonerar esta Proforma?"
    )

    MenuConfirmacion(
        txBtAceptar = "Pagar",
        txBtDenegar = "Cancelar",
        onAceptar = {
            pagarCompleto()
            iniciarMenuConfPagoCompleto = false
        },
        onDenegar = {
            iniciarMenuConfPagoCompleto = false
            iniciarMenuProcesar = true
        },
        mostrarMenu = iniciarMenuConfPagoCompleto,
        titulo = "Pagar Completo",
        subTitulo = "¿Desea pagar Completo?"
    )

    MenuConfirmacion(
        txBtAceptar = "Facturar",
        txBtDenegar = "Cancelar",
        onAceptar = {
            pagarACredito()
            iniciarMenuConfPagoCredito = false
        },
        onDenegar = {
            iniciarMenuConfPagoCredito = false
            iniciarMenuProcesar = true
        },
        mostrarMenu = iniciarMenuConfPagoCredito,
        titulo = "Facturar a Crédito",
        subTitulo = "¿Desea Facturar a Crédito?"
    )

    MenuConfirmacion(
        txBtAceptar = "Eliminar",
        txBtDenegar = "Cancelar",
        onAceptar = {
            eliminarLinea = true
            iniciarMenuConfEliminarArt = false

        },
        onDenegar = {
            iniciarMenuConfEliminarArt = false
        },
        mostrarMenu = iniciarMenuConfEliminarArt,
        titulo = "Eliminar Artículo.",
        subTitulo = "¿Está seguro que desea eliminar este Artículo?"
    )

    MenuConfirmacion(
        txBtAceptar = "Quitar",
        txBtDenegar = "Cancelar",
        onAceptar = {
            quitarExoneracion = true
            iniciarMenuOpcionesProforma = false
            iniciarMenuConfQuitarExoneracion = false

        },
        onDenegar = {
            iniciarMenuOpcionesProforma = true
            iniciarMenuConfQuitarExoneracion = false
        },
        mostrarMenu = iniciarMenuConfQuitarExoneracion,
        titulo = "Quitar Exoneración de Proforma.",
        subTitulo = "¿Está seguro que desea quitar la Exoneración de esta Proforma?"
    )

    MenuConfirmacion(
        onAceptar = {
            navController.popBackStack()
            iniciarMenuConfirmacionSalidaModulo = false
        },
        onDenegar = {
            iniciarMenuConfirmacionSalidaModulo = false
        },
        mostrarMenu = iniciarMenuConfirmacionSalidaModulo,
        titulo = "Salir del Módulo de Facturación.",
        subTitulo = "¿Está seguro que desea salir del Módulo de Facturación?"
    )
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
@Preview
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazFacturacion("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDM2MiIsIk5vbWJyZSI6IllFU0xFUiBMT1JJTyIsIkVtYWlsIjoieWVzbGVybG9yaW9AZ21haWwuY29tIiwiUHVlcnRvIjoiODAxIiwiRW1wcmVzYSI6IlpHVnRiMlpsY25KbCIsIlNlcnZlcklwIjoiTVRreUxqRTJPQzQzTGpNdyIsInRpbWUiOiIyMDI1MDMxMjA1MDMwOSJ9.JrUHQoYYnWJibwMi1B2-iGBTGk-_-2jPqdLAiJ57AJM",  nav, "demoferre","00050","YESLER LORIO")
}