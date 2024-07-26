package com.example.refactoringlifeacademy.ui.imageFragment.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.Image
import com.example.refactoringlifeacademy.data.dto.model.UserProduct
import com.example.refactoringlifeacademy.databinding.FragmentImageBinding
import com.example.refactoringlifeacademy.ui.imageFragment.viewmodel.ImageViewmodel
import com.example.refactoringlifeacademy.ui.imageFragment.viewmodel.adapter.ProductImageAdapter
import com.example.refactoringlifeacademy.ui.home.viewmodel.ProductState
import com.example.refactoringlifeacademy.ui.similar.presenter.SimilarActivity


class ImageFragment : Fragment() {

    private lateinit var binding: FragmentImageBinding // Debes cambiar FragmentImageBinding por tu variable correspondiente
    private val viewModel: ImageViewmodel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observer()

        calls()

        onClick()

        //UserProduct.userProductId?.let{viewModel.getProductById(it)} Sobraba esto esta contenido en la funcion calls
    }

    @SuppressLint("SetTextI18n")
    private fun observer() {
        viewModel.data.observe(viewLifecycleOwner) { state ->
            when(state){
                is ProductState.Loading -> {
                    showLoading()
                }

                is ProductState.Success -> {
                   hideLoading()
                    state.data?.price?.let { UserProduct.price = it
                    } ?: { UserProduct.price = 0.0
                    }
                    binding.tvProductName.text = state.data?.name
                    binding.tvPrice.text = "$ ${state.data?.price}"
                    binding.btProduct.setBackgroundResource(R.drawable.button_image2)
                    //state.data?.images?.let { images -> Hay que dar la posibilidad de que sea null para poder cargar la pantalla de error
                        state.data?.let{updateUI(it.images, it.isFavorite)}
                    //}
                }

                is ProductState.Error -> {
                    hideLoading()
                    binding.icMsgError.cslImgError.visibility = View.VISIBLE
                    showMessageError(state.message)
                }
            }
        }
    }



    private fun showLoading() {
        binding.progressBarr.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.progressBarr.visibility = View.GONE
    }

    private fun showMessageError(message: String) {

        Toast.makeText(requireContext(), "Error: $message", Toast.LENGTH_SHORT).show()
    }

    private fun updateUI(images: List<Image>?, favorite: Boolean?) {
        if(images.isNullOrEmpty()){
            binding.icMsgError.cslImgError.visibility = View.VISIBLE
            binding.icMsgError.cslImgError.visibility = View.VISIBLE
        }else{
            binding.icMsgError.cslImgError.visibility = View.GONE
            val adapter = ProductImageAdapter(images, favorite)
            binding.rvItemImage.adapter = adapter
            binding.rvItemImage.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }



    }
    private fun calls() {
        UserProduct.userProductId?.let { productId ->
            viewModel.getProductById(productId)
        }
    }

    private fun onClick(){
        binding.btProduct.setOnClickListener {

            binding.btProduct.setBackgroundResource(R.drawable.button_image2)
            binding.btColors.setBackgroundResource(R.drawable.button_image1)
            binding.btSimilar.setBackgroundResource(R.drawable.button_image1)
            // Aquí puedes realizar otras acciones según sea necesario

            calls()
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

            goToSimilares()

        }
    }

    private fun goToSimilares() {
        val intent = Intent(context, SimilarActivity::class.java)
        startActivity(intent)
    }
}
