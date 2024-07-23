package com.example.refactoringlifeacademy.ui.descriptionFragmen.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Comment
import com.example.refactoringlifeacademy.databinding.ItemCommentBinding

class AdapterComment(private val commentList: List<Comment>): RecyclerView.Adapter<CommentHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment,parent,false)
        return CommentHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.render(commentList[position])
    }
}

class CommentHolder(view: View): RecyclerView.ViewHolder(view){
    private val binding = ItemCommentBinding.bind(view)
    fun render(value: Comment){
        binding.tvUserName.text = value.commentBy
        binding.tvComment.text = value.comment

    }
}
