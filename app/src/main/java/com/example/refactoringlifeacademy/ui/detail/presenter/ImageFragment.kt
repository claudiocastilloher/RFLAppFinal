package com.example.refactoringlifeacademy.ui.detail.presenter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.FragmentImageBinding
import com.example.refactoringlifeacademy.ui.detail.presenter.viewmodel.ImageViewmodel
import com.example.refactoringlifeacademy.ui.detail.presenter.viewmodel.adapter.ProductImageAdapter
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState


class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding // Debes cambiar FragmentImageBinding por tu variable correspondiente
    private val viewModel: ImageViewmodel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        calls()


        onClick()


        viewModel.getProductById(1)
    }

    private fun observer() {
        viewModel.data.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is ProductState.Loading -> {
                    showLoading()
                }

                is ProductState.Success -> {
                   hideLoading()
                    binding.tvProductName.text = state.data?.name
                    binding.tvPrice.text = "$ ${state.data?.price}"
                    state.data?.images?.let { images ->
                        updateUI(images)
                    }

                }

                is ProductState.Error -> {
                    hideLoading()
                    showMessageError(state.message)
                }
            }
        })
    }



    private fun showLoading() {
        binding.progressBarrImage.rlProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBarrImage.rlProgressBar.visibility = View.GONE
    }

    private fun showMessageError(message: String) {

        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(images: List<Image>) {
        if(images.isNullOrEmpty()){
            binding.icMsgError.cslImgError.visibility = View.VISIBLE
        }else{
            binding.icMsgError.cslImgError.visibility = View.GONE
            val adapter = ProductImageAdapter(images)
            binding.rvItemImage.adapter = adapter
        }



    }
    private fun calls() {
        UserProduct.userProductId?.let { productId ->
            viewModel.getProductById(productId)
        }
    }

    fun onClick(){
        binding.btProduct.setOnClickListener {

            binding.btProduct.setBackgroundResource(R.drawable.button_image2)
            binding.btColors.setBackgroundResource(R.drawable.button_image1)
            binding.btSimilar.setBackgroundResource(R.drawable.button_image1)
            // Aquí puedes realizar otras acciones según sea necesario
        }


        binding.btColors.setOnClickListener {

            binding.btColors.setBackgroundResource(R.drawable.button_image2)
            binding.btProduct.setBackgroundResource(R.drawable.button_image1)
            binding.btSimilar.setBackgroundResource(R.drawable.button_image1)

        }


        binding.btSimilar.setOnClickListener {
            binding.btSimilar.setBackgroundResource(R.drawable.button_image2)
            binding.btColors.setBackgroundResource(R.drawable.button_image1)
            binding.btProduct.setBackgroundResource(R.drawable.button_image1)

        }
    }
}