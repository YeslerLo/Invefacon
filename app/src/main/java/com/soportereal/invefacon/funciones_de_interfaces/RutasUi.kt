package com.soportereal.invefacon.funciones_de_interfaces

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaAjustes
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaInicio
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasMenuPrincipal.PantallaSalir
import com.soportereal.invefacon.interfaces.inicio_sesion.IniciarInterfazInicioSesionCompact
import com.soportereal.invefacon.interfaces.modulos.clientes.IniciarInterfazAgregarCliente
import com.soportereal.invefacon.interfaces.modulos.clientes.IniciarInterfazInformacionCliente
import com.soportereal.invefacon.interfaces.modulos.clientes.IniciarInterfazModuloClientes
import com.soportereal.invefacon.interfaces.modulos.facturacion.IniciarInterfazFacturacion
import com.soportereal.invefacon.interfaces.modulos.sac.InterfazModuloSac
import com.soportereal.invefacon.interfaces.modulos.sac.InterfazSacComanda
import com.soportereal.invefacon.interfaces.pantallas_principales.IniciarInterfazAjustes
import com.soportereal.invefacon.interfaces.pantallas_principales.IniciarInterfazInicio
import com.soportereal.invefacon.interfaces.pantallas_principales.IniciarInterfazMenuPrincipalCompact
import com.soportereal.invefacon.interfaces.pantallas_principales.IniciarInterfazSalir
import com.soportereal.invefacon.interfaces.pantallas_principales.PantallaCarga


sealed class RutasPatallas(val ruta: String){

    // Auntenticacion
    data object InicioSesion : RutasPatallas("auth/Inicio Sesion")

    // Principales ]
    data object Inicio : RutasPatallas("main/Inicio")
    data object InicioAuto : RutasPatallas("main/InicioAuto")

    // Modulos
    data object Clientes : RutasPatallas("mod/Clientes")
    data object Sac : RutasPatallas("mod/Sac")
    data object Facturacion : RutasPatallas("mod/Facturacion")

    // Clientes
    data object ClientesInfo : RutasPatallas("Clientes/Info")
    data object ClientesAgregar: RutasPatallas("Clientes/Agregar")

    // Sac
    data object SacComanda : RutasPatallas("Sac/Comanda")


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


@SuppressLint("ContextCastToActivity")
@Composable
fun NavegacionPantallas(
    navcontroller: NavHostController
){
    val systemUiController = rememberSystemUiController()
    val contexto = LocalContext.current
    guardarParametroSiNoExiste(contexto, "ultimoCorreo", "")
    guardarParametroSiNoExiste(contexto, "token", "0")
    guardarParametroSiNoExiste(contexto, "nombreUsuario", "0")
    guardarParametroSiNoExiste(contexto, "nombreEmpresa", "0")
    guardarParametroSiNoExiste(contexto, "codUsuario", "0")
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        NavHost(navController= navcontroller, startDestination = if(obtenerParametro(contexto, "token")=="0") "auth" else "main"){

            // Navigacion pantalla Auntenticacion
            navigation(startDestination= RutasPatallas.InicioSesion.ruta, route = "auth"){
                composable(RutasPatallas.InicioSesion.ruta
                ) {
                    IniciarInterfazInicioSesionCompact(
                        navController = navcontroller
                    )
                }
            }

            // Navigacion pantallas Principales
            navigation(
                startDestination = if(obtenerParametro(contexto, "token")=="0") RutasPatallas.Inicio.ruta else RutasPatallas.InicioAuto.ruta,
                route = "main"
            ) {
                composable(
                    route = RutasPatallas.Inicio.ruta + "/{token}" + "/{nombreEmpresa}" + "/{nombreUsuario}" + "/{codUsuario}",
                    arguments = listOf(
                        navArgument(name = "token") {
                            type = NavType.StringType
                            defaultValue = "error"
                        },
                        navArgument(name = "nombreEmpresa") {
                            type = NavType.StringType
                            defaultValue = "error"
                        },
                        navArgument(name = "nombreUsuario") {
                            type = NavType.StringType
                            defaultValue = "error"
                        },
                        navArgument(name = "codUsuario") {
                            type = NavType.StringType
                            defaultValue = "error"
                        }
                    ),
                    enterTransition = {
                        slideInHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) { it }
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) { -it }
                    }

                ) { backstackEntry ->
                    val token = requireNotNull(backstackEntry.arguments?.getString("token"))
                    val nombreEmpresa =
                        requireNotNull(backstackEntry.arguments?.getString("nombreEmpresa"))
                    val nombreUsuario =
                        requireNotNull(backstackEntry.arguments?.getString("nombreUsuario"))
                    val codUsuario =
                        requireNotNull(backstackEntry.arguments?.getString("codUsuario"))

                    IniciarInterfazMenuPrincipalCompact(
                        token = token,
                        nombreEmpresa = nombreEmpresa,
                        nombreUsuario = nombreUsuario,
                        navControllerPrincipal = navcontroller,
                        systemUiController = systemUiController,
                        codUsuario = codUsuario
                    )
                }

                composable(
                    RutasPatallas.InicioAuto.ruta
                ) {
                    IniciarInterfazMenuPrincipalCompact(
                        token = obtenerParametro(contexto, "token"),
                        nombreEmpresa = obtenerParametro(contexto, "nombreEmpresa"),
                        nombreUsuario = obtenerParametro(contexto, "nombreUsuario"),
                        navControllerPrincipal = navcontroller,
                        systemUiController = systemUiController,
                        codUsuario = obtenerParametro(contexto, "codUsuario")
                    )
                }
            }

