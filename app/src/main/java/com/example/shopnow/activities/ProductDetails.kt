package com.example.shopnow.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import coil.load
import com.example.shopnow.Constants
import com.example.shopnow.databinding.ActivityProductDetailsBinding
import com.example.shopnow.models.Products
import com.example.shopnow.models.WishList
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlin.Int as Int1

class ProductDetails : AppCompatActivity() {
    private val binding: ActivityProductDetailsBinding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }
    private lateinit var idFromMain: String
    private lateinit var idFromSeeMore: String
    private var count: Int1 = 1
    private var loadedProduct: Products? = null
    private var originalPrice = 0

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        enableEdgeToEdge()
        idFromMain = intent.getStringExtra("key_id").toString()
        idFromSeeMore = intent.getStringExtra("id").toString()


            loadData(idFromMain)
            loadData(idFromSeeMore)


        binding.increment.setOnClickListener {
            binding.counterTv.text = (++count).toString()
             val priceCount = count * originalPrice
            binding.priceTv.text = priceCount.toString()
        }

        binding.decrement.setOnClickListener {
            if (count > 1) {
                binding.counterTv.text = (--count).toString()
               val priceCount = count * originalPrice
                binding.priceTv.text = priceCount.toString()
            }
        }

        binding.addtowishList.setOnClickListener {

            loadedProduct?.let {
                val productImage = it.productImgUrl
                val name = it.productName
                val price = it.price
                val quantities = binding.counterTv.text.toString().toIntOrNull() ?: 1
                val wishList = WishList(productImage, name, price, quantities)
                addWishListData(wishList)
            }
        }

        binding.addtoCart.setOnClickListener {
            loadedProduct?.let {
                val productImage = it.productImgUrl
                val name = it.productName
                val price = it.price
                val quantities = binding.counterTv.text.toString().toIntOrNull() ?: 1
                val wishList = WishList(productImage, name, price, quantities)
                addCartData(wishList)


            }
        }

    }

    private fun addWishListData(wishList: WishList) {
        Firebase.firestore.collection(Constants.WISHLIST).document(Firebase.auth.currentUser!!.uid)
            .collection(Constants.FAVOURITE).document(System.currentTimeMillis().toString())
            .set(wishList).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this@ProductDetails, "Added to favourite", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this@ProductDetails,
                        it.exception!!.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun addCartData(wishList: WishList) {
        Firebase.firestore.collection(Constants.CART).document(Firebase.auth.currentUser!!.uid)
            .collection(Constants.ADDED_TO_CART).document(System.currentTimeMillis().toString())
            .set(wishList).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this@ProductDetails, "Added to Cart", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this@ProductDetails,
                        it.exception!!.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
    private fun loadData(productId: String) {
        Firebase.firestore.collection(Constants.PRODUCTS).document(productId).get()
            .addOnSuccessListener {
                val products = it.toObject(Products::class.java)
                products?.id = it.id

                loadedProduct = products
                binding.dressImage.load(products?.productImgUrl) {
                    crossfade(true)
                }
                products?.apply {
                    binding.dressNameTv.text = productName
                    binding.priceTv.text = price.toString()
                    binding.description.text = description
                    originalPrice = products.price
                }
            }


    }

}