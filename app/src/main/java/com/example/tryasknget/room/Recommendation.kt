package com.example.tryasknget.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recommendations")
data class Recommendation(
    @PrimaryKey(autoGenerate = true)
    val recommendationId: Long = 0L,
    val firebaseId: String? = null,
    val inquiryId: Long,
    val userId: Long,
    val comment: String,
    val recommendedPlaceOrBrand: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)