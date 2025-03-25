package com.soportereal.invefacon.interfaces.modulos.facturacion

import androidx.activity.compose.BackHandler
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
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.PriceChange
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.MenuConfirmacion
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.TextFieldMultifuncional
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.mostrarTeclado
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.interfaces.modulos.clientes.ProcesarDatosModuloClientes
import com.soportereal.invefacon.interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.interfaces.obtenerEstiloBodyMedium
import com.soportereal.invefacon.interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.interfaces.obtenerEstiloHeadMedium
import com.soportereal.invefacon.interfaces.obtenerEstiloHeadSmall
import com.soportereal.invefacon.interfaces.obtenerEstiloLabelBig
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleMedium
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.pantallas_principales.objetoEstadoPantallaCarga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray

@Composable
fun IniciarInterfazFacturacion(
    token: String,
    systemUiController: SystemUiController?,
    navController: NavController,
    nombreEmpresa: String,
    codUsuario: String,
    nombreUsuario: String
){
    systemUiController?.setStatusBarColor(Color(0xFF244BC0))
    systemUiController?.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var expandedClientes by remember { mutableStateOf(false) }
//    var expandedArticulos by remember { mutableStateOf(false) }
    var expandedTotales by remember { mutableStateOf(false) }
    val listaArticulosSeleccionados = remember { mutableStateListOf<ArticuloFacturacion>() }
    var listaArticulosProforma by remember {  mutableStateOf<List<ArticuloFacturacion>>(emptyList()) }
    var nombre by remember { mutableStateOf("") }
    var descuento by remember { mutableStateOf("0.00") }
    var tipoPrecio by remember { mutableStateOf("") }
    var tipoCedula by remember { mutableStateOf("") }
    var numeroCedula by remember { mutableStateOf("") }
    var emailGeneral by remember { mutableStateOf("") }
    var nombreComercial by remember { mutableStateOf("") }
    var telefonos by remember { mutableStateOf("") }
    var plazoCredito by remember { mutableStateOf("") }
    var totalGravado by remember { mutableDoubleStateOf(0.00) }
    var totalIva by remember { mutableDoubleStateOf(0.00) }
    var totalDescuento by remember { mutableDoubleStateOf(0.00) }
    var totalPago by remember { mutableDoubleStateOf(0.00) }
    var totalExonerado by remember { mutableDoubleStateOf(0.00) }
    var totalIvaDevuelto by remember { mutableDoubleStateOf(0.00) }
    var totalMercGrav by remember { mutableDoubleStateOf(0.00) }
    var total by remember { mutableDoubleStateOf(0.00) }
    var codMonedaCliente by remember { mutableStateOf("") }
    var codMonedaProforma by remember { mutableStateOf("") }
    var numeroProforma by remember { mutableStateOf("") }
    var tipoDocumento by remember { mutableStateOf("") }
    var clienteId by remember { mutableStateOf("") }
    val objectoProcesadorDatosApi = ProcesarDatosModuloFacturacion(token)
    val objectoProcesadorDatosApiCliente = ProcesarDatosModuloClientes(token)
    var isCargandoDatos by remember { mutableStateOf(true) }
    var iniciarDescargaArticulos by remember { mutableStateOf(false) }
    var iniciarMenuAgregaEditaArticulo by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarArticulo by remember { mutableStateOf(false) }
    var actuzalizarDatosProforma by remember { mutableStateOf(true) }
    var apiConsultaProforma by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiProforma= CoroutineScope(Dispatchers.IO)
    var apiConsultaArticulos by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiArticulos= CoroutineScope(Dispatchers.IO)
    var apiConsultaBusquedaArticulos by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiBusquedaArticulos= CoroutineScope(Dispatchers.IO)
    var apiConsultaBusquedaClientes by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiBusquedaClientes= CoroutineScope(Dispatchers.IO)
    var errorCargarProforma by remember { mutableStateOf(false) }
    var simboloMoneda by remember { mutableStateOf("") }
    var listaArticulosFacturacion by remember { mutableStateOf(emptyList<ArticuloFacturacion>()) }
    var listaArticulosEncontrados by remember { mutableStateOf(emptyList<ArticuloFacturacion>()) }
    var listaClientesEncontrados by remember { mutableStateOf(emptyList<ClienteFacturacion>()) }
    var datosIngresadosBarraBusquedaArticulos by remember { mutableStateOf("") }
    var isCargandoClientes by remember { mutableStateOf(false) }
    var isCargandoArticulos by remember { mutableStateOf(false) }
    var agregarEditarArticuloActual by remember { mutableStateOf(false) }
    var actualizarListaArticulos by remember { mutableStateOf(true) }
    var articuloLineaProforma by remember { mutableStateOf(ArticuloLineaProforma()) }
    var isAgregar by remember { mutableStateOf(false) }
    var articuloActual by remember { mutableStateOf(ArticuloFacturacion()) }
    var validacionCargaFinalizada by remember { mutableIntStateOf(0) } // si es '2' las dos peticiones al api ya finalizaron
    var iniciarMenuConfirmacionSalidaModulo by remember { mutableStateOf(false) }
    var eliminarLinea by remember { mutableStateOf(false) }
    var lineaAcual by remember { mutableStateOf("") }
    var soloActualizarArticulos by remember { mutableStateOf(false) }
    var soloActualizarDatosCliente by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarProforma by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarCliente by remember { mutableStateOf(false) }
    var datosIngresadosBarraBusquedaCliente by remember { mutableStateOf("") }
    var cambiarClienteProforma by remember { mutableStateOf(false) }
    var clienteSeleccionado by remember { mutableStateOf(ClienteFacturacion()) }
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


    LaunchedEffect(actuzalizarDatosProforma, soloActualizarArticulos, soloActualizarDatosCliente) {
        if (actuzalizarDatosProforma || soloActualizarArticulos || soloActualizarDatosCliente){
            listaArticulosSeleccionados.clear()
            isCargandoDatos = (!soloActualizarArticulos  && !soloActualizarDatosCliente)
            errorCargarProforma = false
            iniciarDescargaArticulos = false
            apiConsultaArticulos?.cancel()
            apiConsultaProforma?.cancel()
            apiConsultaProforma= cortinaConsultaApiProforma.launch{
                objectoProcesadorDatosApi.crearNuevaProforma()
                val result= objectoProcesadorDatosApi.abrirProforma()
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    if(validarExitoRestpuestaServidor(result)) {
                        val data = result.getJSONObject("data")

                        //DATOS CLIENTE
                        val datosCliente = data.getJSONArray("cliente").getJSONObject(0)
                        clienteId= datosCliente.getString("ClienteID")
                        nombre= datosCliente.getString("ClienteNombre")
                        nombreComercial = datosCliente.getString("clientenombrecomercial")
                        numeroCedula = datosCliente.getString("Cedula")
                        emailGeneral = datosCliente.getString("Email")
                        telefonos = datosCliente.getString("Telefonos")
                        tipoCedula = datosCliente.getString("TipoIdentificacion")
                        plazoCredito = datosCliente.getString("plazo")
                        tipoPrecio = datosCliente.getString("TipoPrecioVenta")
                        codMonedaCliente = datosCliente.getString("monedacodigo")
                        descuento = datosCliente.getString("Descuento")

                        //DATOS PROFORMA
                        val datosProforma = data.getJSONArray("datos").getJSONObject(0)
                        numeroProforma= datosProforma.getString("Numero")
                        tipoDocumento = datosProforma.getString("TipoDocumento")

                        //Tipo Moneda
                        codMonedaProforma = data.getString("monedaDocumento")
                        simboloMoneda =  if(codMonedaProforma == "CRC") "\u20A1 " else "\u0024 "
                        iniciarDescargaArticulos = actualizarListaArticulos

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
                                utilidad = datosArticulo.getDouble("Utilidad")
                            )
                            listaArticuloFacturados.add(articuloFacturado)
                        }
                        listaArticulosProforma = listaArticuloFacturados
                    }
                    else{
                        errorCargarProforma = true
                    }
                }
                validacionCargaFinalizada++
                soloActualizarArticulos = false
                soloActualizarDatosCliente = false
            }
        }
    }

    LaunchedEffect(iniciarDescargaArticulos) {
        if (iniciarDescargaArticulos){
            listaArticulosFacturacion = emptyList()
            isCargandoDatos = true
            errorCargarProforma = false
            apiConsultaArticulos?.cancel()
            apiConsultaArticulos= cortinaConsultaApiArticulos.launch{
                val result = objectoProcesadorDatosApi.obtenerArticulos(
                    tipoPrecio = tipoPrecio,
                    moneda = codMonedaCliente
                )
                if (result!=null){
                    if (validarExitoRestpuestaServidor(result)){
                        val data = result.getJSONArray("data")
                        val listaArticulos = mutableListOf<ArticuloFacturacion>()
                        for(i in 0 until data.length()){

                            val listaPrecios = mutableListOf<ParClaveValor>()
                            val listaBodegas = mutableListOf<ParClaveValor>()
                            val datosArticulo = data.getJSONObject(i)

                            for (a in 1 until 11){
                                listaPrecios.add(
                                    ParClaveValor(
                                        clave = "$a",
                                        valor = datosArticulo.getString("Precio$a")
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


                            val articulo = ArticuloFacturacion(
                                codigo = datosArticulo.getString("Codigo"),
                                codBarra = datosArticulo.getString("Cod_Barra"),
                                descripcion = datosArticulo.getString("Descripcion"),
                                stock = datosArticulo.optDouble("Stock", 0.00),
                                costo = datosArticulo.getDouble("Costo"),
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
                                precio = datosArticulo.getDouble("Precio"),
                                listaPrecios = listaPrecios,
                                listaBodegas = listaBodegas
                            )
                            listaArticulos.add(articulo)
                        }
                        listaArticulosFacturacion = listaArticulos
                    }else{
                        errorCargarProforma = true
                        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    }
                }
                validacionCargaFinalizada++
            }
            iniciarDescargaArticulos = false
        }
    }

    LaunchedEffect(datosIngresadosBarraBusquedaArticulos) {
        if(datosIngresadosBarraBusquedaArticulos.isNotEmpty()){
            listaArticulosEncontrados = emptyList()
            apiConsultaBusquedaArticulos?.cancel()
            apiConsultaBusquedaArticulos= cortinaConsultaApiBusquedaArticulos.launch{
                delay(200)
                listaArticulosEncontrados = listaArticulosFacturacion.filter {
                    it.descripcion.contains(datosIngresadosBarraBusquedaArticulos, ignoreCase = true) ||
                            it.codigo.contains(datosIngresadosBarraBusquedaArticulos, ignoreCase = true)
                }.take(50)
                isCargandoArticulos = false
            }
        }

    }

    LaunchedEffect(validacionCargaFinalizada) {
        if (validacionCargaFinalizada == 2){
            actuzalizarDatosProforma= false
            isCargandoDatos= false
            validacionCargaFinalizada = 0
        }
    }

    LaunchedEffect(agregarEditarArticuloActual) {
        if (agregarEditarArticuloActual){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.agregarActualizarLinea(articuloLineaProforma)
            if (result != null){
                if (validarExitoRestpuestaServidor(result)){
                    iniciarMenuAgregaEditaArticulo = false
                    objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    delay(100)
                    actualizarListaArticulos = false
                    validacionCargaFinalizada++
                    soloActualizarArticulos = true
                }else{
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                }
            }
        }
        agregarEditarArticuloActual = false
    }

    LaunchedEffect(eliminarLinea) {
        if (eliminarLinea){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.eliminarLineaProforma(numero = numeroProforma, lineaArticulo = lineaAcual)
            if (result != null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                if (validarExitoRestpuestaServidor(result)){
                    delay(100)
                    actualizarListaArticulos = false
                    validacionCargaFinalizada++
                    soloActualizarArticulos = true
                    lineaAcual = ""
                }
            }
        }
        eliminarLinea = false
    }

    LaunchedEffect(datosIngresadosBarraBusquedaCliente) {
        if (datosIngresadosBarraBusquedaCliente.isNotEmpty()){
            isCargandoClientes = true
            listaClientesEncontrados = emptyList()
            apiConsultaBusquedaClientes?.cancel()
            apiConsultaBusquedaClientes= cortinaConsultaApiBusquedaClientes.launch{
                delay(250)
                val result = objectoProcesadorDatosApiCliente.obtenerDatosClientes(
                    clienteEstado = "1",
                    clienteDatoBusqueda = datosIngresadosBarraBusquedaCliente,
                    clientesPorPagina = "50",
                    paginaCliente = "1",
                    busquedaPor = "BusquedaMixta"
                )
                if (result != null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
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
                                tipoPrecio = datosCliente.getString("tipoprecio")
                            )
                            listaClientes.add(cliente)
                        }
                        listaClientesEncontrados = listaClientes
                    }
                }
                isCargandoClientes = false
            }
        }
    }

    LaunchedEffect(cambiarClienteProforma) {
        if(cambiarClienteProforma){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.cambiarClienteProforma(
                numero = numeroProforma,
                clienteFacturacion = clienteSeleccionado
            )
            if (result != null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                if(validarExitoRestpuestaServidor(result)){
                    iniciarMenuSeleccionarCliente = false
                    validacionCargaFinalizada++
                    actualizarListaArticulos = false
                    soloActualizarDatosCliente = true
                }
            }
        }
        cambiarClienteProforma = false
    }

    // Interceptar el botón de retroceso
    BackHandler {
        if (iniciarMenuAgregaEditaArticulo || iniciarMenuSeleccionarProforma || iniciarMenuSeleccionarCliente || iniciarMenuSeleccionarArticulo){
            iniciarMenuSeleccionarProforma = false
            iniciarMenuSeleccionarCliente = false
            iniciarMenuAgregaEditaArticulo = false
            iniciarMenuSeleccionarArticulo = false
        }else{
            iniciarMenuConfirmacionSalidaModulo = true
        }
    }

    @Composable
    fun BxContenedorArticulosFacturacion(
        articulo : ArticuloFacturacion
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
                    isAgregar = true
                    articuloActual = articulo
                    listaArticulosEncontrados = emptyList()
                    datosIngresadosBarraBusquedaArticulos = ""
                    iniciarMenuSeleccionarArticulo = false
                    iniciarMenuAgregaEditaArticulo = true
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
                        .padding(start = objetoAdaptardor.ajustarAncho(8), top = objetoAdaptardor.ajustarAltura(8)),
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
                        text = simboloMoneda + separacionDeMiles(articulo.precio) + " $codMonedaProforma",
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
    fun BasicTexfiuldWithText(
        textTitle: String,
        text: String,
        icon: ImageVector,
        variable: String,
        nuevoValor: (String) -> Unit
    ){
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
                fontSize = obtenerEstiloBodyMedium()
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
        if (mostrarVentanaArticulo) {
            var bodegaSeleccionda by remember { mutableStateOf(bodega) }
            var tipoPrecioSeleccionado by remember { mutableStateOf(articulo.listaPrecios.find { it.clave == precioVenta.trim() }?:ParClaveValor()) }
            var cantidadProducto by remember { mutableStateOf(if (articulo.articuloCantidad > 0) articulo.articuloCantidad.toString() else "1.0") }
            var cantidadProductoForApi by remember { mutableDoubleStateOf(0.0) }
            var precioUnitarioIva by remember { mutableDoubleStateOf(0.0) }
            var precioProducto by remember {mutableStateOf(articulo.precio.toString()) }
            var seleccionPresentacion by remember { mutableStateOf("Unidad") }
            var porcentajeDescuentoProducto by remember { mutableStateOf(descuentoCliente) }
            var montoDescuento by remember { mutableStateOf("") }
            var subtotal by remember { mutableDoubleStateOf(0.0) }
            var montoIVA by remember { mutableDoubleStateOf(0.0) }
            var totalProducto by remember { mutableDoubleStateOf(0.0) }
            var esCambioPorMontoDescuento by remember { mutableStateOf(false) }
            var isMenuVisible by remember { mutableStateOf(false) }
            val simboloMonedaArticulo by remember { mutableStateOf(if(codigoMoneda == "CRC") "\u20A1 " else "\u0024 ") }

            // Función para calcular totales
            fun calcularTotales() {
                var cantidad = if (cantidadProducto.isEmpty()) 0.00 else cantidadProducto.toDouble()
                val precio = if (precioProducto.isEmpty()) 0.00 else precioProducto.toDouble()
                var porcentajeDescuento = if(porcentajeDescuentoProducto.isEmpty()) 0.00 else porcentajeDescuentoProducto.toDouble()
                var tempMontoDescuento = if(montoDescuento.isEmpty()) 0.00 else montoDescuento.toDouble()

                if (seleccionPresentacion != "Unidad"){
                    cantidad *= articulo.unidadXMedida
                }

                precioUnitarioIva = precio + (precio * (articulo.impuesto) / 100)
                cantidadProductoForApi = cantidad
                subtotal = cantidad * precio

                if (esCambioPorMontoDescuento){
                    porcentajeDescuento =  tempMontoDescuento * 100 / subtotal
                    porcentajeDescuentoProducto = porcentajeDescuento.toString()
                }else{
                    tempMontoDescuento = (subtotal * porcentajeDescuento)/100
                    montoDescuento = tempMontoDescuento.toString()
                }

                val subtotalConDescuento = subtotal - tempMontoDescuento
                val iva = subtotalConDescuento * (articulo.impuesto) / 100

                montoIVA = iva
                totalProducto = subtotalConDescuento + iva


                esCambioPorMontoDescuento = false
            }

            LaunchedEffect(Unit) {
                calcularTotales()
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
                            .clickable(enabled = false) { }
                            .padding(objetoAdaptardor.ajustarAltura(16)),
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

                            Spacer(modifier = Modifier.height(8.dp))

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
                                nuevoValor2 = {bodegaSeleccionda = it},
                                valor = bodegaSeleccionda.valor,
                                contieneOpciones = true,
                                usarOpciones4 = true,
                                opciones4 = articulo.listaBodegas,
                                usarModifierForSize = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .border(2.dp,
                                        color = Color.Gray,
                                        RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                    ),
                                mostrarLeadingIcon = true,
                                leadingIcon = Icons.Default.Warehouse,
                                isUltimo = true,
                                medidaAncho = 350,
                                tomarAnchoMaximo = false,
                                fontSize = obtenerEstiloBodyBig(),
                                mostrarLabel = false
                            )

                            // DATOS BODEGA
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(objetoAdaptardor.ajustarAltura(4)),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TText(
                                    text = "Codigo: ${bodegaSeleccionda.clave}",
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

                            Spacer(modifier = Modifier.height(8.dp))

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
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
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
                                        label = "Precio Unitario:",
                                        textPlaceholder = "Precio",
                                        nuevoValor2 = {
                                            tipoPrecioSeleccionado = it
                                            precioProducto = it.valor
                                            calcularTotales()
                                        },
                                        valor = tipoPrecioSeleccionado.clave,
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
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
                                            ),
                                        mostrarLeadingIcon = true,
                                        leadingIcon = Icons.Filled.PriceChange,
                                        isUltimo = true,
                                        medidaAncho = 350,
                                        tomarAnchoMaximo = false,
                                        fontSize = obtenerEstiloBodyBig(),
                                        mostrarLabel = false,
                                        cantidadLineas = 1,
                                        permitirPuntosDedimales = seleccionPresentacion == "Unidad"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

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
                                            cantidadProducto = it
                                            calcularTotales()
                                        },
                                        valor = cantidadProducto,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(2.dp, color = Color.Gray, RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))),
                                        isUltimo = true,
                                        fontSize = obtenerEstiloBodyBig(),
                                        cantidadLineas = 1,
                                        mostrarPlaceholder = true,
                                        textPlaceholder = "0.00",
                                        mostrarLabel = false,
                                        soloPermitirValoresNumericos = true
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
                                            precioProducto = it
                                            calcularTotales()
                                        },
                                        valor = precioProducto,
                                        usarModifierForSize = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .border(2.dp, color = Color.Gray, RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))),
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

                            Spacer(modifier = Modifier.height(8.dp))

                            // MONTO DESCUENTO Y PORCENTAJE DE DESCUENTO
                            if (articulo.descuentoAdmitido != 0.00){
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
                                                val temp = if(it.isEmpty()) 0.00 else it.toDouble()
                                                if (temp<=articulo.descuentoAdmitido){
                                                    porcentajeDescuentoProducto = it
                                                    calcularTotales()
                                                }
                                            },
                                            modoEdicionActivado = (articulo.descuentoAdmitido != 0.00),
                                            valor = porcentajeDescuentoProducto,
                                            usarModifierForSize = true,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(
                                                    2.dp,
                                                    color = Color.Gray,
                                                    RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))
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
                                            nuevoValor = {
                                                montoDescuento = it
                                                esCambioPorMontoDescuento = true
                                                calcularTotales()
                                            },
                                            valor = montoDescuento,
                                            modoEdicionActivado = (articulo.descuentoAdmitido != 0.00),
                                            usarModifierForSize = true,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .border(2.dp, color = Color.Gray, RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))),
                                            isUltimo = true,
                                            fontSize = obtenerEstiloBodyBig(),
                                            cantidadLineas = 1,
                                            mostrarPlaceholder = true,
                                            textPlaceholder = "0.00",
                                            mostrarLabel = false,
                                            soloPermitirValoresNumericos = true,
                                            darFormatoMiles = true
                                        )
                                    }

                                }

                                Spacer(modifier = Modifier.height(8.dp))
                            }

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
                                        text = "Precio unitario (con IVA): ",
                                        textAlign = TextAlign.Start,
                                        fontSize = obtenerEstiloBodyBig()
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    TText(
                                        text = simboloMonedaArticulo + separacionDeMiles(precioUnitarioIva) +" $codigoMoneda",
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
                                        text = "Subtotal (sin IVA): ",
                                        textAlign = TextAlign.Start,
                                        fontSize = obtenerEstiloBodyBig()
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    TText(
                                        text = simboloMonedaArticulo + separacionDeMiles(subtotal) +" $codigoMoneda",
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
                                        text = "$simboloMonedaArticulo${separacionDeMiles(totalProducto)} $codigoMoneda",
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
                                    text = if (isAgregar) "Agregar" else "Editar",
                                    objetoAdaptardor = objetoAdaptardor,
                                    onClick = {
                                        if (cantidadProducto.isNotEmpty() && precioProducto.isNotEmpty()){
                                            val articuloTemp = ArticuloLineaProforma(
                                                numero = numeroProforma,
                                                articuloLine = articulo.articuloLineaId,
                                                tipoDocumento = tipoDocumento,
                                                articuloCodigo = articulo.codigo,
                                                articuloTipoPrecio = tipoPrecioSeleccionado.clave,
                                                articuloActividadEconomica = articulo.actividadEconomica,
                                                articuloCantidad = cantidadProductoForApi,
                                                articuloUnidadMedida = articulo.unidadMedida,
                                                presentacion = seleccionPresentacion,
                                                articuloBodegaCodigo = bodegaSeleccionda.clave,
                                                articuloVenta = precioProducto.toDouble(),
                                                articuloDescuentoPorcentage = porcentajeDescuentoProducto.toDouble(),
                                                articuloIvaPorcentage = articulo.impuesto,
                                                articuloIvaTarifa = articulo.Cod_Tarifa_Impuesto,
                                                idCliente = clienteId,
                                                articuloVentaSubTotal1 = precioProducto.toDouble()
                                            )
                                            agregaEditaArticulo(articuloTemp)
                                        }else{
                                            if (cantidadProducto.isEmpty() ){
                                                mostrarMensajeError("La cantidad del artículo deber ser mayor a '0.00'.")
                                            }else{
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

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (bxSuperior,flechaRegresar, lzColumPrincipal, iconoActualizar, bxInferior) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(70))
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
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "Icono Facturación",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Facturación",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = obtenerEstiloDisplayBig(),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.constrainAs(flechaRegresar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(16))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
            )
        }

        IconButton(
            onClick = {
                validacionCargaFinalizada++
                actualizarListaArticulos = false
                actuzalizarDatosProforma = true
            },
            modifier = Modifier.constrainAs(iconoActualizar) {
                end.linkTo(parent.end)
                top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(16))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Refresh,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
            )
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
                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                    onClick = {},
                                    textSize = obtenerEstiloBodyBig(),
                                    objetoAdaptardor = objetoAdaptardor
                                )
                                BButton(
                                    modifier = Modifier
                                        .weight(1f),
                                    text = "Guardar",
                                    onClick = {},
                                    textSize = obtenerEstiloBodyBig(),
                                    objetoAdaptardor = objetoAdaptardor
                                )
                                BButton(
                                    modifier = Modifier
                                        .weight(1f),
                                    text = "Clonar",
                                    onClick = {},
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
                                .padding(top = objetoAdaptardor.ajustarAltura(8), bottom = objetoAdaptardor.ajustarAltura(8))
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
                                                .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                                .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                                .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                                .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
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
                                        text = "Número Proforma: $numeroProforma",
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        fontSize = obtenerEstiloTitleSmall()
                                    )
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
                                            onClick = {},
                                            modifier = Modifier
                                                .weight(0.8f),
                                            textSize = obtenerEstiloBodyBig(),
                                            objetoAdaptardor = objetoAdaptardor
                                        )
                                        BButton(
                                            text = "Buscar",
                                            onClick = {
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
                                        textTitle = "Nombre del Cliente:",
                                        text = "Nombre del Cliente",
                                        variable = nombre,
                                        nuevoValor = {nombre=it},
                                        icon = Icons.Default.Person
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
                                                textTitle = "Tipo de Cédula:",
                                                text = "Tipo de Cédula",
                                                variable = tipoCedula,
                                                nuevoValor = {tipoCedula=it},
                                                icon = Icons.Default.Person
                                            )
                                            BasicTexfiuldWithText(
                                                textTitle = "Cédula:",
                                                text = "Cédula",
                                                variable = numeroCedula,
                                                nuevoValor = {numeroCedula=it},
                                                icon = Icons.Default.Person
                                            )
                                            BasicTexfiuldWithText(
                                                textTitle = "Email General:",
                                                text = "Email General",
                                                variable = emailGeneral,
                                                nuevoValor = {emailGeneral=it},
                                                icon = Icons.Default.Person
                                            )
                                            BasicTexfiuldWithText(
                                                textTitle = "Nombre Comercial:",
                                                text = "Nombre Comercial",
                                                variable = nombreComercial,
                                                nuevoValor = {nombreComercial=it},
                                                icon = Icons.Default.Person
                                            )
                                            BasicTexfiuldWithText(
                                                textTitle = "Teléfonos:",
                                                text = "Teléfonos",
                                                variable = telefonos,
                                                nuevoValor = {telefonos=it},
                                                icon = Icons.Default.Person
                                            )
                                            BasicTexfiuldWithText(
                                                textTitle = "Plazo de Crédito en Dias:",
                                                text = "Plazo de Crédito en Dias",
                                                variable = plazoCredito,
                                                nuevoValor = {plazoCredito=it},
                                                icon = Icons.Default.Person
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
                                .padding(top = objetoAdaptardor.ajustarAltura(8), bottom = objetoAdaptardor.ajustarAltura(8))
                                .shadow(
                                    elevation = objetoAdaptardor.ajustarAltura(7),
                                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                ),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            if (isCargandoDatos || soloActualizarArticulos){
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
                                                .padding(
                                                    start = objetoAdaptardor.ajustarAncho(8),
                                                    end = objetoAdaptardor.ajustarAncho(8),
                                                    top = objetoAdaptardor.ajustarAncho(8)
                                                )
                                        )
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(4))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(objetoAdaptardor.ajustarAltura(30))
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                visible = (!isCargandoDatos && !soloActualizarArticulos),
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
                                            text = "Articulos",
                                            modifier = Modifier
                                                .weight(1f),
                                            fontSize = obtenerEstiloTitleBig()
                                        )
                                        BButton(
                                            text = "Agregar",
                                            onClick = {
                                                iniciarMenuSeleccionarArticulo = true
                                            },
                                            modifier = Modifier
                                                .weight(1f),
                                            textSize = obtenerEstiloBodyBig(),
                                            objetoAdaptardor = objetoAdaptardor
                                        )
                                        BButton(
                                            text = "Opciones",
                                            onClick = {},
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
                                        TText("Descripción", Modifier.weight(1.25f), textAlign = TextAlign.Center)
                                        TText("Cant", Modifier.weight(0.5f), textAlign = TextAlign.Center)
                                        TText("Precio.Unit", Modifier.weight(1f), textAlign = TextAlign.Center)
                                        TText("Desc", Modifier.weight(0.5f), textAlign = TextAlign.Center)
                                        TText("Total", Modifier.weight(1.25f), textAlign = TextAlign.Center)
                                    }

                                    listaArticulosProforma.forEach { articulo ->
                                        var isSeleccionado by remember { mutableStateOf(false) }

                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(2)),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clickable {
                                                    val articuloTemp = listaArticulosFacturacion.find { it.codigo == articulo.codigo }
                                                    if (articuloTemp != null){
                                                        val listaBodegas = articuloTemp.listaBodegas
                                                        val listaPrecios = articuloTemp.listaPrecios
                                                        articulo.listaBodegas = listaBodegas
                                                        articulo.listaPrecios = listaPrecios
                                                        articulo.descuentoAdmitido = articuloTemp.descuentoAdmitido
                                                        articulo.unidadXMedida = articuloTemp.unidadXMedida
                                                        articulo.actividadEconomica = articuloTemp.actividadEconomica
                                                        articulo.Cod_Tarifa_Impuesto = articuloTemp.codTarifaImpuesto
                                                        articulo.unidadMedida = articuloTemp.unidadMedida
                                                        articuloActual = articulo
                                                        isAgregar = false
                                                        iniciarMenuAgregaEditaArticulo = true
                                                    }else{
                                                        mostrarMensajeError("No se logró encontrar las bodegas y los precios de este artículo actualice y vuelva a intentar.")
                                                    }
                                                }
                                                .padding(vertical = objetoAdaptardor.ajustarAltura(2))
                                        ) {
                                            Checkbox(
                                                checked = isSeleccionado,
                                                onCheckedChange = { valor ->
                                                    isSeleccionado = valor
                                                    if (isSeleccionado) {
                                                        listaArticulosSeleccionados.add(articulo)
                                                    } else {
                                                        listaArticulosSeleccionados.remove(articulo)
                                                    }
                                                },
                                                modifier = Modifier.weight(0.25f).scale(0.7f),
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = Color(0xFF244BC0),
                                                    uncheckedColor = Color.Gray,
                                                    checkmarkColor = Color.White
                                                )
                                            )

                                            TText(articulo.descripcion, Modifier.weight(1f), textAlign = TextAlign.Start, maxLines = 3)
                                            TText(articulo.articuloCantidad.toString(), Modifier.weight(0.5f), textAlign =TextAlign.Center, maxLines = 3)
                                            TText(simboloMoneda + separacionDeMiles(articulo.precioNeto), Modifier.weight(1f), textAlign = TextAlign.Center, maxLines = 3)
                                            TText("${articulo.articuloDescuentoPorcentaje}%", Modifier.weight(0.5f), textAlign = TextAlign.Center, maxLines = 3)
                                            TText(simboloMoneda + separacionDeMiles(articulo.articuloVentaTotal), Modifier.weight(1f), textAlign = TextAlign.End, maxLines = 3)

                                            IconButton(
                                                onClick = {
                                                    lineaAcual = articulo.articuloLineaId
                                                    eliminarLinea = true
                                                },
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(18))
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.Delete,
                                                    contentDescription = "Eliminar",
                                                    tint = Color(0xFFEB4242),
                                                    modifier = Modifier
                                                        .size(objetoAdaptardor.ajustarAltura(18))
                                                        .weight(0.25f)
                                                )
                                            }
                                        }

                                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                                    }
                                }
                            }
                        }

                        // CARD DE TOTALES
                        Card(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(top = objetoAdaptardor.ajustarAltura(8), bottom = objetoAdaptardor.ajustarAltura(8))
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
                                                .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(6)))
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
                    }
                }
            }

            item {
                Box(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(95)))
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF000000))
                .height(objetoAdaptardor.ajustarAltura(25))
                .constrainAs(bxInferior) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }, contentAlignment = Alignment.Center
        ) {
            val versionApp = stringResource(R.string.app_version)

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center

            ){
                Text(
                    text = "#$codUsuario _ $nombreUsuario _ $nombreEmpresa",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabelBig(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(154)).padding(start = 4.dp)
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
                    text = "Version: $versionApp",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = obtenerEstiloLabelBig(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(130)).padding(end = 6.dp)
                )
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
            descuento.toDouble() > 0 && articuloActual.descuentoAdmitido != 0.00 -> descuento
            articuloActual.descuentoAdmitido != 0.00 -> articuloActual.descuentoFijo.toString()
            else -> "0.0"
        },
        isAgregar = isAgregar,
        agregaEditaArticulo = {
            articuloLineaProforma = it
            agregarEditarArticuloActual = true
        },
        bodega = if (articuloActual.articuloBodegaCodigo.isEmpty()) articuloActual.listaBodegas.first() else articuloActual.listaBodegas.find { it.clave == articuloActual.articuloBodegaCodigo } ?: ParClaveValor(),
        precioVenta = articuloActual.codPrecioVenta.ifEmpty { tipoPrecio }
    )

    if(iniciarMenuSeleccionarArticulo){
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
                            text = "Agregar Artículo",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                        ){
                            BBasicTextField(
                                value = datosIngresadosBarraBusquedaArticulos,
                                onValueChange = {
                                    isCargandoArticulos = true
                                    datosIngresadosBarraBusquedaArticulos = it
                                    if (it.isEmpty()){
                                        apiConsultaBusquedaArticulos?.cancel()
                                        isCargandoArticulos = false
                                        listaArticulosEncontrados = emptyList()
                                    }
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(focusRequester),
                                objetoAdaptardor = objetoAdaptardor,
                                utilizarMedidas = false,
                                fontSize = obtenerEstiloTitleMedium()
                            )
                            IconButton(
                                onClick = {
                                    iniciarMenuSeleccionarArticulo = false
                                    isMenuVisible = false
                                    listaArticulosEncontrados = emptyList()
                                    datosIngresadosBarraBusquedaArticulos = ""
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

    if(iniciarMenuSeleccionarCliente){
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
                            BBasicTextField(
                                value = datosIngresadosBarraBusquedaCliente,
                                onValueChange = {
                                    datosIngresadosBarraBusquedaCliente = it
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .focusRequester(focusRequester),
                                objetoAdaptardor = objetoAdaptardor,
                                utilizarMedidas = false,
                                fontSize = obtenerEstiloTitleMedium()
                            )
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





@Composable
@Preview
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazFacturacion("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDM2MiIsIk5vbWJyZSI6IllFU0xFUiBMT1JJTyIsIkVtYWlsIjoieWVzbGVybG9yaW9AZ21haWwuY29tIiwiUHVlcnRvIjoiODAxIiwiRW1wcmVzYSI6IlpHVnRiMlpsY25KbCIsIlNlcnZlcklwIjoiTVRreUxqRTJPQzQzTGpNdyIsInRpbWUiOiIyMDI1MDMxMjA1MDMwOSJ9.JrUHQoYYnWJibwMi1B2-iGBTGk-_-2jPqdLAiJ57AJM", null, nav, "demoferre","00050","YESLER LORIO")
}