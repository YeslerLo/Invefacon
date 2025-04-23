package com.soportereal.invefacon.interfaces.modulos.sac

 import android.annotation.SuppressLint
 import androidx.compose.animation.AnimatedVisibility
 import androidx.compose.animation.core.tween
 import androidx.compose.animation.expandVertically
 import androidx.compose.animation.fadeIn
 import androidx.compose.animation.fadeOut
 import androidx.compose.animation.shrinkVertically
 import androidx.compose.animation.slideInHorizontally
 import androidx.compose.animation.slideInVertically
 import androidx.compose.animation.slideOutVertically
 import androidx.compose.foundation.Image
 import androidx.compose.foundation.background
 import androidx.compose.foundation.clickable
 import androidx.compose.foundation.layout.Arrangement
 import androidx.compose.foundation.layout.Box
 import androidx.compose.foundation.layout.Column
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
 import androidx.compose.foundation.layout.widthIn
 import androidx.compose.foundation.layout.wrapContentHeight
 import androidx.compose.foundation.layout.wrapContentSize
 import androidx.compose.foundation.layout.wrapContentWidth
 import androidx.compose.foundation.lazy.LazyColumn
 import androidx.compose.foundation.lazy.LazyRow
 import androidx.compose.foundation.lazy.items
 import androidx.compose.foundation.lazy.itemsIndexed
 import androidx.compose.foundation.lazy.rememberLazyListState
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.AccountTree
 import androidx.compose.material.icons.filled.AddCircle
 import androidx.compose.material.icons.filled.ArrowBackIosNew
 import androidx.compose.material.icons.filled.Badge
 import androidx.compose.material.icons.filled.Delete
 import androidx.compose.material.icons.filled.EditNote
 import androidx.compose.material.icons.filled.Email
 import androidx.compose.material.icons.filled.KeyboardArrowDown
 import androidx.compose.material.icons.filled.KeyboardArrowUp
 import androidx.compose.material.icons.filled.MoreHoriz
 import androidx.compose.material.icons.filled.Password
 import androidx.compose.material.icons.filled.Person
 import androidx.compose.material.icons.filled.Phone
 import androidx.compose.material.icons.filled.Place
 import androidx.compose.material.icons.filled.RemoveCircle
 import androidx.compose.material.icons.filled.RestaurantMenu
 import androidx.compose.material.icons.filled.TableBar
 import androidx.compose.material3.AlertDialog
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
 import androidx.compose.ui.res.stringResource
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
 import com.soportereal.invefacon.funciones_de_interfaces.BButton
 import com.soportereal.invefacon.funciones_de_interfaces.RutasPatallas
 import com.soportereal.invefacon.funciones_de_interfaces.TextFieldMultifuncional
 import com.soportereal.invefacon.funciones_de_interfaces.actualizarParametro
 import com.soportereal.invefacon.funciones_de_interfaces.guardarParametroSiNoExiste
 import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeError
 import com.soportereal.invefacon.funciones_de_interfaces.mostrarMensajeExito
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerDatosClienteByCedula
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerParametroLocal
 import com.soportereal.invefacon.funciones_de_interfaces.validarExitoRestpuestaServidor
 import com.soportereal.invefacon.funciones_de_interfaces.FuncionesParaAdaptarContenido
 import com.soportereal.invefacon.interfaces.modulos.clientes.Cliente
 import com.soportereal.invefacon.interfaces.modulos.clientes.ProcesarDatosModuloClientes
 import com.soportereal.invefacon.interfaces.modulos.clientes.quitarTildesYMinusculas
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyBig
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodyMedium
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloBodySmall
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloDisplayMedium
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloLabelBig
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloLabelSmall
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleBig
 import com.soportereal.invefacon.funciones_de_interfaces.obtenerEstiloTitleSmall
 import com.soportereal.invefacon.funciones_de_interfaces.validacionCedula
 import com.soportereal.invefacon.funciones_de_interfaces.validarCorreo
 import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
 import com.soportereal.invefacon.interfaces.pantallas_principales.gestorEstadoPantallaCarga
 import kotlinx.coroutines.CoroutineScope
 import kotlinx.coroutines.Dispatchers
 import kotlinx.coroutines.Job
 import kotlinx.coroutines.delay
 import kotlinx.coroutines.launch
 import org.json.JSONArray
 import org.json.JSONObject
 import java.util.Locale


