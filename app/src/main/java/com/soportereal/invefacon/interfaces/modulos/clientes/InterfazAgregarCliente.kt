package com.soportereal.invefacon.interfaces.modulos.clientes

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.TextFieldMultifuncional
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun IniciarInterfazAgregarCliente(
    navController: NavController?,
    token: String
){
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color(0xFF244BC0))
    systemUiController.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val lazyState= rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var errorResultadoApi by remember { mutableStateOf<Boolean?>(null) }
    val iconoSnht= if(errorResultadoApi==true) Icons.Filled.Error else Icons.Filled.Check
    val colorIconoSnht= if (errorResultadoApi==true) Color.Red else Color.Green
    var snackbarVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val objectoProcesadorDatosApi= ProcesarDatosModuloClientes(token)
    var isCosultaDatasFinalizada by remember { mutableIntStateOf(0) }
    val opcionesTipoCliente: SnapshotStateMap<String, String> = remember { mutableStateMapOf() }
    var codigoTipoClienteSeleccionado by remember { mutableStateOf("") }
    val opcionesAgentesVentasClienteActivos: SnapshotStateMap<String, String> = remember { mutableStateMapOf() }
    var codigoAgenteVentasSeleccionado by remember { mutableStateOf("") }
    var opciontieneCreditoSeleccionada by remember { mutableStateOf("0") }
    var opcionExentoSeleccionada by remember { mutableStateOf("0") }
    val opcionExoneradoSeleccionada by remember { mutableStateOf("") }
    val opcionNoForzaCreditoSeleccionada by remember { mutableStateOf("") }
    var guardarCliente by remember { mutableStateOf(false) }
    val isClienteAgregado by estadoRespuestaApi.estadoBtOk.collectAsState()
    var regresarPantallaAnterior by remember { mutableStateOf(false) }
    
    val opcionesEstadoCliente= remember {
        mutableStateMapOf(
            "1" to "Activo",
            "2" to "Suspendido",
            "3" to "Eliminado"
        )
    }

    val opcionesLogicasCliente= remember {
        mutableStateMapOf(
            "0" to "No",
            "1" to "Sí"
        )
    }

    val opcionesTipoPrecioCliente= remember {
        mutableStateMapOf<String, String>().apply {
            for (i in 1..10){
                put("$i", "$i")
            }
        }
    }

    val opcionesTipoMoneda= remember {
        mutableStateMapOf(
            "CRC" to "₡-Colones",
            "USD" to "$-Dolares",
            "EUR" to "€-Euros"
        )
    }

    var tipoMonedaSeleccionada by remember { mutableStateOf("CRC") }

    var codigoTipoIdentificacionClienteSeleccionada by remember { mutableStateOf("00") }

    val opcionesTipoIndentificacionCliente= remember {
        mutableStateMapOf(
            "00" to "No definido",
            "01" to "Física",
            "02" to "Jurídica",
            "03" to "Dimex",
            "04" to "Nite"
        )
    }

    var tipoCedula by remember { mutableStateOf("00") }
    var cedulaCliente by remember { mutableStateOf("") }
    var nombreJuridico by remember { mutableStateOf("") }
    var nombreComercial by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }

    //Informacion Contacto
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var emailGeneral by remember { mutableStateOf("") }
    var emailFactura by remember { mutableStateOf("") }
    var emailCobros by remember { mutableStateOf("") }
    var contacto by remember { mutableStateOf("") }

    //Informacion Financiera
    var creditoMonto by remember { mutableStateOf("") }
    var creditoPlazo by remember { mutableStateOf("") }
    var diaCobro by remember { mutableStateOf("") }
    var clienteTipo by remember { mutableStateOf("") }
    var monedaCliente by remember { mutableStateOf("CRC") }
    var descuentoCliente by remember { mutableStateOf("") }
    var montoContrato by remember { mutableStateOf("") }
    var detalleContrato by remember { mutableStateOf("") }

    // Informacion de ventas
    var agenteVentas by remember { mutableStateOf("") }
    var tipoPrecio by remember { mutableStateOf("1") }
    var tieneCredito by remember { mutableStateOf("0") }
    var exento by remember { mutableStateOf("0") }
    val zonaCliente by remember { mutableStateOf("") }


    LaunchedEffect(isClienteAgregado, regresarPantallaAnterior) {
        if (isClienteAgregado|| regresarPantallaAnterior){
            navController?.popBackStack()
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    LaunchedEffect(Unit) {
        val result= objectoProcesadorDatosApi.obtenerDatosAgentes()
        //validar si la respuesta de la api fue exitosa
        if (result==null){
            if (!snackbarVisible) {
                errorResultadoApi=true
                coroutineScope.launch {
                    snackbarVisible=true
                    snackbarHostState.showSnackbar(
                        message = "Error: revise su conexión a Internet"
                    )
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarVisible=false
                }
            }
        }
        else if (result.getInt("code")==921){
            println("hola")

        }
        else if (result.getString("status")=="error"){
            if (!snackbarVisible) {
                errorResultadoApi=true
                coroutineScope.launch {
                    snackbarVisible=true
                    snackbarHostState.showSnackbar(
                        message = "Error: ${result.getString("data")}"
                    )
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarVisible=false
                }
            }

        }
        else if(result.getString("status")=="ok" && result.getString("code")=="200"){
            val resultado= result.getJSONObject("resultado")
            val datos= resultado.getJSONArray("data")
            for (i in 0 until datos.length()) {
                val datosAgente= datos.getJSONObject(i)
                opcionesAgentesVentasClienteActivos[datosAgente.getString("Cod_Usuario")]= datosAgente.getString("Nombre")


            }
        }
        isCosultaDatasFinalizada++
    }

    LaunchedEffect(Unit) {
        val result= objectoProcesadorDatosApi.obtenerTiposClientes()
        //validar si la respuesta de la api fue exitosa
        if (result==null){
            if (!snackbarVisible) {
                errorResultadoApi=true
                coroutineScope.launch {
                    snackbarVisible=true
                    snackbarHostState.showSnackbar(
                        message = "Error: revise su conexión a Internet"
                    )
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarVisible=false
                }
            }
        }
        else if (result.getInt("code")==921){
            println("hola")

        }
        else if (result.getString("status")=="error"){
            if (!snackbarVisible) {
                errorResultadoApi=true
                coroutineScope.launch {
                    snackbarVisible=true
                    snackbarHostState.showSnackbar(
                        message = "Error: ${result.getString("data")}"
                    )
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarVisible=false
                }
            }
        }
        else if(result.getString("status")=="ok" && result.getString("code")=="200"){
            val resultado= result.getJSONObject("resultado")
            val datos= resultado.getJSONArray("data")
            for (i in 0 until datos.length()) {
                val datosTipoCliente= datos.getJSONObject(i)
                opcionesTipoCliente[datosTipoCliente.getString("Cod_Tipo_Cliente")]= datosTipoCliente.getString("Descripcion")
            }
        }
        isCosultaDatasFinalizada++
    }

    LaunchedEffect(isCosultaDatasFinalizada) {
        if (isCosultaDatasFinalizada==2){
            delay(500)
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            isCosultaDatasFinalizada= 0
        }
    }

    LaunchedEffect(guardarCliente) {
        if(guardarCliente){
            val datosCliente = Cliente(
                Nombre = nombreJuridico,
                Telefonos = telefono,
                Direccion = direccion,
                TipoPrecioVenta = tipoPrecio,
                Cod_Tipo_Cliente = codigoTipoClienteSeleccionado,
                Cedula = cedulaCliente,
                Email = emailGeneral,
                DiaCobro = diaCobro,
                Contacto = contacto,
                Exento = opcionExentoSeleccionada,
                AgenteVentas = codigoAgenteVentasSeleccionado,
                Cod_Zona = zonaCliente,
                DetalleContrato = detalleContrato,
                MontoContrato = montoContrato,
                Descuento = descuentoCliente,
                MontoCredito = creditoMonto,
                plazo =creditoPlazo,
                TieneCredito = opciontieneCreditoSeleccionada,
                FechaNacimiento = fechaNacimiento,
                Cod_Moneda = tipoMonedaSeleccionada,
                TipoIdentificacion = codigoTipoIdentificacionClienteSeleccionada,
                ClienteNombreComercial = nombreComercial,
                EmailFactura = emailFactura,
                EmailCobro = emailCobros,
                exonerado = opcionExoneradoSeleccionada,
                noForzaCredito = opcionNoForzaCreditoSeleccionada,
                opcionesLogicasCliente = opcionesLogicasCliente,
                opcionesEstadoCliente = opcionesEstadoCliente,
                opcionesTipoCliente = opcionesTipoCliente,
                opcionesAgentesVentas = opcionesAgentesVentasClienteActivos,
                opcionesTipoIndetificacionCliente = opcionesTipoIndentificacionCliente
            )
            if (ValidarCamposObligatoriosClientes(datosCliente)){
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result= objectoProcesadorDatosApi.agregarCliente(datosCliente = datosCliente)
                if (result != null) {
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                }
            }
        }
        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        guardarCliente=false
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (bxSuperior, bxContenedorLzColum,snhtMensajesSuperiores,flechaRegresar)= createRefs()
        //Box superior Informacion Cliente
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
        ){
            Row (horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ){
                Icon(
                    imageVector = Icons.Filled.PersonAdd,
                    contentDescription ="Icono Clientes",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Agregar Cliente",
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(28),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
        IconButton(
            onClick = {regresarPantallaAnterior=true},
            modifier = Modifier.constrainAs(flechaRegresar){
                start.linkTo(parent.start)
                top.linkTo(parent.top,margin = objetoAdaptardor.ajustarAltura(16))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
            )
        }
        //Box contenedor Lazy Column
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(722))
                .background(Color.White)
                .constrainAs(bxContenedorLzColum){
                    start.linkTo(parent.start)
                    top.linkTo(bxSuperior.bottom)
                },
            contentAlignment = Alignment.TopCenter
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(12)),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyState
            ) {
                item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4))) }
                item {
                    Column {

                        AgregarContenedorDatosClientes(
                            objetoAdaptardor = objetoAdaptardor,
                            titulo = "Datos del cliente",
                            fontAksharPrincipal = fontAksharPrincipal,
                            icono = Icons.Filled.AccountCircle
                        ) {
                            TextFieldMultifuncional(
                                label = "Nombre comercial",
                                valor = nombreComercial,
                                nuevoValor = {nombreComercial= it},
                                textPlaceholder = "Ejemplo: Ferreteria"
                            )
                            TextFieldMultifuncional(
                                label = "Nombre jurídico",
                                valor = nombreJuridico,
                                nuevoValor = {nombreJuridico= it},
                                textPlaceholder = "Ejemplo: Ferreteria S.A"
                            )
                            TextFieldMultifuncional(
                                label = "Tipo cédula",
                                valor = opcionesTipoIndentificacionCliente[tipoCedula]?: "00",
                                nuevoValor = {
                                    tipoCedula= it
                                    codigoTipoIdentificacionClienteSeleccionada=it
                                },
                                contieneOpciones = true,
                                opciones = opcionesTipoIndentificacionCliente
                            )
                            TextFieldMultifuncional(
                                label = "Cédula",
                                valor = cedulaCliente,
                                nuevoValor = {cedulaCliente= it},
                                soloPermitirValoresNumericos = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 1111111111"
                            )
                            TextFieldMultifuncional(
                                label = "Fecha nacimiento",
                                valor = fechaNacimiento,
                                nuevoValor = {fechaNacimiento= it},
                                isUltimo = true,
                                contieneOpciones = true,
                                isSeleccionarFecha = true
                            )
                        }

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(22)))

                        AgregarContenedorDatosClientes(
                            objetoAdaptardor = objetoAdaptardor,
                            fontAksharPrincipal = fontAksharPrincipal,
                            titulo = "Información de contacto",
                            icono = Icons.Filled.Phone
                        ){
                            TextFieldMultifuncional(
                                label = "Teléfonos",
                                valor = telefono,
                                nuevoValor = {telefono= it},
                                soloPermitirValoresNumericos = true,
                                permitirComas = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 88888888,8888888"
                            )
                            TextFieldMultifuncional(
                                label = "Email general",
                                valor = emailGeneral,
                                nuevoValor = {emailGeneral= it},
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: emailgeneral@gmail.com"
                            )
                            TextFieldMultifuncional(
                                label = "Email factura",
                                valor = emailFactura,
                                nuevoValor = {emailFactura= it},
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: emailfactura@gmail.com"
                            )
                            TextFieldMultifuncional(
                                label = "Email cobros",
                                valor = emailCobros,
                                nuevoValor = {emailCobros= it},
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: emailcobros@gmail.com"
                            )
                            TextFieldMultifuncional(
                                label = "Contacto",
                                valor = contacto,
                                nuevoValor = {contacto= it},
                                textPlaceholder = "Ejemplo: Carlos Vega"
                            )
                            TextFieldMultifuncional(
                                label = "Dirección",
                                valor = direccion,
                                nuevoValor = {direccion= it},
                                isUltimo = true,
                                textPlaceholder = "Ejemplo: La Uruca 200 metros este"
                            )
                        }

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(22)))

                        AgregarContenedorDatosClientes(
                            objetoAdaptardor = objetoAdaptardor,
                            fontAksharPrincipal = fontAksharPrincipal,
                            titulo = "Información financiera",
                            icono = Icons.Filled.MonetizationOn
                        ){
                            TextFieldMultifuncional(
                                label = "Monto de crédito",
                                valor = creditoMonto,
                                nuevoValor = {creditoMonto= it},
                                darFormatoMiles = true,
                                soloPermitirValoresNumericos = true,
                                permitirPuntosDedimales = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 1000000.10 (100,000.10)"
                            )

                            TextFieldMultifuncional(
                                label = "Plazo de crédito",
                                valor = creditoPlazo,
                                nuevoValor = {creditoPlazo= it},
                                soloPermitirValoresNumericos = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 15"
                            )

                            TextFieldMultifuncional(
                                label = "Día de cobro",
                                valor = diaCobro,
                                nuevoValor = {diaCobro= it},
                                soloPermitirValoresNumericos = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 15"
                            )

                            TextFieldMultifuncional(
                                label = "Tipo de cliente",
                                valor = opcionesTipoCliente[clienteTipo]?:"Seleccione un Tipo de cliente",
                                nuevoValor = {
                                    clienteTipo= it
                                    codigoTipoClienteSeleccionado=it
                                },
                                contieneOpciones = true,
                                opciones = opcionesTipoCliente
                            )

                            TextFieldMultifuncional(
                                label = "Moneda",
                                valor = opcionesTipoMoneda[monedaCliente]?:"CRC",
                                nuevoValor = {
                                    monedaCliente= it
                                    tipoMonedaSeleccionada= it
                                },
                                contieneOpciones = true,
                                opciones = opcionesTipoMoneda
                            )

                            TextFieldMultifuncional(
                                label = "Descuento",
                                valor = descuentoCliente,
                                nuevoValor = {descuentoCliente= it},
                                soloPermitirValoresNumericos = true,
                                permitirPuntosDedimales = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 13.12 o 13"
                            )

                            TextFieldMultifuncional(
                                label = "Monto de contrato",
                                valor = montoContrato,
                                nuevoValor = {montoContrato= it},
                                darFormatoMiles = true,
                                soloPermitirValoresNumericos = true,
                                permitirPuntosDedimales = true,
                                cantidadLineas = 1,
                                textPlaceholder = "Ejemplo: 1000000.10 (100,000.10)"
                            )

                            TextFieldMultifuncional(
                                label = "Detalle de contrato",
                                valor = detalleContrato,
                                nuevoValor = {detalleContrato= it},
                                isUltimo = true,
                                textPlaceholder = "Ejemplo: Ferreteria"
                            )
                        }

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(22)))

                        AgregarContenedorDatosClientes(
                            objetoAdaptardor = objetoAdaptardor,
                            fontAksharPrincipal = fontAksharPrincipal,
                            titulo = "Información de ventas",
                            icono = Icons.Default.Assessment
                        ){
                            TextFieldMultifuncional(
                                label = "Agente de ventas",
                                valor = opcionesAgentesVentasClienteActivos[agenteVentas]?:"Seleccione un Agente de ventas",
                                nuevoValor = {
                                    agenteVentas= it
                                    codigoAgenteVentasSeleccionado=it
                                },
                                contieneOpciones = true,
                                opciones = opcionesAgentesVentasClienteActivos
                            )
                            TextFieldMultifuncional(
                                label = "Tipo de precio",
                                valor = opcionesTipoPrecioCliente[tipoPrecio]?:"Seleccione un Tipo de precio",
                                nuevoValor = {tipoPrecio= it},
                                contieneOpciones = true,
                                opciones = opcionesTipoPrecioCliente
                            )
                            TextFieldMultifuncional(
                                label = "Tiene crédito",
                                valor = opcionesLogicasCliente[tieneCredito]?:"0",
                                nuevoValor = {
                                    tieneCredito= it
                                    opciontieneCreditoSeleccionada=it
                                },
                                contieneOpciones = true,
                                opciones = opcionesLogicasCliente
                            )
                            TextFieldMultifuncional(
                                label = "Exento",
                                valor = opcionesLogicasCliente[exento]?:"0",
                                nuevoValor = {
                                    exento= it
                                    opcionExentoSeleccionada=it
                                },
                                contieneOpciones = true,
                                opciones = opcionesLogicasCliente,
                                isUltimo = true
                            )
                        }

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(22)))

                        //Box Contenerdor boton guardar
                        Box(
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(360))
                                .background(Color.Transparent)
                                .height(objetoAdaptardor.ajustarAltura(50)),
                            contentAlignment = Alignment.Center
                        ){
                            Button(modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(45)),
                                onClick = {
                                    guardarCliente=true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Transparent, // Color de fondo del botón
                                    contentColor = Color(0xFF244BC0)
                                )
                            ){
                                Row {
                                    Text(
                                        text = "Guardar",
                                        maxLines = 1,
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = objetoAdaptardor.ajustarFont(25)
                                    )
                                }

                            }
                        }
                    }
                }
            }

        }
        // Snackbar inferior para mostrar mensajes emergentes para el usuario como:
        // - Problemas de Red
        // - Contraseñas Incorrectas
        // - Correos incorrectos o no encontrados
        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = { snackbarData ->
                Snackbar(
                    containerColor = Color.White, // Color de fondo del Snackbar
                    contentColor = Color.DarkGray // Color del texto del Snackbar

                ) {
                    Row(
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(8)),// Añadir algo de padding para espaciado
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = iconoSnht,
                            contentDescription = "Icono de error",
                            tint = colorIconoSnht, // Cambiar el color del ícono si deseas
                            modifier = Modifier.padding(end = objetoAdaptardor.ajustarAncho(8)).size(objetoAdaptardor.ajustarAltura(35)) // Espacio entre ícono y texto
                        )
                        Text(
                            text = snackbarData.visuals.message,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = objetoAdaptardor.ajustarFont(15),
                                fontWeight = FontWeight.Light,
                                fontFamily = fontAksharPrincipal
                            )
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(snhtMensajesSuperiores) {
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(12))
                    start.linkTo(parent.start)
                }
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun Preview(){
    IniciarInterfazAgregarCliente(null, "")
}