package org.utl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.utl.model.Platillo

@Dao
interface PlatilloDao {

    //insertar platillo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlatillo(platillo: Platillo): Long

    @Query ("SELECT * FROM platillo")
    fun getAllPlatillos (): Flow<List<Platillo>>

    // Insertar varios de golpe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarios(platillos: List<Platillo>)

    // Actualizar stock
    @Query("UPDATE platillo SET stock = :nuevoStock, disponible = CASE WHEN :nuevoStock > 0 THEN 1 ELSE 0 END WHERE id = :id")
    suspend fun actualizarStock(id: Int, nuevoStock: Int): Int

    // Resta stock(solo si hay disponible)
    // Devuelve las filas afectadas (1 si se restó, 0 si no)
    @Query("UPDATE platillo SET stock = stock - :cantidad, disponible = CASE WHEN (stock - :cantidad) > 0 THEN 1 ELSE 0 END WHERE id = :id AND stock >= :cantidad")
    suspend fun restarStockDisponible(id: Int, cantidad: Int): Int

    // Obtener stock actual
    @Query("SELECT stock FROM platillo WHERE id = :id LIMIT 1")
    suspend fun obtenerStockId(id: Int): Int?

}