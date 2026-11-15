package com.example.tryasknget.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tryasknget.R
import com.example.tryasknget.view.OfferAdapter
import com.example.tryasknget.view.RecommendationAdapter
import com.example.tryasknget.viewmodel.InquiryViewModel
import com.example.tryasknget.viewmodel.OfferViewModel
import com.example.tryasknget.viewmodel.RecommendationViewModel

class InquiryDetailActivity : AppCompatActivity() {

    private lateinit var inquiryViewModel: InquiryViewModel
    private lateinit var recommendationViewModel: RecommendationViewModel
    private lateinit var offerViewModel: OfferViewModel

    private lateinit var recommendationAdapter: RecommendationAdapter
    private lateinit var offerAdapter: OfferAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry_detail)

        val inquiryId = intent.getLongExtra("INQUIRY_ID", -1L)
        val currentUserId = getSharedPreferences("AsknGetPrefs", MODE_PRIVATE)
            .getLong("CURRENT_USER_ID", 1L)

        inquiryViewModel = ViewModelProvider(this)[InquiryViewModel::class.java]
        recommendationViewModel = ViewModelProvider(this)[RecommendationViewModel::class.java]
        offerViewModel = ViewModelProvider(this)[OfferViewModel::class.java]

        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvDescription = findViewById<TextView>(R.id.tvDetailDescription)
        val etRecPlace = findViewById<EditText>(R.id.etRecommendPlace)
        val etRecComment = findViewById<EditText>(R.id.etRecommendComment)
        val btnAddRec = findViewById<Button>(R.id.btnSubmitRecommendation)
        val btnMakeOffer = findViewById<Button>(R.id.btnMakeProviderOffer)
        val rvRecommendations = findViewById<RecyclerView>(R.id.rvRecommendations)
        val rvOffers = findViewById<RecyclerView>(R.id.rvOffers)

        recommendationAdapter = RecommendationAdapter()
        offerAdapter = OfferAdapter()

        rvRecommendations.layoutManager = LinearLayoutManager(this)
        rvRecommendations.adapter = recommendationAdapter

        rvOffers.layoutManager = LinearLayoutManager(this)
        rvOffers.adapter = offerAdapter

        inquiryViewModel.getInquiryById(inquiryId).observe(this) { inquiry ->
            inquiry?.let {
                tvTitle.text = it.title
                tvDescription.text = it.description
            }
        }

        recommendationViewModel.getRecommendations(inquiryId).observe(this) { recommendations ->
            recommendationAdapter.submitList(recommendations)
        }

        offerViewModel.getOffersForInquiry(inquiryId).observe(this) { offers ->
            offerAdapter.submitList(offers)
        }

        btnAddRec.setOnClickListener {
            recommendationViewModel.addRecommendation(
                inquiryId = inquiryId,
                userId = currentUserId,
                placeOrBrand = etRecPlace.text.toString(),
                comment = etRecComment.text.toString()
            )
            etRecPlace.text.clear()
            etRecComment.text.clear()
        }

        btnMakeOffer.setOnClickListener {
            val intent = Intent(this, ProviderOfferActivity::class.java)
            intent.putExtra("INQUIRY_ID", inquiryId)
            startActivity(intent)
        }

        recommendationViewModel.validationError.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}