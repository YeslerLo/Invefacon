package com.soportereal.invefacon.interfaces.compact.pantallas_principales

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun IniciarInterfazSalir(navControllerPrincipal: NavController){


    Text(text = "Pantalla Salir", modifier = Modifier.fillMaxSize())
    Button(
        onClick = {
            navControllerPrincipal.popBackStack()
        }
    ) {
        Text("Salir")
    }

}