package com.example.refactoringlifeacademy.ui.detail.presenter

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.databinding.ActivityDetailBinding
import com.example.refactoringlifeacademy.ui.home.presenter.HomeActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvImag.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ImageFragment())
                .commit()
        }

        binding.tvDescript.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, DescriptionFragment())
                .commit()
        }

        binding.tvFinanc.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FinanceFragment())
                .commit()
        }

        binding.tvComent.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CommentFragment())
                .commit()
        }

        binding.arrowLeft1.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
