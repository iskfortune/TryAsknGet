package com.example.tryasknget.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tryasknget.repository.OfferRepository
import com.example.tryasknget.room.AppDatabase
import com.example.tryasknget.room.Offer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OfferViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: OfferRepository

    private val _offerSubmitted = MutableLiveData<Boolean>()
    val offerSubmitted: LiveData<Boolean> get() = _offerSubmitted

    private val _validationError = MutableLiveData<String>()
    val validationError: LiveData<String> get() = _validationError

    init {
        val dao = AppDatabase.getDatabase(application).offerDao()
        repository = OfferRepository(dao)
    }

    fun getOffersForInquiry(inquiryId: Long): LiveData<List<Offer>> {
        return repository.getOffersForInquiry(inquiryId)
    }

    fun submitOffer(
        inquiryId: Long,
        providerId: Long,
        storeName: String,
        description: String,
        priceInput: String
    ) {
        val price = priceInput.toDoubleOrNull()
        if (storeName.isBlank() || description.isBlank() || price == null || price <= 0.0) {
            _validationError.value = "Enter a valid store name, offer details, and price."
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val offer = Offer(
                inquiryId = inquiryId,
                providerId = providerId,
                storeOrProviderName = storeName.trim(),
                offerDescription = description.trim(),
                price = price
            )
            repository.submitOffer(offer)
            _offerSubmitted.postValue(true)
        }
    }
}