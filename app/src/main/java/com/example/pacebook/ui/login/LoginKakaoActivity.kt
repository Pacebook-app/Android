package com.example.pacebook.ui.login

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pacebook.R
import com.example.pacebook.databinding.ActivityLoginKakaoBinding

class LoginKakaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginKakaoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginKakaoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}