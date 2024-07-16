package com.example.refactoringlifeacademy.ui.detail.presenter.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Image
import com.squareup.picasso.Picasso

//class ProductImageAdapter(private val images: List<Image>) : RecyclerView.Adapter<ViewHolder>(){
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun getItemCount(): Int {
//        val image = images[position]
//        Picasso.get().load(image.link).into(holder.imageView)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        override fun getItemCount(): Int = images.size
//    }
//
//}
//
//
//
//
//class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//    val imageView: ImageView = itemView.findViewById(R.id.ivProductImage)
//}