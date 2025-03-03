package com.soportereal.invefacon.interfaces.pantallas_principales


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.NavHostPantallasMenuPrincipal
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaAjustes
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaInicio
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaSalir
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.obtenerEstiloBody
import com.soportereal.invefacon.interfaces.obtenerEstiloLabel


@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun IniciarInterfazMenuPrincipalCompact(
    token: String,
    nombreEmpresa: String,
    nombreUsuario: String,
    navControllerPrincipal: NavController,
    systemUiController: SystemUiController,
    codUsuario: String
) {
    val aksharFont = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val navControllerPantallasMenuPrincipal = rememberNavController()
    val isCargandoPantallaMenuPrincipal by objetoEstadoPantallaCarga.isCargandoPantalla.collectAsState()
    val mostrarRespuestaApi by estadoRespuestaApi.mostrarDatosRespuestaApi.collectAsState()

    Box(modifier = Modifier
        .statusBarsPadding()
        .navigationBarsPadding()
        .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column {
            Scaffold(
                bottomBar = {
                    NavegacionInferior(navControllerPantallasMenuPrincipal)
                },
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(20))
            ) { innerPadding ->
                    NavHostPantallasMenuPrincipal(
                        innerPadding = innerPadding,
                        token = token,
                        navControllerPrincipal = navControllerPrincipal,
                        navControllerPantallasMenuPrincipal= navControllerPantallasMenuPrincipal,
                        nombreEmpresa = nombreEmpresa,
                        nombreUsuario = nombreUsuario,
                        systemUiController= systemUiController,
                        codUsuario = codUsuario
                    )
            }
        }
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF000000))
                .height(objetoAdaptardor.ajustarAltura(20))
        ) {
            val (tx1, tx2, tx3)= createRefs()
            val versionApp = stringResource(R.string.app_version)
            Text(
                text = "Version: $versionApp",
                color = Color.White,
                fontFamily = aksharFont,
                fontWeight = FontWeight.Light,
                fontSize = obtenerEstiloLabel(),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tx1){
                    start.linkTo(parent.start, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(
                text = "Invefacon ©2025",
                color = Color.White,
                fontFamily = aksharFont,
                fontWeight = FontWeight.Light,
                fontSize = obtenerEstiloLabel(),
                textAlign = TextAlign.Center,
                modifier = Modifier.constrainAs(tx2){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
            )

            Text(
                text = "#$codUsuario _ $nombreUsuario _ $nombreEmpresa",
                color = Color.White,
                fontFamily = aksharFont,
                fontWeight = FontWeight.Light,
                fontSize = obtenerEstiloLabel(),
                modifier = Modifier.constrainAs(tx3){
                    end.linkTo(parent.end, margin = 8.dp)
                    bottom.linkTo(parent.bottom)
                }
            )
        }

        if(isCargandoPantallaMenuPrincipal || mostrarRespuestaApi){
            PantallaCarga(systemUiController)
        }
    }
}

@Composable
fun rutaActiva(navController: NavController): String?{
    val rutaEntrada by navController.currentBackStackEntryAsState()
    return rutaEntrada?.destination?.route
}

@Composable
fun NavegacionInferior(navController: NavController) {
    val aksharFont = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)

    Column {
        // Card en la parte superior que simula la sombra
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), // Ajusta la altura de la sombra
            elevation = CardDefaults.cardElevation(20.dp),
            colors = CardDefaults.cardColors(Color(0x7E8C8C8C))
        ) {}


        NavigationBar (
            modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(70)),
            containerColor = Color.White,
            tonalElevation = 100.dp
        ) {
            val rutaActiva = rutaActiva(navController)
            NavigationBarItem(
                selected = rutaActiva == PantallaInicio.ruta,
                onClick = {
                    navController.navigate(PantallaInicio.ruta){
                        popUpTo(PantallaInicio.ruta){inclusive=true}
                        launchSingleTop=true
                    }
                },
                icon = {
                    Icon(
                        imageVector = PantallaInicio.icono,
                        contentDescription = PantallaInicio.titulo,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(28))
                    )
                },
                label = {
                    Text(
                        PantallaInicio.titulo,
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloBody()
                    )
                },
                alwaysShowLabel = true,
                colors =  NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF244BC0), // Color del ícono cuando el ítem está seleccionado
                    unselectedIconColor = Color(0xFF8C8C8C), // Color del ícono cuando el ítem no está seleccionado
                    selectedTextColor =Color(0xFF244BC0), // Color del texto cuando el ítem está seleccionado
                    unselectedTextColor =Color(0xFF8C8C8C), // Color del texto cuando el ítem no está seleccionado
                    indicatorColor = Color.White // Color del "active indicator"
                )

            )

            NavigationBarItem(
                selected = rutaActiva == PantallaAjustes.ruta,
                onClick = {
                    navController.navigate(PantallaAjustes.ruta){
                        launchSingleTop=true
                    }
                },
                icon = {
                    Icon(
                        imageVector = PantallaAjustes.icono,
                        contentDescription = PantallaAjustes.titulo,
                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(28))
                    )
                },
                label = {
                    Text(
                        PantallaAjustes.titulo,
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloBody()
                    )
                },
                alwaysShowLabel = true,
                colors =  NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF244BC0), // Color del ícono cuando el ítem está seleccionado
                    unselectedIconColor = Color(0xFF8C8C8C), // Color del ícono cuando el ítem no está seleccionado
                    selectedTextColor =Color(0xFF244BC0), // Color del texto cuando el ítem está seleccionado
                    unselectedTextColor =Color(0xFF8C8C8C), // Color del texto cuando el ítem no está seleccionado
                    indicatorColor = Color.White // Color del "active indicator"
                )

            )

            NavigationBarItem(
                selected = rutaActiva == PantallaSalir.ruta,
                onClick = {
                    navController.navigate(PantallaSalir.ruta){
                        launchSingleTop=true
                    }
                },
                icon = {
                    Icon(
                        imageVector = PantallaSalir.icono,
                        contentDescription = PantallaSalir.titulo,
                        modifier = Modifier
                            .size(objetoAdaptardor.ajustarAltura(28))
                            .padding( bottom = 0.dp)
                    )
                },
                label = {
                    Text(
                        PantallaSalir.titulo,
                        fontFamily = aksharFont,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
                        modifier = Modifier.padding(top = 0.dp)
                    )
                },
                alwaysShowLabel = true,
                colors =  NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF244BC0), // Color del ícono cuando el ítem está seleccionado
                    unselectedIconColor = Color(0xFF8C8C8C), // Color del ícono cuando el ítem no está seleccionado
                    selectedTextColor =Color(0xFF244BC0), // Color del texto cuando el ítem está seleccionado
                    unselectedTextColor =Color(0xFF8C8C8C), // Color del texto cuando el ítem no está seleccionado
                    indicatorColor = Color.White // Color del "active indicator"
                )
            )
        }
    }
}



@Preview(showBackground = true, widthDp = 384, heightDp = 812, fontScale = 1.15F)
@Composable
private fun Preview(){
    val systemUiController = rememberSystemUiController()
    val na = rememberNavController()
    IniciarInterfazMenuPrincipalCompact("", "demo", "YESLER LORIO", na,systemUiController,"")
}