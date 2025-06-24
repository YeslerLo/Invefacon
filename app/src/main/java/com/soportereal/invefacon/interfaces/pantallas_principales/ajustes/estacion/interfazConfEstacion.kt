package com.soportereal.invefacon.interfaces.pantallas_principales.ajustes.estacion

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.PointOfSale
import androidx.compose.material.icons.filled.Print
import androidx.compose.material.icons.filled.Timer
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
import com.soportereal.invefacon.funciones_de_interfaces.BButton
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
import com.soportereal.invefacon.funciones_de_interfaces.ParClaveValor
import com.soportereal.invefacon.funciones_de_interfaces.ProcGenSocket
import com.soportereal.invefacon.funciones_de_interfaces.TText
import com.soportereal.invefacon.funciones_de_interfaces.TTextTitCuer
import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeExito
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloHeadBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleBig
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


@Composable
fun IniciarInterfazConfEstacion (
    navController: NavController,
    nombreEmpresa : String,
    token : String
) {
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla = configuration.fontScale
    val objetoAdaptardor =
        FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val context = LocalContext.current
    var codBodega by remember { mutableStateOf(obtenerParametroLocal(context, "bodega$nombreEmpresa")) }
    var datosTiempoReal by remember { mutableStateOf(obtenerParametroLocal(context, "datosTiempoReal$nombreEmpresa" ,"Si")) }
    var tipoPrecioVenta by remember { mutableStateOf( obtenerParametroLocal(context, "precioVenta$nombreEmpresa", valorPorDefecto = "1")) }
    val listaPrecios by remember {
        mutableStateOf(
            (1..10).map { numero -> ParClaveValor(numero.toString(), numero.toString()) }
        )
    }
    val listaBoleanos by remember {
        mutableStateOf(
          listOf(
              ParClaveValor(clave = "Si", valor = "Si")
          )
        )
    }
    val gestorProcGenSocket = ProcGenSocket()
    var impreFactura by remember { mutableStateOf(obtenerParametroLocal(context, "imprfactu$nombreEmpresa", valorPorDefecto = "Local")) }
    var impreProforma by remember { mutableStateOf(obtenerParametroLocal(context, "imprProf$nombreEmpresa", valorPorDefecto = "Local")) }
    var impreCredito by remember { mutableStateOf(obtenerParametroLocal(context, "imprCred$nombreEmpresa", valorPorDefecto = "Local")) }
    var impreRecibos by remember { mutableStateOf(obtenerParametroLocal(context, "imprRecib$nombreEmpresa", valorPorDefecto = "Local")) }
    var listaImpresoras by remember {  mutableStateOf<List<ParClaveValor>>(emptyList()) }
    var listaBodegas by remember {  mutableStateOf<List<ParClaveValor>>(emptyList()) }
    var socketJob by remember { mutableStateOf<Job?>(null) }
    val cortinaSocket= CoroutineScope(Dispatchers.IO)
    val objectoProcesadorDatosApi = ProcesarDatosEstacion(token)

    LaunchedEffect(Unit) {
        val listaImpresorasTemp = mutableListOf<ParClaveValor>()
        val listaBodegasTemp = mutableListOf<ParClaveValor>()
        listaImpresorasTemp.add(ParClaveValor("Local", "Local"))
        socketJob = cortinaSocket.launch {
            val result = objectoProcesadorDatosApi.obtenerBodegas()
            if (result== null) return@launch
            if (!validarExitoRestpuestaServidor(result)) return@launch
            val resultado = result.getJSONObject("resultado")
            val data = resultado.getJSONArray("data")
            for (i in 0 until data.length()){
                val bodega = data.getJSONObject(i)
                val codigo = bodega.getString("Cod_Bodega")
                val nombre = bodega.getString("Descripcion")
                listaBodegasTemp.add(ParClaveValor(clave = codigo, valor = "$codigo-$nombre"))
            }
            listaBodegas = listaBodegasTemp
            gestorProcGenSocket.obtenerImpresorasRemotas(
                context = context,
                datosRetornados = {
                    val listaTemp = it
                    listaTemp.forEach { impresora ->
                        listaImpresorasTemp.add(ParClaveValor(clave = impresora[0], valor = impresora[0]+"-"+impresora[1]))
                    }
                    listaImpresoras = listaImpresorasTemp
                },
                onErrorOrFin = {
                    socketJob?.cancel()
                }
            )
        }
    }


    @Composable
    fun BxOpcionesEstacion(
        titulo : String,
        icono : ImageVector,
        variable : String,
        opciones :  List<ParClaveValor> = emptyList(),
        onChange : (String) -> Unit
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
        ){
            Icon(
                imageVector = icono,
                contentDescription = null
            )
            TText(
                text = "$titulo: ",
                fontSize = obtenerEstiloTitleBig(),
                textAlign = TextAlign.Start
            )
            BBasicTextField(
                value = variable,
                onValueChange = {
                   onChange(it)
                },
                objetoAdaptardor = objetoAdaptardor,
                opciones = opciones,
                modifier = Modifier
                    .width(objetoAdaptardor.ajustarAncho(90))
                    .border(
                        1.dp,
                        color = Color.Gray,
                        RoundedCornerShape(
                            objetoAdaptardor.ajustarAltura(
                                10
                            )
                        )
                    ),
                backgroundColor = Color.White,
                utilizarMedidas = false,
                fontSize = obtenerEstiloTitleBig(),
                placeholder = "Código...",
                mostrarLeadingIcon = false,
                soloPermitirValoresNumericos = true
            )
        }
        HorizontalDivider()
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(objetoAdaptardor.ajustarAltura(622))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (bxSuperior, columPrincipal, flechaRegresar) = createRefs()
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
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(6))
            ) {
                Icon(
                    Icons.Filled.PointOfSale,
                    contentDescription = "Icono Ajustes",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "Estación",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = obtenerEstiloDisplayBig(),
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = objetoAdaptardor.ajustarAltura(660))
                .padding(objetoAdaptardor.ajustarAltura(16))
                .constrainAs(columPrincipal) {
                    start.linkTo(parent.start)
                    top.linkTo(bxSuperior.bottom)
                },
            verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(8))
        ) {
            TText(
                text = "Configuración de Estación",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = obtenerEstiloHeadBig()
            )
            HorizontalDivider(thickness = objetoAdaptardor.ajustarAltura(1),
                color = Color.LightGray
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAncho(8))
            ) {

                Icon(
                    imageVector = Icons.Filled.Business,
                    contentDescription = "",
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                )
                TTextTitCuer(
                    titulo = "Empresa:",
                    contenido = nombreEmpresa,
                    fontSize = obtenerEstiloTitleBig()
                )

                Spacer(modifier = Modifier.weight(1f))
                BButton(
                    text = "Guardar",
                    onClick = {
                        actualizarParametro(context, "datosTiempoReal$nombreEmpresa", datosTiempoReal)
                        actualizarParametro(context, "precioVenta$nombreEmpresa", tipoPrecioVenta)
                        actualizarParametro(context, "imprfactu$nombreEmpresa", impreFactura)
                        actualizarParametro(context, "imprProf$nombreEmpresa", impreProforma)
                        actualizarParametro(context, "imprCred$nombreEmpresa", impreCredito)
                        actualizarParametro(context, "imprRecib$nombreEmpresa", impreRecibos)
                        if (codBodega.isNotEmpty()){
                            actualizarParametro(context, "bodega$nombreEmpresa", codBodega)
                            mostrarMensajeExito("Configuración Guardada!")
                        }else{
                            mostrarMensajeError("Ingrese un Código de Bodega Válido.")
                        }

                    },
                    objetoAdaptardor = objetoAdaptardor,
                    textSize = obtenerEstiloBodyBig()
                )
            }

            HorizontalDivider(
                thickness = objetoAdaptardor.ajustarAltura(2),
                color = Color.Black
            )
            BxOpcionesEstacion(
                titulo = "Bodega de Inventario",
                icono = Icons.Filled.Inventory,
                opciones = listaBodegas,
                variable = codBodega,
                onChange = {codBodega = it}
            )
            BxOpcionesEstacion(
                titulo = "Tipo Precio Venta",
                icono = Icons.Filled.LocalOffer,
                variable = tipoPrecioVenta,
                opciones = listaPrecios,
                onChange = {tipoPrecioVenta = it}
            )
            BxOpcionesEstacion(
                titulo = "Mantener Datos Tiempo real",
                icono = Icons.Filled.Timer,
                variable = datosTiempoReal,
                opciones = listaBoleanos,
                onChange = {datosTiempoReal = it}
            )
            BxOpcionesEstacion(
                titulo = "Impresora Factura",
                icono =Icons.Filled.Print,
                variable = impreFactura,
                opciones = listaImpresoras,
                onChange = {impreFactura = it}
            )
            BxOpcionesEstacion(
                titulo = "Impresora Proforma",
                icono =Icons.Filled.Print,
                variable = impreProforma,
                opciones = listaImpresoras,
                onChange = {impreProforma= it}
            )
            BxOpcionesEstacion(
                titulo = "Impresora Crédito",
                icono =Icons.Filled.Print,
                variable = impreCredito,
                opciones = listaImpresoras,
                onChange = {impreCredito = it}
            )
            BxOpcionesEstacion(
                titulo = "Impresora Recibos",
                icono =Icons.Filled.Print,
                variable = impreRecibos,
                opciones = listaImpresoras,
                onChange = {impreRecibos = it}
            )
        }
    }
}

@Preview
@Composable
private fun Preview(){
    val nav = rememberNavController()
    IniciarInterfazConfEstacion(nav,"DEMOFERRE", "")
}
