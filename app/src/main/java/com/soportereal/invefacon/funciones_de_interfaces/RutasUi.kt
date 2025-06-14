package com.soportereal.invefacon.funciones_de_interfaces

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import com.soportereal.invefacon.interfaces.modulos.ventas.IniciarInterfazDetalleFactura
import com.soportereal.invefacon.interfaces.modulos.ventas.IniciarInterfazVentas
import com.soportereal.invefacon.interfaces.pantallas_principales.IniciarInterfazMenuPrincipalCompact
import com.soportereal.invefacon.interfaces.pantallas_principales.PantallaCarga
import com.soportereal.invefacon.interfaces.pantallas_principales.ajustes.IniciarInterfazAjustes
import com.soportereal.invefacon.interfaces.pantallas_principales.ajustes.IniciarInterfazAjustesImpresora
import com.soportereal.invefacon.interfaces.pantallas_principales.inicio.IniciarInterfazInicio
import com.soportereal.invefacon.interfaces.pantallas_principales.salir.IniciarInterfazSalir


sealed class RutasPatallas(val ruta: String){

    // Auntenticacion
    data object InicioSesion : RutasPatallas("auth/Inicio Sesion")

    // Principales
    data object Inicio : RutasPatallas("main/Inicio")
    data object InicioAuto : RutasPatallas("main/InicioAuto")

    // Modulos
    data object Clientes : RutasPatallas("mod/Clientes")
    data object Sac : RutasPatallas("mod/Sac")
    data object Facturacion : RutasPatallas("mod/Facturacion")
    data object Proformas : RutasPatallas("mod/Proformas")
    data object Ventas : RutasPatallas("mod/Ventas")

    // Clientes
    data object ClientesInfo : RutasPatallas("Clientes/Info")
    data object ClientesAgregar: RutasPatallas("Clientes/Agregar")

    // Ventas
    data object VentasDetalleFactura : RutasPatallas("Ventas/DestalleFactura")

    // Sac
    data object SacComanda : RutasPatallas("Sac/Comanda")

    // Ajustes
    data object  AjustImpresora : RutasPatallas("Ajust/Impresora")
}

