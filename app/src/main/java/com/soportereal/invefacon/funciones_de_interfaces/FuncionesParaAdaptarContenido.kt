package com.soportereal.invefacon.funciones_de_interfaces

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times


class FuncionesParaAdaptarContenido(
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
fun obtenerEstiloDisplayBig(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 26.sp
        configuration.screenWidthDp in 360..599 -> 27.sp
        configuration.screenWidthDp in 600..839 -> 30.sp
        else ->33.sp
    }
}

@Composable
fun obtenerEstiloDisplayMedium(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 25.sp
        configuration.screenWidthDp in 360..599 -> 26.sp
        configuration.screenWidthDp in 600..839 -> 29.sp
        else ->32.sp
    }
}

@Composable
fun obtenerEstiloDisplaySmall(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 24.sp
        configuration.screenWidthDp in 360..599 -> 25.sp
        configuration.screenWidthDp in 600..839 -> 28.sp
        else ->31.sp
    }
}

@Composable
fun obtenerEstiloHeadBig(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 22.sp
        configuration.screenWidthDp in 360..599 -> 23.sp
        configuration.screenWidthDp in 600..839 -> 26.sp
        else ->29.sp
    }
}

@Composable
fun obtenerEstiloHeadMedium(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 21.sp
        configuration.screenWidthDp in 360..599 -> 22.sp
        configuration.screenWidthDp in 600..839 -> 25.sp
        else ->28.sp
    }
}

@Composable
fun obtenerEstiloHeadSmall(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 20.sp
        configuration.screenWidthDp in 360..599 -> 21.sp
        configuration.screenWidthDp in 600..839 -> 24.sp
        else ->27.sp
    }
}

@Composable
fun obtenerEstiloTitleBig(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 18.sp
        configuration.screenWidthDp in 360..599 -> 19.sp
        configuration.screenWidthDp in 600..839 -> 22.sp
        else ->25.sp
    }
}
@Composable
fun obtenerEstiloTitleMedium(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 17.sp
        configuration.screenWidthDp in 360..599 -> 18.sp
        configuration.screenWidthDp in 600..839 -> 21.sp
        else ->24.sp
    }
}

@Composable
fun obtenerEstiloTitleSmall(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 16.sp
        configuration.screenWidthDp in 360..599 -> 17.sp
        configuration.screenWidthDp in 600..839 -> 20.sp
        else ->23.sp
    }
}

@Composable
fun obtenerEstiloBodyBig(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 14.sp
        configuration.screenWidthDp in 360..599 -> 15.sp
        configuration.screenWidthDp in 600..839 -> 18.sp
        else -> 21.sp
    }
}

@Composable
fun obtenerEstiloBodyMedium(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 13.sp
        configuration.screenWidthDp in 360..599 -> 14.sp
        configuration.screenWidthDp in 600..839 -> 17.sp
        else -> 20.sp
    }
}

@Composable
fun obtenerEstiloBodySmall(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 12.sp
        configuration.screenWidthDp in 360..599 -> 13.sp
        configuration.screenWidthDp in 600..839 -> 16.sp
        else ->19.sp
    }
}

@Composable
fun obtenerEstiloLabelBig(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 10.sp
        configuration.screenWidthDp in 360..599 -> 11.sp
        configuration.screenWidthDp in 600..839 -> 14.sp
        else ->17.sp
    }
}

@Composable
fun obtenerEstiloLabelMedium(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 9.sp
        configuration.screenWidthDp in 360..599 -> 10.sp
        configuration.screenWidthDp in 600..839 -> 13.sp
        else ->16.sp
    }
}

@Composable
fun obtenerEstiloLabelSmall(): TextUnit {
    val configuration = LocalConfiguration.current
    return when {
        configuration.screenWidthDp < 360 -> 8.sp
        configuration.screenWidthDp in 360..599 -> 9.sp
        configuration.screenWidthDp in 600..839 -> 12.sp
        else ->15.sp
    }
}

