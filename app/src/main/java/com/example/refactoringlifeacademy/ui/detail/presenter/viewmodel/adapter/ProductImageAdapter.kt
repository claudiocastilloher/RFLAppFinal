package com.example.refactoringlifeacademy.ui.detail.presenter.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.databinding.ItemImageBinding
import com.squareup.picasso.Picasso

class ProductImageAdapter(private val images: List<Image>, private val favorite: Boolean?) :
    RecyclerView.Adapter<ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        //val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //return ImageViewHolder(binding)
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.render(images[position], favorite)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}

//class ImageViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(binding.root) {
class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val binding = ItemImageBinding.bind(view)
    fun render(value: Image, favorite: Boolean?) {
        Picasso.get().load(value.link).into(binding.ivImageProduct)
        when (favorite) {
            true -> binding.ivBlueHeart.setImageResource(R.drawable.heart_blue_fill)
            else -> binding.ivBlueHeart.setImageResource(R.drawable.heart_blue)
        }
    }
}
