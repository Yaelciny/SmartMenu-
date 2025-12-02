package org.utl.db

import kotlinx.coroutines.flow.Flow
import org.utl.dao.PlatilloDao
import org.utl.model.Platillo

class PlatilloRepository (private val platilloDao: PlatilloDao) {
    //clase que permite el acceso al platillo}

    val allPlatillos: Flow<List<Platillo>> = platilloDao.getAllPlatillos()

    suspend fun insertPlatillo (platillo: Platillo): Long{
        return platilloDao.insertPlatillo(platillo)
    }

    suspend fun insertarVarios(platillos: List<Platillo>) {
        platilloDao.insertarVarios(platillos)
    }

    suspend fun actualizarStock(id: Int, nuevoStock: Int): Boolean{
        return platilloDao.actualizarStock(id, nuevoStock) > 0
    }

    suspend fun restarStockDisponible(id: Int, cantidad: Int): Boolean{
        return platilloDao.restarStockDisponible(id, cantidad) > 0
    }

    suspend fun obtenerStock(id: Int): Int?{
        return platilloDao.obtenerStockId(id)
    }
}