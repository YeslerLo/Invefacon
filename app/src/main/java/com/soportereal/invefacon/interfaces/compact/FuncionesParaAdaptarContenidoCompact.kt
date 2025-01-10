package com.soportereal.invefacon.interfaces.compact

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times


internal class FuncionesParaAdaptarContenidoCompact(
    val alturaPantalla: Int,
    val anchoPantalla: Int,
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

