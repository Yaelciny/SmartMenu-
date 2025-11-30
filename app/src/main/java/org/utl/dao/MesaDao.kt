package org.utl.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.utl.model.Mesa

interface MesaDao {
    @Query("SELECT * FROM mesa")
    fun getAllMesas(): Flow<List<Mesa>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarMesa(mesa: Mesa): Long
}