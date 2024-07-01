package com.example.refactoringlifeacademy.ui.home.presenter

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.databinding.ActivityHomeBinding
import com.example.refactoringlifeacademy.ui.home.viewmodel.HomeViewModel
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        observer()
    }

    private fun observer() {
        homeViewModel.productsState.observe(this, Observer { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.products
                    // agregar al adapter
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        homeViewModel.lastUserProductState.observe(this, Observer { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.product
                    // agregar al adapter
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        homeViewModel.productTypesState.observe(this, Observer { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.productTypes
                    // agregar al adapter
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

        homeViewModel.dailyOfferState.observe(this, Observer { state ->
            when (state) {
                is ProductState.Loading -> {
                    // Muestra una barra de progreso
                }
                is ProductState.Success -> {
                    val products = state.data.product
                    // agregar al adapter
                }
                is ProductState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
