package com.soportereal.invefacon
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.soportereal.invefacon.funciones_de_interfaces.NavegacionPantallas

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowCompat.getInsetsController(window, window.decorView).apply {
            isAppearanceLightNavigationBars = false // false = íconos claros para fondo oscuro
            isAppearanceLightStatusBars = false      // true = íconos oscuros para fondo claro
        }

        setContent {
            val navControllerPrincipal = rememberNavController()
            NavegacionPantallas(navcontroller = navControllerPrincipal)
        }
    }
}
