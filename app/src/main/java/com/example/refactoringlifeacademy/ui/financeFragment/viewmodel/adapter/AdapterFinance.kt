package com.example.refactoringlifeacademy.ui.financeFragment.viewmodel.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringlifeacademy.R
import com.example.refactoringlifeacademy.data.dto.model.FinanceMethods
import com.example.refactoringlifeacademy.databinding.ItemFinanceBinding

class AdapterFinance(private val financeMethodsList: List<FinanceMethods>) :
    RecyclerView.Adapter<FinanceMethodsHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceMethodsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_finance, parent, false)
        return FinanceMethodsHolder(view)
    }

    override fun getItemCount(): Int {
        return financeMethodsList.size
    }

    override fun onBindViewHolder(holder: FinanceMethodsHolder, position: Int) {
        holder.render(financeMethodsList[position])
    }
}

class FinanceMethodsHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemFinanceBinding.bind(view)
    fun render(value: FinanceMethods) {

        val bank = value.entity

        when (bank?.lowercase()) {
            "galicia" -> {
                binding.ivBank.setImageResource(R.drawable.galicia)
                binding.tvBank.isVisible = false
                binding.llCard.setBackgroundColor(binding.llCard.context.getColor(R.color.orange))
            }

            "santander" -> {
                binding.ivBank.setImageResource(R.drawable.santander)
                binding.tvBank.isVisible = false
                binding.llCard.setBackgroundColor(binding.llCard.context.getColor(R.color.black))
            }

            "otros" -> {
                binding.ivBank.setImageResource(R.drawable.bank)
                binding.tvBank.isVisible = true
                binding.tvBank.text = value.entity
                binding.llCard.setBackgroundColor(binding.llCard.context.getColor(R.color.black))
            }

            else -> {
                binding.ivBank.setImageResource(R.drawable.bank)
                binding.tvBank.isVisible = true
                binding.tvBank.text = value.entity
                binding.llCard.setBackgroundColor(binding.llCard.context.getColor(R.color.black))
            }

        }

        var textMethods = ""
        val methods = value.installments
        val stringCoutas = " cuotas: "

        methods?.let {
            it.forEach { element ->
                textMethods =
                    textMethods + element.quantity.toString() + stringCoutas + element.interest + "\n" + "\n"
            }
        }
        binding.tvFinanceMethods.text = textMethods
    }
}
