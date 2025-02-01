package com.soportereal.invefacon.funciones_de_interfaces

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaAjustes
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaInicio
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaSalir
import com.soportereal.invefacon.interfaces.compact.inicio_sesion.IniciarInterfazInicioSesionCompact
import com.soportereal.invefacon.interfaces.compact.modulos.clientes.IniciarInterfazAgregarCliente
import com.soportereal.invefacon.interfaces.compact.modulos.clientes.IniciarInterfazInformacionCliente
import com.soportereal.invefacon.interfaces.compact.modulos.clientes.IniciarInterfazModuloClientes
import com.soportereal.invefacon.interfaces.compact.modulos.clientes.InterfazModuloClientes
import com.soportereal.invefacon.interfaces.compact.modulos.sac.IniciarInterfazModuloSac
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.CustomBarView
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.IniciarInterfazAjustes
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.IniciarInterfazInicio
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.IniciarInterfazMenuPrincipalCompact
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.IniciarInterfazSalir
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.InterfazInicio
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.objetoEstadoPantallaCarga
import com.soportereal.invefacon.interfaces.large.sac.InterfazModuloSacLarge
import com.soportereal.invefacon.interfaces.large.sac.InterfazSacComandaLarge


sealed class RutasPantallasPrincipales (
    val ruta: String
)
{
    data object PantallaInicioSesion: RutasPantallasPrincipales(
        ruta = "PantallaInicioSesion"
    )
    data object PantallaMenuPrincipal: RutasPantallasPrincipales(
        ruta = "PantallasMenuPrincipal"
    )
}

sealed class RutasPantallasMenuPrincipal(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
)
{
    data object PantallaInicio: RutasPantallasMenuPrincipal(
        ruta = "PantallaInicio",
        titulo = "Inicio",
        icono = Icons.Filled.Home
    )
    data object PantallaAjustes: RutasPantallasMenuPrincipal(
        ruta = "PantallaAjustes",
        titulo = "Ajustes",
        icono = Icons.Filled.Settings
    )
    data object PantallaSalir: RutasPantallasMenuPrincipal(
        ruta = "PantallaSalir",
        titulo = "Salir",
        icono = Icons.AutoMirrored.Filled.Logout
    )
}

sealed class RutasPantallasModulos(
    val ruta: String
)
{
    data object PantllaInicio: RutasPantallasModulos("PantallaInicio")
    data object PantallaModuloClientes: RutasPantallasModulos("PantallaModuloClientes")
    data object PantallaModuloSac: RutasPantallasModulos("PantallaModuloSac")

}

sealed class RutasPantallasModuloClientes(
    val ruta: String
)
{
    data object PantallaInicioModuloClientes: RutasPantallasModuloClientes("PantallaInicioModuloClientes")
    data object PantallaInfoCliente: RutasPantallasModuloClientes("PantallaInfoCliente")
    data object PantallaAgregarCliente: RutasPantallasModuloClientes("PantallaAgregarCliente")
}

sealed class RutasPantallasModuloSac(
    val ruta: String
){
    data object PantallaInicioMuduloSac: RutasPantallasModuloSac("PantallaInicioModuloSac")
    data object PantallaSacComanda: RutasPantallasModuloSac("PantallaSacComanda")
}

