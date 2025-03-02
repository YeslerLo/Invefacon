package com.soportereal.invefacon.interfaces.modulos.sac

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import com.soportereal.invefacon.interfaces.modulos.clientes.Cliente
import okhttp3.MultipartBody
import org.json.JSONArray
import org.json.JSONObject

class ProcesarDatosModuloSac(apiToken: String){

    private val objetoFuncionesHttpInvefacon= FuncionesHttp(apiToken = apiToken)

    suspend fun obtenerListaMesas(mesa: String=""): JSONObject?{

        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("mesa",mesa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/listaMesas.php")
    }

    suspend fun crearNuevaMesa(nombreMesa: String, nombreSalon: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("MesaSubCuenta","JUNTOS")
            .addFormDataPart("MesaNombre",nombreMesa)
            .addFormDataPart("SalonNombre",nombreSalon)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/crearMesa.php")
    }

    suspend fun obtenerListaArticulos(codFamilia: String, codSubFamilia: String, datosBarraBusqueda: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("familia",codFamilia)
            .addFormDataPart("subfamilia",codSubFamilia)
            .addFormDataPart("nombreArticulo",datosBarraBusqueda)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/listaArticulos.php")
    }

    suspend fun obtenerListaFamilias():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("opcional","opcional")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/listaFamilias.php")
    }

    suspend fun comandarSubCuentaEliminarArticulos(mesa: String, salon: String, codUsuario: String, clienteId: String, jsonComandaDetalle: JSONArray): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa",mesa)
            .addFormDataPart("Salon",salon)
            .addFormDataPart("Cod_Usuario", codUsuario)
            .addFormDataPart("ClienteId", clienteId)
            .addFormDataPart("JsonComandaDetalle",jsonComandaDetalle. toString())
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/crearComanda.php")
    }

    suspend fun obtenerDatosMesaComandada(nombreMesa: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa",nombreMesa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/detalleMesa.php")
    }

    suspend fun quitarMesa(nombreMesa: String, password: String, codUsuario: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa", nombreMesa)
            .addFormDataPart("password", password)
            .addFormDataPart("cod_usuario", codUsuario)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/vaciarMesa.php")
    }

    suspend fun pedirCuenta(nombreMesa: String, subCuenta: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa", nombreMesa)
            .addFormDataPart("SubCuenta", subCuenta)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/pedirCuenta.php")
    }

    suspend fun obetenerNombresMesas():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa", "")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/listaMesasSola.php")
    }

    suspend fun obetenerSubCuentasMesa(nombreMesa: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa", nombreMesa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/listaSubcuentas.php")
    }

    suspend fun aplicarImp2(nombreMesa: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa", nombreMesa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/aplicarImpuestoServicio.php")
    }

    suspend fun moverMesa(
        mesa: String,
        mesaDestino: String,
        password: String,
        codUsuario: String,
        idCliente: String
    ):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("MesaActual", mesa)
            .addFormDataPart("MesaDestino", mesaDestino)
            .addFormDataPart("password", password)
            .addFormDataPart("cod_usuario", codUsuario)
            .addFormDataPart("IdCliente", idCliente)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/MoverMesaCompleta.php")
    }

    suspend fun moverArticulo(
        codigoArticulo: String,
        cantidadArticulos: String,
        mesa: String,
        mesaDestino: String,
        subCuenta: String,
        subCuentaDestino: String,
        codUsuario: String,
        linea : String,
        isCombo: Int,
        idCliente: String
    ):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("MesaActual", mesa)
            .addFormDataPart("MesaDestino", mesaDestino)
            .addFormDataPart("SubCuentaActual", subCuenta)
            .addFormDataPart("SubCuentaDestino", subCuentaDestino)
            .addFormDataPart("Cod_Articulo", codigoArticulo)
            .addFormDataPart("Cantidad", cantidadArticulos)
            .addFormDataPart("Cod_Usuario", codUsuario)
            .addFormDataPart("linea", linea)
            .addFormDataPart("isCombo", isCombo.toString())
            .addFormDataPart("IdCliente", idCliente)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "sacMovil/moverArticulo.php")
    }
}

data class Mesa(
    var nombre: String ="",
    var idMesa: String ="",
    var cantidadSubcuentas: String = "",
    var tiempo: Int = 0,
    var total: String = "",
    var estado: String= "",
    var salon: String= "",
    var clienteId: String = "SN"
)

data class ArticuloSac(
    var nombre: String="",
    var codigo: String= "",
    var precio: Double = 0.00,
    var cantidadArticulosObli: Int = 1,
    var imp1 : String= "",
    var listaGrupos : List<SacGrupo> = emptyList()
)

data class SacGrupo(
    var nombre: String="",
    var cantidadItems: Int = 0,
    var articulos : List<ArticuloSacGrupo> = emptyList()
)

data class ArticuloSacGrupo(
    var nombre: String="",
    var nombreGrupo : String = "",
    var idGrupo: String = "",
    var codigo: String= "",
    var cantidad: Int = 0,
    var precio: Double = 0.00,
    var imp1: String = "",
    var isOpcional : Int = 0
)

data class FamiliaSac(
    var codigo: String= "",
    var nombre: String = "",
    var subFamilias: String= ""
)

data class SubFamiliaSac(
    var codigo: String="",
    var nombre: String=""
)

data class ArticulosSeleccionadosSac(
    var codigo: String = "",
    var nombre: String = "",
    var anotacion: String = "",
    var cantidad: Int = 0,
    var precioUnitario: Double = 0.0,
    var montoTotal : Double = 0.0,
    var subCuenta : String = "",
    var articulosCombo : List<ArticuloSacGrupo> = emptyList(),
    var idGrupo: String = "",
    var isCombo : Int = 0,
    var cantidadArticulosObli : Int = 1,
    var imp1: String = ""
){
    fun calcularMontoTotal() {
        montoTotal = (precioUnitario * cantidad.toDouble())
        for (i in articulosCombo.indices){
            val articulo = articulosCombo[i]
            montoTotal+= articulo.precio*articulo.cantidad
        }
    }
}

data class ArticuloComandado(
    val Consec: String= "",
    val Cod_Articulo: String= "",
    var Cantidad: Int= 0,
    val Precio: Double = 0.00,
    val Imp1: String= "",
    val Imp2: String= "",
    val Linea: String = "",
    var SubCuenta: String= "",
    val nombre: String = "",
    var montoTotal: Double= 0.00,
    var anotacion: String = "Sin Anotacion",
    var articulos : List<ArticuloSacGrupo> = emptyList(),
    var isCombo : Int = 0
){
    fun calcularMontoTotal() {
        montoTotal = (Precio*Cantidad.toDouble())
        for (i in articulos.indices){
            val articulo = articulos[i]
            montoTotal+= articulo.precio*articulo.cantidad
        }
    }
}
