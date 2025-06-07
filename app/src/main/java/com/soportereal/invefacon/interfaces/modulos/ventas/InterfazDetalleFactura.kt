package com.soportereal.invefacon.interfaces.modulos.ventas

import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.deserializarFacturaHecha
import com.soportereal.invefacon.funciones_de_interfaces.formatearFechaTexto
import com.soportereal.invefacon.funciones_de_interfaces.gestorImpresora
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloLabelBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.interfaces.modulos.facturacion.Factura
import com.soportereal.invefacon.interfaces.modulos.facturacion.ProcesarDatosModuloFacturacion
import com.soportereal.invefacon.interfaces.modulos.facturacion.imprimirFactura
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun IniciarInterfazDetalleFactura(
    token: String,
    navController: NavController,
    nombreEmpresa: String,
    codUsuario: String,
    nombreUsuario: String,
    numeroFactura: String
) {
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla = configuration.fontScale
    val objetoAdaptardor = FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val objectoProcesadorDatosApi = ProcesarDatosModuloVentas(token)
    val objectoProcesadorDatosApiFacturacion = ProcesarDatosModuloFacturacion(token)
    var expandedDetFactura by remember { mutableStateOf(false) }
    var expandedDetCliente by remember { mutableStateOf(false) }
    var datosCliente by remember { mutableStateOf(ClienteVentas()) }
    var detallesDocumento by remember { mutableStateOf(DetalleDocumentoVentas()) }
    var totales by remember { mutableStateOf(TotalesVentas()) }
    var listaArticulos by remember { mutableStateOf(listOf<ArticuloVenta>()) }
    val listaEstados by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("0", "TODO"),
                ParClaveValor("1", "PROCESANDO"),
                ParClaveValor("2", "PROCESADO"),
                ParClaveValor("3", "NULA"),
                ParClaveValor("4", "CAJA"),
                ParClaveValor("5", "RECHAZADA")
            )
        )
    }
    val listaFormaPago = remember { mutableStateListOf<ParClaveValor>() }
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
    var simboloMoneda by remember { mutableStateOf("") }
    var expandedTotales by remember { mutableStateOf(false) }
    val listaIvas = remember { mutableStateListOf<ParClaveValor>() }
    var isCargando by remember { mutableStateOf(true) }
    var obtenerDatosFacturaEmitida by remember { mutableStateOf(false) }
    var isImprimiendo by remember { mutableStateOf(false) }
    var iniciarPantallaEstadoImpresion by remember { mutableStateOf(false) }
    var exitoImpresion by remember { mutableStateOf(false) }
    var imprimir by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var datosFacturaEmitida by remember { mutableStateOf(Factura()) }
    val listaImpresion = remember { mutableStateListOf( ParClaveValor("0","3")) }
    val coroutineScope = rememberCoroutineScope()
    val valorImpresionActiva by remember { mutableStateOf( obtenerParametroLocal(context, "isImpresionActiva$codUsuario$nombreEmpresa")) }

    if (valorImpresionActiva == "1") {
        gestorImpresora.PedirPermisos(context)
    }

    suspend fun obtenerFormaPago(){
        val result = objectoProcesadorDatosApi.obtenerFormaPago()
        if (result == null) return
        if (!validarExitoRestpuestaServidor(result)){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            return
        }
        listaFormaPago.clear()
        val data = result.getJSONObject("data")
        val tipos = data.getJSONArray("tipos")
        for ( i in 0 until tipos.length()){
            val datosFormaPago = tipos.getJSONObject(i)
            val codigo = datosFormaPago.getString("Cod_FormaPago")
            val descripcion = datosFormaPago.getString("Descripcion")
            val formaPagoTemp = ParClaveValor(clave = codigo, valor = descripcion)
            listaFormaPago.add(formaPagoTemp)
        }
        if (listaFormaPago.isEmpty()) listaFormaPago.add(ParClaveValor(clave = "0", valor = "0-No definido"))
    }

    LaunchedEffect(Unit) {
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        isCargando = true
        obtenerFormaPago()
        val result = objectoProcesadorDatosApi.obtenerDatosFactura(numeroFactura)
        if (result == null) return@LaunchedEffect
        if (!validarExitoRestpuestaServidor(result)){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            return@LaunchedEffect
        }
        val data = result.getJSONObject("data")
        val datosClienteArray = data.getJSONArray("datoscliente")
        var obj = datosClienteArray.getJSONObject(0)
        datosCliente =
            ClienteVentas(
                id_cliente = obj.getString("id_cliente"),
                nombre = obj.getString("nombre"),
                clientenombrecomercial = obj.getString("clientenombrecomercial"),
                tipoidentificacion = obj.getString("tipoidentificacion"),
                cedula = obj.getString("cedula"),
                plazo = obj.getString("plazo"),
                emailfactura = obj.getString("emailfactura"),
                email = obj.getString("email"),
                emailcobro = obj.getString("emailcobro"),
                direccion = obj.getString("direccion"),
                telefonos = obj.getString("telefonos")
            )



        val detallesDocumentoArray = data.getJSONArray("detallesdocumento")
        obj = detallesDocumentoArray.getJSONObject(0)
        detallesDocumento =
            DetalleDocumentoVentas(
                numero = obj.getString("numero"),
                fecha = obj.getString("fecha"),
                usuariocodigo = obj.getString("usuariocodigo"),
                agentecodigo = obj.getString("agentecodigo"),
                refereridocodigo = obj.getString("refereridocodigo"),
                referencia = obj.getString("referencia"),
                estado = obj.getString("estado"),
                monedacodigo = obj.getString("monedacodigo"),
                formapagocodigo = obj.getString("formapagocodigo"),
                mediopagodetalle = obj.getString("mediopagodetalle"),
                detalle = obj.getString("detalle")
            )
        simboloMoneda =  if(detallesDocumento.monedacodigo == "CRC") "\u20A1 " else "\u0024 "



        val detalleVentaArray = data.getJSONArray("detalleventa")

        listaArticulos = List(detalleVentaArray.length()) { i ->
            obj = detalleVentaArray.getJSONObject(i)
            ArticuloVenta(
                Cod_Articulo = obj.getString("Cod_Articulo"),
                ArticuloTipoPrecio = obj.getString("ArticuloTipoPrecio"),
                Descripcion = obj.getString("Descripcion"),
                ArticuloCantidad = obj.getDouble("ArticuloCantidad"),
                ArticuloBodegaCodigo = obj.getString("ArticuloBodegaCodigo"),
                ArticuloDescuentoPorcentage = obj.getDouble("ArticuloDescuentoPorcentage"),
                ArticuloVenta = obj.getDouble("ArticuloVenta"),
                ArticuloDescuentoMonto = obj.getDouble("ArticuloDescuentoMonto"),
                ArticuloVentaSubTotal2 = obj.getString("ArticuloVentaSubTotal2"),
                ArticuloVentaGravado = obj.getDouble("ArticuloVentaGravado"),
                ArticuloVentaExonerado = obj.getString("ArticuloVentaExonerado"),
                ArticuloVentaExento = obj.getString("ArticuloVentaExento"),
                ArticuloIvaPorcentage = obj.getDouble("ArticuloIvaPorcentage"),
                ArticuloIvaMonto = obj.getDouble("ArticuloIvaMonto"),
                ArticuloVentaTotal = obj.getDouble("ArticuloVentaTotal")
            )
        }

        listaArticulos.forEach { articulo ->
            val ivaTemp = listaIvas.find { it.clave == articulo.ArticuloIvaPorcentage.toString() }
            if ( ivaTemp != null){
                ivaTemp.valor = (ivaTemp.valor.toDouble() + articulo.ArticuloIvaMonto).toString()
            }else{
                listaIvas.add(ParClaveValor(clave = articulo.ArticuloIvaPorcentage.toString(), valor = articulo.ArticuloIvaMonto.toString()))
            }
        }


        val totalesArray = data.getJSONArray("totales")
        obj = totalesArray.getJSONObject(0)
        totales =
            TotalesVentas(
                TotalVenta = obj.getDouble("TotalVenta"),
                TotalDescuento = obj.getDouble("TotalDescuento"),
                TotalMercGravado = obj.getDouble("TotalMercGravado"),
                TotalMercExonerado = obj.getDouble("TotalMercExonerado"),
                TotalMercExento = obj.getDouble("TotalMercExento"),
                TotalServGravado = obj.getDouble("TotalServGravado"),
                TotalServExento = obj.getDouble("TotalServExento"),
                TotalServExonerado = obj.getDouble("TotalServExonerado"),
                totaliva = obj.getDouble("totaliva"),
                TotalIvaDevuelto = obj.getDouble("TotalIvaDevuelto"),
                Total = obj.getDouble("Total")
            )
        delay(1000)
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        isCargando = false

    }

    LaunchedEffect (obtenerDatosFacturaEmitida) {
        if (!obtenerDatosFacturaEmitida) return@LaunchedEffect
        isImprimiendo = true
        iniciarPantallaEstadoImpresion = true
        if (!gestorImpresora.validarConexion(context)){
            exitoImpresion = false
            isImprimiendo = false
            imprimir = false
            obtenerDatosFacturaEmitida = false
            return@LaunchedEffect
        }
        val result = objectoProcesadorDatosApiFacturacion.obtenerFactura(numeroFactura)

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

    LaunchedEffect (imprimir) {
        if (!imprimir) return@LaunchedEffect
        isImprimiendo = true
        iniciarPantallaEstadoImpresion = true
        if (!gestorImpresora.validarConexion(context)){
            exitoImpresion = false
            isImprimiendo = false
            imprimir = false
            return@LaunchedEffect
        }
        exitoImpresion = imprimirFactura(datosFacturaEmitida, context, nombreEmpresa, listaImpresion.first())
        delay(3500)
        isImprimiendo = false
        if (!exitoImpresion){
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
        imprimir = false
    }

    BackHandler {
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        navController.popBackStack()
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

    @Composable
    fun AddText(
        titulo: String,
        contenido: String,
        fontSize: TextUnit = obtenerEstiloBodyBig(),
        color: Color = Color.Black, saltoLinea: Boolean = false,
        textAlign: TextAlign = TextAlign.Start
    ) {
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = fontAksharPrincipal,
                        fontSize = fontSize,
                        color = color
                    )
                ) {
                    append(titulo)
                    append(" ")
                    if (saltoLinea){
                        append("\n")
                    }
                }

                withStyle(
                    style = SpanStyle(
                        fontSize = fontSize,
                        fontFamily = FontFamily(Font(R.font.akshar_regular)),
                        fontWeight = FontWeight.Normal,
                        color = color
                    )
                ) {
                    AnimatedVisibility(
                        visible = (!isCargando),
                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                    ){
                        append(contenido)
                    }

                }
            },
            textAlign = textAlign,
            maxLines = 10
        )

    }

    @Composable
    fun AddTextTotal(titulo: String, contenido: String){
        Row {
            TText(
                text = titulo,
                textAlign = TextAlign.Start,
                modifier = Modifier.weight(1f),
                fontSize = obtenerEstiloBodyBig()
            )
            AnimatedVisibility(
                visible = (!isCargando),
                enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
            ){
                TText(
                    text =  simboloMoneda + contenido +" ${detallesDocumento.monedacodigo}",
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f),
                    fontSize = obtenerEstiloBodyBig(),
                    fontFamily = FontFamily(Font(R.font.akshar_regular)),
                    fontWeight = null
                )
            }

        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.LightGray
        )
    }

    @Composable
    fun BxContenedorArticulo(articulo: ArticuloVenta){
        var expandedDetalles by remember { mutableStateOf(false) }
        val iconoDdmOpcionesFlechasLaterales = if (expandedDetalles) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
        val interactionSource = remember { MutableInteractionSource() }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(2)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    expandedDetalles = !expandedDetalles
                }
                .padding(
                    vertical = objetoAdaptardor.ajustarAltura(
                        4
                    )
                )
        ) {
            Box(
                modifier = Modifier
                    .height(objetoAdaptardor.ajustarAltura(45))
                    .width(objetoAdaptardor.ajustarAncho(45)),
                contentAlignment = Alignment.Center
            ) {
                SubcomposeAsyncImage(
                    model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articulo.Cod_Articulo}.png",
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
            Column {
                Row {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily(Font(R.font.akshar_regular)),
                                    fontSize = obtenerEstiloBodyMedium(),
                                    color = Color.DarkGray
                                )
                            ) {
                                append("#${articulo.Cod_Articulo} ")

                            }

                            withStyle(
                                style = SpanStyle(
                                    fontSize = obtenerEstiloBodyMedium(),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Normal,
                                    color = Color.Black
                                )
                            ) {
                                append(articulo.Descripcion)
                            }
                        },
                        textAlign = TextAlign.Start,
                        maxLines = 2,
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = iconoDdmOpcionesFlechasLaterales,
                        contentDescription = "Icono flechas",
                        tint = Color.Black,
                        modifier = Modifier.weight(0.1f)
                    )
                }
                HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                AnimatedVisibility(
                    visible = expandedDetalles,
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                ) {
                    Column {
                        Row{
                            AddText("Cantidad:", articulo.ArticuloCantidad.toString(), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.weight(1f).padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            AddText("P/U:", separacionDeMiles(articulo.ArticuloVenta), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.weight(1f).padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            AddText("SubTotal:", separacionDeMiles(articulo.ArticuloVenta*articulo.ArticuloCantidad), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center)
                        }
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                        Row {
                            AddText("Precio:", articulo.ArticuloTipoPrecio, fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.weight(1f).padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            AddText("Descuento(${articulo.ArticuloDescuentoPorcentage}%):", separacionDeMiles(articulo.ArticuloDescuentoMonto), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center)
                            Spacer(modifier = Modifier.weight(1f).padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            AddText("Gravado:", separacionDeMiles(articulo.ArticuloVentaGravado), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center)
                        }
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = expandedDetalles,
                            enter = fadeIn(animationSpec = tween(300)),
                            exit = fadeOut(animationSpec = tween(300))
                        ) {
                            AddText(
                                "Imp(${articulo.ArticuloIvaPorcentage}%):",
                                separacionDeMiles(articulo.ArticuloIvaMonto),
                                fontSize = obtenerEstiloBodySmall(),
                                textAlign = TextAlign.Center
                            )
                        }

                        androidx.compose.animation.AnimatedVisibility(
                            visible = !expandedDetalles,
                            enter = fadeIn(animationSpec = tween(300)),
                            exit = fadeOut(animationSpec = tween(300))
                        ) {
                            AddText(
                                "Cantidad:",
                                articulo.ArticuloCantidad.toString(),
                                fontSize = obtenerEstiloBodySmall(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = objetoAdaptardor.ajustarAncho(2))
                    )

                    AddText(
                        "Total:",
                        separacionDeMiles(articulo.ArticuloVentaTotal),
                        fontSize = obtenerEstiloBodyBig(),
                        color = Color(0xFF244BC0),
                        textAlign = TextAlign.Center
                    )
                }

            }
        }
        HorizontalDivider(thickness = 5.dp, color = Color.LightGray)
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
            val (bxSuperior,lzColumFacturas) = createRefs()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color(0xFF244BC0))
                    .constrainAs(bxSuperior) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        bottom.linkTo(lzColumFacturas.top)
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
                        imageVector = Icons.Filled.Description,
                        contentDescription = "Icono Ventas",
                        tint = Color.White,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                    Text(
                        "Detalles de Factura",
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
                            obtenerDatosFacturaEmitida = true
                        },
                        modifier = Modifier.padding(0.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Print,
                            contentDescription = "Flecha atras",
                            tint = Color.White,
                            modifier = Modifier
                                .size(objetoAdaptardor.ajustarAltura(25))
                                .padding(0.dp)
                        )
                    }
                }
            }

            LazyColumn(
                modifier = Modifier
                    .wrapContentSize()
                    .constrainAs(lzColumFacturas) {
                        start.linkTo(parent.start)
                        top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                    },
                verticalArrangement = Arrangement.Top
            ) {
                item {
                    Column (
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            objetoAdaptardor.ajustarAltura(8),
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = objetoAdaptardor.ajustarAltura(8))
                                .shadow(
                                    elevation = objetoAdaptardor.ajustarAltura(7),
                                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                ),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16)),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(
                                    objetoAdaptardor.ajustarAltura(
                                        4
                                    )
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        objetoAdaptardor.ajustarAltura(8),
                                        alignment = Alignment.CenterHorizontally
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Description,
                                        contentDescription = ""
                                    )
                                    TText(
                                        text = "Información Factura",
                                        modifier = Modifier
                                            .padding(end = objetoAdaptardor.ajustarAncho(4)),
                                        fontSize = obtenerEstiloHeadSmall(),
                                        color = Color.Black
                                    )

                                }
                                HorizontalDivider(
                                    thickness = objetoAdaptardor.ajustarAltura(2),
                                    color = Color.Black
                                )

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AddText("Fecha:", formatearFechaTexto(detallesDocumento.fecha))
                                    Spacer(modifier = Modifier.weight(1f))
                                    AnimatedVisibility(
                                        visible = (!isCargando),
                                        enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                        exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                    ){
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Circle,
                                                contentDescription = "",
                                                tint = colorPorEstado(detallesDocumento.estado),
                                                modifier = Modifier
                                                    .size(objetoAdaptardor.ajustarAltura(17))
                                            )
                                            TText(
                                                text = listaEstados.find { it.clave == detallesDocumento.estado }?.valor
                                                    ?: "Indefinido",
                                                modifier = Modifier
                                                    .padding(start = objetoAdaptardor.ajustarAltura(8)),
                                                fontSize = obtenerEstiloBodyBig(),
                                                color = Color.Black
                                            )
                                        }
                                    }
                                }
                                HorizontalDivider()
                                AddText("Nombre Factura:", datosCliente.clientenombrecomercial)
                                HorizontalDivider()
                                AddText("Número:", detallesDocumento.numero)
                                HorizontalDivider()
                                AddText("Referencia:", detallesDocumento.referencia)
                                AnimatedVisibility(
                                    visible = expandedDetFactura,
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.spacedBy(
                                            objetoAdaptardor.ajustarAltura(
                                                4
                                            )
                                        )
                                    ) {
                                        HorizontalDivider()
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Center
                                        ) {
                                            AddText("Moneda:", detallesDocumento.monedacodigo)
                                            Spacer(modifier = Modifier.weight(1f))
                                            AddText("Forma de Pago:", listaFormaPago.find { it.clave == detallesDocumento.formapagocodigo}?.valor?:"null")
                                        }
                                        HorizontalDivider()
                                        AddText("Usuario:", detallesDocumento.usuariocodigo)
                                        HorizontalDivider()
                                        AddText("Agente:", detallesDocumento.agentecodigo)
                                        HorizontalDivider()
                                        AddText("Referido:", detallesDocumento.refereridocodigo)
                                        HorizontalDivider()
                                        AddText("Medio de Pago:", detallesDocumento.mediopagodetalle)
                                        HorizontalDivider()
                                        AddText("Detalle:", detallesDocumento.detalle)

                                    }
                                }
                                BButton(
                                    text = if (expandedDetFactura) "Menos detalles" else "Más detalles",
                                    onClick = { expandedDetFactura = !expandedDetFactura },
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
                                .padding(horizontal = objetoAdaptardor.ajustarAltura(8))
                                .shadow(
                                    elevation = objetoAdaptardor.ajustarAltura(7),
                                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                ),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16)),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(
                                    objetoAdaptardor.ajustarAltura(
                                        4
                                    )
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        objetoAdaptardor.ajustarAltura(8),
                                        alignment = Alignment.CenterHorizontally
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PersonPin,
                                        contentDescription = ""
                                    )
                                    TText(
                                        text = "Información Cliente",
                                        modifier = Modifier
                                            .padding(end = objetoAdaptardor.ajustarAncho(4)),
                                        fontSize = obtenerEstiloHeadSmall(),
                                        color = Color.Black
                                    )

                                }
                                HorizontalDivider(
                                    thickness = objetoAdaptardor.ajustarAltura(2),
                                    color = Color.Black
                                )
                                AddText("Código:", datosCliente.id_cliente)
                                HorizontalDivider()
                                AddText("Nombre Comercial:", datosCliente.clientenombrecomercial)
                                HorizontalDivider()
                                AddText("Nombre Jurídico:", datosCliente.nombre)
                                HorizontalDivider()
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    AddText("N.Cédula:", datosCliente.cedula)
                                    Spacer(modifier = Modifier.weight(1f))
                                    AddText("Tipo:", listaTipoCedula.find { it.clave == datosCliente.tipoidentificacion}?.valor?:"null")
                                }
                                AnimatedVisibility(
                                    visible = expandedDetCliente,
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.Start,
                                        verticalArrangement = Arrangement.spacedBy(
                                            objetoAdaptardor.ajustarAltura(
                                                4
                                            )
                                        )
                                    ) {
                                        HorizontalDivider()
                                        AddText("Correo Factura:", datosCliente.emailfactura)
                                        HorizontalDivider()
                                        AddText("Correo Cliente:", datosCliente.email)
                                        HorizontalDivider()
                                        AddText("Correo Cobro:", datosCliente.emailcobro)
                                        HorizontalDivider()
                                        AddText("Telefono(s):", datosCliente.telefonos)
                                        HorizontalDivider()
                                        AddText("Dirección:", datosCliente.direccion)
                                    }
                                }
                                BButton(
                                    text = if (expandedDetCliente) "Menos detalles" else "Más detalles",
                                    onClick = { expandedDetCliente = !expandedDetCliente },
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
                                .padding(horizontal = objetoAdaptardor.ajustarAltura(8))
                                .shadow(
                                    elevation = objetoAdaptardor.ajustarAltura(7),
                                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                ),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(16)),
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(
                                    objetoAdaptardor.ajustarAltura(
                                        4
                                    )
                                )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        objetoAdaptardor.ajustarAltura(8),
                                        alignment = Alignment.CenterHorizontally
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Inventory,
                                        contentDescription = ""
                                    )
                                    TText(
                                        text = "Artículos Facturados",
                                        modifier = Modifier
                                            .padding(end = objetoAdaptardor.ajustarAncho(4)),
                                        fontSize = obtenerEstiloHeadSmall(),
                                        color = Color.Black
                                    )

                                }
                                HorizontalDivider(
                                    thickness = objetoAdaptardor.ajustarAltura(2),
                                    color = Color.Black
                                )
                                AnimatedVisibility(
                                    visible = (!isCargando),
                                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                ){
                                    Column {
                                        listaArticulos.forEach { articulo ->
                                            BxContenedorArticulo(articulo)
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
                                    horizontal = objetoAdaptardor.ajustarAltura(8),
                                    vertical = objetoAdaptardor.ajustarAltura(8)
                                )
                                .shadow(
                                    elevation = objetoAdaptardor.ajustarAltura(7),
                                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                ),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(objetoAdaptardor.ajustarAltura(16))
                            ){
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(
                                        objetoAdaptardor.ajustarAltura(8),
                                        alignment = Alignment.CenterHorizontally
                                    )
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.BarChart,
                                        contentDescription = ""
                                    )
                                    TText(
                                        text = "Totales",
                                        modifier = Modifier
                                            .padding(end = objetoAdaptardor.ajustarAncho(4)),
                                        fontSize = obtenerEstiloHeadSmall(),
                                        color = Color.Black
                                    )

                                }
                                HorizontalDivider(
                                    thickness = objetoAdaptardor.ajustarAltura(2),
                                    color = Color.Black
                                )
                                AddTextTotal("SubTotal:", separacionDeMiles(totales.TotalMercGravado + totales.TotalDescuento))
                                AddTextTotal("Total Descuento:", separacionDeMiles(totales.TotalDescuento))
                                AddTextTotal("Total Gravado:", separacionDeMiles(totales.TotalMercGravado + totales.TotalServGravado))
                                AddTextTotal("Total IVA:", separacionDeMiles(totales.totaliva))

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
                                        AddTextTotal("Total Merc Gravada:", separacionDeMiles(totales.TotalMercGravado))
                                        AddTextTotal("Total Merc Exonerada:", separacionDeMiles(totales.TotalMercExonerado))
                                        AddTextTotal("Total Merc Exenta:", separacionDeMiles(totales.TotalMercExento))
                                        AddTextTotal("Total Serv Gravado:", separacionDeMiles(totales.TotalServGravado))
                                        AddTextTotal("Total Serv Exento:", separacionDeMiles(totales.TotalServExento))
                                        AddTextTotal("Total Serv Exonerado:", separacionDeMiles(totales.TotalServExonerado))
                                        AddTextTotal("Total IVA Devuelto:", separacionDeMiles(totales.TotalIvaDevuelto))
                                        for (i in listaIvas){
                                            AddTextTotal("IVA( ${i.clave}% ): ", separacionDeMiles(i.valor.toDouble()))
                                        }
                                    }
                                }
                                AddTextTotal("Total:", separacionDeMiles(totales.Total))
                                BButton(
                                    text =  if (expandedTotales) "Mostrar menos" else "Mostrar más",
                                    onClick = {expandedTotales = !expandedTotales},
                                    contenteColor = Color(0xFF244BC0),
                                    backgroundColor = Color.White,
                                    conSombra = false,
                                    textSize = obtenerEstiloBodyBig(),
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }

                        }
                    }
                }

                item {
                    Box(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(80)))
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
                                        text = "Reimprimiendo...",
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
                                        text = if(exitoImpresion) "Reimpresión exitosa!" else "Error en Reimpresión.",
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
}


@RequiresApi(Build.VERSION_CODES.S)
@Composable
@Preview(showBackground = true)
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazDetalleFactura("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDM2MiIsIk5vbWJyZSI6IllFU0xFUiBMT1JJTyIsIkVtYWlsIjoieWVzbGVybG9yaW9AZ21haWwuY29tIiwiUHVlcnRvIjoiODAxIiwiRW1wcmVzYSI6IlpHVnRiMlpsY25KbCIsIlNlcnZlcklwIjoiTVRJM0xqQXVNQzR4IiwidGltZSI6IjIwMjUwNjAzMDUwNjQ5In0.rt1zviKSi3oDG184LFdQm2FEOznyAKxkbx9nK5GPRyc",  nav, "demoferre","00050","YESLER LORIO","00200001010000001413")
}