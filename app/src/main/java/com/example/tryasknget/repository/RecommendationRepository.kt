package com.example.tryasknget.repository

import androidx.lifecycle.LiveData
import com.example.tryasknget.room.RecommendationDao
import com.example.tryasknget.room.Recommendation

class RecommendationRepository(private val recommendationDao: RecommendationDao) {

    private val useRemoteFirebase = false

    fun getRecommendationsForInquiry(inquiryId: Long): LiveData<List<Recommendation>> {
        return recommendationDao.getRecommendationsForInquiry(inquiryId)
    }

    suspend fun addRecommendation(recommendation: Recommendation): Long {
        val localId = recommendationDao.insertRecommendation(recommendation)
        if (useRemoteFirebase) {
            // TODO: Push recommendation to Firestore "recommendations" collection
        }
        return localId
    }
}