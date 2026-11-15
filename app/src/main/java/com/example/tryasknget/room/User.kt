package com.example.tryasknget.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Long = 0L,
    val firebaseUid: String? = null, // Ready for future Firebase Auth UID
    val fullName: String,
    val email: String,
    val passwordHash: String, // Used locally until Firebase Auth replaces it
    val trustScore: Double = 5.0,
    val isProvider: Boolean = false
)