@SuppressLint("MutableCollectionMutableState")
@Composable
fun InterfazModuloSac(
    token: String,
    systemUiController: SystemUiController?,
    navController: NavController,
    nombreEmpresa: String,
    codUsuario: String,
    nombreUsuario : String
){
    systemUiController?.setStatusBarColor(Color(0xFF244BC0))
    systemUiController?.setNavigationBarColor(Color.Black)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, true)
    var datosIngresadosBarraBusqueda by remember  { mutableStateOf("") }
    var datosIngresadosBarraBusquedaCliente by remember  { mutableStateOf("") }
    val lazyStateMesas= rememberLazyListState()
    val lazyStateCuentasActivas= rememberLazyListState()
    var listaCuentasActivasActuales by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    var listaMesasActualesFiltradas by remember { mutableStateOf<List<Mesa>>(emptyList()) }
    val objectoProcesadorDatosApi= ProcesarDatosModuloSac(token)
    val objectoProcesadorDatosApiClientes= ProcesarDatosModuloClientes(token)
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
    var opcionesSubCuentasComandadas by remember { mutableStateOf<List<String>>(emptyList()) }
    var listaSalonesAtuales by remember { mutableStateOf<List<String>>(emptyList()) }
    val opcionesSubCuentasDestino: MutableState<LinkedHashMap<String, String>> = remember { mutableStateOf(LinkedHashMap()) }
    val opcionesMesas: MutableState<LinkedHashMap<String, String>> = remember { mutableStateOf(LinkedHashMap()) }
    var mesaDestino by remember { mutableStateOf("") }
    val articulosComandados = remember { mutableStateListOf<ArticuloComandado>() }
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
    var reactivarCuenta by remember { mutableStateOf(false) }
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
    var iniciarMenuCrearExpress by remember { mutableStateOf(false) }
    var iniciarMenuAjustes by remember { mutableStateOf(false) }
    val context = LocalContext.current
    guardarParametroSiNoExiste(context, "prmImp2$nombreEmpresa$codUsuario", "1")
    var valorPrmImp2 by remember { mutableStateOf(obtenerParametroLocal(context, "prmImp2$nombreEmpresa$codUsuario")) }
    var codUsuarioIngresado by remember { mutableStateOf(codUsuario) }
    var passwordIngresada by remember { mutableStateOf("") }
    var agregarImpuestoServicio by remember { mutableStateOf(false) }
    val lazyStateListaClientes= rememberLazyListState()
    var listaClientesActuales by remember { mutableStateOf<List<Cliente>>(emptyList()) }
    var isCargandoClientes by remember { mutableStateOf(false) }
    var apiConsultaActual by remember { mutableStateOf<Job?>(null) }
    val cortinaConsultaApi= CoroutineScope(Dispatchers.IO)
    var iniciarMenuCrearCliente by remember { mutableStateOf(false) }
    var iniciarBusquedaClienteByCedula by remember { mutableStateOf(false) }
    var cedulaCliente by remember { mutableStateOf("") }
    var nombreCliente by remember { mutableStateOf("") }
    var telefonoCliente by remember { mutableStateOf("") }
    var correoCliente by remember { mutableStateOf("") }
    var direccionCliente by remember { mutableStateOf("") }
    var agregarCliente by remember { mutableStateOf(false) }
    var isSubCuentaPedida by remember { mutableStateOf(true) }
    var salonActual by remember { mutableStateOf("TODOS") }
    var cambiarPrmImp2 by remember { mutableStateOf(false) }
    var estadoImp2 by remember { mutableStateOf(valorPrmImp2 != "0") }
    var iniciarMenuConfCambPrm by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            delay(500)
            navController.navigate(
                RutasPatallas.SacComanda.ruta+"/"+mesaActual.nombre+"/"+mesaActual.salon+
                        "/"+token+"/"+nombreEmpresa+"/"+codUsuario+"/"+mesaActual.estado+"/"
                        +mesaActual.clienteId+"/"+ subCuentaSeleccionada.ifEmpty { "JUNTOS" }+"/"+nombreUsuario
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
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = objectoProcesadorDatosApi.crearNuevaMesa(nombreNuevaMesa.uppercase(), nombreSalonNuevaMesa.uppercase())
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
            val listaSalones = mutableListOf<String>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda, if(salonActual=="TODOS") "" else salonActual)
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

                    val salones= data.getJSONArray("salones")
                    listaSalones.add("TODOS")
                    for( i in 0 until salones.length()){
                        val datoSalon = salones.getJSONObject(i)
                        listaSalones.add(datoSalon.getString("SalonNombre"))
                    }
                }
            }

            if (listaMesasActualesFiltradas!=listaMesas){
                listaMesasActualesFiltradas=listaMesas
            }
            if (listaCuentasActivasActuales!=listaCuentasActivas){
                listaCuentasActivasActuales=listaCuentasActivas
            }

            if(listaSalones!= listaSalonesAtuales){
                listaSalonesAtuales = listaSalones
            }
            if (isPrimeraVezCargando){
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                isPrimeraVezCargando=false
                isCargandoMesas=false
            }
            delay(10000)
        }
    }

    LaunchedEffect(actualizarListaMesas,datosIngresadosBarraBusqueda,salonActual) {
        if (actualizarListaMesas){
            isCargandoMesas = true
            val listaMesas = mutableListOf<Mesa>()
            val listaCuentasActivas = mutableListOf<Mesa>()
            val listaSalones = mutableListOf<String>()
            val result= objectoProcesadorDatosApi.obtenerListaMesas(datosIngresadosBarraBusqueda, if(salonActual=="TODOS") "" else salonActual)
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

                    val salones= data.getJSONArray("salones")
                    listaSalones.add("TODOS")
                    for( i in 0 until salones.length()){
                        val datoSalon = salones.getJSONObject(i)
                        listaSalones.add(datoSalon.getString("SalonNombre"))
                    }
                }
                if (listaMesasActualesFiltradas!=listaMesas){
                    listaMesasActualesFiltradas=listaMesas
                }
                if (listaCuentasActivasActuales!=listaCuentasActivas){
                    listaCuentasActivasActuales=listaCuentasActivas
                }

                if(listaSalones!= listaSalonesAtuales){
                    listaSalonesAtuales = listaSalones
                }
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                actualizarListaMesas=false
            }
            isCargandoMesas = false
        }
    }

    LaunchedEffect(iniciarMenuMesaComandada, estadoBtOkRespuestaApi) {
        if(iniciarMenuMesaComandada && !iniciarMenuMoverArticulo) {
            articulosComandados.clear()
            opcionesSubCuentas.value.clear()
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.obtenerDatosMesaComandada(mesaActual.nombre)
            if (result != null) {
                if (validarExitoRestpuestaServidor(result)
                    ){
                    val data = result.getJSONObject("data")
                    val subCuentas = data.getJSONArray("Subcuentas")
                    val datosLineas = data.getJSONArray("datosLineas")
                    opcionesSubCuentas.value.clear()
                    opcionesSubCuentasComandadas = emptyList()

                    for (i in 0 until subCuentas.length()) {
                        val datosSubCuenta = subCuentas.getJSONObject(i)
                        val nombre= datosSubCuenta.getString("nombre")
                        val estado= datosSubCuenta.getInt("estado")
                        if(estado==2){
                            opcionesSubCuentasComandadas = opcionesSubCuentasComandadas + nombre
                        }
                        opcionesSubCuentas.value[nombre] = nombre
                    }

                    if(subCuentas.length()>0){
                        subCuentaSeleccionada= subCuentas.getJSONObject(0).getString("nombre")
                        articulosComandados.clear()
                    }


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
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
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
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
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
                    jsonComandaDetalle = jsonComandaDetalle,
                    clienteId = mesaActual.clienteId
                )
                if (result != null) {
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result )
                }
            }
            actualizarListaMesas= true
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
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = objectoProcesadorDatosApi.quitarMesa(mesaActual.nombre, passwordIngresada, codUsuarioIngresado)
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                    if (result.getString("status")=="ok"){
                        iniciarMenuMesaComandada=false
                        actualizarListaMesas= true
                        iniciarMenuQuitarMesa=false
                        codUsuarioIngresado=codUsuario
                        passwordIngresada=""
                    }else{
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    }
                }
                quitarMesa= false
            }
        }
    }

    LaunchedEffect(pedirCuenta) {
        if(pedirCuenta){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.pedirCuenta(mesaActual.nombre, subCuentaSeleccionada)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                if (result.getString("status")=="ok" && result.getString("code")=="200"){
                    iniciarMenuMesaComandada = false
                    actualizarListaMesas= true
                }else{
                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                }
            }
        }
        pedirCuenta= false
    }

    LaunchedEffect(reactivarCuenta) {
        if(reactivarCuenta){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.reactivarCuenta(mesaActual.nombre, subCuentaSeleccionada)
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                if (result.getString("status")=="ok" && result.getString("code")=="200"){
                    actualizarListaMesas= true
                }else{
                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                }
            }
        }
        reactivarCuenta= false
    }

    LaunchedEffect(actualizarSubCuentasYMesas, mesaDestino) {
        if(actualizarSubCuentasYMesas){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            var result = objectoProcesadorDatosApi.obetenerNombresMesas()
            if (result!=null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                if (result.getString("status")!="ok"){
                    gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
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
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
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
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                actualizarSubCuentasYMesas = false
            }
        }
    }

    LaunchedEffect(moverArticulo) {
        if(moverArticulo){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result= objectoProcesadorDatosApi.moverArticulo(
                codigoArticulo = articuloActualSeleccionado.Cod_Articulo,
                cantidadArticulos = cantidadArticulos.toString(),
                mesa = mesaActual.nombre,
                mesaDestino = mesaDestino,
                subCuenta = articuloActualSeleccionado.SubCuenta,
                subCuentaDestino = subCuentaDestinoArticulo,
                codUsuario = codUsuario,
                linea = articuloActualSeleccionado.Linea,
                isCombo = articuloActualSeleccionado.isCombo,
                idCliente = mesaActual.clienteId
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
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = objectoProcesadorDatosApi.moverMesa(
                    mesa = mesaActual.nombre,
                    mesaDestino = mesaDestino,
                    password = passwordIngresada,
                    codUsuario = codUsuarioIngresado,
                    idCliente = mesaActual.clienteId
                )
                if (result!= null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                    if (result.getString("status")=="ok"){
                        actualizarListaMesas = true
                        iniciarMenuMesaComandada = false
                        iniciarMenuMoverMesa= false
                    }else{
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    }
                }
                mesaDestino=""
                passwordIngresada=""
            }
        }
        moverMesa= false
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
                    nombre =nombreNuevaPersona.uppercase(),
                    salon = "PERSONA"
                )
                subCuentaSeleccionada="JUNTOS"
                iniciarPantallaSacComanda = true
                nombreNuevaPersona=""
                iniciarMenuCrearPersona=false
            }
            iniciarCreacionNuevaMesa = false
        }
        iniciarCreacionNuevaPersona = false
    }

    LaunchedEffect(agregarImpuestoServicio) {
        if(agregarImpuestoServicio){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            val result = objectoProcesadorDatosApi.aplicarImp2( nombreMesa= mesaActual.nombre)
            if (result!= null){
                estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
            }
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
        }
        agregarImpuestoServicio= false
    }

    LaunchedEffect(datosIngresadosBarraBusquedaCliente, iniciarMenuCrearExpress) {

        if (iniciarMenuCrearExpress) {
            listaClientesActuales = emptyList()
            isCargandoClientes=true
            apiConsultaActual?.cancel()
            apiConsultaActual= cortinaConsultaApi.launch{
                delay(500)
                val result= objectoProcesadorDatosApiClientes.obtenerDatosClientes(
                    clientesPorPagina = "30",
                    paginaCliente = "1",
                    clienteDatoBusqueda = datosIngresadosBarraBusquedaCliente.trim(),
                    clienteEstado = "1",
                    busquedaPor = quitarTildesYMinusculas("Busqueda Mixta")

                )
                if(result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                }
                if(result?.getString("status")=="ok" && result.getString("code")=="200"){
                    val resultado= result.getJSONObject("resultado")
                    val datosClientes= resultado.getJSONArray("data")
                    val listaClientes = mutableListOf<Cliente>()
                    for (i in 0 until datosClientes.length()) {
                        val datosCliente = datosClientes.getJSONObject(i)
                        val cliente = Cliente(
                            codigo = datosCliente.getString("codigo"),
                            Cedula = datosCliente.getString("cedula"),
                            nombreComercial = datosCliente.getString("nombrecomercial"),
                            nombreJuridico = datosCliente.getString("nombrejuridico"),
                            Telefonos = datosCliente.getString("telefonos"),
                            correo = datosCliente.getString("emailgeneral"),
                            estado = datosCliente.getString("estado"),
                            Direccion = datosCliente.getString("direccion")
                        )
                        listaClientes.add(cliente)
                    }
                    listaClientesActuales=listaClientesActuales+listaClientes
                }
                isCargandoClientes=false
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            }
        }
    }

    LaunchedEffect(iniciarBusquedaClienteByCedula) {
        if(iniciarBusquedaClienteByCedula){
            if (cedulaCliente.length >=9){
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = obtenerDatosClienteByCedula(numeroCedula = cedulaCliente)
                if (result!=null){
                    if ((result.optString("resultcount", "0")) == "1"){
                        val results = result.getJSONArray("results")
                        val datos = results.getJSONObject(0)
                        nombreCliente = datos.getString("fullname")
                    }else{
                        mostrarMensajeError("El cliente no encontrado")
                    }
                }
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
            }else{
                mostrarMensajeError("Ingrese una cedula valida")
            }
        }
        iniciarBusquedaClienteByCedula = false
    }

    LaunchedEffect(agregarCliente) {
        if(agregarCliente){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            if (
                nombreCliente.isNotEmpty() &&
                telefonoCliente.isNotEmpty() &&
                correoCliente.isNotEmpty() &&
                direccionCliente.isNotEmpty() &&
                cedulaCliente.length >= 9
            ){
                val cliente = Cliente(
                    nombreComercial = nombreCliente,
                    Nombre = nombreCliente,
                    Telefonos = telefonoCliente,
                    EmailFactura = correoCliente,
                    EmailCobro = correoCliente,
                    Email = correoCliente,
                    Cedula = cedulaCliente,
                    Cod_Zona = "1",
                    AgenteVentas = codUsuario,
                    noForzaCredito = "0",
                    TipoPrecioVenta = "1",
                    TipoIdentificacion = "00",
                    Cod_Moneda = "CRC"
                )

                val result = objectoProcesadorDatosApiClientes.agregarCliente(cliente)
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarSoloRespuestaError = true, datosRespuesta = result)
                    if (result.getString("status")=="ok" && result.getString("code")=="200"){
                        mesaActual=Mesa(
                            nombre =result.getString("Id_Cliente"),
                            salon = "EXPRESS"
                        )
                        subCuentaSeleccionada="JUNTOS"
                        iniciarPantallaSacComanda = true
                    }else{
                        gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                    }
                }
            }else{
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                mostrarMensajeError("Complete los campos de datos validos")
            }
        }
        agregarCliente=false
    }

    LaunchedEffect(cambiarPrmImp2) {
        if(cambiarPrmImp2){
            if (passwordIngresada.length<4 || codUsuarioIngresado.isEmpty()){
                mostrarMensajeError("Complete los campos para continuar")
                agregarImpuestoServicio=false
            }else{
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
                val result = objectoProcesadorDatosApi.cambiarPrmImp2(passwordIngresada, codUsuarioIngresado)
                if (result!=null){
                    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = result)
                    if (result.getString("status")=="ok" && result.getString("code")=="200"){
                        actualizarParametro(context, "prmImp2$nombreEmpresa$codUsuario", if(valorPrmImp2=="0") "1" else "0")
                        valorPrmImp2 = obtenerParametroLocal(context, "prmImp2$nombreEmpresa$codUsuario")
                        estadoImp2 = valorPrmImp2 != "0"
                    }
                }
                gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(false)
                passwordIngresada=""
                codUsuarioIngresado=codUsuario
            }
        }
        cambiarPrmImp2=false
        iniciarMenuConfCambPrm=false
    }

    @Composable
    fun AgregarBt(
        text: String,
        color: Long,
        alto: Int = 35,
        onClick: (Boolean)->Unit,
        quitarPadInterno: Boolean = false
    ){
        BButton(
            text = text,
            backgroundColor = Color(color),
            onClick = {
                onClick(true)
            },
            objetoAdaptardor = objetoAdaptardor,
            modifier = if (quitarPadInterno) {
                Modifier.height(objetoAdaptardor.ajustarAltura(alto))
            } else {
                Modifier.width(objetoAdaptardor.ajustarAncho(120))
            }
        )
    }


    @Composable
    fun AgregarBxContenedorArticulosComandados(
        articuloComandado: ArticuloComandado
    ){
        var expanded by remember { mutableStateOf(false) }
        articuloComandado.calcularMontoTotal()
        Box(
            modifier = Modifier
                .background(Color.White)
                .widthIn(max =objetoAdaptardor.ajustarAncho(380))
                .then(
                    if(articuloComandado.articulos.isNotEmpty()){
                        Modifier.clickable {
                            expanded = !expanded
                        }
                    }else{
                        Modifier
                    }
                ),
            contentAlignment = Alignment.Center
        ){
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .height(objetoAdaptardor.ajustarAltura(50))
                            .width(objetoAdaptardor.ajustarAncho(50))
                            .padding(2.dp),
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
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloLabelBig(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(165)).padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))
                    if (isSubCuentaPedida){
                        Text(
                            "Cantidad: "+articuloComandado.Cantidad.toString(),
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloLabelSmall(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .then(
                                    if(articuloComandado.articulos.isNotEmpty()){
                                        Modifier.width(objetoAdaptardor.ajustarAncho(110))
                                    }else{
                                        Modifier.fillMaxSize()
                                    }
                                )
                        )
                        if(articuloComandado.articulos.isNotEmpty()){
                            IconButton(
                                onClick = {
                                    expanded = !expanded
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                            ) {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Flechas",
                                    tint = Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                )
                            }
                        }

                    }
                    else{
                        IconButton(
                            onClick = {
                                if(articuloComandado.Cantidad>0 && !isSubCuentaPedida){
                                    articuloActualSeleccionado = articuloComandado
                                    iniciarVentanaEliminarArticulo = true
                                }
                            },
                            modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23)),
                            enabled = !isSubCuentaPedida
                        ) {
                            Icon(
                                imageVector = if(articuloComandado.Cantidad==1 && !isSubCuentaPedida) Icons.Filled.Delete else Icons.Filled.RemoveCircle,
                                contentDescription = "Basurero",
                                tint = when {
                                    isSubCuentaPedida -> Color.White
                                    articuloComandado.Cantidad == 1 -> Color.Red
                                    else -> Color.Black
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                            )
                        }
                        Text(
                            articuloComandado.Cantidad.toString(),
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloLabelSmall(),
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                        )
                        if(articuloComandado.articulos.isEmpty()){
                            IconButton(
                                onClick = {
                                    articuloActualSeleccionado= articuloComandado
                                    iniciarVentanaAgregarArticulo = true
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.AddCircle,
                                    contentDescription = "Agregar Articulo",
                                    tint =   Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                )
                            }
                        }else{
                            IconButton(
                                onClick = {
                                    expanded = !expanded
                                },
                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                            ) {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Flechas",
                                    tint = Color.Black,
                                    modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                        BButton(
                            text = "Mover",
                            onClick = {
                                articuloActualSeleccionado= articuloComandado
                                iniciarMenuMoverArticulo = true
                                mesaDestino= mesaActual.nombre
                                actualizarSubCuentasYMesas = true
                            },
                            objetoAdaptardor = objetoAdaptardor,
                            modifier = Modifier.height(objetoAdaptardor.ajustarAltura(37))

                        )
                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    }
                }
                HorizontalDivider(
                    modifier = Modifier
                    .width(objetoAdaptardor.ajustarAncho(380))
                    .padding(1.dp)
                )
                AnimatedVisibility(
                    visible = expanded,
                    enter = expandVertically(animationSpec = tween(300)) + fadeIn(),
                    exit = shrinkVertically(animationSpec = tween(300)) + fadeOut()
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) { // Agrupar los elementos dentro de la Column
                        articuloComandado.articulos.forEach { articuloCombo ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.End, // Alinea los elementos a la derecha
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(10)))

                                Text(
                                    text = articuloCombo.cantidad.toString(),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = obtenerEstiloLabelSmall(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(20))
                                        .padding(2.dp),
                                    color = Color.DarkGray
                                )

                                Text(
                                    text = articuloCombo.nombre,
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = obtenerEstiloLabelSmall(),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(200))
                                        .padding(2.dp),
                                    color = Color.DarkGray
                                )

                                Text(
                                    text = "+ \u20A1 ${String.format(Locale.US, "%,.2f", articuloCombo.precio * articuloCombo.cantidad)}",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Light,
                                    fontSize = obtenerEstiloLabelSmall(),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .width(objetoAdaptardor.ajustarAncho(100))
                                        .padding(2.dp)
                                )
                            }
                            HorizontalDivider()
                        }
                    }
                }

                Row {
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    Text(
                        "Total artículo: \u20A1 "+String.format(Locale.US, "%,.2f", articuloComandado.montoTotal.toString().replace(",", "").toDouble()),
                        fontFamily = fontAksharPrincipal,
                        fontWeight = FontWeight.Light,
                        fontSize = obtenerEstiloLabelBig(),
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.End,
                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(422)).padding(2.dp)
                    )
                }
                HorizontalDivider(
                    thickness = 2.dp,
                    color = Color.Black,
                    modifier = Modifier
                        .width(objetoAdaptardor.ajustarAncho(380))
                        .padding(2.dp)
                )
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
        val (bxSuperior, bxContenedorMesas ,txfBarraBusqueda, bxContenedorSalones,
            bxContenerdorCuentasActivas, bxContenedorBotones, flechaRegresar, bxInferior)= createRefs()

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
                modifier = Modifier.padding(bottom = objetoAdaptardor.ajustarAltura(4))
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
                    fontSize = obtenerEstiloTitleBig(),
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
                .size(objetoAdaptardor.ajustarAncho(23))
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBackIosNew,
                contentDescription = "Flecha atras",
                tint = Color.White,
                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
            )
        }

        Box(
            modifier = Modifier.constrainAs(txfBarraBusqueda) {
                top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(8))
            }
        ){
            BBasicTextField(
                value = datosIngresadosBarraBusqueda,
                onValueChange =  { nuevoValor ->
                    salonActual = if(nuevoValor.isEmpty()) "TODOS" else ""
                    datosIngresadosBarraBusqueda = nuevoValor
                    actualizarListaMesas= true
                },
                alto = 45,
                ancho = 170,
                objetoAdaptardor = objetoAdaptardor
            )
        }


        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(45))
                .width(objetoAdaptardor.ajustarAncho(517))
                .constrainAs(bxContenedorBotones) {
                    start.linkTo(txfBarraBusqueda.end, margin = objetoAdaptardor.ajustarAncho(8))
                    top.linkTo(bxSuperior.bottom, margin = objetoAdaptardor.ajustarAltura(8))
                }
        ){
            Row(
                horizontalArrangement = Arrangement.Start
            ) {

                BButton(
                    modifier = Modifier
                        .weight(1.1f)
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    onClick = {
                        iniciarMenuCrearMesa= it
                    },
                    text = "Crear mesa",
                    objetoAdaptardor = objetoAdaptardor
                )

                BButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    onClick = {
                        iniciarMenuCrearPersona= it
                    },
                    text = "Persona",
                    objetoAdaptardor = objetoAdaptardor
                )

                BButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    onClick = {
                        iniciarMenuCrearExpress= it
                    },
                    text = "Express",
                    objetoAdaptardor = objetoAdaptardor
                )

                BButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    onClick = {
                        iniciarMenuAjustes= it
                    },
                    text = "Ajustes",
                    objetoAdaptardor = objetoAdaptardor
                )

                BButton(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = objetoAdaptardor.ajustarAltura(4), end =  objetoAdaptardor.ajustarAltura(4)),
                    onClick = {
                        actualizarListaMesas= it
                    },
                    text = "Refrescar",
                    objetoAdaptardor = objetoAdaptardor
                )
            }
        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .height(objetoAdaptardor.ajustarAltura(45))
                .width(objetoAdaptardor.ajustarAncho(690))
                .constrainAs(bxContenedorSalones) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(12))
                    top.linkTo(txfBarraBusqueda.bottom, margin = objetoAdaptardor.ajustarAltura(4))
                }
            , contentAlignment = Alignment.CenterStart
        ){
            if (listaSalonesAtuales.isNotEmpty()){
                val isSalonesVisible = remember { mutableStateOf(false) }

                LaunchedEffect(Unit) {
                    delay(100)
                    isSalonesVisible.value = true
                }
                LazyRow {
                    item {
                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(6)))
                    }
                    items(listaSalonesAtuales) { salon ->
                        AnimatedVisibility(
                            visible = isSalonesVisible.value,
                            enter = fadeIn(animationSpec = tween(500)) + slideInHorizontally(initialOffsetX = { it }),
                            exit = fadeOut()
                        ) {
                            BButton(
                                text = salon,
                                backgroundColor =  if (salonActual == salon) Color(0xFFBFBFBF) else Color(0xFFEAEAEA),
                                contenteColor = Color.Black,
                                onClick = {
                                    datosIngresadosBarraBusqueda = ""
                                    salonActual = salon
                                    actualizarListaMesas = true
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.height(objetoAdaptardor.ajustarAltura(35))
                            )
                        }
                        Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                    }
                }
            }

        }

        Box(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(690))
                .height(objetoAdaptardor.ajustarAltura(450))
                .constrainAs(bxContenedorMesas) {
                    start.linkTo(parent.start, margin = objetoAdaptardor.ajustarAncho(12))
                    top.linkTo(bxContenedorSalones.bottom, margin = objetoAdaptardor.ajustarAltura(4))
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
            }
            else{
                val isMesasVisible = remember { mutableStateOf(false) }
                LaunchedEffect(Unit) {
                    delay(100)
                    isMesasVisible.value = true
                }
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(16)),
                    state = lazyStateMesas,
                ) {
                    itemsIndexed(listaMesasActualesFiltradas.chunked(5)) { index, rowItems ->
                        AnimatedVisibility(
                            visible = isMesasVisible.value,
                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(
                                initialOffsetY = { it * (index + 1) }
                            )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.Top
                            ) {
                                rowItems.forEach { mesa ->
                                    BxContendorDatosMesa(
                                        datosMesa = mesa,
                                        navControllerPantallasModuloSac = navController,
                                        iniciarMenuDetalleComanda = { valor -> iniciarMenuMesaComandada = valor },
                                        mesaSeleccionada = { datosMesaActual -> mesaActual = datosMesaActual },
                                        token = token,
                                        nombreEmpresa = nombreEmpresa,
                                        codUsuario = codUsuario,
                                        subCuentaSeleccionada = subCuentaSeleccionada,
                                        nombreUsuario = nombreUsuario
                                    )
                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(12)))
                                }
                            }
                        }
                    }
                    item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(24))) }
                }

            }
        }

        Card(
            modifier = Modifier
                .background(Color.White)
                .width(objetoAdaptardor.ajustarAncho(245))
                .height(objetoAdaptardor.ajustarAltura(510))
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
                        fontSize = obtenerEstiloBodyBig(),
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(objetoAdaptardor.ajustarAncho(230))
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
                            fontSize = obtenerEstiloBodyMedium(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(80))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )

                        Text(
                            "Tiempo",
                            fontFamily = fontAksharPrincipal,
                            color = Color(0xFF1D3FA4),
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloBodyMedium(),
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
                            fontSize = obtenerEstiloBodyMedium(),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(85))
                                .padding(objetoAdaptardor.ajustarAncho(2))
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFFFAFAFA)),
                ){
                    if (listaCuentasActivasActuales.isNotEmpty()){
                        var isCuentasVisible by remember { mutableStateOf(false) }
                        LaunchedEffect(Unit) {
                            delay(100)
                            isCuentasVisible = true
                        }
                        LazyColumn(state = lazyStateCuentasActivas) {
                            items(listaCuentasActivasActuales) { mesa ->
                                if (mesa.estado != "null") {
                                    Column {
                                        AnimatedVisibility(
                                            visible = isCuentasVisible,
                                            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
                                            exit = fadeOut()
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .background(Color(0xFFFAFAFA))
                                                    .clickable {
                                                        mesaActual = mesa
                                                        iniciarMenuMesaComandada = true
                                                    }
                                            ) {
                                                Row(
                                                    horizontalArrangement = Arrangement.Center,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(2)))

                                                    Text(
                                                        mesa.nombre,
                                                        fontFamily = fontAksharPrincipal,
                                                        color = Color.Black,
                                                        fontWeight = FontWeight.Light,
                                                        fontSize = obtenerEstiloBodySmall(),
                                                        overflow = TextOverflow.Ellipsis,
                                                        maxLines = 1,
                                                        textAlign = TextAlign.Center,
                                                        modifier = Modifier
                                                            .width(objetoAdaptardor.ajustarAncho(80))
                                                            .padding(objetoAdaptardor.ajustarAncho(2))
                                                    )

                                                    var tiempo by remember { mutableStateOf("") }
                                                    tiempo = if (mesa.tiempo >= 60) {
                                                        val minutos = mesa.tiempo
                                                        val horas = minutos / 60
                                                        val minutosRestantes = minutos % 60
                                                        "$horas:$minutosRestantes h"
                                                    } else {
                                                        "${mesa.tiempo} m"
                                                    }

                                                    Text(
                                                        tiempo,
                                                        fontFamily = fontAksharPrincipal,
                                                        color = Color.Black,
                                                        fontWeight = FontWeight.Light,
                                                        fontSize = obtenerEstiloBodySmall(),
                                                        overflow = TextOverflow.Ellipsis,
                                                        textAlign = TextAlign.Center,
                                                        maxLines = 1,
                                                        modifier = Modifier
                                                            .width(objetoAdaptardor.ajustarAncho(72))
                                                            .padding(objetoAdaptardor.ajustarAncho(2))
                                                    )

                                                    val total = mesa.total
                                                    val totalMiles = try {
                                                        String.format(Locale.US, "%,.2f", total.replace(",", "").toDouble())
                                                    } catch (e: NumberFormatException) {
                                                        total
                                                    }

                                                    Text(
                                                        totalMiles,
                                                        fontFamily = fontAksharPrincipal,
                                                        color = Color.Black,
                                                        fontWeight = FontWeight.Light,
                                                        fontSize = obtenerEstiloBodySmall(),
                                                        maxLines = 1,
                                                        textAlign = TextAlign.End,
                                                        modifier = Modifier
                                                            .width(objetoAdaptardor.ajustarAncho(85))
                                                            .padding(objetoAdaptardor.ajustarAncho(2))
                                                    )
                                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(4)))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(16))) }
                        }
                    }

                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF000000))
                .height(objetoAdaptardor.ajustarAltura(25))
                .constrainAs(bxInferior) {
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                }, contentAlignment = Alignment.TopCenter
        ) {
            val versionApp = stringResource(R.string.app_version)

            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
                
            ){
                Text(
                    text = "#$codUsuario _ $nombreUsuario _ $nombreEmpresa",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabelSmall(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(382)).padding(start = 6.dp)
                )

                Text(
                    text = "Invefacon ©2025",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    fontSize = obtenerEstiloLabelSmall(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(200))
                )

                Text(
                    text = "Version: $versionApp",
                    color = Color.White,
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Light,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = obtenerEstiloLabelSmall(),
                    textAlign = TextAlign.End,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(382)).padding(end = 6.dp)
                )
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
                    fontSize = obtenerEstiloDisplayMedium(),
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
                BButton(
                    text = "Crear",
                    onClick = {
                        iniciarCreacionNuevaMesa=true
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                )
            },
            dismissButton = {
                BButton(
                    text = "Cancelar",
                    onClick = {
                        iniciarMenuCrearMesa = false
                        nombreNuevaMesa=""
                        nombreSalonNuevaMesa=""
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                    backgroundColor = Color.Red
                )
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
                    fontSize =  obtenerEstiloDisplayMedium(),
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
                BButton(
                    text = "Continuar",
                    onClick = {
                        iniciarCreacionNuevaPersona=true
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                )
            },
            dismissButton = {
                BButton(
                    text = "Cancelar",
                    onClick = {
                        iniciarMenuCrearPersona= false
                        nombreNuevaPersona=""
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                    backgroundColor = Color.Red
                )
            }
        )
    }

    if (iniciarMenuMesaComandada) {
        var isMenuVisible by remember { mutableStateOf(false) }
        var isArticuloVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isMenuVisible = true
        }

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
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
                                fontSize = obtenerEstiloTitleSmall(),
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
                                        TextFieldMultifuncional(
                                            label = "Sub-Cuentas",
                                            opciones2 = opcionesSubCuentas,
                                            usarOpciones2 = true,
                                            contieneOpciones = true,
                                            nuevoValor = { nuevoValor ->
                                                subCuentaSeleccionada = nuevoValor
                                                iniciarCalculoMontos = true
                                                isArticuloVisible = false
                                            },
                                            valor = subCuentaSeleccionada,
                                            isUltimo = true,
                                            tomarAnchoMaximo = false,
                                            medidaAncho = 80
                                        )
                                        if (isSubCuentaPedida) {
                                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                            AgregarBt(
                                                text = "Reactivar",
                                                color = 0xFF16417C,
                                                onClick = {
                                                    reactivarCuenta = true
                                                }
                                            )
                                        }
                                        if (!isSubCuentaPedida) {
                                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                            AgregarBt(
                                                text = "Quitar mesa",
                                                color = 0xFFFF5722,
                                                onClick = {
                                                    iniciarMenuQuitarMesa = true
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
                                        if(articulosComandados.isNotEmpty()){
                                            LaunchedEffect(isArticuloVisible) {
                                                delay(100)
                                                isArticuloVisible = true
                                            }
                                            LazyColumn {
                                                items(articulosComandados) { producto ->
                                                    if (producto.SubCuenta.uppercase() == subCuentaSeleccionada.uppercase()) {
                                                        val existeCuentaInLista = opcionesSubCuentasComandadas.find { it == subCuentaSeleccionada }
                                                        isSubCuentaPedida = existeCuentaInLista != null
                                                        Column {
                                                            AnimatedVisibility(
                                                                visible = isArticuloVisible,
                                                                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it })
                                                            ) {
                                                                AgregarBxContenedorArticulosComandados(producto)
                                                            }
                                                        }
                                                    }
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
                                                fontSize = obtenerEstiloLabelSmall(),
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
                                                fontSize = obtenerEstiloBodySmall(),
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
                                        if (!isSubCuentaPedida){
                                            AgregarBt(
                                                text = "Pedir Cuenta",
                                                color = 0xFF16417C,
                                                onClick = {
                                                    pedirCuenta = true
                                                }
                                            )
                                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                        }
                                        AgregarBt(
                                            text = "+10",
                                            color = 0xFF244BC0,
                                            onClick = {
                                                agregarImpuestoServicio = true
                                            }
                                        )
                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))
                                        AgregarBt(
                                            text = "Agregar",
                                            color = 0xFF22B14C,
                                            onClick = { iniciarPantallaSacComanda = true }
                                        )

                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4)))


                                        AgregarBt(
                                            text = "Salir",
                                            color = 0xFFE10000,
                                            onClick = {
                                                iniciarMenuMesaComandada = false
                                                mesaActual = Mesa()
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
    }

    if (iniciarVentanaEliminarArticulo || iniciarVentanaAgregarArticulo){
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    if (iniciarVentanaEliminarArticulo) "Eliminar Articulo de ${mesaActual.nombre}-${mesaActual.salon}" else "Agregar Articulo a ${mesaActual.nombre}-${mesaActual.salon}",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = obtenerEstiloBodyMedium(),
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
                                model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articuloActualSeleccionado.Cod_Articulo}.png",
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
                            fontSize = obtenerEstiloBodySmall(),
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
                                    fontSize = obtenerEstiloLabelBig(),
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
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.RemoveCircle,
                                            contentDescription = "Basurero",
                                            tint = Color.Black,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                        )
                                    }
                                }

                                Text(
                                    cantidadArticulos.toString(),
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = obtenerEstiloLabelBig(),
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                                )

                                if ((cantidadArticulos<articuloActualSeleccionado.Cantidad) || iniciarVentanaAgregarArticulo){
                                    IconButton(
                                        onClick = {
                                            cantidadArticulos+=1
                                        },
                                        modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.AddCircle,
                                            contentDescription = "Agregar Articulo",
                                            tint = Color.Black,
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
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
                BButton(
                    text = if (iniciarVentanaAgregarArticulo)  "Agregar" else "Eliminar",
                    onClick = {
                        if (iniciarVentanaAgregarArticulo) agregarArticulo= true else eliminarArticulo= true
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                    backgroundColor = Color.Red
                )
            },
            dismissButton = {
                BButton(
                    text = "Cancelar",
                    onClick = {
                        iniciarVentanaEliminarArticulo=false
                        iniciarVentanaAgregarArticulo= false
                        cantidadArticulos= 1
                        anotacionComanda= ""
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                )
            }
        )
    }

    if (iniciarMenuMoverArticulo){
        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isMenuVisible = true
        }

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
        ) {
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
                    shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(16)),
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
                                "Mover Articulo ${mesaActual.nombre}-${mesaActual.salon}",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloBodyMedium(),
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
                                    model = "https://invefacon.com/img/$nombreEmpresa/articulos/${articuloActualSeleccionado.Cod_Articulo}.png",
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
                                fontSize = obtenerEstiloBodySmall(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                color = Color.Black
                            )
                            Box{
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        "Cantidad articulos a mover:",
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = obtenerEstiloBodySmall(),
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
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.RemoveCircle,
                                                contentDescription = "Basurero",
                                                tint = Color.Black,
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                            )
                                        }
                                    }

                                    Text(
                                        cantidadArticulos.toString(),
                                        fontFamily = fontAksharPrincipal,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = obtenerEstiloLabelBig(),
                                        overflow = TextOverflow.Ellipsis,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.width(objetoAdaptardor.ajustarAncho(35)).padding(2.dp)
                                    )

                                    if (cantidadArticulos<articuloActualSeleccionado.Cantidad){
                                        IconButton(
                                            onClick = {
                                                cantidadArticulos+=1
                                            },
                                            modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.AddCircle,
                                                contentDescription = "Agregar Articulo",
                                                tint = Color.Black,
                                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                                            )
                                        }
                                    }
                                }
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                TextFieldMultifuncional(
                                    label = "Mesa Destino",
                                    opciones2 = opcionesMesas,
                                    usarOpciones2 = true,
                                    contieneOpciones = true,
                                    nuevoValor = { nuevoValor->
                                        actualizarSubCuentasYMesas = true
                                        mesaDestino= nuevoValor
                                    },
                                    valor = opcionesMesas.value[mesaDestino]?: "Seleccione una mesa",
                                    isUltimo = true,
                                    tomarAnchoMaximo = false,
                                    medidaAncho = 100
                                )
                                TextFieldMultifuncional(
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
                                BButton(
                                    text = "Cancelar",
                                    onClick = {
                                        iniciarMenuMoverArticulo= false
                                        cantidadArticulos= 1
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                                )

                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                BButton(
                                    text = "Mover",
                                    onClick = {
                                        moverArticulo= true
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    backgroundColor = Color.Red,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuMoverMesa){

        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isMenuVisible = true
        }

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
        ) {
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
                                "Mover Mesa ${mesaActual.nombre}-${mesaActual.salon}",
                                fontFamily = fontAksharPrincipal,
                                fontWeight = FontWeight.Medium,
                                fontSize = obtenerEstiloBodyBig(),
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
                                TextFieldMultifuncional(
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
                                BButton(
                                    text = "Cancelar",
                                    onClick = {
                                        codUsuarioIngresado=codUsuario
                                        passwordIngresada=""
                                        iniciarMenuMoverMesa= false
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                                )
                                Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                BButton(
                                    text = "Mover",
                                    onClick = {
                                        moverMesa= true
                                        iniciarMenuMoverMesa= false
                                    },
                                    objetoAdaptardor = objetoAdaptardor,
                                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                                    backgroundColor = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }


    }

    if (iniciarMenuAjustes){

        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isMenuVisible = true
        }

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
        ) {
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
                                fontSize =  obtenerEstiloDisplayMedium(),
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
                                    fontSize = obtenerEstiloBodySmall(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Switch(
                                    checked = estadoImp2,
                                    onCheckedChange = {
                                        iniciarMenuConfCambPrm=true
                                    },colors = SwitchDefaults.colors(
                                        checkedTrackColor = Color(0xFF1D3FA4)
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                            BButton(
                                text = "Salir",
                                onClick = {
                                    iniciarMenuAjustes= false
                                },
                                objetoAdaptardor = objetoAdaptardor,
                                modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                                backgroundColor = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }

    if (iniciarMenuCrearExpress){
        var isMenuVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            isMenuVisible = true
        }

        AnimatedVisibility(
            visible = isMenuVisible,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(initialOffsetY = { it }),
            exit = fadeOut(animationSpec = tween(500)) + slideOutVertically(targetOffsetY = { it })
        ) {
            var isNuevoCliente by remember { mutableStateOf(false) }
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(enabled = false) {},
                contentAlignment = Alignment.Center
            ) {
                if(iniciarMenuCrearCliente){
                    Surface(
                        modifier = Modifier
                            .wrapContentWidth()
                            .heightIn(max = objetoAdaptardor.ajustarAltura(500))
                            .align(Alignment.Center),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(objetoAdaptardor.ajustarAltura(24)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    if(isNuevoCliente)"Agregar Cliente" else "Editar Cliente",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize =  obtenerEstiloDisplayMedium(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                                Row(
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Column {
                                        Text(
                                            "Cedula:",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloBodySmall(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        Row (
                                            verticalAlignment = Alignment.CenterVertically
                                        ){
                                            BBasicTextField(
                                                value = cedulaCliente,
                                                onValueChange = {
                                                    cedulaCliente = it
                                                    nombreCliente = ""
                                                },
                                                placeholder = "Ingrese la cedula",
                                                objetoAdaptardor = objetoAdaptardor,
                                                alto = 60,
                                                ancho = 230,
                                                icono = Icons.Filled.Badge,
                                                soloPermitirValoresNumericos = true
                                            )
                                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                            if(nombreCliente.isEmpty()){
                                                AgregarBt(
                                                    text = "Buscar",
                                                    color = 0xFF244BC0,
                                                    onClick = {
                                                        iniciarBusquedaClienteByCedula = true
                                                    }
                                                )
                                            }
                                        }
                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                                        Text(
                                            "Nombre:",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloBodySmall(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        BBasicTextField(
                                            value = nombreCliente,
                                            onValueChange = {
                                                nombreCliente = it
                                            },
                                            placeholder = "Ingrese el nombre",
                                            objetoAdaptardor = objetoAdaptardor,
                                            alto = 60,
                                            ancho = 360,
                                            icono = Icons.Filled.Person
                                        )
                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                                        Text(
                                            "Telefono:",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloBodySmall(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                        )
                                        BBasicTextField(
                                            value = telefonoCliente,
                                            onValueChange = {
                                                telefonoCliente = it
                                            },
                                            placeholder = "Ingrese el telefono",
                                            objetoAdaptardor = objetoAdaptardor,
                                            alto = 60,
                                            ancho = 360,
                                            icono = Icons.Filled.Phone,
                                            soloPermitirValoresNumericos = true
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                    Column {
                                        Text(
                                            "Correo:",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloBodySmall(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        BBasicTextField(
                                            value = correoCliente,
                                            onValueChange = {
                                                correoCliente = it
                                            },
                                            placeholder = "Ingrese el correo",
                                            objetoAdaptardor = objetoAdaptardor,
                                            alto = 60,
                                            ancho = 360,
                                            icono = Icons.Filled.Email
                                        )
                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(8)))
                                        Text(
                                            "Direccion:",
                                            fontFamily = fontAksharPrincipal,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = obtenerEstiloBodySmall(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.Center,
                                            color = Color.Black
                                        )
                                        BBasicTextField(
                                            value = direccionCliente,
                                            onValueChange = {
                                                direccionCliente = it
                                            },
                                            placeholder = "Ingrese la direccion",
                                            objetoAdaptardor = objetoAdaptardor,
                                            alto = 60,
                                            ancho = 360,
                                            icono = Icons.Filled.Place,
                                            cantidadLineas = 12
                                        )
                                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(40)))

                                        Row {
                                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(60)))
                                            AgregarBt(
                                                text = "Regresar",
                                                color = 0xFFEB3324,
                                                onClick = {
                                                    iniciarMenuCrearCliente= false
                                                }
                                            )
                                            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                            AgregarBt(
                                                text = "Continuar",
                                                color = 0xFF244BC0,
                                                onClick = {
                                                    if(isNuevoCliente){
                                                        if (!validarCorreo(correoCliente)) return@AgregarBt
                                                        agregarCliente = true
                                                    }else{
                                                        mostrarMensajeError("Esta opcion esta en desarrollo.")
                                                    }
                                                }
                                            )
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
                else{
                    Surface(
                        modifier = Modifier
                            .widthIn(max = objetoAdaptardor.ajustarAncho(400))
                            .heightIn(max = objetoAdaptardor.ajustarAltura(570))
                            .align(Alignment.Center),
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(objetoAdaptardor.ajustarAltura(24)),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Crear Express",
                                    fontFamily = fontAksharPrincipal,
                                    fontWeight = FontWeight.Medium,
                                    fontSize =  obtenerEstiloDisplayMedium(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center,
                                    color = Color.Black
                                )
                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(6)))
                                Row (
                                    verticalAlignment = Alignment.CenterVertically
                                ){
                                    BBasicTextField(
                                        value = datosIngresadosBarraBusquedaCliente,
                                        onValueChange = {
                                            datosIngresadosBarraBusquedaCliente = it
                                        },
                                        objetoAdaptardor = objetoAdaptardor,
                                        alto = 60,
                                        ancho = 292,
                                        mostrarTrailingIcon = true,
                                        trailingIcon = Icons.Filled.AddCircle,
                                        onTrailingIconClick = {
                                            cedulaCliente = ""
                                            nombreCliente = ""
                                            telefonoCliente = ""
                                            correoCliente = ""
                                            direccionCliente = ""
                                            isNuevoCliente = true
                                            iniciarMenuCrearCliente = it
                                        }
                                    )
                                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                                    AgregarBt(
                                        text = "Salir",
                                        color = 0xFFEB3324,
                                        onClick = {
                                            iniciarMenuCrearExpress = false
                                        }
                                    )
                                }

                                Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(6)))
                                Box(
                                    modifier = Modifier
                                        .height(objetoAdaptardor.ajustarAltura(400))
                                        .background(Color.White)
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ){
                                    LazyColumn(
                                        modifier = Modifier.fillMaxSize(),
                                        verticalArrangement = Arrangement.spacedBy(objetoAdaptardor.ajustarAltura(12)),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        state = lazyStateListaClientes
                                    ) {
                                        items(listaClientesActuales) { datosCliente ->
                                            BxContenerdorCliente(
                                                datosCliente = datosCliente,
                                                onClick = {
                                                    mesaActual= it
                                                    iniciarPantallaSacComanda = true
                                                },
                                                iconOnClick = {
                                                    mesaActual= Mesa(
                                                        nombre =datosCliente.codigo,
                                                        salon = "EXPRESS",
                                                        clienteId = datosCliente.codigo
                                                    )
                                                    cedulaCliente = datosCliente.Cedula
                                                    nombreCliente = datosCliente.nombreJuridico
                                                    telefonoCliente = datosCliente.Telefonos
                                                    correoCliente = datosCliente.correo
                                                    direccionCliente = datosCliente.Direccion
                                                    isNuevoCliente= false
                                                    iniciarMenuCrearCliente = it
                                                }
                                            )
                                        }

                                        // Muestra el indicador de carga al final de la lista mientras se cargan nuevos elementos
                                        if (isCargandoClientes) {
                                            item {
                                                CircularProgressIndicator(
                                                    color = Color(0xFF244BC0),
                                                    modifier = Modifier
                                                        .size(objetoAdaptardor.ajustarAltura(60))
                                                        .padding(4.dp)
                                                )
                                            }
                                        }
                                        item { Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(4))) }

                                    }
                                }
                            }
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
                    fontSize = obtenerEstiloBodyMedium(),
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
                BButton(
                    text = "Crear",
                    onClick = {
                        if (nombreNuevaSubCuenta.isEmpty()){
                            mostrarMensajeError("Ingrese el nombre de la Sub-Cuenta")
                        }
                        else{
                            opcionesSubCuentasDestino.value[nombreNuevaSubCuenta.uppercase()]=nombreNuevaSubCuenta.uppercase()
                            subCuentaDestinoArticulo= nombreNuevaSubCuenta.uppercase()
                            iniciarMenuAgregarSubCuenta=false
                            nombreNuevaSubCuenta=""
                            mostrarMensajeExito("Sub-Cuenta creada")
                        }
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120)),
                )
            },
            dismissButton = {
                BButton(
                    text = "Cancelar",
                    onClick = {
                        iniciarMenuAgregarSubCuenta=false
                        nombreNuevaSubCuenta=""
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    backgroundColor = Color.Red,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                )
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
                    "Quitar Mesa ${mesaActual.nombre}-${mesaActual.salon}",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize = obtenerEstiloDisplayMedium(),
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
                            fontSize = obtenerEstiloLabelBig(),
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
                BButton(
                    text = "Quitar",
                    onClick = {
                        quitarMesa=true
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    backgroundColor = Color.Red,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))

                )
            },
            dismissButton = {
                BButton(
                    text = "Cancelar",
                    onClick = {
                        codUsuarioIngresado=codUsuario
                        passwordIngresada=""
                        iniciarMenuQuitarMesa= false
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))

                )
            }
        )
    }

    if(iniciarMenuConfCambPrm) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            containerColor = Color.White,
            onDismissRequest = { },
            title = {
                Text(
                    "Confirmacion de Cambios",
                    fontFamily = fontAksharPrincipal,
                    fontWeight = FontWeight.Medium,
                    fontSize =  obtenerEstiloDisplayMedium(),
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
                            "¿Desea confirmar los cambios?",
                            fontFamily = fontAksharPrincipal,
                            fontWeight = FontWeight.Medium,
                            fontSize = obtenerEstiloLabelBig(),
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
                BButton(
                    text = "Continuar",
                    onClick = {
                        cambiarPrmImp2=true
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    backgroundColor = Color.Red,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                )
            },
            dismissButton = {
                BButton(
                    text = "Cancelar",
                    onClick = {
                        codUsuarioIngresado=codUsuario
                        passwordIngresada=""
                        iniciarMenuConfCambPrm= false
                    },
                    objetoAdaptardor = objetoAdaptardor,
                    modifier = Modifier.width(objetoAdaptardor.ajustarAncho(120))
                )
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
    codUsuario: String,
    subCuentaSeleccionada : String,
    nombreUsuario: String
){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    var iniciarPantallaSacComanda by remember { mutableStateOf(false) }

    LaunchedEffect(iniciarPantallaSacComanda) {
        if (iniciarPantallaSacComanda && datosMesa.estado=="0"){
            gestorEstadoPantallaCarga.cambiarEstadoPantallasCarga(true)
            iniciarMenuDetalleComanda(false)
            delay(500)
            navControllerPantallasModuloSac?.navigate(
                RutasPatallas.SacComanda.ruta+"/"+datosMesa.nombre+"/"+datosMesa.salon+"/"+token+"/"
                        +nombreEmpresa+"/"+codUsuario+"/"+datosMesa.estado+"/"+datosMesa.clienteId+"/"
                        +subCuentaSeleccionada.ifEmpty { "JUNTOS" }+"/"+nombreUsuario
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
                    fontWeight = FontWeight.SemiBold,
                    fontSize = obtenerEstiloBodySmall(),
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
                        fontWeight =    FontWeight.Medium,
                        fontSize = obtenerEstiloLabelBig(),
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
                        fontWeight =    FontWeight.Medium,
                        fontSize = obtenerEstiloLabelBig(),
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
                        fontWeight = FontWeight.Medium,
                        fontSize = obtenerEstiloLabelBig(),
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
fun BxContenerdorCliente(
    datosCliente: Cliente,
    onClick: (Mesa)-> Unit,
    iconOnClick: (Boolean)-> Unit

){
    val configuration = LocalConfiguration.current
    val dpAnchoPantalla = configuration.screenWidthDp
    val dpAltoPantalla = configuration.screenHeightDp
    val dpFontPantalla= configuration.fontScale
    val objetoAdaptardor= FuncionesParaAdaptarContenido(dpAltoPantalla, dpAnchoPantalla, dpFontPantalla, isPantallaHorizontal = true)
    val fontAksharPrincipal = FontFamily(Font(R.font.akshar_medium))
    Card(
        modifier = Modifier
            .wrapContentHeight()
            .clickable {
                val mesaActual=Mesa(
                    nombre =datosCliente.codigo,
                    salon = "EXPRESS",
                    clienteId = datosCliente.codigo
                )
                onClick(mesaActual)
            }
            .width(objetoAdaptardor.ajustarAncho(370))
            .shadow(
                elevation = objetoAdaptardor.ajustarAltura(7),
                shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20))
            ),
        shape = RoundedCornerShape(objetoAdaptardor.ajustarAltura(20)),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF244BC0))
    ) {
        Row (
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(20)))
            Box(modifier = Modifier
                .wrapContentSize()
                .background(Color.White)
            ){
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = Modifier.width(objetoAdaptardor.ajustarAncho(8)))
                    Column {
                        Spacer(modifier = Modifier.height(objetoAdaptardor.ajustarAltura(2)))

                        // Codigo Cliente
                        Text(text = "#"+datosCliente.codigo,
                            fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  obtenerEstiloLabelBig(),
                            color = Color.Black,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )
                        // Nombre Juridico
                        Text(datosCliente.nombreJuridico
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  obtenerEstiloLabelBig(),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )

                        // Telefono
                        Text(datosCliente.Telefonos
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  obtenerEstiloLabelBig(),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )

                        // Correo
                        Text(datosCliente.correo
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  obtenerEstiloLabelBig(),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )
                        // Diredcion
                        Text(datosCliente.Direccion
                            ,fontFamily = fontAksharPrincipal,
                            fontWeight =    FontWeight.SemiBold,
                            fontSize =  obtenerEstiloLabelBig(),
                            color = Color(0xFF626262),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .width(objetoAdaptardor.ajustarAncho(260))
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ){

                        IconButton(
                            onClick = {
                                iconOnClick(true)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.MoreHoriz,
                                contentDescription = "Icono mostrar opciones clientes",
                                tint = Color.DarkGray,
                                modifier = Modifier.size(objetoAdaptardor.ajustarAncho(23))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(widthDp = 964, heightDp = 523)
private fun Preview(){
    val nav = rememberNavController()
    InterfazModuloSac("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb2RpZ28iOiIwMDA0MyIsIk5vbWJyZSI6IlJPQkVSVE8gQURNSU4iLCJFbWFpbCI6InJyZXllc0Bzb3BvcnRlcmVhbC5jb20iLCJQdWVydG8iOiI4MDEiLCJFbXByZXNhIjoiWkdWdGIzSmxjM1E9IiwiU2VydmVySXAiOiJNVGt5TGpFMk9DNDNMak13IiwidGltZSI6IjIwMjUwMzEwMDIwMzA0In0.7kfmdiMMKZ30R7mSvuIT0iNod_naX8DBDPguf9KC_H4", null, nav, "demorest","00050", "YESLER LORIO")
}
