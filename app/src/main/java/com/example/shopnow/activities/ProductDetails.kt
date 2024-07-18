package com.example.shopnow.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import coil.load
import com.example.shopnow.Constants
import com.example.shopnow.databinding.ActivityProductDetailsBinding
import com.example.shopnow.models.Products
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductDetails : AppCompatActivity() {
    private val binding: ActivityProductDetailsBinding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var idFromMain: String
    private lateinit var idFromSeeMore: String
    private var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        idFromMain = intent.getStringExtra("key_id").toString()
        idFromSeeMore = intent.getStringExtra("id").toString()

            loadData(idFromSeeMore)
            loadData(idFromMain)


        binding.increment.setOnClickListener {
            binding.counterTv.text = (++count).toString()
        }

        binding.decrement.setOnClickListener {
            if (count > 0) {
                binding.counterTv.text = (--count).toString()
            }
        }

    }

    private fun loadData(productId: String) {
        Firebase.firestore.collection(Constants.products).document(productId).get().addOnSuccessListener {
            val products = it.toObject(Products::class.java)
            products?.id = it.id

            binding.dressImage.load(products?.productImgUrl) {
                crossfade(true)
            }
            products?.apply {
                binding.dressNameTv.text = productName
                binding.priceTv.text = price.toString()
                binding.description.text = description
            }
        }
    }

}