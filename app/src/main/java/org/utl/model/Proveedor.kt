package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "proveedor")
data class  Proveedor (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    //datos d contacto: email y num
    val email: String,
    val telefono: String,
    val insumo: String
)