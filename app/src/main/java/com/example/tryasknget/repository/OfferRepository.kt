package com.example.tryasknget.repository

import androidx.lifecycle.LiveData
import com.example.tryasknget.room.OfferDao
import com.example.tryasknget.room.Offer

class OfferRepository(private val offerDao: OfferDao) {

    private val useRemoteFirebase = false

    fun getOffersForInquiry(inquiryId: Long): LiveData<List<Offer>> {
        return offerDao.getOffersForInquiry(inquiryId)
    }

    fun getProviderOffers(providerId: Long): LiveData<List<Offer>> {
        return offerDao.getOffersByProvider(providerId)
    }

    suspend fun submitOffer(offer: Offer): Long {
        val localId = offerDao.insertOffer(offer)
        if (useRemoteFirebase) {
            // TODO: Push offer to Firestore "offers" collection
        }
        return localId
    }
}