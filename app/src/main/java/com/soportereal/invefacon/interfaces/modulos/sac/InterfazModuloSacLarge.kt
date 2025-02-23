package com.soportereal.invefacon.interfaces.modulos.sac

 import android.annotation.SuppressLint
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
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.AccountTree
 import androidx.compose.material.icons.filled.AddCircle
 import androidx.compose.material.icons.filled.ArrowBackIosNew
 import androidx.compose.material.icons.filled.Delete
 import androidx.compose.material.icons.filled.EditNote
 import androidx.compose.material.icons.filled.Password
 import androidx.compose.material.icons.filled.Person
 import androidx.compose.material.icons.filled.Place
 import androidx.compose.material.icons.filled.RemoveCircle
 import androidx.compose.material.icons.filled.RestaurantMenu
 import androidx.compose.material.icons.filled.TableBar
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
 import androidx.compose.material3.Switch
 import androidx.compose.material3.SwitchDefaults
 import androidx.compose.material3.Text
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.compose.runtime.MutableState
 import androidx.compose.runtime.collectAsState
 import androidx.compose.runtime.getValue
 import androidx.compose.runtime.mutableIntStateOf
 import androidx.compose.runtime.mutableStateListOf
 import androidx.compose.runtime.mutableStateOf
 import androidx.compose.runtime.remember
 import androidx.compose.runtime.setValue
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.shadow
 import androidx.compose.ui.graphics.Color
 import androidx.compose.ui.layout.ContentScale
 import androidx.compose.ui.platform.LocalConfiguration
 import androidx.compose.ui.platform.LocalContext
 import androidx.compose.ui.res.painterResource
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
 import coil.compose.SubcomposeAsyncImage
 import com.google.accompanist.systemuicontroller.SystemUiController
 import com.soportereal.invefacon.R
 import com.soportereal.invefacon.funciones_de_interfaces.BBasicTextField
 import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas
 import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
 import com.soportereal.invefacon.funciones_de_interfaces.guardarParametroSiNoExiste
 import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
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
 import org.json.JSONArray
 import org.json.JSONObject
 import java.util.Locale


