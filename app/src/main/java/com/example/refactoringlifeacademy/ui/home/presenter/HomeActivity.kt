package com.example.refactoringlifeacademy.ui.home.presenter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.ProductType
import com.example.refactoringlifeacademy.databinding.ActivityHomeBinding
import com.example.refactoringlifeacademy.ui.home.viewmodel.HomeViewModel
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import com.example.refactoringlifeacademy.ui.home.viewmodel.adapter.AdapterCategory
import com.example.refactoringlifeacademy.ui.home.viewmodel.adapter.AdapterProduct
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calls()
        observer()
    }

    private fun calls(){
        homeViewModel.getProducts()
        homeViewModel.getProductTypes()
        homeViewModel.getDailyOffer()
        homeViewModel.getLastUserProduct()
    }
    private fun observer() {
        homeViewModel.productsState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBar.rlProgressBar.visibility= View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressBar.rlProgressBar.visibility= View.GONE
                    state.data.products?.let { products ->
                        initRecyclerViewProduct(products)
                    } ?: run {
                        Toast.makeText(this, "Product list is null", Toast.LENGTH_SHORT).show()
                    }
                }
                is ProductState.Error -> {
                    binding.progressBar.rlProgressBar.visibility= View.GONE
                    showMessageError(state.message)
                }
            }
        }

        homeViewModel.lastUserProductState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBar.rlProgressBar.visibility= View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    val product = state.data.product
                    if (product != null) {
                        updateLastUserProductUI(product)
                        showMessageSuccess("Last viewed product loaded successfully")
                    } else {
                        showMessageError("Last viewed product is null")
                    }
                }
                is ProductState.Error -> {
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        homeViewModel.productTypesState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBar.rlProgressBar.visibility = View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    state.data.productTypes?.let { productTypes ->
                       initRecyclerViewCategory(productTypes)
                    } ?: run {
                        Toast.makeText(this, "Product types list is null", Toast.LENGTH_SHORT).show()
                    }
                }
                is ProductState.Error -> {
                    binding.progressBar.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        homeViewModel.dailyOfferState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressTv.rlProgressBar.visibility = View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
                    val dailyOffer = state.data.product
                    if (dailyOffer != null) {
                        updateDailyOffer(dailyOffer)
                    }
                }
                is ProductState.Error -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        homeViewModel.favoriteState.observe(this){ state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressTv.rlProgressBar.visibility = View.VISIBLE
                }
                is ProductState.Success -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
                    showMessageSuccess("Daily offer loaded successfully")
                }
                is ProductState.Error -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }
    }

    private fun showMessageSuccess(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    private fun showMessageError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateDailyOffer(dailyOffer: Product) {
        Picasso.get()
            .load(dailyOffer.image)
            .into(binding.ivOfferDaily)
        binding.tvProductName.text = dailyOffer.name ?: ""
        binding.tvDescription.text = dailyOffer.description ?: ""
        binding.tvPrice.text = dailyOffer.price ?: ""
    }

    private fun updateLastUserProductUI(product: Product) {
        Picasso.get().load(product.image).into(binding.ivOfferDaily)
        binding.tvProductName.text = product.name ?: ""
        binding.tvDescription.text  = product.description ?: ""
        binding.tvPrice.text = product.price ?: ""
    }
    private fun initRecyclerViewCategory(value: List<ProductType>) {
        binding.rvCategory.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterCategory(value)
        binding.rvCategory.adapter = adapter

    }

    private fun initRecyclerViewProduct(value: List<Product>) {
        binding.rvProduct.layoutManager = LinearLayoutManager(this)
        val adapter = AdapterProduct(value)
        binding.rvProduct.adapter = adapter

        }
    }
