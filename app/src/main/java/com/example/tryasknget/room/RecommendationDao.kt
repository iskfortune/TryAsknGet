package com.example.tryasknget.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tryasknget.room.Recommendation

@Dao
interface RecommendationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecommendation(recommendation: Recommendation): Long

    @Query("SELECT * FROM recommendations WHERE inquiryId = :inquiryId ORDER BY timestamp DESC")
    fun getRecommendationsForInquiry(inquiryId: Long): LiveData<List<Recommendation>>
}