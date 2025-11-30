package org.utl.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("mesa")
data class Mesa(
    @PrimaryKey(true)
    val id: Int = 0,
    val numero: Int
)
