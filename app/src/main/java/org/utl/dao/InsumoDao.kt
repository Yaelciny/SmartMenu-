package org.utl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.utl.model.Insumo
import org.utl.model.Platillo

@Dao
interface InsumoDao {

    //insertar platillo
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInsumo(insumo: Insumo): Long

    @Query ("SELECT * FROM insumo")
    fun getAllInsumos (): Flow<List<Insumo>>

    // Insertar varios de golpe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarVarios(insumos: List<Insumo>)
}