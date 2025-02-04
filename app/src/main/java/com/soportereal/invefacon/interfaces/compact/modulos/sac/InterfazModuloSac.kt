package com.soportereal.invefacon.interfaces.compact.modulos.sac

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.interfaces.compact.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.objetoEstadoPantallaCarga


@Composable
fun InterfazModuloSac(
    apiToken: String,
    navControllerPantallasModuloSac: NavController?,
    systemUiController: SystemUiController?,
    navControllerPantallasModulos: NavController?
){
    systemUiController?.setStatusBarColor(Color(0xFF244BC0))
    systemUiController?.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(objetoAdaptardor.ajustarAltura(622))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior)= createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(70))
                .background(Color(0xFF244BC0))
                .constrainAs(bxSuperior) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                },
            contentAlignment = Alignment.Center
        ){
            Row (
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ){
                Icon(
                    Icons.Default.RestaurantMenu,
                    contentDescription ="Icono SAC",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "SAC",
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(30),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(660)),
            contentAlignment = Alignment.Center
        ){
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Filled.Error, tint = Color.Red, contentDescription = "")
                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(20)))
                Text("Actualmente este módulo no se encuentra disponible para este dispositivo", textAlign = TextAlign.Center)
            }
        }
        objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
    }
}
@Composable
@Preview(showBackground = true, widthDp = 1280, heightDp = 660)
private fun Preview(){
    InterfazModuloSac("", null, null, null)
}
