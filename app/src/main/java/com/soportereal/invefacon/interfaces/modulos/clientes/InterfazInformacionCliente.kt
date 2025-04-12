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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.PermIdentity
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import org.json.JSONObject
import java.util.regex.Pattern


@Composable
fun IniciarInterfazInformacionCliente(
    codigoCliente: String,
    token: String,
    navControllerPantallasModuloClientes: NavController?
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
    var datosCliente by remember { mutableStateOf(JSONObject("{}")) }
    var modoEdicionActivado by remember { mutableStateOf(false) }
    var errorResultadoApi by remember { mutableStateOf<Boolean?>(null) }
    var mostrarDatosCliente by remember { mutableStateOf(false) }
    val objectoProcesadorDatosApi= ProcesarDatosModuloClientes(token)
    var snackbarVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val iconoSnht= if(errorResultadoApi==true) Icons.Filled.Error else Icons.Filled.Check
    val colorIconoSnht= if (errorResultadoApi==true) Color.Red else Color.Green
    var isCosultaDatasFinalizada by remember { mutableIntStateOf(0) }
    val opcionesTipoCliente: SnapshotStateMap<String, String> = remember { mutableStateMapOf() }
    var codigoTipoClienteSeleccionado by remember { mutableStateOf("Seleccione un Tipo de cliente") }
    val opcionesAgentesVentasClienteActivos: SnapshotStateMap<String, String> = remember { mutableStateMapOf() }
    var codigoAgenteVentasSeleccionado by remember { mutableStateOf("Seleccione un Agente de ventas") }
    var opciontieneCreditoSeleccionada by remember { mutableStateOf("") }
    var opcionExentoSeleccionada by remember { mutableStateOf("") }
    var guardarEdicionCliente by remember { mutableStateOf(false) }
    val isClienteActualizado by estadoRespuestaApi.estadoBtOk.collectAsState()
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

    val opcionesTipoIndentificacionCliente= remember {
        mutableStateMapOf(
            "00" to "No definido",
            "01" to "Física",
            "02" to "Jurídica",
            "03" to "Dimex",
            "04" to "Nite"
        )
    }

    var codigoTipoIdentificacionClienteSeleccionada by remember { mutableStateOf("") }

    LaunchedEffect(isClienteActualizado, regresarPantallaAnterior) {
        if (isClienteActualizado||regresarPantallaAnterior){
            navControllerPantallasModuloClientes?.popBackStack()
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    LaunchedEffect(Unit) {
        val result= objectoProcesadorDatosApi.obtenerDatosClientes(
            clienteDatoBusqueda = codigoCliente
        )
        if (result?.getString("status")=="error"){
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
        else if(result?.getString("status")=="ok" && result.getString("code")=="200"){
            val resultado= result.getJSONObject("resultado")
            val datos= resultado.getJSONArray("data")
            for (i in 0 until datos.length()) {
                datosCliente= JSONObject(datos.getJSONObject(i).toString())
            }
            delay(500)
            mostrarDatosCliente= true
        }
        isCosultaDatasFinalizada++
    }

    LaunchedEffect(Unit) {
        val result= objectoProcesadorDatosApi.obtenerDatosAgentes()
        if (result?.getString("status")=="error"){
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
        else if(result?.getString("status")=="ok" && result.getString("code")=="200"){
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
        if (result?.getString("status")=="error"){
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
        else if(result?.getString("status")=="ok" && result.getString("code")=="200"){
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
        if (isCosultaDatasFinalizada==3){
            codigoTipoClienteSeleccionado= datosCliente.getString("clientetipo")
            codigoAgenteVentasSeleccionado= datosCliente.getString("agenteventas")
            codigoTipoIdentificacionClienteSeleccionada= datosCliente.getString("cedulatipo")
            opciontieneCreditoSeleccionada= datosCliente.getString("tienecredito")
            opcionExentoSeleccionada= datosCliente.getString("exento")
            delay(500)
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            isCosultaDatasFinalizada=0
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (bxSuperior, bxContenedorLzColum, snhtMensajesSuperiores,flechaRegresar, bxContenedorCirculoCarga)= createRefs()

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
                    imageVector = Icons.Default.PermIdentity,
                    contentDescription ="Icono Clientes",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                )
                Text(
                    "Información del Cliente",
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

        if (!mostrarDatosCliente) {
            Box(
                modifier =
                Modifier
                    .fillMaxSize()
                    .constrainAs(bxContenedorCirculoCarga){
                        start.linkTo(parent.start)
                        top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(16))
                    },
                contentAlignment = Alignment.TopCenter
            ){
                CircularProgressIndicator(
                    color = Color(0xFF244BC0),
                    modifier = Modifier
                        .size(objetoAdaptardor.ajustarAltura(30))
                        .padding(2.dp)
                )
            }

        }

        if (mostrarDatosCliente){

            var tipoCedula by remember { mutableStateOf(datosCliente.getString("cedulatipo")) }
            var cedulaCliente by remember { mutableStateOf(datosCliente.getString("cedula")) }
            var nombreJuridico by remember { mutableStateOf(datosCliente.getString("nombrejuridico")) }
            var nombreComercial by remember { mutableStateOf(datosCliente.getString("nombrecomercial")) }
            var fechaNacimiento by remember { mutableStateOf(datosCliente.getString("FechaNacimiento")) }

            //Informacion Contacto
            var direccion by remember { mutableStateOf(datosCliente.getString("direccion")) }
            var telefono by remember { mutableStateOf(datosCliente.getString("telefonos")) }
            var emailGeneral by remember { mutableStateOf(datosCliente.getString("emailgeneral")) }
            var emailFactura by remember { mutableStateOf(datosCliente.getString("emailfactura")) }
            var emailCobros by remember { mutableStateOf(datosCliente.getString("emailcobros")) }
            var contacto by remember { mutableStateOf(datosCliente.getString("contacto")) }

            //Informacion Financiera
            var creditoMonto by remember { mutableStateOf(datosCliente.getString("creditomonto")) }
            var creditoPlazo by remember { mutableStateOf(datosCliente.getString("creditoplazo")) }
            var diaCobro by remember { mutableStateOf(datosCliente.getString("diacobro")) }
            var clienteTipo by remember { mutableStateOf(datosCliente.getString("clientetipo")) }
            var monedaCliente by remember { mutableStateOf(datosCliente.getString("moneda")) }
            var descuentoCliente by remember { mutableStateOf(datosCliente.getString("descuento")) }
            var montoContrato by remember { mutableStateOf(datosCliente.getString("MontoContrato")) }
            var detalleContrato by remember { mutableStateOf(datosCliente.getString("DetalleContrato")) }

            // Informacion de ventas
            var ultimaVenta by remember { mutableStateOf(datosCliente.getString("ultimaventa")) }
            var agenteVentas by remember { mutableStateOf(datosCliente.getString("agenteventas")) }
            var tipoPrecio by remember { mutableStateOf(datosCliente.getString("tipoprecio")) }
            var estadoCliente by remember { mutableStateOf(datosCliente.getString("estado")) }
            var tieneCredito by remember { mutableStateOf(datosCliente.getString("tienecredito")) }
            var noForzarCredito by remember { mutableStateOf(datosCliente.getString("noforzarcredito")) }
            var exento by remember { mutableStateOf(datosCliente.getString("exento")) }
            var exonerado by remember { mutableStateOf(datosCliente.getString("exonerado")) }
            var zonaCliente by remember { mutableStateOf( datosCliente.getString("zona")) }

            // Datos iniciales Cliente
            val clienteActual = Cliente(
                Id_cliente = codigoCliente,
                Nombre = datosCliente.getString("nombrejuridico"),
                Telefonos = datosCliente.getString("telefonos"),
                Direccion = datosCliente.getString("direccion"),
                TipoPrecioVenta = datosCliente.getString("tipoprecio"),
                Cod_Tipo_Cliente = datosCliente.getString("clientetipo"),
                Email = datosCliente.getString("emailgeneral"),
                DiaCobro = datosCliente.getString("diacobro"),
                Contacto = datosCliente.getString("contacto"),
                Exento = datosCliente.getString("exento"),
                AgenteVentas = datosCliente.getString("agenteventas"),
                Cod_Zona = datosCliente.getString("zona"),
                DetalleContrato = datosCliente.getString("DetalleContrato"),
                MontoContrato = datosCliente.getString("MontoContrato"),
                Descuento = datosCliente.getString("descuento"),
                MontoCredito = datosCliente.getString("creditomonto"),
                plazo = datosCliente.getString("creditoplazo"),
                TieneCredito = datosCliente.getString("tienecredito"),
                FechaNacimiento = datosCliente.getString("FechaNacimiento"),
                Cod_Moneda = datosCliente.getString("moneda"),
                TipoIdentificacion = datosCliente.getString("cedulatipo"),
                ClienteNombreComercial = datosCliente.getString("nombrecomercial"),
                EmailFactura = datosCliente.getString("emailfactura"),
                EmailCobro = datosCliente.getString("emailcobros"),
                estado = datosCliente.getString("estado"),
                exonerado = datosCliente.getString("exonerado"),
                cedulaCliente = datosCliente.getString("cedula"),
                ultimaVenta = datosCliente.getString("ultimaventa"),
                noForzaCredito = datosCliente.getString("noforzarcredito"),
                opcionesLogicasCliente = opcionesLogicasCliente,
                opcionesEstadoCliente = opcionesEstadoCliente,
                opcionesTipoCliente = opcionesTipoCliente,
                opcionesAgentesVentas = opcionesAgentesVentasClienteActivos,
                opcionesTipoIndetificacionCliente = opcionesTipoIndentificacionCliente

            )

            LaunchedEffect(guardarEdicionCliente) {
                if(guardarEdicionCliente){
                    val clienteModificado = Cliente(
                        Id_cliente = codigoCliente,
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
                        AgenteVentas = agenteVentas,
                        Cod_Zona = zonaCliente,
                        DetalleContrato = detalleContrato,
                        MontoContrato = montoContrato,
                        Descuento = descuentoCliente,
                        MontoCredito = creditoMonto,
                        plazo =creditoPlazo,
                        TieneCredito = opciontieneCreditoSeleccionada,
                        FechaNacimiento = fechaNacimiento,
                        Cod_Moneda = monedaCliente,
                        TipoIdentificacion = codigoTipoIdentificacionClienteSeleccionada,
                        ClienteNombreComercial = nombreComercial,
                        EmailFactura = emailFactura,
                        EmailCobro = emailCobros,
                    )
                    if (ValidarCamposObligatoriosClientes(clienteModificado)){
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                        val result= objectoProcesadorDatosApi.actualizarDatosClientes(
                            clienteActual = clienteActual,
                            clienteModificado = clienteModificado
                        )
                        //validar si la respuesta de la api fue exitosa
                        if (result!=null){
                            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                        }
                    }
                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    guardarEdicionCliente=false
                }
            }

            //Box contenedor Lazy Column
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(objetoAdaptardor.ajustarAltura(742))
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
                                icono = Icons.Filled.AccountCircle,
                                isPrimero = true,
                                valor = modoEdicionActivado,
                                nuevoValor = { nuevoEstado-> modoEdicionActivado= nuevoEstado},
                            ) {
                                TextFieldMultifuncional(
                                    label = "Nombre comercial",
                                    valor = nombreComercial,
                                    nuevoValor = {nombreComercial= it},
                                    modoEdicionActivado = modoEdicionActivado
                                )
                                TextFieldMultifuncional(
                                    label = "Nombre jurídico",
                                    valor = nombreJuridico,
                                    nuevoValor = {nombreJuridico= it},
                                    modoEdicionActivado = modoEdicionActivado
                                )
                                TextFieldMultifuncional(
                                    label = "Tipo cédula",
                                    valor = opcionesTipoIndentificacionCliente[tipoCedula]?: "Sin definir",
                                    nuevoValor = {
                                        tipoCedula= it
                                        codigoTipoIdentificacionClienteSeleccionada=it
                                    },
                                    modoEdicionActivado = false
                                )
                                TextFieldMultifuncional(
                                    label = "Cédula",
                                    valor = cedulaCliente,
                                    nuevoValor = {cedulaCliente= it},
                                    modoEdicionActivado = false
                                )
                                TextFieldMultifuncional(
                                    label = "Fecha nacimiento",
                                    valor = fechaNacimiento,
                                    nuevoValor = {fechaNacimiento= it},
                                    isUltimo = true,
                                    contieneOpciones = true,
                                    isSeleccionarFecha = true,
                                    modoEdicionActivado = modoEdicionActivado
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
                                    modoEdicionActivado = modoEdicionActivado,
                                    soloPermitirValoresNumericos = true,
                                    permitirComas = true,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Email general",
                                    valor = emailGeneral,
                                    nuevoValor = {emailGeneral= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Email factura",
                                    valor = emailFactura,
                                    nuevoValor = {emailFactura= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Email cobros",
                                    valor = emailCobros,
                                    nuevoValor = {emailCobros= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Contacto",
                                    valor = contacto,
                                    nuevoValor = {contacto= it},
                                    modoEdicionActivado = modoEdicionActivado
                                )
                                TextFieldMultifuncional(
                                    label = "Dirección",
                                    valor = direccion,
                                    nuevoValor = {direccion= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    isUltimo = true
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
                                    modoEdicionActivado = modoEdicionActivado,
                                    darFormatoMiles = true,
                                    soloPermitirValoresNumericos = true,
                                    permitirPuntosDedimales = true,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Plazo de crédito",
                                    valor = creditoPlazo,
                                    nuevoValor = {telefono= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    soloPermitirValoresNumericos = true,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Día de cobro",
                                    valor = diaCobro,
                                    nuevoValor = {diaCobro= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    soloPermitirValoresNumericos = true,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Tipo de cliente",
                                    valor = opcionesTipoCliente[clienteTipo]?:"Sin definir",
                                    nuevoValor = {
                                        clienteTipo= it
                                        codigoTipoClienteSeleccionado=it
                                    },
                                    modoEdicionActivado = modoEdicionActivado,
                                    contieneOpciones = true,
                                    opciones = opcionesTipoCliente
                                )
                                TextFieldMultifuncional(
                                    label = "Moneda",
                                    valor = monedaCliente,
                                    nuevoValor = {monedaCliente= it},
                                    modoEdicionActivado = modoEdicionActivado
                                )
                                TextFieldMultifuncional(
                                    label = "Descuento",
                                    valor = descuentoCliente,
                                    nuevoValor = {descuentoCliente= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    soloPermitirValoresNumericos = true,
                                    permitirPuntosDedimales = true,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Monto de contrato",
                                    valor = montoContrato,
                                    nuevoValor = {montoContrato= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    darFormatoMiles = true,
                                    soloPermitirValoresNumericos = true,
                                    permitirPuntosDedimales = true,
                                    cantidadLineas = 1
                                )
                                TextFieldMultifuncional(
                                    label = "Detalle de contrato",
                                    valor = detalleContrato,
                                    nuevoValor = {detalleContrato= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    isUltimo = true
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
                                    label = "Ultima venta",
                                    valor = ultimaVenta,
                                    nuevoValor = {ultimaVenta= it},
                                    modoEdicionActivado = false
                                )
                                TextFieldMultifuncional(
                                    label = "Agente de ventas",
                                    valor = opcionesAgentesVentasClienteActivos[agenteVentas]?:"Sin definir",
                                    nuevoValor = {
                                        agenteVentas= it
                                        codigoAgenteVentasSeleccionado=it
                                    },
                                    modoEdicionActivado = modoEdicionActivado,
                                    contieneOpciones = true,
                                    opciones = opcionesAgentesVentasClienteActivos
                                )
                                TextFieldMultifuncional(
                                    label = "Tipo de precio",
                                    valor = opcionesTipoPrecioCliente[tipoPrecio]?:"Sin definir",
                                    nuevoValor = {tipoPrecio= it},
                                    modoEdicionActivado = modoEdicionActivado,
                                    contieneOpciones = true,
                                    opciones = opcionesTipoPrecioCliente
                                )
                                TextFieldMultifuncional(
                                    label = "Estado",
                                    valor = opcionesEstadoCliente[estadoCliente]?: "Sin definir",
                                    nuevoValor = {estadoCliente= it},
                                    modoEdicionActivado = false
                                )
                                TextFieldMultifuncional(
                                    label = "Tiene crédito",
                                    valor = opcionesLogicasCliente[tieneCredito]?:"Sin definir",
                                    nuevoValor = {
                                        tieneCredito= it
                                        opciontieneCreditoSeleccionada=it
                                    },
                                    modoEdicionActivado = modoEdicionActivado,
                                    contieneOpciones = true,
                                    opciones = opcionesLogicasCliente
                                )
                                TextFieldMultifuncional(
                                    label = "Forzar crédito",
                                    valor = opcionesLogicasCliente[noForzarCredito]?:"Sin definir",
                                    nuevoValor = {noForzarCredito= it},
                                    modoEdicionActivado = false
                                )
                                TextFieldMultifuncional(
                                    label = "Exento",
                                    valor = opcionesLogicasCliente[exento]?:"Sin definir",
                                    nuevoValor = {
                                        exento= it
                                        opcionExentoSeleccionada=it
                                    },
                                    modoEdicionActivado = modoEdicionActivado,
                                    contieneOpciones = true,
                                    opciones = opcionesLogicasCliente
                                )
                                TextFieldMultifuncional(
                                    label = "Exonerado",
                                    valor = opcionesLogicasCliente[exonerado]?:"Sin definir",
                                    nuevoValor = {exonerado= it},
                                    modoEdicionActivado = false,
                                    isUltimo = true,
                                )
                            }

                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(22)))

                            if (modoEdicionActivado){
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
                                            guardarEdicionCliente=true
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
                            else{
                                // Información General
                                tipoCedula = datosCliente.getString("cedulatipo")
                                cedulaCliente = datosCliente.getString("cedula")
                                nombreJuridico = datosCliente.getString("nombrejuridico")
                                nombreComercial = datosCliente.getString("nombrecomercial")
                                fechaNacimiento = datosCliente.getString("FechaNacimiento")

                                // Información de Contacto
                                direccion = datosCliente.getString("direccion")
                                telefono = datosCliente.getString("telefonos")
                                emailGeneral = datosCliente.getString("emailgeneral")
                                emailFactura = datosCliente.getString("emailfactura")
                                emailCobros = datosCliente.getString("emailcobros")
                                contacto = datosCliente.getString("contacto")

                                // Información Financiera
                                creditoMonto = datosCliente.getString("creditomonto")
                                creditoPlazo = datosCliente.getString("creditoplazo")
                                diaCobro = datosCliente.getString("diacobro")
                                clienteTipo = datosCliente.getString("clientetipo")
                                monedaCliente = datosCliente.getString("moneda")
                                descuentoCliente = datosCliente.getString("descuento")
                                montoContrato = datosCliente.getString("MontoContrato")
                                detalleContrato = datosCliente.getString("DetalleContrato")

                                // Información de Ventas
                                ultimaVenta = datosCliente.getString("ultimaventa")
                                agenteVentas = datosCliente.getString("agenteventas")
                                tipoPrecio = datosCliente.getString("tipoprecio")
                                estadoCliente = datosCliente.getString("estado")
                                tieneCredito = datosCliente.getString("tienecredito")
                                noForzarCredito = datosCliente.getString("noforzarcredito")
                                exento = datosCliente.getString("exento")
                                exonerado = datosCliente.getString("exonerado")
                                zonaCliente = datosCliente.getString("zona")

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
internal fun AgregarContenedorDatosClientes(
    objetoAdaptardor: FuncionesParaAdaptarContenido,
    titulo: String,
    fontAksharPrincipal: FontFamily,
    icono: ImageVector,
    isPrimero: Boolean? = false,
    valor: Boolean? = false,
    nuevoValor: ((Boolean) -> Unit)? = null,
    contenido: @Composable ()-> Unit
){
    Card(
        modifier = Modifier
            .width(objetoAdaptardor.ajustarAncho(360))
            .shadow(
                elevation = objetoAdaptardor.ajustarAltura(7),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(10))
            ),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(10)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.TopStart
        ){
            Column {
                TextField(
                    enabled = false,
                    value = "",
                    onValueChange = {},
                    textStyle = TextStyle(
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize =  objetoAdaptardor.ajustarFont(18),
                        color = Color.Black,
                        textAlign = TextAlign.Start
                    ),
                    label = {
                        Box(
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(30))
                                .fillMaxWidth()
                                .background(Color.White),
                            contentAlignment = Alignment.BottomCenter
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Icon(
                                    imageVector = icono,
                                    contentDescription = "Icono",
                                    tint = Color.DarkGray,
                                    modifier = Modifier
                                        .size(objetoAdaptardor.ajustarAltura(30))
                                )
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))
                                Text(
                                    titulo,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight =    FontWeight.Light,
                                    fontSize =  objetoAdaptardor.ajustarFont(22),
                                    color = Color.DarkGray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.width(if (isPrimero==true) objetoAdaptardor.ajustarAncho(170)else objetoAdaptardor.ajustarAncho(360))
                                )
                                if(isPrimero==true) {
                                    Spacer(
                                        modifier = Modifier.width(
                                            objetoAdaptardor.ajustarAncho(
                                                12
                                            )
                                        )
                                    )
                                    Text(
                                        text = "Editar",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Light,
                                        fontSize = objetoAdaptardor.ajustarFont(20),
                                        color = Color.DarkGray,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .height(objetoAdaptardor.ajustarAltura(25))
                                            .width(objetoAdaptardor.ajustarAncho(47))
                                    )
                                    Spacer(
                                        modifier = Modifier.width(
                                            objetoAdaptardor.ajustarAncho(
                                                5
                                            )
                                        )
                                    )

                                    Switch(
                                        checked = valor ?: false,
                                        onCheckedChange = { nuevoEstado ->
                                            nuevoValor?.invoke(nuevoEstado)
                                        },
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = Color(0xFF244BC0),
                                            uncheckedThumbColor = Color.LightGray,
                                            checkedTrackColor = Color.White,
                                            uncheckedTrackColor = Color.White,
                                            uncheckedBorderColor = Color.LightGray,
                                            checkedBorderColor = Color.LightGray
                                        )
                                    )
                                }

                            }
                        }

                    },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        unfocusedIndicatorColor = Color.Black,
                        focusedIndicatorColor = Color.Black,
                        cursorColor = Color(0xFF244BC0),
                        disabledContainerColor = Color.White,
                        disabledIndicatorColor = Color.Black
                    ),
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(360))
                )
                contenido()
            }
        }
    }
}

internal fun ValidarCamposObligatoriosClientes(datosCliente: Cliente): Boolean{
    var nombreCampo= ""
    val estructuraParaValidacionCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{2,}$"
    val objetoValidadorCorreo = Pattern.compile(estructuraParaValidacionCorreo)

    if (datosCliente.ClienteNombreComercial.isEmpty()){
        nombreCampo="Ingrese un Nombre Comercial para continuar"
    }
    if(datosCliente.Nombre.isEmpty()){
        nombreCampo= "Ingrese un Nombre jurídico para continuar"
    }
    if(datosCliente.Cedula.isEmpty()){
        nombreCampo= "Ingrese un numero de Cédula"
    }
    if(datosCliente.TipoIdentificacion=="01"){
        if (datosCliente.Cedula.length!=9){
            nombreCampo="El número de cédula ingresado no cumple con los requisitos de una cédula física válida."
        }
    }
    if(datosCliente.TipoIdentificacion=="02"){
        if (datosCliente.Cedula.length!=10){
            nombreCampo="El número de cédula ingresado no cumple con los requisitos de una cédula jurídica válida."
        }
    }
    if(datosCliente.TipoIdentificacion=="03"){
        if (datosCliente.Cedula.length != 11 && datosCliente.Cedula.length != 12){
            nombreCampo="El número de cédula ingresado no cumple con los requisitos de una cédula Dimex válida."
        }
    }
    if(datosCliente.TipoIdentificacion=="04"){
        if (datosCliente.Cedula.length!=10){
            nombreCampo="El número de cédula ingresado no cumple con los requisitos de una cédula Nite válida."
        }
    }
    if(datosCliente.Telefonos.length<8){
        nombreCampo= "Ingrese un teléfono válido para continuar"
    }
    if(datosCliente.Email.isEmpty()){
        nombreCampo= "Ingrese un Email general para continuar"
    }
    if(datosCliente.Email.isNotEmpty()){
        if(!objetoValidadorCorreo.matcher(datosCliente.Email).matches()){
            nombreCampo="El formato del Email general es invalido"
        }
    }
    if(datosCliente.EmailCobro.isNotEmpty()){
        if(!objetoValidadorCorreo.matcher(datosCliente.EmailCobro).matches()){
            nombreCampo="El formato del Email Cobro es invalido"
        }
    }
    if(datosCliente.EmailFactura.isNotEmpty()){
        if(!objetoValidadorCorreo.matcher(datosCliente.EmailFactura).matches()){
            nombreCampo="El formato del Email Factura es invalido"
        }
    }
    if(datosCliente.Cod_Tipo_Cliente=="Seleccione un Tipo de cliente"){
        nombreCampo= "Seleccione un Tipo Cliente para continuar"
    }
    if(datosCliente.AgenteVentas=="Seleccione un Agente de ventas"){
        nombreCampo= "Seleccione un Agente de ventas para continuar"
    }
    if(datosCliente.TipoPrecioVenta=="Seleccione un Tipo de precio"){
        nombreCampo= "Seleccione un Tipo de precio para continuar"
    }

    if (nombreCampo.isNotEmpty()){
        val jsonRespuesta= JSONObject("""{"code":400,"status":"error","data":"$nombreCampo"}""")
        estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonRespuesta)
        return false
    }
    return true
}


@Preview(showBackground = true)
@Composable
private fun Preview(){
    IniciarInterfazInformacionCliente(
        codigoCliente = "",
        token = "",
        navControllerPantallasModuloClientes = null
    )
}