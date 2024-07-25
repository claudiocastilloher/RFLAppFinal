package com.example.refactoringlifeacademy.ui.search.presenter

import android.content.Intent
import android.os.Bundle
import java.text.Normalizer
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.databinding.ActivitySearchBinding
import com.example.refactoringlifeacademy.ui.detail.presenter.DetailActivity
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import com.example.refactoringlifeacademy.ui.search.viewmodel.SearchVielModel
import com.example.refactoringlifeacademy.ui.search.viewmodel.adapter.AdapterSearch
import com.example.refactoringlifeacademy.utils.EmailUtils

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchVielModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initGmail()
        observers()
        initSearch()
    }

    private fun initSearch() {
        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            viewModel.searchProducts(productName = query)
        }

    }


    private fun observers() {
        viewModel.searchState.observe(this, Observer { state ->
            when(state){
                is ProductState.Loading -> {
                    binding.progressBarr.visibility = View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressBarr.visibility = View.GONE
                    state.data?.products?.let { initRecycler(it) }
                }
                is ProductState.Error -> {
                    binding.progressBarr.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        })
    }

    private fun initGmail() {
        binding.tvSupport.setOnClickListener {
            EmailUtils.sendEmail(this)
        }
    }

    private fun initRecycler(value: List<Product>) {
        binding.rvSearch.adapter = AdapterSearch(value){
            it.price?.let { precio -> goToDetails(precio) }
        }
        binding.rvSearch.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun goToDetails(productPrice: Double) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("productPrice", productPrice)
        startActivity(intent)
        finish()
    }

    private fun showMessageError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}