package com.soportereal.invefacon.interfaces.compact.modulos.clientes

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.NavHostPantallasModuloClientes
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasModuloClientes
import com.soportereal.invefacon.interfaces.compact.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.compact.inicio_sesion.ocultarTeclado
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.EstadoPantallaCarga
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.objetoEstadoPantallaCarga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.Normalizer


@SuppressLint("SourceLockedOrientationActivity")
@Composable
internal fun IniciarInterfazModuloClientes(
    apiToken: String,
    systemUiController: SystemUiController,
    navControllerPantallasModulos: NavController?
) {
//    val activity = LocalContext.current as Activity
//    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    val navControllerPantallasModuloCliente= rememberNavController()

    Scaffold (
        content = {innerPadding ->
            NavHostPantallasModuloClientes(
                apiToken =  apiToken,
                navControlerPantallasModuloClientes = navControllerPantallasModuloCliente,
                innerPadding= innerPadding,
                systemUiController= systemUiController,
                navControllerPantallasModulos= navControllerPantallasModulos
            )
        }
    )
}


@Composable
fun InterfazModuloClientes(
    apiToken: String,
    navControllerPantallasModuloClientes: NavController,
    systemUiController: SystemUiController,
    navControllerPantallasModulos: NavController?
){
    systemUiController.setStatusBarColor(Color(0xFF244BC0))
    systemUiController.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var datosIngresadosBarraBusqueda by rememberSaveable  { mutableStateOf("") }
    val objectoProcesadorDatosApi= ProcesarDatosModuloClientes(apiToken)
    var apiConsultaActual by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApi= CoroutineScope(Dispatchers.IO)
    var listaClientesActuales by remember { mutableStateOf<List<Cliente>>(emptyList()) }
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var errorResultadoApi by remember { mutableStateOf<Boolean?>(false) }
    val iconoSnht= if(errorResultadoApi==true) Icons.Filled.Error else Icons.Filled.Check
    val colorIconoSnht= if (errorResultadoApi==true) Color.Red else Color.Green
    var isCargandoClientes by remember { mutableStateOf(true) }
    val lazyState= rememberLazyListState()
    var paginaActualLazyColumn by remember { mutableIntStateOf(1) }
    var ultimaPaginaLazyColumn by remember { mutableIntStateOf(1) }
    var iniciarCargaDatos by remember { mutableStateOf(false) }
    var isDialogOpen by remember { mutableStateOf(false) }
    // Estados para las selecciones de los menús
    var opcionFiltroPor by remember { mutableStateOf("Busqueda Mixta") }
    var opcionFiltroEstado by remember {mutableStateOf("Activos y Suspendidos")}
    var opcionFiltroZona by remember { mutableStateOf("Null")}
    var opcionFiltroAgenteVenta by remember { mutableStateOf("Null") }
    var opcionFiltroTipo by remember { mutableStateOf("Null") }
    val contexto = LocalContext.current
    var iniciarPantallaAgregarClente by remember { mutableStateOf(false) }
    var regresarPantallaAnterior by remember { mutableStateOf(false) }

    LaunchedEffect(snackbarHostState) {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    LaunchedEffect(iniciarPantallaAgregarClente) {
        if (iniciarPantallaAgregarClente){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            navControllerPantallasModuloClientes.navigate(RutasPantallasModuloClientes.PantallaAgregarCliente.ruta){
                restoreState= true
                launchSingleTop=true
            }
        }
    }

    LaunchedEffect(regresarPantallaAnterior) {
        if(regresarPantallaAnterior){
            navControllerPantallasModulos?.popBackStack()
        }
    }


    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(objetoAdaptardor.ajustarAltura(622))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior, txfBarraBusqueda, bxOpcionAgregarCliente, bxContenedorDatosClientes, snhtMensajesSuperiores, flechaRegresar)= createRefs()

        fun buscarClientes(){
            isCargandoClientes=true
            apiConsultaActual?.cancel()
            apiConsultaActual= cortinaConsultaApi.launch{
                delay(500)
                val result= objectoProcesadorDatosApi.obtenerDatosClientes(
                    clientesPorPagina = "5",
                    paginaCliente = paginaActualLazyColumn.toString(),
                    clienteDatoBusqueda = datosIngresadosBarraBusqueda.trim(),
                    clienteEstado = when(opcionFiltroEstado){
                        "Activo"->"1"
                        "Suspendido"->"2"
                        "Eliminado"->"3"
                        "Activos y Suspendidos"->"4"
                        else->""
                    },
                    busquedaPor = quitarTildesYMinusculas(opcionFiltroPor)

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
                            isCargandoClientes=false
                        }
                    }
                }
                else if(result?.getString("status")=="ok" && result.getString("code")=="200"){
                    val resultado= result.getJSONObject("resultado")
                    ultimaPaginaLazyColumn= resultado.getInt("paginas")
                    val datosClientes= resultado.getJSONArray("data")
                    val listaClientes = mutableListOf<Cliente>()
                    for (i in 0 until datosClientes.length()) {
                        val datosCliente = datosClientes.getJSONObject(i)
                        val cliente = Cliente(
                            codigo = datosCliente.getString("codigo"),
                            nombreComercial = datosCliente.getString("nombrecomercial"),
                            nombreJuridico = datosCliente.getString("nombrejuridico"),
                            Telefonos = datosCliente.getString("telefonos"),
                            correo = datosCliente.getString("emailgeneral"),
                            estado = datosCliente.getString("estado")
                        )
                        listaClientes.add(cliente)
                    }

                    listaClientesActuales=listaClientesActuales+listaClientes
                    isCargandoClientes=false
                }
                objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(70))
                .background(Color(0xFF244BC0))
                .constrainAs(bxSuperior) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                },
            contentAlignment = Alignment.BottomCenter
        ){
            Row (horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ){
                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription ="Icono Clientes",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Clientes",
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(32),
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

        LaunchedEffect(iniciarCargaDatos) {
            delay(500)
            iniciarCargaDatos=true
        }

        if(iniciarCargaDatos){
            // Detecta si el usuario llega al final de la lista para cargar más
            LaunchedEffect(lazyState) {
                snapshotFlow { lazyState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                    .collect { lastVisibleIndex ->
                        if (lastVisibleIndex == listaClientesActuales.lastIndex && !isCargandoClientes && paginaActualLazyColumn<ultimaPaginaLazyColumn) {
                            paginaActualLazyColumn += 1 // Incrementa la página para cargar más elementos
                            buscarClientes()
                        }
                    }
            }

            LaunchedEffect(datosIngresadosBarraBusqueda) {
                listaClientesActuales= emptyList()
                paginaActualLazyColumn=1
                lazyState.scrollToItem(0)
                buscarClientes()
            }
        }

        TextField(
            value = datosIngresadosBarraBusqueda,
            onValueChange = {
                datosIngresadosBarraBusqueda = it
            },
            leadingIcon = {
                IconButton(
                    onClick = {
                        buscarClientes()
                    }
                ) {
                    Icon(imageVector = Icons.Filled.Search,
                        contentDescription = "Icono Buscar",
                        tint= Color.DarkGray,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(35))
                    )
                }

            },
            trailingIcon = {
                IconButton(onClick = { isDialogOpen=true}) {
                    Icon(imageVector = Icons.Filled.Tune,
                        contentDescription = "Icono Filtrar",
                        tint= Color.DarkGray,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(35))
                    )
                }
            },
            placeholder = {
                Text("Buscar...",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = objetoAdaptardor.ajustarFont(22),
                    maxLines = 1
                )
            },
            modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(360))
                .height(objetoAdaptardor.ajustarAltura(60))
                .constrainAs(txfBarraBusqueda){
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(12))
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(18)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color(0xFF244BC0),
                focusedLabelColor = Color.DarkGray,
                unfocusedLabelColor = Color.DarkGray
            ),
            textStyle = TextStyle(
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(20),
                textAlign = TextAlign.Justify
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { ocultarTeclado(contexto) }
            )
        )

        Box(modifier = Modifier
            .height(objetoAdaptardor.ajustarAltura(44))
            .fillMaxWidth()
            .constrainAs(bxOpcionAgregarCliente){
                top.linkTo(txfBarraBusqueda.bottom, margin = 0.dp)
                start.linkTo(parent.start, margin = 0.dp)
            },
            contentAlignment = Alignment.Center
        ){
            Button(modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(45)),
                onClick = {
                    iniciarPantallaAgregarClente=true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Color de fondo del botón
                    contentColor = Color(0xFF244BC0)
                )
            ){
                Row {
                    Icon(
                        imageVector = Icons.Filled.PersonAdd,
                        contentDescription = "Icono agregar Cliente"
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))

                    Text(
                        text = "Agregar cliente",
                        maxLines = 1,
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = objetoAdaptardor.ajustarFont(19)
                    )
                }

            }
        }

        Box(
            modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(538))
                .background(Color.White)
                .fillMaxWidth()
                .constrainAs(bxContenedorDatosClientes){
                    top.linkTo(bxOpcionAgregarCliente.bottom, margin = objetoAdaptardor.ajustarAltura(2))
                    start.linkTo(parent.start)
                },
            contentAlignment = Alignment.Center
        ){
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(12)),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyState
            ) {
                items(listaClientesActuales) { cliente ->
                    AgregarBxConenedorInformacionCliente(
                        datosCliente = cliente,
                        navControllerPantallasModuloClientes = navControllerPantallasModuloClientes,
                        estadoPantallaCarga= objetoEstadoPantallaCarga
                    )
                }

                // Muestra el indicador de carga al final de la lista mientras se cargan nuevos elementos
                if (isCargandoClientes) {
                    item {
                        CircularProgressIndicator(
                            color = Color(0xFF244BC0),
                            modifier = Modifier
                                .size(objetoAdaptardor.ajustarAltura(30))
                                .padding(2.dp)
                        )
                    }
                }
                item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4))) }

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
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start)
                }
        )
        // Cuadro emergente (Diálogo)
        if (isDialogOpen) {
            AlertDialog(
                modifier = Modifier.background(Color.White),
                containerColor = Color.White,
                onDismissRequest = {  },
                title = {
                    Text(
                        "Filtros de búsqueda",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = objetoAdaptardor.ajustarFont(27),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                },
                text = {
                    Column {
                        // Primer DropdownMenu
                        FiltroDropdownMenu(
                            label = "Buscar por: ",
                            opciones = listOf("Busqueda Mixta","Nombre", "Cédula", "Código", "Dirección"),
                            selectedOption = opcionFiltroPor,
                            opcionSeleccionada = { opcionFiltroPor = it }
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        // Segundo DropdownMenu
                        FiltroDropdownMenu(
                            label = "Estado:",
                            opciones = listOf("Activos y Suspendidos","Todos", "Activo", "Suspendido", "Eliminado"),
                            selectedOption = opcionFiltroEstado,
                            opcionSeleccionada = { opcionFiltroEstado = it }
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        // Segundo DropdownMenu
                        FiltroDropdownMenu(
                            label = "Zona:",
                            opciones = listOf(),
                            selectedOption = opcionFiltroZona,
                            opcionSeleccionada = { opcionFiltroZona = it }
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        // Segundo DropdownMenu
                        FiltroDropdownMenu(
                            label = "Tipo:",
                            opciones = listOf(),
                            selectedOption = opcionFiltroTipo,
                            opcionSeleccionada = { opcionFiltroTipo = it }
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        // Segundo DropdownMenu
                        FiltroDropdownMenu(
                            label = "Agente de Venta:",
                            opciones = listOf(),
                            selectedOption = opcionFiltroAgenteVenta,
                            opcionSeleccionada = { opcionFiltroAgenteVenta = it }
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                isDialogOpen = false
                                listaClientesActuales= emptyList()
                                paginaActualLazyColumn=1
                                lazyState.scrollToItem(0)
                                buscarClientes()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF244BC0), // Color de fondo del botón
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF244BC0),
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            "Aplicar Filtros",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(15),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                },
                dismissButton = {
                    Button(
                        onClick = { isDialogOpen = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red, // Color de fondo del botón
                            contentColor = Color.White,
                            disabledContainerColor = Color.Red,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            "Cancelar",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(15),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }
            )
        }
    }
}


@Composable
fun AgregarBxConenedorInformacionCliente(
    datosCliente: Cliente,
    navControllerPantallasModuloClientes: NavController,
    estadoPantallaCarga: EstadoPantallaCarga
){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    var iniciarPantallaInformacionCliente by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaInformacionCliente) {
        if (iniciarPantallaInformacionCliente){
            estadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            navControllerPantallasModuloClientes.navigate(RutasPantallasModuloClientes.PantallaInfoCliente.ruta+"/${datosCliente.codigo}"){
                restoreState= true
                launchSingleTop=true
            }
        }
    }

    Card(
        modifier = Modifier
            .height(objetoAdaptardor.ajustarAltura(115))
            .clickable {
                CoroutineScope(Dispatchers.IO).launch {
                    iniciarPantallaInformacionCliente= true
                } }
            .width(objetoAdaptardor.ajustarAncho(360))
            .shadow(
                elevation = objetoAdaptardor.ajustarAltura(7),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
            ),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(objetoAdaptardor.ajustarAncho(20))
                .background(
                    when (datosCliente.estado) {
                        "1" -> {
                            Color(0xFF00C05A)
                        }
                        "2" -> {
                            Color(0xFFF3ED00)
                        }
                        else -> {
                            Color(0xFFD50000)
                        }
                    }
                )
            )

            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
                , contentAlignment = Alignment.CenterStart
            ){
                Row {
                    Column {
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(2)))

                        // Codigo Cliente
                        Text(text = "#"+datosCliente.codigo,
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize = objetoAdaptardor.ajustarFont(15),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(19))
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )

                        // Nombre Comercial
                        Text(datosCliente.nombreComercial
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  objetoAdaptardor.ajustarFont(17),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(26))
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )

                        // Nombre Juridico
                        Text(datosCliente.nombreJuridico
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  objetoAdaptardor.ajustarFont(15),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(23))
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )

                        // Telefono
                        Text(datosCliente.Telefonos
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  objetoAdaptardor.ajustarFont(15),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(19))
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )

                        // Correo
                        Text(datosCliente.correo
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  objetoAdaptardor.ajustarFont(15),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(23))
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )
                    }

                    // Opciones
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){

                        IconButton(
                            onClick = {
                                CoroutineScope(Dispatchers.IO).launch {
                                    iniciarPantallaInformacionCliente= true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreHoriz,
                                contentDescription = "Icono mostrar opciones clientes",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FiltroDropdownMenu(
    label: String,
    opciones: List<String>,
    selectedOption: String,
    opcionSeleccionada: (String) -> Unit
) {
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var expanded by remember { mutableStateOf(false) } // Controla si el menú está abierto

    Column(modifier = Modifier.background(Color.White)) {
        Text(
            label,
            fontFamily = fontAksharPrincipal,
            fontWeight = FontWeight.Medium,
            fontSize = objetoAdaptardor.ajustarFont(18),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedButton(onClick = { expanded = !expanded }) {
            Text(
                selectedOption,
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Medium,
                fontSize = objetoAdaptardor.ajustarFont(17),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                color = Color.Black
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            opciones.forEach { opcion ->
                DropdownMenuItem(
                    onClick = {
                        opcionSeleccionada(opcion)
                        expanded = false // Cierra el menú después de seleccionar
                    },
                    text = {
                        Text(
                            opcion,
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(17),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                    },
                    modifier = Modifier.background(Color.White)
                )
            }
        }
    }
}



fun quitarTildesYMinusculas(texto: String): String {
    val textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
    val textoSinTildes = textoNormalizado.replace(Regex("\\p{M}"), "")
    val textoSinEspacios= textoSinTildes.replace(" ", "")
    println(textoSinEspacios)
    return if (texto=="Busqueda Mixta") textoSinEspacios else textoSinEspacios.lowercase()
}



@Preview(showBackground = true)
@Composable
private fun Preview(){
    val systemUiController = rememberSystemUiController()
    IniciarInterfazModuloClientes("", systemUiController, null)
}