package com.soportereal.invefacon.funciones_de_interfaces

import com.soportereal.invefacon.interfaces.pantallas_principales.estadoRespuestaApi
import org.json.JSONObject

fun mostrarMensajeError(mensaje: String){
    val jsonObject = JSONObject(
        """
                    {
                        "code": 400,
                        "status": "error",
                        "data": "$mensaje"
                    }
                """
    )
    estadoRespuestaApi.cambiarEstadoRespuestaApi(mostrarRespuesta = true, datosRespuesta = jsonObject)
}