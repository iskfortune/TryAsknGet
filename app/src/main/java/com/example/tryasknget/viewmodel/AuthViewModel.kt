package com.example.tryasknget.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tryasknget.repository.UserRepository
import com.example.tryasknget.room.AppDatabase
import com.example.tryasknget.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository

    private val _authUser = MutableLiveData<User?>()
    val authUser: LiveData<User?> get() = _authUser

    private val _authError = MutableLiveData<String?>()
    val authError: LiveData<String?> get() = _authError

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        userRepository = UserRepository(userDao)
    }

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _authError.value = "Email and password cannot be empty."
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val user = userRepository.loginUser(email.trim(), password)
            if (user != null) {
                _authUser.postValue(user)
            } else {
                _authError.postValue("Invalid email or password.")
            }
        }
    }

    fun register(fullName: String, email: String, password: String, isProvider: Boolean) {
        if (fullName.isBlank() || email.isBlank() || password.length < 6) {
            _authError.value = "Fill all fields. Password must be at least 6 characters."
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val newUser = User(
                    fullName = fullName.trim(),
                    email = email.trim(),
                    passwordHash = password,
                    isProvider = isProvider
                )
                val id = userRepository.registerUser(newUser)
                _authUser.postValue(newUser.copy(userId = id))
            } catch (e: Exception) {
                _authError.postValue("Registration failed: ${e.message}")
            }
        }
    }
}