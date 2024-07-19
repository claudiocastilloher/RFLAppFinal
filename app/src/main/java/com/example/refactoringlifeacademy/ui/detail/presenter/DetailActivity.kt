package com.example.refactoringlifeacademy.ui.detail.presenter

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.refactoringlifeacademy.databinding.ActivityDetailBinding
import com.example.refactoringlifeacademy.ui.buy.presenter.BuyActivity
import com.example.refactoringlifeacademy.ui.commentFragment.presenter.CommentFragment
import com.example.refactoringlifeacademy.ui.descriptionFragmen.presenter.DescriptionFragment
import com.example.refactoringlifeacademy.ui.financeFragment.presenter.FinanceFragment
import com.example.refactoringlifeacademy.ui.home.presenter.HomeActivity

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private var productPrice: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstLoad()
        initLeftBar()
        goToBuy()
        goToHome()
        getInformation()

    }

    private fun getInformation() {
        productPrice = intent.getDoubleExtra("productPrice", 0.0)
    }

    private fun firstLoad() {
        binding.ellipseImage.visibility = View.VISIBLE
        loadFragment(ImageFragment())
    }
    private fun initLeftBar() {
        binding.tvImag.setOnClickListener {
            binding.ellipseDescrip.visibility = View.INVISIBLE
            binding.ellipseFinance.visibility = View.INVISIBLE
            binding.ellipseComment.visibility = View.INVISIBLE
            binding.ellipseImage.visibility = View.VISIBLE
            loadFragment(ImageFragment())
        }

        binding.tvDescript.setOnClickListener {
            binding.ellipseFinance.visibility = View.INVISIBLE
            binding.ellipseComment.visibility = View.INVISIBLE
            binding.ellipseImage.visibility = View.INVISIBLE
            binding.ellipseDescrip.visibility = View.VISIBLE
            loadFragment(DescriptionFragment())
        }

        binding.tvFinanc.setOnClickListener {
            binding.ellipseComment.visibility = View.INVISIBLE
            binding.ellipseImage.visibility = View.INVISIBLE
            binding.ellipseDescrip.visibility = View.INVISIBLE
            binding.ellipseFinance.visibility = View.VISIBLE
            loadFragment(FinanceFragment.newInstance(productPrice))
        }

        binding.tvComent.setOnClickListener {
            binding.ellipseImage.visibility = View.INVISIBLE
            binding.ellipseDescrip.visibility = View.INVISIBLE
            binding.ellipseFinance.visibility = View.INVISIBLE
            binding.ellipseComment.visibility = View.VISIBLE
            loadFragment(CommentFragment.newInstance(productPrice))
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
    private fun goToHome(){
        binding.arrowLeft1.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun goToBuy(){
        binding.btnBuy.setOnClickListener {
            val intent = Intent(this, BuyActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
