package com.example.refactoringlifeacademy.ui.home.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class AdapterProduct(private val productList: List<Product>, private val onProductSelected: (Product) -> Unit) : RecyclerView.Adapter<ProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        holder.render(productList[position], onProductSelected)
    }
}

class ProductHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemProductBinding.bind(view)

    fun render(value: Product, onProductSelected: (Product) -> Unit){
        val image = value.image
        val name = value.name
        val currency = value.currency ?: "$"
        val price = "$currency ${value.price}"
        if (image.isNullOrEmpty()) {
            binding.ivProduct.setImageResource(R.drawable.no_photo)
        } else {
            Picasso.get().load(image).into(binding.ivProduct)
        }

        binding.producName.text = name
        binding.producPrice.text = price
        itemView.setOnClickListener{
            onProductSelected(value)
        }
    }
}
