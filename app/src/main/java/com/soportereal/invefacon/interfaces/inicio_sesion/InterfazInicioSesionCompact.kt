package com.soportereal.invefacon.interfaces.inicio_sesion

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.view.inputmethod.InputMethodManager
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas
import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametro
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.obtenerEstiloLabel
import com.soportereal.invefacon.interfaces.pantallas_principales.objetoEstadoPantallaCarga
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.util.regex.Pattern


@SuppressLint("SourceLockedOrientationActivity", "ContextCastToActivity")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IniciarInterfazInicioSesionCompact(
    navController: NavController?
){

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color(0xFF244BC0))
    systemUiController.setNavigationBarColor(Color.Black)
    var clientePassword by remember { mutableStateOf("") }
    var clienteEmpresaSeleccionada by remember { mutableStateOf("") }
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val arrayEmpresasDisponibles = remember { mutableStateListOf<String>() }
    var expandirDdmOpcionesEmpresas by remember { mutableStateOf(false) }
    var textFieldOpcionesEmpresasMedida by remember { mutableStateOf(Size.Zero) }
    val iconoDdmOpcionesEmpresasFlechasLaterales = if (expandirDdmOpcionesEmpresas) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown
    val estructuraParaValidacionCorreo = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z0-9]{2,}$"
    val objetoValidadorCorreo = Pattern.compile(estructuraParaValidacionCorreo)
    var mostrarPasswordOtxf by remember { mutableStateOf(false) }
    var existenCorreos by remember { mutableStateOf<Boolean?>(true) }
    var isInicioSesionAprobado by remember { mutableStateOf(false) }
    var apiToken by remember { mutableStateOf("") }
    var nombreEmpresa by remember { mutableStateOf("") }
    var nombreUsuario by remember { mutableStateOf("") }
    var codUsuario by remember { mutableStateOf("") }
    val objetoProcesamientoDatosApi= ProcesamientoDatosInterfazInicioSesion()
    val snackbarHostState = remember { SnackbarHostState() }
    var snackbarVisible by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var errorResultadoApi by remember { mutableStateOf<Boolean?>(false) }
    val iconoSnht= if(errorResultadoApi==true) Icons.Filled.Error else Icons.Filled.Check
    val colorIconoSnht= if (errorResultadoApi==true) Color.Red else Color.Green
    val isBtIniciarSesionActivo = remember { mutableStateOf(true) }
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    val contexto = LocalContext.current
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var apiConsultaActual by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApi= CoroutineScope(Dispatchers.IO)
    var isCorreoIngresadoValido by remember { mutableStateOf(false) }
    var guardarDatosSesion by remember { mutableStateOf(false) }
    val scrollState= rememberScrollState(0)
    var usuarioCorreoEmpresa by remember { mutableStateOf(obtenerParametro(contexto, "ultimoCorreo")) }


    LaunchedEffect(isInicioSesionAprobado) {
        if (isInicioSesionAprobado){
            navController?.navigate(RutasPatallas.Inicio.ruta+"/$apiToken/$nombreEmpresa/$nombreUsuario/$codUsuario")
            actualizarParametro(contexto, "ultimoCorreo",usuarioCorreoEmpresa)
            if (guardarDatosSesion){
                actualizarParametro(contexto, "token",apiToken)
                actualizarParametro(contexto, "nombreUsuario",nombreUsuario)
                actualizarParametro(contexto, "nombreEmpresa",nombreEmpresa)
                actualizarParametro(contexto, "codUsuario",codUsuario)
            }
        }
    }


    LaunchedEffect(snackbarHostState) {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    ConstraintLayout (
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        //Referencias para ajustar componetes en la Interfaz
        val (bxCirculoSuperiorAzulInterfaz, crdContenedoraComponetesEntradaDatos, bxContenedorLogo,
            txBienvenido, snhtMensajesSuperiores,bxInferior)= createRefs()

        // Box Circular Superior azul con degradado
        Canvas(
            modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(650))
                .height(objetoAdaptardor.ajustarAltura(650))
                .constrainAs(bxCirculoSuperiorAzulInterfaz) {
                    top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(-300))
                    start.linkTo(parent.start, margin = 0.dp)
                }
        ) {
            val colorAzulDegradado = Brush.linearGradient(
                colors = listOf(Color(0xFF244BC0), Color(0xFF244BC0)), // Colores del degradado
                start = Offset(1000f, 500f), // Punto inicial del degradado
                end = Offset(500f, 500f) // Punto final del degradado
            )
            drawCircle(
                radius = size.minDimension,
                brush = colorAzulDegradado
            )
        }

        //Box Contenedor del Logo
        Box(
            modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(130))
                .width(objetoAdaptardor.ajustarAncho(115))
                .constrainAs(bxContenedorLogo) {
                    top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(37))
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(134))
                }
        ) {
            // Logo Invefacon
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                painter = painterResource(id = R.drawable.logo_invenfacon),
                contentDescription = "Descripción de la imagen",
                contentScale = ContentScale.Fit
            )
        }

        //Texto Binevenido
        Text(
            modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(60))
                .wrapContentSize()
                .fillMaxWidth()
                .constrainAs(txBienvenido) {
                    top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(177))
                },
            fontFamily = fontAksharPrincipal,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontSize = objetoAdaptardor.ajustarFont(50),
            text = "¡Bienvenido!",
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        //Card contenedora de componetes de entrada de datos
        Card(
            modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(475))
                .width(objetoAdaptardor.ajustarAncho(339))
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(7),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                )
                .constrainAs(crdContenedoraComponetesEntradaDatos) {
                    top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(247))
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(22))
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {

            //Box contenedor de componentes de entrada de datos, su funcion es centrar los componetes dentro del Card
            Box(
                modifier =Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter //Alinea los compoentes que esten detro del Box
            ){
                // Columna para alinear los componetes que esten dentro de ella de forma vertical
                // Contiene los componetentes para obtener el correo y las contraseñas y mostrar las empresas
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    // Spacer para para mantener un margen en la parte parte superior
                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                    // Texto Inicio Sesion
                    Text(
                        text = "Inicio Sesión",
                        fontSize = objetoAdaptardor.ajustarFont(45),
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF5B5B5B),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.height(objetoAdaptardor.ajustarAltura(57))
                    )

                    LaunchedEffect(usuarioCorreoEmpresa) {
                        usuarioCorreoEmpresa= usuarioCorreoEmpresa.replace(" ", "")
                        arrayEmpresasDisponibles.clear()
                        clienteEmpresaSeleccionada=""
                        existenCorreos= true
                        apiConsultaActual?.cancel()
                        isCorreoIngresadoValido= objetoValidadorCorreo.matcher(usuarioCorreoEmpresa).matches()
                        //Si el correo ingresado es valido ingresa la if
                        if(isCorreoIngresadoValido){
                            //Se pone null para iniciar el circulo de carga
                            existenCorreos=null
                            apiConsultaActual= cortinaConsultaApi.launch{
                                val resultApi= objetoProcesamientoDatosApi.obtenerNombresEmpresasPorCorreo(usuarioCorreoEmpresa) // Datos retornados del API

                                //Si la respuesta del api es null es porque no hay conexion a Internet o hubo problemas con la conexion al servidor
                                if(resultApi==null){
                                    existenCorreos=null
                                }else if(resultApi.getString("status")=="error"){
                                    if (resultApi.getString("code")=="401"){
                                        if (!snackbarVisible) {
                                            errorResultadoApi=true
                                            coroutineScope.launch {
                                                snackbarVisible=true
                                                snackbarHostState.showSnackbar(
                                                    message = "Error: ${resultApi.getString("data")}"
                                                )
                                                snackbarHostState.currentSnackbarData?.dismiss()
                                                snackbarVisible=false
                                            }
                                        }
                                    }
                                    existenCorreos=false
                                }
                                else if(resultApi.getString("status")=="ok"){
                                    val arrayEmpresas= resultApi.getJSONArray("data")
                                    for (i in 0 until arrayEmpresas.length()) {
                                        val empresa = arrayEmpresas.getJSONObject(i).getString("empresa") // O el campo correspondiente
                                        arrayEmpresasDisponibles.add(empresa)
                                    }
                                    scrollState.scrollTo(0)
                                    ocultarTeclado(contexto)
                                    existenCorreos=true
                                }
                            }

                        }
                    }

                    // Input Correo Cliente
                    OutlinedTextField(
                        value = usuarioCorreoEmpresa,
                        onValueChange =  {
                            usuarioCorreoEmpresa = it
                        },
                        enabled = isBtIniciarSesionActivo.value,
                        label = {
                            Text("Correo",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Light,
                                color = if (existenCorreos==null || existenCorreos==true) Color(0xFF5B5B5B) else Color.Red,
                                fontSize = objetoAdaptardor.ajustarFont(16),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Default.Email,
                                contentDescription = "Email Icon",
                                tint = if (existenCorreos==null || existenCorreos==true) Color.DarkGray else Color.Red)
                        },
                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                        textStyle = TextStyle(
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(16)
                        ),
                        trailingIcon = {
                            if (existenCorreos==false) {
                                Icon(
                                    imageVector = Icons.Default.Error,
                                    contentDescription = "Correo inválido",
                                    tint = Color.Red
                                )
                            }
                        },
                        placeholder = {
                            Text(
                                "Ingrese su correo",
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
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.DarkGray,
                            unfocusedTextColor = Color.DarkGray,
                            focusedPlaceholderColor = Color.DarkGray,
                            unfocusedPlaceholderColor = Color.DarkGray,
                            focusedBorderColor = if (existenCorreos==null || existenCorreos==true) Color(0xFF5B5B5B) else Color.Red,
                            unfocusedBorderColor = if (existenCorreos==null || existenCorreos==true) Color(0xFF5B5B5B) else Color.Red,
                            cursorColor = Color.DarkGray
                        )
                    )//Fin Input Correo Cliente


                    //Spacer seperador de componente
                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(20)))

                    //Segun es el estado de la variable se va a mostrar un componente
                    // Su funcion es mostrar el estado de busqueda de correos como caragando, correo no econtrado y los resulatados en caso de que el correo este asociado a una empresa
                    when(existenCorreos){
                        null ->{
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                CircularProgressIndicator(
                                    strokeWidth = objetoAdaptardor.ajustarAltura(4),
                                    color = Color(0xFF244BC0)
                                )
                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                Text(
                                    "Buscando empresas asociadas al correo...",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }

                        }// Mostrar Circulo de carga
                        false->{
                            val txtEstadoBusquedaCorreo="No se encontró ninguna empresa asociada a $usuarioCorreoEmpresa"
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                                Text(
                                    text = txtEstadoBusquedaCorreo,
                                    color = Color.Red,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    textAlign = TextAlign.Center
                                    ,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }// Mostrar Mensaje Correo no encontrado
                        else->{
                            var numeroPaginaDdm by remember { mutableIntStateOf(0) }
                            val cantidadEmpresasPorPagina = 50
                            val totalPaginas = (arrayEmpresasDisponibles.size + cantidadEmpresasPorPagina - 1) / cantidadEmpresasPorPagina
                            val nombresEmpresasPaginaActual =
                                arrayEmpresasDisponibles.drop(numeroPaginaDdm * cantidadEmpresasPorPagina)
                                    .take(cantidadEmpresasPorPagina)

                            ExposedDropdownMenuBox(
                                expanded = expandirDdmOpcionesEmpresas,
                                onExpandedChange = { if (isBtIniciarSesionActivo.value) {expandirDdmOpcionesEmpresas = !expandirDdmOpcionesEmpresas} }
                            ) {
                                OutlinedTextField(
                                    value = clienteEmpresaSeleccionada,
                                    readOnly = true,
                                    enabled = isBtIniciarSesionActivo.value,
                                    onValueChange = { clienteEmpresaSeleccionada = it },
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.Home,
                                            contentDescription = "Email Icon",
                                            tint = Color.DarkGray
                                        )
                                    },
                                    trailingIcon = {
                                        Icon(
                                            imageVector = iconoDdmOpcionesEmpresasFlechasLaterales,
                                            contentDescription = " "
                                        )
                                    },
                                    textStyle = TextStyle(
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Light,
                                        color = Color.DarkGray,
                                        fontSize = objetoAdaptardor.ajustarFont(16)
                                    ),
                                    shape = RoundedCornerShape(
                                        objetoAdaptardor.ajustarAltura(
                                            16
                                        )
                                    ),
                                    placeholder = {
                                        var estadoSeleccionEmpresa = "Seleccione su Empresa"
                                        if (arrayEmpresasDisponibles.isEmpty()) {
                                            estadoSeleccionEmpresa = "Ingrese un correo válido"

                                        }
                                        Text(
                                            estadoSeleccionEmpresa,
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Light,
                                            color = Color.DarkGray,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(300))
                                        .height(objetoAdaptardor.ajustarAltura(70))
                                        .onGloballyPositioned { coordinates ->
                                            textFieldOpcionesEmpresasMedida =
                                                coordinates.size.toSize()
                                        }
                                        .menuAnchor(),
                                    label = {
                                        Text(
                                            "Empresa",
                                            color = Color.DarkGray,
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Light,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    },
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedTextColor = Color(0xFF5B5B5B),
                                        unfocusedTextColor = Color(0xFF5B5B5B),
                                        focusedPlaceholderColor = Color(0xFF5B5B5B),
                                        unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                        focusedBorderColor = Color(0xFF5B5B5B),
                                        unfocusedBorderColor = Color(0xFF5B5B5B),
                                        cursorColor = Color.DarkGray
                                    )
                                )


                                ExposedDropdownMenu(
                                    expanded = expandirDdmOpcionesEmpresas,
                                    onDismissRequest = { expandirDdmOpcionesEmpresas = false },
                                    modifier = Modifier
                                        .width(with(LocalDensity.current) { textFieldOpcionesEmpresasMedida.width.toDp() })
                                        .background(Color.White),
                                    scrollState = scrollState
                                ) {
                                    nombresEmpresasPaginaActual.forEach { label ->
                                        DropdownMenuItem(
                                            onClick = {
                                                clienteEmpresaSeleccionada = label
                                                expandirDdmOpcionesEmpresas = false
                                            },
                                            text = {
                                                Text(
                                                    text = label,
                                                    fontFamily = fontAksharPrincipal,
                                                    fontWeight = FontWeight.Medium,
                                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            },
                                            modifier = Modifier
                                                .background(Color.White),
                                            enabled = isBtIniciarSesionActivo.value
                                        )
                                    }
                                    if (numeroPaginaDdm < totalPaginas - 1) {
                                        DropdownMenuItem(
                                            onClick = { numeroPaginaDdm++ },
                                            text = {
                                                Text(
                                                    "Mostrar más",
                                                    fontFamily = fontAksharPrincipal,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color(0xFF31B927),
                                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            },
                                            modifier = Modifier
                                                .background(Color.White),
                                            enabled = isBtIniciarSesionActivo.value
                                        )
                                    }

                                    if (numeroPaginaDdm > 0) {
                                        DropdownMenuItem(
                                            onClick = { numeroPaginaDdm-- },
                                            text = {
                                                Text(
                                                    text = "Mostrar anteriores",
                                                    fontFamily = fontAksharPrincipal,
                                                    fontWeight = FontWeight.Medium,
                                                    color = Color.Red,
                                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            },
                                            modifier = Modifier
                                                .background(Color.White),
                                            enabled = isBtIniciarSesionActivo.value
                                        )
                                    }
                                }
                            }
                        }// Mostrar Lista desplegable de las empresas encontradas con base al correo
                    }

                    //Spacer seperador de componente
                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(20)))

                    // Input Contraseña Cliente
                    OutlinedTextField(
                        value = clientePassword,
                        onValueChange = { newText -> clientePassword = newText },
                        label = {
                            Text(
                                "Contraseña",
                                color = Color.DarkGray,
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Light,
                                fontSize = objetoAdaptardor.ajustarFont(16),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Email Icon", tint = Color.DarkGray) },
                        placeholder = {
                            Text(
                                "Ingrese su contraseña",
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
                        visualTransformation = if (mostrarPasswordOtxf) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (mostrarPasswordOtxf)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            IconButton(onClick = { mostrarPasswordOtxf = !mostrarPasswordOtxf }) {
                                Icon(imageVector = image, contentDescription = "Mostrar/Ocultar contraseña", tint = Color.DarkGray)
                            }
                        },
                        enabled = isBtIniciarSesionActivo.value,
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
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                apiConsultaActual?.cancel()
                                errorResultadoApi=null
                                if (clienteEmpresaSeleccionada.isEmpty()){
                                    errorResultadoApi=true
                                    if (!snackbarVisible) {
                                        coroutineScope.launch {
                                            snackbarVisible=true
                                            snackbarHostState.showSnackbar(
                                                message = "Error: seleccione una empresa"
                                            )
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarVisible=false
                                        }
                                    }
                                }
                                else if(clientePassword.length< 4){
                                    errorResultadoApi=true
                                    if (!snackbarVisible) {
                                        coroutineScope.launch {
                                            snackbarVisible=true
                                            snackbarHostState.showSnackbar(
                                                message = "Error: Ingrese una contraseña correcta."
                                            )
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarVisible=false
                                        }
                                    }
                                }else{
                                    isBtIniciarSesionActivo.value= false
                                }
                            }
                        )
                    )


                    if (!isBtIniciarSesionActivo.value){
                        LaunchedEffect(isBtIniciarSesionActivo) {
                            apiConsultaActual= cortinaConsultaApi.launch{
                                val result= objetoProcesamientoDatosApi.validarInicioSesion(usuarioCorreoEmpresa, clienteEmpresaSeleccionada, clientePassword)
                                if(result==null){
                                    if (!snackbarVisible) {
                                        errorResultadoApi=true
                                        coroutineScope.launch {
                                            snackbarVisible=true
                                            snackbarHostState.showSnackbar(
                                                message = "Error: revise su conexion a Internet"
                                            )
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarVisible=false
                                            isBtIniciarSesionActivo.value= true
                                        }
                                    }
                                }else if (result.getString("status")=="error"){
                                    errorResultadoApi=true
                                    isBtIniciarSesionActivo.value= true
                                    if (!snackbarVisible) {
                                        coroutineScope.launch {
                                            snackbarVisible=true
                                            snackbarHostState.showSnackbar(
                                                message = "Error: ${result.getString("data")}"
                                            )
                                            snackbarHostState.currentSnackbarData?.dismiss()
                                            snackbarVisible=false
                                        }
                                    }
                                }else if (result.getString("status")=="ok"){
                                    coroutineScope.cancel()
                                    val datos= result.getJSONObject("data")
                                    nombreUsuario= datos.getString("Nombre")
                                    nombreEmpresa= datos.getString("Empresa")
                                    apiToken= datos.getString("Token")
                                    codUsuario= datos.getString("Codigo")
                                    isInicioSesionAprobado=true

                                }
                            }
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(16.dp))
                        Checkbox(
                            checked = guardarDatosSesion,
                            onCheckedChange = { guardarDatosSesion = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFF244BC0),
                                uncheckedColor = Color.DarkGray,
                                checkmarkColor = Color.White
                            )
                        )
                        Text("Iniciar sesión automáticamente",
                            fontFamily =  fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            color = Color.DarkGray,
                            fontSize = objetoAdaptardor.ajustarFont(15),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                    //Boton Iniciar Sesion
                    Button(
                        onClick = {
                            apiConsultaActual?.cancel()
                            errorResultadoApi=null
                            if (clienteEmpresaSeleccionada.isEmpty()){
                                errorResultadoApi=true
                                if (!snackbarVisible) {
                                    coroutineScope.launch {
                                        snackbarVisible=true
                                        snackbarHostState.showSnackbar(
                                            message = "Error: seleccione una empresa"
                                        )
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarVisible=false
                                    }
                                }
                            }
                            else if(clientePassword.length< 4){
                                errorResultadoApi=true
                                if (!snackbarVisible) {
                                    coroutineScope.launch {
                                        snackbarVisible=true
                                        snackbarHostState.showSnackbar(
                                            message = "Error: Ingrese una contraseña correcta."
                                        )
                                        snackbarHostState.currentSnackbarData?.dismiss()
                                        snackbarVisible=false
                                    }
                                }
                            }else{
                                isBtIniciarSesionActivo.value= false
                            }
                        },
                        enabled = isBtIniciarSesionActivo.value,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF244BC0), // Color de fondo del botón
                            contentColor = Color.White,
                            disabledContainerColor = Color(0xFF244BC0),
                            disabledContentColor = Color.White
                        ), // Color del texto del botón
                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(12)),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                        border = BorderStroke(width = objetoAdaptardor.ajustarAncho(2), brush = SolidColor(Color(0xFF244BC0))),
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(200))
                            .height(objetoAdaptardor.ajustarAltura(50))
                    ){
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(
                                "Iniciar Sesión",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = objetoAdaptardor.ajustarFont(25),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                        // Texto Boton
                    }

                    // Spacer separador de componente
                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(15)))

                    // Texto Inferior
                    Text("¿Olvidaste tu contraseña?",
                        fontFamily =  fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        color = Color(0xFF244BC0),
                        fontSize = objetoAdaptardor.ajustarFont(15),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.height(objetoAdaptardor.ajustarAltura(35))
                    )
                }
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
                }, contentAlignment = Alignment.TopCenter
        ) {
            val versionApp = stringResource(R.string.app_version)

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center

            ){
                Text(
                    text = "",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabel(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(142)).padding(start = 6.dp)
                )

                Text(
                    text = "Invefacon ©2025",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabel(),
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
                    fontSize = obtenerEstiloLabel(),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(142)).padding(end = 6.dp)
                )
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
                            modifier = Modifier
                                .padding(end = objetoAdaptardor.ajustarAncho(8))
                                .size(objetoAdaptardor.ajustarAltura(35)) // Espacio entre ícono y texto
                        )
                        Text(
                            text = snackbarData.visuals.message,
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = objetoAdaptardor.ajustarFont(18),
                                fontWeight = FontWeight.Light,
                                fontFamily = fontAksharPrincipal
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(snhtMensajesSuperiores) {
                    top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(24))
                    start.linkTo(parent.start)
                }
        )


        if (errorResultadoApi==null){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
        }
        else{
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        }
    }
}

fun ocultarTeclado(contexto: Context) {
    val inputMethodManager = contexto.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = (contexto as? Activity)?.currentFocus
    view?.let {
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@Preview(showBackground = true, widthDp = 384, heightDp = 812, fontScale = 1.15F)
@Composable
private fun Preview(){
    IniciarInterfazInicioSesionCompact(null)
}
