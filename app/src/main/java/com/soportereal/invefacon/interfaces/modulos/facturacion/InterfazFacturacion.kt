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
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
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
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.obtenerEstiloBody
import com.soportereal.invefacon.interfaces.obtenerEstiloHead
import com.soportereal.invefacon.interfaces.obtenerEstiloTitle
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
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var expandedClientes by remember { mutableStateOf(false) }
//    var expandedArticulos by remember { mutableStateOf(false) }
    var expandedTotales by remember { mutableStateOf(false) }
    val listaArticulosSeleccionados = remember { mutableStateListOf<Articulo>() }
    var listaArticulosProforma by remember {  mutableStateOf<List<Articulo>>(emptyList()) }
    var nombre by remember { mutableStateOf("") }
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
    var codMoneda by remember { mutableStateOf("") }
    var numeroProforma by remember { mutableStateOf("") }
    var cuenta by remember { mutableStateOf("") }
    val objectoProcesadorDatosApi = ProcesarDatosModuloFacturacion(token)
    var isCargandoDatos by remember { mutableStateOf(true) }
    var mostrarDatosArticulo by remember { mutableStateOf(false) }
    var actuzalizarDatosProforma by remember { mutableStateOf(true) }
    var apiConsultaActual by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApi= CoroutineScope(Dispatchers.IO)
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

    LaunchedEffect(actuzalizarDatosProforma) {
        if (actuzalizarDatosProforma){
            isCargandoDatos = true
            apiConsultaActual?.cancel()
            apiConsultaActual= cortinaConsultaApi.launch{
                objectoProcesadorDatosApi.crearNuevaProforma()

                val result= objectoProcesadorDatosApi.abrirProforma()
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    if(validarExitoRestpuestaServidor(result)){
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

                        //DATOS PROFORMA
                        val datosProforma = data.getJSONArray("datos").getJSONObject(0)
                        numeroProforma= datosProforma.getString("Numero")

                        //Tipo Moneda
                        codMoneda = data.getString("monedaNacional")

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
                        val listaArticulos = mutableListOf<Articulo>()
                        val articulos = data.getJSONArray("proforma")
                        for(i in 0 until articulos.length()){
                            val datosArticulo = articulos.getJSONObject(i)
                            val articulo = Articulo(
                                codigo = datosArticulo.getString("ArticuloCodigo"),
                                descripcion = datosArticulo.getString("Descripcion"),
                                cantidad = datosArticulo.getInt("ArticuloCantidad"),
                                precioUnitario = datosArticulo.getDouble("PrecioUd"),
                                descuento = datosArticulo.getDouble("ArticuloDescuentoPorcentage"),
                                total = datosArticulo.getDouble("ArticuloVentaTotal")
                            )
                            listaArticulos.add(articulo)
                        }
                        listaArticulosProforma = listaArticulos
                    }
                }
                delay(500)
                actuzalizarDatosProforma= false
                isCargandoDatos= false
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
                    .padding(start = objetoAdaptardor.ajustarAncho(8)),
                color = Color.DarkGray
            )
            BBasicTextField(
                value = variable,
                onValueChange = {nuevoValor(it)},
                placeholder = text,
                utilizarMedidas = false,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(
                        start = objetoAdaptardor.ajustarAncho(4),
                        end = objetoAdaptardor.ajustarAncho(4),
                        top = objetoAdaptardor.ajustarAltura(2),
                        bottom = objetoAdaptardor.ajustarAltura(2)
                    ),
                objetoAdaptardor = objetoAdaptardor,
                icono = icon,
                iconTint = Color.Gray,
                textColor = Color.Black
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
        val (bxSuperior,flechaRegresar, lzColumPrincipal, iconoActualizar) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(70))
                .background(Color(0xFF244BC0))
                .constrainAs(bxSuperior) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
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
                    fontSize = objetoAdaptardor.ajustarFont(28),
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
                .fillMaxSize()
                .constrainAs(lzColumPrincipal) {
                    start.linkTo(parent.start)
                    top.linkTo(bxSuperior.bottom)
                }
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ){
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(objetoAdaptardor.ajustarAltura(8))
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
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
                                            .weight(1f)
                                            .height(objetoAdaptardor.ajustarAltura(35)),
                                        text = "Abrir",
                                        onClick = {},
                                        textSize = obtenerEstiloTitle()
                                    )
                                    BButton(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(objetoAdaptardor.ajustarAltura(35)),
                                        text = "Guardar",
                                        onClick = {},
                                        textSize = obtenerEstiloTitle()
                                    )
                                    BButton(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(objetoAdaptardor.ajustarAltura(35)),
                                        text = "Clonar",
                                        onClick = {},
                                        textSize = obtenerEstiloTitle()
                                    )
                                }

                            }

                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(objetoAdaptardor.ajustarAltura(8))
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
                                            .padding(objetoAdaptardor.ajustarAltura(8))
                                    ){
                                        Column(
                                            horizontalAlignment = Alignment.Start,
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4))
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .width(objetoAdaptardor.ajustarAncho(250))
                                                    .height(objetoAdaptardor.ajustarAltura(15))
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
                                                    .height(objetoAdaptardor.ajustarAltura(15))
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
                                                        .height(objetoAdaptardor.ajustarAltura(15))
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
                                                    .height(objetoAdaptardor.ajustarAltura(20))
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
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(8))
                                    ){
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4))
                                        ) {
                                            TText(
                                                text = "Número Proforma: $numeroProforma",
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = objetoAdaptardor.ajustarAncho(8),
                                                        end = objetoAdaptardor.ajustarAncho(8),
                                                        top = objetoAdaptardor.ajustarAncho(8)
                                                    ),
                                                fontSize = obtenerEstiloHead()
                                            )
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.PersonPin,
                                                    contentDescription = "ICONO DE CLIENTE",
                                                    modifier = Modifier.padding(start = objetoAdaptardor.ajustarAncho(4))
                                                )
                                                TText(
                                                    text = "Información del cliente",
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = objetoAdaptardor.ajustarAncho(4))
                                                    , fontSize = obtenerEstiloHead()
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
                                                        .padding(start = objetoAdaptardor.ajustarAncho(8))
                                                        .weight(1f)
                                                )
                                                BButton(
                                                    text = "Opciones",
                                                    onClick = {},
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                                    textSize = obtenerEstiloTitle()
                                                )
                                                BButton(
                                                    text = "Buscar",
                                                    onClick = {},
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                                    textSize = obtenerEstiloTitle()
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
                                                modifier = Modifier
                                                    .height(objetoAdaptardor.ajustarAltura(35)),
                                                conSombra = false,
                                                textSize = obtenerEstiloTitle()
                                            )
                                        }
                                    }
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(objetoAdaptardor.ajustarAltura(8))
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
                                                    .height(objetoAdaptardor.ajustarAltura(25))
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

                                AnimatedVisibility(
                                    visible = !isCargandoDatos,
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ){
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
                                                Icon(
                                                    imageVector = Icons.Default.Inventory,
                                                    contentDescription = "ICONO DE ARTICULOS"
                                                )
                                                TText(
                                                    text = "Articulos",
                                                    modifier = Modifier
                                                        .weight(1f),
                                                    fontSize = obtenerEstiloHead()
                                                )
                                                BButton(
                                                    text = "Agregar",
                                                    onClick = {},
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                                    textSize = obtenerEstiloTitle()
                                                )
                                                BButton(
                                                    text = "Opciones",
                                                    onClick = {},
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                                    textSize = obtenerEstiloTitle()
                                                )
                                            }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically,
                                                modifier = Modifier
                                                    .background(color = Color.LightGray)
                                            ){
                                                TText(
                                                    text = "Descripción",
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center
                                                )
                                                TText(
                                                    text = "Precio.Unit",
                                                    modifier = Modifier
                                                        .weight(0.9f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center
                                                )
                                                TText(
                                                    text = "Desc",
                                                    modifier = Modifier
                                                        .weight(0.5f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center
                                                )
                                                TText(
                                                    text = "Total",
                                                    modifier = Modifier
                                                        .weight(1.15f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center
                                                )
                                            }
                                            listaArticulosProforma.forEach { articulo ->
                                                var isSeleccionado by remember { mutableStateOf(false) }
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically
                                                ){
                                                    Checkbox(
                                                        checked = isSeleccionado,
                                                        onCheckedChange = { valor->
                                                            isSeleccionado = valor
                                                            val productoExistente = listaArticulosSeleccionados.find {it.codigo == articulo.codigo}
                                                            if (productoExistente==null){
                                                                listaArticulosSeleccionados.add(articulo)
                                                            }else{
                                                                listaArticulosSeleccionados.remove(articulo)
                                                            }
                                                        },
                                                        modifier = Modifier
                                                            .weight(0.1f)
                                                            .scale(0.7f)
                                                            .padding(1.dp),
                                                        colors = CheckboxDefaults.colors(
                                                            checkedColor = Color(0xFF244BC0),
                                                            uncheckedColor = Color.Gray,
                                                            checkmarkColor = Color.White
                                                        )
                                                    )
                                                    TText(
                                                        text = "(${articulo.cantidad}) "+articulo.descripcion,
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(objetoAdaptardor.ajustarAltura(2)),
                                                        fontSize = obtenerEstiloBody(),
                                                        textAlign = TextAlign.Center,
                                                        maxLines = 3,
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    TText(
                                                        text = separacionDeMiles(articulo.precioUnitario),
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(objetoAdaptardor.ajustarAltura(2)),
                                                        fontSize = obtenerEstiloBody(),
                                                        textAlign = TextAlign.Center,
                                                        maxLines = 3,
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    TText(
                                                        text = articulo.descuento.toString()+"%",
                                                        modifier = Modifier
                                                            .weight(0.6f)
                                                            .padding(objetoAdaptardor.ajustarAltura(2)),
                                                        fontSize = obtenerEstiloBody(),
                                                        textAlign = TextAlign.Center,
                                                        maxLines = 3,
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    TText(
                                                        text = separacionDeMiles(montoDouble = articulo.total),
                                                        modifier = Modifier
                                                            .weight(1f)
                                                            .padding(objetoAdaptardor.ajustarAltura(2)),
                                                        fontSize = obtenerEstiloBody(),
                                                        textAlign = TextAlign.Center,
                                                        maxLines = 3,
                                                        fontWeight = FontWeight.Light
                                                    )
                                                    IconButton(
                                                        onClick = {
                                                        },
                                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(18))
                                                    ) {
                                                        Icon(
                                                            imageVector =  Icons.Filled.Delete,
                                                            contentDescription = "Basurero",
                                                            tint = Color(0xFFEB4242) ,
                                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(18))
                                                        )
                                                    }
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
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
                                    .padding(objetoAdaptardor.ajustarAltura(8))
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
                                                    .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                                        .height(objetoAdaptardor.ajustarAltura(25))
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
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(objetoAdaptardor.ajustarAltura(16))
                                    ){
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                        ){
                                            TText(
                                                text = "Totales",
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.fillMaxWidth(),
                                                fontSize = obtenerEstiloHead()
                                            )
                                            Row {
                                                TText(
                                                    text = "Total Gravado:",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                TText(
                                                    text = separacionDeMiles(totalGravado),
                                                    textAlign = TextAlign.End,
                                                    modifier = Modifier.weight(1f)
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
                                                    modifier = Modifier.weight(1f)
                                                )
                                                TText(
                                                    text =separacionDeMiles(totalIva),
                                                    textAlign = TextAlign.End,
                                                    modifier = Modifier.weight(1f)
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
                                                    modifier = Modifier.weight(1f)
                                                )
                                                TText(
                                                    text =separacionDeMiles(totalDescuento),
                                                    textAlign = TextAlign.End,
                                                    modifier = Modifier.weight(1f)
                                                )
                                            }
                                            HorizontalDivider(
                                                thickness = 1.dp,
                                                color = Color.LightGray
                                            )
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
                                                    Row {
                                                        TText(
                                                            text = "Pago:",
                                                            textAlign = TextAlign.Start,
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                        TText(
                                                            text = separacionDeMiles(totalPago),
                                                            textAlign = TextAlign.End,
                                                            modifier = Modifier.weight(1f)
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
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                        TText(
                                                            text = separacionDeMiles(totalExonerado),
                                                            textAlign = TextAlign.End,
                                                            modifier = Modifier.weight(1f)
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
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                        TText(
                                                            text = separacionDeMiles(totalIvaDevuelto),
                                                            textAlign = TextAlign.End,
                                                            modifier = Modifier.weight(1f)
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
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                        TText(
                                                            text =separacionDeMiles(totalMercGrav),
                                                            textAlign = TextAlign.End,
                                                            modifier = Modifier.weight(1f)
                                                        )
                                                    }
                                                    HorizontalDivider(
                                                        thickness = 1.dp,
                                                        color = Color.LightGray
                                                    )
                                                }
                                            }
                                            Row {
                                                TText(
                                                    text = "Total:",
                                                    textAlign = TextAlign.Start,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                TText(
                                                    text = codMoneda,
                                                    textAlign = TextAlign.Center,
                                                    modifier = Modifier.weight(1f)
                                                )
                                                TText(
                                                    text =separacionDeMiles(total),
                                                    textAlign = TextAlign.End,
                                                    modifier = Modifier.weight(1f)
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
                                                modifier = Modifier
                                                    .height(objetoAdaptardor.ajustarAltura(35)),
                                                conSombra = false,
                                                textSize = obtenerEstiloTitle()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Box(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(70)))
            }
        }
    }

    ProductoDialog(
        mostrarVentanaArticulo = mostrarDatosArticulo,
        onDismiss = { /* Acción al cerrar */ },
        nombreProducto = "Laptop Gamer",
        codigoProducto = "LPT-1234",
        descripcionesBodegas = listOf("Bodega Central", "Sucursal Norte"),
        monedaCodigo = "USD",
        opcionesPresentacion = listOf("Caja", "Unidad"),
        impuestoProducto = "15%",
        textoBotonVentanaArticulos = "Agregar",
        onAgregarLinea = { id -> println("Agregando línea con ID: $id") },
        articuloLineaId = 1
    )
}


@Composable
fun ProductoDialog(
    mostrarVentanaArticulo: Boolean,
    onDismiss: () -> Unit,
    nombreProducto: String,
    codigoProducto: String,
    descripcionesBodegas: List<String>,
    monedaCodigo: String,
    opcionesPresentacion: List<String>,
    impuestoProducto: String,
    textoBotonVentanaArticulos: String,
    onAgregarLinea: (Int) -> Unit,
    articuloLineaId: Int
) {
    if (mostrarVentanaArticulo) {
        var selectedBodegaDescripcion by remember { mutableStateOf("") }
        var cantidadProducto by remember { mutableStateOf("") }
        var precioProducto by remember { mutableStateOf("") }
        var seleccionPresentacion by remember { mutableStateOf("") }
        var descuentoProducto by remember { mutableStateOf("") }
        var montoDescuento by remember { mutableStateOf("") }
        var codigoBodega by remember { mutableStateOf("") }
        var existenciaBodega by remember { mutableStateOf("") }
        var isVerDatosBodega by remember { mutableStateOf(false) }
        var subtotal by remember { mutableDoubleStateOf(0.0) }
        var montoIVA by remember { mutableDoubleStateOf(0.0) }
        var totalProducto by remember { mutableDoubleStateOf(0.0) }
        var esCambioPorPorcentaje by remember { mutableStateOf(true) }
        var esCambioPorMontoDescuento by remember { mutableStateOf(false) }

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
            val iva = subtotalConDescuento * (impuestoProducto.toDoubleOrNull() ?: 0.0) / 100
            montoIVA = iva
            totalProducto = subtotalConDescuento + iva
        }

        // Función para buscar bodega por descripción
        fun buscarBodegaPorDescripcion() {
            // Implementar lógica para obtener código y existencia de bodega
            codigoBodega = "XYZ123" // Ejemplo
            existenciaBodega = "150" // Ejemplo
        }

        LaunchedEffect(Unit) {
            esCambioPorPorcentaje = true
            calcularTotales()
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable { onDismiss() },
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .shadow(10.dp)
                    .width(400.dp)
                    .heightIn(max = 650.dp)
                    .clickable(enabled = false) { /* Prevent click through */ },
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Título del artículo
                    Text(
                        text = nombreProducto,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Código del producto
                        Text(
                            text = "Código: $codigoProducto",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            textAlign = TextAlign.Start
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Sección de Bodega
                        Column(
                            modifier = Modifier.padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Bodega",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            ExposedDropdownMenuBox(
                                expanded = false,
                                onExpandedChange = { /* Implementar lógica */ }
                            ) {
                                OutlinedTextField(
                                    value = selectedBodegaDescripcion,
                                    onValueChange = {
                                        selectedBodegaDescripcion = it
                                        buscarBodegaPorDescripcion()
                                    },
                                    readOnly = true,
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowDropDown,
                                            contentDescription = "Dropdown"
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    placeholder = { Text("Bodegas") }
                                )

                                ExposedDropdownMenu(
                                    expanded = false,
                                    onDismissRequest = { /* Implementar lógica */ }
                                ) {
                                    descripcionesBodegas.forEach { opcion ->
                                        DropdownMenuItem(
                                            text = { Text(text = opcion) },
                                            onClick = {
                                                selectedBodegaDescripcion = opcion
                                                buscarBodegaPorDescripcion()
                                            }
                                        )
                                    }
                                }
                            }

                            if (isVerDatosBodega) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Codigo: $codigoBodega",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = "Existencia: $existenciaBodega",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }

                            TextButton(
                                onClick = { isVerDatosBodega = !isVerDatosBodega },
                                colors = ButtonDefaults.textButtonColors(
                                    contentColor = Color(0xFF244BC0)
                                )
                            ) {
                                Text(
                                    text = if (isVerDatosBodega) "Ocultar" else "Mostrar detalles",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Cantidad y Precio en la misma línea
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Cantidad",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                OutlinedTextField(
                                    value = cantidadProducto,
                                    onValueChange = { newValue ->
                                        cantidadProducto = validarEntradasArticulos(cantidadProducto, newValue)
                                        esCambioPorPorcentaje = true
                                        calcularTotales()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Precio",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                OutlinedTextField(
                                    value = precioProducto,
                                    onValueChange = { newValue ->
                                        precioProducto = validarEntradasArticulos(precioProducto, newValue)
                                        esCambioPorPorcentaje = true
                                        calcularTotales()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Tipo de medida
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = "Tipo de medida",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            ExposedDropdownMenuBox(
                                expanded = false,
                                onExpandedChange = { /* Implementar lógica */ }
                            ) {
                                OutlinedTextField(
                                    value = seleccionPresentacion,
                                    onValueChange = {
                                        seleccionPresentacion = it
                                        esCambioPorPorcentaje = true
                                        calcularTotales()
                                    },
                                    readOnly = true,
                                    trailingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.ArrowDropDown,
                                            contentDescription = "Dropdown"
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    placeholder = { Text("Medida") }
                                )

                                ExposedDropdownMenu(
                                    expanded = false,
                                    onDismissRequest = { /* Implementar lógica */ }
                                ) {
                                    opcionesPresentacion.forEach { opcion ->
                                        DropdownMenuItem(
                                            text = { Text(text = opcion) },
                                            onClick = {
                                                seleccionPresentacion = opcion
                                                esCambioPorPorcentaje = true
                                                calcularTotales()
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Subtotal
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Subtotal:",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = separacionDeMiles(subtotal),
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Descuento y Monto Descuento en la misma línea
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(15.dp)
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Descuento (%)",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                OutlinedTextField(
                                    value = descuentoProducto,
                                    onValueChange = { newValue ->
                                        descuentoProducto = validarEntradasArticulos(descuentoProducto, newValue)
                                        esCambioPorPorcentaje = true
                                        calcularTotales()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Monto Descuento",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                OutlinedTextField(
                                    value = montoDescuento,
                                    onValueChange = { newValue ->
                                        montoDescuento = validarEntradasArticulos(montoDescuento, newValue)
                                        esCambioPorMontoDescuento = true
                                        calcularTotales()
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Decimal,
                                        imeAction = ImeAction.Done
                                    ),
                                    singleLine = true
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Impuestos y Total
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Impuesto (IVA %)",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = impuestoProducto,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "Monto IVA: ${String.format("%.2f", montoIVA)}",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Divider(modifier = Modifier.padding(vertical = 8.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = "Total",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = "$monedaCodigo ${separacionDeMiles(totalProducto)}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    // Botones de acción
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { onDismiss() },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Cancelar",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }

                        Button(
                            onClick = {
                                onDismiss()
                                onAgregarLinea(articuloLineaId)
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF244BC0)
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = textoBotonVentanaArticulos,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// Componentes adicionales que necesitamos implementar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    content: @Composable ColumnScope.() -> Unit
) {
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            content = content
        )
    }
}

// Iconos para los dropdowns
object Icons {
    object Filled {
        val ArrowDropDown = androidx.compose.material.icons.Icons.Default.ArrowDropDown
    }
}

@Composable
@Preview
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazFacturacion("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDA0MyIsIk5vbWJyZSI6IlJPQkVSVE8gQURNSU4iLCJFbWFpbCI6InJyZXllc0Bzb3BvcnRlcmVhbC5jb20iLCJQdWVydG8iOiI4MDEiLCJFbXByZXNhIjoiWkdWdGIzSmxjM1E9IiwiU2VydmVySXAiOiJNVGt5TGpFMk9DNDNMak13IiwidGltZSI6IjIwMjUwMzEwMDIwMzA0In0.7kfmdiMMKZ30R7mSvuIT0iNod_naX8DBDPguf9KC_H4", null, nav, "","","")
}