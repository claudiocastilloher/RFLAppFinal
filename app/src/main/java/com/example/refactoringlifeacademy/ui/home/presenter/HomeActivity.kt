package com.example.refactoringlifeacademy.ui.home.presenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.ProductType
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.ActivityHomeBinding
import com.example.refactoringlifeacademy.ui.detail.presenter.DetailActivity
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
        putFavoriteProduct()
    }

    private fun calls() {
        homeViewModel.getProducts()
        homeViewModel.getProductTypes()
        when (UserProduct.isLogging) { //condicion de carga del item principal
            false -> {
                homeViewModel.getDailyOffer()
                UserProduct.isLogging = true
            }

            true -> {
                homeViewModel.getLastUserProduct()
            }
        }
    }

    private fun observer() {
        homeViewModel.productsState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarr.rlProgressBar.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.rlProgressBar.visibility = View.GONE
                    state.data.products?.let { products ->
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
                    binding.progressTv.rlProgressBar.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
                    when (val product = state.data.product) {
                        null -> {
                            showMessageError("Last viewed product is null")
                        }

                        else -> {
                            updateLastUserProductUI(product)
                            UserProduct.userProductId = product.idProduct
                            UserProduct.isfavorite = product.isFavorite
                            showMessageSuccess("Last viewed product loaded successfully")
                        }
                    }
                }

                is ProductState.Error -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
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
                    state.data.productTypes?.let { productTypes ->
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
                    when (val dailyOffer = state.data.product) {
                        null -> {
                            showMessageError("Daily offer product is null")
                        }

                        else -> {
                            updateDailyOffer(dailyOffer)
                            UserProduct.userProductId = dailyOffer.idProduct
                            UserProduct.isfavorite = dailyOffer.isFavorite
                            showMessageSuccess("Daily offer product loaded successfully")
                        }
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
                    if (UserProduct.isfavorite == true || UserProduct.isfavorite == null) {
                        binding.ivHeartBlue.setImageResource(R.drawable.heart_blue)
                        UserProduct.isfavorite = false
                        showMessageSuccess("Mark not favorite product successfully")
                    } else {
                        binding.ivHeartBlue.setImageResource(R.drawable.heart_blue_fill)
                        UserProduct.isfavorite = true
                        showMessageSuccess("Mark favorite product successfully")
                    }

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
        binding.tvDescription.text = product.description ?: ""
        binding.tvPrice.text = product.price ?: ""
    }

    private fun initRecyclerViewCategory(value: List<ProductType>) {
        binding.rvCategory.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = AdapterCategory(value)
        binding.rvCategory.adapter = adapter

    }

    private fun initRecyclerViewProduct(value: List<Product>) {
        binding.rvProduct.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = AdapterProduct(value)
        binding.rvProduct.adapter = adapter

    }

    private fun putFavoriteProduct() {
        binding.ivHeartBlue.setOnClickListener {
            val idProduct = UserProduct.userProductId
            if (idProduct != null) {
                homeViewModel.markProductAsFavorite(idProduct)
            }
        }
    }
}
