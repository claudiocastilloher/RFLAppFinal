package com.example.refactoringlifeacademy.ui.detail.presenter

import android.content.Intent
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
import com.example.refactoringlifeacademy.ui.home.presenter.HomeActivity
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState


class ImageFragment : Fragment() {


    private val viewModel: ImageViewmodel by viewModels()
    private var _binding: FragmentImageBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageBinding.inflate(inflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observer()
        calls()
        initUI()
    }


    private fun observer(){
        viewModel.data.observe(viewLifecycleOwner, Observer { state ->
            when (state) {
                is ProductState.Loading -> {
                    binding.progressBarrImage.rlProgressBar.visibility = View.VISIBLE

                }
                is ProductState.Success -> {
                    binding.progressBarrImage.rlProgressBar.visibility = View.GONE
                    binding.tvProductName.text = state.data?.name
                    binding.tvPrice.text = "$ ${state.data?.price}"

                }
                is ProductState.Error -> {
                    binding.progressBarrImage.rlProgressBar.visibility = View.GONE
                    showMessageError(state.message)
                }
            }

        })
    }

    private fun calls() {
        UserProduct.userProductId?.let { viewModel.getProductById(it)}
    }
    private fun initUI() {
        binding.btnBuy.setOnClickListener {
            goToSale()
        }
    }

    private fun goToSale(){
        val intent = Intent(context, HomeActivity::class.java)//CAMBIAR UNA VEZ QUE SE TENGA LA CLASE
        startActivity(intent)
    }

    private fun showMessageError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}
