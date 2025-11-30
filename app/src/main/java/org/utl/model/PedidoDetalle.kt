package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "pedido_detalle")
data class PedidoDetalle(
    //aki ya van datos van exactos: platillos y la cantidad (desglosado)
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idPedido: Int, //para relacionar con pedido directamente
    val idPlatillo: Int, //para relacionar con platillos solicitados
    val nombrePedido: String,
    val precioPedido: Double,
    val cantidad: Int
)