package com.soportereal.invefacon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.soportereal.invefacon.funciones_de_interfaces.NavegacionPantallas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            val navControllerPrincipal = rememberNavController()
            NavegacionPantallas(navcontroller = navControllerPrincipal)
        }
    }
}
