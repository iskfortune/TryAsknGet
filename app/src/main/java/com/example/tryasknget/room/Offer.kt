package com.example.tryasknget.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offers")
data class Offer(
    @PrimaryKey(autoGenerate = true)
    val offerId: Long = 0L,
    val firebaseId: String? = null,
    val inquiryId: Long,
    val providerId: Long,
    val storeOrProviderName: String,
    val offerDescription: String,
    val price: Double,
    val status: String = "PENDING", // PENDING, ACCEPTED, DECLINED
    val timestamp: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)