package org.utl.db

import kotlinx.coroutines.flow.Flow
import org.utl.dao.InsumoDao
import org.utl.dao.PlatilloDao
import org.utl.model.Insumo
import org.utl.model.Platillo

class InsumoRepository (private val insumoDao: InsumoDao) {
    //clase que permite el acceso insumos

    val allInsumos: Flow<List<Insumo>> = insumoDao.getAllInsumos()

    suspend fun insertInsumo (insumo: Insumo): Long{
        return insumoDao.insertInsumo(insumo)
    }

    suspend fun insertarVarios(insumo: List<Insumo>) {
        insumoDao.insertarVarios(insumo)
    }
}