@SuppressLint("MutableCollectionMutableState")
@Composable
fun InterfazModuloSacLarge(
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
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, true)
    var datosIngresadosBarraBusqueda by remember  { mutableStateOf("") }
    val lazyStateMesas= rememberLazyListState()
    val lazyStateCuentasActivas= rememberLazyListState()
    var listaCuentasActivasActuales by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    var listaMesasActualesFiltradas by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    val objectoProcesadorDatosApi= ProcesarDatosModuloSac(token)
    var iniciarMenuCrearMesa by remember { mutableStateOf(false) }
    var nombreNuevaMesa by remember { mutableStateOf("") }
    var nombreNuevaPersona by remember { mutableStateOf("") }
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
    var actualizarListaMesas by remember { mutableStateOf(false) }
    var regresarPantallaAnterior by remember { mutableStateOf(false) }
    var actualizarSubCuentasYMesas by remember { mutableStateOf(false) }
    var cantidadArticulosComandados by remember { mutableIntStateOf(0) }
    var iniciarMenuCrearPersona by remember { mutableStateOf(false) }
    var iniciarCreacionNuevaPersona by remember { mutableStateOf(false) }
    var iniciarMenuAjustes by remember { mutableStateOf(false) }
    val context = LocalContext.current
    guardarParametroSiNoExiste(context, "prmImp2", "1")
    var valorPrmImp2 by remember { mutableStateOf(obtenerParametro(context, "prmImp2")) }
    var codUsuarioIngresado by remember { mutableStateOf(codUsuario) }
    var passwordIngresada by remember { mutableStateOf("") }
    var aplicarImp2 by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            delay(500)
            navController.navigate(
                RutasPatallas.SacComanda.ruta+"/"+mesaActual.nombre+"/"+mesaActual.salon+"/"+token+"/"+nombreEmpresa+"/"+codUsuario+"/"+mesaActual.estado
            ){
                restoreState= true
                launchSingleTop=true
            }
        }
    }

    LaunchedEffect(iniciarCreacionNuevaMesa) {
        if (iniciarCreacionNuevaMesa){
            if (nombreNuevaMesa.isEmpty() && nombreSalonNuevaMesa.isEmpty()){
                mostrarMensajeError("Complete los campos")
            }else{
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                delay(500)
                val result = objectoProcesadorDatosApi.crearNuevaMesa(nombreNuevaMesa, nombreSalonNuevaMesa)
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                }
                nombreNuevaMesa=""
                nombreSalonNuevaMesa=""
                actualizarListaMesas= true
                iniciarMenuCrearMesa=false
                iniciarCreacionNuevaMesa=false
            }
            iniciarCreacionNuevaMesa = false
        }
    }

    LaunchedEffect(Unit) {
        delay(500)
        while (true){
            val listaMesas = mutableListOf<Mesa>()
            val listaCuentasActivas = mutableListOf<Mesa>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda)
            if (result!=null){
                if (result.getString("status")=="ok"){
                    val data = result.getJSONObject("data")
                    val datosMesas= data.getJSONArray("mesas")
                    for (i in 0 until datosMesas.length()){
                        val datoMesa= datosMesas.getJSONObject(i)
                        val estado= if(datoMesa.getString("CodEstado")=="1" || datoMesa.getString("CodEstado")=="2") datoMesa.getString("CodEstado") else "0"
                        val mesa = Mesa(
                            nombre = datoMesa.getString("mesa"),
                            idMesa = datoMesa.getString("Consec"),
                            cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                            tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                            total = datoMesa.getString("monto"),
                            estado = estado,
                            salon = datoMesa.getString("salon")
                        )
                        listaMesas.add(mesa)
                    }

                    val datosCuentasActivas = data.getJSONArray("cuentasActivas")
                    for (i in 0 until datosCuentasActivas.length()) {
                        val datoMesa= datosCuentasActivas.getJSONObject(i)
                        val mesa = Mesa(
                            nombre = datoMesa.getString("mesa"),
                            salon = datoMesa.getString("salon"),
                            idMesa = datoMesa.getString("Consec"),
                            cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                            tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                            total = datoMesa.getString("monto"),
                            estado = datoMesa.getString("CodEstado")
                        )
                        listaCuentasActivas.add(mesa)
                    }
                }
            }

            if (listaMesasActualesFiltradas!=listaMesas){
                listaMesasActualesFiltradas=listaMesas
            }
            if (listaCuentasActivasActuales!=listaCuentasActivas){
                listaCuentasActivasActuales=listaCuentasActivas
            }
            if (isPrimeraVezCargando){
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                isPrimeraVezCargando=false
                isCargandoMesas=false
            }
            delay(10000)
        }
    }

    LaunchedEffect(actualizarListaMesas,datosIngresadosBarraBusqueda) {
        if (actualizarListaMesas){
            val listaMesas = mutableListOf<Mesa>()
            val listaCuentasActivas = mutableListOf<Mesa>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda)
            if (result!=null){
                if (result.getString("status")=="ok"){
                    val data = result.getJSONObject("data")
                    val datosMesas= data.getJSONArray("mesas")
                    for (i in 0 until datosMesas.length()){
                        val datoMesa= datosMesas.getJSONObject(i)
                        val estado= if(datoMesa.getString("CodEstado")=="1" || datoMesa.getString("CodEstado")=="2") datoMesa.getString("CodEstado") else "0"
                        val mesa = Mesa(
                            nombre = datoMesa.getString("mesa"),
                            idMesa = datoMesa.getString("Consec"),
                            cantidadSubcuentas = datoMesa.getString("cantidad_subcuentas"),
                            tiempo = if(!datoMesa.isNull("minutos")) datoMesa.getInt("minutos") else 0,
                            total = datoMesa.getString("monto"),
                            estado = estado,
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
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                actualizarListaMesas=false
            }
        }
    }

    LaunchedEffect(iniciarMenuMesaComandada, estadoBtOkRespuestaApi) {
        if(iniciarMenuMesaComandada && !iniciarMenuMoverArticulo) {
            articulosComandados.clear()
            opcionesSubCuentas.value.clear()
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.obtenerDatosMesaComandada(mesaActual.nombre)
            if (result != null) {
                if (result.getString("code")== "200"){
                    val data = result.getJSONObject("data")
                    val subCuentas = data.getJSONArray("Subcuentas")
                    val datosLineas = data.getJSONArray("datosLineas")
                    opcionesSubCuentas.value.clear()

                    for (i in 0 until subCuentas.length()) {
                        opcionesSubCuentas.value[subCuentas[i].toString()] = subCuentas[i].toString()
                    }

                    subCuentaSeleccionada= subCuentas[0].toString()
                    articulosComandados.clear()

                    for (i in 0 until datosLineas.length()) {
                        val datoLinea = datosLineas.getJSONObject(i)
                        val articulosLineaString = datoLinea.getString("articulosLinea")
                        val articulosLinea = JSONArray(articulosLineaString)
                        val articulo= articulosLinea.getJSONObject(0)
                        val articuloComadado = ArticuloComandado(
                            Consec = articulo.getString("Consec"),
                            Cod_Articulo = articulo.getString("Cod_Articulo"),
                            Cantidad = articulo.getInt("Cantidad"),
                            Precio = articulo.getDouble("Precio"),
                            Imp1 = articulo.getString("Imp1"),
                            Imp2 = articulo.getString("Imp2"),
                            Linea = articulo.getString("Linea"),
                            SubCuenta = articulo.getString("SubCuenta"),
                            nombre= articulo.getString("nombreArticulo"),
                            isCombo = articulo.getInt("isCombo")
                        )
                        if (articulosLinea.length()>1){
                            val listaArticulosGrupo = mutableListOf<ArticuloSacGrupo>()
                            for (a in 1 until articulosLinea.length()){
                                val datoArticulo = articulosLinea.getJSONObject(a)
                                val articuloGrupo = ArticuloSacGrupo(
                                    nombre = datoArticulo.getString("nombreArticulo"),
                                    precio = datoArticulo.getDouble("Precio"),
                                    cantidad = datoArticulo.getInt("Cantidad")
                                )
                                listaArticulosGrupo.add(articuloGrupo)
                            }
                            articuloComadado.articulos= listaArticulosGrupo
                        }
                        articulosComandados.add(articuloComadado)
                    }
                }else{
                    iniciarMenuMesaComandada=false
                }
            }
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            iniciarCalculoMontos= true
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
            cantidadArticulosComandados = 0
            for (i in 0 until articulosComandados.size){
                if (articulosComandados[i].SubCuenta==subCuentaSeleccionada){
                    cantidadArticulosComandados+=1
                }
            }
            iniciarCalculoMontos= false
        }
    }

    LaunchedEffect(agregarArticulo, eliminarArticulo) {
        if(agregarArticulo  || (eliminarArticulo&& anotacionComanda.isNotEmpty())){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val jsonComandaDetalle = JSONArray()
            val codigo=articuloActualSeleccionado.Cod_Articulo
            val cantidad= if (agregarArticulo) cantidadArticulos else -cantidadArticulos
            val precio = articuloActualSeleccionado.Precio
            val imp1= "13.00"
            val imp2 = "10.00"
            val anotacion = anotacionComanda
            val subCuenta = articuloActualSeleccionado.SubCuenta
            val linea = articuloActualSeleccionado.Linea
            val isCombo = articuloActualSeleccionado.isCombo
            val jsonObject = JSONObject().apply {
                put("codigo", codigo)
                put("cantidad", cantidad)
                put("precio", precio)
                put("imp1",imp1)
                put("imp2", imp2)
                put("anotacion", anotacion)
                put("subcuenta", subCuenta)
                put("linea", linea)
                put("isCombo", isCombo)
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
                val result= objectoProcesadorDatosApi.comandarSubCuentaEliminarArticulos(
                    codUsuario = codUsuario,
                    salon = mesaActual.salon,
                    mesa = mesaActual.nombre,
                    jsonComandaDetalle = jsonComandaDetalle
                )
                if (result != null) {
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result )
                }
            }
            actualizarListaMesas= true
            delay(100)
            eliminarArticulo= false
            agregarArticulo= false
        }

        if(eliminarArticulo && anotacionComanda.isEmpty()){
            mostrarMensajeError("Ingrese el motivo de la eliminacion del articulo")
            actualizarListaMesas= true
            eliminarArticulo= false
            agregarArticulo= false
        }

    }

    LaunchedEffect(quitarMesa) {
        if(quitarMesa){
            if (passwordIngresada.isEmpty() || codUsuarioIngresado.isEmpty()){
                mostrarMensajeError("Complete los campos para continuar")
                quitarMesa=false
            }else{
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = objectoProcesadorDatosApi.quitarMesa(mesaActual.nombre, passwordIngresada, codUsuarioIngresado)
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                    if (result.getString("status")=="ok"){
                        iniciarMenuMesaComandada=false
                        actualizarListaMesas= true
                    }
                }
                iniciarMenuQuitarMesa=false
                codUsuarioIngresado=codUsuario
                passwordIngresada=""
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                quitarMesa= false
            }


        }
    }

    LaunchedEffect(pedirCuenta) {
        if(pedirCuenta){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.pedirCuenta(mesaActual.nombre)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            actualizarListaMesas= true
            pedirCuenta= false
        }
    }

    LaunchedEffect(actualizarSubCuentasYMesas, mesaDestino) {
        if(actualizarSubCuentasYMesas){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            var result = objectoProcesadorDatosApi.obetenerNombresMesas()
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                if (result.getString("status")!="ok"){
                    objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                }else{
                    val data= result.getJSONObject("data")
                    val mesas= data.getJSONArray("Mesas")
                    for(i in 0 until mesas.length()){
                        val datosMesa = mesas.getJSONObject(i)
                        opcionesMesas.value[datosMesa.getString("nombreMesa")] = datosMesa.getString("nombreMesa")+"-"+datosMesa.getString("nombreSalon")
                    }
                    if (mesaDestino.isEmpty()) {
                        mesaDestino = opcionesMesas.value[mesaActual.nombre].toString()
                    }
                }


            }
            subCuentaDestinoArticulo=""
            opcionesSubCuentasDestino.value.clear()
            if (iniciarMenuMoverArticulo){
                result = objectoProcesadorDatosApi.obetenerSubCuentasMesa(mesaDestino)
                if (result!= null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    if (result.getString("status")!="ok"){
                        objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    }else{
                        val data= result.getJSONObject("data")
                        val subCuentas= data.getJSONArray("Subcuentas")
                        for( i in 0 until subCuentas.length()){
                            opcionesSubCuentasDestino.value[subCuentas.getString(i)] = subCuentas.getString(i)
                        }
                        if (subCuentaDestinoArticulo.isEmpty()){
                            subCuentaDestinoArticulo= opcionesSubCuentasDestino.value.keys.first()
                        }
                    }
                }
            }
            if (mesaDestino.isNotEmpty() || subCuentaDestinoArticulo.isNotEmpty()){
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                actualizarSubCuentasYMesas = false
            }
        }
    }

    LaunchedEffect(moverArticulo) {
        if(moverArticulo){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result= objectoProcesadorDatosApi.moverArticulo(
                codigoArticulo = articuloActualSeleccionado.Cod_Articulo,
                cantidadArticulos = cantidadArticulos.toString(),
                mesa = mesaActual.nombre,
                mesaDestino = mesaDestino,
                subCuenta = articuloActualSeleccionado.SubCuenta,
                subCuentaDestino = subCuentaDestinoArticulo,
                codUsuario = codUsuario,
                linea = articuloActualSeleccionado.Linea,
                isCombo = articuloActualSeleccionado.isCombo
            )
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            iniciarMenuMoverArticulo= false
            mesaDestino= ""
            subCuentaDestinoArticulo=""
            actualizarListaMesas= true
            moverArticulo= false
        }
    }

    LaunchedEffect(moverMesa) {
        if(moverMesa){
            if (passwordIngresada.isEmpty() || codUsuarioIngresado.isEmpty()){
                mostrarMensajeError("Complete los campos para continuar")
                moverMesa=false
            }else{
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = objectoProcesadorDatosApi.moverMesa(mesa = mesaActual.nombre, mesaDestino = mesaDestino, password = passwordIngresada, codUsuario = codUsuarioIngresado)
                if (result!= null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                }
                iniciarMenuMoverMesa= false
                mesaDestino=""
                codUsuarioIngresado=codUsuario
                passwordIngresada=""
                objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                moverMesa= false
            }


        }
    }

    LaunchedEffect(regresarPantallaAnterior) {
        if (regresarPantallaAnterior){
            navController.popBackStack()
            estadoRespuestaApi.cambiarEstadoRespuestaApi()
        }
    }

    LaunchedEffect(iniciarCreacionNuevaPersona) {
        if(iniciarCreacionNuevaPersona){
            if (nombreNuevaPersona.isEmpty()){
                mostrarMensajeError("Ingrese el nombre de la Persona")
            }else{
                mesaActual=Mesa(
                    nombre =nombreNuevaPersona,
                    salon = "PERSONA"
                )
                iniciarPantallaSacComanda = true
                nombreNuevaPersona=""
                iniciarMenuCrearPersona=false
            }
            iniciarCreacionNuevaMesa = false
        }
    }

    LaunchedEffect(aplicarImp2) {
        if(aplicarImp2){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.aplicarImp2( nombreMesa= mesaActual.nombre)
            if (result!= null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            aplicarImp2= false
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
                Modifier.height(objetoAdaptardor.ajustarAltura(35))
            } else {
                Modifier.width(objetoAdaptardor.ajustarAncho(120))
            },
            onClick = {
                onClick(true)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor =Color(color), // Color de fondo del botón
                contentColor = Color.White,
                disabledContainerColor = Color.Red,
                disabledContentColor = Color.White
            ),contentPadding = if (quitarPadInterno) PaddingValues(0.dp) else PaddingValues(8.dp)
        ) {
            Text(
                text,
                fontFamily = fontAksharPrincipal,
                fontWeight = FontWeight.Medium,
                fontSize = obtenerEstiloLabel(),
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
                .width(objetoAdaptardor.ajustarAncho(365)),
            contentAlignment = Alignment.CenterEnd
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
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
                        fontSize = obtenerEstiloBody(),
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
                                fontSize = obtenerEstiloBody(),
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                            )
                            IconButton(
                                onClick = {
                                    if(articuloComandado.articulos.isEmpty()){
                                        articuloActualSeleccionado= articuloComandado
                                        iniciarVentanaAgregarArticulo = true
                                    }
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAltura(22))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = "Agregar Articulo",
                                    tint =  if(articuloComandado.articulos.isEmpty()) Color.Black else Color.White,
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
                            iniciarMenuMoverArticulo = true
                            actualizarSubCuentasYMesas = true
                        },
                        quitarPadInterno = true
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                }
                HorizontalDivider()
                for (i in 0 until articuloComandado.articulos.size){
                    val articuloCombo = articuloComandado.articulos[i]
                    Row(
                        horizontalArrangement = Arrangement.End,
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
                            articuloCombo.nombre,
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            fontSize = obtenerEstiloLabel(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200)).padding(2.dp),
                            color = Color.DarkGray
                        )
                        Text(
                            "+ \u20A1 "+String.format(Locale.US, "%,.2f", "${articuloCombo.precio*articuloCombo.cantidad}".replace(",", "").toDouble()),
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Light,
                            fontSize = obtenerEstiloLabel(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.End,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(100)).padding(2.dp)
                        )
                    }
                    HorizontalDivider()
                }
                Row {
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    Text(
                        "\u20A1 "+String.format(Locale.US, "%,.2f", articuloComandado.montoTotal.toString().replace(",", "").toDouble()),
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
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
            .fillMaxSize()
            .background(Color(0xFFFFFFFF))
            .statusBarsPadding()
            .navigationBarsPadding()
    ){
        val (bxSuperior, bxContenedorMesas ,txfBarraBusqueda,
            bxContenerdorCuentasActivas, bxContenedorBotones, flechaRegresar)= createRefs()

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
                    fontSize = obtenerEstiloDisplay(),
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }

        IconButton(
            onClick = {regresarPantallaAnterior=true},
            modifier = Modifier
                .constrainAs(flechaRegresar){
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

        BBasicTextField(
            value = datosIngresadosBarraBusqueda,
            onValueChange =  { nuevoValor ->
                datosIngresadosBarraBusqueda = nuevoValor
                actualizarListaMesas= true
            },
            fontFamily = fontAksharPrincipal,
            alto = 45,
            ancho = 200,
            objetoAdaptardor = objetoAdaptardor,
            modifier = Modifier.constrainAs(txfBarraBusqueda) {
                top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
            }
        )

        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(45))
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
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        iniciarMenuCrearMesa= true
                    }, contentPadding = PaddingValues(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Crear mesa",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloBody(),
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
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        iniciarMenuCrearPersona= true
                    }, contentPadding = PaddingValues(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Persona",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloBody(),
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
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        iniciarMenuAjustes= true
                    }, contentPadding = PaddingValues(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Ajustes",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloBody(),
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
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp),
                    onClick = {
                        actualizarListaMesas= true
                    }, contentPadding = PaddingValues(2.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                        Text(
                            "Refrescar",
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

        Box(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(690))
                .height(objetoAdaptardor.ajustarAltura(515))
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
                                    navControllerPantallasModuloSac = navController,
                                    iniciarMenuDetalleComanda = { valor-> iniciarMenuMesaComandada=valor},
                                    mesaSeleccionada = {datosMesaActual-> mesaActual= datosMesaActual },
                                    token = token,
                                    nombreEmpresa = nombreEmpresa,
                                    codUsuario = codUsuario
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
                .width(objetoAdaptardor.ajustarAncho(245))
                .height(objetoAdaptardor.ajustarAltura(530))
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
                        fontSize = obtenerEstiloTitle(),
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
                        .wrapContentHeight()
                        .background(Color(0xFFFAFAFA)),
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                        Text(
                            "Mesa",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloTitle(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(85))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )

                        Text(
                            "Tiempo",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloTitle(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(72))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )

                        Text(
                            "Total",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloTitle(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(80))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )
                    }

                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFAFAFA)),
                ){
                    LazyColumn(
                        state = lazyStateCuentasActivas
                    ) {
                        items(listaCuentasActivasActuales){ mesa->
                            if (mesa.estado!="null"){
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(0xFFFAFAFA))
                                        .clickable {
                                            mesaActual=mesa
                                            iniciarMenuMesaComandada=true
                                        }
                                ){
                                    Row(
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(2)))
                                        Text(
                                            mesa.nombre,
                                            fontFamily = fontAksharPrincipal,
                                            color = Color.Black,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloBody(),
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1,
                                            textAlign = TextAlign.Center,
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(85))
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
                                            fontSize = obtenerEstiloBody(),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            maxLines = 1,
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(72))
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
                                            fontSize = obtenerEstiloBody(),
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.End,
                                            modifier = Modifier
                                                .width(objetoAdaptardor.ajustarAncho(80))
                                                .padding(objetoAdaptardor.ajustarAncho(2))
                                        )
                                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))
                                    }
                                }
                            }

                        }
                        item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16)))}
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

                        BBasicTextField(
                            value = nombreNuevaMesa,
                            onValueChange =  { nuevoValor ->
                                nombreNuevaMesa = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Nombre de la Mesa",
                            icono = Icons.Filled.TableBar,
                            objetoAdaptardor = objetoAdaptardor
                        )

                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

                        BBasicTextField(
                            value = nombreSalonNuevaMesa,
                            onValueChange =  { nuevoValor ->
                                nombreSalonNuevaMesa = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Nombre de Salon",
                            icono = Icons.Filled.Place,
                            objetoAdaptardor = objetoAdaptardor
                        )

                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
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

    if(iniciarMenuCrearPersona) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Crear Nueva Persona",
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
                        BBasicTextField(
                            value = nombreNuevaPersona,
                            onValueChange =  { nuevoValor ->
                                nombreNuevaPersona = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Nombre de la Persona",
                            icono = Icons.Filled.TableBar,
                            objetoAdaptardor = objetoAdaptardor
                        )
                        // Spacer separador de componente
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        iniciarCreacionNuevaPersona=true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
                        disabledContentColor = Color.White
                    )
                ) {
                    Text(
                        "Continuar",
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
                        iniciarMenuCrearPersona= false
                        nombreNuevaPersona=""

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
                    .wrapContentWidth()
                    .heightIn(max = objetoAdaptardor.ajustarAltura(500))
                    .align(Alignment.Center),
                shape = RoundedCornerShape(16.dp),
                color = Color.White
            )  {
                Box(
                    modifier = Modifier.padding(objetoAdaptardor.ajustarAltura(30)),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Articulos Comandados ${mesaActual.nombre}-${mesaActual.salon}",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloDisplay(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black,
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                contentAlignment = Alignment.Center
                            ){
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
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
                                        medidaAncho = 80
                                    )
                                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                    AgregarBt(
                                        text = "Quitar mesa",
                                        color = 0xFFFF5722,
                                        onClick = {
                                            iniciarMenuQuitarMesa= true
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                    AgregarBt(
                                        text = "Mover Mesa",
                                        color = 0xFF244BC0,
                                        onClick = {
                                            iniciarMenuMoverMesa = true
                                            actualizarSubCuentasYMesas = true
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))


                                }
                            }
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(16)))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ){
                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(12)))
                                Box(
                                    modifier = Modifier
                                        .heightIn(max = objetoAdaptardor.ajustarAltura(320))
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
                                Row {
                                    Box(
                                        contentAlignment = Alignment.CenterStart,
                                        modifier = Modifier
                                            .height(objetoAdaptardor.ajustarAltura(50))
                                            .width(objetoAdaptardor.ajustarAncho(165))
                                    ){
                                        Text(
                                            "Articulos: $cantidadArticulosComandados",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloLabel(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Start,
                                            color = Color.Black,
                                        )
                                    }
                                    Box(
                                        contentAlignment = Alignment.CenterEnd,
                                        modifier = Modifier
                                            .height(objetoAdaptardor.ajustarAltura(50))
                                            .width(objetoAdaptardor.ajustarAncho(200))
                                    ){
                                        Text(
                                            "Total $montoTotalComandado",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloTitle(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.End,
                                            color = Color.Black,
                                        )
                                    }
                                }

                            }
                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(16)))
                            Box(
                                contentAlignment = Alignment.Center
                            ){
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ){
                                    AgregarBt(
                                        text = "Pedir Cuenta",
                                        color = 0xFF244BC0,
                                        onClick = {
                                            pedirCuenta= false
                                            pedirCuenta= true
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                    AgregarBt(
                                        text = "+10",
                                        color = 0xFF244BC0,
                                        onClick = {
                                            aplicarImp2=true
                                        }
                                    )

                                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))

                                    AgregarBt(
                                        text = "Agregar",
                                        color =  0xFF22B14C,
                                        onClick = { iniciarPantallaSacComanda= true}
                                    )

                                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))

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
                    fontSize = obtenerEstiloHead(),
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
                            fontSize = obtenerEstiloTitle(),
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
                                    fontSize = obtenerEstiloBody(),
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
                                    fontSize = obtenerEstiloBody(),
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

                        BBasicTextField(
                            value = anotacionComanda,
                            onValueChange =  { nuevoValor ->
                                anotacionComanda = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = if (iniciarVentanaAgregarArticulo) "Ingrese la anotacion" else "Ingrese el motivo",
                            icono = Icons.Filled.EditNote,
                            objetoAdaptardor = objetoAdaptardor
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
                        containerColor = Color.Red, // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color.Red,
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
                        containerColor = Color(0xFF244BC0),
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .clickable(enabled = false) {},
            contentAlignment = Alignment.Center
        ){
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
                            "Mover Articulo",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloHead(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )
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
                            fontSize = obtenerEstiloTitle(),
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
                                    fontSize = obtenerEstiloTitle(),
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
                                    fontSize = obtenerEstiloBody(),
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

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AgregarTextFieldMultifuncional(
                                label = "Mesa Destino",
                                opciones2 = opcionesMesas,
                                usarOpciones2 = true,
                                contieneOpciones = true,
                                nuevoValor = { nuevoValor->
                                    actualizarSubCuentasYMesas = true
                                    mesaDestino= nuevoValor
                                },
                                valor = opcionesMesas.value[mesaDestino]?:"Seleccione un Mesa",
                                isUltimo = true,
                                tomarAnchoMaximo = false,
                                medidaAncho = 100
                            )
                            AgregarTextFieldMultifuncional(
                                label = "Sub-Cuenta Destino",
                                opciones2 = opcionesSubCuentasDestino,
                                usarOpciones2 = true,
                                contieneOpciones = true,
                                nuevoValor = { nuevoValor->
                                    subCuentaDestinoArticulo= nuevoValor
                                },
                                valor = subCuentaDestinoArticulo,
                                isUltimo = true,
                                tomarAnchoMaximo = false,
                                medidaAncho = 90,
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

                        Row {
                            Button(
                                onClick = {
                                    moverArticulo= true
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Red, // Color de fondo del botón
                                    contentColor = Color.White,
                                    disabledContainerColor = Color.Red,
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

                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                            Button(
                                onClick = {
                                    iniciarMenuMoverArticulo= false
                                    cantidadArticulos= 1
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF244BC0), // Color de fondo del botón
                                    contentColor = Color.White,
                                    disabledContainerColor = Color(0xFF244BC0),
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

    if (iniciarMenuMoverMesa){
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
                            "Mover Mesa",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(27),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        BBasicTextField(
                            value = codUsuarioIngresado,
                            onValueChange =  { nuevoValor ->
                                codUsuarioIngresado = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Ingrese el codigo de usuario",
                            icono = Icons.Filled.Person,
                            objetoAdaptardor = objetoAdaptardor
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        BBasicTextField(
                            value = passwordIngresada,
                            onValueChange =  { nuevoValor ->
                                passwordIngresada = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Ingrese la contraseña",
                            icono = Icons.Filled.Password,
                            objetoAdaptardor = objetoAdaptardor
                        )

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))

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
                                valor = opcionesMesas.value[mesaDestino]?:"Seleccione una Mesa",
                                isUltimo = true,
                                tomarAnchoMaximo = false,
                                medidaAncho = 100
                            )
                        }
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        Row {
                            Button(
                                onClick = {
                                    moverMesa= true
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

                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))

                            Button(
                                onClick = {
                                    codUsuarioIngresado=codUsuario
                                    passwordIngresada=""
                                    iniciarMenuMoverMesa= false
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF244BC0), // Color de fondo del botón
                                    contentColor = Color.White,
                                    disabledContainerColor = Color(0xFF244BC0),
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

    if (iniciarMenuAjustes){
        var estado by remember { mutableStateOf(valorPrmImp2 != "0") }
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
                            "Ajustes",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = objetoAdaptardor.ajustarFont(27),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "Cobrar impuesto de Servicio",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloTitle(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                            Switch(
                                checked = estado,
                                onCheckedChange = {
                                    actualizarParametro(context, "prmImp2", if(valorPrmImp2=="0") "1" else "0")
                                    valorPrmImp2 = obtenerParametro(context, "prmImp2")
                                    estado = valorPrmImp2 != "0"
                                },colors = SwitchDefaults.colors(
                                    checkedTrackColor = Color(0xFF1D3FA4)
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        Button(
                            onClick = {
                                iniciarMenuAjustes= false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red, // Color de fondo del botón
                                contentColor = Color.White,
                                disabledContainerColor = Color.Red,
                                disabledContentColor = Color.White
                            )
                        ) {
                            Text(
                                "Salir",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
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
                        BBasicTextField(
                            value = nombreNuevaSubCuenta,
                            onValueChange =  { nuevoValor ->
                                nombreNuevaSubCuenta = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Nombre de la Sub-Cuenta",
                            icono = Icons.Filled.AccountTree,
                            objetoAdaptardor = objetoAdaptardor
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
                            opcionesSubCuentasDestino.value[nombreNuevaSubCuenta]=nombreNuevaSubCuenta
                            subCuentaDestinoArticulo= nombreNuevaSubCuenta
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

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Text(
                            "¿Desea eliminar todos los articulos de la mesa?",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloBody(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Justify,
                            color = Color.Black
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        BBasicTextField(
                            value = codUsuarioIngresado,
                            onValueChange =  { nuevoValor ->
                                codUsuarioIngresado = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Ingrese el codigo de usuario",
                            icono = Icons.Filled.Person,
                            objetoAdaptardor = objetoAdaptardor
                        )
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                        BBasicTextField(
                            value = passwordIngresada,
                            onValueChange =  { nuevoValor ->
                                passwordIngresada = nuevoValor
                            },
                            fontFamily = fontAksharPrincipal,
                            alto = 70,
                            ancho = 300,
                            placeholder = "Ingrese la contraseña",
                            icono = Icons.Filled.Password,
                            objetoAdaptardor = objetoAdaptardor
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        quitarMesa=true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red, // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color.Red,
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
                        codUsuarioIngresado=codUsuario
                        passwordIngresada=""
                        iniciarMenuQuitarMesa= false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF244BC0), // Color de fondo del botón
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFF244BC0),
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
    mesaSeleccionada: (Mesa)->Unit,
    token : String,
    nombreEmpresa: String,
    codUsuario: String
){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenidoCompact(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    var iniciarPantallaSacComanda by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda && datosMesa.estado=="0"){
            objetoEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            iniciarMenuDetalleComanda(false)
            delay(500)
            navControllerPantallasModuloSac?.navigate(
                RutasPatallas.SacComanda.ruta+"/"+datosMesa.nombre+"/"+datosMesa.salon+"/"+token+"/"+nombreEmpresa+"/"+codUsuario+"/"+datosMesa.estado
            ){
                restoreState= true
                launchSingleTop=true
            }
        }
        if (iniciarPantallaSacComanda && datosMesa.estado!="0"){
            iniciarMenuDetalleComanda(true)
            iniciarPantallaSacComanda=false
        }
    }

    Card(
        modifier = Modifier
            .wrapContentHeight()
            .width(objetoAdaptardor.ajustarAncho(51))
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
                .width(objetoAdaptardor.ajustarAncho(130))
                .wrapContentHeight()
                .background(
                    when (datosMesa.estado) {
                        "1" -> {
                            Color(0xFF244BC0)
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

                Text(text = datosMesa.nombre,
                    fontFamily = fontAksharPrincipal,
                    fontWeight =    FontWeight.SemiBold,
                    fontSize = obtenerEstiloTitle(),
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
                Column {
                    Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                    Text(
                        text =  if (datosMesa.estado == "0")  "Disponible" else "Sub Cuentas: ${datosMesa.cantidadSubcuentas}",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
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

                    Text(text =  if (datosMesa.estado == "0")  "" else "Tiempo: $tiempo",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
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
                    Text(text =  if (datosMesa.estado == "0")  "" else "Total: $totalMiles",
                        fontFamily = fontAksharPrincipal,
                        fontWeight =    FontWeight.Light,
                        fontSize = obtenerEstiloBody(),
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


@Composable
@Preview(widthDp = 964, heightDp = 523)
private fun Preview(){
    val nav = rememberNavController()
    InterfazModuloSacLarge("", null, nav, "","")
}
