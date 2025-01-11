package com.soportereal.invefacon.interfaces.large.sac

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import okhttp3.MultipartBody
import org.json.JSONArray
import org.json.JSONObject

class ProcesarDatosModuloSac(apiToken: String){

    private val objetoFuncionesHttpInvefacon= FuncionesHttp(servidorUrl = "https://invefacon.com", apiToken = apiToken)
    suspend fun obtenerListaMesas(mesa: String=""): JSONObject?{

        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("mesa",mesa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "apiMovil/sac/listaMesas.php")
    }

    suspend fun crearNuevaMesa(nombreMesa: String, nombreSalon: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("MesaSubCuenta","JUNTOS")
            .addFormDataPart("MesaNombre",nombreMesa)
            .addFormDataPart("SalonNombre",nombreSalon)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "apiMovil/sac/crearMesa.php")
    }

    suspend fun obtenerListaArticulos(codFamilia: String, codSubFamilia: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("familia",codFamilia)
            .addFormDataPart("subfamilia",codSubFamilia)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "apiMovil/sac/listaArticulos.php")
    }

    suspend fun obtenerListaFamilias():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("opcional","opcional")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "apiMovil/sac/listaFamilias.php")
    }

    suspend fun comandarSubCuenta(mesa: String, salon: String, codUsuario: String, jsonComandaDetalle: JSONArray): JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa",mesa)
            .addFormDataPart("Salon",salon)
            .addFormDataPart("Cod_Usuario", codUsuario)
            .addFormDataPart("JsonComandaDetalle",jsonComandaDetalle. toString())
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "apimovil/sac/crearComanda.php")
    }
    suspend fun obtenerDatosMesaComandada(nombreMesa: String):JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Mesa",nombreMesa)
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "apimovil/sac/detalleMesa.php")
    }
}

data class Mesa(
    var nombre: String ="",
    var idMesa: String ="",
    var cantidadSubcuentas: String = "",
    var tiempo: Int = 0,
    var total: String = "",
    var estado: String= "",
    var salon: String= ""
)

data class ArticuloSac(
    var nombre: String="",
    var codigo: String= "",
    var precio: String = ""
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
    var tieneIVA: Boolean = false,
    var tieneImpuestoRestaurante:  Boolean = false,
    var estado: Int = 0,
    var montoTotal : Double = 0.0,
    var subCuenta : String = ""
){
    fun calcularMontoTotal() {
        montoTotal = (precioUnitario * cantidad)
    }
}
data class ArticuloComandado(
    val Consec: String,
    val Cod_Articulo: String,
    var Cantidad: Double,
    val Precio: Double,
    val Imp1: String,
    val Imp2: String,
    val Linea: String,
    var SubCuenta: String,
    val nombre: String,
    var montoTotal: Double= 0.00,
    var anotacion: String = "Sin Anotacion"
){
    fun calcularMontoTotal() {
        montoTotal = (Precio*Cantidad)
    }
}
