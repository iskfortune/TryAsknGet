package com.example.tryasknget.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tryasknget.room.Inquiry

@Dao
interface InquiryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInquiry(inquiry: Inquiry): Long

    @Query("SELECT * FROM inquiries ORDER BY timestamp DESC")
    fun getAllInquiries(): LiveData<List<Inquiry>>

    @Query("SELECT * FROM inquiries WHERE inquiryId = :inquiryId")
    fun getInquiryById(inquiryId: Long): LiveData<Inquiry>

    @Query("SELECT * FROM inquiries WHERE userId = :userId ORDER BY timestamp DESC")
    fun getInquiriesByUser(userId: Long): LiveData<List<Inquiry>>
}