@Composable
fun NavHostPrincipal(
    navControllerPrincipal: NavHostController
){
    val systemUiController = rememberSystemUiController()
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val isCargandoPantallasPrincipales by objetoEstadoPantallaCarga.isCargandoPantallaPrincipales.collectAsState()

    Box(modifier = Modifier.fillMaxSize()){

        NavHost(navController= navControllerPrincipal, startDestination = RutasPantallasPrincipales.PantallaInicioSesion.ruta){
            composable(RutasPantallasPrincipales.PantallaInicioSesion.ruta
            ) {
                when(dpAnchoPantalla){
                    in 300..600->{
                        IniciarInterfazInicioSesionCompact(navControllerPrincipal)
                    }else->{
                        IniciarInterfazInicioSesionCompact(navControllerPrincipal)
                    }
                }
            }
            composable(RutasPantallasPrincipales.PantallaMenuPrincipal.ruta+"/{token}"+"/{nombreEmpresa}"+"/{nombreUsuario}"+"/{codUsuario}",
                arguments = listOf(
                    navArgument(name= "token"){
                        type= NavType.StringType
                        defaultValue="error"
                    },
                    navArgument(name= "nombreEmpresa"){
                        type= NavType.StringType
                        defaultValue="error"
                    },
                    navArgument(name= "nombreUsuario"){
                        type= NavType.StringType
                        defaultValue="error"
                    },
                    navArgument(name= "codUsuario"){
                        type= NavType.StringType
                        defaultValue="error"
                    }
                ),
                enterTransition = { slideInHorizontally { it } + fadeIn() },
                exitTransition = { slideOutHorizontally { -it } + fadeOut() },
                popEnterTransition = { fadeIn() },
                popExitTransition = { fadeOut() }
            ) { backstackEntry->
                val token= requireNotNull(backstackEntry.arguments?.getString("token"))
                val nombreEmpresa= requireNotNull(backstackEntry.arguments?.getString("nombreEmpresa"))
                val nombreUsuario= requireNotNull(backstackEntry.arguments?.getString("nombreUsuario"))
                val codUsuario= requireNotNull(backstackEntry.arguments?.getString("codUsuario"))
                IniciarInterfazMenuPrincipalCompact(token,nombreEmpresa, nombreUsuario,navControllerPrincipal, systemUiController, codUsuario)
            }
        }

        if(isCargandoPantallasPrincipales){
            CustomBarView(systemUiController)
        }


    }

}

@Composable
fun NavHostPantallasMenuPrincipal(
    innerPadding: PaddingValues,
    token: String,
    nombreUsuario: String,
    nombreEmpresa: String,
    navControllerPrincipal: NavController?,
    navControllerPantallasMenuPrincipal: NavHostController,
    systemUiController: SystemUiController,
    codUsuario: String
){

    NavHost(
        navController = navControllerPantallasMenuPrincipal,
        startDestination = PantallaInicio.ruta,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(
            PantallaInicio.ruta,
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } }
        ) {
            if (navControllerPrincipal != null) {
                IniciarInterfazInicio(
                    token = token,
                    nombreUsuario = nombreUsuario,
                    nombreEmpresa = nombreEmpresa,
                    navControllerPrincipal = navControllerPrincipal,
                    systemUiController= systemUiController,
                    codUsuario = codUsuario
                )
            }
        }
        composable(
            PantallaAjustes.ruta,
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } }
        ) {
            IniciarInterfazAjustes()
        }
        composable(
            PantallaSalir.ruta
        ) {
            if (navControllerPrincipal != null) {
                IniciarInterfazSalir(navControllerPrincipal)
            }
        }
    }
}

@Composable
fun NavHostPantallasModulos(
    navControllerPantallasModulos:NavHostController,
    navControllerPrincipal:NavController,
    token: String,
    nombreUsuario: String,
    nombreEmpresa: String,
    codUsuario: String,
    innerPadding: PaddingValues,
    systemUiController: SystemUiController
){
    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navControllerPantallasModulos,
            startDestination = RutasPantallasModulos.PantllaInicio.ruta,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            composable(
                RutasPantallasModulos.PantllaInicio.ruta,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) { it }
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) { -it }
                }
            ) {
                InterfazInicio(
                    navControllerPantallasModulos = navControllerPantallasModulos,
                    nombreUsuario = nombreUsuario,
                    nombreEmpresa = nombreEmpresa,
                    navControllerPrincipal = navControllerPrincipal,
                    systemUiController = systemUiController
                )
            }

            composable(
                RutasPantallasModulos.PantallaModuloClientes.ruta,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) { it }
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) { -it }
                }
            ) {
                IniciarInterfazModuloClientes(token, systemUiController, navControllerPantallasModulos)
            }

            composable(
                RutasPantallasModulos.PantallaModuloSac.ruta,
                enterTransition = {
                    slideInHorizontally(
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) { it }
                },
                exitTransition = {
                    slideOutHorizontally(
                        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                    ) { -it }
                }
            ) {
                IniciarInterfazModuloSac(token, systemUiController, navControllerPantallasModulos, codUsuario, nombreEmpresa)
            }
        }
    }
}

