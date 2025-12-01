package org.utl.model

import androidx.room.Embedded
import androidx.room.Relation

//es una clase de ayuda
data class PedidoConDetalles(
    @Embedded val pedido: Pedido,

    @Relation(
        parentColumn = "id",       // El ID en la tabla Pedido
        entityColumn = "idPedido"  // La FK en la tabla PedidoDetalle
    )
    val detalles: List<PedidoDetalle>
)
