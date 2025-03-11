package com.soportereal.invefacon.interfaces.modulos.facturacion

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.separacionDeMiles
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.modulos.sac.ArticulosSeleccionadosSac
import com.soportereal.invefacon.interfaces.obtenerEstiloBody
import com.soportereal.invefacon.interfaces.obtenerEstiloHead
import com.soportereal.invefacon.interfaces.obtenerEstiloTitle

@Composable
fun IniciarInterfazFacturacion(
    token: String,
    systemUiController: SystemUiController?,
    navController: NavController,
    nombreEmpresa: String,
    codUsuario: String
){
    systemUiController?.setStatusBarColor(Color(0xFF244BC0))
    systemUiController?.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    var datosIngresadoBarraNombreCliente by remember { mutableStateOf("SN") }
    var expandedClientes by remember { mutableStateOf(false) }
    var expandedArticulos by remember { mutableStateOf(false) }
    var expandedTotales by remember { mutableStateOf(false) }
    var listaArticulos by remember {
        mutableStateOf(
            listOf(
                Articulo(precioUnitario = 9000000.00, descuento = 5.6, descripcion = "CARRO DE CARRERAS HOTWHEELS MATEL RRRS", cantidad = 1,total = 11000000.00),
                Articulo(precioUnitario = 1500.00, descuento = 0.00, descripcion = "COCA-COLA 3.5 L", cantidad = 2,total = 3000.00),
                Articulo(precioUnitario = 36500.00, descuento = 0.00, descripcion = "CONTROL PS5 WIRELLES COD 555979155665", cantidad = 1,total = 36500.00)
            )
        )
    }

    @Composable
    fun BasicTexfiuldWithText(
        text: String,
        icon: ImageVector,
        variable: String,
        nuevoValor: (String) -> Unit
    ){
        Column {
            TText(
                text = text,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = objetoAdaptardor.ajustarAncho(8)),
                color = Color.DarkGray
            )
            BBasicTextField(
                value = variable,
                onValueChange = {nuevoValor(it)},
                placeholder = text,
                utilizarMedidas = false,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(
                        start = objetoAdaptardor.ajustarAncho(4),
                        end = objetoAdaptardor.ajustarAncho(4),
                        top = objetoAdaptardor.ajustarAltura(2),
                        bottom = objetoAdaptardor.ajustarAltura(2)
                    ),
                objetoAdaptardor = objetoAdaptardor,
                icono = icon,
                iconTint = Color.Gray,
                textColor = Color.Black
            )
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (bxSuperior,flechaRegresar, lzColumPrincipal) = createRefs()
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(70))
                .background(Color(0xFF244BC0))
                .constrainAs(bxSuperior) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
            contentAlignment = Alignment.BottomCenter
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ) {
                Icon(
                    imageVector = Icons.Default.Description,
                    contentDescription = "Icono Facturación",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(45))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Facturación",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(28),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
        IconButton(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.constrainAs(flechaRegresar) {
                start.linkTo(parent.start)
                top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(16))
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(lzColumPrincipal){
                    start.linkTo(parent.start)
                    top.linkTo(bxSuperior.bottom)
                }
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter
                ){
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(objetoAdaptardor.ajustarAltura(8))
                    ){
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Top
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                            ) {
                                BButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                    text = "Abrir",
                                    onClick = {},
                                    textSize = obtenerEstiloTitle()
                                )
                                BButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                    text = "Guardar",
                                    onClick = {},
                                    textSize = obtenerEstiloTitle()
                                )
                                BButton(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(objetoAdaptardor.ajustarAltura(35)),
                                    text = "Clonar",
                                    onClick = {},
                                    textSize = obtenerEstiloTitle()
                                )
                            }
                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(objetoAdaptardor.ajustarAltura(8))
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(objetoAdaptardor.ajustarAltura(8))
                                ){
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(4))
                                    ) {
                                        TText(
                                            text = "Número Proforma: U00362U",
                                            modifier = Modifier.fillMaxWidth()
                                                .padding(objetoAdaptardor.ajustarAncho(8)),
                                            fontSize = obtenerEstiloHead()
                                        )
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.PersonPin,
                                                contentDescription = "ICONO DE CLIENTE",
                                                modifier = Modifier.padding(start = objetoAdaptardor.ajustarAncho(4))
                                            )
                                            TText(
                                                text = "Información del cliente",
                                                modifier = Modifier.fillMaxWidth()
                                                    .padding(start = objetoAdaptardor.ajustarAncho(4))
                                                , fontSize = obtenerEstiloHead()
                                            )
                                        }

                                        Row (
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                        ){
                                            TText(
                                                text = "Cuenta: 14",
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .padding(start = objetoAdaptardor.ajustarAncho(8))
                                                    .weight(1f)
                                            )
                                            BButton(
                                                text = "Opciones",
                                                onClick = {},
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(objetoAdaptardor.ajustarAltura(35)),
                                                textSize = obtenerEstiloTitle()
                                            )
                                            BButton(
                                                text = "Buscar",
                                                onClick = {},
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(objetoAdaptardor.ajustarAltura(35)),
                                                textSize = obtenerEstiloTitle()
                                            )
                                        }

                                        BasicTexfiuldWithText(
                                            text = "Nombre del Cliente:",
                                            variable = datosIngresadoBarraNombreCliente,
                                            nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                            icon = Icons.Default.Person
                                        )
                                        AnimatedVisibility(
                                            visible = expandedClientes,
                                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                            ) {
                                                BasicTexfiuldWithText(
                                                    text = "Tipo de Cédula:",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                                BasicTexfiuldWithText(
                                                    text = "Cédula:",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                                BasicTexfiuldWithText(
                                                    text = "Email General:",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                                BasicTexfiuldWithText(
                                                    text = "Nombre Comercial",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                                BasicTexfiuldWithText(
                                                    text = "Teléfonos:",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                                BasicTexfiuldWithText(
                                                    text = "Email General:",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                                BasicTexfiuldWithText(
                                                    text = "Plazo de Crédito en Dias:",
                                                    variable = datosIngresadoBarraNombreCliente,
                                                    nuevoValor = {datosIngresadoBarraNombreCliente=it},
                                                    icon = Icons.Default.Person
                                                )
                                            }
                                        }
                                        BButton(
                                            text =  if (expandedClientes) "Mostrar menos" else "Mostrar más",
                                            onClick = {expandedClientes = !expandedClientes},
                                            contenteColor = Color(0xFF244BC0),
                                            backgroundColor = Color.White,
                                            modifier = Modifier
                                                .height(objetoAdaptardor.ajustarAltura(35)),
                                            conSombra = false,
                                            textSize = obtenerEstiloTitle()
                                        )
                                    }
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(objetoAdaptardor.ajustarAltura(8))
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(objetoAdaptardor.ajustarAltura(16))
                                ){
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                    ){
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Inventory,
                                                contentDescription = "ICONO DE ARTICULOS"
                                            )
                                            TText(
                                                text = "Articulos",
                                                modifier = Modifier
                                                    .weight(1f),
                                                fontSize = obtenerEstiloHead()
                                            )
                                            BButton(
                                                text = "Agregar",
                                                onClick = {},
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(objetoAdaptardor.ajustarAltura(35)),
                                                textSize = obtenerEstiloTitle()
                                            )
                                            BButton(
                                                text = "Opciones",
                                                onClick = {},
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .height(objetoAdaptardor.ajustarAltura(35)),
                                                textSize = obtenerEstiloTitle()
                                            )
                                        }
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            modifier = Modifier
                                                .background(color = Color.LightGray)
                                        ){
                                            TText(
                                                text = "Descripción",
                                                modifier = Modifier
                                                    .weight(1f)
                                                    .padding(objetoAdaptardor.ajustarAltura(2)),
                                                fontSize = obtenerEstiloBody(),
                                                textAlign = TextAlign.Center
                                            )
                                            TText(
                                                text = "Precio.Unit",
                                                modifier = Modifier
                                                    .weight(0.9f)
                                                    .padding(objetoAdaptardor.ajustarAltura(2)),
                                                fontSize = obtenerEstiloBody(),
                                                textAlign = TextAlign.Center
                                            )
                                            TText(
                                                text = "Desc",
                                                modifier = Modifier
                                                    .weight(0.5f)
                                                    .padding(objetoAdaptardor.ajustarAltura(2)),
                                                fontSize = obtenerEstiloBody(),
                                                textAlign = TextAlign.Center
                                            )
                                            TText(
                                                text = "Total",
                                                modifier = Modifier
                                                    .weight(1.15f)
                                                    .padding(objetoAdaptardor.ajustarAltura(2)),
                                                fontSize = obtenerEstiloBody(),
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                        listaArticulos.forEach { articulo ->
                                            var isSeleccionado by remember { mutableStateOf(false) }
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ){
                                                Checkbox(
                                                    checked = isSeleccionado,
                                                    onCheckedChange = { isSeleccionado = it },
                                                    modifier = Modifier
                                                        .weight(0.1f)
                                                        .scale(0.7f)
                                                )
                                                TText(
                                                    text = "(${articulo.cantidad}) "+articulo.descripcion,
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 10
                                                )
                                                TText(
                                                    text = separacionDeMiles(articulo.precioUnitario),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 10
                                                )
                                                TText(
                                                    text = articulo.descuento.toString()+"%",
                                                    modifier = Modifier
                                                        .weight(0.6f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 10
                                                )
                                                TText(
                                                    text = separacionDeMiles(montoDouble = articulo.total),
                                                    modifier = Modifier
                                                        .weight(1f)
                                                        .padding(objetoAdaptardor.ajustarAltura(2)),
                                                    fontSize = obtenerEstiloBody(),
                                                    textAlign = TextAlign.Center,
                                                    maxLines = 10
                                                )
                                                IconButton(
                                                    onClick = {
                                                    },
                                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(18))
                                                ) {
                                                    Icon(
                                                        imageVector =  Icons.Filled.Delete,
                                                        contentDescription = "Basurero",
                                                        tint = Color.Red ,
                                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(18))
                                                    )
                                                }
                                            }
                                            HorizontalDivider(
                                                thickness = 1.dp,
                                                color = Color.LightGray
                                            )
                                        }
                                    }
                                }
                            }

                            Card(
                                modifier = Modifier
                                    .wrapContentHeight()
                                    .fillMaxWidth()
                                    .padding(objetoAdaptardor.ajustarAltura(8))
                                    .shadow(
                                        elevation = objetoAdaptardor.ajustarAltura(7),
                                        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                                    ),
                                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
                                colors = CardDefaults.cardColors(containerColor = Color.White)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(objetoAdaptardor.ajustarAltura(16))
                                ){
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
                                    ){
                                        TText(
                                            text = "Totales",
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier.fillMaxWidth(),
                                            fontSize = obtenerEstiloHead()
                                        )
                                        Row {
                                            TText(
                                                text = "Total Gravado:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f)
                                            )
                                            TText(
                                                text = separacionDeMiles(53450.00),
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        Row {
                                            TText(
                                                text = "Total IVA:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f)
                                            )
                                            TText(
                                                text =separacionDeMiles(1860.00),
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        Row {
                                            TText(
                                                text = "Total Descuento:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f)
                                            )
                                            TText(
                                                text =separacionDeMiles(2365.00),
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        AnimatedVisibility(
                                            visible = expandedTotales,
                                            enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                                            exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                                        ) {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                verticalArrangement = Arrangement.spacedBy(
                                                    objetoAdaptardor.ajustarAltura(8)
                                                )
                                            ) {
                                                Row {
                                                    TText(
                                                        text = "Pago:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    TText(
                                                        text = separacionDeMiles(13500.00),
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Total Exonerado:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    TText(
                                                        text = separacionDeMiles(),
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Total IVA Devuelto:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    TText(
                                                        text = separacionDeMiles(),
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                                Row {
                                                    TText(
                                                        text = "Total Merc Gravado:",
                                                        textAlign = TextAlign.Start,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    TText(
                                                        text =separacionDeMiles(1400.00),
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                }
                                                HorizontalDivider(
                                                    thickness = 1.dp,
                                                    color = Color.LightGray
                                                )
                                            }
                                        }
                                        Row {
                                            TText(
                                                text = "Total:",
                                                textAlign = TextAlign.Start,
                                                modifier = Modifier.weight(1f)
                                            )
                                            TText(
                                                text = "CRC",
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier.weight(1f)
                                            )
                                            TText(
                                                text =separacionDeMiles(68450.00),
                                                textAlign = TextAlign.End,
                                                modifier = Modifier.weight(1f)
                                            )
                                        }
                                        HorizontalDivider(
                                            thickness = 1.dp,
                                            color = Color.LightGray
                                        )
                                        BButton(
                                            text =  if (expandedTotales) "Mostrar menos" else "Mostrar más",
                                            onClick = {expandedTotales = !expandedTotales},
                                            contenteColor = Color(0xFF244BC0),
                                            backgroundColor = Color.White,
                                            modifier = Modifier
                                                .height(objetoAdaptardor.ajustarAltura(35)),
                                            conSombra = false,
                                            textSize = obtenerEstiloTitle()
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
            item {
                Box(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(70)))
            }
        }
    }
}

@Composable
@Preview
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazFacturacion("", null, nav, "","")
}