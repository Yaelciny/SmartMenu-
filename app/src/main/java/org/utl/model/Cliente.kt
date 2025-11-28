package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class Cliente (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    //pendiente datos aun ya q es de mostrador
    val nombre: String,
    val historial: String?
)