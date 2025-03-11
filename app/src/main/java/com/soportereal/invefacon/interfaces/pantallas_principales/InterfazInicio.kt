package com.soportereal.invefacon.interfaces.pantallas_principales

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas
import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametro
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@SuppressLint("SourceLockedOrientationActivity", "ContextCastToActivity")
@Composable
fun IniciarInterfazInicio(
    token: String,
    nombreEmpresa: String,
    nombreUsuario: String,
    navControllerPrincipal: NavController,
    systemUiController: SystemUiController,
    codUsuario: String
){
    val activity = LocalContext.current as Activity
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    systemUiController.setStatusBarColor(Color(0xFF244BC0))
    systemUiController.setNavigationBarColor(Color.Black)
    val aksharFont = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var iniciarPantallaModulo by remember { mutableStateOf(false) }
    var rutaPantallaModulo by remember { mutableStateOf("") }
    val contexto = LocalContext.current

    // Estado para controlar la visibilidad del diálogo
    val showDialog = remember { mutableStateOf(false) }

    // Interceptar el botón de retroceso
    BackHandler {
        showDialog.value = true // Mostrar el diálogo
    }

    // Mostrar el diálogo si es necesario
    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false }, // Cerrar el diálogo sin acción
            title = {
                Text(
                    "Cerrar sesion",
                    fontFamily = aksharFont,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = objetoAdaptardor.ajustarFont(18),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            text = {
                Text(
                    "¿Estás seguro de que quieres salir?",
                    fontFamily = aksharFont,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black,
                    fontSize = objetoAdaptardor.ajustarFont(18),
                    overflow = TextOverflow.Ellipsis
                )
            },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                    containerColor =Color(0xDFC73434), // Color de fondo del botón
                    contentColor = Color.White // Color del contenido (texto e iconos)
                ),
                    onClick = {
                        if (obtenerParametro(contexto, "token") =="0"){
                            navControllerPrincipal.popBackStack()
                        }else{
                            actualizarParametro(contexto, "token","0")
                            actualizarParametro(contexto, "nombreUsuario","0")
                            actualizarParametro(contexto, "nombreEmpresa","0")
                            actualizarParametro(contexto, "codUsuario","0")
                            navControllerPrincipal.navigate("auth") {
                                popUpTo("main") { inclusive = true }
                            }
                        }
                        showDialog.value = false // Cerrar el diálog
                    }
                ) {
                    Text(
                        "Salir",
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = objetoAdaptardor.ajustarFont(17),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor =Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White // Color del contenido (texto e iconos)
                    )
                ) {
                    Text(
                        "Cancelar",
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        fontSize = objetoAdaptardor.ajustarFont(17),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        )
    }

    LaunchedEffect(iniciarPantallaModulo) {
        if(iniciarPantallaModulo){
            navControllerPrincipal.navigate(rutaPantallaModulo)
        }
    }


    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .height(objetoAdaptardor.ajustarAltura(722))
        .background(Color(0xFFFFFFFF))
        .statusBarsPadding()
        .navigationBarsPadding()
    )
    {
        val ( bxContenedorBtModulos, bxSuperior)= createRefs()

        @Composable
        fun btOpcionesModulos(text: String, icono: ImageVector, rutaPantalla: String?){
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        if (rutaPantalla!=null){
                            rutaPantallaModulo=rutaPantalla
                            iniciarPantallaModulo=true
                        }
                    }
                },
                modifier = Modifier
                    .height(objetoAdaptardor.ajustarAltura(90))
                    .width(objetoAdaptardor.ajustarAncho(164)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White, // Color de fondo del botón
                    contentColor = Color(0xFF244BC0) // Color del contenido (texto e iconos)
                ),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(8)), // Botón con esquinas redondeadas
                elevation = ButtonDefaults.filledTonalButtonElevation(objetoAdaptardor.ajustarAltura(5))
            )  {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = icono, // Cambia este ícono al que prefieras
                        contentDescription = "Icono del botón",
                        tint = Color(0xFF244BC0),
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(26))
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8))) // Espacio entre el ícono y el texto
                    Text(
                        text = text,
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF244BC0),
                        fontSize = objetoAdaptardor.ajustarFont(15),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
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
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(60))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Column {
                    Text(text = nombreUsuario, color = Color.White,
                        fontSize = objetoAdaptardor.ajustarFont(20),
                        fontFamily = aksharFont, fontWeight = FontWeight.Light,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(150))
                    )
                    Text(text = " $nombreEmpresa", color = Color.White,
                        fontSize = objetoAdaptardor.ajustarFont(15),
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Light,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(150))
                    )
                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(5)))
                }
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(20)))
                Box(modifier = Modifier
                    .height(objetoAdaptardor.ajustarAltura(60))
                    .width(objetoAdaptardor.ajustarAncho(120)),
                    contentAlignment = Alignment.Center
                ) {
                    SubcomposeAsyncImage(
                        model = "https://invefacon.com/img/$nombreEmpresa/$nombreEmpresa.png",
                        contentDescription = "Logo empresa",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.Fit,
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                                )
                            }
                        },
                        error = { Image(
                            modifier = Modifier
                                .fillMaxSize(),
                            painter = painterResource(id = R.drawable.logo_invenfacon),
                            contentDescription = "Descripción de la imagen",
                            contentScale = ContentScale.Fit
                        ) }
                    )
                }
            }
        }

        val lzycLadoDerecho = rememberLazyListState()
        val lzycLadoIzquierdo = rememberLazyListState()


        Box(modifier = Modifier
            .fillMaxWidth()
            .height(objetoAdaptardor.ajustarAltura(640))
            .constrainAs(bxContenedorBtModulos){
                top.linkTo(bxSuperior.bottom)
            },
            contentAlignment = Alignment.Center){
            Row {
                Column {
                    LazyColumn(
                        state = lzycLadoIzquierdo,
                        modifier = Modifier
                            .height(objetoAdaptardor.ajustarAltura(640)),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(12))
                    ) {
                        item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8))) }
                        item { btOpcionesModulos("Facturación", Icons.Default.Description, RutasPatallas.Facturacion.ruta+"/$token"+"/$nombreEmpresa"+"/$codUsuario"+"/$nombreUsuario") }
                        item { btOpcionesModulos("Ventas", Icons.AutoMirrored.Filled.ShowChart, null) }
                        item { btOpcionesModulos("Inventario", Icons.Default.Inventory, null) }
                        item { btOpcionesModulos("CxC", Icons.Default.CreditCard, null) }
                        item { btOpcionesModulos("Proveedores", Icons.Filled.LocalShipping, null) }
                        item { btOpcionesModulos("Usuarios", Icons.Default.Person, null) }
                    }
                }
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(14)))
                Column {
                    LazyColumn(
                        state = lzycLadoDerecho,
                        modifier = Modifier.height(objetoAdaptardor.ajustarAltura(640)),
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(12))
                    ) {
                        item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8))) }
                        item { btOpcionesModulos("Proformas", Icons.Default.Receipt, null) }
                        item { btOpcionesModulos("Clientes", Icons.Default.People, RutasPatallas.Clientes.ruta+"/$token") }
                        item { btOpcionesModulos("SAC", Icons.Default.RestaurantMenu, RutasPatallas.Sac.ruta+"/$token"+"/$nombreEmpresa"+"/$codUsuario/$nombreUsuario") }
                        item { btOpcionesModulos("CxP", Icons.Default.MonetizationOn, null) }
                        item { btOpcionesModulos("Compras", Icons.Default.ShoppingCart, null) }
                        item { btOpcionesModulos("Resumen", Icons.Default.Assessment, null) }
                    }
                }
            }
        }
    }
    objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
}

@Preview(showBackground = true)
@Composable
private fun Preview(){
    val systemUiController = rememberSystemUiController()
    val na = rememberNavController()
    IniciarInterfazInicio("","" ,"", na, systemUiController, "")
}

