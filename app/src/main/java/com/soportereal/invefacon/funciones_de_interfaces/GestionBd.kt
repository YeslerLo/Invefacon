package com.soportereal.invefacon.funciones_de_interfaces

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@Entity(tableName = "articulosFacturacion")
data class ArticuloDb(
    @PrimaryKey(autoGenerate = true) val id: Int = 0 ,
    val codigo: String,
    val codigoBarra : String,
    val description: String,
    val datos: String,
    val empresa: String
)

@Dao
interface ArticuloDao {
    @Insert
    suspend fun insertar(articulo: ArticuloDb)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarArticulos(lista: List<ArticuloDb>)


    @Query("DELETE FROM articulosFacturacion WHERE codigo = :codigo AND empresa = :empresa")
    suspend fun eliminarArticulo(codigo: String, empresa: String)

    @Query("SELECT * FROM articulosFacturacion WHERE empresa = :empresa")
    fun obtenerTodos(empresa: String): Flow<List<ArticuloDb>>


    @Query("""
        SELECT * FROM articulosFacturacion
        WHERE empresa = :empresa AND (
            codigo = :codigo)
    """)
    suspend fun obtenerArticulo(empresa: String, codigo: String): ArticuloDb?

    // Coincidencia exacta
    @Query("""
    SELECT * FROM articulosFacturacion
    WHERE empresa = :empresa AND (
        codigo = :input COLLATE NOCASE OR 
        codigoBarra = :input COLLATE NOCASE OR
        description = :input COLLATE NOCASE
    )
""")
    fun busquedaExacta(empresa: String, input: String): Flow<List<ArticuloDb>>

    // Coincidencias parciales
    @Query("""
    SELECT * FROM articulosFacturacion
    WHERE empresa = :empresa AND (
        codigo || codigoBarra || description
    ) LIKE '%' || :input || '%'
    LIMIT 50
""")
    fun busquedaParcial(empresa: String, input: String): Flow<List<ArticuloDb>>

    @Delete
    suspend fun eliminar(articulo: ArticuloDb)

}

@Database(entities = [ArticuloDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun articuloDao(): ArticuloDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}


class GestorTablaArticulos(application: Application) : AndroidViewModel(application) {
    private val db = AppDatabase.getDatabase(application)
    private val articuloDao = db.articuloDao()
    private val _articulo = MutableStateFlow<ArticuloDb?>(null)

    val articulo: StateFlow<ArticuloDb?> = _articulo
    private val _empresa = MutableStateFlow("")
    private val _inputBusqueda = MutableStateFlow("")
    private val _modoExacto = MutableStateFlow(true) // cambiar si quieres parcial o exacta

    @OptIn(ExperimentalCoroutinesApi::class)
    val articulos: StateFlow<List<ArticuloDb>> = combine(
        _empresa, _inputBusqueda, _modoExacto
    ) { empresa, input, exacto ->
        Triple(empresa, input, exacto)
    }.flatMapLatest { (empresa, input, exacto) ->
        if (input.isBlank()) {
            flowOf(emptyList())
        } else {
            if (exacto) {
                articuloDao.busquedaExacta(empresa, input)
            } else {
                articuloDao.busquedaParcial(empresa, input)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun cargarEmpresa(empresa: String) {
        _empresa.value = empresa
    }

    fun actualizarInputBusqueda(input: String) {
        _inputBusqueda.value = input
    }

    fun modoExacto(buscarExacto: Boolean) {
        _modoExacto.value = buscarExacto
    }

    fun eliminarArticulo(codigo: String, empresa: String) {
        viewModelScope.launch {
            articuloDao.eliminarArticulo(codigo, empresa)
        }
    }

    fun insertarArticulos(lista: List<ArticuloDb>) {
        viewModelScope.launch {
            articuloDao.insertarArticulos(lista)
        }
    }

    suspend fun obetenerDatosArticulo(codigo: String): ArticuloDb? {
        return articuloDao.obtenerArticulo(_empresa.value, codigo)
    }
}




