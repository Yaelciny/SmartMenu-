package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingrediente")
data class Ingrediente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nombre: String,
    val stock: Int,
)