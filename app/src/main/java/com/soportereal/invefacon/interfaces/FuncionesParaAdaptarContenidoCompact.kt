package com.soportereal.invefacon.interfaces

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times


internal class FuncionesParaAdaptarContenidoCompact(
    private val alturaPantalla: Int,
    private val anchoPantalla: Int,
    var dpFontPantalla: Float,
    val isPantallaHorizontal: Boolean = false
){

    internal fun ajustarAltura(dpAltura: Int): Dp {

        val resultadoEscala= alturaPantalla/if (anchoPantalla>600 && isPantallaHorizontal) 601.0 else 812.0
        return dpAltura*resultadoEscala.dp
    }

    internal fun ajustarAncho(dpAncho: Int): Dp {
        val resultadoEscala= anchoPantalla/if (anchoPantalla>600 && isPantallaHorizontal) 964.0 else 384.0
        return dpAncho*resultadoEscala.dp
    }

    internal fun ajustarFont(dpFont: Int): TextUnit{
        if (dpFontPantalla>1.2F) dpFontPantalla= 0.8F
        val resultadoEscala= dpFontPantalla/1.2
        return dpFont*resultadoEscala.sp
    }
}

@Composable
fun obtenerEstiloDisplay(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 18.sp
        configuration.screenWidthDp in 360..599 -> 20.sp
        configuration.screenWidthDp in 600..839 -> 22.sp
        else ->24.sp
    }
}

@Composable
fun obtenerEstiloHead(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 15.sp
        configuration.screenWidthDp in 360..599 -> 17.sp
        configuration.screenWidthDp in 600..839 -> 19.sp
        else -> 20.sp
    }
}

@Composable
fun obtenerEstiloTitle(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 12.sp
        configuration.screenWidthDp in 360..599 -> 15.sp
        configuration.screenWidthDp in 600..839 -> 17.sp
        else ->19.sp
    }
}

@Composable
fun obtenerEstiloBody(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 10.sp
        configuration.screenWidthDp in 360..599 -> 12.sp
        configuration.screenWidthDp in 600..839 -> 14.sp
        else ->16.sp
    }
}

@Composable
fun obtenerEstiloLabel(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 8.sp
        configuration.screenWidthDp in 360..599 -> 10.sp
        configuration.screenWidthDp in 600..839 -> 12.sp
        else ->14.sp
    }
}

