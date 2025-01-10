package com.soportereal.invefacon.interfaces.large.sac

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasModuloSac
import com.soportereal.invefacon.interfaces.compact.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.compact.inicio_sesion.ocultarTeclado
import com.soportereal.invefacon.interfaces.compact.modulos.clientes.AgregarTextFieldMultifuncional
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.objetoEstadoPantallaCarga
import kotlinx.coroutines.delay
import java.util.Locale


@Composable
fun InterfazModuloSacLarge(
    apiToken: String,
    navControllerPantallasModuloSac: NavController?,
    systemUiController: SystemUiController?,
    navControllerPantallasModulos: NavController?
){
    systemUiController?.setStatusBarColor(Color(0xFF244BC0))
    systemUiController?.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, true)
    var datosIngresadosBarraBusqueda by rememberSaveable  { mutableStateOf("") }
    val contexto = LocalContext.current
    val lazyStateMesas= rememberLazyListState()
    val lazyStateCuentasActivas= rememberLazyListState()
    var listaCuentasActivasActuales by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    var listaMesasActualesFiltradas by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    val objectoProcesadorDatosApi= ProcesarDatosModuloSac(apiToken)
    var iniciarMenuCrearMesa by remember { mutableStateOf(false) }
    var nombreNuevaMesa by remember { mutableStateOf("") }
    var nombreSalonNuevaMesa by remember { mutableStateOf("") }
    var iniciarCreacionNuevaMesa by remember { mutableStateOf(false) }
    var isPrimeraVezCargando by remember { mutableStateOf(true) }
    var isCargandoMesas by remember { mutableStateOf(true) }
    var iniciarMenuMesaComandada by remember { mutableStateOf(false) }
    var mesaActual by remember { mutableStateOf(Mesa()) }
    var subCuentaSeleccionada by remember { mutableStateOf("Juntos") }
    val opcionesSubCuentas: SnapshotStateMap<String, String> = remember { mutableStateMapOf() }
    opcionesSubCuentas["Juntos"]="Juntos"

    LaunchedEffect(iniciarCreacionNuevaMesa) {
        if (iniciarCreacionNuevaMesa){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            delay(500)
            val result = objectoProcesadorDatosApi.crearNuevaMesa(nombreNuevaMesa, nombreSalonNuevaMesa)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            nombreNuevaMesa=""
            nombreSalonNuevaMesa=""
            iniciarCreacionNuevaMesa=false
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
        }
    }

    LaunchedEffect(Unit) {
        while (true){
            val listaMesas = mutableListOf<Mesa>()
            val listaCuentasActivas = mutableListOf<Mesa>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda)
            if (result!=null){
                val data = result.getJSONObject("data")
                val datosMesas= data.getJSONArray("mesas")
                for (i in 0 until datosMesas.length()){
                    val datoMesa= datosMesas.getJSONObject(i)
                    val mesa = Mesa(
                        nombre = datoMesa.getString("mesa"),
                        idMesa = datoMesa.getString("Consec"),
                        cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                        tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                        total = datoMesa.getString("monto"),
                        estado = datoMesa.getString("CodEstado"),
                        salon = datoMesa.getString("salon")
                    )
                    listaMesas.add(mesa)
                }

                val datosCuentasActivas = data.getJSONArray("cuentasActivas")
                for (i in 0 until datosCuentasActivas.length()) {
                    val datoMesa= datosCuentasActivas.getJSONObject(i)
                    val mesa = Mesa(
                        nombre = datoMesa.getString("mesa"),
                        idMesa = datoMesa.getString("Consec"),
                        cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                        tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                        total = datoMesa.getString("monto"),
                        estado = datoMesa.getString("CodEstado")
                    )
                    listaCuentasActivas.add(mesa)
                }

            }
            if (listaMesasActualesFiltradas!=listaMesas){
                listaMesasActualesFiltradas=listaMesas
            }
            if (listaCuentasActivasActuales!=listaCuentasActivas){
                listaCuentasActivasActuales=listaCuentasActivas
            }
            delay(500)
            if (isPrimeraVezCargando){
                objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
                isPrimeraVezCargando=false
                isCargandoMesas=false
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .width(objetoAdaptardor.ajustarAncho(964))
            .height(objetoAdaptardor.ajustarAltura(523))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior, bxContenedorMesas ,txfBarraBusqueda, bxContenerdorCuentasActivas, bxContenedorBotones)= createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(50))
                .background(Color(0xFF244BC0))
                .constrainAs(bxSuperior) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                },
            contentAlignment = Alignment.Center
        ){
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ){
                Icon(
                    Icons.Default.RestaurantMenu,
                    contentDescription ="Icono SAC",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "SAC",
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(30),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        OutlinedTextField(
            value = datosIngresadosBarraBusqueda,
            onValueChange = {
                datosIngresadosBarraBusqueda = it
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search,
                    contentDescription = "Icono Buscar",
                    tint= Color.DarkGray,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                )
            },
            placeholder = {
                Text("Buscar...",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    maxLines = 1
                )
            },
            modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(250))
                .height(objetoAdaptardor.ajustarAltura(55))
                .constrainAs(txfBarraBusqueda) {
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
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
                color = Color.DarkGray,
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
        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(55))
                .width(objetoAdaptardor.ajustarAncho(487))
                .constrainAs(bxContenedorBotones) {
                    start.linkTo(txfBarraBusqueda.end, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                }
        ){
            Row(
                horizontalArrangement = Arrangement.Start
            ) {

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        iniciarMenuCrearMesa= true
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Crear mesa",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {}
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Barra",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }

                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {}
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Ajustes",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(740))
                .height(objetoAdaptardor.ajustarAltura(410))
                .constrainAs(bxContenedorMesas) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(12))
                    top.linkTo(txfBarraBusqueda.bottom, margin = objetoAdaptardor.ajustarAltura(4))
                },
            contentAlignment = Alignment.TopStart
        ){
            if (isCargandoMesas) {
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
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(16)),
                    state = lazyStateMesas,
                ) {
                    items(listaMesasActualesFiltradas.chunked(5)) { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            rowItems.forEach { mesa ->
                                BxContendorDatosMesa(
                                    datosMesa = mesa,
                                    navControllerPantallasModuloSac = navControllerPantallasModuloSac,
                                    iniciarMenuDetalleComanda = { valor-> iniciarMenuMesaComandada=valor},
                                    mesaSeleccionada = {datosMesaActual-> mesaActual= datosMesaActual }
                                )
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(12)))
                            }

                        }
                    }
                    item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16)))}

                }
            }


        }

        Card(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(195))
                .height(objetoAdaptardor.ajustarAltura(469))
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(2),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                )
                .constrainAs(bxContenerdorCuentasActivas) {
                    start.linkTo(bxContenedorMesas.end, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
        ){
            Column {
                Box(
                    modifier =
                    Modifier
                        .background(Color(0xFF244BC0))
                        .height(objetoAdaptardor.ajustarAltura(40))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Cuentas activas",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.SemiBold,
                        fontSize = objetoAdaptardor.ajustarFont(25),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(130))
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(objetoAdaptardor.ajustarAltura(20))
                        .background(Color(0xFFFAFAFA))
                ){
                    Row {
                        Text(
                            "Mesa",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(55))
                        )

                        Text(
                            "Tiempo",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(55))
                        )

                        Text(
                            "Total",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(85))
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFAFAFA))
                ){
                    LazyColumn(
                        state = lazyStateCuentasActivas
                    ) {
                        items(listaCuentasActivasActuales){ mesa->
                            if (mesa.estado!="null"){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFFAFAFA)),
                                    contentAlignment = Alignment.Center
                                ){
                                    Row {
                                        Text(
                                            mesa.nombre,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(55))
                                        )

                                        var tiempo by remember { mutableStateOf("") }
                                        if (mesa.tiempo>=60){
                                            val minutos =  mesa.tiempo
                                            val horas = minutos / 60
                                            val minutosRestantes = minutos % 60
                                            tiempo="$horas:$minutosRestantes h"
                                        }else{
                                            tiempo= "${mesa.tiempo} m"
                                        }

                                        Text(
                                            tiempo,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(55))
                                        )

                                        val total= mesa.total
                                        var totalMiles by remember { mutableStateOf("") }
                                        totalMiles = try {
                                            String.format(Locale.US, "%,.2f", total.replace(",", "").toDouble())
                                        } catch (e: NumberFormatException) {
                                            total
                                        }

                                        Text(
                                            totalMiles,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(85))
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

    if(iniciarMenuCrearMesa) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Crear Nueva Mesa",
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
                Box{
                    Column {
                        // Input Nombre Mesa
                        OutlinedTextField(
                            value = nombreNuevaMesa,
                            onValueChange = { newText -> nombreNuevaMesa = newText },
                            label = {
                                Text(
                                    "Nombre de la Mesa",
                                    color = Color.DarkGray,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            placeholder = {
                                Text(
                                    "Ingrese el nombre de la mesa",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70)),
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = fontAksharPrincipal, fontWeight = FontWeight.Light, color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(16)),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF5B5B5B), // Color del texto cuando está enfocado
                                unfocusedTextColor = Color(0xFF5B5B5B),
                                focusedPlaceholderColor =  Color(0xFF5B5B5B),
                                unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                focusedBorderColor =  Color(0xFF5B5B5B),
                                unfocusedBorderColor = Color(0xFF5B5B5B)
                            ),
//                            keyboardOptions = KeyboardOptions.Default.copy(
//                                    imeAction = ImeAction.Go
//                                    ),
//                            keyboardActions = KeyboardActions(
//                                onGo = {
//                                }
//                            )
                        )

                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                        // Input Nombre salon
                        OutlinedTextField(
                            value = nombreSalonNuevaMesa,
                            onValueChange = { newText -> nombreSalonNuevaMesa = newText },
                            label = {
                                Text(
                                    "Nombre del Salon",
                                    color = Color.DarkGray,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            placeholder = {
                                Text(
                                    "Ingrese el nombre del salon",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70)),
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = fontAksharPrincipal, fontWeight = FontWeight.Light, color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(16)),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF5B5B5B), // Color del texto cuando está enfocado
                                unfocusedTextColor = Color(0xFF5B5B5B),
                                focusedPlaceholderColor =  Color(0xFF5B5B5B),
                                unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                focusedBorderColor =  Color(0xFF5B5B5B),
                                unfocusedBorderColor = Color(0xFF5B5B5B)
                            ),
//                            keyboardOptions = KeyboardOptions.Default.copy(
//                                    imeAction = ImeAction.Go
//                                    ),
//                            keyboardActions = KeyboardActions(
//                                onGo = {
//                                }
//                            )
                        )

                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        iniciarMenuCrearMesa=false
                        iniciarCreacionNuevaMesa=true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Crear",
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
                    onClick = {
                        iniciarMenuCrearMesa = false
                        nombreNuevaMesa=""
                        nombreSalonNuevaMesa=""
                    },
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

    if(iniciarMenuMesaComandada) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ){
            Surface(
                modifier = Modifier
                    .align(Alignment.Center)
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface
            )  {
                Box(
                    modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(24))
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "Articulos Comandados ${mesaActual.nombre}",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(27),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                        Box(
                            contentAlignment = Alignment.Center
                        ){
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        "Sub-Cuentas: ",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = objetoAdaptardor.ajustarFont(22),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        color = Color.Black
                                    )

                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                    AgregarTextFieldMultifuncional(
                                        label = "Sub-Cuentas",
                                        opciones = opcionesSubCuentas,
                                        contieneOpciones = true,
                                        nuevoValor = {nuevoValor-> subCuentaSeleccionada=nuevoValor},
                                        valor = opcionesSubCuentas[subCuentaSeleccionada]?:"Juntos",
                                        isUltimo = true,
                                        tomarAnchoMaximo = false,
                                        medidaAncho = 180
                                    )
                                }


                            }
                        }
                        Box(
                            contentAlignment = Alignment.Center
                        ){
                            Row{
                                Button(
                                    onClick = {
                                        iniciarMenuCrearMesa=false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor =Color(0xFF244BC0), // Color de fondo del botón
                                        contentColor = Color.White,
                                        disabledContainerColor = Color.Red,
                                        disabledContentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        "Mover Mesa",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = objetoAdaptardor.ajustarFont(17),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                Button(
                                    onClick = {
                                        iniciarMenuCrearMesa=false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor =Color(0xFF244BC0), // Color de fondo del botón
                                        contentColor = Color.White,
                                        disabledContainerColor = Color.Red,
                                        disabledContentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        "Pedir Cuenta",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = objetoAdaptardor.ajustarFont(17),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                Button(
                                    onClick = {
                                        iniciarMenuMesaComandada=false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFF5722), // Color de fondo del botón
                                        contentColor = Color.White,
                                        disabledContainerColor = Color(0xFFFF5722),
                                        disabledContentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        "Quitar mesa",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = objetoAdaptardor.ajustarFont(17),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                Button(
                                    onClick = {
                                        iniciarMenuCrearMesa=false
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color.Red, // Color de fondo del botón
                                        contentColor = Color.White,
                                        disabledContainerColor = Color.Red,
                                        disabledContentColor = Color.White
                                    )
                                ) {
                                    Text(
                                        "Salir",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = objetoAdaptardor.ajustarFont(17),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
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
internal fun BxContendorDatosMesa(
    datosMesa: Mesa,
    navControllerPantallasModuloSac:NavController?,
    iniciarMenuDetalleComanda: (Boolean)->Unit,
    mesaSeleccionada: (Mesa)->Unit
){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    var iniciarPantallaSacComanda by remember { mutableStateOf(false) }
    mesaSeleccionada(datosMesa)

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda && datosMesa.estado!="null"){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            iniciarMenuDetalleComanda(false)
            delay(500)
            navControllerPantallasModuloSac?.navigate(RutasPantallasModuloSac.PantallaSacComanda.ruta+"/"+datosMesa.nombre+"/"+datosMesa.salon){
                restoreState= true
                launchSingleTop=true
            }
        }
        if (iniciarPantallaSacComanda && datosMesa.estado=="null"){
            iniciarMenuDetalleComanda(true)

           iniciarPantallaSacComanda=false
        }
    }

    Card(
        modifier = Modifier
            .height(objetoAdaptardor.ajustarAltura(165))
            .width(objetoAdaptardor.ajustarAncho(55))
            .clickable {
                iniciarPantallaSacComanda = true
            }
            .shadow(
                elevation = objetoAdaptardor.ajustarAltura(7),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
            ),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(147))
                .height(objetoAdaptardor.ajustarAltura(35))
                .background(
                    when (datosMesa.estado) {
                        "1" -> {
                            Color(0xFFFFC107)
                        }

                        "2" -> {

                            Color(0xFFFF5722)
                        }

                        else -> {
                            Color(0xFF4CAF50)
                        }
                    }
                ),
                contentAlignment = Alignment.Center
            ){
                // Codigo Cliente
                Text(text = datosMesa.nombre,
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(23),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(130))
                )
            }
            Box(modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(147))
                .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                if (datosMesa.estado == "null") {
                    Text(text = "Disponible",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize = objetoAdaptardor.ajustarFont(20),
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(140))
                    )
                }else{
                    Column {
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        Text(text = "Sub Cuentas: ${datosMesa.cantidadSubcuentas}",
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(18),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(140))
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))

                        var tiempo by remember { mutableStateOf("${datosMesa.tiempo} m") }

                        if (datosMesa.tiempo>=60){
                            val minutos =  datosMesa.tiempo
                            val horas = minutos / 60
                            val minutosRestantes = minutos % 60
                            tiempo="$horas:$minutosRestantes h"
                        }else{
                            tiempo= "${datosMesa.tiempo} m"
                        }

                        Text(text = "Tiempo: $tiempo",
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(18),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(140))
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        // Codigo Cliente
                        val total= datosMesa.total
                        var totalMiles by remember { mutableStateOf("") }
                        totalMiles = try {
                            String.format(Locale.US, "%,.2f", total.replace(",", "").toDouble())
                        } catch (e: NumberFormatException) {
                            total
                        }
                        Text(text = "Total: $totalMiles",
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(18),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(140))
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                    }
                }


            }
        }

    }
}

@Composable
@Preview(widthDp = 964, heightDp = 523)
//@Preview()
private fun Preview(){
    val m= Mesa(idMesa = "1", estado = "2", nombre = "Mesa 1", total= "10000", tiempo = 45, cantidadSubcuentas = "1")
//    BxContendorDatosMesa(m)
    InterfazModuloSacLarge("", null, null, null)
}
