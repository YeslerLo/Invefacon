package com.soportereal.invefacon.interfaces.modulos.ventas

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Receipt
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.MenuConfirmacion
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.ProcGenSocket
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.TTextTitCuer
import com.soportereal.invefacon.funciones_de_interfaces.deserializarFacturaHecha
import com.soportereal.invefacon.funciones_de_interfaces.formatearFechaTexto
import com.soportereal.invefacon.funciones_de_interfaces.gestorImpresora
import com.soportereal.invefacon.funciones_de_interfaces.imprimirFactura
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeExito
import com.soportereal.invefacon.funciones_de_interfaces.mostrarToastSeguro
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloLabelBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleMedium
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import com.soportereal.invefacon.interfaces.modulos.facturacion.Factura
import com.soportereal.invefacon.interfaces.modulos.facturacion.ProcesarDatosModuloFacturacion
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
    var isImprimiendo by remember { mutableStateOf(false) }
    var iniciarPantallaEstadoImpresion by remember { mutableStateOf(false) }
    var exitoImpresion by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var datosFacturaEmitida by remember { mutableStateOf(Factura()) }
    val listaImpresion = remember { mutableStateListOf<ParClaveValor>() }
    val coroutineScope = rememberCoroutineScope()
    val valorImpresionActiva by remember { mutableStateOf( obtenerParametroLocal(context, "isImpresionActiva$codUsuario$nombreEmpresa")) }
    var socketJob by remember { mutableStateOf<Job?>(null) }
    val cortinaSocket= CoroutineScope(Dispatchers.IO)
    var iniciarMenuConfNotComple by remember { mutableStateOf(false) }
    var iniciarMenuConfAnulacion by remember { mutableStateOf(false) }
    var iniciarMenuOpciones by remember { mutableStateOf(false) }
    var iniciarMenuAplicarNotas by remember { mutableStateOf(false) }
    var listaMotivosNotas by remember {  mutableStateOf<List<ParClaveValor>>(emptyList()) }
    var isNotaCredito by remember { mutableStateOf(false) }
    var codMotivoNota by remember { mutableStateOf("") }
    var detalleNota by remember { mutableStateOf("") }
    var consecutivoNota by remember { mutableStateOf("") }
    var codImpresora by remember { mutableStateOf("Local") }
    val listaTiposDocumentos by remember {
        mutableStateOf(
            listOf(
                ParClaveValor("01", "Factura"),
                ParClaveValor("02", "Nota de Débito"),
                ParClaveValor("03", "Nota de Crédito"),
                ParClaveValor("04", "Tiquete"),
                ParClaveValor("09", "Exportación")
            )
        )
    }
    val gestorProcGenSocket = ProcGenSocket()
    var consecutivoImprimir by remember { mutableStateOf(numeroFactura) }
    var correoTemp by remember { mutableStateOf("") }
    var iniciarMenuReEnviarXml by remember { mutableStateOf(false) }

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

    suspend fun imprimir() {
        isImprimiendo = true
        iniciarPantallaEstadoImpresion = true
        val isConectado = gestorImpresora.validarConexion(context)
        delay(2000)
        if (!isConectado){
            gestorImpresora.reconectar(context)
            mostrarToastSeguro(context, "Reconectando impresora...")
            delay(12000)
            val isReconectada = gestorImpresora.validarConexion(context)
            delay(1000)
            if (!isReconectada){
                exitoImpresion = false
                isImprimiendo = false
                return
            }
        }
        exitoImpresion = imprimirFactura(
            factura = datosFacturaEmitida,
            context = context,
            nombreEmpresa = nombreEmpresa,
            tipoDoc = listaImpresion.first().tipoDocumento,
            isCopia = listaImpresion.first().isCopia,
            usuario = "#$codUsuario $nombreUsuario"
        )
        delay(3500)
        isImprimiendo = false
        if (!exitoImpresion) return
        listaImpresion.removeAt(0)
        if (listaImpresion.isNotEmpty()) return
        delay(2000)
        iniciarPantallaEstadoImpresion= false
    }

    suspend fun obtenerDatosFactura() {
        if (valorImpresionActiva == "0") return  mostrarMensajeError("La impresión esta desactivada.")
        isImprimiendo = true
        iniciarPantallaEstadoImpresion = true
        delay(1000)
        val result = objectoProcesadorDatosApiFacturacion.obtenerFactura(consecutivoImprimir)

        if (result == null) return

        if (validarExitoRestpuestaServidor(result)) {
            datosFacturaEmitida = deserializarFacturaHecha(result)
            imprimir()
        }else{
            mostrarMensajeError(result.getString("message"))
            iniciarPantallaEstadoImpresion = true
            exitoImpresion = false
            isImprimiendo = false
        }
    }

    suspend fun refrescar() {
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        isCargando = true
        obtenerFormaPago()
        val result = objectoProcesadorDatosApi.obtenerDatosFactura(numeroFactura)
        if (result == null) return
        if (!validarExitoRestpuestaServidor(result)){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            return
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
                detalle = obj.getString("detalle"),
                tipoDocumento = obj.getString("TipoDocumento")
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
        delay(500)
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        isCargando = false
    }

    LaunchedEffect(Unit) {
        refrescar()
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
                            TTextTitCuer("Cantidad:", articulo.ArticuloCantidad.toString(), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center, isCargando = isCargando)
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            TTextTitCuer("P/U:", separacionDeMiles(articulo.ArticuloVenta), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center, isCargando = isCargando)
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            TTextTitCuer("SubTotal:", separacionDeMiles(articulo.ArticuloVenta*articulo.ArticuloCantidad), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center, isCargando = isCargando)
                        }
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                        Row {
                            TTextTitCuer("Precio:", articulo.ArticuloTipoPrecio, fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center, isCargando = isCargando)
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            TTextTitCuer("Descuento(${articulo.ArticuloDescuentoPorcentage}%):", separacionDeMiles(articulo.ArticuloDescuentoMonto), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center, isCargando = isCargando)
                            Spacer(modifier = Modifier
                                .weight(1f)
                                .padding(vertical = objetoAdaptardor.ajustarAncho(2)))
                            TTextTitCuer("Gravado:", separacionDeMiles(articulo.ArticuloVentaGravado), fontSize = obtenerEstiloBodySmall(), saltoLinea = true, textAlign = TextAlign.Center, isCargando = isCargando)
                        }
                        HorizontalDivider(thickness = 1.dp, color = Color.LightGray)
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box {
                        androidx.compose.animation.AnimatedVisibility(
                            visible = expandedDetalles,
                            enter = fadeIn(animationSpec = tween(300)),
                            exit = fadeOut(animationSpec = tween(300))
                        ) {
                            TTextTitCuer(
                                "Imp(${articulo.ArticuloIvaPorcentage}%):",
                                separacionDeMiles(articulo.ArticuloIvaMonto),
                                fontSize = obtenerEstiloBodySmall(),
                                textAlign = TextAlign.Center,
                                isCargando = isCargando
                            )
                        }

                        androidx.compose.animation.AnimatedVisibility(
                            visible = !expandedDetalles,
                            enter = fadeIn(animationSpec = tween(300)),
                            exit = fadeOut(animationSpec = tween(300))
                        ) {
                            TTextTitCuer(
                                "Cantidad:",
                                articulo.ArticuloCantidad.toString(),
                                fontSize = obtenerEstiloBodySmall(),
                                textAlign = TextAlign.Center,
                                isCargando = isCargando
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = objetoAdaptardor.ajustarAncho(2))
                    )

                    TTextTitCuer(
                        "Total:",
                        separacionDeMiles(articulo.ArticuloVentaTotal),
                        fontSize = obtenerEstiloBodyBig(),
                        color = Color(0xFF244BC0),
                        textAlign = TextAlign.Center,
                        isCargando = isCargando
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
                        listaTiposDocumentos.find { it.clave == detallesDocumento.tipoDocumento }?.valor?:"DOC DESCONOCIDO",
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
                            if (detallesDocumento.estado != "2") return@IconButton mostrarMensajeError("EL DOCUMENTO NO SE ENCUENTRA PROCESADO.")
                            coroutineScope.launch {
                                consecutivoImprimir = numeroFactura
                                listaImpresion.add(ParClaveValor(tipoDocumento = detallesDocumento.tipoDocumento, isCopia = true))
                                consecutivoImprimir = detallesDocumento.numero
                                obtenerDatosFactura()
                            }
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
                        Row {
                            BButton(
                                text = "Re-Enviar XML",
                                onClick = {
                                    iniciarMenuReEnviarXml = true
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = objetoAdaptardor.ajustarAncho(8)),
                                textSize = obtenerEstiloBodyBig()
                            )
                            BButton(
                                text = "Opciones",
                                onClick = {
                                    iniciarMenuOpciones = true
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = objetoAdaptardor.ajustarAncho(8)),
                                textSize = obtenerEstiloBodyBig()
                            )
                            BButton(
                                text = "Refrescar",
                                onClick = {
                                    coroutineScope.launch {
                                        refrescar()
                                    }
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = objetoAdaptardor.ajustarAncho(8)),
                                textSize = obtenerEstiloBodyBig()
                            )
                        }

                        Card(
                            modifier = Modifier
                                .padding(horizontal = objetoAdaptardor.ajustarAncho(8))
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
                                    TTextTitCuer("Fecha:", formatearFechaTexto(detallesDocumento.fecha), isCargando = isCargando)
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
                                TTextTitCuer("Nombre Factura:", datosCliente.clientenombrecomercial, isCargando = isCargando)
                                HorizontalDivider()
                                TTextTitCuer("Número:", detallesDocumento.numero, isCargando = isCargando)
                                HorizontalDivider()
                                TTextTitCuer("Referencia:", detallesDocumento.referencia, isCargando = isCargando)
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
                                            TTextTitCuer("Moneda:", detallesDocumento.monedacodigo, isCargando = isCargando)
                                            Spacer(modifier = Modifier.weight(1f))
                                            TTextTitCuer("Forma de Pago:", listaFormaPago.find { it.clave == detallesDocumento.formapagocodigo}?.valor?:"null", isCargando = isCargando)
                                        }
                                        HorizontalDivider()
                                        TTextTitCuer("Usuario:", detallesDocumento.usuariocodigo, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Agente:", detallesDocumento.agentecodigo, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Referido:", detallesDocumento.refereridocodigo, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Medio de Pago:", detallesDocumento.mediopagodetalle, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Detalle:", detallesDocumento.detalle, isCargando = isCargando)

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
                                TTextTitCuer("Código:", datosCliente.id_cliente, isCargando = isCargando)
                                HorizontalDivider()
                                TTextTitCuer("Nombre Comercial:", datosCliente.clientenombrecomercial, isCargando = isCargando)
                                HorizontalDivider()
                                TTextTitCuer("Nombre Jurídico:", datosCliente.nombre, isCargando = isCargando)
                                HorizontalDivider()
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    TTextTitCuer("N.Cédula:", datosCliente.cedula, isCargando = isCargando)
                                    Spacer(modifier = Modifier.weight(1f))
                                    TTextTitCuer("Tipo:", listaTipoCedula.find { it.clave == datosCliente.tipoidentificacion}?.valor?:"null", isCargando = isCargando)
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
                                        TTextTitCuer("Correo Factura:", datosCliente.emailfactura, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Correo Cliente:", datosCliente.email, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Correo Cobro:", datosCliente.emailcobro, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Telefono(s):", datosCliente.telefonos, isCargando = isCargando)
                                        HorizontalDivider()
                                        TTextTitCuer("Dirección:", datosCliente.direccion, isCargando = isCargando)
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
                                        text = "Imprimiendo...",
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
                                        text = if(exitoImpresion) "Impresión exitosa!" else "Error en Impresión.",
                                        fontSize = obtenerEstiloTitleSmall(),
                                        modifier = Modifier.fillMaxWidth()
                                    )
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
                                        coroutineScope.launch {
                                            if (valorImpresionActiva == "0") return@launch mostrarMensajeError("La impresión esta desactivada.")
                                            obtenerDatosFactura()
                                        }
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

    if (iniciarMenuOpciones) {
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
                            text = "Opciones",
                            fontSize = obtenerEstiloHeadSmall(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        BButton(
                            text = "Nota de Crédito Completa",
                            onClick = {
                                if (detallesDocumento.tipoDocumento != "01") return@BButton mostrarMensajeError("SOLO PUEDE APLICAR NOTAS DE CREDITO A FACTURAS")
                                if (detallesDocumento.estado != "2") return@BButton mostrarMensajeError("SOLO PUEDE APLICAR NOTAS DE CREDITO A DOCUMENTOS PROCESADOS")
                                iniciarMenuOpciones = false
                                isNotaCredito = true
                                iniciarMenuAplicarNotas = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Nota de Débito Completa",
                            onClick = {
                                if (detallesDocumento.tipoDocumento != "01") return@BButton mostrarMensajeError("SOLO PUEDE APLICAR NOTAS DE DEBITO A FACTURAS")
                                if (detallesDocumento.estado != "2") return@BButton mostrarMensajeError("SOLO PUEDE APLICAR NOTAS DE DEBITO A DOCUMENTOS PROCESADOS")
                                iniciarMenuOpciones = false
                                isNotaCredito = false
                                iniciarMenuAplicarNotas = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Anular",
                            onClick = {
                                if (detallesDocumento.estado != "5") return@BButton mostrarMensajeError("SOLO SE PUEDEN ANULAR DOCUMENTOS RECHAZADOS")
                                if (detallesDocumento.tipoDocumento == "01" && detallesDocumento.estado == "2") return@BButton mostrarMensajeError("NO PUEDE ANULAR UNA FACTURA, DEBE APLICAR UNA NOTA")
                                iniciarMenuOpciones = false
                                iniciarMenuConfAnulacion = true
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.fillMaxWidth(),
                            objetoAdaptardor = objetoAdaptardor
                        )
                        BButton(
                            text = "Salir",
                            onClick = {
                                iniciarMenuOpciones = false
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

    if (iniciarMenuAplicarNotas) {
        var isMenuVisible by remember { mutableStateOf(false) }
        var impreNota by remember { mutableStateOf("Local") }
        var listaImpresoras by remember {  mutableStateOf<List<ParClaveValor>>(emptyList()) }

        LaunchedEffect(Unit) {
            val listaMotivosTemp = mutableStateListOf<ParClaveValor>()
            detalleNota = ""
            socketJob = cortinaSocket.launch {
                objectoProcesadorDatosApi.obtenerMotivosNotas(
                    context = context,
                    datosRetornados = {
                        val lista = it
                        lista.forEach { motivo ->
                            val codigo = motivo[0]
                            val descripcion = motivo[1]
                            listaMotivosTemp.add(ParClaveValor(clave = codigo, valor = "$codigo-$descripcion" ))
                        }
                        listaMotivosNotas = listaMotivosTemp
                        if (listaMotivosTemp.isNotEmpty()) codMotivoNota = listaMotivosTemp.first().clave
                    },
                    onExitoOrFin = {
                        if (!it) socketJob?.cancel()
                    }
                )
            }
            val listaImpresorasTemp = mutableListOf<ParClaveValor>()
            listaImpresorasTemp.add(ParClaveValor("Local", "Local"))
            gestorProcGenSocket.obtenerImpresorasRemotas(
                context = context,
                datosRetornados = {
                    val listaTemp = it
                    listaTemp.forEach { impresora ->
                        listaImpresorasTemp.add(ParClaveValor(clave = impresora[0], valor = impresora[0]+"-"+impresora[1]))
                    }
                    listaImpresoras = listaImpresorasTemp
                },
                onExitoOrFin = {
                    return@obtenerImpresorasRemotas
                }
            )
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
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
                            .wrapContentSize(),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(
                                objetoAdaptardor.ajustarAltura(8),
                                alignment = Alignment.CenterHorizontally
                            ),
                            modifier = Modifier. fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Receipt,
                                contentDescription = ""
                            )
                            TText(
                                text = "Nota de ${if(isNotaCredito) "Crédito" else "Débito"} Completa",
                                fontSize = obtenerEstiloHeadSmall(),
                                textAlign = TextAlign.Center
                            )

                        }
                        HorizontalDivider(
                            thickness = objetoAdaptardor.ajustarAltura(2),
                            color = Color.Black
                        )
                        TText(
                            text = "Detalles de Nota",
                            fontSize = obtenerEstiloTitleBig(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        TTextTitCuer("Documento:", detallesDocumento.numero)
                        HorizontalDivider()
                        TTextTitCuer("Nombre Factura:", datosCliente.clientenombrecomercial)
                        HorizontalDivider()
                        TTextTitCuer("Cantidad Artículos:", listaArticulos.size.toString())
                        HorizontalDivider()
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            TText(
                                text = "Motivo:  ",
                                fontSize = obtenerEstiloBodyBig()
                            )
                            BBasicTextField(
                                value = listaMotivosNotas.find { it.clave == codMotivoNota }?.valor?: "null",
                                onValueChange = {
                                    codMotivoNota = it
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                opciones = listaMotivosNotas,
                                modifier = Modifier
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
                                utilizarMedidas = false,
                                fontSize = obtenerEstiloBodyBig(),
                                placeholder = "Motivo...",
                                mostrarLeadingIcon = false,
                                soloPermitirValoresNumericos = true
                            )
                        }
                        HorizontalDivider()
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ){
                            TText(
                                text = "Detalle:  ",
                                fontSize = obtenerEstiloBodyBig()
                            )
                            BBasicTextField(
                                value = detalleNota,
                                onValueChange = {
                                    detalleNota = it
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier
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
                                utilizarMedidas = false,
                                fontSize = obtenerEstiloBodyBig(),
                                placeholder = "Detalle...",
                                mostrarLeadingIcon = false,
                                cantidadLineas = 10
                            )
                        }
                        HorizontalDivider()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ){
                            TText(
                                text = "Impresora: ",
                                fontSize = obtenerEstiloTitleSmall(),
                                textAlign = TextAlign.Center
                            )
                            BBasicTextField(
                                value = impreNota,
                                onValueChange = { clave ->
                                    impreNota = listaImpresoras.find { it.clave == clave }?.valor?:"Local"
                                    codImpresora = clave
                                },
                                opciones = listaImpresoras,
                                objetoAdaptardor = objetoAdaptardor,
                                utilizarMedidas = false,
                                modifier = Modifier
                                    .wrapContentWidth()
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
                                placeholder = "Impresora..."
                            )

                        }
                        HorizontalDivider()
                        TTextTitCuer("Total:", separacionDeMiles(totales.Total), fontSize = obtenerEstiloTitleSmall())
                        HorizontalDivider()
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ){
                            BButton(
                                text = "Cancelar",
                                onClick = {
                                    iniciarMenuAplicarNotas = false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f)
                            )
                            BButton(
                                text = "Aplicar",
                                onClick = {
                                    if (detalleNota.isEmpty()) return@BButton mostrarMensajeError("Debe agregar un detalle a la Nota.")
                                    iniciarMenuAplicarNotas = false
                                    iniciarMenuConfNotComple = true
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

    if (iniciarMenuReEnviarXml) {

        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            correoTemp = ""
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
                            "Re-Enviar XML de Factura",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloDisplayMedium(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        TTextTitCuer(
                            titulo = "Correo Cliente:",
                            contenido = datosCliente.email,
                            fontSize = obtenerEstiloTitleSmall()

                        )
                        TText(
                            text = "Ingrese un correo si desea enviar una copia: ",
                            fontSize = obtenerEstiloTitleSmall(),
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            maxLines = 4
                        )
                        BBasicTextField(
                            value = correoTemp,
                            onValueChange =  { nuevoValor ->
                                correoTemp = nuevoValor
                            },
                            utilizarMedidas = false,
                            placeholder = "Correo electrónico",
                            icono = Icons.Filled.Email,
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentHeight(),
                            fontSize = obtenerEstiloBodyBig()
                        )
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                        ) {
                            BButton(
                                text = "Re-Enviar",
                                onClick = {
                                    iniciarMenuReEnviarXml = false
                                    socketJob = cortinaSocket.launch{
                                        objectoProcesadorDatosApi.reEnviarXml(
                                            context = context,
                                            numero = detallesDocumento.numero,
                                            emailCopia = correoTemp,
                                            onExitoOrFin = {
                                                if (!it){
                                                    socketJob?.cancel()
                                                    return@reEnviarXml
                                                }
                                                mostrarMensajeExito("XML RE-ENVIADO EXITOSAMENTE!")
                                                socketJob?.cancel()
                                                return@reEnviarXml
                                            }
                                        )
                                    }
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.weight(1f),
                                textSize = obtenerEstiloBodyBig()
                            )

                            BButton(
                                text = "Cancelar",
                                onClick = {
                                    iniciarMenuReEnviarXml = false
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

    MenuConfirmacion(
        txBtAceptar = "Aplicar",
        txBtDenegar = "Cancelar",
        onAceptar = {
            iniciarMenuConfNotComple = false
            consecutivoNota = ""
            socketJob = cortinaSocket.launch {
                objectoProcesadorDatosApi.aplicarNotaCredDebiCompleta(
                    context = context,
                    numeroDocumento = detallesDocumento.numero,
                    codMotivo = codMotivoNota,
                    detalle = detalleNota,
                    onExitoOrFin = {
                        if (!it) {
                            socketJob?.cancel()
                            return@aplicarNotaCredDebiCompleta
                        }
                        mostrarMensajeExito("LA NOTA DE ${if(isNotaCredito) "CREDITO" else "DEBITO"} FUE PROCESADA CON EXITO!")
                        if (codImpresora != "Local"){
                            socketJob?.cancel()
                            return@aplicarNotaCredDebiCompleta
                        }
                        coroutineScope.launch {
                            if (consecutivoNota.isEmpty()) {
                                mostrarMensajeError("EL CONSECUTIVO DE LA NOTA ESTA VACIO, ERROR EN IMPRESION.")
                            }else{
                                listaImpresion.add(ParClaveValor(tipoDocumento = if(isNotaCredito) "03" else "02", isCopia = false))
                                consecutivoImprimir = consecutivoNota
                                obtenerDatosFactura()
                            }
                        }
                        socketJob?.cancel()
                        return@aplicarNotaCredDebiCompleta
                    },
                    isNotaCredito = isNotaCredito,
                    codUsuario = codUsuario,
                    impresora = if (codImpresora == "Local") "" else codImpresora,
                    consecutivo = {
                        consecutivoNota = it
                    }
                )
            }
        },
        onDenegar = {
            iniciarMenuConfNotComple = false
        },
        mostrarMenu = iniciarMenuConfNotComple,
        titulo = "Nota de ${if(isNotaCredito) "Crédito" else "Débito"} Completa",
        subTitulo = "¿Desea aplicar una Nota de ${if(isNotaCredito) "Crédito" else "Débito"} Completa a esta Factura?"
    )

    MenuConfirmacion(
        txBtAceptar = "Anular",
        txBtDenegar = "Cancelar",
        onAceptar = {
            socketJob = cortinaSocket.launch{
                objectoProcesadorDatosApi.anularDocumento(
                    context = context,
                    numero = detallesDocumento.numero,
                    onExitoOrFin = {
                        if (!it){
                            socketJob?.cancel()
                            return@anularDocumento
                        }
                        refrescar()
                        mostrarMensajeExito("FACTURA ANULADA EXITOSAMENTE!")
                        socketJob?.cancel()
                        return@anularDocumento
                    }
                )
            }
            iniciarMenuConfAnulacion = false
        },
        onDenegar = {
            iniciarMenuConfAnulacion = false
        },
        mostrarMenu = iniciarMenuConfAnulacion,
        titulo = "Anular Factura",
        subTitulo = "¿Desea anular esta Factura?"
    )

}


@Composable
@Preview(showBackground = true)
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazDetalleFactura("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDM2MiIsIk5vbWJyZSI6IllFU0xFUiBMT1JJTyIsIkVtYWlsIjoieWVzbGVybG9yaW9AZ21haWwuY29tIiwiUHVlcnRvIjoiODAxIiwiRW1wcmVzYSI6IlpHVnRiMlpsY25KbCIsIlNlcnZlcklwIjoiTVRJM0xqQXVNQzR4IiwidGltZSI6IjIwMjUwNjAzMDUwNjQ5In0.rt1zviKSi3oDG184LFdQm2FEOznyAKxkbx9nK5GPRyc",  nav, "demoferre","00050","YESLER LORIO","00200001010000001413")
}