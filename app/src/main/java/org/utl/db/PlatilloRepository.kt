package org.utl.db

import kotlinx.coroutines.flow.Flow
import org.utl.dao.PlatilloDao
import org.utl.model.Platillo

class PlatilloRepository (private val platilloDao: PlatilloDao) {
    //clase que permite el acceso al platillo}

    val allPlatillos: Flow<List<Platillo>> = platilloDao.getAllPlatillos()

    suspend fun insertPlatillo (platillo: Platillo): Int{
        return platilloDao.insertPlatillo(platillo)
    }

}