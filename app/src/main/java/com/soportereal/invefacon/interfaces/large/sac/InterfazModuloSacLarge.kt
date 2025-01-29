package com.soportereal.invefacon.interfaces.large.sac

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.soportereal.invefacon.funciones_de_interfaces.RutasPantallasModuloSac
import com.soportereal.invefacon.interfaces.compact.FuncionesParaAdaptarContenidoCompact
import com.soportereal.invefacon.interfaces.compact.inicio_sesion.ocultarTeclado
import com.soportereal.invefacon.interfaces.compact.modulos.clientes.AgregarTextFieldMultifuncional
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.estadoRespuestaApi
import com.soportereal.invefacon.interfaces.compact.pantallas_principales.objetoEstadoPantallaCarga
import kotlinx.coroutines.delay
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale


@Composable
fun InterfazModuloSacLarge(
    apiToken: String,
    navControllerPantallasModuloSac: NavController?,
    systemUiController: SystemUiController?,
    navControllerPantallasModulos: NavController?,
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
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, true)
    var datosIngresadosBarraBusqueda by rememberSaveable  { mutableStateOf("") }
    val contexto = LocalContext.current
    val lazyStateMesas= rememberLazyListState()
    val lazyStateCuentasActivas= rememberLazyListState()
    var listaCuentasActivasActuales by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    var listaMesasActualesFiltradas by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    val objectoProcesadorDatosApi= ProcesarDatosModuloSac(apiToken)
    var iniciarMenuCrearMesa by remember { mutableStateOf(false) }
    var nombreNuevaMesa by remember { mutableStateOf("") }
    var nombreSalonNuevaMesa by remember { mutableStateOf("") }
    var iniciarCreacionNuevaMesa by remember { mutableStateOf(false) }
    var isPrimeraVezCargando by remember { mutableStateOf(true) }
    var isCargandoMesas by remember { mutableStateOf(true) }
    var iniciarMenuMesaComandada by remember { mutableStateOf(false) }
    var mesaActual by remember { mutableStateOf(Mesa()) }
    var subCuentaSeleccionada by remember { mutableStateOf("") }
    var subCuentaDestinoArticulo by remember { mutableStateOf("") }
    val opcionesSubCuentas: MutableState<LinkedHashMap<String, String>> = remember { mutableStateOf(LinkedHashMap()) }
    val opcionesSubCuentasDestino: MutableState<LinkedHashMap<String, String>> = remember { mutableStateOf(LinkedHashMap()) }
    val opcionesMesas: MutableState<LinkedHashMap<String, String>> = remember { mutableStateOf(LinkedHashMap()) }
    var mesaDestino by remember { mutableStateOf("") }
    val articulosComandados = remember { mutableStateListOf<ArticuloComandado>() }
    val lazyStateArticulosSeleccionados= rememberLazyListState()
    var iniciarPantallaSacComanda by remember { mutableStateOf(false) }
    var montoTotalComandado by remember { mutableStateOf("\u20A1 "+"0.00") }
    var iniciarCalculoMontos by remember { mutableStateOf(false) }
    var iniciarVentanaEliminarArticulo by remember { mutableStateOf(false) }
    var iniciarVentanaAgregarArticulo by remember { mutableStateOf(false) }
    var iniciarMenuMoverArticulo by remember { mutableStateOf(false) }
    var iniciarMenuMoverMesa by remember { mutableStateOf(false) }
    var articuloActualSeleccionado by remember { mutableStateOf(ArticuloComandado()) }
    val estadoBtOkRespuestaApi by estadoRespuestaApi.estadoBtOk.collectAsState()
    var agregarArticulo by remember { mutableStateOf(false) }
    var cantidadArticulos by remember { mutableIntStateOf(1) }
    var anotacionComanda by remember { mutableStateOf("") }
    var eliminarArticulo by remember { mutableStateOf(false) }
    var quitarMesa by remember { mutableStateOf(false) }
    var pedirCuenta by remember { mutableStateOf(false) }
    var moverArticulo by remember { mutableStateOf(false) }
    var moverMesa by remember { mutableStateOf(false) }
    var nombreNuevaSubCuenta by remember { mutableStateOf("") }
    var iniciarMenuAgregarSubCuenta by remember { mutableStateOf(false) }
    var iniciarMenuQuitarMesa by remember { mutableStateOf(false) }
    var actualizarEstadoMesas by remember { mutableStateOf(false) }
    var regresarPantallaAnterior by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            delay(500)
            navControllerPantallasModuloSac?.navigate(RutasPantallasModuloSac.PantallaSacComanda.ruta+"/"+mesaActual.nombre+"/"+mesaActual.salon){
                restoreState= true
                launchSingleTop=true
            }
        }
    }

    LaunchedEffect(iniciarCreacionNuevaMesa) {
        if (iniciarCreacionNuevaMesa){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            delay(500)
            val result = objectoProcesadorDatosApi.crearNuevaMesa(nombreNuevaMesa, nombreSalonNuevaMesa)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            nombreNuevaMesa=""
            nombreSalonNuevaMesa=""
            actualizarEstadoMesas= true
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            iniciarCreacionNuevaMesa=false

        }
    }

    LaunchedEffect(Unit) {
        while (true){
            val listaMesas = mutableListOf<Mesa>()
            val listaCuentasActivas = mutableListOf<Mesa>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda)
            if (result!=null){
                val data = result.getJSONObject("data")
                val datosMesas= data.getJSONArray("mesas")
                for (i in 0 until datosMesas.length()){
                    val datoMesa= datosMesas.getJSONObject(i)
                    val mesa = Mesa(
                        nombre = datoMesa.getString("mesa"),
                        idMesa = datoMesa.getString("Consec"),
                        cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                        tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                        total = datoMesa.getString("monto"),
                        estado = datoMesa.getString("CodEstado"),
                        salon = datoMesa.getString("salon")
                    )
                    listaMesas.add(mesa)
                }

                val datosCuentasActivas = data.getJSONArray("cuentasActivas")
                for (i in 0 until datosCuentasActivas.length()) {
                    val datoMesa= datosCuentasActivas.getJSONObject(i)
                    val mesa = Mesa(
                        nombre = datoMesa.getString("mesa"),
                        idMesa = datoMesa.getString("Consec"),
                        cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                        tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                        total = datoMesa.getString("monto"),
                        estado = datoMesa.getString("CodEstado")
                    )
                    listaCuentasActivas.add(mesa)
                }
            }

            if (listaMesasActualesFiltradas!=listaMesas){
                listaMesasActualesFiltradas=listaMesas
            }
            if (listaCuentasActivasActuales!=listaCuentasActivas){
                listaCuentasActivasActuales=listaCuentasActivas
            }

            if (isPrimeraVezCargando){
                objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
                isPrimeraVezCargando=false
                isCargandoMesas=false
            }
            delay(10000)
        }
    }

    LaunchedEffect(actualizarEstadoMesas) {
        if (actualizarEstadoMesas){
            val listaMesas = mutableListOf<Mesa>()
            val listaCuentasActivas = mutableListOf<Mesa>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda)
            if (result!=null){
                val data = result.getJSONObject("data")
                val datosMesas= data.getJSONArray("mesas")
                for (i in 0 until datosMesas.length()){
                    val datoMesa= datosMesas.getJSONObject(i)
                    val mesa = Mesa(
                        nombre = datoMesa.getString("mesa"),
                        idMesa = datoMesa.getString("Consec"),
                        cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                        tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                        total = datoMesa.getString("monto"),
                        estado = datoMesa.getString("CodEstado"),
                        salon = datoMesa.getString("salon")
                    )
                    listaMesas.add(mesa)
                }

                val datosCuentasActivas = data.getJSONArray("cuentasActivas")
                for (i in 0 until datosCuentasActivas.length()) {
                    val datoMesa= datosCuentasActivas.getJSONObject(i)
                    val mesa = Mesa(
                        nombre = datoMesa.getString("mesa"),
                        idMesa = datoMesa.getString("Consec"),
                        cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                        tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                        total = datoMesa.getString("monto"),
                        estado = datoMesa.getString("CodEstado")
                    )
                    listaCuentasActivas.add(mesa)
                }
            }

            if (listaMesasActualesFiltradas!=listaMesas){
                listaMesasActualesFiltradas=listaMesas
            }
            if (listaCuentasActivasActuales!=listaCuentasActivas){
                listaCuentasActivasActuales=listaCuentasActivas
            }
            actualizarEstadoMesas=false
        }
    }

    LaunchedEffect(iniciarMenuMesaComandada, estadoBtOkRespuestaApi) {
        if(iniciarMenuMesaComandada && !iniciarMenuMoverArticulo) {
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            val result = objectoProcesadorDatosApi.obtenerDatosMesaComandada(mesaActual.nombre)
            println(result)
            if (result != null) {
                estadoRespuestaApi.cambiarEstadoRespuestaApi(
                    mostrarSoloRespuestaError = true,
                    datosRespuesta = result
                )

                if (result.getString("code")== "200"){
                    val data = result.getJSONObject("data")
                    val subCuentas = data.getJSONArray("Subcuentas")
                    val articulos = data.getJSONArray("detalleSubcuenta")
                    opcionesSubCuentas.value.clear()

                    for (i in 0 until subCuentas.length()) {
                        opcionesSubCuentas.value[subCuentas[i].toString()] = subCuentas[i].toString()
                    }

                    subCuentaSeleccionada= subCuentas[0].toString()
                    articulosComandados.clear()

                    for (i in 0 until articulos.length()) {
                        val articulo = articulos.getJSONObject(i)
                        val articuloComadado = ArticuloComandado(
                            Consec = articulo.getString("Consec"),
                            Cod_Articulo = articulo.getString("Cod_Articulo"),
                            Cantidad = articulo.getInt("Cantidad"),
                            Precio = articulo.getDouble("Precio"),
                            Imp1 = articulo.getString("Imp1"),
                            Imp2 = articulo.getString("Imp2"),
                            Linea = articulo.getString("Linea"),
                            SubCuenta = articulo.getString("SubCuenta"),
                            nombre= articulo.getString("nombreArticulo")
                        )
                        articulosComandados.add(articuloComadado)
                    }
                }
                else{
                    iniciarMenuMesaComandada=false
                }

            }
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            iniciarCalculoMontos= true
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    LaunchedEffect(iniciarCalculoMontos, subCuentaSeleccionada) {
        if (iniciarCalculoMontos){
            montoTotalComandado="0.00"
            articulosComandados.forEach{articuloComandado ->
                if (articuloComandado.SubCuenta.uppercase()==subCuentaSeleccionada.uppercase()){
                    articuloComandado.calcularMontoTotal()
                    montoTotalComandado=(montoTotalComandado.toDouble()+articuloComandado.montoTotal).toString()
                }
            }
            montoTotalComandado= "\u20A1 "+String.format(Locale.US, "%,.2f", montoTotalComandado.replace(",", "").toDouble())
            iniciarCalculoMontos= false
        }
    }

    LaunchedEffect(agregarArticulo, eliminarArticulo) {
        if(agregarArticulo  || (eliminarArticulo&& anotacionComanda.isNotEmpty())){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            val jsonComandaDetalle = JSONArray()
            val codigo=articuloActualSeleccionado.Cod_Articulo
            val cantidad= if (agregarArticulo) cantidadArticulos else -cantidadArticulos
            val precio = articuloActualSeleccionado.Precio
            val imp1= "13.00"
            val imp2 = "10.00"
            val anotacion = anotacionComanda
            val subCuenta = articuloActualSeleccionado.SubCuenta
            val jsonObject = JSONObject().apply {
                put("codigo", codigo)
                put("cantidad", cantidad)
                put("precio", precio)
                put("imp1",imp1)
                put("imp2", imp2)
                put("anotacion", anotacion)
                put("subcuenta", subCuenta)
            }
            jsonComandaDetalle.put(jsonObject)

            if (jsonComandaDetalle.length()>0){
                articulosComandados.clear()
                opcionesSubCuentas.value.clear()
                subCuentaSeleccionada =""
                iniciarVentanaEliminarArticulo=false
                iniciarVentanaAgregarArticulo=false
                anotacionComanda= ""
                cantidadArticulos= 1
                val result= objectoProcesadorDatosApi.comandarSubCuenta_eliminarArticulos(
                    codUsuario = codUsuario,
                    salon = mesaActual.salon,
                    mesa = mesaActual.nombre,
                    jsonComandaDetalle = jsonComandaDetalle
                )
                if (result != null) {
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result )
                }
            }
            delay(100)
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            eliminarArticulo= false
            agregarArticulo= false
        }

        if(eliminarArticulo && anotacionComanda.isEmpty()){
            val jsonObject = JSONObject("""
                    {
                        "code": 400,
                        "status": "error",
                        "data": "Ingrese el motivo de la eliminacion del articulo"
                    }
                """
            )
            estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject )
            actualizarEstadoMesas= true
            eliminarArticulo= false
            agregarArticulo= false
        }

    }

    LaunchedEffect(quitarMesa) {
        if(quitarMesa){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            val result = objectoProcesadorDatosApi.quitarMesa(mesaActual.nombre)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            iniciarMenuQuitarMesa=false
            iniciarMenuMesaComandada=false
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            actualizarEstadoMesas= true
            quitarMesa= false
        }
    }

    LaunchedEffect(pedirCuenta) {
        if(pedirCuenta){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            val result = objectoProcesadorDatosApi.pedirCuenta(mesaActual.nombre)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            actualizarEstadoMesas= true
            pedirCuenta= false
        }
    }

    LaunchedEffect(iniciarMenuMoverArticulo, mesaDestino, iniciarMenuMoverMesa) {
        if(iniciarMenuMoverArticulo || iniciarMenuMoverMesa){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            var result = objectoProcesadorDatosApi.obetenerNombresMesas()
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                val data= result.getJSONObject("data")
                val mesas= data.getJSONArray("Mesas")
                for(i in 0 until mesas.length()){
                    println(mesas.getString(i))
                    opcionesMesas.value[mesas.getString(i)]= mesas.getString(i)
                }
                if (mesaDestino.isEmpty()){
                    mesaDestino= opcionesMesas.value[mesaActual.nombre].toString()
                }
            }
            subCuentaDestinoArticulo=""
            opcionesSubCuentasDestino.value.clear()
            result = objectoProcesadorDatosApi.obetenerSubCuentasMesa(mesaDestino)
            if (result!= null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                val data= result.getJSONObject("data")
                val subCuentas= data.getJSONArray("Subcuentas")
                for( i in 0 until subCuentas.length()){
                    opcionesSubCuentasDestino.value[subCuentas.getString(i)] = subCuentas.getString(i)
                }
                if (subCuentaDestinoArticulo.isEmpty()){
                    subCuentaDestinoArticulo= opcionesSubCuentasDestino.value.keys.first()
                }
            }
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
        }
    }

    LaunchedEffect(moverArticulo) {
        if(moverArticulo){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            val result= objectoProcesadorDatosApi.moverArticulo(
                codigoArticulo = articuloActualSeleccionado.Cod_Articulo,
                cantidadArticulos = cantidadArticulos.toString(),
                mesa = mesaActual.nombre,
                mesaDestino = mesaDestino,
                subCuenta = articuloActualSeleccionado.SubCuenta,
                subCuentaDestino = subCuentaDestinoArticulo,
                codUsuario = codUsuario
            )
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            iniciarMenuMoverArticulo= false
            mesaDestino= ""
            subCuentaDestinoArticulo=""
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            actualizarEstadoMesas= true
            moverArticulo= false
        }
    }

    LaunchedEffect(moverMesa) {
        if(moverMesa){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            val result = objectoProcesadorDatosApi.moverMesa(mesa = mesaActual.nombre, mesaDestino = mesaDestino)
            if (result!= null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            mesaDestino=""
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(false)
            iniciarMenuMoverMesa= false
            actualizarEstadoMesas= true
            moverMesa= false
        }
    }

    LaunchedEffect(regresarPantallaAnterior) {
        if (regresarPantallaAnterior){
            navControllerPantallasModulos?.popBackStack()
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    @Composable
    fun AgregarBt(
        text: String,
        color: Long,
        onClick: (Boolean)->Unit,
        quitarPadInterno: Boolean = false
    ){
        Button(
            modifier = if (quitarPadInterno) {
                Modifier.height(objetoAdaptardor.ajustarAltura(30))
            } else {
                Modifier
            },
            onClick = {
                onClick(true)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =Color(color), // Color de fondo del botón
                contentColor = Color.White,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.White
            ),contentPadding = if (quitarPadInterno) PaddingValues(0.dp) else ButtonDefaults.ContentPadding
        ) {
            Text(
                text,
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

    @Composable
    fun AgregarBxContenedorArticulosComandados(
        articuloComandado: ArticuloComandado
    ){
        articuloComandado.calcularMontoTotal()
        Box(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(365))
        ){
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .height(objetoAdaptardor.ajustarAltura(50))
                            .width(objetoAdaptardor.ajustarAncho(50)),
                        contentAlignment = Alignment.Center
                    ) {
                        SubcomposeAsyncImage(
                            model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articuloComandado.Cod_Articulo}.png",
                            contentDescription = "Imagen Articulo",
                            modifier = Modifier
                                .fillMaxSize(),
                            contentScale = ContentScale.FillBounds,
                            loading = {
                                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                                    CircularProgressIndicator(
                                        color = Color(0xFF244BC0),
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(20))
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
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    Text(
                        articuloComandado.nombre,
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = objetoAdaptardor.ajustarFont(18),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(165)).padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))
                    Box{
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    articuloActualSeleccionado= articuloComandado
                                    iniciarVentanaEliminarArticulo= true
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(22))
                            ) {
                                Icon(
                                    imageVector = if(articuloComandado.Cantidad==1) Icons.Filled.Delete else Icons.Filled.RemoveCircle,
                                    contentDescription = "Basurero",
                                    tint = if(articuloComandado.Cantidad==1)Color.Red else Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(22))
                                )
                            }

                            Text(
                                articuloComandado.Cantidad.toString(),
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = objetoAdaptardor.ajustarFont(18),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                            )
                            IconButton(
                                onClick = {
                                    articuloActualSeleccionado= articuloComandado
                                    iniciarVentanaAgregarArticulo = true
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(22))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = "Agregar Articulo",
                                    tint = Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(22))
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))

                    AgregarBt(
                        text = "Mover",
                        color = 0xFF244BC0,
                        onClick = {
                            articuloActualSeleccionado= articuloComandado
                            iniciarMenuMoverArticulo=true
                        },
                        quitarPadInterno = true
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                }
                HorizontalDivider()
                Row {
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    Text(
                        "\u20A1 "+String.format(Locale.US, "%,.2f", articuloComandado.montoTotal.toString().replace(",", "").toDouble()),
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = objetoAdaptardor.ajustarFont(18),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(422)).padding(2.dp)
                    )
                }
            }
        }
    }

    ConstraintLayout(
        modifier = Modifier
            .width(objetoAdaptardor.ajustarAncho(964))
            .height(objetoAdaptardor.ajustarAltura(523))
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior, bxContenedorMesas ,txfBarraBusqueda, bxContenerdorCuentasActivas, bxContenedorBotones, flechaRegresar)= createRefs()

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

        IconButton(
            onClick = {regresarPantallaAnterior=true},
            modifier = Modifier.constrainAs(flechaRegresar){
                start.linkTo(parent.start)
                top.linkTo(parent.top)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(25))
            )
        }

        OutlinedTextField(
            value = datosIngresadosBarraBusqueda,
            onValueChange = {
                datosIngresadosBarraBusqueda = it
            },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search,
                    contentDescription = "Icono Buscar",
                    tint= Color.DarkGray,
                    modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                )
            },
            placeholder = {
                Text("Buscar...",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    maxLines = 1
                )
            },
            modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(250))
                .height(objetoAdaptardor.ajustarAltura(55))
                .constrainAs(txfBarraBusqueda) {
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(18)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.LightGray,
                unfocusedContainerColor = Color.LightGray,
                disabledContainerColor = Color.White,
                focusedIndicatorColor = Color.White,
                unfocusedIndicatorColor = Color.White,
                cursorColor = Color(0xFF244BC0),
                focusedLabelColor = Color.DarkGray,
                unfocusedLabelColor = Color.DarkGray
            ),
            textStyle = TextStyle(
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Light,
                color = Color.DarkGray,
                textAlign = TextAlign.Justify
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { ocultarTeclado(contexto) }
            )
        )

        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(55))
                .width(objetoAdaptardor.ajustarAncho(487))
                .constrainAs(bxContenedorBotones) {
                    start.linkTo(txfBarraBusqueda.end, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                }
        ){
            Row(
                horizontalArrangement = Arrangement.Start
            ) {

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        iniciarMenuCrearMesa= true
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Crear mesa",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        actualizarEstadoMesas= true
                    }
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Refrescar",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(740))
                .height(objetoAdaptardor.ajustarAltura(410))
                .constrainAs(bxContenedorMesas) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(12))
                    top.linkTo(txfBarraBusqueda.bottom, margin = objetoAdaptardor.ajustarAltura(4))
                },
            contentAlignment = Alignment.TopStart
        ){
            if (isCargandoMesas) {
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
                    state = lazyStateMesas,
                ) {
                    items(listaMesasActualesFiltradas.chunked(5)) { rowItems ->
                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.Top
                        ) {
                            rowItems.forEach { mesa ->
                                BxContendorDatosMesa(
                                    datosMesa = mesa,
                                    navControllerPantallasModuloSac = navControllerPantallasModuloSac,
                                    iniciarMenuDetalleComanda = { valor-> iniciarMenuMesaComandada=valor},
                                    mesaSeleccionada = {datosMesaActual-> mesaActual= datosMesaActual }
                                )
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
                .width(objetoAdaptardor.ajustarAncho(195))
                .height(objetoAdaptardor.ajustarAltura(469))
                .shadow(
                    elevation = objetoAdaptardor.ajustarAltura(2),
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
                )
                .constrainAs(bxContenerdorCuentasActivas) {
                    start.linkTo(bxContenedorMesas.end, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                },
            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA))
        ){
            Column {
                Box(
                    modifier =
                    Modifier
                        .background(Color(0xFF244BC0))
                        .height(objetoAdaptardor.ajustarAltura(40))
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        text = "Cuentas activas",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.SemiBold,
                        fontSize = objetoAdaptardor.ajustarFont(25),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(130))
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(objetoAdaptardor.ajustarAltura(20))
                        .background(Color(0xFFFAFAFA))
                ){
                    Row {
                        Text(
                            "Mesa",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(55))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )

                        Text(
                            "Tiempo",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(55))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )

                        Text(
                            "Total",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(16),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(85))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFAFAFA))
                ){
                    LazyColumn(
                        state = lazyStateCuentasActivas
                    ) {
                        items(listaCuentasActivasActuales){ mesa->
                            if (mesa.estado!="null"){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFFAFAFA)),
                                    contentAlignment = Alignment.Center
                                ){
                                    Row {
                                        Text(
                                            mesa.nombre,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(55))
                                                .padding(objetoAdaptardor.ajustarAncho(2))
                                        )

                                        var tiempo by remember { mutableStateOf("") }
                                        if (mesa.tiempo>=60){
                                            val minutos =  mesa.tiempo
                                            val horas = minutos / 60
                                            val minutosRestantes = minutos % 60
                                            tiempo="$horas:$minutosRestantes h"
                                        }else{
                                            tiempo= "${mesa.tiempo} m"
                                        }

                                        Text(
                                            tiempo,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(55))
                                                .padding(objetoAdaptardor.ajustarAncho(2))
                                        )

                                        val total= mesa.total
                                        var totalMiles by remember { mutableStateOf("") }
                                        totalMiles = try {
                                            String.format(Locale.US, "%,.2f", total.replace(",", "").toDouble())
                                        } catch (e: NumberFormatException) {
                                            total
                                        }

                                        Text(
                                            totalMiles,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = objetoAdaptardor.ajustarFont(16),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Start,
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(85))
                                                .padding(objetoAdaptardor.ajustarAncho(2))
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }

        }
    }

    if(iniciarMenuCrearMesa) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Crear Nueva Mesa",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = objetoAdaptardor.ajustarFont(27),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box{
                    Column {
                        // Input Nombre Mesa
                        OutlinedTextField(
                            value = nombreNuevaMesa,
                            onValueChange = { newText -> nombreNuevaMesa = newText },
                            label = {
                                Text(
                                    "Nombre de la Mesa",
                                    color = Color.DarkGray,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            placeholder = {
                                Text(
                                    "Ingrese el nombre de la mesa",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70)),
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = fontAksharPrincipal, fontWeight = FontWeight.Light, color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(16)),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF5B5B5B), // Color del texto cuando está enfocado
                                unfocusedTextColor = Color(0xFF5B5B5B),
                                focusedPlaceholderColor =  Color(0xFF5B5B5B),
                                unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                focusedBorderColor =  Color(0xFF5B5B5B),
                                unfocusedBorderColor = Color(0xFF5B5B5B)
                            )
                        )

                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                        // Input Nombre salon
                        OutlinedTextField(
                            value = nombreSalonNuevaMesa,
                            onValueChange = { newText -> nombreSalonNuevaMesa = newText },
                            label = {
                                Text(
                                    "Nombre del Salon",
                                    color = Color.DarkGray,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            placeholder = {
                                Text(
                                    "Ingrese el nombre del salon",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70)),
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = fontAksharPrincipal, fontWeight = FontWeight.Light, color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(16)),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF5B5B5B), // Color del texto cuando está enfocado
                                unfocusedTextColor = Color(0xFF5B5B5B),
                                focusedPlaceholderColor =  Color(0xFF5B5B5B),
                                unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                focusedBorderColor =  Color(0xFF5B5B5B),
                                unfocusedBorderColor = Color(0xFF5B5B5B)
                            )
                        )

                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        iniciarMenuCrearMesa=false
                        iniciarCreacionNuevaMesa=true
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
                        iniciarMenuCrearMesa = false
                        nombreNuevaMesa=""
                        nombreSalonNuevaMesa=""

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

    if(iniciarMenuMesaComandada) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ){
            Surface(
                modifier = Modifier
                    .width(objetoAdaptardor.ajustarAncho(630))
                    .wrapContentHeight()
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            )  {
                Box(
                    modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(24)),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Articulos Comandados ${mesaActual.nombre}",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(27),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                                Text(
                                    "Sub-Cuentas: ",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(22),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                AgregarTextFieldMultifuncional(
                                    label = "Sub-Cuentas",
                                    opciones2 = opcionesSubCuentas,
                                    usarOpciones2 = true,
                                    contieneOpciones = true,
                                    nuevoValor = { nuevoValor->
                                        subCuentaSeleccionada=nuevoValor
                                        iniciarCalculoMontos=true
                                    },
                                    valor = subCuentaSeleccionada,
                                    isUltimo = true,
                                    tomarAnchoMaximo = false,
                                    medidaAncho = 180
                                )
                        }
                        Box(
                            modifier = Modifier
                                .heightIn(max = objetoAdaptardor.ajustarAltura(300))
                                .wrapContentWidth(),
                            contentAlignment = Alignment.Center
                        ){
                            LazyColumn(
                                state = lazyStateArticulosSeleccionados,
                            ) {
                                items(articulosComandados){ producto->
                                    if (producto.SubCuenta.uppercase()==subCuentaSeleccionada.uppercase()){
                                        AgregarBxContenedorArticulosComandados(producto)
                                    }
                                }
                            }
                        }
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .height(objetoAdaptardor.ajustarAltura(50))
                                .width(objetoAdaptardor.ajustarAncho(500))
                        ){
                            Text(
                                "Total $montoTotalComandado",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = objetoAdaptardor.ajustarFont(25),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.Black,
                            )
                        }

                        Box(
                            contentAlignment = Alignment.Center
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ){
                                AgregarBt(
                                    text = "Mover Mesa",
                                    color = 0xFF244BC0,
                                    onClick = { iniciarMenuMoverMesa=true}
                                )

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                AgregarBt(
                                    text = "Pedir Cuenta",
                                    color = 0xFF244BC0,
                                    onClick = {
                                        pedirCuenta= false
                                        pedirCuenta= true
                                    }
                                )

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                AgregarBt(
                                    text = "Quitar mesa",
                                    color = 0xFFFF5722,
                                    onClick = {
                                        iniciarMenuQuitarMesa= true
                                    }
                                )

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                AgregarBt(
                                    text = "Agregar comanda",
                                    color =  0xFF22B14C,
                                    onClick = { iniciarPantallaSacComanda= true}
                                )

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                                AgregarBt(
                                    text = "Salir",
                                    color = 0xFFE10000,
                                    onClick = {
                                        iniciarMenuMesaComandada= false
                                        mesaActual= Mesa()
                                        articulosComandados.clear()
                                        opcionesSubCuentas.value.clear()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (iniciarVentanaEliminarArticulo || iniciarVentanaAgregarArticulo){
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    if (iniciarVentanaEliminarArticulo) "Eliminar Articulo" else "Agregar Articulo",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = objetoAdaptardor.ajustarFont(27),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box(
                    contentAlignment = Alignment.TopCenter
                ){
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
                                model = "https://invefacon.com/img/demorest/articulos/${articuloActualSeleccionado.Cod_Articulo}.png",
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


                        Text(
                            articuloActualSeleccionado.nombre,
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(27),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Box{
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    if (iniciarVentanaAgregarArticulo) "Cantidad a agregar: " else "Cantidad a eliminar:  ",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(23),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )

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
                                            tint = Color.Black,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                        )
                                    }
                                }

                                Text(
                                    cantidadArticulos.toString(),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(22),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                                )

                                if ((cantidadArticulos<articuloActualSeleccionado.Cantidad) || iniciarVentanaAgregarArticulo){
                                    IconButton(
                                        onClick = {
                                            cantidadArticulos+=1
                                        },
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AddCircle,
                                            contentDescription = "Agregar Articulo",
                                            tint = Color.Black,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                        )
                                    }
                                }


                            }
                        }
                        // Input Nombre Mesa
                        OutlinedTextField(
                            value = anotacionComanda,
                            onValueChange = { newText -> anotacionComanda = newText },
                            label = {
                                Text(
                                    if (iniciarVentanaAgregarArticulo) "Anotacion" else "Motivo de eliminacion",
                                    color = Color.DarkGray,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            placeholder = {
                                Text(
                                    if (iniciarVentanaAgregarArticulo) "Ingrese la anotacion" else "Ingrese el motivo",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70)),
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = fontAksharPrincipal, fontWeight = FontWeight.Light, color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(16)),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF5B5B5B), // Color del texto cuando está enfocado
                                unfocusedTextColor = Color(0xFF5B5B5B),
                                focusedPlaceholderColor =  Color(0xFF5B5B5B),
                                unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                focusedBorderColor =  Color(0xFF5B5B5B),
                                unfocusedBorderColor = Color(0xFF5B5B5B)
                            )
                        )
                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (iniciarVentanaAgregarArticulo) agregarArticulo= true else eliminarArticulo= true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        if (iniciarVentanaAgregarArticulo)  "Agregar" else "Eliminar",
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
                        iniciarVentanaEliminarArticulo=false
                        iniciarVentanaAgregarArticulo= false
                        cantidadArticulos= 1
                        anotacionComanda= ""
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

    if (iniciarMenuMoverArticulo){
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                   "Mover Articulo",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = objetoAdaptardor.ajustarFont(27),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box(
                    contentAlignment = Alignment.TopCenter
                ){
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
                                model = "https://invefacon.com/img/demorest/articulos/${articuloActualSeleccionado.Cod_Articulo}.png",
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
                        Text(
                            articuloActualSeleccionado.nombre,
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(27),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
                        Box{
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "Cantidad articulos a mover",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(23),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )

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
                                            tint = Color.Black,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                        )
                                    }
                                }

                                Text(
                                    cantidadArticulos.toString(),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = objetoAdaptardor.ajustarFont(22),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                                )

                                if (cantidadArticulos<articuloActualSeleccionado.Cantidad){
                                    IconButton(
                                        onClick = {
                                            cantidadArticulos+=1
                                        },
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AddCircle,
                                            contentDescription = "Agregar Articulo",
                                            tint = Color.Black,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAltura(30))
                                        )
                                    }
                                }


                            }
                        }

                        Box {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                AgregarTextFieldMultifuncional(
                                    label = "Nueva Mesa",
                                    opciones2 = opcionesMesas,
                                    usarOpciones2 = true,
                                    contieneOpciones = true,
                                    nuevoValor = { nuevoValor->
                                        mesaDestino= nuevoValor
                                    },
                                    valor = mesaDestino,
                                    isUltimo = true,
                                    tomarAnchoMaximo = false,
                                    medidaAncho = 180,
                                    mostrarClave = true
                                )
                                AgregarTextFieldMultifuncional(
                                    label = "Nueva Sub-Cuenta",
                                    opciones2 = opcionesSubCuentasDestino,
                                    usarOpciones2 = true,
                                    contieneOpciones = true,
                                    nuevoValor = { nuevoValor->
                                        subCuentaDestinoArticulo= nuevoValor
                                    },
                                    valor = subCuentaDestinoArticulo,
                                    isUltimo = true,
                                    tomarAnchoMaximo = false,
                                    medidaAncho = 180,
                                    mostrarClave = true
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
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                       moverArticulo= true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Mover",
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
                        iniciarMenuMoverArticulo= false
                        cantidadArticulos= 1
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

    if (iniciarMenuMoverMesa){
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Mover Mesa",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = objetoAdaptardor.ajustarFont(27),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box(
                    contentAlignment = Alignment.Center
                ){
                    AgregarTextFieldMultifuncional(
                        label = "Mesa Destino",
                        opciones2 = opcionesMesas,
                        usarOpciones2 = true,
                        contieneOpciones = true,
                        nuevoValor = { nuevoValor->
                            mesaDestino= nuevoValor
                        },
                        valor = mesaDestino,
                        isUltimo = true,
                        tomarAnchoMaximo = false,
                        medidaAncho = 180,
                        mostrarClave = true
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        moverMesa= true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Mover",
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
                        iniciarMenuMoverMesa= false
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
                    fontSize = objetoAdaptardor.ajustarFont(27),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box{
                    Column {
                        // Input Nombre Mesa
                        OutlinedTextField(
                            value = nombreNuevaSubCuenta,
                            onValueChange = { newText -> nombreNuevaSubCuenta = newText },
                            label = {
                                Text(
                                    "Nombre de la Sub-Cuenta",
                                    color = Color.DarkGray,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            placeholder = {
                                Text(
                                    "Ingrese el nombre de la Sub-Cuenta",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = objetoAdaptardor.ajustarFont(16),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(300))
                                .height(objetoAdaptardor.ajustarAltura(70)),
                            singleLine = true,
                            textStyle = TextStyle(fontFamily = fontAksharPrincipal, fontWeight = FontWeight.Light, color = Color.DarkGray, fontSize = objetoAdaptardor.ajustarFont(16)),
                            shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color(0xFF5B5B5B), // Color del texto cuando está enfocado
                                unfocusedTextColor = Color(0xFF5B5B5B),
                                focusedPlaceholderColor =  Color(0xFF5B5B5B),
                                unfocusedPlaceholderColor = Color(0xFF5B5B5B),
                                focusedBorderColor =  Color(0xFF5B5B5B),
                                unfocusedBorderColor = Color(0xFF5B5B5B)
                            )
                        )
                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        opcionesSubCuentasDestino.value[nombreNuevaSubCuenta]=nombreNuevaSubCuenta
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

    if(iniciarMenuQuitarMesa) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Quitar Mesa",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = objetoAdaptardor.ajustarFont(25),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    color = Color.Black
                )
            },
            text = {
                Box(contentAlignment = Alignment.Center){
                    Text(
                        "¿Desea eliminar todos los articulos de mesa?",
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Medium,
                        fontSize = objetoAdaptardor.ajustarFont(27),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = Color.Black
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        quitarMesa=true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Quitar",
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
                        iniciarMenuQuitarMesa= false
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
}

@Composable
internal fun BxContendorDatosMesa(
    datosMesa: Mesa,
    navControllerPantallasModuloSac:NavController?,
    iniciarMenuDetalleComanda: (Boolean)->Unit,
    mesaSeleccionada: (Mesa)->Unit
){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    var iniciarPantallaSacComanda by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda && datosMesa.estado=="null"){
            objetoEstadoPantallaCarga.cambiarEstadoMenuPrincipal(true)
            iniciarMenuDetalleComanda(false)
            delay(500)
            navControllerPantallasModuloSac?.navigate(RutasPantallasModuloSac.PantallaSacComanda.ruta+"/"+datosMesa.nombre+"/"+datosMesa.salon){
                restoreState= true
                launchSingleTop=true
            }
        }
        if (iniciarPantallaSacComanda && datosMesa.estado!="null"){
            iniciarMenuDetalleComanda(true)
            iniciarPantallaSacComanda=false
        }
    }

    Card(
        modifier = Modifier
            .height(objetoAdaptardor.ajustarAltura(165))
            .width(objetoAdaptardor.ajustarAncho(55))
            .clickable {
                mesaSeleccionada(datosMesa)
                iniciarPantallaSacComanda = true

            }
            .shadow(
                elevation = objetoAdaptardor.ajustarAltura(7),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
            ),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(147))
                .height(objetoAdaptardor.ajustarAltura(35))
                .background(
                    when (datosMesa.estado) {
                        "1" -> {
                            Color(0xFFFFC107)
                        }

                        "2" -> {

                            Color(0xFFFF5722)
                        }

                        else -> {
                            Color(0xFF4CAF50)
                        }
                    }
                ),
                contentAlignment = Alignment.Center
            ){
                // Codigo Cliente
                Text(text = datosMesa.nombre,
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = objetoAdaptardor.ajustarFont(23),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(130))
                )
            }
            Box(modifier = Modifier
                .width(objetoAdaptardor.ajustarAncho(147))
                .background(Color.White),
                contentAlignment = Alignment.Center
            ){
                if (datosMesa.estado == "null") {
                    Text(text = "Disponible",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize = objetoAdaptardor.ajustarFont(20),
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(140))
                    )
                }else{
                    Column {
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        Text(text = "Sub Cuentas: ${datosMesa.cantidadSubcuentas}",
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(18),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(140))
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))

                        var tiempo by remember { mutableStateOf("${datosMesa.tiempo} m") }

                        if (datosMesa.tiempo>=60){
                            val minutos =  datosMesa.tiempo
                            val horas = minutos / 60
                            val minutosRestantes = minutos % 60
                            tiempo="$horas:$minutosRestantes h"
                        }else{
                            tiempo= "${datosMesa.tiempo} m"
                        }

                        Text(text = "Tiempo: $tiempo",
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(18),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(140))
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        // Codigo Cliente
                        val total= datosMesa.total
                        var totalMiles by remember { mutableStateOf("") }
                        totalMiles = try {
                            String.format(Locale.US, "%,.2f", total.replace(",", "").toDouble())
                        } catch (e: NumberFormatException) {
                            total
                        }
                        Text(text = "Total: $totalMiles",
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.Light,
                            fontSize = objetoAdaptardor.ajustarFont(18),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(140))
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                    }
                }


            }
        }

    }
}


@Composable
@Preview(widthDp = 964, heightDp = 523)
//@Preview()
private fun Preview(){
    val m= Mesa(idMesa = "1", estado = "2", nombre = "Mesa 1", total= "10000", tiempo = 45, cantidadSubcuentas = "1")
//    BxContendorDatosMesa(m)
    InterfazModuloSacLarge("", null, null, null, "","")
}

//@Preview
//@Composable
//private fun Preview2(){
//    val articulo =  ArticuloComandado(
//        Consec = "1185",
//        Cod_Articulo = "01040001",
//        Cantidad = 7.00,
//        Precio = 100.00,
//        Imp1 = "13.00",
//        Imp2 = ".00",
//        Linea = "2647",
//        SubCuenta = "Yesler",
//        nombre = "Hamburguesa con queso y papas"
//    )
//    AgregarBxContenedorArticulosComandados(articulo)
//}-