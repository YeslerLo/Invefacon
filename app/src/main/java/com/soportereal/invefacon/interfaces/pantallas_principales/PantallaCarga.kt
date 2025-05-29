package com.soportereal.invefacon.interfaces.pantallas_principales

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
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
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadSmall
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleMedium
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.json.JSONObject


@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun PantallaCarga() {
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var activeIndex by remember { mutableIntStateOf(0) }
    val mostrarRespuestaApi by estadoRespuestaApi.mostrarDatosRespuestaApi.collectAsState()
    val mostrarSoloRespuestaError by estadoRespuestaApi.mostrarSoloRespuestaError.collectAsState()
    val datosRespuestaApi by estadoRespuestaApi.datosRespuestaApi.collectAsState()
    val isCargandoPantallaMenuPrincipal by gestorEstadoPantallaCarga.isCargandoPantalla.collectAsState()
    var exitoRespuestaApi by remember { mutableStateOf(false) }

    // Animación de las barras
    LaunchedEffect(isCargandoPantallaMenuPrincipal) {
        while (isCargandoPantallaMenuPrincipal) {
            activeIndex = (activeIndex + 1) % 5
            delay(200L)
        }
    }
    if (isCargandoPantallaMenuPrincipal){
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
                        inactiveColor = Color(36, 75, 192).copy(alpha = 0.3f)
                    )
                }
            }
        }
    }

    if(!isCargandoPantallaMenuPrincipal){
        // Estado para controlar la visibilidad del diálogo
        if (datosRespuestaApi.toString()!="{}"){
            exitoRespuestaApi= datosRespuestaApi.getString("code")=="200" && datosRespuestaApi.getString("status")=="ok"
        }
        if(mostrarRespuestaApi || (mostrarSoloRespuestaError && !exitoRespuestaApi)){
            Box(
                modifier = Modifier
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.4f)),
                contentAlignment = Alignment.Center
            ) {
                AlertDialog(
                    onDismissRequest = {},
                    containerColor = Color.White,
                    confirmButton = {
                        Button(
                            onClick = {  estadoRespuestaApi.cambiarEstadoRespuestaApi(
                                mostrarRespuesta = false,
                                regresarPantallaAnterior = exitoRespuestaApi
                            ) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF244BC0),
                                contentColor = Color.White
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Ok",
                                fontFamily = FontFamily(Font(R.font.akshar_medium)),
                                fontSize = obtenerEstiloTitleBig()
                            )
                        }
                    },
                    text = {
                        DialogContent(exito = exitoRespuestaApi, mensaje = datosRespuestaApi.getString("data"), objetoAdaptardor)
                    }
                )
            }
        }

    }
}

@Composable
private fun DialogContent(exito: Boolean, mensaje: String, objetoAdaptarContenido: FuncionesParaAdaptarContenido) {
    val scale by rememberInfiniteTransition(label = "").animateFloat(
        initialValue = 0.9f,
        targetValue = 1.1f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val icon = if (exito) Icons.Default.CheckCircle else Icons.Default.Error
    val color = if (exito) Color(0xFF4CAF50) else Color(0xFFF44336)
    val titulo = if (exito) "Éxito" else "Error"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier
                .size(objetoAdaptarContenido.ajustarAltura(70))
                .graphicsLayer(scaleX = scale, scaleY = scale)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = titulo,
            fontFamily = FontFamily(Font(R.font.akshar_medium)),
            fontSize = obtenerEstiloHeadSmall(),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(objetoAdaptarContenido.ajustarAltura(8)))

        Text(
            text = mensaje,
            fontFamily = FontFamily(Font(R.font.akshar_regular)),
            fontSize = obtenerEstiloTitleMedium(),
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = objetoAdaptarContenido.ajustarAncho(16))
        )
    }
}

@Composable
fun BarItem(
    isActive: Boolean,
    activeHeight: androidx.compose.ui.unit.Dp,
    inactiveHeight: androidx.compose.ui.unit.Dp,
    activeColor: Color,
    inactiveColor: Color
) {
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
    private val _isCargandoPantalla = MutableStateFlow(false)

    val isCargandoPantalla: StateFlow<Boolean> = _isCargandoPantalla

    fun cambiarEstadoPantallasCarga(cargando: Boolean) {
        _isCargandoPantalla.value = cargando
    }
}

val gestorEstadoPantallaCarga= EstadoPantallaCarga()

class EstadoRespuestaApi : ViewModel(){
    private val _mostrarDatosRespuestaApi = MutableStateFlow(false)
    private val _datosRespuestaApi = MutableStateFlow(JSONObject("{}"))
    private val _estadoBtOk = MutableStateFlow(false)
    private val _mostrarSoloRespuestaError = MutableStateFlow(false)


    val mostrarDatosRespuestaApi: StateFlow<Boolean> = _mostrarDatosRespuestaApi
    val datosRespuestaApi : StateFlow<JSONObject> = _datosRespuestaApi
    val estadoBtOk : StateFlow<Boolean> = _estadoBtOk
    val mostrarSoloRespuestaError : StateFlow<Boolean> = _mostrarSoloRespuestaError


    fun cambiarEstadoRespuestaApi(
        mostrarRespuesta: Boolean= false,
        datosRespuesta: JSONObject= JSONObject("{}"),
        regresarPantallaAnterior: Boolean  = false,
        mostrarSoloRespuestaError: Boolean= false
    ){
        _mostrarDatosRespuestaApi.value= mostrarRespuesta
        _datosRespuestaApi.value= datosRespuesta
        _estadoBtOk.value = regresarPantallaAnterior
        _mostrarSoloRespuestaError.value = mostrarSoloRespuestaError
    }
}

val estadoRespuestaApi= EstadoRespuestaApi()