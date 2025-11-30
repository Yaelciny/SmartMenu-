package org.utl.db

import kotlinx.coroutines.flow.Flow
import org.utl.dao.PedidoDao
import org.utl.dao.PedidoDetalleDao
import org.utl.model.Pedido
import org.utl.model.PedidoDetalle
import org.utl.model.Usuario

class  PedidoRepository (private val pedidoDao: PedidoDao, private val pedidoDetalleDao: PedidoDetalleDao){

    val allPedidos: Flow<List<Pedido>> = pedidoDao.getAllPedidos()

    suspend fun insertPedido(pedido: Pedido): Long{
       return pedidoDao.insertPedido(pedido)
    }

    suspend fun insertDetalle (detalles: List<PedidoDetalle>): List<Long>{
       return pedidoDetalleDao.insertDetalle(detalles)
    }

    fun getDetallePorPedido (idPedido: Int): Flow<List<PedidoDetalle>>{
        return pedidoDetalleDao.getDetallePorPedido(idPedido)
    }

    suspend fun insertPedidoDetalles (pedido: Pedido, detalles: List<PedidoDetalle>): Boolean{
        val idPedido = insertPedido(pedido)

        //todos los id detalles en base  los pedidos
        val detallesConId = detalles.map { detalle ->
            detalle.copy(idPedido = idPedido.toInt())
        }

        insertDetalle(detallesConId)

        return true
    }

}