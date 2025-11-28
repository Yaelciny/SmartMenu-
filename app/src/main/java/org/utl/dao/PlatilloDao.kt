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
    suspend fun insertarPlatillo(platillo: Platillo): Int

    @Query ("SELECT * FROM platillo")
    fun getAllPlatillos (): Flow<List<Platillo>>
    
}