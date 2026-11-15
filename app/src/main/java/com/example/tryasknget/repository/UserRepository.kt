package com.example.tryasknget.repository

import androidx.lifecycle.LiveData
import com.example.tryasknget.room.UserDao
import com.example.tryasknget.room.User

class UserRepository(private val userDao: UserDao) {

    // Toggle to true when Firebase Authentication & Firestore are added later
    private val useRemoteFirebase = false

    suspend fun registerUser(user: User): Long {
        if (useRemoteFirebase) {
            // TODO: Call FirebaseAuth.getInstance().createUserWithEmailAndPassword(...)
        }
        return userDao.registerUser(user)
    }

    suspend fun loginUser(email: String, password: String): User? {
        if (useRemoteFirebase) {
            // TODO: Call FirebaseAuth.getInstance().signInWithEmailAndPassword(...)
        }
        return userDao.loginUser(email, password)
    }

    fun getUserProfile(userId: Long): LiveData<User> {
        return userDao.getUserById(userId)
    }

    suspend fun updateUserProfile(user: User) {
        userDao.updateUser(user)
        if (useRemoteFirebase) {
            // TODO: Sync updated trust score/profile to Firestore "users" collection
        }
    }
}