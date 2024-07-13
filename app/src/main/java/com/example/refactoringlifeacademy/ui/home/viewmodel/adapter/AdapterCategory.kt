package com.example.refactoringlifeacademy.ui.home.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.ProductTypeAlt
import com.example.refactoringlifeacademy.databinding.ItemCategoryBinding

class AdapterCategory(private val categoryList: List<ProductTypeAlt>, private val onCategorySelected: (ProductTypeAlt) -> Unit) : RecyclerView.Adapter<CategoryHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false)
        return CategoryHolder(view)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: CategoryHolder, position: Int) {
        holder.render(categoryList[position], onCategorySelected)
    }
}

class CategoryHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemCategoryBinding.bind(view)

    fun render(value: ProductTypeAlt, onCategorySelected: (ProductTypeAlt) -> Unit){
        binding.tvCategory.text = value.description
        itemView.setOnClickListener {
            onCategorySelected(value)
        }
    }
}
