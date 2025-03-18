package com.soportereal.invefacon.interfaces.modulos.facturacion

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Warehouse
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.TextFieldMultifuncional
import com.soportereal.invefacon.funciones_de_interfaces.isSoloNumeros
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.interfaces.obtenerEstiloBodyMedium
import com.soportereal.invefacon.interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.interfaces.obtenerEstiloHeadMedium
import com.soportereal.invefacon.interfaces.obtenerEstiloLabelBig
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleMedium
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var expandedClientes by remember { mutableStateOf(false) }
//    var expandedArticulos by remember { mutableStateOf(false) }
    var expandedTotales by remember { mutableStateOf(false) }
    val listaArticulosSeleccionados = remember { mutableStateListOf<ArticuloFacturado>() }
    var listaArticulosProforma by remember {  mutableStateOf<List<ArticuloFacturado>>(emptyList()) }
    var nombre by remember { mutableStateOf("") }
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
    var cuenta by remember { mutableStateOf("") }
    val objectoProcesadorDatosApi = ProcesarDatosModuloFacturacion(token)
    var isCargandoDatos by remember { mutableStateOf(false) }
    var articuloFacturadoActual by remember { mutableStateOf(ArticuloFacturado()) }
    var iniciarMenuDatosArticulo by remember { mutableStateOf(false) }
    var iniciarMenuSeleccionarArticulo by remember { mutableStateOf(false) }
    var actuzalizarDatosProforma by remember { mutableStateOf(true) }
    var apiConsultaActual by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApi= CoroutineScope(Dispatchers.IO)
    val transition = rememberInfiniteTransition(label = "shimmer")
    var errorCargarProforma by remember { mutableStateOf(false) }
    val simboloMoneda by remember { mutableStateOf(if(codMonedaProforma == "CRC") "\u20A1 " else "\u0024 ") }
    var listaArticulosBuscados by remember { mutableStateOf(emptyList<ArticuloFaturacion>()) }
    var datosIngresadosBarraBusquedaArticulos by remember { mutableStateOf("") }
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

    LaunchedEffect(actuzalizarDatosProforma) {
        if (actuzalizarDatosProforma){
            listaArticulosSeleccionados.clear()
            isCargandoDatos = true
            errorCargarProforma = false
            apiConsultaActual?.cancel()
            apiConsultaActual= cortinaConsultaApi.launch{
                objectoProcesadorDatosApi.crearNuevaProforma()

                val result= objectoProcesadorDatosApi.abrirProforma()
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    if(validarExitoRestpuestaServidor(result)) {
                        val data = result.getJSONObject("data")

                        //DATOS CLIENTE
                        val datosCliente = data.getJSONArray("cliente").getJSONObject(0)
                        cuenta= datosCliente.getString("ClienteID")
                        nombre= datosCliente.getString("ClienteNombre")
                        nombreComercial = datosCliente.getString("clientenombrecomercial")
                        numeroCedula = datosCliente.getString("Cedula")
                        emailGeneral = datosCliente.getString("Email")
                        telefonos = datosCliente.getString("Telefonos")
                        tipoCedula = datosCliente.getString("TipoIdentificacion")
                        plazoCredito = datosCliente.getString("plazo")
                        tipoPrecio = datosCliente.getString("TipoPrecioVenta")
                        codMonedaCliente = datosCliente.getString("monedacodigo")

                        //DATOS PROFORMA
                        val datosProforma = data.getJSONArray("datos").getJSONObject(0)
                        numeroProforma= datosProforma.getString("Numero")

                        //Tipo Moneda
                        codMonedaProforma = data.getString("monedaDocumento")

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
                        val listaArticuloFacturados = mutableListOf<ArticuloFacturado>()
                        val articulos = data.getJSONArray("proforma")
                        for(i in 0 until articulos.length()){
                            val datosArticulo = articulos.getJSONObject(i)
                            val articuloFacturado = ArticuloFacturado(
                                articuloCodigo = datosArticulo.getString("ArticuloCodigo"),
                                pv = datosArticulo.getString("PV"),
                                descripcion = datosArticulo.getString("Descripcion"),
                                articuloCantidad = datosArticulo.getInt("ArticuloCantidad"),
                                precioUd = datosArticulo.getDouble("PrecioUd"),
                                articuloDescuentoPorcentaje = datosArticulo.getDouble("ArticuloDescuentoPorcentage"),
                                articuloDescuentoMonto = datosArticulo.getDouble("ArticuloDescuentoMonto"),
                                articuloVentaSubTotal2 = datosArticulo.getDouble("ArticuloVentaSubTotal2"),
                                articuloVentaGravado = datosArticulo.getDouble("ArticuloVentaGravado"),
                                articuloIvaExonerado = datosArticulo.getDouble("ArticuloIvaExonerado"),
                                articuloVentaExento = datosArticulo.getDouble("ArticuloVentaExento"),
                                articuloIvaPorcentaje = datosArticulo.getDouble("ArticuloIvaPorcentage"),
                                articuloIvaMonto = datosArticulo.getDouble("ArticuloIvaMonto"),
                                articuloBodegaCodigo = datosArticulo.getString("ArticuloBodegaCodigo"),
                                articuloVentaTotal = datosArticulo.getDouble("ArticuloVentaTotal"),
                                existencia = datosArticulo.getDouble("Existencia"),
                                articuloLineaId = datosArticulo.getInt("ArticuloLineaId"),
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
                delay(300)
                actuzalizarDatosProforma= false
                isCargandoDatos= false
            }
        }
    }

    LaunchedEffect(datosIngresadosBarraBusquedaArticulos) {
        if (datosIngresadosBarraBusquedaArticulos.isNotEmpty()){
            var codigo = ""
            var descripcion = ""
            apiConsultaActual?.cancel()
            apiConsultaActual= cortinaConsultaApi.launch{
                delay(500)
                if(datosIngresadosBarraBusquedaArticulos.isSoloNumeros()) {
                    codigo = datosIngresadosBarraBusquedaArticulos
                    descripcion = ""
                }else{
                    codigo = ""
                    descripcion =datosIngresadosBarraBusquedaArticulos
                }
                val result = objectoProcesadorDatosApi.obtenerArticulos(
                    codigo = codigo,
                    descripcion = descripcion,
                    tipoPrecio = tipoPrecio,
                    moneda = codMonedaCliente
                )
                if (result!=null){
                    if (validarExitoRestpuestaServidor(result)){
                        val data = result.getJSONArray("data")
                        for(i in 0 until data.length()){
                            val datosArticulo = data.getJSONObject(i)
                            println(datosArticulo)
                        }
                    }else{
                        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    }
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
                        if (isCargandoDatos){
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(objetoAdaptardor.ajustarAltura(35))
                                    .background(brush, shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(4)))
                            )
                        }

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
                            if (isCargandoDatos){
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
                                visible = !isCargandoDatos,
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
                                            text = "Cuenta: $cuenta",
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
                                            onClick = {},
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
                                visible = !isCargandoDatos,
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
                                                    articuloFacturadoActual = articulo
                                                    iniciarMenuDatosArticulo = true
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
                                            TText(simboloMoneda + separacionDeMiles(articulo.precioUd), Modifier.weight(1f), textAlign = TextAlign.Center, maxLines = 3)
                                            TText("${articulo.articuloDescuentoPorcentaje}%", Modifier.weight(0.5f), textAlign = TextAlign.Center, maxLines = 3)
                                            TText(simboloMoneda + separacionDeMiles(articulo.articuloVentaTotal), Modifier.weight(1f), textAlign = TextAlign.End, maxLines = 3)

                                            IconButton(
                                                onClick = { /* Lógica para eliminar */ },
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
                                            text = simboloMoneda + separacionDeMiles(totalGravado),
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
                                            text = simboloMoneda + separacionDeMiles(totalIva),
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
                                            text = simboloMoneda + separacionDeMiles(totalDescuento),
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
                                                    text = simboloMoneda + separacionDeMiles(totalPago),
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
                                                    text = simboloMoneda + separacionDeMiles(totalExonerado),
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
                                                    text = simboloMoneda + separacionDeMiles(totalIvaDevuelto),
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
                                                    text = simboloMoneda + separacionDeMiles(totalMercGrav),
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
                                            text = codMonedaProforma,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.weight(1f),
                                            fontSize = obtenerEstiloBodyBig()
                                        )
                                        TText(
                                            text = simboloMoneda + separacionDeMiles(total),
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

    ProductoDialog(
        mostrarVentanaArticulo = iniciarMenuDatosArticulo,
        onDismiss = {iniciarMenuDatosArticulo = false},
        nombreProducto = articuloFacturadoActual.descripcion,
        codigoProducto = articuloFacturadoActual.articuloCodigo,
        opcionesBodegas = listOf("Bodega Central", "Sucursal Norte"),
        monedaCodigo = codMonedaProforma,
        opcionesPresentacion = listOf("Caja", "Unidad"),
        impuestoProducto = articuloFacturadoActual.articuloIvaPorcentaje,
        textoBotonVentanaArticulos = "Agregar",
        onAgregarLinea = { id -> println("Agregando línea con ID: $id") },
        articuloLineaId = articuloFacturadoActual.articuloLineaId,
        objetoAdaptardor = objetoAdaptardor
    )

    if(iniciarMenuSeleccionarArticulo){
        var isMenuVisible by remember { mutableStateOf(true) }

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
                Surface(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight()
                        .padding(objetoAdaptardor.ajustarAltura(24))
                        .align(Alignment.TopCenter),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                    color = Color.White
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter,
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16))
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                            ){
                                BBasicTextField(
                                    value = datosIngresadosBarraBusquedaArticulos,
                                    onValueChange = {
                                        datosIngresadosBarraBusquedaArticulos = it
                                    },
                                    modifier = Modifier.weight(1f),
                                    objetoAdaptardor = objetoAdaptardor,
                                    utilizarMedidas = false,
                                    fontSize = obtenerEstiloBodyMedium()
                                )
                                IconButton(
                                    onClick = {
                                        iniciarMenuSeleccionarArticulo = false
                                    },
                                    modifier = Modifier.weight(0.2f)
                                ) {
                                    Box(
                                        contentAlignment = Alignment.Center,
                                        modifier = Modifier
                                            .size(objetoAdaptardor.ajustarAltura(30))
                                            .background(Color.Gray, shape = CircleShape)
                                            .clickable { }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.Close,
                                            contentDescription = "Cerrar",
                                            tint = Color.White,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                        )
                                    }
                                }
                            }
                            Column(
                                modifier = Modifier
                                    .verticalScroll(rememberScrollState())
                                    .heightIn(max = objetoAdaptardor.ajustarAltura(500)),
                                verticalArrangement = Arrangement.Top,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ProductoDialog(
    mostrarVentanaArticulo: Boolean,
    onDismiss: () -> Unit,
    nombreProducto: String,
    codigoProducto: String,
    opcionesBodegas: List<String>,
    monedaCodigo: String,
    opcionesPresentacion: List<String>,
    impuestoProducto: Double,
    textoBotonVentanaArticulos: String,
    onAgregarLinea: (Int) -> Unit,
    articuloLineaId: Int,
    objetoAdaptardor: FuncionesParaAdaptarContenido
) {
    if (mostrarVentanaArticulo) {
        var bodegaSeleccionda by remember { mutableStateOf("") }
        var cantidadProducto by remember { mutableStateOf("") }
        var precioProducto by remember { mutableStateOf("") }
        var seleccionPresentacion by remember { mutableStateOf("") }
        var descuentoProducto by remember { mutableStateOf("") }
        var montoDescuento by remember { mutableStateOf("") }
        val codigoBodega by remember { mutableStateOf("") }
        val existenciaBodega by remember { mutableStateOf("") }
        var isVerDatosBodega by remember { mutableStateOf(false) }
        var subtotal by remember { mutableDoubleStateOf(0.0) }
        var montoIVA by remember { mutableDoubleStateOf(0.0) }
        var totalProducto by remember { mutableDoubleStateOf(0.0) }
        var esCambioPorPorcentaje by remember { mutableStateOf(true) }
        var esCambioPorMontoDescuento by remember { mutableStateOf(false) }
        var expandedBodegas by remember { mutableStateOf(false) }
        var expandedMedida by remember { mutableStateOf(false) }
        var isMenuVisible by remember { mutableStateOf(false) }

        // Función para validar entradas de artículos
        fun validarEntradasArticulos(oldValor: String, newValor: String): String {
            // Implementar lógica de validación según necesidades
            return newValor
        }

        // Función para calcular totales
        fun calcularTotales() {
            val cantidad = cantidadProducto.toDoubleOrNull() ?: 0.0
            val precio = precioProducto.toDoubleOrNull() ?: 0.0

            subtotal = cantidad * precio

            val descuento = if (esCambioPorPorcentaje) {
                val porcentaje = descuentoProducto.toDoubleOrNull() ?: 0.0
                subtotal * porcentaje / 100
            } else {
                montoDescuento.toDoubleOrNull() ?: 0.0
            }

            if (esCambioPorPorcentaje) {
                montoDescuento = separacionDeMiles(descuento)
            } else if (esCambioPorMontoDescuento) {
                val porcentajeCalculado = if (subtotal > 0) (descuento / subtotal) * 100 else 0.0
                descuentoProducto = separacionDeMiles(porcentajeCalculado)
                esCambioPorMontoDescuento = false
            }

            val subtotalConDescuento = subtotal - descuento
            val iva = subtotalConDescuento * (impuestoProducto) / 100
            montoIVA = iva
            totalProducto = subtotalConDescuento + iva
        }

        LaunchedEffect(Unit) {
            esCambioPorPorcentaje = true
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
                            text = nombreProducto,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = obtenerEstiloHeadMedium(),
                            textAlign = TextAlign.Center,
                            maxLines = 2
                        )
                        TText(
                            text = "Código: $codigoProducto",
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
                            textPlaceholder = "Selccione una bodega.",
                            nuevoValor = {bodegaSeleccionda = it},
                            valor = bodegaSeleccionda,
                            contieneOpciones = true,
                            usarOpciones3 = true,
                            opciones3 = opcionesBodegas,
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

                        if (isVerDatosBodega) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TText(
                                    text = "Codigo: $codigoBodega",
                                    modifier = Modifier
                                        .weight(1f),
                                    textAlign = TextAlign.Start
                                )
                                TText(
                                    text = "Existencia: $existenciaBodega",
                                    modifier = Modifier
                                        .weight(1f),
                                    textAlign = TextAlign.End
                                )
                            }
                        }

                        TextButton(
                            onClick = { isVerDatosBodega = !isVerDatosBodega },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color(0xFF244BC0)
                            )
                        ) {
                            TText(
                                text = if (isVerDatosBodega) "Ocultar" else "Mostrar detalles",
                                textAlign = TextAlign.Center,
                                color = Color(0xFF244BC0)
                            )
                        }



                        Spacer(modifier = Modifier.height(8.dp))

                        // Cantidad y Precio en la misma línea
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
                                    nuevoValor = {cantidadProducto = it},
                                    valor = cantidadProducto,
                                    usarModifierForSize = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(2.dp, color = Color.Gray, RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))),
                                    isUltimo = true,
                                    fontSize = obtenerEstiloBodySmall(),
                                    cantidadLineas = 1,
                                    mostrarPlaceholder = false,
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
                                    nuevoValor = {precioProducto = it},
                                    valor = precioProducto,
                                    usarModifierForSize = true,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(2.dp, color = Color.Gray, RoundedCornerShape(objetoAdaptardor.ajustarAltura(12))),
                                    isUltimo = true,
                                    fontSize = obtenerEstiloBodySmall(),
                                    cantidadLineas = 1,
                                    mostrarPlaceholder = false,
                                    mostrarLabel = false,
                                    soloPermitirValoresNumericos = true,
                                    darFormatoMiles = true
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))


                        TText(
                            text = "Tipo de Medida:",
                            fontSize = obtenerEstiloBodyMedium(),
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray
                        )
                        TextFieldMultifuncional(
                            label = "Medida",
                            textPlaceholder = "Medida",
                            nuevoValor = { seleccionPresentacion = it },
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

                        Spacer(modifier = Modifier.height(8.dp))

                        // Subtotal
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TText(
                                text = "Subtotal:",
                                textAlign = TextAlign.Start,
                                fontSize = obtenerEstiloBodyMedium()
                            )
                            TText(
                                text = separacionDeMiles(subtotal),
                                textAlign = TextAlign.End,
                                fontSize = obtenerEstiloBodyBig()
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Descuento y Monto Descuento en la misma línea
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
                                    textPlaceholder = "Descuento",
                                    nuevoValor = { descuentoProducto = it },
                                    valor = descuentoProducto,
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
                                    permitirComas = true,
                                    cantidadLineas = 1
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                TText(
                                    text =  "Monto Descuento:",
                                    fontSize = obtenerEstiloBodyMedium(),
                                    fontWeight = FontWeight.Light,
                                    color = Color.DarkGray
                                )
                                TextFieldMultifuncional(
                                    label = "Monto",
                                    textPlaceholder = "Monto",
                                    nuevoValor = { newValue ->
                                        montoDescuento = validarEntradasArticulos(montoDescuento, newValue)
                                        esCambioPorMontoDescuento = true
                                        calcularTotales()
                                    },
                                    valor = montoDescuento,
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
                                    permitirComas = true,
                                    cantidadLineas = 1
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Impuestos y Total
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TText(
                                    text = "Impuesto (IVA %): $impuestoProducto" ,
                                    textAlign = TextAlign.End,
                                    fontSize = obtenerEstiloBodyBig()
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                TText(
                                    text = "Monto IVA: ${separacionDeMiles( montoIVA)}",
                                    textAlign = TextAlign.End,
                                    fontSize = obtenerEstiloBodyBig()
                                )
                            }

                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                TText(
                                    text = "Total: ",
                                    textAlign = TextAlign.End,
                                    fontSize = obtenerEstiloTitleSmall()
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                TText(
                                    text = "$monedaCodigo ${separacionDeMiles(totalProducto)}",
                                    textAlign = TextAlign.End,
                                    fontSize = obtenerEstiloTitleSmall()
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
                                text = "Agregar",
                                objetoAdaptardor = objetoAdaptardor,
                                onClick = {
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


@Composable
@Preview
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazFacturacion("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDM2MiIsIk5vbWJyZSI6IllFU0xFUiBMT1JJTyIsIkVtYWlsIjoieWVzbGVybG9yaW9AZ21haWwuY29tIiwiUHVlcnRvIjoiODAxIiwiRW1wcmVzYSI6IlpHVnRiMlpsY25KbCIsIlNlcnZlcklwIjoiTVRreUxqRTJPQzQzTGpNdyIsInRpbWUiOiIyMDI1MDMxMjA1MDMwOSJ9.JrUHQoYYnWJibwMi1B2-iGBTGk-_-2jPqdLAiJ57AJM", null, nav, "demoferre","00050","YESLER LORIO")
}