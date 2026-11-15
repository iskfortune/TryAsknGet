package com.example.tryasknget.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inquiries")
data class Inquiry(
    @PrimaryKey(autoGenerate = true)
    val inquiryId: Long = 0L,
    val firebaseId: String? = null, // Ready for Firestore document ID
    val userId: Long,
    val categoryId: Long,
    val title: String,
    val description: String,
    val status: String = "OPEN", // OPEN, RESOLVED
    val timestamp: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false
)