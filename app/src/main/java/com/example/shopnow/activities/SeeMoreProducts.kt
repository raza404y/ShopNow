package com.example.shopnow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shopnow.Constants
import com.example.shopnow.R
import com.example.shopnow.adapters.SeeMoreAdapter
import com.example.shopnow.databinding.ActivitySeeMoreProductsBinding
import com.example.shopnow.models.Products
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class SeeMoreProducts : AppCompatActivity() {
    private val binding : ActivitySeeMoreProductsBinding by lazy {
        ActivitySeeMoreProductsBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = SeeMoreAdapter(this@SeeMoreProducts)
        val arrayList = ArrayList<Products>()
        binding.seeMoreRecyclerView.adapter = adapter
        val layoutManager = LinearLayoutManager(this@SeeMoreProducts)
        binding.seeMoreRecyclerView.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true

        Firebase.firestore.collection(Constants.products).get().addOnSuccessListener {
            arrayList.clear()
            for (products in it){
                val allProducts = products.toObject(Products::class.java)
                arrayList.add(allProducts)
            }
            arrayList.shuffle()
            adapter.submitList(arrayList)
        }

    }




}