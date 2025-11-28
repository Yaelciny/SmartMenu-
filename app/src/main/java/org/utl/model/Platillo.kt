package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "platillo")
data class Platillo (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val precio: Double,
    val disponible: Boolean
)