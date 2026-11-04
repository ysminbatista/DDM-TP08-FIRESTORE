package com.example.tp08_firestore.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tp08_firestore.databinding.ItemProductBinding
import com.example.tp08_firestore.model.Product
import java.text.NumberFormat
import java.util.Locale

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    private val items = mutableListOf<Product>()
    private val currency = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    fun submit(products: List<Product>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = items[position]
        holder.binding.txtName.text = product.name
        holder.binding.txtCategory.text = product.category
        holder.binding.txtPrice.text = currency.format(product.price)
    }

    override fun getItemCount() = items.size

    class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
}
