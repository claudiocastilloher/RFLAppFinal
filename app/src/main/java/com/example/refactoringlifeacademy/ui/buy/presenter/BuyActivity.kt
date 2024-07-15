package com.example.refactoringlifeacademy.ui.buy.presenter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.refactoringlifeacademy.databinding.ActivityBuyBinding

class BuyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}