package com.example.tryasknget.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Long = 0L,
    val firebaseId: String? = null,
    val name: String, // e.g., "Electronics", "Home Repair", "Tutoring"
    val type: String  // "Product" or "Service"
)