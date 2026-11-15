package com.example.tryasknget.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tryasknget.repository.InquiryRepository
import com.example.tryasknget.room.AppDatabase
import com.example.tryasknget.room.Category
import com.example.tryasknget.room.Inquiry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InquiryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InquiryRepository
    val allInquiries: LiveData<List<Inquiry>>
    val allCategories: LiveData<List<Category>>

    private val _postSuccess = MutableLiveData<Boolean>()
    val postSuccess: LiveData<Boolean> get() = _postSuccess

    private val _validationError = MutableLiveData<String>()
    val validationError: LiveData<String> get() = _validationError

    init {
        val db = AppDatabase.getDatabase(application)
        repository = InquiryRepository(db.inquiryDao(), db.categoryDao())
        allInquiries = repository.allInquiries
        allCategories = repository.allCategories
    }

    fun getInquiryById(inquiryId: Long): LiveData<Inquiry> {
        return repository.getInquiryById(inquiryId)
    }

    fun postInquiry(userId: Long, categoryId: Long, title: String, description: String) {
        if (title.isBlank() || description.isBlank()) {
            _validationError.value = "Title and description are required."
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val inquiry = Inquiry(
                userId = userId,
                categoryId = categoryId,
                title = title.trim(),
                description = description.trim()
            )
            repository.postInquiry(inquiry)
            _postSuccess.postValue(true)
        }
    }
}