            // Navigacion pantallas Modulo Clientes
            navigation(startDestination = RutasPatallas.Clientes.ruta, "Clientes"){
                composable(
                    route = RutasPatallas.Clientes.ruta+"/{token}",
                    arguments = listOf(
                        navArgument(name= "token"){
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
                    val token= requireNotNull(backstackEntry.arguments?.getString("token"))
                    IniciarInterfazModuloClientes(
                        token = token,
                        systemUiController = systemUiController,
                        navController = navcontroller
                    )
                }

                composable(
                    route = RutasPatallas.ClientesInfo.ruta+"/{codigoCliente}/{token}",
                    arguments = listOf(
                        navArgument(name = "codigoCliente"){
                            type= NavType.StringType
                            defaultValue="error"
                        },
                        navArgument(name= "token"){
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
                    val token= requireNotNull(backstackEntry.arguments?.getString("token"))
                    IniciarInterfazInformacionCliente(
                        codigoCliente = codigoCliente,
                        navControllerPantallasModuloClientes = navcontroller,
                        token = token
                    )
                }

                composable(
                    route = RutasPatallas.ClientesAgregar.ruta+"/{token}",
                    arguments = listOf(
                        navArgument(name= "token"){
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
                    val token= requireNotNull(backstackEntry.arguments?.getString("token"))
                    IniciarInterfazAgregarCliente(
                        navController = navcontroller,
                        token = token
                    )
                }
            }

            // Navigacion pantallas Modulo SAC
            navigation(startDestination = RutasPatallas.Sac.ruta, route= "Sac"){

                composable(
                    route = RutasPatallas.Sac.ruta+"/{token}/{nombreEmpresa}/{codUsuario}/{nombreUsuario}",
                    arguments = listOf(
                        navArgument(name ="token"){
                            type= NavType.StringType
                            defaultValue= "Error"
                        },
                        navArgument(name ="nombreEmpresa"){
                            type= NavType.StringType
                            defaultValue= "Error"
                        },navArgument(name ="codUsuario"){
                            type= NavType.StringType
                            defaultValue= "Error"
                        },
                        navArgument(name= "nombreUsuario"){
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
                ){backStackEntry->
                    val token = requireNotNull(backStackEntry.arguments?.getString("token"))
                    val nombreEmpresa = requireNotNull(backStackEntry.arguments?.getString("nombreEmpresa"))
                    val codUsuario = requireNotNull(backStackEntry.arguments?.getString("codUsuario"))
                    val nombreUsuario= requireNotNull(backStackEntry.arguments?.getString("nombreUsuario"))
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

                    InterfazModuloSac(
                        token = token,
                        systemUiController = systemUiController,
                        navController = navcontroller,
                        nombreEmpresa = nombreEmpresa,
                        codUsuario = codUsuario,
                        nombreUsuario = nombreUsuario
                    )
                }

                composable(
                    RutasPatallas.SacComanda.ruta+"/{nombreMesa}/{salon}/{token}/{nombreEmpresa}/{codUsuario}/{estadoMesa}/{clienteId}/{subCuenta}/{nombreUsuario}",
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
                        },
                        navArgument(name= "token"){
                            type= NavType.StringType
                            defaultValue="error"
                        },
                        navArgument(name= "nombreEmpresa"){
                            type= NavType.StringType
                            defaultValue="error"
                        },
                        navArgument(name= "codUsuario"){
                            type= NavType.StringType
                            defaultValue="error"
                        },
                        navArgument(name= "estadoMesa"){
                            type= NavType.StringType
                            defaultValue="error"
                        },
                        navArgument(name= "clienteId"){
                        type= NavType.StringType
                        defaultValue="error"
                        },
                        navArgument(name= "subCuenta"){
                            type= NavType.StringType
                            defaultValue="error"
                        },
                        navArgument(name= "nombreUsuario"){
                            type= NavType.StringType
                            defaultValue="error"
                        }

                    )
                ){backstackEntry->
                    val nombreMesa= requireNotNull(backstackEntry.arguments?.getString("nombreMesa"))
                    val salon= requireNotNull(backstackEntry.arguments?.getString("salon"))
                    val token= requireNotNull(backstackEntry.arguments?.getString("token"))
                    val nombreEmpresa= requireNotNull(backstackEntry.arguments?.getString("nombreEmpresa"))
                    val codUsuario= requireNotNull(backstackEntry.arguments?.getString("codUsuario"))
                    val estadoMesa= requireNotNull(backstackEntry.arguments?.getString("estadoMesa"))
                    val clienteId= requireNotNull(backstackEntry.arguments?.getString("clienteId"))
                    val subCuenta= requireNotNull(backstackEntry.arguments?.getString("subCuenta"))
                    val nombreUsuario= requireNotNull(backstackEntry.arguments?.getString("nombreUsuario"))

                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                    InterfazSacComanda(
                        systemUiController = systemUiController,
                        navControllerPantallasModuloSac = navcontroller,
                        token = token,
                        nombreMesa= nombreMesa,
                        nombreEmpresa= nombreEmpresa,
                        codUsuario = codUsuario,
                        salon= salon,
                        estadoMesa = estadoMesa,
                        clienteId = clienteId,
                        subCuentaInicial = subCuenta,
                        nombreUsuario = nombreUsuario

                    )
                }
            }

            // Navigacion pantallas Modulo Facturacion
            navigation(startDestination = RutasPatallas.Facturacion.ruta, route= "Facturacion") {

                composable(
                    route = RutasPatallas.Facturacion.ruta + "/{token}/{nombreEmpresa}/{codUsuario}/{nombreUsuario}",
                    arguments = listOf(
                        navArgument(name = "token") {
                            type = NavType.StringType
                            defaultValue = "Error"
                        },
                        navArgument(name = "nombreEmpresa") {
                            type = NavType.StringType
                            defaultValue = "Error"
                        }, navArgument(name = "codUsuario") {
                            type = NavType.StringType
                            defaultValue = "Error"
                        },
                        navArgument(name = "nombreUsuario") {
                            type = NavType.StringType
                            defaultValue = "error"
                        }
                    ),
                    enterTransition = {
                        slideInHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) { it }
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            animationSpec = tween(
                                durationMillis = 500,
                                easing = FastOutSlowInEasing
                            )
                        ) { -it }
                    }
                ) { backStackEntry ->
                    val token = requireNotNull(backStackEntry.arguments?.getString("token"))
                    val nombreEmpresa =
                        requireNotNull(backStackEntry.arguments?.getString("nombreEmpresa"))
                    val codUsuario =
                        requireNotNull(backStackEntry.arguments?.getString("codUsuario"))
                    val nombreUsuario =
                        requireNotNull(backStackEntry.arguments?.getString("nombreUsuario"))

                    IniciarInterfazFacturacion(
                        token = token,
                        systemUiController = systemUiController,
                        navController = navcontroller,
                        nombreEmpresa = nombreEmpresa,
                        codUsuario = codUsuario,
                        nombreUsuario = nombreUsuario
                    )
                }
            }

        }
        PantallaCarga(systemUiController)
    }
}

@Composable
fun NavHostPantallasMenuPrincipal(
    innerPadding: PaddingValues,
    token: String,
    nombreUsuario: String,
    nombreEmpresa: String,
    navControllerPrincipal: NavController,
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
            IniciarInterfazInicio(
                token = token,
                nombreUsuario = nombreUsuario,
                nombreEmpresa = nombreEmpresa,
                navControllerPrincipal = navControllerPrincipal,
                systemUiController= systemUiController,
                codUsuario = codUsuario
            )
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
            IniciarInterfazSalir(navControllerPrincipal)
        }
    }
}
