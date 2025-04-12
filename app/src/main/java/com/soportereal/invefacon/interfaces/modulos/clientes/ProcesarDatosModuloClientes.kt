package com.soportereal.invefacon.interfaces.modulos.clientes

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp
import okhttp3.MultipartBody
import org.json.JSONObject

class ProcesarDatosModuloClientes(apiToken: String){

    private val objetoFuncionesHttpInvefacon= FuncionesHttp(apiToken = apiToken)

    suspend fun obtenerDatosClientes(
        clientesPorPagina: String? = "1",
        paginaCliente: String? = "1",
        clienteDatoBusqueda: String,
        clienteEstado: String? = "",
        busquedaPor: String? = "codigo"
    ):JSONObject?
    {
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("porPagina", clientesPorPagina?:"1")
            .addFormDataPart("paginaActual", paginaCliente?: "1")
            .addFormDataPart("cliente${busquedaPor?:"codigo"}", clienteDatoBusqueda)
            .addFormDataPart("clientecodigoestado", clienteEstado?:"")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "clientes/clientes.php")
    }

    suspend fun obtenerDatosAgentes():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("estado", "1")
            .addFormDataPart("grupo", "2")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "seguridad/usuarios.php")
    }

    suspend fun obtenerTiposClientes():JSONObject?{
        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("estado", "1")
            .build()
        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "clientes/tipocliente.php")
    }

    suspend fun actualizarDatosClientes(
        clienteActual: Cliente,
        clienteModificado: Cliente
    ): JSONObject?
    {
        val formBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)

        // Agregar solo los campos que hayan cambiado
        formBuilder.addFormDataPart("Id_Cliente", clienteModificado.Id_cliente)
        formBuilder.addFormDataPart("Nombre", clienteModificado.Nombre)
        formBuilder.addFormDataPart("EmailFactura", clienteModificado.EmailFactura)
        formBuilder.addFormDataPart("Email", clienteModificado.Email)
        formBuilder.addFormDataPart("EmailCobro", clienteModificado.EmailCobro)
        formBuilder.addFormDataPart("Telefonos", clienteModificado.Telefonos)
        formBuilder.addFormDataPart("Direccion", clienteModificado.Direccion)
        formBuilder.addFormDataPart("AgenteVentas",clienteModificado.AgenteVentas)
        formBuilder.addFormDataPart("Cod_Moneda", clienteModificado.Cod_Moneda)
        formBuilder.addFormDataPart("TipoIdentificacion",clienteModificado.TipoIdentificacion)
        formBuilder.addFormDataPart("ClienteNombreComercial", clienteModificado.ClienteNombreComercial)
        formBuilder.addFormDataPart("TipoPrecioVenta", clienteModificado.TipoPrecioVenta)

        if (clienteActual.Cod_Tipo_Cliente != clienteModificado.Cod_Tipo_Cliente) {
            formBuilder.addFormDataPart("Cod_Tipo_Cliente", clienteModificado.Cod_Tipo_Cliente)
        }

        if (clienteActual.DiaCobro != clienteModificado.DiaCobro) {
            formBuilder.addFormDataPart("DiaCobro", clienteModificado.DiaCobro)
        }
        if (clienteActual.Contacto != clienteModificado.Contacto) {
            formBuilder.addFormDataPart("Contacto", clienteModificado.Contacto)
        }
        if (clienteActual.Exento != clienteModificado.Exento) {
            formBuilder.addFormDataPart("Exento",clienteModificado.Exento)
        }

        if (clienteActual.Cod_Zona != clienteModificado.Cod_Zona) {
            formBuilder.addFormDataPart("Cod_Zona", clienteModificado.Cod_Zona)
        }
        if (clienteActual.DetalleContrato != clienteModificado.DetalleContrato) {
            formBuilder.addFormDataPart("DetalleContrato", clienteModificado.DetalleContrato)
        }
        if (clienteActual.MontoContrato != clienteModificado.MontoContrato) {
            formBuilder.addFormDataPart("MontoContrato", clienteModificado.MontoContrato)
        }
        if (clienteActual.Descuento != clienteModificado.Descuento) {
            formBuilder.addFormDataPart("Descuento", clienteModificado.Descuento)
        }
        if (clienteActual.MontoCredito != clienteModificado.MontoCredito) {
            formBuilder.addFormDataPart("MontoCredito", clienteModificado.MontoCredito)
        }
        if (clienteActual.plazo != clienteModificado.plazo) {
            formBuilder.addFormDataPart("plazo", clienteModificado.plazo)
        }
        if (clienteActual.TieneCredito != clienteModificado.TieneCredito) {
            println(clienteActual.TieneCredito)
            println( clienteModificado.TieneCredito)
            formBuilder.addFormDataPart("TieneCredito", clienteModificado.TieneCredito)
        }
        if (clienteActual.FechaNacimiento != clienteModificado.FechaNacimiento) {
            formBuilder.addFormDataPart("FechaNacimiento", clienteModificado.FechaNacimiento)
        }

        // Constuir el cuerpo del formulario
        val formBody = formBuilder.build()

        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "clientes/editarcliente.php")

    }

    suspend fun agregarCliente(datosCliente: Cliente): JSONObject?{

        val formBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("Nombre", datosCliente.Nombre)
            .addFormDataPart("ClienteNombreComercial", datosCliente.nombreComercial)
            .addFormDataPart("Telefonos", datosCliente.Telefonos)
            .addFormDataPart("Direccion", datosCliente.Direccion)
            .addFormDataPart("Email", datosCliente.Email)
            .addFormDataPart("TipoIdentificacion", datosCliente.TipoIdentificacion)
            .addFormDataPart("Cod_Moneda", datosCliente.Cod_Moneda)
            .addFormDataPart("TipoPrecioVenta", datosCliente.TipoPrecioVenta)
            .addFormDataPart("Descuento", datosCliente.Descuento)
            .addFormDataPart("TieneCredito", datosCliente.TieneCredito)
            .addFormDataPart("MontoCredito", datosCliente.MontoCredito)
            .addFormDataPart("plazo", datosCliente.plazo)
            .addFormDataPart("MontoContrato", datosCliente.MontoContrato)
            .addFormDataPart("Cod_Tipo_Cliente", datosCliente.Cod_Tipo_Cliente)
            .addFormDataPart("DiaCobro", datosCliente.DiaCobro)
            .addFormDataPart("Contacto", datosCliente.Contacto)
            .addFormDataPart("Exento", datosCliente.Exento)
            .addFormDataPart("AgenteVentas", datosCliente.AgenteVentas)
            .addFormDataPart("Cod_Zona", datosCliente.Cod_Zona)
            .addFormDataPart("DetalleContrato", datosCliente.DetalleContrato)
            .addFormDataPart("FechaNacimiento", datosCliente.FechaNacimiento)
            .addFormDataPart("EmailFactura", datosCliente.EmailFactura)
            .addFormDataPart("EmailCobro", datosCliente.EmailCobro)
            .addFormDataPart("Cedula", datosCliente.Cedula)
            .build()

        return objetoFuncionesHttpInvefacon.metodoPost(formBody = formBody, apiDirectorio = "clientes/agregarcliente.php")
    }
}



