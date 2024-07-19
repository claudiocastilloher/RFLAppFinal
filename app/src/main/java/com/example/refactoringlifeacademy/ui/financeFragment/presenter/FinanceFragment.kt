package com.example.refactoringlifeacademy.ui.financeFragment.presenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.refactoringlifeacademy.databinding.FragmentFinanceBinding

class FinanceFragment : Fragment() {

    private var _binding: FragmentFinanceBinding? = null
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
        _binding = FragmentFinanceBinding.inflate(inflater, container, false)
        return binding.root
    }
    companion object {
        private const val ARG_PRODUCT_PRICE = "productPrice"
        fun newInstance(productPrice: Double) =
            FinanceFragment().apply {
                arguments = Bundle().apply {
                    putDouble(ARG_PRODUCT_PRICE, productPrice)
                }
            }
    }
}