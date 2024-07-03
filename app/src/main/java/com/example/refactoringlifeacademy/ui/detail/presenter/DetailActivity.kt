package com.example.refactoringlifeacademy.ui.detail.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.refactoringlifeacademy.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