@Composable
fun NavHostPantallasModuloClientes(
    apiToken: String,
    navControlerPantallasModuloClientes: NavHostController,
    innerPadding: PaddingValues,
    systemUiController: SystemUiController,
    navControllerPantallasModulos: NavController?
){
    NavHost(
        navController = navControlerPantallasModuloClientes,
        startDestination = RutasPantallasModuloClientes.PantallaInicioModuloClientes.ruta,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(
            RutasPantallasModuloClientes.PantallaInicioModuloClientes.ruta,
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } }
        ){
            InterfazModuloClientes(apiToken, navControlerPantallasModuloClientes, systemUiController,navControllerPantallasModulos)
        }

        composable(
            RutasPantallasModuloClientes.PantallaInfoCliente.ruta+"/{codigoCliente}",
            arguments = listOf(
                navArgument(name = "datosCliente"){
                    type= NavType.StringType
                    defaultValue="error"
                }
            ),
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } }
        ) {backstackEntry->
            val codigoCliente= requireNotNull(backstackEntry.arguments?.getString("codigoCliente"))
            IniciarInterfazInformacionCliente(
                codigoCliente = codigoCliente,
                navControllerPantallasModuloClientes = navControlerPantallasModuloClientes,
                token = apiToken
            )
        }

        composable(
            RutasPantallasModuloClientes.PantallaAgregarCliente.ruta,
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } }
        ) {
            IniciarInterfazAgregarCliente(
                navController = navControlerPantallasModuloClientes,
                token = apiToken
            )
        }
    }
}

@Composable
fun NavHostPantallasModuloSac(
    apiToken: String,
    navControllerPantallasModuloSac: NavHostController,
    innerPadding: PaddingValues,
    systemUiController: SystemUiController,
    navControllerPantallasModulos: NavController?,
    nombreEmpresa: String,
    codUsuario: String
){
    NavHost(
        navController = navControllerPantallasModuloSac,
        startDestination = RutasPantallasModuloSac.PantallaInicioMuduloSac.ruta,
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        composable(
            RutasPantallasModuloSac.PantallaInicioMuduloSac.ruta,
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } }
        ){
            val configuration = LocalConfiguration.current
            val dpAnchoPantalla = configuration.screenWidthDp
            when(dpAnchoPantalla){
                in 300..600-> {
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    InterfazModuloSacLarge(
                        apiToken,
                        navControllerPantallasModuloSac,
                        systemUiController,
                        navControllerPantallasModulos,
                        nombreEmpresa,
                        codUsuario
                    )
                }else->{
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    InterfazModuloSacLarge(
                        apiToken,
                        navControllerPantallasModuloSac,
                        systemUiController,
                        navControllerPantallasModulos,
                        nombreEmpresa,
                        codUsuario
                    )
                }
            }
        }

        composable(
            RutasPantallasModuloSac.PantallaSacComanda.ruta+"/{nombreMesa}/{salon}",
            enterTransition = { slideInHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { it } },
            exitTransition = { slideOutHorizontally(
                animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
            ) { -it } },
            arguments = listOf(
                navArgument(name= "nombreMesa"){
                    type= NavType.StringType
                    defaultValue="error"
                },
                navArgument(name= "salon"){
                    type= NavType.StringType
                    defaultValue="error"
                }

            )
        ){backstackEntry->
            val nombreMesa= requireNotNull(backstackEntry.arguments?.getString("nombreMesa"))
            val salon= requireNotNull(backstackEntry.arguments?.getString("salon"))
            val configuration = LocalConfiguration.current
            val dpAnchoPantalla = configuration.screenWidthDp
            when(dpAnchoPantalla){
                in 300..600-> {
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    InterfazSacComandaLarge(
                        systemUiController = systemUiController,
                        navControllerPantallasModuloSac = navControllerPantallasModuloSac,
                        token = apiToken,
                        nombreMesa= nombreMesa,
                        nombreEmpresa= nombreEmpresa,
                        codUsuario = codUsuario,
                        salon= salon

                    )
                }else->{
                val activity = LocalContext.current as Activity
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                InterfazSacComandaLarge(
                    systemUiController = systemUiController,
                    navControllerPantallasModuloSac = navControllerPantallasModuloSac,
                    token = apiToken,
                    nombreMesa= nombreMesa,
                    nombreEmpresa= nombreEmpresa,
                    codUsuario = codUsuario,
                    salon= salon

                )
            }
            }
        }
    }
}