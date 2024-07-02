package com.example.refactoringlifeacademy.ui.home.presenter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.databinding.ActivityHomeBinding
import com.example.refactoringlifeacademy.databinding.ActivityRegisterBinding
import com.example.refactoringlifeacademy.ui.home.viewmodel.adapter.AdapterCategory
import com.example.refactoringlifeacademy.ui.home.viewmodel.adapter.AdapterProduct

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun initRecyclerViewCategory(value: List<String>) {
        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterCategory(value)
        binding.rvCategory.adapter = adapter
    }

    private fun initRecyclerViewProduct(value: List<String>) {
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterProduct(value)
        binding.rvProduct.adapter = adapter
    }
}
