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
import androidx.room.RawQuery
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
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

    @RawQuery(observedEntities = [ArticuloDb::class])
    fun busquedaDinamica(query: SupportSQLiteQuery): Flow<List<ArticuloDb>>

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
                buscarArticulosPorPalabras(empresa,input)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun buscarArticulosPorPalabras(empresa: String, input: String): Flow<List<ArticuloDb>> {
        // Split the input string into individual words
        val palabras = input.split(" ")

        // Get the maximum number of articles to filter (e.g., 50)
        val cantArtFiltrar = obtenerValorParametroEmpresa("164", "50")

        // If the input is empty or contains only blank spaces, return an empty list immediately.
        if (palabras.isEmpty() || palabras.all { it.isBlank() }) {
            return flowOf(emptyList())
        }

        // Filter out any blank words that might result from splitting (e.g., multiple spaces)
        val validWords = palabras.filter { it.isNotBlank() }

        // If, after filtering, there are no valid search words, return an empty list.
        // This prevents building a query without search conditions.
        if (validWords.isEmpty()) {
            return flowOf(emptyList())
        }

        // Start building the SQL query string
        val queryBuilder = StringBuilder()

        queryBuilder.append("SELECT * FROM articulosFacturacion WHERE empresa = '")
        // Sanitize the company string to prevent SQL injection issues
        queryBuilder.append(empresa.replace("'", "''"))
        queryBuilder.append("'") // Close the company filter part

        // Append the opening parenthesis for the search conditions (codigo, codigoBarra, description)
        queryBuilder.append(" AND (")

        // Iterate through each valid search word and add a LIKE condition
        validWords.forEachIndexed { index, palabra ->
            // For subsequent words, add an 'AND' operator
            if (index > 0) {
                queryBuilder.append(" AND ")
            }
            // Use string concatenation (||) to search across multiple columns
            // Sanitize each search word to prevent SQL injection
            queryBuilder.append("(codigo || codigoBarra || description) LIKE '%")
            queryBuilder.append(palabra.replace("'", "''"))
            queryBuilder.append("%'")
        }
        queryBuilder.append(")") // Close the parenthesis for the search conditions

        // Determine the sorting order based on a company parameter
        val parametroOrdenBusqueda = obtenerValorParametroEmpresa("309", "1")
        val ordenBusqueda = when (parametroOrdenBusqueda) {
            "1" -> "ORDER BY description"
            "2" -> "ORDER BY codigo"
            else -> "" // If parameter is invalid, no specific order is applied
        }

        // **CRITICAL FIX: Append ORDER BY clause BEFORE the LIMIT clause**
        if (ordenBusqueda.isNotBlank()) {
            queryBuilder.append(" ") // Add a space for proper SQL syntax
            queryBuilder.append(ordenBusqueda)
        }

        // Append the LIMIT clause to restrict the number of results
        queryBuilder.append(" LIMIT ") // Add a space for proper SQL syntax
        queryBuilder.append(cantArtFiltrar)

        // Convert the StringBuilder content to a final SQL query string
        val queryString = queryBuilder.toString()
        // Create a SimpleSQLiteQuery object from the dynamically built string
        val queryCondition = SimpleSQLiteQuery(queryString)

        // Execute the dynamic query using your DAO
        return articuloDao.busquedaDinamica(queryCondition)
    }

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




