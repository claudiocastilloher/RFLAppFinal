package com.example.refactoringlifeacademy.ui.detail.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.databinding.FragmentImageBinding
import com.example.refactoringlifeacademy.ui.detail.presenter.viewmodel.ImageViewmodel
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState


class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding
    private lateinit var viewmodel: ImageViewmodel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentImageBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idProduct = arguments?.getInt("idProduct") ?: 0
        viewmodel.getProductById(idProduct)

        viewmodel.productState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProductState.Loading -> {
//                    binding.progressBar.visibility = View.VISIBLE
//                    binding.tvError.visibility = View.GONE
                }
                is ProductState.Success -> {
//                    binding.progressBar.visibility = View.GONE
//                    updateUI(state.data.images)
                }
                is ProductState.Error -> {
//                    binding.progressBar.visibility = View.GONE
//                    binding.tvError.visibility = View.VISIBLE
//                    binding.tvError.text = state.message
                }
            }
        }
    }

    private fun updateUI(images: List<Image>) {
//        val adapter = ProductImagesAdapter(images)
//        binding.rvProductImages.layoutManager = LinearLayoutManager(context)
//        binding.rvProductImages.adapter = adapter
    }


}
