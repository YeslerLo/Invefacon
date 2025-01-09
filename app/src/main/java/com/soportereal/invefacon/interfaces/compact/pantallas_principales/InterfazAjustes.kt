package com.soportereal.invefacon.interfaces.compact.pantallas_principales

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun IniciarInterfazAjustes(){
    val colorAzulDegradado = Brush.linearGradient(
        colors = listOf(Color(0xFF4D6CC7), Color(0xFF244BC0)), // Colores del degradado
        start = Offset(1000f, 500f), // Punto inicial del degradado
        end = Offset(500f, 500f) // Punto final del degradado
    )
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(Color.Transparent)

    Box(modifier = Modifier
        .background(colorAzulDegradado)
        .fillMaxSize()
        .statusBarsPadding()){
        Text("Ajsutes")
    }
}

@Composable
fun IniciarInterfazClientes(){

    Box(modifier = Modifier.background(Color.Green).fillMaxSize()){
        Text("Ajsutes")
    }
}