package com.soportereal.invefacon.interfaces.pantallas_principales.ajustes

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.navigation.compose.rememberNavController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadBig
import com.soportereal.invefacon.funciones_de_interfaces.tienePermiso
import com.soportereal.invefacon.interfaces.pantallas_principales.ajustes.impresora.OpcionesInterfazAjsutes

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun IniciarInterfazAjustes(
    navController: NavController
){
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)


    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(objetoAdaptardor.ajustarAltura(622))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior, lzColumPrincipal)= createRefs()
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
                    Icons.Filled.Settings,
                    contentDescription ="Icono Ajustes",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Ajustes",
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = obtenerEstiloDisplayBig(),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = objetoAdaptardor.ajustarAltura(660))
                .constrainAs(lzColumPrincipal) {
                    start.linkTo(parent.start)
                    top.linkTo(bxSuperior.bottom)
                }
        ) {
            item {
                OpcionesInterfazAjsutes.opciones.forEach { opcion ->
                    Button(
                        onClick = {
                            if (opcion.permisoValidar.isNotEmpty()){
                                if (!tienePermiso(opcion.permisoValidar)){
                                    mostrarMensajeError("No cuenta con el permiso ${opcion.permisoValidar} para acceder al Ajuste de ${opcion.titulo}.")
                                    return@Button
                                }
                            }
                            navController.navigate(opcion.ruta)
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.White,
                            disabledContainerColor = Color.White,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(16))
                        ){
                            Icon(
                                imageVector = opcion.icono,
                                contentDescription = "",
                                tint = Color.Black,
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(35))
                            )
                            TText(
                                text = opcion.titulo,
                                fontSize = obtenerEstiloHeadBig(),
                                textAlign = TextAlign.Start,
                                maxLines = 2,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    HorizontalDivider()
                }
            }
        }

    }
}

@SuppressLint("ComposableNaming")
@Preview
@Composable
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazAjustes(nav)
}
