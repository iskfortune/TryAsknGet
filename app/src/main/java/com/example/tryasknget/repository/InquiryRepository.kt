package com.example.tryasknget.repository

import androidx.lifecycle.LiveData
import com.example.tryasknget.room.CategoryDao
import com.example.tryasknget.room.InquiryDao
import com.example.tryasknget.room.Category
import com.example.tryasknget.room.Inquiry

class InquiryRepository(
    private val inquiryDao: InquiryDao,
    private val categoryDao: CategoryDao
) {
    private val useRemoteFirebase = false

    val allInquiries: LiveData<List<Inquiry>> = inquiryDao.getAllInquiries()
    val allCategories: LiveData<List<Category>> = categoryDao.getAllCategories()

    fun getInquiryById(inquiryId: Long): LiveData<Inquiry> {
        return inquiryDao.getInquiryById(inquiryId)
    }

    fun getUserInquiries(userId: Long): LiveData<List<Inquiry>> {
        return inquiryDao.getInquiriesByUser(userId)
    }

    suspend fun postInquiry(inquiry: Inquiry): Long {
        val localId = inquiryDao.insertInquiry(inquiry)
        if (useRemoteFirebase) {
            // TODO: Push inquiry to Firestore "inquiries" collection and update isSynced = true
        }
        return localId
    }
}