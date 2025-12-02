package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platillo")
data class Platillo (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val precio: Double,
    val disponible: Boolean,
    //Se agrega campo stock
    val stock : Int = 0
)