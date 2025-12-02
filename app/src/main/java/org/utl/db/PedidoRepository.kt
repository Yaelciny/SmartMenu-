package org.utl.db

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import org.utl.dao.PedidoDao
import org.utl.dao.PedidoDetalleDao
import org.utl.dao.PlatilloDao
import org.utl.model.Pedido
import org.utl.model.PedidoConDetalles
import org.utl.model.PedidoDetalle
import org.utl.model.Usuario

class PedidoRepository (
    private val db: AppDataBase,
    private val pedidoDao: PedidoDao,
    private val pedidoDetalleDao: PedidoDetalleDao,
    private val platilloDao: PlatilloDao
){

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

    //Modificacion para que tambien reste stock(revisar)
    //Uso de Exceptions
    suspend fun insertarPedidoConStock(pedido: Pedido, detalles: List<PedidoDetalle>): Boolean {
        return try {
            db.withTransaction {
                val idPedido = pedidoDao.insertPedido(pedido)
                val detallesConId = detalles.map { detalle -> detalle.copy(idPedido = idPedido.toInt()) }
                pedidoDetalleDao.insertDetalle(detallesConId)

                // Para cada detalle, restar stock
                //Se lanza exception si no hay
                detallesConId.forEach { detalle ->
                    val rows = platilloDao.restarStockDisponible(detalle.idPlatillo, detalle.cantidad)
                    if (rows == 0) {
                        throw IllegalStateException("Stock insuficiente para ${detalle.nombrePedido}")
                    }
                }
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // Escuchar la cocina
    val pedidosEnCocina: Flow<List<PedidoConDetalles>> = pedidoDao.obtenerPedidosPendientes()

    // Marcar como listo
    suspend fun finalizarPedido(id: Int) {
        pedidoDao.terminarPedido(id)
    }

}