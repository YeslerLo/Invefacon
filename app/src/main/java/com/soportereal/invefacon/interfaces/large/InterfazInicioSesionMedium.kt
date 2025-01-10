package com.soportereal.invefacon.interfaces.large

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.sp


@Composable
fun IniciarInterfazInicioSesionMedium(){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    println(dpAnchoPantalla)
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("En desarrollo...", fontSize = 30.sp)
    }
}