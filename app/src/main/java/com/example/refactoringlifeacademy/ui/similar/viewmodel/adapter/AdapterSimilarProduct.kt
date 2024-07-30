package com.example.refactoringlifeacademy.ui.similar.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.ItemSimilarBinding
import com.squareup.picasso.Picasso

class AdapterSimilarProduct(private val similarProductList: List<Product>, private val onSimilarProductSelected: (Product) -> Unit, private val onSimilarProductMarkFavorite: (Product) -> Unit) : RecyclerView.Adapter<SimilarProductHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_similar,parent,false)
        return SimilarProductHolder(view)
    }

    override fun getItemCount(): Int {
        return similarProductList.size
    }

    override fun onBindViewHolder(holder: SimilarProductHolder, position: Int) {
        holder.render(similarProductList[position], onSimilarProductSelected, onSimilarProductMarkFavorite)
    }

    fun updateItem(position: Int, newFavorite: Boolean) {
        val newProduct = similarProductList[position].copy(isFavorite = newFavorite)
        (similarProductList as MutableList)[position] = newProduct
        }
}

class SimilarProductHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSimilarBinding.bind(view)

    fun render(value: Product, onSimilarProductSelected: (Product) -> Unit, onSimilarProductMarkFavorite: (Product) -> Unit){
        val image = value.image
        val name = value.name
        val description = value.description
        val favorite = value.isFavorite
        val price = "$ ${value.price}"
        Picasso.get().load(image).into(binding.ivSimilar)
        binding.tvName.text = name
        binding.tvDesc.text = description
        binding.tvPrice.text = price
        when (favorite) {
            true -> binding.ivHeartBlue.setImageResource(R.drawable.heart_blue_fill)
            else -> binding.ivHeartBlue.setImageResource(R.drawable.heart_blue)
        }
        binding.btnSee.setOnClickListener{
            onSimilarProductSelected(value)
        }
        binding.ivHeartBlue.setOnClickListener{
            UserProduct.positionAdapter = adapterPosition
            onSimilarProductMarkFavorite(value)
        }
    }
}
