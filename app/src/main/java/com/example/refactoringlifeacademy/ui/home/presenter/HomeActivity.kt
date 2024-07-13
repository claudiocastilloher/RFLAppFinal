package com.example.refactoringlifeacademy.ui.home.presenter

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.ProductTypeAlt
import com.example.refactoringlifeacademy.data.dto.response.DailyOfferResponse
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
        initSearch()
    }

    private fun initSearch() {
        binding.svSearch.isIconifiedByDefault = false

    }

    private fun calls() {
        homeViewModel.getProducts()
        homeViewModel.getProductTypes()
        homeViewModel.getDailyOffer()
        homeViewModel.getLastUserProduct()
    }
    private fun observer() {
        homeViewModel.productsState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarr.rlProgressBar.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
                    state.data?.products?.let { products ->
                        initRecyclerViewProduct(products)
                    } ?: run {
                        Toast.makeText(this, "Product list is null", Toast.LENGTH_SHORT).show()
                    }
                }

                is ProductState.Error -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        homeViewModel.lastUserProductState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarr.rlProgressBar.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
                    val product = state.data?.product
                    if (product != null) {
                        updateLastUserProductUI(product)
                        showMessageSuccess("Last viewed product loaded successfully")
                    } else {
                        showMessageError("Last viewed product is null")
                    }
                }

                is ProductState.Error -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        homeViewModel.productTypesState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarr.rlProgressBar.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
                    state.data?.productTypes?.let { productTypes ->
                        initRecyclerViewCategory(productTypes)
                    } ?: run {
                        Toast.makeText(this, "Product types list is null", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                is ProductState.Error -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
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
                    val dailyOffer = state.data
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

        homeViewModel.favoriteState.observe(this) { state ->
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

    private fun updateDailyOffer(dailyOffer: DailyOfferResponse?) {
        dailyOffer?.let {
            if (!it.images.isNullOrEmpty()) {
                Picasso.get()
                    .load(it.images[0].link)
                    .into(binding.ivOfferDaily)
            } else {
                binding.ivOfferDaily.setImageResource(R.drawable.trademark)
            }
            binding.tvProductName.text = it.name ?: ""
            binding.tvDescription.text = it.description ?: ""
            binding.tvPrice.text = (dailyOffer.price ?: 0).toString()
        }
    }

    private fun updateLastUserProductUI(product: Product) {
        Picasso.get().load(product.image).into(binding.ivOfferDaily)
        binding.tvProductName.text = product.name ?: ""
        binding.tvDescription.text = product.description ?: ""
        binding.tvPrice.text = (product.price ?: 0).toString()
    }

    private fun initRecyclerViewCategory(value: List<ProductTypeAlt>) {
        val adapter = AdapterCategory(value) {
            onCategorySelected(it)
        }
        binding.rvCategory.adapter = adapter
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun initRecyclerViewProduct(value: List<Product>) {
        val adapter = AdapterProduct(value)
        binding.rvProduct.adapter = adapter
        binding.rvProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun onCategorySelected(category: ProductTypeAlt) {
        homeViewModel.getProducts(idProductType = category.idProductType)
    }
}
