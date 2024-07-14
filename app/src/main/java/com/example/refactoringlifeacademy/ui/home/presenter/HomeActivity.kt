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
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
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
        putFavoriteProduct()
    }

    private fun initSearch() {
        binding.svSearch.isIconifiedByDefault = false


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
                    binding.progressTv.rlProgressBar.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressTv.rlProgressBar.visibility = View.GONE
                    when (val product = state.data?.product) {
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
                    when (val dailyOffer = state.data) {
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

    private fun putFavoriteProduct() {
        binding.ivHeartBlue.setOnClickListener {
            println("FAVORITO")
            val idProduct = UserProduct.userProductId
            if (idProduct != null) {
                println("ID PRODUCTO")
                println(idProduct.toString())
                homeViewModel.markProductAsFavorite(idProduct)
            }
        }
    }

    private fun onCategorySelected(category: ProductTypeAlt) {
        homeViewModel.getProducts(idProductType = category.idProductType)
    }
}