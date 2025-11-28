package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuario")
data class Usuario (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val nombre: String,
    val email: String,
    val password: String,
    //este rol se define como: administrador, mesero o cocinero
    val rol: String
)