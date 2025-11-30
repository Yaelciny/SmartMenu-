package org.utl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.utl.model.Pedido
import org.utl.model.PedidoDetalle

@Dao
interface PedidoDetalleDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDetalle(detalles: List<PedidoDetalle>): List<Long>

    @Query("SELECT * FROM pedido_detalle WHERE idPedido = :idPedido")
    fun getDetallePorPedido (idPedido: Int): Flow<List<PedidoDetalle>>

}