data class Cliente(
    var Id_cliente: String = "",
    var Nombre: String = "",
    var Telefonos: String = "",
    var Direccion: String = "",
    var TipoPrecioVenta: String = "1",
    var Cod_Tipo_Cliente: String = "",
    var Email: String = "",
    var Cedula: String= "",
    var DiaCobro: String = "",
    var Contacto: String = "",
    var Exento: String = "",
    var AgenteVentas: String = "",
    var Cod_Zona: String = "",
    var DetalleContrato: String = "",
    var MontoContrato: String = "",
    var Descuento: String = "",
    var MontoCredito: String = "",
    var plazo: String = "",
    var TieneCredito: String = "",
    var FechaNacimiento: String = "",
    var Cod_Moneda: String = "CRC",
    var TipoIdentificacion: String = "01",
    var ClienteNombreComercial: String = "",
    var EmailFactura: String = "",
    var EmailCobro: String = "",
    var estado: String = "0",
    var codigo: String = "",
    var nombreComercial: String = "",
    var nombreJuridico: String = "",
    var correo: String = "",
    var exonerado: String = "",
    var cedulaCliente: String = "",
    var ultimaVenta: String = "",
    var noForzaCredito: String = "",
    var opcionesTipoCliente: SnapshotStateMap<String, String> = mutableStateMapOf(),
    var opcionesAgentesVentas:  SnapshotStateMap<String, String> = mutableStateMapOf(),
    var opcionesEstadoCliente:  SnapshotStateMap<String, String> = mutableStateMapOf(),
    var opcionesLogicasCliente:  SnapshotStateMap<String, String> = mutableStateMapOf(),
    var opcionesTipoIndetificacionCliente:  SnapshotStateMap<String, String> = mutableStateMapOf()
)