package com.soportereal.invefacon.interfaces.modulos.ventas

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Numbers
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.ButtonFecha
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.formatearFechaTexto
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloLabelBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerFechaHoy
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IniciarInterfazVentas(
    token: String,
    navController: NavController,
    nombreEmpresa: String,
    codUsuario: String,
    nombreUsuario: String
) {
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla = configuration.fontScale
    val objetoAdaptardor = FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val objectoProcesadorDatosApi= ProcesarDatosModuloVentas(token)
    var fechafinalProforma by rememberSaveable { mutableStateOf(obtenerFechaHoy()) }
    var fechaInicialProforma by rememberSaveable { mutableStateOf(obtenerFechaHoy()) }
    var numeroDocumento by rememberSaveable { mutableStateOf("") }
    var usuario by rememberSaveable { mutableStateOf("") }
    var formaPago by rememberSaveable { mutableStateOf("") }
    var nombreFactura by rememberSaveable { mutableStateOf("") }
    var codCliente by rememberSaveable { mutableStateOf("") }
    var estado by rememberSaveable { mutableStateOf("") }
    var moneda by rememberSaveable { mutableStateOf("") }
    var tipoDocumento by rememberSaveable { mutableStateOf("") }
    var medioPago by rememberSaveable { mutableStateOf("") }
    var agente by rememberSaveable { mutableStateOf("") }
    var gravado by rememberSaveable { mutableDoubleStateOf(0.00) }
    var exonerado by rememberSaveable { mutableDoubleStateOf(0.00) }
    var exento by rememberSaveable { mutableDoubleStateOf(0.00) }
    var iva by rememberSaveable { mutableDoubleStateOf(0.00) }
    var impuestoServicio by rememberSaveable { mutableDoubleStateOf(0.00) }
    var total by rememberSaveable { mutableDoubleStateOf(0.00) }
    var expandedOpciFiltros by rememberSaveable { mutableStateOf(false) }
    var listaFacturas by rememberSaveable { mutableStateOf<List<FacturaVentas>>(emptyList()) }
    var apiConsultaFacturas by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApiFacturas= CoroutineScope(Dispatchers.IO)
    var isBuscandoFacturas by rememberSaveable { mutableStateOf(false) }
    val listaMonedas by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("CRC", "CRC"),
                ParClaveValor("USD", "USD"),
                ParClaveValor("EUR", "EUR")
            )
        )
    }
    val listaTiposDocumentos by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("00", "00-TODO"),
                ParClaveValor("01", "01-FACTURA"),
                ParClaveValor("02", "02-NOTA DE DEBITO"),
                ParClaveValor("03", "03-NOTA DE CREDITO"),
                ParClaveValor("04", "04-TIQUETE"),
                ParClaveValor("09", "09-EXPORTACION")
            )
        )
    }
    val listaEstados by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("0", "0-TODO"),
                ParClaveValor("1", "1-PROCESANDO"),
                ParClaveValor("2", "2-PROCESADO"),
                ParClaveValor("3", "3-NULA"),
                ParClaveValor("4", "4-CAJA"),
                ParClaveValor("5", "5-RECHAZADAS")
            )
        )
    }
    val listaMedioPago by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("00", "00-TODO"),
                ParClaveValor("01", "01-EFECTIVO"),
                ParClaveValor("02", "02-TARJETA"),
                ParClaveValor("03", "04-CHEQUE"),
                ParClaveValor("04", "05-TRANSFERENCIA")
            )
        )
    }
    var listaFormaPago by rememberSaveable { mutableStateOf<List<ParClaveValor>>(emptyList()) }
    var expandedTotales by rememberSaveable { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val lazyState= rememberLazyListState()
    var paginaActualLazyColumn by rememberSaveable { mutableIntStateOf(1) }
    var ultimaPaginaLazyColumn by rememberSaveable { mutableIntStateOf(1) }
    val focusManager = LocalFocusManager.current


    suspend fun buscarFacturas(isPaginacion: Boolean = false){
        isBuscandoFacturas = true
        if(!isPaginacion) listaFacturas = emptyList()
        if(!isPaginacion) paginaActualLazyColumn = 1
        delay(500)
        apiConsultaFacturas?.cancel()
        apiConsultaFacturas= cortinaConsultaApiFacturas.launch{
            val result = objectoProcesadorDatosApi.obtenerFacturas(
                fechaInicio = fechaInicialProforma,
                fechaFinal = fechafinalProforma,
                numeroDocumento = numeroDocumento,
                moneda = moneda,
                codCliente = codCliente,
                agente = agente,
                usuario = usuario,
                estado = estado,
                formaPago = formaPago,
                medioPago = medioPago,
                nombreFactura = nombreFactura,
                tipoDocumento = tipoDocumento,
                paginActual = paginaActualLazyColumn.toString()
            )
            if (result == null) return@launch
            if (!validarExitoRestpuestaServidor(result)){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                return@launch
            }
            val listaFacturastemp = mutableListOf<FacturaVentas>()
            val resultado = result.getJSONObject("resultado")
            ultimaPaginaLazyColumn = resultado.getInt("paginas")
            val data = resultado.getJSONArray("data")
            for (i in 0 until data.length()){
                val datosFactura = data.getJSONObject(i)
                val factura = FacturaVentas(
                    numero = datosFactura.getString("Numero"),
                    cliente = datosFactura.getString("NombreCliente"),
                    fecha = datosFactura.getString("Fecha"),
                    estado = datosFactura.getString("EstadoCodigo"),
                    impuestos = datosFactura.getDouble("Impuestos"),
                    total = datosFactura.getDouble("Total"),
                    moneda = datosFactura.getString("MonedaCodigo")
                )
                listaFacturastemp.add(factura)
            }
            listaFacturas = if (!isPaginacion) listaFacturastemp else listaFacturas + listaFacturastemp
            val totales = result.getJSONObject("totales")
            gravado = totales.getDouble("gravado")
            exonerado = totales.getDouble("exonerado")
            exento = totales.getDouble("exento")
            iva = totales.getDouble("iva")
            impuestoServicio = totales.getDouble("impuestoservicio")
            total = totales.getDouble("total")
            isBuscandoFacturas = false
        }

    }

    suspend fun obtenerFormaPago() {
        val result = objectoProcesadorDatosApi.obtenerFormaPago()
        if (result == null) return
        if (!validarExitoRestpuestaServidor(result)){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            return
        }
        listaFormaPago = emptyList()
        val data = result.getJSONObject("data")
        val tipos = data.getJSONArray("tipos")
        val listaFormaPagotemp = mutableListOf<ParClaveValor>()
        for ( i in 0 until tipos.length()){
            val datosFormaPago = tipos.getJSONObject(i)
            val codigo = datosFormaPago.getString("Cod_FormaPago")
            val descripcion = datosFormaPago.getString("Descripcion")
            val formaPagoTemp = ParClaveValor(clave = codigo, valor = "$codigo-$descripcion")
            listaFormaPagotemp.add(formaPagoTemp)
        }
        listaFormaPago = listaFormaPagotemp
        if (listaFormaPago.isEmpty()) listaFormaPagotemp.add(ParClaveValor(clave = "0", valor = "0-No definido"))
        if (listaFormaPago.isNotEmpty()) formaPago = listaFormaPago[0].clave
    }

    LaunchedEffect(Unit) {
        if (listaFacturas.isNotEmpty()) return@LaunchedEffect
        estado = listaEstados.first().clave
        moneda = listaMonedas.first().clave
        medioPago = listaMedioPago.first().clave
        tipoDocumento = listaTiposDocumentos.first().clave
        obtenerFormaPago()
        buscarFacturas()
    }

    LaunchedEffect(lazyState) {
        snapshotFlow { lazyState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleIndex ->
                // Trigger pagination when nearing the end of the list
                // lastVisibleIndex + 1 is used to account for 0-based index vs list.size
                if (lastVisibleIndex != null && lastVisibleIndex >= listaFacturas.size - 2 &&
                    !isBuscandoFacturas && paginaActualLazyColumn < ultimaPaginaLazyColumn) {
                    paginaActualLazyColumn += 1
                    buscarFacturas(true)
                }
            }
    }

    @Composable
    fun AddText(
        titulo: String,
        contenido: String,
        color: Color = Color.Black,
        textAlign: TextAlign = TextAlign.Center,
        fontSizeTitulo : TextUnit = obtenerEstiloBodyBig(),
        fontSizeContenido : TextUnit = obtenerEstiloBodyMedium()
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = fontAksharPrincipal,
                        fontSize = fontSizeTitulo,
                        color = color
                    )
                ) {
                    append(titulo)
                    append("\n")
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = fontSizeContenido,
                        fontFamily = FontFamily(Font(R.font.akshar_regular)),
                        fontWeight = FontWeight.Normal,
                        color = color
                    )
                ) {
                    append(contenido)
                }
            },
            textAlign = textAlign,
            maxLines = 10
        )
    }

    @Composable
    fun BasicTexfiuldWithText(
        textTitle: String,
        text: String,
        icon: ImageVector,
        variable: String,
        nuevoValor: (String) -> Unit,
        modifier: Modifier,
        opciones: List<ParClaveValor> = emptyList()
    ) {
        Column(
            modifier = modifier
        ) {
            TText(
                text = textTitle,
                fontSize = obtenerEstiloBodyBig()
            )

            BBasicTextField(
                value = variable,
                onValueChange = {
                   nuevoValor(it)
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
                backgroundColor = Color(0xFFE7E7E7),
                fontSize = obtenerEstiloBodyBig(),
                placeholder = text,
                icono = icon,
                iconsSize = 20,
                opciones = opciones
            )
        }
    }

    fun colorPorEstado(estado: String): Color {
        return when (estado) {
            "1" -> Color(0xFFFFEB3B) // Procesando
            "2" -> Color(0xFF4CAF50) // Procesada
            "3" -> Color(0xFFFF9800) // Nula
            "4" -> Color(0xFF244BC0) // Caja
            "5" -> Color(0xFFF44336) // Rechazada
            else -> Color.LightGray  // Estado desconocido
        }
    }

    @SuppressLint("UnrememberedMutableInteractionSource")
    @Composable
    fun BxContenedorFactura(
        factura : FacturaVentas,
        modifier: Modifier
    ) {
        val simboloMoneda =  if(factura.moneda == "CRC") "\u20A1 " else "\u0024 "
        Column(
            modifier = Modifier
                .clip(RectangleShape)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    navController.navigate(RutasPatallas.VentasDetalleFactura.ruta+"/$token"+"/$nombreEmpresa"+"/$codUsuario"+"/$nombreUsuario"+"/${factura.numero}")
                }
                .padding(vertical = objetoAdaptardor.ajustarAltura(4))
                .then(modifier)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(6))
            ) {

                Box(
                    modifier = Modifier
                        .size(objetoAdaptardor.ajustarAltura(20))
                        .clip(CircleShape) // o cualquier forma
                        .clickable(
                            onClick = {
                                navController.navigate(RutasPatallas.VentasDetalleFactura.ruta+"/$token"+"/$nombreEmpresa"+"/$codUsuario"+"/$nombreUsuario"+"/${factura.numero}")
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.RemoveRedEye,
                        contentDescription = "Ver",
                        tint = Color(0xFF244BC0),
                        modifier = Modifier.fillMaxSize()
                    )
                }


                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(2))
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        TText(
                            text = "#"+factura.numero,
                            modifier = Modifier
                                .padding(end = objetoAdaptardor.ajustarAncho(4)),
                            fontSize = obtenerEstiloBodySmall(),
                            color = Color.Gray
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        TText(
                            text = formatearFechaTexto(factura.fecha),
                            fontSize = obtenerEstiloBodySmall(),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                    }

                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        TText(
                            text = factura.cliente,
                            fontSize = obtenerEstiloBodyBig(),
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f),
                            maxLines = 3
                        )
                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                        Icon(
                            imageVector = Icons.Default.Circle,
                            contentDescription = "",
                            tint = colorPorEstado(factura.estado),
                            modifier = Modifier
                                .size(objetoAdaptardor.ajustarAltura(14))
                        )
                        TText(
                            text = listaEstados.find { it.clave == factura.estado }?.valor?:"-NULL".substringAfter("-"),
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(start = objetoAdaptardor.ajustarAltura(8)),
                            fontSize = obtenerEstiloBodySmall(),
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.akshar_regular)),
                            fontWeight = null
                        )
                    }
                    TText(
                        text = "Total: $simboloMoneda "+separacionDeMiles(factura.total),
                        fontSize = obtenerEstiloTitleSmall(),
                        color = Color(0xFF244BC0),
                        fontFamily = FontFamily(Font(R.font.akshar_regular)),
                        fontWeight = null 
                    )
                }
            }
            HorizontalDivider(thickness = 2.dp, color = Color.Black)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Fondo para la status bar
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
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
            val (bxSuperior,lzColumFacturas, bxContBusqueda, bxConTotales) = createRefs()

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0xFF244BC0))
                    .constrainAs(bxSuperior) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(bxContBusqueda.top)
                    },
                contentAlignment = Alignment.BottomCenter
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
                ) {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIosNew,
                            contentDescription = "Flecha atras",
                            tint = Color.White,
                            modifier = Modifier
                                .size(objetoAdaptardor.ajustarAltura(25))
                                .padding(0.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ShowChart,
                        contentDescription = "Icono Ventas",
                        tint = Color.White,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                    Text(
                        "Ventas",
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
                            focusManager.clearFocus()
                            apiConsultaFacturas?.cancel()
                            apiConsultaFacturas= cortinaConsultaApiFacturas.launch{
                                buscarFacturas()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Flecha atras",
                            tint = Color.White,
                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                        )
                    }

                }
            }

            Card(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(objetoAdaptardor.ajustarAltura(8))
                    .constrainAs(bxContBusqueda) {
                        start.linkTo(parent.start)
                        top.linkTo(bxSuperior.bottom)
                    }
                    .shadow(
                        elevation = objetoAdaptardor.ajustarAltura(7),
                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                    ),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(12)),
                    verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4))
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            objetoAdaptardor.ajustarAltura(4),
                            alignment = Alignment.CenterHorizontally
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = ""
                        )
                        TText(
                            text = "De",
                            fontSize = obtenerEstiloBodyBig(),
                            color = Color.Black
                        )

                        ButtonFecha(
                            valor = fechaInicialProforma,
                            nuevoValor = { fechaInicialProforma = it },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.weight(1f),
                            backgroundColor = Color(0xFFEEEEEE),
                            contenteColor = Color.Black,
                            textSize = obtenerEstiloBodySmall(),
                            conSombra = false
                        )
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = ""
                        )
                        TText(
                            text = "Al:",
                            fontSize = obtenerEstiloBodyBig(),
                            color = Color.Black
                        )
                        ButtonFecha(
                            valor = fechafinalProforma,
                            nuevoValor = { fechafinalProforma = it },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.weight(1f),
                            backgroundColor = Color(0xFFEEEEEE),
                            contenteColor = Color.Black,
                            textSize = obtenerEstiloBodySmall(),
                            conSombra = false
                        )
                    }
                    HorizontalDivider(
                        thickness = objetoAdaptardor.ajustarAltura(2),
                        color = Color.Black
                    )
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                    ){
                        BasicTexfiuldWithText(
                            variable = numeroDocumento,
                            nuevoValor = {
                                numeroDocumento = it
                            },
                            textTitle = "Documento:",
                            text =  "Documento...",
                            icon = Icons.Default.Numbers,
                            modifier = Modifier.weight(1f)
                        )
                        BasicTexfiuldWithText(
                            variable = nombreFactura,
                            nuevoValor = { nombreFactura = it },
                            textTitle = "Nombre de Factura:",
                            text =  "Nombre...",
                            icon = Icons.Default.PersonPin,
                            modifier = Modifier.weight(1f)
                        )
                    }
                    AnimatedVisibility(
                        visible = expandedOpciFiltros,
                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(6))
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                            ){
                                BasicTexfiuldWithText(
                                    variable = usuario,
                                    nuevoValor = { usuario = it },
                                    textTitle = "Usuario: ",
                                    text =  "Usuario...",
                                    icon = Icons.Default.PersonOutline,
                                    modifier = Modifier.weight(1f)
                                )

                                BasicTexfiuldWithText(
                                    variable = listaFormaPago.find { it.clave == formaPago }?.valor ?: "null",
                                    nuevoValor = { formaPago = it},
                                    textTitle = "Forma de Pago:",
                                    text =  "Forma de Pago...",
                                    icon = Icons.Default.AccountBalanceWallet,
                                    modifier = Modifier.weight(1f),
                                    opciones = listaFormaPago
                                )
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                            ){
                                BasicTexfiuldWithText(
                                    variable = codCliente,
                                    nuevoValor = { codCliente = it },
                                    textTitle = "Cod.Cliente: ",
                                    text =  "Cod.Cliente...",
                                    icon = Icons.Default.Person,
                                    modifier = Modifier.weight(1f)
                                )

                                BasicTexfiuldWithText(
                                    variable = listaEstados.find { it.clave == estado }?.valor ?: "null",
                                    nuevoValor = { estado = it },
                                    textTitle = "Estado:",
                                    text =  "Estado...",
                                    icon = Icons.Default.Schedule,
                                    modifier = Modifier.weight(1f),
                                    opciones = listaEstados
                                )
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                            ){
                                BasicTexfiuldWithText(
                                    variable = moneda,
                                    nuevoValor = {moneda = it},
                                    textTitle = "Moneda: ",
                                    text =  "Moneda...",
                                    icon = Icons.Default.AttachMoney,
                                    modifier = Modifier.weight(1f),
                                    opciones = listaMonedas
                                )

                                BasicTexfiuldWithText(
                                    variable = listaTiposDocumentos.find { it.clave == tipoDocumento }?.valor ?: "null",
                                    nuevoValor = {tipoDocumento = it},
                                    textTitle = "Tipo:",
                                    text =  "Tipo...",
                                    icon = Icons.Default.Receipt,
                                    modifier = Modifier.weight(1f),
                                    opciones = listaTiposDocumentos
                                )
                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                            ){
                                BasicTexfiuldWithText(
                                    variable = listaMedioPago.find { it.clave == medioPago }?.valor ?: "null",
                                    nuevoValor = {medioPago = it},
                                    textTitle = "Medio de Pago: ",
                                    text =  "Medio de Pago...",
                                    icon = Icons.Default.CreditCard,
                                    modifier = Modifier.weight(1f),
                                    opciones = listaMedioPago
                                )

                                BasicTexfiuldWithText(
                                    variable = agente,
                                    nuevoValor = {agente = it},
                                    textTitle = "Agente:",
                                    text =  "Agente...",
                                    icon = Icons.Default.Work,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                    BButton(
                        text =  if (expandedOpciFiltros) "Menos Filtros" else "Más Filtros",
                        onClick = {expandedOpciFiltros = !expandedOpciFiltros},
                        contenteColor = Color(0xFF244BC0),
                        backgroundColor = Color(0xFFFFFFFF),
                        conSombra = false,
                        textSize = obtenerEstiloBodyBig(),
                        objetoAdaptardor = objetoAdaptardor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(objetoAdaptardor.ajustarAltura(35))
                    )

                }
            }

            Card(
                modifier = Modifier
                    .pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            focusManager.clearFocus()
                        })
                    }
                    .padding(horizontal = objetoAdaptardor.ajustarAltura(8))
                    .constrainAs(lzColumFacturas) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(bxContBusqueda.bottom)
                        bottom.linkTo(bxConTotales.top, margin = objetoAdaptardor.ajustarAltura(8))
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .shadow(
                        elevation = objetoAdaptardor.ajustarAltura(7),
                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                    ),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(16)),
                    verticalArrangement = Arrangement.spacedBy(
                        objetoAdaptardor.ajustarAncho(4),
                        alignment = Alignment.CenterVertically
                    ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyColumn(
                        state = lazyState,
                        modifier = Modifier.wrapContentSize(),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(listaFacturas) { index, factura ->
                            val animatedAlpha = remember { androidx.compose.animation.core.Animatable(if (factura.isAnimado) 1f else 0f) }
                            val animatedTranslationY = remember { androidx.compose.animation.core.Animatable(if (factura.isAnimado) 0f else 50f) }
                            LaunchedEffect(key1 = factura.numero) {
                                if (!factura.isAnimado) {
                                    val animationDelay = if (index < 5) index * 80L else 0L
                                    delay(animationDelay)
                                    launch { animatedAlpha.animateTo(1f, animationSpec = tween(300)) }
                                    launch { animatedTranslationY.animateTo(0f, animationSpec = tween(300)) }
                                    factura.isAnimado = true
                                }
                            }
                            BxContenedorFactura(
                                factura = factura,
                                modifier = Modifier
                                    .graphicsLayer(
                                        alpha = animatedAlpha.value,
                                        translationY = animatedTranslationY.value
                                    )
                            )
                        }
                        item {
                            if (isBuscandoFacturas && paginaActualLazyColumn == 1 && listaFacturas.isEmpty()){
                                // Indicador de carga inicial
                                CircularProgressIndicator(
                                    color = Color(0xFF244BC0),
                                    modifier = Modifier
                                        .size(objetoAdaptardor.ajustarAltura(50))
                                        .padding(8.dp)
                                )
                            } else if (isBuscandoFacturas){
                                CircularProgressIndicator(
                                    color = Color(0xFF244BC0),
                                    modifier = Modifier
                                        .size(objetoAdaptardor.ajustarAltura(50))
                                        .padding(8.dp)
                                )
                            } else if(listaFacturas.isEmpty()){
                                Icon(
                                    imageVector = Icons.Default.Dangerous,
                                    contentDescription = "ICONO DE PELIGRO",
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50)),
                                    tint = Color(0xFFEB4242)
                                )

                                TText(
                                    text ="No se encontraron Facturas.",
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200)),
                                    fontSize = obtenerEstiloTitleMedium(),
                                    maxLines = 5,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .padding(
                        bottom = objetoAdaptardor.ajustarAltura(15),
                        start = objetoAdaptardor.ajustarAncho(8),
                        end = objetoAdaptardor.ajustarAncho(8)
                    )
                    .constrainAs(bxConTotales) {
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }
                    .shadow(
                        elevation = objetoAdaptardor.ajustarAltura(12),
                        shape = RoundedCornerShape(
                            topStart = objetoAdaptardor.ajustarAltura(20),
                            topEnd = objetoAdaptardor.ajustarAltura(20),
                            bottomStart = 0.dp,
                            bottomEnd = 0.dp
                        )
                    ),
                shape = RoundedCornerShape(
                    topStart = objetoAdaptardor.ajustarAltura(20),
                    topEnd = objetoAdaptardor.ajustarAltura(20),
                    bottomStart = 0.dp,
                    bottomEnd = 0.dp
                ),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(8))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            expandedTotales = !expandedTotales
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            objetoAdaptardor.ajustarAncho(4)
                        )
                    ) {
                        AddText(titulo = "Gravado", contenido = separacionDeMiles(gravado))
                        Spacer(modifier = Modifier.weight(1f))
                        AddText(titulo = "IVA", contenido = separacionDeMiles(iva))
                        Spacer(modifier = Modifier.weight(1f))
                        AddText(titulo = "Total", contenido = separacionDeMiles(total), fontSizeTitulo = obtenerEstiloTitleMedium(), fontSizeContenido = obtenerEstiloTitleSmall(), color = Color(0xFF244BC0))

                    }

                    AnimatedVisibility(
                        visible = expandedTotales,
                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                    ) {
                        HorizontalDivider(color = Color.Black)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                objetoAdaptardor.ajustarAncho(4)
                            )
                        ) {
                            AddText(titulo = "Exonerado", contenido = separacionDeMiles(exonerado), color = Color.DarkGray)
                            Spacer(modifier = Modifier.weight(1f))
                            AddText(titulo = "Exento", contenido = separacionDeMiles(exento), color = Color.DarkGray)
                            Spacer(modifier = Modifier.weight(1f))
                            AddText(titulo = "Imp.Sevicio", contenido = separacionDeMiles(impuestoServicio), color = Color.DarkGray)

                        }
                    }

                    Box(
                        modifier = Modifier
                            .size(objetoAdaptardor.ajustarAltura(22))
                            .clip(CircleShape) // o cualquier forma
                            .clickable(
                                onClick = {
                                    expandedTotales = !expandedTotales
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = if (expandedTotales) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                            contentDescription = "Ver",
                            tint = Color(0xFF244BC0),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

            }
        }

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
}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazVentas("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDM2MiIsIk5vbWJyZSI6IllFU0xFUiBMT1JJTyIsIkVtYWlsIjoieWVzbGVybG9yaW9AZ21haWwuY29tIiwiUHVlcnRvIjoiODAxIiwiRW1wcmVzYSI6IlpHVnRiMlpsY25KbCIsIlNlcnZlcklwIjoiTVRJM0xqQXVNQzR4IiwidGltZSI6IjIwMjUwNjAzMDUwNjQ5In0.rt1zviKSi3oDG184LFdQm2FEOznyAKxkbx9nK5GPRyc",  nav, "demoferre","00050","YESLER LORIO")
}