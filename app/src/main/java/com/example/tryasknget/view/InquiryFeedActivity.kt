package com.example.tryasknget.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tryasknget.R
import com.example.tryasknget.view.InquiryAdapter
import com.example.tryasknget.viewmodel.InquiryViewModel

class InquiryFeedActivity : AppCompatActivity() {

    private lateinit var inquiryViewModel: InquiryViewModel
    private lateinit var inquiryAdapter: InquiryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquiry_feed)

        inquiryViewModel = ViewModelProvider(this)[InquiryViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.rvInquiryFeed)
        val btnNewInquiry = findViewById<Button>(R.id.btnOpenPostInquiry)
        val btnProfile = findViewById<Button>(R.id.btnOpenProfile)

        inquiryAdapter = InquiryAdapter { selectedInquiry ->
            val intent = Intent(this, InquiryDetailActivity::class.java)
            intent.putExtra("INQUIRY_ID", selectedInquiry.inquiryId)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = inquiryAdapter

        inquiryViewModel.allInquiries.observe(this) { inquiries ->
            inquiryAdapter.submitList(inquiries)
        }

        btnNewInquiry.setOnClickListener {
            startActivity(Intent(this, PostInquiryActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}