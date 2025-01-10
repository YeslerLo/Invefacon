package com.soportereal.invefacon.interfaces.compact.pantallas_principales

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.interfaces.compact.FuncionesParaAdaptarContenidoCompact
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject


@Composable
fun CustomBarView(
    systemUiController: SystemUiController
) {
    val aksharFont = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var activeIndex by remember { mutableIntStateOf(0) }
    val mostrarRespuestaApi by estadoRespuestaApi.mostrarDatosRespuestaApi.collectAsState()
    val mostrarSoloRespuestaError by estadoRespuestaApi.mostrarSoloRespuestaError.collectAsState()
    val datosRespuestaApi by estadoRespuestaApi.datosRespuestaApi.collectAsState()
    val isCargandoPantallaMenuPrincipal by objetoEstadoPantallaCarga.isCargandoPantallasMenuPrincipal.collectAsState()
    val isCargandoPantallasPrincipales by objetoEstadoPantallaCarga.isCargandoPantallaPrincipales.collectAsState()
    val showDialog = remember { mutableStateOf(mostrarRespuestaApi) }
    var exitoRespuestaApi by remember { mutableStateOf(false) }

    // Animación de las barras
    LaunchedEffect(isCargandoPantallaMenuPrincipal, isCargandoPantallasPrincipales) {
        while (isCargandoPantallaMenuPrincipal || isCargandoPantallasPrincipales) {
            activeIndex = (activeIndex + 1) % 5
            delay(200L)
        }
    }
    if (isCargandoPantallaMenuPrincipal || isCargandoPantallasPrincipales){
        // Fondo translúcido
        Box(
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .clickable(false, onClick = {})
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {

            // Barras animadas
            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(70.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (index in 0 until 5) {
                    BarItem(
                        isActive = index == activeIndex,
                        activeHeight = 60.dp,
                        inactiveHeight = 20.dp,
                        activeColor = Color(36, 75, 192),
                        inactiveColor = Color(36, 75, 192).copy(alpha = 0.3f),
                        systemUiController = systemUiController
                    )
                }
            }

        }
    }


    if(!isCargandoPantallaMenuPrincipal || !isCargandoPantallasPrincipales){

        // Estado para controlar la visibilidad del diálogo
        if (datosRespuestaApi.toString()!="{}"){
            exitoRespuestaApi= datosRespuestaApi.getString("code")=="200" && datosRespuestaApi.getString("status")=="ok"
        }
        else{
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = false, datosRespuesta = JSONObject("""{"code":400,"status":"error","data":"Error"}"""))
        }

        if(mostrarRespuestaApi || (mostrarSoloRespuestaError && !exitoRespuestaApi)){
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .clickable(false, onClick = {})
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                AlertDialog(
                    modifier = Modifier.background(Color.White),
                    containerColor = Color.White,
                    onDismissRequest = { showDialog.value = false }, // Cerrar el diálogo sin acción
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = if(exitoRespuestaApi) Icons.Default.CheckCircle else Icons.Filled.Error,
                                tint = if(exitoRespuestaApi) Color(0xFF4CAF50) else Color(0xFFF44336),
                                contentDescription = "",
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(70))
                            )
                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                            Text(
                                if(exitoRespuestaApi) "Éxito" else "Error",
                                fontFamily = aksharFont,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                fontSize = objetoAdaptardor.ajustarFont(22),
                                overflow = TextOverflow.Ellipsis
                            )
                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                            Text(
                                datosRespuestaApi.getString("data"),
                                fontFamily = aksharFont,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black,
                                fontSize = objetoAdaptardor.ajustarFont(18),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    },
                    confirmButton = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentAlignment = Alignment.Center // Centra los botones dentro de la caja
                        ) {
                            Button(
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF244BC0), // Color de fondo del botón
                                    contentColor = Color.White // Color del contenido (texto e iconos)
                                ),
                                onClick = {
                                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = false, regresarPantallaAnterior = exitoRespuestaApi)
                                }
                            ) {
                                Text(
                                    "Ok",
                                    fontFamily = aksharFont,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White,
                                    fontSize = objetoAdaptardor.ajustarFont(22),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }

                    },
                    dismissButton = {
                    }
                )
            }
        }

    }
}

@Composable
fun BarItem(
    isActive: Boolean,
    activeHeight: androidx.compose.ui.unit.Dp,
    inactiveHeight: androidx.compose.ui.unit.Dp,
    activeColor: Color,
    inactiveColor: Color,
    systemUiController: SystemUiController
) {
    systemUiController.setStatusBarColor(Color(0xFF244BC0))
    val height by animateDpAsState(
        targetValue = if (isActive) activeHeight else inactiveHeight,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing), label = ""
    )

    val color by animateColorAsState(
        targetValue = if (isActive) activeColor else inactiveColor,
        animationSpec = tween(durationMillis = 500, easing = LinearEasing), label = ""
    )

    Box(
        modifier = Modifier
            .width(12.dp)
            .height(height)
            .background(color = color, shape = RoundedCornerShape(4.dp))
    )
}


class EstadoPantallaCarga : ViewModel() {
    private val _isCargandoPantallasMenuPrincipal = MutableStateFlow(false)
    private val _isCargandoPantallasPrincipales= MutableStateFlow(false)

    val isCargandoPantallasMenuPrincipal: StateFlow<Boolean> = _isCargandoPantallasMenuPrincipal
    val isCargandoPantallaPrincipales: StateFlow<Boolean> = _isCargandoPantallasPrincipales

    fun cambiarEstadoMenuPrincipal(cargando: Boolean) {
        _isCargandoPantallasMenuPrincipal.value = cargando
    }

    fun cambiarEstadoPantallaPrincipal(cargando: Boolean){
        _isCargandoPantallasPrincipales.value= cargando
    }
}
val objetoEstadoPantallaCarga= EstadoPantallaCarga()

class EstadoRespuestaApi : ViewModel(){
    private val _mostrarDatosRespuestaApi = MutableStateFlow(false)
    private val _datosRespuestaApi = MutableStateFlow(JSONObject("{}"))
    private val _regresarPantallaAnterior = MutableStateFlow(false)
    private val _mostrarSoloRespuestaError = MutableStateFlow(false)


    val mostrarDatosRespuestaApi: StateFlow<Boolean> = _mostrarDatosRespuestaApi
    val datosRespuestaApi : StateFlow<JSONObject> = _datosRespuestaApi
    val regresarPantallaAnterior : StateFlow<Boolean> = _regresarPantallaAnterior
    val mostrarSoloRespuestaError : StateFlow<Boolean> = _mostrarSoloRespuestaError


    fun cambiarEstadoRespuestaApi(
        mostrarRespuesta: Boolean= false,
        datosRespuesta: JSONObject= JSONObject("{}"),
        regresarPantallaAnterior: Boolean  = false,
        mostrarSoloRespuestaError: Boolean= false
    ){
        _mostrarDatosRespuestaApi.value= mostrarRespuesta
        _datosRespuestaApi.value= datosRespuesta
        _regresarPantallaAnterior.value = regresarPantallaAnterior
        _mostrarSoloRespuestaError.value = mostrarSoloRespuestaError
    }
}
val estadoRespuestaApi= EstadoRespuestaApi()