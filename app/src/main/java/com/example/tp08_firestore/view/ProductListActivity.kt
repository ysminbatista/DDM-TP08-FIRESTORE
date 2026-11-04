package com.example.tp08_firestore.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tp08_firestore.R
import com.example.tp08_firestore.databinding.ActivityProductListBinding
import com.example.tp08_firestore.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class ProductListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProductListBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerProducts.adapter = adapter
        binding.btnAdd.setOnClickListener {
            startActivity(Intent(this, RegisterProductActivity::class.java))
        }
    }

    // Recarrega ao voltar da tela de cadastro.
    override fun onResume() {
        super.onResume()
        loadProducts()
    }

    private fun loadProducts() {
        binding.progress.visibility = View.VISIBLE
        FirebaseFirestore.getInstance().collection(Product.COLLECTION)
            .get()
            .addOnSuccessListener { result ->
                val products = result.documents.map { doc ->
                    Product(
                        id = doc.id,
                        name = doc.getString(Product.NAME).orEmpty(),
                        category = doc.getString(Product.CATEGORY).orEmpty(),
                        price = doc.getDouble(Product.PRICE) ?: 0.0
                    )
                }.sortedBy { it.name.lowercase() }
                adapter.submit(products)
                binding.txtEmpty.visibility = if (products.isEmpty()) View.VISIBLE else View.GONE
                binding.progress.visibility = View.GONE
            }
            .addOnFailureListener {
                binding.progress.visibility = View.GONE
                Toast.makeText(this, R.string.error_load, Toast.LENGTH_LONG).show()
            }
    }
}
