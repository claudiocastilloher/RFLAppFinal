package com.example.refactoringlifeacademy.ui.financeFragment.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.data.dto.model.FinanceMethods
import com.example.refactoringlifeacademy.databinding.FragmentFinanceBinding
import com.example.refactoringlifeacademy.ui.financeFragment.viewmodel.FinanceViewModel
import com.example.refactoringlifeacademy.ui.financeFragment.viewmodel.adapter.AdapterFinance
import com.example.refactoringlifeacademy.utils.FinanceState

class FinanceFragment : Fragment() {

    private val viewModel: FinanceViewModel by viewModels()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()
        calls()
    }

    @SuppressLint("SetTextI18n")
    private fun calls() {
        viewModel.getFinanceMethods()
        binding.tvPrice.text = "$ $productPrice"
    }

    private fun observer() {
        viewModel.financeState.observe(viewLifecycleOwner, Observer { state ->
            when(state){
                is FinanceState.Loading -> {
                    binding.progressBarr.visibility = View.VISIBLE
                }

                is FinanceState.Success -> {
                    binding.progressBarr.visibility = View.GONE
                    state.info.financeMethods?.let { financeMethods ->
                        initRecyclerViewFinance(financeMethods)
                    } ?: showMessageError("Finece Methods list is null")
                }

                is FinanceState.Error -> {
                    binding.progressBarr.visibility = View.GONE
                    showMessageError(state.message)
                }
            }
        })
    }

    private fun initRecyclerViewFinance(value: List<FinanceMethods>) {
        val adapter = AdapterFinance(value)
        binding.rvFinance.adapter = adapter
        binding.rvFinance.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun showMessageError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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