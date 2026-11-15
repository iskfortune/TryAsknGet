package com.example.tryasknget.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryasknget.R
import com.example.tryasknget.room.Category
import com.example.tryasknget.viewmodel.InquiryViewModel

class PostInquiryActivity : AppCompatActivity() {

    private lateinit var inquiryViewModel: InquiryViewModel
    private var categoryList: List<Category> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_inquiry)

        inquiryViewModel = ViewModelProvider(this)[InquiryViewModel::class.java]

        val currentUserId = getSharedPreferences("AsknGetPrefs", MODE_PRIVATE)
            .getLong("CURRENT_USER_ID", 1L)

        val spCategory = findViewById<Spinner>(R.id.spCategory)
        val etTitle = findViewById<EditText>(R.id.etInquiryTitle)
        val etDesc = findViewById<EditText>(R.id.etInquiryDescription)
        val btnSubmit = findViewById<Button>(R.id.btnSubmitInquiry)

        inquiryViewModel.allCategories.observe(this) { categories ->
            categoryList = categories
            val names = categories.map { "${it.name} (${it.type})" }
            spCategory.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, names)
        }

        btnSubmit.setOnClickListener {
            val selectedCategoryId = if (categoryList.isNotEmpty()) {
                categoryList[spCategory.selectedItemPosition].categoryId
            } else 1L

            inquiryViewModel.postInquiry(
                userId = currentUserId,
                categoryId = selectedCategoryId,
                title = etTitle.text.toString(),
                description = etDesc.text.toString()
            )
        }

        inquiryViewModel.postSuccess.observe(this) { success ->
            if (success) {
                Toast.makeText(this, "Inquiry posted!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        inquiryViewModel.validationError.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}