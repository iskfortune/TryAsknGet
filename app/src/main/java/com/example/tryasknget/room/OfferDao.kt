package com.example.tryasknget.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.tryasknget.room.Offer

@Dao
interface OfferDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffer(offer: Offer): Long

    @Query("SELECT * FROM offers WHERE inquiryId = :inquiryId ORDER BY price ASC")
    fun getOffersForInquiry(inquiryId: Long): LiveData<List<Offer>>

    @Query("SELECT * FROM offers WHERE providerId = :providerId ORDER BY timestamp DESC")
    fun getOffersByProvider(providerId: Long): LiveData<List<Offer>>
}