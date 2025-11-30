package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedido")
data class Pedido(
    //aki van solo datos generales del pedido: mesa y estado
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val mesa: Int,
    val estado: String //aki podemos poner si esta pendiente, finalizado, en proceso y asi
)