package com.example.refactoringlifeacademy.ui.home.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.ProductType
import com.example.refactoringlifeacademy.databinding.ActivityHomeBinding
import com.example.refactoringlifeacademy.ui.home.viewmodel.HomeViewModel
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import com.example.refactoringlifeacademy.ui.home.viewmodel.adapter.AdapterCategory
import com.example.refactoringlifeacademy.ui.home.viewmodel.adapter.AdapterProduct

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        observer()
    }

    private fun observer() {
        homeViewModel.productsState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.products
                    // actualizar el recycler de productos
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        homeViewModel.lastUserProductState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.product
                    // actualizar informacion del ultimo producto visitado
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        homeViewModel.productTypesState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.productTypes
                    // recycler categoria de productos
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        homeViewModel.dailyOfferState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.product
                    // actualizar la oferta del dia
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
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
