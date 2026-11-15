package com.example.tryasknget.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.tryasknget.R
import com.example.tryasknget.viewmodel.AuthViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        authViewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        val etName = findViewById<EditText>(R.id.etRegName)
        val etEmail = findViewById<EditText>(R.id.etRegEmail)
        val etPassword = findViewById<EditText>(R.id.etRegPassword)
        val cbProvider = findViewById<CheckBox>(R.id.cbIsProvider)
        val btnRegister = findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            authViewModel.register(
                fullName = etName.text.toString(),
                email = etEmail.text.toString(),
                password = etPassword.text.toString(),
                isProvider = cbProvider.isChecked
            )
        }

        authViewModel.authUser.observe(this) { user ->
            if (user != null) {
                val prefs = getSharedPreferences("AsknGetPrefs", MODE_PRIVATE)
                prefs.edit().putLong("CURRENT_USER_ID", user.userId).apply()

                Toast.makeText(this, "Welcome to AsknGet!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, InquiryFeedActivity::class.java))
                finish()
            }
        }

        authViewModel.authError.observe(this) { error ->
            error?.let { Toast.makeText(this, it, Toast.LENGTH_SHORT).show() }
        }
    }
}