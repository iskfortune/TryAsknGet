package com.example.tryasknget.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.tryasknget.repository.InquiryRepository
import com.example.tryasknget.repository.OfferRepository
import com.example.tryasknget.repository.UserRepository
import com.example.tryasknget.room.AppDatabase
import com.example.tryasknget.room.Inquiry
import com.example.tryasknget.room.Offer
import com.example.tryasknget.room.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository: UserRepository
    private val inquiryRepository: InquiryRepository
    private val offerRepository: OfferRepository

    init {
        val db = AppDatabase.getDatabase(application)
        userRepository = UserRepository(db.userDao())
        inquiryRepository = InquiryRepository(db.inquiryDao(), db.categoryDao())
        offerRepository = OfferRepository(db.offerDao())
    }

    fun getUserProfile(userId: Long): LiveData<User> {
        return userRepository.getUserProfile(userId)
    }

    fun getMyInquiries(userId: Long): LiveData<List<Inquiry>> {
        return inquiryRepository.getUserInquiries(userId)
    }

    fun getMyProviderOffers(providerId: Long): LiveData<List<Offer>> {
        return offerRepository.getProviderOffers(providerId)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        userRepository.updateUserProfile(user)
    }
}