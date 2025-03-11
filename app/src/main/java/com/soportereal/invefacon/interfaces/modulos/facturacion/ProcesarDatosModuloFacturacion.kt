package com.soportereal.invefacon.interfaces.modulos.facturacion

import com.soportereal.invefacon.funciones_de_interfaces.FuncionesHttp

class ProcesarDatosModuloFacturacion(apiToken: String) {
    private val objetoFuncionesHttpInvefacon = FuncionesHttp(apiToken = apiToken)
}

data class Articulo(
    var descripcion: String,
    var precioUnitario: Double,
    var descuento: Double,
    var total : Double,
    var cantidad : Int
)