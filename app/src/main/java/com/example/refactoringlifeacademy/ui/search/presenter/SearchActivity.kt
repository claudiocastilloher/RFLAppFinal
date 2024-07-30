package com.example.refactoringlifeacademy.ui.search.presenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.ActivitySearchBinding
import com.example.refactoringlifeacademy.ui.detail.presenter.DetailActivity
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import com.example.refactoringlifeacademy.ui.search.viewmodel.SearchViewModel
import com.example.refactoringlifeacademy.ui.search.viewmodel.adapter.AdapterSearch
import com.example.refactoringlifeacademy.utils.EmailUtils

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel: SearchViewModel by viewModels()
    private lateinit var adapterSearch: AdapterSearch
    private var products: List<Product> = emptyList()
    private var favoriteProducts: List<Product> = emptyList()
    private var showingFavorites: Boolean = false

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
        initRecycler()
        initListener()
    }

    private fun initListener() {
        binding.ivHeart.setOnClickListener {
            toggleFavoriteFilter()
        }
    }

    private fun toggleFavoriteFilter() {
        showingFavorites = !showingFavorites
        updateFavoriteIcon()
        updateRecyclerView()
    }

    private fun updateRecyclerView() {
        val filteredProducts = if (showingFavorites) {
            favoriteProducts
        } else {
            products
        }

        if (filteredProducts.isEmpty()) {
            binding.lyNotFound.visibility = View.VISIBLE
        } else {
            binding.lyNotFound.visibility = View.GONE
        }

        adapterSearch.updateData(filteredProducts)
    }

    private fun updateFavoriteIcon() {
        if (showingFavorites) {
            binding.ivHeart.setImageResource(R.drawable.heart_black_fill)
        } else {
            binding.ivHeart.setImageResource(R.drawable.heart)
        }
    }

    private fun initSearch() {
        binding.etSearch.addTextChangedListener {
            val query = it.toString()
            if (showingFavorites) {
                searchLocalFavorites(query)
            } else {
                viewModel.loadAllProducts(productName = query)
            }
        }
    }

    private fun searchLocalFavorites(query: String) {
        val filteredProducts = favoriteProducts.filter {
            it.name?.contains(query, ignoreCase = true) ?: false
        }

        if (filteredProducts.isEmpty()) {
            binding.lyNotFound.visibility = View.VISIBLE
        } else {
            binding.lyNotFound.visibility = View.GONE
        }

        adapterSearch.updateData(filteredProducts)
    }

    private fun observers() {
        viewModel.allProducts.observe(this) { allProducts ->
            products = allProducts
            favoriteProducts = allProducts.filter { it.isFavorite == true }
            updateRecyclerView()
        }

        viewModel.searchState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarr.visibility = View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressBarr.visibility = View.GONE
                }
                is ProductState.Error -> {
                    binding.progressBarr.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        viewModel.favoriteState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                }
                is ProductState.Success -> {
                }
                is ProductState.Error -> {
                    showMessageError(state.message)
                }
            }
        }
    }

    private fun initGmail() {
        binding.tvSupport.setOnClickListener {
            EmailUtils.sendEmail(this)
        }
    }

    private fun initRecycler() {
        adapterSearch = AdapterSearch(
            value = emptyList(),
            onProductSelected = { product ->
                goToDetails(product)
            },
            onFavoriteSelected = { product ->
                putFavoriteProduct(product)
            }
        )
        binding.rvSearch.adapter = adapterSearch
        binding.rvSearch.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun putFavoriteProduct(product: Product) {
        val updatedProduct = product.copy(isFavorite = !product.isFavorite!!)
        val position = adapterSearch.value.indexOf(product)
        if (position != -1) {
            adapterSearch.updateItem(updatedProduct, position)
        }
        product.idProduct?.let {
            viewModel.markProductAsFavorite(it)
            updateFavoriteList(updatedProduct)
        }
    }

    private fun updateFavoriteList(updatedProduct: Product) {
        favoriteProducts = if (updatedProduct.isFavorite == true) {
            favoriteProducts + updatedProduct
        } else {
            favoriteProducts.filter { it.idProduct != updatedProduct.idProduct }
        }
        if (showingFavorites) {
            updateRecyclerView()
        }
    }

    private fun goToDetails(product: Product) {
        UserProduct.userProductId = product.idProduct
        UserProduct.isFavorite = product.isFavorite
        val productPrice = product.price
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("productPrice", productPrice)
        startActivity(intent)
        finish()
    }

    private fun showMessageError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
