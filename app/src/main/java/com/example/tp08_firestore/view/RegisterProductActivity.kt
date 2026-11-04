package com.example.tp08_firestore.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.example.tp08_firestore.R
import com.example.tp08_firestore.databinding.ActivityRegisterProductBinding
import com.example.tp08_firestore.model.Product
import com.google.firebase.firestore.FirebaseFirestore

class RegisterProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.edtName.doOnTextChanged { _, _, _, _ -> binding.tilName.error = null }
        binding.edtCategory.doOnTextChanged { _, _, _, _ -> binding.tilCategory.error = null }
        binding.edtPrice.doOnTextChanged { _, _, _, _ -> binding.tilPrice.error = null }

        binding.btnBack.setOnClickListener { finish() }
        binding.btnSave.setOnClickListener { save() }
    }

    private fun save() {
        val name = binding.edtName.text.toString().trim()
        val category = binding.edtCategory.text.toString().trim()
        val price = binding.edtPrice.text.toString().trim().replace(',', '.').toDoubleOrNull()

        binding.tilName.error = if (name.isEmpty()) getString(R.string.error_name_required) else null
        binding.tilCategory.error =
            if (category.isEmpty()) getString(R.string.error_category_required) else null
        binding.tilPrice.error =
            if (price == null || price < 0) getString(R.string.error_price_invalid) else null

        if (name.isEmpty() || category.isEmpty() || price == null || price < 0) return

        binding.btnSave.isEnabled = false
        val data = hashMapOf(
            Product.NAME to name,
            Product.CATEGORY to category,
            Product.PRICE to price
        )
        // add() gera o ID do documento automaticamente.
        FirebaseFirestore.getInstance().collection(Product.COLLECTION)
            .add(data)
            .addOnSuccessListener {
                Toast.makeText(this, R.string.saved_ok, Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener {
                binding.btnSave.isEnabled = true
                Toast.makeText(this, R.string.error_save, Toast.LENGTH_LONG).show()
            }
    }
}
