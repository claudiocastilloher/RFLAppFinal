package com.example.refactoringlifeacademy.ui.commentFragment.presenter

import android.annotation.SuppressLint
import  android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.data.dto.model.Comment
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.FragmentCommentBinding
import com.example.refactoringlifeacademy.ui.commentFragment.viewmodel.CommentViewModel
import com.example.refactoringlifeacademy.ui.descriptionFragmen.viewmodel.adapter.AdapterComment
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState

class CommentFragment : Fragment() {

    private val viewModel: CommentViewModel by viewModels()
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private var productPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            productPrice = it.getDouble(ARG_PRODUCT_PRICE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        calls()
    }

    @SuppressLint("SetTextI18n")
    private fun calls() {
        UserProduct.userProductId?.let { viewModel.getComments(it) }
        binding.tvPrice.text = "$ $productPrice"
    }

    private fun observer() {
        viewModel.comments.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is ProductState.Loading -> {
                    binding.progressBarr.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.visibility = View.GONE
                    state.data?.comments?.let { comments ->
                        initRecyclerViewComments(comments)
                    } ?: showMessageError("Comment list is null")
                }

                is ProductState.Error -> {
                    binding.progressBarr.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        })
    }

    private fun initRecyclerViewComments(value: List<Comment>) {
        val adapter = AdapterComment(value)
        binding.rvComments.adapter = adapter
        binding.rvComments.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun showMessageError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ARG_PRODUCT_PRICE = "productPrice"

        fun newInstance(productPrice: Double) =
            CommentFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PRODUCT_PRICE, productPrice)
                }
            }
    }
}
