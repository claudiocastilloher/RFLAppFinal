package com.example.refactoringlifeacademy.ui.detail.presenter.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.databinding.ItemImageBinding
import com.squareup.picasso.Picasso

class ProductImageAdapter(private var images: List<Image>) : RecyclerView.Adapter<ProductImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        holder.render(image)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    fun updateImages(newImages: List<Image>) {
        images = newImages
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun render(image: Image) {
            Picasso.get().load(image.link).into(binding.ivImageProduct)
        }
    }
}