package com.example.refactoringlifeacademy.ui.descriptionFragmen.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.FragmentDescriptionBinding
import com.example.refactoringlifeacademy.ui.descriptionFragmen.viewmodel.DescriptionViewModel
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState

class DescriptionFragment : Fragment() {

    private val viewModel: DescriptionViewModel by viewModels()
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDescriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        calls()
    }

    private fun calls() {
        UserProduct.userProductId?.let { viewModel.getProductByID(it) }
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.productByIdState.observe(viewLifecycleOwner) { state ->
            when(state){
                is ProductState.Loading -> {
                    binding.progressBarr.visibility = View.VISIBLE
                }

                is ProductState.Success -> {
                    binding.progressBarr.visibility = View.GONE
                    binding.productName.text = state.data?.name
                    binding.producDescription.text = state.data?.description
                    binding.producLargeDescription.text = state.data?.largeDescription
                    binding.tvPrice.text = "$ ${state.data?.price}"
                    binding.divider.visibility = View.VISIBLE
                    binding.divierDown.visibility = View.VISIBLE
                }

                is ProductState.Error -> {
                    binding.progressBarr.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        }
    }

    private fun showMessageError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