sealed class RutasPantallasMenuPrincipal(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector
) {
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



@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("ContextCastToActivity")
@Composable
fun NavegacionPantallas(
    navcontroller: NavHostController
){
    val contexto = LocalContext.current
    guardarParametroSiNoExiste(contexto, "ultimoCorreo", "")
    guardarParametroSiNoExiste(contexto, "token", "0")
    guardarParametroSiNoExiste(contexto, "nombreUsuario", "0")
    guardarParametroSiNoExiste(contexto, "nombreEmpresa", "0")
    guardarParametroSiNoExiste(contexto, "codUsuario", "0")
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        NavHost(navController= navcontroller, startDestination = if(obtenerParametroLocal(contexto, "token")=="0") "auth" else "main"){

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
                startDestination = if(obtenerParametroLocal(contexto, "token")=="0") RutasPatallas.Inicio.ruta else RutasPatallas.InicioAuto.ruta,
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
                        codUsuario = codUsuario
                    )
                }

                composable(
                    RutasPatallas.InicioAuto.ruta
                ) {
                    IniciarInterfazMenuPrincipalCompact(
                        token = obtenerParametroLocal(contexto, "token"),
                        nombreEmpresa = obtenerParametroLocal(contexto, "nombreEmpresa"),
                        nombreUsuario = obtenerParametroLocal(contexto, "nombreUsuario"),
                        navControllerPrincipal = navcontroller,
                        codUsuario = obtenerParametroLocal(contexto, "codUsuario")
                    )
                }
            }

            // Navigacion pantallas Modulo Clientes
            navigation(startDestination = RutasPatallas.Clientes.ruta, route = "Clientes"){
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
                        navController = navcontroller,
                        nombreEmpresa = obtenerParametroLocal(contexto, "nombreEmpresa"),
                        nombreUsuario = obtenerParametroLocal(contexto, "nombreUsuario"),
                        codUsuario = obtenerParametroLocal(contexto, "codUsuario")
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
                        token = token,
                        nombreEmpresa = obtenerParametroLocal(contexto, "nombreEmpresa"),
                        nombreUsuario = obtenerParametroLocal(contexto, "nombreUsuario"),
                        codUsuario = obtenerParametroLocal(contexto, "codUsuario")
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
                        token = token,
                        nombreEmpresa = obtenerParametroLocal(contexto, "nombreEmpresa"),
                        nombreUsuario = obtenerParametroLocal(contexto, "nombreUsuario"),
                        codUsuario = obtenerParametroLocal(contexto, "codUsuario")
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
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                    IniciarInterfazFacturacion(
                        token = token,
                        navController = navcontroller,
                        nombreEmpresa = nombreEmpresa,
                        codUsuario = codUsuario,
                        nombreUsuario = nombreUsuario
                    )
                }
            }

            // Navegacion modulo de Ventas
            navigation(startDestination = RutasPatallas.Ventas.ruta, route= "Ventas") {

                composable(
                    route = RutasPatallas.Ventas.ruta + "/{token}/{nombreEmpresa}/{codUsuario}/{nombreUsuario}",
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
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                    IniciarInterfazVentas(
                        token = token,
                        navController = navcontroller,
                        nombreEmpresa = nombreEmpresa,
                        codUsuario = codUsuario,
                        nombreUsuario = nombreUsuario
                    )
                }

                composable(
                    route = RutasPatallas.VentasDetalleFactura.ruta + "/{token}/{nombreEmpresa}/{codUsuario}/{nombreUsuario}/{numeroFactura}",
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
                        },
                        navArgument(name = "numeroFactura") {
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
                    val numeroFactura =
                        requireNotNull(backStackEntry.arguments?.getString("numeroFactura"))
                    val activity = LocalContext.current as Activity
                    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

                    IniciarInterfazDetalleFactura(
                        token = token,
                        navController = navcontroller,
                        nombreEmpresa = nombreEmpresa,
                        codUsuario = codUsuario,
                        nombreUsuario = nombreUsuario,
                        numeroFactura = numeroFactura
                    )
                }
            }

        }
        PantallaCarga()
        DialogoActualizacion()
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavHostPantallasMenuPrincipal(
    token: String,
    nombreUsuario: String,
    nombreEmpresa: String,
    navControllerPrincipal: NavController,
    navControllerPantallasMenuPrincipal: NavHostController,
    codUsuario: String
){
    NavHost(
        navController = navControllerPantallasMenuPrincipal,
        startDestination = "Inicio",
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Navigacion pantalla Inicio
        navigation(
            startDestination = PantallaInicio.ruta, route = "Inicio"){

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
                    codUsuario = codUsuario
                )
            }
        }

        // Navigacion pantallas Ajustes
        navigation(
            startDestination = PantallaAjustes.ruta, route = "Ajustes"){

            composable(
                PantallaAjustes.ruta,
                enterTransition = { slideInHorizontally(
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) { it } },
                exitTransition = { slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) { -it } }
            ) {
                IniciarInterfazAjustes(
                    navController = navControllerPantallasMenuPrincipal
                )
            }

            composable(
                RutasPatallas.AjustImpresora.ruta,
                enterTransition = { slideInHorizontally(
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) { it } },
                exitTransition = { slideOutHorizontally(
                    animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
                ) { -it } }
            ) {
                IniciarInterfazAjustesImpresora(
                    navController = navControllerPantallasMenuPrincipal,
                    nombreEmpresa, codUsuario
                )
            }
        }

        // Navigacion pantalla Salir
        navigation(
            startDestination = PantallaSalir.ruta, route = "Salir"){

            composable(
                PantallaSalir.ruta
            ) {
                IniciarInterfazSalir(navControllerPrincipal)
            }
        }

    }
}
