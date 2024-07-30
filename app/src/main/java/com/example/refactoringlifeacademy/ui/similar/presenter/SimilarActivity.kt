package com.example.refactoringlifeacademy.ui.similar.presenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.ActivitySimilarBinding
import com.example.refactoringlifeacademy.ui.detail.presenter.DetailActivity
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import com.example.refactoringlifeacademy.ui.similar.viewmodel.SimilarViewModel
import com.example.refactoringlifeacademy.ui.similar.viewmodel.adapter.AdapterSimilarProduct
import com.example.refactoringlifeacademy.utils.EmailUtils


class SimilarActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySimilarBinding
    private val similarViewModel: SimilarViewModel by viewModels()
    private lateinit var adapterSimilar: AdapterSimilarProduct
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimilarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        calls()
        observer()
        onClick()
    }

    private fun calls() {
        UserProduct.userProductId?.let {
            similarViewModel.getSimilarProducts(it)
        }
    }

    private fun observer() {
        similarViewModel.productsSimilarState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarr.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.visibility = View.GONE
                    // como no se pudo definir lo del vendedor decidi poner un elemento comun a todos los productos y es la cantidad
                    binding.seller.text = getString(R.string.total)
                    state.data?.let {
                        binding.sellerWho.text = it.totalProducts.toString()
                    }
                    state.data?.products?.let { products ->
                        initRecyclerViewSimilarProduct(products)
                        showMessageSuccess()
                    } ?: showMessageError("Similar Products list is null")
                }

                is ProductState.Error -> {
                    binding.progressBarr.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }

        similarViewModel.favoriteState.observe(this) { state ->
            when (state) {
                is ProductState.Loading -> {
                }

                is ProductState.Success -> {
                    state.data?.isFavorite?.let { loadHeartFavorite(it) }
                }

                is ProductState.Error -> {
                    showMessageError(state.message)
                }
            }
        }

    }

    private fun showMessageSuccess() {
        Toast.makeText(this, "Similar Products loaded successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showMessageError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun initRecyclerViewSimilarProduct(value: List<Product>) {
        adapterSimilar = AdapterSimilarProduct(value,
            onSimilarProductSelected = { onSimilarProductSee(it) },
            onSimilarProductMarkFavorite = { putProductMarkFavorite(it) }
        )
        binding.rvSimilares.adapter = adapterSimilar
        binding.rvSimilares.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun onClick() {
        binding.vector.setOnClickListener {
            goToDetails(UserProduct.price)
        }

        binding.tvSupport.setOnClickListener {
            EmailUtils.sendEmail(this)
        }
    }

    private fun onSimilarProductSee(product: Product) {
        UserProduct.userProductId = product.idProduct //Actualizar Id Producto
        UserProduct.isFavorite = product.isFavorite  //Actualizar Favorito DProducto
        product.price?.let { goToDetails(it) }
    }

    private fun putProductMarkFavorite(product: Product) {
        product.idProduct?.let { similarViewModel.markProductAsFavorite(it) }
    }

    private fun goToDetails(productPrice: Double) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("productPrice", productPrice)
        startActivity(intent)
    }

    private fun loadHeartFavorite(favorite: Boolean) {
        UserProduct.positionAdapter?.let { adapterSimilar.updateItem(it, favorite)
            adapterSimilar.notifyItemChanged(it)
        }
    }
}
