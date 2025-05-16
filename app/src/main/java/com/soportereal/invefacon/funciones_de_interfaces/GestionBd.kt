package com.soportereal.invefacon.funciones_de_interfaces

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "articulos")
data class Articulo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    val precio: Double
)

@Dao
interface ArticuloDao {
    @Insert
    suspend fun insertar(articulo: Articulo)

    @Query("SELECT * FROM articulos")
    fun obtenerTodos(): Flow<List<Articulo>>

    @Delete
    suspend fun eliminar(articulo: Articulo)
}


@Database(entities = [Articulo::class], version = 1)
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
                    "articulos_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}


