package org.utl.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import org.utl.model.Pedido
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
}