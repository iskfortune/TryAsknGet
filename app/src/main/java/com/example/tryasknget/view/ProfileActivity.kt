package com.example.tryasknget.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryasknget.R
import com.example.tryasknget.viewmodel.ProfileViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val currentUserId = getSharedPreferences("AsknGetPrefs", MODE_PRIVATE)
            .getLong("CURRENT_USER_ID", 1L)

        profileViewModel = ViewModelProvider(this)[ProfileViewModel::class.java]

        val tvUserName = findViewById<TextView>(R.id.tvProfileName)
        val tvUserEmail = findViewById<TextView>(R.id.tvProfileEmail)
        val tvTrustScore = findViewById<TextView>(R.id.tvProfileTrustScore)
        val tvInquiryCount = findViewById<TextView>(R.id.tvMyInquiriesCount)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        profileViewModel.getUserProfile(currentUserId).observe(this) { user ->
            user?.let {
                tvUserName.text = it.fullName
                tvUserEmail.text = it.email
                tvTrustScore.text = "Trust Score: ${it.trustScore} / 5.0"
            }
        }

        profileViewModel.getMyInquiries(currentUserId).observe(this) { inquiries ->
            tvInquiryCount.text = "Inquiries Posted: ${inquiries.size}"
        }

        btnLogout.setOnClickListener {
            getSharedPreferences("AsknGetPrefs", MODE_PRIVATE).edit().clear().apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}