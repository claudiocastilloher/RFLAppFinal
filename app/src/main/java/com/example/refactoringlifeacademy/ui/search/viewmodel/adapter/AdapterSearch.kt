package com.example.refactoringlifeacademy.ui.search.viewmodel.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Product
import com.example.refactoringlifeacademy.databinding.ItemSearchBinding
import com.squareup.picasso.Picasso

class AdapterSearch(private val value: List<Product>, private val onProductSelected: (Product) -> Unit) : RecyclerView.Adapter<SerachHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SerachHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false)
        return SerachHolder(view)
    }

    override fun getItemCount(): Int {
        return value.size
    }

    override fun onBindViewHolder(holder: SerachHolder, position: Int) {
        holder.render(value[position], onProductSelected)
    }
}

class SerachHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemSearchBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun render(value: Product, onProductSelected: (Product) -> Unit) {
        binding.tvProductName.text = value.name
        binding.tvDescription.text = value.description
        binding.tvPrice.text = "${value.currency} ${value.price}"
        Picasso.get().load(value.image).into(binding.ivProduct)
        binding.btnSee.setOnClickListener {
            onProductSelected(value)
        }
    }
}