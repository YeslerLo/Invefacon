package com.soportereal.invefacon.funciones_de_interfaces

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.json.JSONException
import org.json.JSONObject
import kotlin.coroutines.cancellation.CancellationException

class FuncionesHttp(
    private val servidorUrl:String = "https://invefacon.com/api/",
    private val apiToken:String
) {

    suspend fun metodoPost(formBody: MultipartBody, apiDirectorio: String): JSONObject? {
        return withContext(Dispatchers.IO) {
            // Estado para el cliente HTTP
            val client = OkHttpClient()
            // Configuración de la solicitud POST
            val request = Request.Builder()
                .url("$servidorUrl/$apiDirectorio")
                .post(formBody)
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer $apiToken")
                .build()
            try {
                // Ejecutar la solicitud en el contexto de IO
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody!=null){
                        val json = JSONObject(responseBody)
                        val data = json.getString("status")
                        println(responseBody)
                        JSONObject(responseBody)
                    }else{
                        JSONObject("""{"code":401,"status":"error","data":"El servidor no regresó ningun dato"}""")
                    }
                } else {
                    JSONObject("""{"code":401,"status":"error","data":"Error respuesta servidor"}""")
                }
            } catch (e: IOException) {
                JSONObject("""{"code":401,"status":"error","data":"Revise su conexion a Internet"}""")
            } catch (e: JSONException) {
                JSONObject("""{"code":401,"status":"error","data":"$e"}""")
            } catch (e: CancellationException){
                null
            } catch (e: Exception) {
                e.printStackTrace()
                JSONObject(
                    """{
                "code":401,
                "status":"error",
                "data":"Error desconocido exceptionType":"${e.javaClass.name} message":"${e.message}"
                }"""
                )
            }
        }
    }
}
