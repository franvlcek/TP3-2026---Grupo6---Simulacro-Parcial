package com.ort.quotesappgrupo6.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quotes")
data class QuoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String, // Agregado para separar favoritos por usuario
    val quote: String,
    val author: String,
    val category: String
)