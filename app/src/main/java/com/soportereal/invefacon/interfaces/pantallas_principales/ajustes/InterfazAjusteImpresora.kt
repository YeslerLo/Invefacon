package com.soportereal.invefacon.interfaces.pantallas_principales.ajustes

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatAlignLeft
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Print
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
import com.soportereal.invefacon.funciones_de_interfaces.gestorImpresora
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametro
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.interfaces.obtenerEstiloBodySmall
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.interfaces.obtenerEstiloTitleSmall
import com.soportereal.invefacon.interfaces.pantallas_principales.objetoEstadoPantallaCarga


@RequiresApi(Build.VERSION_CODES.S)
@SuppressLint("MissingPermission")
@Composable
fun IniciarInterfazAjustesImpresora (
    navController: NavController,
    nombreEmpresa : String,
    codUsuario : String
){
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val context = LocalContext.current
    var iniciarMenuParametrosImpresora by remember { mutableStateOf(false) }
    var valorImpresionActiva by remember { mutableStateOf( obtenerParametro(context, "isImpresionActiva$codUsuario$nombreEmpresa")) }
    gestorImpresora.PedirPermisos(context)


    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(objetoAdaptardor.ajustarAltura(622))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior, columPrincipal,flechaRegresar)= createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(70))
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
                    Icons.Filled.Print,
                    contentDescription ="Icono Ajustes",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Impresora",
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

        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.constrainAs(flechaRegresar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(16))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = objetoAdaptardor.ajustarAltura(660))
                .padding(objetoAdaptardor.ajustarAltura(16))
                .constrainAs(columPrincipal) {
                    start.linkTo(parent.start)
                    top.linkTo(bxSuperior.bottom)
                },
            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = "",
                    tint = if (gestorImpresora.isConectada) Color.Green else Color.Red,
                    modifier = Modifier
                        .size(objetoAdaptardor.ajustarAltura(15))
                        .weight(0.1f)
                )

                Column(
                    modifier = Modifier
                        .weight(1f)
                ){
                    TText(
                        text = if (gestorImpresora.isConectada)
                            "Conectado a: ${gestorImpresora.dispositivoActual?.device?.name}"
                        else
                            "Sin Conexión",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = obtenerEstiloTitleSmall()
                    )
                    TText(
                        text = if (gestorImpresora.isConectada)
                            "MAC: ${gestorImpresora.dispositivoActual?.device?.address}"
                        else
                            "Seleccione un dispositivo...",
                        textAlign = TextAlign.Start,
                        fontSize = obtenerEstiloBodyBig()
                    )
                }
                if (gestorImpresora.isConectada){
                    Column(
                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4)),
                        horizontalAlignment = Alignment.End
                    ){
                        BButton(
                            text = "Desconectar",
                            objetoAdaptardor = objetoAdaptardor,
                            onClick = {
                                gestorImpresora.deconectar(context)
                            },
                            backgroundColor = Color.Red,
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.height(objetoAdaptardor.ajustarAltura(35))
                        )
                        BButton(
                            text = "Probar",
                            objetoAdaptardor = objetoAdaptardor,
                            onClick = {
                            gestorImpresora.probar(context, nombreEmpresa)
                            },
                            textSize = obtenerEstiloBodyBig(),
                            modifier = Modifier.height(objetoAdaptardor.ajustarAltura(35))
                        )
                    }

                }

            }
            HorizontalDivider()
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ){
                BButton(
                    text = "Parametros",
                    objetoAdaptardor = objetoAdaptardor,
                    onClick = {
                        iniciarMenuParametrosImpresora = true
                    },
                    textSize = obtenerEstiloBodyBig(),
                    modifier = Modifier
                        .height(objetoAdaptardor.ajustarAltura(35))
                        .padding(4.dp)
                )
                BButton(
                    text = "Buscar",
                    objetoAdaptardor = objetoAdaptardor,
                    onClick = {
                        if (valorImpresionActiva != "1") return@BButton Toast.makeText(context, "La impresión está inactiva.", Toast.LENGTH_SHORT).show()
                        gestorImpresora.buscar(context)
                    },
                    textSize = obtenerEstiloBodyBig(),
                    modifier = Modifier
                        .height(objetoAdaptardor.ajustarAltura(35))
                        .padding(4.dp)
                )
            }
            TText(
                text = "Impresoras disponibles:",
                textAlign = TextAlign.Start,
                fontSize = obtenerEstiloTitleBig()
            )
            LazyColumn(
                modifier = Modifier
                    .height(objetoAdaptardor.ajustarAltura(400))
            ){
                if(gestorImpresora.listaImpresoras.isNotEmpty()){
                    items(gestorImpresora.listaImpresoras){ impresora ->
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED) {

                            Button(
                                onClick = {
                                    if (valorImpresionActiva != "1") return@Button Toast.makeText(context, "La impresión está inactiva.", Toast.LENGTH_SHORT).show()
                                    gestorImpresora.conexion = impresora
                                    gestorImpresora.conectar(context,impresora.device.address,impresora.device.name)
                                    gestorImpresora.dispositivoActual = impresora
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.White,
                                    contentColor = Color.White,
                                    disabledContainerColor = Color.White,
                                    disabledContentColor = Color.White
                                ),
                                contentPadding =  PaddingValues(0.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8)),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = objetoAdaptardor.ajustarAltura(8))
                                        .wrapContentHeight()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Print,
                                        contentDescription = "",
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30)),
                                        tint = Color.DarkGray
                                    )
                                    Column{
                                        TText(
                                            text = impresora.device.name,
                                            textAlign = TextAlign.Start,
                                            fontSize = obtenerEstiloBodyBig()
                                        )
                                        TText(
                                            text = impresora.device.address,
                                            textAlign = TextAlign.Start,
                                            fontSize = obtenerEstiloBodyBig()
                                        )
                                    }
                                }
                            }

                            HorizontalDivider()
                        }
                    }
                }
            }
        }
        objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
    }

    if (iniciarMenuParametrosImpresora){
        var caracteresPorLinea by remember { mutableStateOf( obtenerParametro(context, "cantidadCaracPorLineaImpre")) }
        var isImpresionActiva by remember { mutableStateOf(valorImpresionActiva=="1") }
        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isMenuVisible = true
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = isMenuVisible,
                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
            ) {
                Surface(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White
                ) {
                    Box(
                        modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(24)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Parametros Impresora",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = objetoAdaptardor.ajustarFont(27),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Impresión Activa: ",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloBodySmall(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Switch(
                                    checked = isImpresionActiva,
                                    onCheckedChange = {
                                        actualizarParametro(context, "isImpresionActiva$codUsuario$nombreEmpresa", if(valorImpresionActiva == "0") "1" else "0")
                                        valorImpresionActiva = obtenerParametro(context, "isImpresionActiva$codUsuario$nombreEmpresa")
                                        isImpresionActiva = valorImpresionActiva == "1"
                                    },colors = SwitchDefaults.colors(
                                        checkedTrackColor = Color(0xFF1D3FA4)
                                    )
                                )
                            }
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ){
                                Text(
                                    "Caracteres por línea: ",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloBodySmall(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                BBasicTextField(
                                    value = caracteresPorLinea,
                                    onValueChange = {
                                        actualizarParametro(context, "cantidadCaracPorLineaImpre", it)
                                        caracteresPorLinea = obtenerParametro(context, "cantidadCaracPorLineaImpre")
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    icono = Icons.AutoMirrored.Filled.FormatAlignLeft,
                                    alto = 32,
                                    ancho = 100,
                                    opciones = listOf(ParClaveValor("32","32"),
                                        ParClaveValor("48","48")
                                    )
                                )
                            }
                            BButton(
                                text = "Salir",
                                onClick = {
                                    iniciarMenuParametrosImpresora= false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                                backgroundColor = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S)
@Preview
@Composable
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazAjustesImpresora(nav,"", "")
}