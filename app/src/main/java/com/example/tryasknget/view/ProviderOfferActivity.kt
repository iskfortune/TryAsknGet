package com.example.tryasknget.view

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryasknget.R
import com.example.tryasknget.viewmodel.OfferViewModel

class ProviderOfferActivity : AppCompatActivity() {

    private lateinit var offerViewModel: OfferViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_provider_offer)

        val inquiryId = intent.getLongExtra("INQUIRY_ID", -1L)
        val providerId = getSharedPreferences("AsknGetPrefs", MODE_PRIVATE)
            .getLong("CURRENT_USER_ID", 1L)

        offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]

        val etStoreName = findViewById<EditText>(R.id.etOfferStoreName)
        val etOfferDesc = findViewById<EditText>(R.id.etOfferDetails)
        val etOfferPrice = findViewById<EditText>(R.id.etOfferPrice)
        val btnSubmitOffer = findViewById<Button>(R.id.btnSubmitOffer)

        btnSubmitOffer.setOnClickListener {
            offerViewModel.submitOffer(
                inquiryId = inquiryId,
                providerId = providerId,
                storeName = etStoreName.text.toString(),
                description = etOfferDesc.text.toString(),
                priceInput = etOfferPrice.text.toString()
            )
        }

        offerViewModel.offerSubmitted.observe(this) { submitted ->
            if (submitted) {
                Toast.makeText(this, "Offer submitted!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        offerViewModel.validationError.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}