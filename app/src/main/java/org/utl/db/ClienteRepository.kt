package org.utl.db

import kotlinx.coroutines.flow.Flow
import org.utl.dao.ClienteDao
import org.utl.model.Cliente

class ClienteRepository(private val clienteDao: ClienteDao) {
    val todosLosClientes: Flow<List<Cliente>> = clienteDao.getAllClientes()

    suspend fun insertar(cliente: Cliente){
        clienteDao.insertCliente(cliente)
    }
}