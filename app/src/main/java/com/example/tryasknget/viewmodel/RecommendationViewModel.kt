package com.example.tryasknget.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tryasknget.repository.RecommendationRepository
import com.example.tryasknget.room.AppDatabase
import com.example.tryasknget.room.Recommendation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendationViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecommendationRepository

    private val _validationError = MutableLiveData<String>()
    val validationError: LiveData<String> get() = _validationError

    init {
        val dao = AppDatabase.getDatabase(application).recommendationDao()
        repository = RecommendationRepository(dao)
    }

    fun getRecommendations(inquiryId: Long): LiveData<List<Recommendation>> {
        return repository.getRecommendationsForInquiry(inquiryId)
    }

    fun addRecommendation(inquiryId: Long, userId: Long, placeOrBrand: String, comment: String) {
        if (placeOrBrand.isBlank() || comment.isBlank()) {
            _validationError.value = "Please enter a recommendation and a brief comment."
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val rec = Recommendation(
                inquiryId = inquiryId,
                userId = userId,
                recommendedPlaceOrBrand = placeOrBrand.trim(),
                comment = comment.trim()
            )
            repository.addRecommendation(rec)
        }
    }
}