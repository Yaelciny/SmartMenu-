package org.utl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import org.utl.model.Pedido
import org.utl.model.PedidoConDetalles
import org.utl.model.Platillo
import org.utl.model.Usuario

@Dao
interface PedidoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPedido(pedido: Pedido): Long

    @Query ("SELECT * FROM pedido")
    fun getAllPedidos (): Flow<List<Pedido>>

    @Query ("SELECT * FROM pedido WHERE id = :id")
    suspend fun getPedidoById (id:Int): Pedido?

    @Transaction //cuando se usa @RELATION
    @Query("SELECT * FROM pedido WHERE estado = 'Pendiente'")
    fun obtenerPedidosPendientes(): Flow<List<PedidoConDetalles>>

    // para cambiar el estado a termiando
    @Query("UPDATE pedido SET estado = 'Terminado' WHERE id = :idPedido")
    suspend fun terminarPedido(idPedido: Int)
}