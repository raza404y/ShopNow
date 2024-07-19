package com.example.shopnow.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopnow.Constants
import com.example.shopnow.adapters.SeeMoreAdapter
import com.example.shopnow.databinding.ActivityCategoriesProductsBinding
import com.example.shopnow.models.Products
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CategoriesProducts : AppCompatActivity() {
    private val binding : ActivityCategoriesProductsBinding by lazy {
        ActivityCategoriesProductsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = SeeMoreAdapter(this@CategoriesProducts)
        val arrayList = ArrayList<Products>()
        binding.categoriesRV.adapter = adapter
        val layoutManager = LinearLayoutManager(this@CategoriesProducts)
        binding.categoriesRV.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        binding.backbtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val categoryName = intent.getStringExtra("key_name")
        binding.backbtn.text = "$categoryName Category"

        Firebase.firestore.collection(Constants.PRODUCTS).whereEqualTo("category", categoryName).get()
            .addOnSuccessListener { querySnapshot ->
                arrayList.clear()
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot) {
                        val productModel = document.toObject(Products::class.java)
                        productModel.id = document.id
                        arrayList.add(productModel)
                    }
                    adapter.submitList(arrayList)
                } else {
                    Log.d("Firestore", "No documents found")
                    Toast.makeText(this@CategoriesProducts, "No Product is available!", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching documents", e)
            }

    }
}