package com.soportereal.invefacon.interfaces.modulos.sac

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.systemuicontroller.SystemUiController
import com.soportereal.invefacon.R
import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametro
import com.soportereal.invefacon.interfaces.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.modulos.clientes.AgregarTextFieldMultifuncional
import com.soportereal.invefacon.interfaces.obtenerEstiloBody
import com.soportereal.invefacon.interfaces.obtenerEstiloDisplay
import com.soportereal.invefacon.interfaces.obtenerEstiloHead
import com.soportereal.invefacon.interfaces.obtenerEstiloLabel
import com.soportereal.invefacon.interfaces.obtenerEstiloTitle
import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.pantallas_principales.objetoEstadoPantallaCarga
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun InterfazSacComandaLarge(
    systemUiController: SystemUiController?,
    token: String,
    navControllerPantallasModuloSac: NavController?,
    nombreMesa: String,
    nombreEmpresa: String,
    codUsuario: String,
    salon: String
){
    systemUiController?.setStatusBarColor(Color(0xFF244BC0))
    systemUiController?.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objectoProcesadorDatosApi= ProcesarDatosModuloSac(token)
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, true)
    var datosIngresadosBarraBusqueda by rememberSaveable  { mutableStateOf("") }
    var montoTotal by rememberSaveable  {mutableDoubleStateOf(0.00) }
    var listaArticulosActuales by remember { mutableStateOf<List<ArticuloSac>>(emptyList()) }
    val lazyStateArticulos= rememberLazyListState()
    val lazyStateFamilias= rememberLazyListState()
    val lazyStateSubFamilias= rememberLazyListState()
    val lazyStateArticulosSeleccionados= rememberLazyListState()
    var listaFamiliasSac by remember { mutableStateOf<List<FamiliaSac>>(emptyList()) }
    var listaSubFamiliasSac by remember { mutableStateOf<List<SubFamiliaSac>>(emptyList()) }
    var familiaActualSeleccionada by remember { mutableStateOf(FamiliaSac()) }
    var subFamiliaActualSeleccionada by remember { mutableStateOf(SubFamiliaSac()) }
    var isCargandoArticulos by remember { mutableStateOf(true) }
    val articulosSeleccionados = remember { mutableStateListOf<ArticulosSeleccionadosSac>() }
    val articulosComboSeleccionados = remember { mutableStateListOf<ArticuloSacGrupo>() }
    var subCuentaSeleccionada by remember { mutableStateOf("") }
    val opcionesSubCuentas: MutableState<LinkedHashMap<String, String>> = remember { mutableStateOf(LinkedHashMap()) }
    var nombreNuevaSubCuenta by remember { mutableStateOf("") }
    var iniciarMenuAgregarSubCuenta by remember { mutableStateOf(false) }
    var articuloActualSeleccionado by remember { mutableStateOf(ArticuloSac()) }
    var iniciarVentanaAgregarArticulo by remember { mutableStateOf(false) }
    var actualizarMontos by remember { mutableStateOf(false) }
    var iniciarComandaSubCuenta by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val regresarPantallaAnterior by estadoRespuestaApi.estadoBtOk.collectAsState()
    var regresarPantalla by remember { mutableStateOf(false) }
    var iniciarVentanaAgregarCombo by remember { mutableStateOf(false) }
    val lazyStateArticulosCombo= rememberLazyListState()
    var precioTotalArticulo by remember { mutableDoubleStateOf(0.00) }
    var iniciarEdicionArticulo by remember { mutableStateOf(false) }
    var articuloActualSeleccionadoEdicion by remember { mutableStateOf(ArticulosSeleccionadosSac()) }
    val context = LocalContext.current
    val valorPrmImp2 by remember { mutableStateOf(obtenerParametro(context, "prmImp2")) }

    LaunchedEffect(actualizarMontos) {
        montoTotal= 0.00
        articulosSeleccionados.forEach{ articulo->
            if (articulo.subCuenta==subCuentaSeleccionada){
                montoTotal+= articulo.montoTotal
            }
        }
        montoTotal= BigDecimal(montoTotal)
            .setScale(2, RoundingMode.HALF_UP)
            .toDouble()
        actualizarMontos=false
    }

    LaunchedEffect(Unit) {
        estadoRespuestaApi.cambiarEstadoRespuestaApi()
        var result= objectoProcesadorDatosApi.obtenerListaFamilias()
        val listaFamilias = mutableListOf<FamiliaSac>()
        if (result!=null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            if(result.getString("status")=="ok"){
                val datosFamilias = result.getJSONArray("data")
                for(i in 0 until datosFamilias.length()){
                    val datoFamilia= datosFamilias.getJSONObject(i)
                    val familia= FamiliaSac(
                        nombre = datoFamilia.getString("Familia"),
                        codigo = datoFamilia.getString("Codigo"),
                        subFamilias = datoFamilia.getString("subfamilias")
                    )
                    listaFamilias.add(familia)
                }
                listaFamiliasSac=listaFamilias
                familiaActualSeleccionada= listaFamilias[0]
            }
        }

        result = objectoProcesadorDatosApi.obetenerSubCuentasMesa(nombreMesa)
        if (result!= null){
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
            if(result.getString("status")=="ok"){
                val data= result.getJSONObject("data")
                val subCuentas= data.getJSONArray("Subcuentas")
                for( i in 0 until subCuentas.length()){
                    opcionesSubCuentas.value[subCuentas.getString(i)] = subCuentas.getString(i)
                }
                subCuentaSeleccionada= opcionesSubCuentas.value.keys.first()
            }
        }
    }

    LaunchedEffect(familiaActualSeleccionada) {
        if(familiaActualSeleccionada.subFamilias.isNotEmpty()){
            val listaSubFamilias= mutableListOf<SubFamiliaSac>()
            val datosFamilias= JSONArray(familiaActualSeleccionada.subFamilias)
            for (i in 0 until datosFamilias.length()){
                val datosSubFamilia= datosFamilias.getJSONObject(i)
                val subFamilia= SubFamiliaSac(
                    nombre = datosSubFamilia.getString("subfamilia"),
                    codigo = datosSubFamilia.getString("cod_subfamilia")
                )
                listaSubFamilias.add(subFamilia)
            }
            listaSubFamiliasSac=listaSubFamilias
            subFamiliaActualSeleccionada= listaSubFamilias[0]
        }
    }

    LaunchedEffect(familiaActualSeleccionada, subFamiliaActualSeleccionada, datosIngresadosBarraBusqueda) {
        if (subFamiliaActualSeleccionada.codigo.isNotEmpty()){
            isCargandoArticulos=true
            val result= objectoProcesadorDatosApi.obtenerListaArticulos(
                codFamilia = if (datosIngresadosBarraBusqueda.isEmpty()) familiaActualSeleccionada.codigo else "",
                codSubFamilia =  if (datosIngresadosBarraBusqueda.isEmpty()) subFamiliaActualSeleccionada.codigo else "",
                datosBarraBusqueda = datosIngresadosBarraBusqueda
            )
            val listaArticulos = mutableListOf<ArticuloSac>()
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                if(result.getString("status")=="ok"){
                    val datosArticulosSac = result.getJSONArray("data")
                    for(i in 0 until datosArticulosSac.length()){
                        val datosArticulo= datosArticulosSac.getJSONObject(i)
                        println(datosArticulo)
                        val imp1 = datosArticulo.getDouble("imp1")

                        val precioUnitario = BigDecimal(datosArticulo.getDouble("precioArticulo"))
                            .setScale(2, RoundingMode.DOWN)
                            .toDouble()
                        val iva = BigDecimal(datosArticulo.getDouble("precioArticulo")*imp1/100)
                            .setScale(2, RoundingMode.DOWN)
                            .toDouble()
                        val impuestoServicio = BigDecimal(datosArticulo.getDouble("precioArticulo")*if(valorPrmImp2=="0")0.0 else 0.1)
                            .setScale(2, RoundingMode.DOWN)
                            .toDouble()

                        val precioFinal = BigDecimal(precioUnitario+iva+impuestoServicio)
                            .setScale(0, RoundingMode.HALF_UP)
                            .toDouble()

                        val datosGruposStr = datosArticulo.optString("datosGrupos", "[]")
                        val datosGrupos = JSONArray(datosGruposStr)

                        val listaGrupos = mutableListOf<SacGrupo>()
                        var cantidadArticulosObli = 0

                        for (a in 0 until datosGrupos.length()){
                            val datosGrupo= datosGrupos.getJSONObject(a)
                            val nombreGrupo = datosGrupo.getString("nombreGrupo")
                            val cantidadArticulos = datosGrupo.getInt("cantidadArticulos")
                            val articulosGrupoStr = datosGrupo.optString("articulosGrupo", "[]")
                            val articulosGrupo = JSONArray(articulosGrupoStr)

                            val listaArticulosGrupo = mutableListOf<ArticuloSacGrupo>()

                            for(e in 0 until articulosGrupo.length()){
                                val datosArticuloGrupo = articulosGrupo.getJSONObject(e)
                                val codigo = datosArticuloGrupo.getString("codigo")
                                val nombreArticulo = datosArticuloGrupo.getString("nombreArticulo")
                                val precio = datosArticuloGrupo.getDouble("precio")
                                val impuestoServicioArticulosGrupo = BigDecimal(precio*if(valorPrmImp2=="0")0.0 else 0.1)
                                    .setScale(2, RoundingMode.DOWN)
                                    .toDouble()
                                val imp13 = datosArticuloGrupo.getString("imp1")

                                val articuloGrupo = ArticuloSacGrupo(
                                    codigo = codigo,
                                    nombre = nombreArticulo,
                                    nombreGrupo = nombreGrupo,
                                    precio = precio+impuestoServicioArticulosGrupo,
                                    imp1 = imp13
                                )
                                listaArticulosGrupo.add(articuloGrupo)
                            }

                            val grupo = SacGrupo(
                                nombre = nombreGrupo,
                                cantidadItems = cantidadArticulos,
                                articulos = listaArticulosGrupo
                            )
                            cantidadArticulosObli+=cantidadArticulos
                            listaGrupos.add(grupo)
                        }

                        val articulo = ArticuloSac(
                            nombre = datosArticulo.getString("nombreArticulo"),
                            codigo = datosArticulo.getString("Cod_Articulo"),
                            precio = precioFinal,
                            listaGrupos = listaGrupos,
                            imp1 = imp1.toString(),
                            cantidadArticulosObli = cantidadArticulosObli
                        )
                        listaArticulos.add(articulo)
                    }
                }
            }
            listaArticulosActuales= listaArticulos
            delay(500)
            isCargandoArticulos=false
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        }

    }

    LaunchedEffect(iniciarComandaSubCuenta) {

        if (iniciarComandaSubCuenta && articulosSeleccionados.isEmpty()){
            val jsonObject = JSONObject("""
                    {
                        "code": 400,
                        "status": "error",
                        "data": "Agregue articulos para continuar"
                    }
                """
            )
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject )
            iniciarComandaSubCuenta=false
        }

        if(iniciarComandaSubCuenta && articulosSeleccionados.isNotEmpty()){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val jsonComandaDetalle = JSONArray()
            val articulosAEliminar = mutableListOf<ArticulosSeleccionadosSac>()
            articulosSeleccionados.forEach{articulo ->
                if (subCuentaSeleccionada==articulo.subCuenta){
                    val codigo=articulo.codigo
                    val cantidad=articulo.cantidad
                    val precio = articulo.precioUnitario
                    val imp1= articulo.imp1
                    val imp2 = if(valorPrmImp2=="0") "0.00" else "10.00"
                    val anotacion = articulo.anotacion
                    val subCuenta = articulo.subCuenta
                    val grupo = articulo.idGrupo
                    val isCombo = articulo.isCombo
                    val jsonObject = JSONObject().apply {
                        put("codigo", codigo)
                        put("cantidad", cantidad)
                        put("precio", precio)
                        put("imp1",imp1)
                        put("imp2", imp2)
                        put("anotacion", anotacion)
                        put("subcuenta", subCuenta)
                        put("grupo", grupo)
                        put("isCombo", isCombo)
                    }
                    println(jsonObject)
                    jsonComandaDetalle.put(jsonObject)

                    val articulosCombo = articulo.articulosCombo

                    articulosCombo.forEach { articuloCombo ->

                        val codigoAr=articuloCombo.codigo
                        val cantidadAr=articuloCombo.cantidad
                        val precioAr = articuloCombo.precio
                        val imp1Ar = articuloCombo.imp1
                        val imp2Ar = if(valorPrmImp2=="0") "0.00" else "10.00"
                        val anotacionAr = articulo.anotacion
                        val subCuentaAr = articulo.subCuenta
                        val grupoAr = articulo.idGrupo
                        val jsonObjectAr = JSONObject().apply {
                            put("codigo", codigoAr)
                            put("cantidad", cantidadAr)
                            put("precio", precioAr)
                            put("imp1",imp1Ar)
                            put("imp2", imp2Ar)
                            put("anotacion", anotacionAr)
                            put("subcuenta", subCuentaAr)
                            put("grupo", grupoAr)
                            put("isCombo", isCombo)
                        }
                        println(jsonObjectAr)
                        jsonComandaDetalle.put(jsonObjectAr)
                    }
                    articulosAEliminar.add(articulo)
                }
            }

            if (jsonComandaDetalle.length()>0){
                opcionesSubCuentas.value = LinkedHashMap(opcionesSubCuentas.value).apply {
                    remove(subCuentaSeleccionada)
                }
                articulosSeleccionados.removeAll(articulosAEliminar)
                val result= objectoProcesadorDatosApi.comandarSubCuentaEliminarArticulos(
                    codUsuario = codUsuario,
                    salon = salon,
                    mesa = nombreMesa,
                    jsonComandaDetalle = jsonComandaDetalle
                )
                if (result != null) {
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result )
                }
            }else{
                val jsonObject = JSONObject("""
                    {
                        "code": 400,
                        "status": "error",
                        "data": "Agregue articulos a la sub-cuenta"
                    }
                """
                )
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject )
            }
            delay(100)
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            delay(2000)
            estadoRespuestaApi.cambiarEstadoRespuestaApi(regresarPantallaAnterior=true)
            println(regresarPantallaAnterior)
            iniciarComandaSubCuenta= false
        }
    }

    LaunchedEffect(regresarPantallaAnterior) {
        if (regresarPantallaAnterior && opcionesSubCuentas.value.isEmpty() && listaArticulosActuales.isNotEmpty()){
            navControllerPantallasModuloSac?.popBackStack()
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }

        if (regresarPantallaAnterior && opcionesSubCuentas.value.isNotEmpty()  && listaArticulosActuales.isNotEmpty()){
            subCuentaSeleccionada= opcionesSubCuentas.value.keys.first()
            actualizarMontos=true
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    LaunchedEffect(regresarPantalla) {
        if (regresarPantalla){
            navControllerPantallasModuloSac?.popBackStack()
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    fun agregarOActualizarProducto(nuevoProducto: ArticulosSeleccionadosSac, isEditar: Boolean= false) {

        // Buscar un producto existente en la lista por su 'codigo'
        val productoExistente = articulosSeleccionados.find { it.codigo == nuevoProducto.codigo && (isEditar || it.subCuenta== nuevoProducto.subCuenta) && (nuevoProducto.isCombo != 1 || it.idGrupo == nuevoProducto.idGrupo)}
        val index = articulosSeleccionados.indexOfFirst { it.codigo == nuevoProducto.codigo && (isEditar || it.subCuenta== nuevoProducto.subCuenta) && (nuevoProducto.isCombo != 1 || it.idGrupo == nuevoProducto.idGrupo)}

        if (productoExistente != null) {
            if (isEditar){
                productoExistente.cantidad = nuevoProducto.cantidad
            }else{
                productoExistente.cantidad += nuevoProducto.cantidad
            }
            productoExistente.anotacion = nuevoProducto.anotacion
            productoExistente.subCuenta = nuevoProducto.subCuenta


            // Si la cantidad es cero o negativa, eliminar el producto de la lista
            if (productoExistente.cantidad <= 0) {
                articulosSeleccionados.remove(productoExistente)
            } else {
                productoExistente.calcularMontoTotal()
            }
        } else {
            // Si el producto no existe, agregarlo a la lista
            nuevoProducto.calcularMontoTotal()
            articulosSeleccionados.add(nuevoProducto)
        }

        // Refrescar la lista de productos seleccionados (esto asegura que la UI se actualice correctamente)
        val productos = articulosSeleccionados.toList()
        articulosSeleccionados.clear()
        articulosSeleccionados.addAll(productos)
        coroutineScope.launch {
            lazyStateArticulosSeleccionados.animateScrollToItem(if (index!=-1)index else articulosSeleccionados.size)
        }
        actualizarMontos=true
    }

    fun agregarOActualizarArticuloCombo(cantidad: Int =0, articulo: ArticuloSacGrupo) {

        // Buscar un producto existente en la lista por su 'codigo'
        val productoExistente = articulosComboSeleccionados.find { it.codigo==articulo.codigo && it.idGrupo==articulo.idGrupo}

        if (productoExistente != null) {
            // Si el producto ya existe, actualizar su cantidad
            productoExistente.cantidad += cantidad
            precioTotalArticulo+= articulo.precio*cantidad
        } else {
            // Si el producto no existe, agregarlo a la lista
            articulosComboSeleccionados.add(articulo)
        }
    }

    @Composable
    fun AgregarBxContendorArticuloAgregado(
        articulo: ArticulosSeleccionadosSac
    ){
        Box(
            modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(260))
                .background(Color(0xFFF6F6F6))
                .clickable {
                    if (articulo.isCombo == 0) {
                        articuloActualSeleccionadoEdicion = articulo
                        iniciarEdicionArticulo = true
                    }
                }
        ){
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    Text(
                        articulo.nombre,
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = obtenerEstiloBody(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(165))
                            .padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))
                    Box{
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            IconButton(
                                onClick = {
                                    val articuloSeleccionado = ArticulosSeleccionadosSac(
                                        nombre = articulo.nombre,
                                        codigo = articulo.codigo,
                                        precioUnitario = articulo.precioUnitario,
                                        cantidad = -1,
                                        subCuenta = articulo.subCuenta,
                                        idGrupo = articulo.idGrupo,
                                        articulosCombo = articulo.articulosCombo
                                    )
                                    agregarOActualizarProducto(articuloSeleccionado)
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                            ) {
                                Icon(
                                    imageVector = if(articulo.cantidad==1) Icons.Filled.Delete else Icons.Filled.RemoveCircle,
                                    contentDescription = "Basurero",
                                    tint = if(articulo.cantidad==1)Color.Red else Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                )
                            }

                            Text(
                                articulo.cantidad.toString(),
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloLabel(),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(objetoAdaptardor.ajustarAncho(35))
                                    .padding(2.dp)
                            )
                            if (articulo.articulosCombo.isEmpty()){
                                IconButton(
                                    onClick = {
                                        val articuloSeleccionado = ArticulosSeleccionadosSac(
                                            nombre = articulo.nombre,
                                            codigo = articulo.codigo,
                                            precioUnitario = articulo.precioUnitario,
                                            cantidad = 1,
                                            subCuenta = articulo.subCuenta,
                                            idGrupo = articulo.idGrupo,
                                            articulosCombo = articulo.articulosCombo
                                        )
                                        agregarOActualizarProducto(articuloSeleccionado)
                                    },
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "Basurero",
                                        tint = Color.Black,
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                }
                HorizontalDivider()
                for (i in 0 until articulo.articulosCombo.size){
                    val articuloCombo = articulo.articulosCombo[i]
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(10)))
                        Text(
                            articuloCombo.cantidad.toString(),
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            fontSize = obtenerEstiloLabel(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(20)).padding(2.dp),
                            color = Color.DarkGray
                        )
                        Text(
                            articuloCombo.nombreGrupo+": "+articuloCombo.nombre,
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            fontSize = obtenerEstiloLabel(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(140)).padding(1.dp),
                            color = Color.DarkGray
                        )
                        Text(
                            "+ \u20A1 "+String.format(Locale.US, "%,.2f", "${articuloCombo.precio*articuloCombo.cantidad}".replace(",", "").toDouble()),
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            fontSize = obtenerEstiloLabel(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(130)).padding(1.dp)
                        )
                    }
                    HorizontalDivider()
                }
                Row {
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    Text(
                        articulo.anotacion,
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloLabel(),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(130))
                            .padding(2.dp),
                        color = Color.DarkGray
                    )
                    Text(
                        "\u20A1 "+String.format(Locale.US, "%,.2f", articulo.montoTotal.toString().replace(",", "").toDouble()),
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(130))
                            .padding(2.dp)
                    )
                }
            }
        }
    }

    @Composable
    fun AgregarBxContenedorArticulos(
        articulo: ArticuloSac
    ){
        Card(
            modifier = Modifier
                .height(objetoAdaptardor.ajustarAltura(130))
                .width(objetoAdaptardor.ajustarAncho(123))
                .let {
                    if (articulo.listaGrupos.isEmpty()) {
                        it.pointerInput(Unit) {
                            detectTapGestures(
                                onTap = {
                                    articuloActualSeleccionado = articulo
                                    iniciarVentanaAgregarArticulo = true
                                },
                                onDoubleTap = {
                                    val formatter = SimpleDateFormat("HHmmssSSS", Locale.getDefault())
                                    val hora = formatter.format(Date())
                                    val articuloSeleccionado = ArticulosSeleccionadosSac(
                                        nombre = articulo.nombre,
                                        codigo = articulo.codigo,
                                        precioUnitario = articulo.precio,
                                        cantidad = 1,
                                        anotacion = "",
                                        subCuenta = subCuentaSeleccionada,
                                        idGrupo = hora,
                                        imp1 = articulo.imp1
                                    )
                                    agregarOActualizarProducto(articuloSeleccionado)
                                }
                            )
                        }
                    } else {
                        it.clickable {
                            articuloActualSeleccionado = articulo
                            iniciarVentanaAgregarCombo = true
                        }
                    }
                }
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(7),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16))
                ),
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .height(objetoAdaptardor.ajustarAltura(130))
                        .width(objetoAdaptardor.ajustarAncho(123)),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    SubcomposeAsyncImage(
                        model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articulo.codigo}.png",
                        contentDescription = "Imagen Articulo",
                        modifier = Modifier
                            .fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        loading = {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                CircularProgressIndicator(
                                    color = Color(0xFF244BC0),
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                                )
                            }
                        },
                        error = {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(),
                                painter = painterResource(id = R.drawable.sin_imagen),
                                contentDescription = "Descripción de la imagen",
                                contentScale = ContentScale.FillBounds
                            )
                        }
                    )
                    Column(
                        verticalArrangement = Arrangement.Bottom,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(133))
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(6)))
                            Text(
                                articulo.nombre,
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Light,
                                fontSize = obtenerEstiloLabel(),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.White,
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100))
                            )
                        }

                        // Espaciador con weight para adaptarse dinámicamente
                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(133))
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "\u20A1 "+String.format(Locale.US, "%,.2f", articulo.precio.toString().replace(",", "").toDouble()),
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Light,
                                fontSize = obtenerEstiloBody(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AgregarBxContenedorArticulosCombo(
        grupo: SacGrupo
    ){
        var actualizarArticulos by remember { mutableStateOf(true) }
        var itemsRestantes by remember { mutableIntStateOf(grupo.cantidadItems) }
        LaunchedEffect(actualizarArticulos) {
            if (iniciarVentanaAgregarCombo && actualizarArticulos){
                for(i in 0 until grupo.articulos.size){
                    val articulo = grupo.articulos[i]
                    val nuevoArticulo= ArticuloSacGrupo(
                        nombre = articulo.nombre,
                        nombreGrupo = articulo.nombreGrupo,
                        idGrupo = articulo.idGrupo,
                        codigo = articulo.codigo,
                        cantidad = 0,
                        precio = articulo.precio,
                        imp1 = articulo.imp1
                    )
                    agregarOActualizarArticuloCombo(
                        articulo = nuevoArticulo
                    )
                }
                actualizarArticulos = false
            }
        }


        Card(
            modifier = Modifier
                .wrapContentHeight()
                .width(objetoAdaptardor.ajustarAncho(450))
                .padding(objetoAdaptardor.ajustarAltura(8))
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(7),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16))
                ),
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                Row {
                    Text(
                        "${grupo.nombre} (${grupo.cantidadItems})",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloHead(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        color = Color.Black,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(300))
                            .padding(objetoAdaptardor.ajustarAltura(8))
                    )
                    Text(
                        "Faltan: $itemsRestantes",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        color = Color.Black,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(110))
                            .padding(objetoAdaptardor.ajustarAltura(8))
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Black
                )

                for(i in 0 until articulosComboSeleccionados.size){
                    val articulo = articulosComboSeleccionados[i]
                    if(articulo.nombreGrupo==grupo.nombre){
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(30)))
                            Text(
                                articulo.nombre,
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Light,
                                fontSize = obtenerEstiloLabel(),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Start,
                                color = Color.Black,
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200))
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(16)))
                                Text(
                                    "\u20A1 "+String.format(Locale.US, "%,.2f", "${articulo.precio*articulo.cantidad}".replace(",", "").toDouble()),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = obtenerEstiloLabel(),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start,
                                    color = Color.Black,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100)).padding(6.dp)
                                )
                                IconButton(
                                    onClick = {
                                        if (itemsRestantes>=0 && articulo.cantidad>0){
                                            agregarOActualizarArticuloCombo(
                                                cantidad = -1,
                                                articulo = articulo
                                            )
                                            itemsRestantes+=1
                                        }
                                    },
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.RemoveCircle,
                                        contentDescription = "Basurero",
                                        tint =  if (itemsRestantes>=0 && articulo.cantidad>0) Color.Black else Color.White,
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                    )
                                }
                                Text(
                                    articulo.cantidad.toString(),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloLabel(),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(35))
                                        .padding(2.dp)
                                )

                                IconButton(
                                    onClick = {
                                        if (itemsRestantes>0){
                                            agregarOActualizarArticuloCombo(
                                                cantidad = 1,
                                                articulo = articulo
                                            )
                                            itemsRestantes-=1
                                        }
                                    },
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "Basurero",
                                        tint =  if (itemsRestantes>0) Color.Black else Color.White,
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
                                    )
                                }
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(16)))
                            }
                        }
                        HorizontalDivider()
                    }
                }
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        val (bxSuperior, bxContenedorArticulos, txfBarraBusqueda,
            bxContenerdorArticulosSeleccionados,
            bxContenedorFamilias, bxContenedorSubFamilias,flechaRegresar) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(objetoAdaptardor.ajustarAltura(50))
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
                    Icons.Default.RestaurantMenu,
                    contentDescription = "Icono SAC",
                    tint = Color.White,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                )
                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                Text(
                    "SAC $nombreMesa-$salon",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = obtenerEstiloDisplay(),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        IconButton(
            onClick = {regresarPantalla=true},
            modifier = Modifier
                .constrainAs(flechaRegresar) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(20))
                    top.linkTo(parent.top, margin = objetoAdaptardor.ajustarAltura(12))
                }
                .size(objetoAdaptardor.ajustarAltura(30))

        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
            )
        }

        BasicTextField(
            value = datosIngresadosBarraBusqueda,
            onValueChange = { nuevoValor ->
                datosIngresadosBarraBusqueda = nuevoValor
            },
            singleLine = true,
            textStyle = TextStyle(
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(250))
                        .height(objetoAdaptardor.ajustarAltura(45))
                        .background(
                            Color.LightGray,
                            RoundedCornerShape(objetoAdaptardor.ajustarAltura(18))
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Filled.Search,
                            contentDescription = "Icono Buscar",
                            tint= Color.DarkGray,
                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(modifier = Modifier.weight(1f)) {
                            if (datosIngresadosBarraBusqueda.isEmpty()) {
                                Text(
                                    text = "Buscar...",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    color = Color.Gray,
                                    maxLines = 1
                                )
                            }
                            innerTextField()
                        }
                    }
                }
            }, modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(200))
                .height(objetoAdaptardor.ajustarAltura(45))
                .constrainAs(txfBarraBusqueda) {
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
                }
        )

        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(45))
                .width(objetoAdaptardor.ajustarAncho(470))
                .constrainAs(bxContenedorFamilias) {
                    start.linkTo(txfBarraBusqueda.end, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                }
            , contentAlignment = Alignment.CenterStart
        ){
            LazyRow(
                state = lazyStateFamilias
            ){
                item { Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6))) }
                items(listaFamiliasSac) { familia ->
                    Button(
                        modifier = Modifier.height(objetoAdaptardor.ajustarAltura(35)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (familiaActualSeleccionada.nombre==familia.nombre) Color(0xFFBFBFBF) else Color(0xFFEAEAEA), // Color de fondo del botón
                            contentColor = Color.White,
                            disabledContainerColor = if (familiaActualSeleccionada.nombre==familia.nombre) Color(0xFFBFBFBF) else Color(0xFFEAEAEA),
                            disabledContentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 7.dp),
                        onClick = {
                            datosIngresadosBarraBusqueda = ""
                            familiaActualSeleccionada= familia
                        },
                        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 0.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(
                                familia.nombre,
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloBody(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(40))
                .width(objetoAdaptardor.ajustarAncho(675))
                .constrainAs(bxContenedorSubFamilias) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(txfBarraBusqueda.bottom, margin = objetoAdaptardor.ajustarAltura(6))
                }
        ){
            LazyRow(
                state = lazyStateSubFamilias
            ){
                item { Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6))) }
                items(listaSubFamiliasSac) { subFamilia ->
                    Button(
                        modifier = Modifier.height(objetoAdaptardor.ajustarAltura(35)),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (subFamiliaActualSeleccionada.nombre==subFamilia.nombre) Color(0xFFBFBFBF) else Color(0xFFEAEAEA), // Color de fondo del botón
                            contentColor = Color.White,
                            disabledContainerColor = if (subFamiliaActualSeleccionada.nombre==subFamilia.nombre) Color(0xFFBFBFBF) else Color(0xFFEAEAEA),
                            disabledContentColor = Color.White
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 7.dp),
                        onClick = {
                            datosIngresadosBarraBusqueda = ""
                            subFamiliaActualSeleccionada= subFamilia
                        },
                        contentPadding = PaddingValues(start = 12.dp, end = 12.dp, top = 0.dp, bottom = 0.dp)
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                            Text(
                                subFamilia.nombre,
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloBody(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(675))
                .height(objetoAdaptardor.ajustarAltura(465))
                .constrainAs(bxContenedorArticulos) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(
                        bxContenedorSubFamilias.bottom,
                        margin = objetoAdaptardor.ajustarAltura(7)
                    )
                },
            contentAlignment = Alignment.TopStart
        ){
            if (isCargandoArticulos) {
                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator(
                        color = Color(0xFF244BC0),
                        modifier = Modifier
                            .size(objetoAdaptardor.ajustarAltura(50))
                            .padding(2.dp)
                    )
                }
            }else{
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(16)),
                    state = lazyStateArticulos,
                ) {
                    items(listaArticulosActuales.chunked(5)) { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            rowItems.forEach { articulo ->
                                AgregarBxContenedorArticulos(articulo)
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(12)))
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16)))}
                }
            }
        }

        Card(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(260))
                .height(objetoAdaptardor.ajustarAltura(530))
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(2),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                )
                .constrainAs(bxContenerdorArticulosSeleccionados) {
                    start.linkTo(
                        bxContenedorArticulos.end,
                        margin = objetoAdaptardor.ajustarAncho(8)
                    )
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
        ){
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Box(modifier = Modifier
                    .background(Color(0xFFF6F6F6))
                    .width(objetoAdaptardor.ajustarAncho(260))
                    .height(objetoAdaptardor.ajustarAltura(445)),
                    contentAlignment = Alignment.TopCenter
                ){
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ){
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AgregarTextFieldMultifuncional(
                                    label = "Sub-Cuentas",
                                    opciones2 = opcionesSubCuentas,
                                    usarOpciones2 = true,
                                    contieneOpciones = true,
                                    nuevoValor = {
                                        nuevoValor-> subCuentaSeleccionada=nuevoValor
                                        actualizarMontos=true
                                    },
                                    valor = subCuentaSeleccionada,
                                    isUltimo = true,
                                    tomarAnchoMaximo = false,
                                    medidaAncho = 70
                                )
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                IconButton(
                                    onClick = {
                                        iniciarMenuAgregarSubCuenta=true
                                    }
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.AddCircle,
                                        contentDescription = "Agregar Sub Cuenta",
                                        tint = Color(0xFF244BC0)
                                    )
                                }
                            }
                        }

                        LazyColumn(
                            state = lazyStateArticulosSeleccionados
                        ) {
                            items(articulosSeleccionados){ producto->
                                if (producto.subCuenta==subCuentaSeleccionada){
                                    AgregarBxContendorArticuloAgregado(producto)
                                    HorizontalDivider(
                                        thickness = 2.dp,
                                        color = Color.Black
                                    )
                                }
                            }
                        }
                    }

                }
                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(2)))

                Box(
                    modifier = Modifier
                        .background(Color(0xFFFAFAFA))
                        .width(objetoAdaptardor.ajustarAncho(260))
                        .wrapContentHeight(),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AgregarBxContenerdorMontosCuenta(
                            nombreCampo = "Total",
                            monto = montoTotal
                        )
                        Button(
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(45))
                                .width(objetoAdaptardor.ajustarAncho(255)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF244BC0), // Color de fondo del botón
                                contentColor = Color.White,
                                disabledContainerColor = Color(0xFF244BC0),
                                disabledContentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                            onClick = {
                                iniciarComandaSubCuenta=true
                            },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                "Comandar",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloBody(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }

    if(iniciarMenuAgregarSubCuenta) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Agregar Nueva Sub-Cuenta",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = obtenerEstiloHead(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box{
                    Column {
                        BasicTextField(
                            value = nombreNuevaSubCuenta,
                            onValueChange = { nuevoValor ->
                                nombreNuevaSubCuenta = nuevoValor
                            },
                            singleLine = true,
                            textStyle = TextStyle(
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Light,
                                color = Color.Black,
                                textAlign = TextAlign.Justify
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            decorationBox = { innerTextField ->
                                Box(
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(300))
                                        .height(objetoAdaptardor.ajustarAltura(70))
                                        .background(
                                            Color.LightGray,
                                            RoundedCornerShape(objetoAdaptardor.ajustarAltura(18))
                                        )
                                        .padding(horizontal = 16.dp, vertical = 4.dp),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AccountTree,
                                            contentDescription = "Icono Buscar",
                                            tint = Color.DarkGray,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Box(modifier = Modifier.weight(1f)) {
                                            if (nombreNuevaSubCuenta.isEmpty()) {
                                                Text(
                                                    "Nombre de la Sub-Cuenta",
                                                    fontFamily = fontAksharPrincipal,
                                                    fontWeight = FontWeight.Light,
                                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                            innerTextField()
                                        }
                                    }
                                }
                            }, modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70))
                        )
                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (nombreNuevaSubCuenta.isEmpty()){
                            val jsonObject = JSONObject("""
                                {
                                    "code": 400,
                                    "status": "error",
                                   "data": "Ingrese el nombre de la Sub-Cuenta"
                                }
                                """
                            )
                            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
                        }else{
                            val nuevoMapa = LinkedHashMap<String, String>()
                            nuevoMapa[nombreNuevaSubCuenta] = nombreNuevaSubCuenta
                            nuevoMapa.putAll(opcionesSubCuentas.value)
                            opcionesSubCuentas.value = nuevoMapa
                            val jsonObject = JSONObject("""
                                {
                                    "code": 200,
                                    "status": "ok",
                                    "data": "Sub-Cuenta creada"
                                }
                                """
                            )
                            iniciarMenuAgregarSubCuenta=false
                            nombreNuevaSubCuenta=""
                            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Crear",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = objetoAdaptardor.ajustarFont(15),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        iniciarMenuAgregarSubCuenta=false
                        nombreNuevaSubCuenta=""
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color.Red,
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Cancelar",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = objetoAdaptardor.ajustarFont(15),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        )
    }

    if (iniciarVentanaAgregarArticulo || iniciarEdicionArticulo){
        var cantidadArticulos by remember { mutableIntStateOf(if(iniciarVentanaAgregarArticulo) 1 else articuloActualSeleccionadoEdicion.cantidad) }
        var anotacion by remember { mutableStateOf(if(iniciarVentanaAgregarArticulo) "" else articuloActualSeleccionadoEdicion.anotacion) }

        LaunchedEffect(cantidadArticulos) {
            precioTotalArticulo=if(iniciarVentanaAgregarArticulo) articuloActualSeleccionado.precio*cantidadArticulos.toDouble() else articuloActualSeleccionadoEdicion.precioUnitario*cantidadArticulos.toDouble()
        }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(24)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            if(iniciarVentanaAgregarArticulo) "Agregar Articulo" else "Editar Articulo",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloHead(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                        Box(
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(123))
                                .width(objetoAdaptardor.ajustarAncho(123)),
                            contentAlignment = Alignment.BottomCenter
                        ) {
                            SubcomposeAsyncImage(
                                model = "https://invefacon.com/img/demorest/articulos/${if(iniciarVentanaAgregarArticulo) articuloActualSeleccionado.codigo else articuloActualSeleccionadoEdicion.codigo}.png",
                                contentDescription = "Imagen Articulo",
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.FillBounds,
                                loading = {
                                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                        CircularProgressIndicator(
                                            color = Color(0xFF244BC0),
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                                        )
                                    }
                                },
                                error = {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        painter = painterResource(id = R.drawable.sin_imagen),
                                        contentDescription = "Descripción de la imagen",
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            )
                        }
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        Text(
                            if(iniciarVentanaAgregarArticulo) articuloActualSeleccionado.nombre else articuloActualSeleccionadoEdicion.nombre ,
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloTitle(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "\u20A1 "+String.format(Locale.US, "%,.2f", precioTotalArticulo.toString().replace(",", "").toDouble()),
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloBody(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )

                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                            if(cantidadArticulos>1){
                                IconButton(
                                    onClick = {
                                        cantidadArticulos-= if(cantidadArticulos==1) 0 else 1
                                    },
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.RemoveCircle,
                                        contentDescription = "Basurero",
                                        tint = if(cantidadArticulos==1)Color.Red else Color.Black,
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                    )
                                }
                            }


                            Text(
                                cantidadArticulos.toString(),
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloLabel(),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .width(objetoAdaptardor.ajustarAncho(35))
                                    .padding(2.dp)
                            )
                            IconButton(
                                onClick = {
                                    cantidadArticulos+=1
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = "Basurero",
                                    tint = Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            AgregarTextFieldMultifuncional(
                                label = "Sub-Cuenta",
                                opciones2 = opcionesSubCuentas,
                                usarOpciones2 = true,
                                contieneOpciones = true,
                                nuevoValor = {nuevoValor-> subCuentaSeleccionada=nuevoValor},
                                valor = subCuentaSeleccionada,
                                isUltimo = true,
                                tomarAnchoMaximo = false,
                                medidaAncho = 70
                            )
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                            IconButton(
                                onClick = {
                                    iniciarMenuAgregarSubCuenta=true
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = "Agregar Sub Cuenta",
                                    tint = Color(0xFF244BC0)
                                )
                            }
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                            BasicTextField(
                                value = anotacion,
                                onValueChange = { nuevoValor ->
                                    anotacion = nuevoValor
                                },
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    color = Color.Black,
                                    textAlign = TextAlign.Justify
                                ),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                decorationBox = { innerTextField ->
                                    Box(
                                        modifier = Modifier
                                            .width(objetoAdaptardor.ajustarAncho(300))
                                            .height(objetoAdaptardor.ajustarAltura(70))
                                            .background(
                                                Color.LightGray,
                                                RoundedCornerShape(objetoAdaptardor.ajustarAltura(18))
                                            )
                                            .padding(horizontal = 16.dp, vertical = 4.dp),
                                        contentAlignment = Alignment.CenterStart
                                    ) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.EditNote,
                                                contentDescription = "Icono Buscar",
                                                tint = Color.DarkGray,
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                            )
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Box(modifier = Modifier.weight(1f)) {
                                                if (anotacion.isEmpty()) {
                                                    Text(
                                                        "Anotación",
                                                        fontFamily = fontAksharPrincipal,
                                                        fontWeight = FontWeight.Light,
                                                        fontSize = objetoAdaptardor.ajustarFont(16),
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis
                                                    )
                                                }
                                                innerTextField()
                                            }
                                        }
                                    }
                                }, modifier = Modifier
                                    .width(objetoAdaptardor.ajustarAncho(200))
                                    .height(objetoAdaptardor.ajustarAltura(50))
                            )
                        }

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        Row {
                            Button(
                                onClick = {
                                    val formatter = SimpleDateFormat("HHmmssSSS", Locale.getDefault())
                                    val hora = formatter.format(Date())
                                    val articuloSeleccionado = ArticulosSeleccionadosSac(
                                        nombre = if(iniciarVentanaAgregarArticulo) articuloActualSeleccionado.nombre else articuloActualSeleccionadoEdicion.nombre,
                                        codigo = if(iniciarVentanaAgregarArticulo) articuloActualSeleccionado.codigo else articuloActualSeleccionadoEdicion.codigo,
                                        precioUnitario = if(iniciarVentanaAgregarArticulo) articuloActualSeleccionado.precio else articuloActualSeleccionadoEdicion.precioUnitario,
                                        cantidad = cantidadArticulos,
                                        anotacion = anotacion,
                                        subCuenta = subCuentaSeleccionada,
                                        idGrupo = hora.toString(),
                                        imp1 = articuloActualSeleccionado.imp1
                                    )
                                    agregarOActualizarProducto(articuloSeleccionado, iniciarEdicionArticulo)
                                    iniciarVentanaAgregarArticulo=false
                                    iniciarEdicionArticulo= false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF244BC0), // Color de fondo del botón
                                    contentColor = Color.White,
                                    disabledContainerColor = Color(0xFF244BC0),
                                    disabledContentColor = Color.White
                                )
                            ) {
                                Text(
                                    if(iniciarVentanaAgregarArticulo) "Agregar" else "Editar",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(15),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                            Button(
                                onClick = {
                                    iniciarVentanaAgregarArticulo=false
                                    iniciarEdicionArticulo= false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red, // Color de fondo del botón
                                    contentColor = Color.White,
                                    disabledContainerColor = Color.Red,
                                    disabledContentColor = Color.White
                                )
                            ) {
                                Text(
                                    "Cancelar",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(15),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (iniciarVentanaAgregarCombo){
        articulosComboSeleccionados.clear()
        val cantidadArticulos by remember { mutableIntStateOf(1) }
        var anotacion by remember { mutableStateOf("") }
        val listaArticulosCombo = remember { mutableStateListOf<ArticuloSacGrupo>() }
        precioTotalArticulo= 0.00

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .wrapContentHeight()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            ) {
                Box(
                    modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(24)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            if(iniciarVentanaAgregarCombo) "Agregar Combo" else "Editar Combo",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloHead(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .height(objetoAdaptardor.ajustarAltura(123))
                                        .width(objetoAdaptardor.ajustarAncho(123)),
                                    contentAlignment = Alignment.BottomCenter
                                ) {
                                    SubcomposeAsyncImage(
                                        model = "https://invefacon.com/img/demorest/articulos/${articuloActualSeleccionado.codigo}.png",
                                        contentDescription = "Imagen Articulo",
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        contentScale = ContentScale.FillBounds,
                                        loading = {
                                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                                CircularProgressIndicator(
                                                    color = Color(0xFF244BC0),
                                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(50))
                                                )
                                            }
                                        },
                                        error = {
                                            Image(
                                                modifier = Modifier
                                                    .fillMaxSize(),
                                                painter = painterResource(id = R.drawable.sin_imagen),
                                                contentDescription = "Descripción de la imagen",
                                                contentScale = ContentScale.FillBounds
                                            )
                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                Text(
                                   articuloActualSeleccionado.nombre,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloTitle(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                Text(
                                    "\u20A1 "+String.format(Locale.US, "%,.2f", precioTotalArticulo.toString().replace(",", "").toDouble()),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloBody(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))

                                AgregarTextFieldMultifuncional(
                                    label = "Sub-Cuenta",
                                    opciones2 = opcionesSubCuentas,
                                    usarOpciones2 = true,
                                    contieneOpciones = true,
                                    nuevoValor = {nuevoValor-> subCuentaSeleccionada=nuevoValor},
                                    valor = subCuentaSeleccionada,
                                    isUltimo = true,
                                    tomarAnchoMaximo = false,
                                    medidaAncho = 70
                                )
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                BasicTextField(
                                    value = anotacion,
                                    onValueChange = { nuevoValor ->
                                        anotacion = nuevoValor
                                    },
                                    singleLine = true,
                                    textStyle = TextStyle(
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Light,
                                        color = Color.Black,
                                        textAlign = TextAlign.Justify
                                    ),
                                    keyboardOptions = KeyboardOptions.Default.copy(
                                        imeAction = ImeAction.Done
                                    ),
                                    decorationBox = { innerTextField ->
                                        Box(
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(300))
                                                .height(objetoAdaptardor.ajustarAltura(70))
                                                .background(
                                                    Color.LightGray,
                                                    RoundedCornerShape(
                                                        objetoAdaptardor.ajustarAltura(18)
                                                    )
                                                )
                                                .padding(horizontal = 16.dp, vertical = 4.dp),
                                            contentAlignment = Alignment.CenterStart
                                        ) {
                                            Row(
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Filled.EditNote,
                                                    contentDescription = "Icono Buscar",
                                                    tint = Color.DarkGray,
                                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Box(modifier = Modifier.weight(1f)) {
                                                    if (anotacion.isEmpty()) {
                                                        Text(
                                                            "Anotación",
                                                            fontFamily = fontAksharPrincipal,
                                                            fontWeight = FontWeight.Light,
                                                            fontSize = objetoAdaptardor.ajustarFont(
                                                                16
                                                            ),
                                                            maxLines = 1,
                                                            overflow = TextOverflow.Ellipsis
                                                        )
                                                    }
                                                    innerTextField()
                                                }
                                            }
                                        }
                                    }, modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(200))
                                        .height(objetoAdaptardor.ajustarAltura(50))
                                )

                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                                Row {
                                    Button(
                                        onClick = {
                                            val formatter = SimpleDateFormat("HHmmssSSS", Locale.getDefault())
                                            val hora = formatter.format(Date())
                                            var cantidadArticulosAgre = 0
                                            for (i in 0 until articulosComboSeleccionados.size){
                                                val articulo = articulosComboSeleccionados[i]
                                                if (articulo.cantidad>0){
                                                    cantidadArticulosAgre += articulo.cantidad
                                                    articulo.idGrupo = hora
                                                    listaArticulosCombo.add(articulo)
                                                }
                                            }
                                            if (articuloActualSeleccionado.cantidadArticulosObli== cantidadArticulosAgre){
                                                val articuloSeleccionado = ArticulosSeleccionadosSac(
                                                    nombre = articuloActualSeleccionado.nombre,
                                                    codigo =  articuloActualSeleccionado.codigo,
                                                    precioUnitario = articuloActualSeleccionado.precio,
                                                    cantidad = cantidadArticulos,
                                                    anotacion = anotacion,
                                                    subCuenta = subCuentaSeleccionada,
                                                    idGrupo = hora.toString(),
                                                    articulosCombo = listaArticulosCombo,
                                                    isCombo = 1,
                                                    imp1 = articuloActualSeleccionado.imp1
                                                )
                                                agregarOActualizarProducto(articuloSeleccionado, true)
                                                iniciarVentanaAgregarCombo = false
                                            }else{
                                                listaArticulosCombo.clear()
                                                val jsonObject = JSONObject("""
                                                    {
                                                        "code": 400,
                                                        "status": "error",
                                                       "data": "Seleccione todos los articulos obligatorios del combo"
                                                    }
                                                """
                                                )
                                                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
                                            }

                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF244BC0), // Color de fondo del botón
                                            contentColor = Color.White,
                                            disabledContainerColor = Color(0xFF244BC0),
                                            disabledContentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            if(iniciarVentanaAgregarCombo) "Agregar" else "Editar",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(15),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                    Button(
                                        onClick = {
                                            iniciarVentanaAgregarCombo = false
                                        },
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color.Red, // Color de fondo del botón
                                            contentColor = Color.White,
                                            disabledContainerColor = Color.Red,
                                            disabledContentColor = Color.White
                                        )
                                    ) {
                                        Text(
                                            "Cancelar",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(15),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                            Box(
                                modifier = Modifier
                                    .heightIn(max = objetoAdaptardor.ajustarAltura(500)),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                LazyColumn(
                                    state = lazyStateArticulosCombo
                                ){
                                    items(articuloActualSeleccionado.listaGrupos) { grupo ->
                                        AgregarBxContenedorArticulosCombo(grupo)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun AgregarBxContenerdorMontosCuenta(
    nombreCampo: String,
    monto: Double = 0.00,
){
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, true)

    Box(modifier = Modifier
        .background(Color(0xFFFAFAFA))
        .width(objetoAdaptardor.ajustarAncho(260))
        .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ){
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                nombreCampo,
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Medium,
                fontSize = obtenerEstiloBody(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start,
                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100)),
                color = Color.Black
            )
            Text(
                "\u20A1 "+String.format(Locale.US, "%,.2f", monto.toString().replace(",", "").toDouble()),
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Medium,
                fontSize = obtenerEstiloTitle(),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.End,
                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(143)),
                color =  Color.Black
            )
        }
    }
}



@Composable
@Preview(widthDp = 964, heightDp = 523, showBackground = true)
private fun Preview(){
    InterfazSacComandaLarge(null, "", null, "", "", "", "")
}
